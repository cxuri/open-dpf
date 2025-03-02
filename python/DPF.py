import base64
import json
from datetime import datetime
from cryptography.hazmat.primitives.ciphers import Cipher, algorithms, modes
from cryptography.hazmat.backends import default_backend
from cryptography.hazmat.primitives import padding
import os

class DPF:

    def __init__(self):
        self.version = 1.0

    def encrypt(self, data, key, salt):
        """
        Encrypt the data using AES encryption and return the encrypted string.
        """
        # Padding data to be block-size aligned
        padder = padding.PKCS7(128).padder()
        padded_data = padder.update(data.encode()) + padder.finalize()

        # Key and Salt preparation
        key = key.encode()
        salt = salt.encode()

        # Simple example using AES CBC mode with a randomly generated IV (Initialization Vector)
        iv = os.urandom(16)

        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
        encryptor = cipher.encryptor()
        encrypted_data = encryptor.update(padded_data) + encryptor.finalize()

        # Concatenate the IV and encrypted data for storage
        return base64.b64encode(iv + encrypted_data).decode()

    def decrypt(self, encrypted_data, key, salt):
        """
        Decrypt the data using AES encryption and return the decrypted string.
        """
        encrypted_data = base64.b64decode(encrypted_data)

        # Extract the IV (first 16 bytes) and the encrypted part of the data
        iv = encrypted_data[:16]
        cipher_text = encrypted_data[16:]

        # Prepare the cipher for decryption
        key = key.encode()
        cipher = Cipher(algorithms.AES(key), modes.CBC(iv), backend=default_backend())
        decryptor = cipher.decryptor()
        padded_data = decryptor.update(cipher_text) + decryptor.finalize()

        # Unpad the data to get the original string
        unpadder = padding.PKCS7(128).unpadder()
        data = unpadder.update(padded_data) + unpadder.finalize()

        return data.decode()

    def create(self, filename, data_list, description, author, key, salt):
        """
        Create a custom .dpf file with multiple data items, encrypted and Base64 format.
        """
        # Get the current timestamp
        timestamp = datetime.now().strftime("%d-%m-%Y %H:%M:%S")

        # Encrypt each data item and collect the results
        encrypted_data = []
        for data in data_list:
            encrypted_data.append(self.encrypt(data, key, salt))

        # Prepare the metadata
        metadata = {
            "version": self.version,
            "author": author,
            "created_on": timestamp,
            "description": description,
            "encrypted_data": ";".join(encrypted_data)
        }

        # Base64 encode the metadata string
        base64_encoded = base64.b64encode(json.dumps(metadata).encode()).decode()

        # Write the Base64 encoded data to a binary file
        try:
            with open(f"{filename}.dpf", "wb") as file:
                file.write(base64_encoded.encode())
            print(f"File created: {filename}.dpf")
        except IOError as e:
            print("An error occurred while creating or writing the file.")
            print(str(e))

    def read(self, filename, key, salt):
        """
        Read and decrypt the data from the .dpf file, returns a list of decrypted data.
        """
        decrypted_data_list = []

        try:
            with open(filename, "rb") as file:
                encoded_data = file.read()

            # Base64 decode the content
            decoded_string = base64.b64decode(encoded_data).decode()

            # Parse the metadata
            metadata = json.loads(decoded_string)
            version_str = metadata["version"]
            author = metadata["author"]
            created_on = metadata["created_on"]
            description = metadata["description"]
            encrypted_data_string = metadata["encrypted_data"]

            # Split the encrypted data (separated by semicolon)
            encrypted_data_list = encrypted_data_string.split(";")

            decrypted_data_list.extend([version_str, author, created_on, description])

            # Decrypt each item and add it to the list
            for encrypted_data in encrypted_data_list:
                decrypted_data = self.decrypt(encrypted_data, key, salt)
                decrypted_data_list.append(decrypted_data)

        except IOError as e:
            print("An error occurred while reading the file.")
            print(str(e))
        except Exception as e:
            print("An error occurred while processing the file content.")
            print(str(e))

        return decrypted_data_list
