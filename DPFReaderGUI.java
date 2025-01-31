import java.awt.*;
import java.awt.Desktop;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class DPFReaderGUI {

    private JFrame frame;
    private JTextField filePathField, keyField, saltField;
    private JTextArea decryptedDataArea;
    private JButton openFileButton, readButton, aboutButton; // Fixed typo here

    // These labels will display the extracted metadata
    private JLabel versionLabel, authorLabel, createdOnLabel, descriptionLabel;

    private DPF dpf;

    public DPFReaderGUI() {
        dpf = new DPF(); // Instantiate DPF class for reading the .dpf file

        frame = new JFrame("DPF File Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500); // Fixed size for the frame
        frame.setResizable(false); // Disable resizing of the window
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10)); // Add some spacing between rows
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        inputPanel.add(new JLabel("File Path:"));
        filePathField = new JTextField();
        filePathField.setPreferredSize(new Dimension(200, 25));
        inputPanel.add(filePathField);

        inputPanel.add(new JLabel("Decryption Key:"));
        keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(200, 25));
        inputPanel.add(keyField);

        inputPanel.add(new JLabel("Decryption Salt:"));
        saltField = new JTextField();
        saltField.setPreferredSize(new Dimension(200, 25));
        inputPanel.add(saltField);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Panel for buttons (make sure the panel is placed at the bottom)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        openFileButton = new JButton("Select File");
        openFileButton.setBackground(Color.BLUE);
        openFileButton.setForeground(Color.WHITE);
        openFileButton.setFocusPainted(false);
        buttonPanel.add(openFileButton);

        readButton = new JButton("Read & Decrypt");
        readButton.setBackground(Color.BLACK);
        readButton.setForeground(Color.WHITE);
        readButton.setFocusPainted(false);
        buttonPanel.add(readButton);

        aboutButton = new JButton("About us"); // Fixed typo here
        aboutButton.setBackground(Color.RED);
        aboutButton.setForeground(Color.WHITE);
        aboutButton.setFocusPainted(false);
        buttonPanel.add(aboutButton);

        frame.add(buttonPanel, BorderLayout.SOUTH); // Make sure buttons are at the bottom

        // Decrypted data area
        decryptedDataArea = new JTextArea();
        decryptedDataArea.setEditable(false);
        decryptedDataArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        decryptedDataArea.setLineWrap(true); // Wrap text to fit within the window
        decryptedDataArea.setWrapStyleWord(true); // Wrap words instead of breaking in the middle

        // Metadata Tab with version, author, etc.
        JTabbedPane tabbedPane = new JTabbedPane();

        // Panel for metadata information
        JPanel metaPanel = new JPanel();
        metaPanel.setLayout(new GridLayout(4, 1, 10, 10));
        metaPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Labels for metadata (will be updated after decryption)
        versionLabel = new JLabel("Version: ");
        authorLabel = new JLabel("Author: ");
        createdOnLabel = new JLabel("Created On: ");
        descriptionLabel = new JLabel("Description: ");

        metaPanel.add(versionLabel);
        metaPanel.add(authorLabel);
        metaPanel.add(createdOnLabel);
        metaPanel.add(descriptionLabel);

        // Add metadata panel as a tab
        tabbedPane.addTab("Metadata", metaPanel);

        // Scrollable decrypted data area
        JScrollPane scrollPane = new JScrollPane(decryptedDataArea);
        scrollPane.setPreferredSize(new Dimension(550, 200));
        JPanel decryptedPanel = new JPanel(new BorderLayout());
        decryptedPanel.add(scrollPane, BorderLayout.CENTER);

        // Add decrypted data as a tab
        tabbedPane.addTab("Decrypted Data", decryptedPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);

        // Action Listeners for buttons
        openFileButton.addActionListener(e -> selectFile());
        readButton.addActionListener(e -> readAndDecryptFile());
        aboutButton.addActionListener(e -> showAbout());

        frame.setVisible(true);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void showAbout() {
        String aboutMessage =
            "<html>" +
            "DPF File Reader V1.0, Made by Cxuri<br>" +
            "<a href='https://github.com/cxuri'>Visit our GitHub</a>" + // Correct URL
            "</html>";

        // Create a JEditorPane to display the HTML content with clickable link
        JEditorPane editorPane = new JEditorPane("text/html", aboutMessage);
        editorPane.setEditable(false); // Make the editor pane non-editable
        editorPane.setBackground(UIManager.getColor("Panel.background")); // Set background color to match dialog
        editorPane.addHyperlinkListener(
            new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (
                        HyperlinkEvent.EventType.ACTIVATED.equals(
                            e.getEventType()
                        )
                    ) {
                        // Open the link in the default browser
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (Exception ex) {
                            ex.printStackTrace(); // Handle potential exceptions
                        }
                    }
                }
            }
        );

        // Show the dialog with the JEditorPane (making the content scrollable if it's too large)
        JOptionPane.showMessageDialog(
            frame,
            new JScrollPane(editorPane), // Wrap in JScrollPane for large content
            "About", // Title of the dialog
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void readAndDecryptFile() {
        decryptedDataArea.setText("");
        versionLabel.setText("Version: N/A");
        authorLabel.setText("Author: N/A");
        createdOnLabel.setText("Created On: N/A");
        descriptionLabel.setText("Description: N/A");

        String filePath = filePathField.getText();
        String key = keyField.getText();
        String salt = saltField.getText();

        if (filePath.isEmpty() || key.isEmpty() || salt.isEmpty()) {
            JOptionPane.showMessageDialog(
                frame,
                "Please provide file path, key, and salt.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Read and decrypt the file
        try {
            List<String> decryptedData = dpf.read(filePath, key, salt);

            // Check if all data is null or empty (after the first 4 indices)
            List<String> filteredData = decryptedData.size() > 4
                ? decryptedData.subList(4, decryptedData.size())
                : List.of();
            if (
                filteredData
                    .stream()
                    .allMatch(data -> data == null || data.trim().isEmpty())
            ) {
                JOptionPane.showMessageDialog(
                    frame,
                    "Invalid salt or IV. Decryption failed.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Remove the first 4 indices from the decrypted data
            decryptedDataArea.setText(""); // Clear previous content
            for (String data : filteredData) {
                decryptedDataArea.append(data + "\n");
            }

            // Extract and display metadata from the first 4 items if available
            if (decryptedData.size() >= 4) {
                versionLabel.setText("Version: " + decryptedData.get(0));
                authorLabel.setText("Author: " + decryptedData.get(1));
                createdOnLabel.setText("Created On: " + decryptedData.get(2));
                descriptionLabel.setText(
                    "Description: " + decryptedData.get(3)
                );
            } else {
                versionLabel.setText("Version: N/A");
                authorLabel.setText("Author: N/A");
                createdOnLabel.setText("Created On: N/A");
                descriptionLabel.setText("Description: N/A");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                frame,
                "Error while reading or decrypting the file: " +
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DPFReaderGUI::new); // Launch the GUI on the Event Dispatch Thread
    }
}
