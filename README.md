# 🔐 DPF: Data Protection Framework

A lightweight Python-based **Data Protection Framework (DPF)** for encrypting and securely storing sensitive information in custom `.dpf` files using **AES encryption**.

## ✨ Features

- 🔑 **AES Encryption (CBC Mode)** for secure data storage.
- 🛠️ **Automatic PKCS7 Padding** ensures data integrity.
- 🔏 **Metadata storage** (author, creation timestamp, description).
- 📂 **Custom `.dpf` format** for structured encrypted data storage.
- 📖 **Easy-to-use API** for encrypting and decrypting data.

---

## 📦 Installation

Ensure you have **Python 3.6+** and install dependencies:

```sh
pip install cryptography
pip install tk #for gui
```
# 🚀 Usage
### 1️⃣ Encrypt and Store Data

```python
from dpf import DPF

dpf = DPF()
data_list = ["Secret1", "ConfidentialData2"]
dpf.create("my_secure_file", data_list, "Sample Encrypted File", "John Doe", "MySecretKey123", "MySaltValue")
```

### 2️⃣ Read and Decrypt Data

```python
from dpf import DPF
dpf = DPF()

decrypted_data = dpf.read("my_secure_file.dpf", "MySecretKey123", "MySaltValue")
print(decrypted_data)  # Original data restored

```

# GUI MODE (read only)

```sh

cd python
python gui.py

```
## Usage JAVA 📚
```
git clone https://github.com/cxuri/open-dpf.git
```

### Encrypting and Decrypting Data in Java

The following Java code demonstrates how to create, save, and decrypt a `.dpf` file using the DPF format.

#### Example:

```java
import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        List<String> dataList = Arrays.asList(
            "Hello, World!",
            "This is a test.",
            "Sample encrypted data."
        );

        // Metadata information
        String description = "Sample file with encrypted data";
        String author = "John Doe";

        // Encryption key and salt (make sure these match when reading the file)
        String key = "mysecretkey";
        String salt = "randomsalt123";

        // Create a DPF instance
        DPF dpf = new DPF();

        // Create and save the .dpf file
        String filename = "testfile";
        dpf.create(filename, dataList, description, author, key, salt);

        // Now, let's read the content from the file and decrypt the data
        List<String> decryptedDataList = dpf.read(filename, key, salt);

        // Print the decrypted data
        System.out.println("Decrypted Data from file:");
        for (String decryptedData : decryptedDataList) {
            System.out.println(decryptedData);
        }# 🔐 DPF: Data Protection Framework
```

        A lightweight **Data Protection Framework (DPF)** for encrypting and securely storing sensitive information in custom `.dpf` files using **AES-256 encryption**.

        ## ✨ Features

        - 🔑 **AES-256 Encryption (CBC Mode)** for secure data storage.
        - 🛠️ **Automatic PKCS7 Padding** ensures data integrity.
        - 🔏 **Metadata storage** (author, creation timestamp, description).
        - 📂 **Custom `.dpf` format** for structured encrypted data storage.
        - 📖 **Easy-to-use API** for encrypting and decrypting data.
        - 🖥️ **Cross-platform compatibility** (Python and Java support).
        - 📜 **Graphical User Interface (GUI) for reading `.dpf` files**.

        ---

        ## 📦 Installation

        clone the repository:

        ```sh
        git clone https://github.com/cxuri/open-dpf.git
        ```

        #### for python
        Ensure you have **Python 3.6+** and install dependencies:

        ```sh
        pip install cryptography
        pip install tk  # for GUI
        ```



        ### 🔗 Quick Links:
        - [Python Usage](#-python-implementation)
        - [Java Usage](#-java-implementation)

        ---

        # 🚀 Usage

        ## 🔹 Python Implementation

        ```sh
        cd python
        ```

        ### 1️⃣ Encrypt and Store Data

        ```python
        from dpf import DPF

        dpf = DPF()
        data_list = ["Secret1", "ConfidentialData2"]
        dpf.create("my_secure_file", data_list, "Sample Encrypted File", "John Doe", "MySecretKey123", "MySaltValue")
        ```

        ### 2️⃣ Read and Decrypt Data

        ```python
        from dpf import DPF

        dpf = DPF()
        decrypted_data = dpf.read("my_secure_file.dpf", "MySecretKey123", "MySaltValue")
        print(decrypted_data)  # Original data restored
        ```

        ### 🔹 GUI Mode (Read Only)

        ```sh
        python gui.py
        ```

        ---

        ## 🔹 Java Implementation

        ```sh
        cd java
        ```

        The following Java code demonstrates how to create, save, and decrypt a `.dpf` file using the DPF format.

        ### Example:

        ```java
        import java.util.Arrays;
        import java.util.List;

        public class Test {
            public static void main(String[] args) {
                List<String> dataList = Arrays.asList(
                    "Hello, World!",
                    "This is a test.",
                    "Sample encrypted data."
                );

                // Metadata information
                String description = "Sample file with encrypted data";
                String author = "John Doe";

                // Encryption key and salt (must match when reading the file)
                String key = "mysecretkey";
                String salt = "randomsalt123";

                // Create a DPF instance
                DPF dpf = new DPF();

                // Create and save the .dpf file
                String filename = "testfile";
                dpf.create(filename, dataList, description, author, key, salt);

                // Read the content and decrypt the data
                List<String> decryptedDataList = dpf.read(filename, key, salt);

                // Print decrypted data
                System.out.println("Decrypted Data from file:");
                for (String decryptedData : decryptedDataList) {
                    System.out.println(decryptedData);
                }
            }
        }
        ```

        ---

        ## 📦 Releases

        Download the latest release of **DPF** from the [Releases page](https://github.com/cxuri/open-dpf/releases). Stay updated with the latest improvements, bug fixes, and new features! 🚀

        ---

        ## 🖥️ DPFReader - GUI for `.dpf` Files

        A graphical tool is available to **read and view** `.dpf` files easily.

        ![DPFReader Screenshot](https://github.com/user-attachments/assets/507bc0bd-690a-484d-b262-22242a377feb)

        ---

        **Directory Structure:**

        ```
        open-dpf/
        │── python/    # Python-based DPF implementation
        │── java/      # Java-based DPF implementation
        │── gui.py     # GUI application for reading `.dpf` files
        │── README.md  # Documentation
        ```

        ---

        ### 🔗 Contribute & Support

        We welcome contributions! Feel free to submit issues or pull requests on [GitHub](https://github.com/cxuri/open-dpf). If you find DPF useful, consider giving it a ⭐ to support the project!

        Happy encrypting! 🔐🚀


    }
}
```

## Releases 📦

You can download the latest release of DPF from the [Releases page](https://github.com/cxuri/open-dpf/releases). Make sure to check out each release for important updates, improvements, and bug fixes! 🚀


## DPFReader To Read .dpf Files
![Screenshot from 2025-01-31 22-53-01](https://github.com/user-attachments/assets/507bc0bd-690a-484d-b262-22242a377feb)
