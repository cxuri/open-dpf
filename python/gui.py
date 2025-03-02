import tkinter as tk
from tkinter import filedialog, messagebox, scrolledtext
import webbrowser
from datetime import datetime

class DPFReaderGUI:
    def __init__(self, root):
        self.root = root
        self.root.title("DPF File Reader")
        self.root.geometry("600x500")
        self.root.resizable(False, False)

        self.dpf = DPF()  # Assume DPF is a class with the logic for reading and decrypting .dpf files

        # Panel for input fields
        self.file_path_label = tk.Label(self.root, text="File Path:")
        self.file_path_label.pack(padx=20, pady=5, anchor="w")

        self.file_path_field = tk.Entry(self.root, width=40)
        self.file_path_field.pack(padx=20, pady=5)

        self.key_label = tk.Label(self.root, text="Decryption Key:")
        self.key_label.pack(padx=20, pady=5, anchor="w")

        self.key_field = tk.Entry(self.root, width=40)
        self.key_field.pack(padx=20, pady=5)

        self.salt_label = tk.Label(self.root, text="Decryption Salt:")
        self.salt_label.pack(padx=20, pady=5, anchor="w")

        self.salt_field = tk.Entry(self.root, width=40)
        self.salt_field.pack(padx=20, pady=5)

        # Buttons panel
        self.button_frame = tk.Frame(self.root)
        self.button_frame.pack(padx=20, pady=10, anchor="center")

        self.open_file_button = tk.Button(self.button_frame, text="Select File", command=self.select_file, bg="blue", fg="white")
        self.open_file_button.pack(side="left", padx=10)

        self.read_button = tk.Button(self.button_frame, text="Read & Decrypt", command=self.read_and_decrypt_file, bg="black", fg="white")
        self.read_button.pack(side="left", padx=10)

        self.about_button = tk.Button(self.button_frame, text="About Us", command=self.show_about, bg="red", fg="white")
        self.about_button.pack(side="left", padx=10)

        # Text area for decrypted data
        self.decrypted_data_area = scrolledtext.ScrolledText(self.root, width=70, height=10, wrap=tk.WORD, font=("Courier", 10))
        self.decrypted_data_area.pack(padx=20, pady=10)

        # Metadata labels
        self.version_label = tk.Label(self.root, text="Version: N/A")
        self.version_label.pack(padx=20, anchor="w")

        self.author_label = tk.Label(self.root, text="Author: N/A")
        self.author_label.pack(padx=20, anchor="w")

        self.created_on_label = tk.Label(self.root, text="Created On: N/A")
        self.created_on_label.pack(padx=20, anchor="w")

        self.description_label = tk.Label(self.root, text="Description: N/A")
        self.description_label.pack(padx=20, anchor="w")

    def select_file(self):
        file_path = filedialog.askopenfilename(title="Select DPF File")
        if file_path:
            self.file_path_field.delete(0, tk.END)
            self.file_path_field.insert(0, file_path)

    def show_about(self):
        about_message = (
            "DPF File Reader V1.0, Made by Cxuri\n"
            "Visit our GitHub: https://github.com/cxuri"
        )
        messagebox.showinfo("About", about_message)

    def read_and_decrypt_file(self):
        file_path = self.file_path_field.get()
        key = self.key_field.get()
        salt = self.salt_field.get()

        if not file_path or not key or not salt:
            messagebox.showerror("Error", "Please provide file path, key, and salt.")
            return

        try:
            decrypted_data = self.dpf.read(file_path, key, salt)

            # Check if data is valid and contains metadata
            if len(decrypted_data) < 4 or all(data is None or data.strip() == "" for data in decrypted_data[4:]):
                messagebox.showerror("Error", "Invalid salt or IV. Decryption failed.")
                return

            # Clear previous content and display new decrypted data
            self.decrypted_data_area.delete(1.0, tk.END)
            for data in decrypted_data[4:]:
                self.decrypted_data_area.insert(tk.END, data + "\n")

            # Display metadata if available
            self.version_label.config(text=f"Version: {decrypted_data[0] if len(decrypted_data) > 0 else 'N/A'}")
            self.author_label.config(text=f"Author: {decrypted_data[1] if len(decrypted_data) > 1 else 'N/A'}")
            self.created_on_label.config(text=f"Created On: {decrypted_data[2] if len(decrypted_data) > 2 else 'N/A'}")
            self.description_label.config(text=f"Description: {decrypted_data[3] if len(decrypted_data) > 3 else 'N/A'}")
        except Exception as e:
            messagebox.showerror("Error", f"Error while reading or decrypting the file: {str(e)}")


class DPF:
    def read(self, file_path, key, salt):
        # Example function to simulate reading and decrypting a file
        # Replace this logic with your actual decryption logic
        return [
            "1.0",  # Version
            "Cxuri",  # Author
            str(datetime.now()),  # Created On
            "A sample description.",  # Description
            "Decrypted data line 1.",
            "Decrypted data line 2.",
            "Decrypted data line 3."
        ]


if __name__ == "__main__":
    root = tk.Tk()
    app = DPFReaderGUI(root)
    root.mainloop()
