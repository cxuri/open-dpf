# DPF (Data Protected Format) 🔒📁

DPF (Data Protected Format) is an **open-source**, **secure file format** designed for storing sensitive data such as passwords, notes, and other confidential information. By utilizing **AES-256 encryption**, it ensures that your data remains protected from unauthorized access. This makes DPF the ideal solution for securely storing personal or sensitive information. 🛡️

## Features ✨

- **AES-256 Encryption**: Provides robust security for your sensitive data 🔐.
- **Open Source**: Transparent, community-driven, and free to use 🔄.
- **Simple Format**: Easily store and manage encrypted data in a secure and portable file format 📂.
- **Cross-platform**: Compatible with different platforms and tools 🌍.

## Usage 📚

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
        }
    }
}
```

## DPFReader To Read .dpf Files
![Screenshot from 2025-01-31 22-53-01](https://github.com/user-attachments/assets/507bc0bd-690a-484d-b262-22242a377feb)
