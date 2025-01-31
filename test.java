import java.util.Arrays;
import java.util.List;

public class test {

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
