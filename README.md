# 🔐 DPF: Data Protection Framework

**DPF** is a lightweight, cross-platform **Data Protection Framework** for securely encrypting and storing sensitive information in custom `.dpf` files using **AES-256 encryption**.

## ✨ Features

- 🔑 **AES-256 Encryption (CBC Mode)** for robust data security.
- 🛠️ **Automatic PKCS7 Padding** ensures data integrity.
- 🔏 **Metadata storage** (author, creation timestamp, description).
- 📂 **Custom `.dpf` format** for structured encrypted storage.
- 📖 **Easy-to-use API** for seamless encryption and decryption.
- 🖥️ **Cross-platform support** (Python and Java).
- 📜 **Graphical User Interface (GUI) for reading `.dpf` files**.

---

## 📦 Installation

Clone the repository:

```sh
git clone https://github.com/cxuri/open-dpf.git
```

### Python Installation
Ensure you have **Python 3.6+**, then install dependencies:

```sh
pip install cryptography
pip install tk  # for GUI
```

### Java Usage
Simply clone the repository and navigate to the Java implementation.

---

## 🚀 Usage

### 🔹 Python Implementation

```sh
cd python
```

#### 1️⃣ Encrypt and Store Data

```python
from dpf import DPF

dpf = DPF()
data_list = ["Secret1", "ConfidentialData2"]
dpf.create("my_secure_file", data_list, "Sample Encrypted File", "John Doe", "MySecretKey123", "MySaltValue")
```

#### 2️⃣ Read and Decrypt Data

```python
from dpf import DPF

dpf = DPF()
decrypted_data = dpf.read("my_secure_file.dpf", "MySecretKey123", "MySaltValue")
print(decrypted_data)  # Original data restored
```

#### 🔹 GUI Mode (Read Only)

```sh
python gui.py
```

---

### 🔹 Java Implementation

```sh
cd java
```

#### Example Code:

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

        // Metadata
        String description = "Sample file with encrypted data";
        String author = "John Doe";

        // Encryption key and salt (must match for decryption)
        String key = "mysecretkey";
        String salt = "randomsalt123";

        // Create DPF instance
        DPF dpf = new DPF();

        // Create and save the .dpf file
        String filename = "testfile";
        dpf.create(filename, dataList, description, author, key, salt);

        // Read and decrypt the file
        List<String> decryptedDataList = dpf.read(filename, key, salt);

        // Print decrypted data
        System.out.println("Decrypted Data:");
        for (String decryptedData : decryptedDataList) {
            System.out.println(decryptedData);
        }
    }
}
```

---

## 📦 Releases

Download the latest version of **DPF** from the [Releases page](https://github.com/cxuri/open-dpf/releases). Stay updated with the latest improvements and features! 🚀

---

## 🖥️ DPFReader - GUI for `.dpf` Files

A graphical tool is available to **read and view** `.dpf` files easily.

![DPFReader Screenshot](https://github.com/user-attachments/assets/507bc0bd-690a-484d-b262-22242a377feb)

---

### 📂 Directory Structure

```
open-dpf/
│── python/    # Python-based DPF implementation
│── java/      # Java-based DPF implementation
│── README.md  # Documentation
```

---

### 🔗 Contribute & Support

We welcome contributions! Submit issues or pull requests on [GitHub](https://github.com/cxuri/open-dpf). If you find **DPF** useful, consider giving it a ⭐ to support the project!

Happy encrypting! 🔐🚀

