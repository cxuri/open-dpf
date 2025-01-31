import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DPF {

    double version = 1.0;
    encryption se = new encryption(); // Assuming Encryption is the correct class

    // Method to create a custom .dpf file with multiple data items, encrypted and Base64 format
    void create(
        String filename,
        List<String> dataList,
        String description,
        String author,
        String key,
        String salt
    ) {
        // Get the current timestamp
        LocalDateTime t = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
            "dd-MM-yyyy HH:mm:ss"
        );
        String timestamp = t.format(dtf);

        // Encrypt each data item in the list and collect the results
        StringBuilder encryptedData = new StringBuilder();
        for (String data : dataList) {
            encryptedData.append(se.encrypt(data, key, salt)).append(";");
        }

        // Remove the trailing semicolon
        if (encryptedData.length() > 0) {
            encryptedData.setLength(encryptedData.length() - 1);
        }

        // Define the header and metadata as a simple string (manually formatted)
        String metadata =
            "{" +
            "\"version\": " +
            version +
            "," +
            "\"author\": \"" +
            author +
            "\"," +
            "\"created_on\": \"" +
            timestamp +
            "\"," +
            "\"description\": \"" +
            description +
            "\"," +
            "\"encrypted_data\": \"" +
            encryptedData.toString() +
            "\"" +
            "}";

        // Base64 encode the metadata string
        String base64Encoded = Base64.getEncoder()
            .encodeToString(metadata.getBytes(StandardCharsets.UTF_8));

        // Write the Base64 encoded data to a binary file
        try {
            File newFile = new File(filename + ".dpf");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            }

            // Convert the Base64 string to a byte array
            byte[] base64Bytes = base64Encoded.getBytes(StandardCharsets.UTF_8);

            // Write the byte array to the file as binary
            try (FileOutputStream fos = new FileOutputStream(newFile)) {
                fos.write(base64Bytes);
                System.out.println(
                    "File written in Base64 encoded binary format."
                );
            }
        } catch (IOException e) {
            System.out.println(
                "An error occurred while creating or writing the file."
            );
            e.printStackTrace();
        }
    }

    // Reader function to read and decrypt the data from the .dpf file, returns a list of decrypted data
    List<String> read(String filename, String key, String salt) {
        List<String> decryptedDataList = new ArrayList<>();

        try {
            // Read the .dpf file
            File file = new File(filename);
            byte[] encodedData = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(encodedData);
            }

            // Base64 decode the content
            String decodedString = new String(
                Base64.getDecoder().decode(encodedData),
                StandardCharsets.UTF_8
            );

            // Manually parse the metadata
            String versionStr = decodedString
                .split("\"version\": ")[1].split(",")[0];
            String author = decodedString
                .split("\"author\": \"")[1].split("\"")[0];
            String createdOn = decodedString
                .split("\"created_on\": \"")[1].split("\"")[0];
            String description = decodedString
                .split("\"description\": \"")[1].split("\"")[0];
            String encryptedDataString = decodedString
                .split("\"encrypted_data\": \"")[1].split("\"")[0];

            // Split the encrypted data (separated by semicolon)
            String[] encryptedDataArray = encryptedDataString.split(";");

            decryptedDataList.add(versionStr);
            decryptedDataList.add(author);
            decryptedDataList.add(createdOn);
            decryptedDataList.add(description);

            // Decrypt each item and add it to the list
            for (String encryptedData : encryptedDataArray) {
                String decryptedData = se.decrypt(encryptedData, key, salt);
                decryptedDataList.add(decryptedData);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(
                "An error occurred while processing the file content."
            );
            e.printStackTrace();
        }

        return decryptedDataList;
    }
}
