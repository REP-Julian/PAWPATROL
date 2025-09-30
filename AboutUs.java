import javax.swing.*;
import java.awt.*;
import java.io.File; // Import the File class
import java.io.IOException;
import javax.imageio.ImageIO;

public class AboutUs {

    public static void main(String[] args) {
        // Ensure the UI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            createAndShowGui();
        });
    }

    /**
     * Creates and returns the AboutUs panel for integration into other components
     */
    public static JPanel createAboutUsPanel() {
        // --- Main Container Panel ---
        // This panel will hold all other components and use a BoxLayout to stack them vertically.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(128, 128, 128)); // Match the gray background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Add padding

        // --- Person Data ---
        String julianDesc = "Julian is a passionate software developer with expertise in Java and UI design. He loves creating user-friendly applications and has a keen eye for detail. His dedication to clean code and excellent user experience makes him a valuable team member.";
        String rodmarkDesc = "Rodmark is a skilled developer specializing in backend systems and database management. He has extensive experience in building robust and scalable applications. His problem-solving skills and technical expertise drive innovative solutions.";
        String marlonDesc = "Maria is a creative designer and front-end developer who brings visual concepts to life. She specializes in user interface design and has a talent for creating engaging user experiences that are both beautiful and functional.";
        String paulDesc = "Carlos is a full-stack developer with strong leadership skills. He has experience in project management and team coordination. His ability to bridge technical and business requirements makes him an excellent team lead.";
        String dayritDesc = "Ana is a quality assurance specialist who ensures our applications meet the highest standards. Her attention to detail and systematic testing approach helps deliver bug-free software that users can rely on.";

        // --- Image File Paths for each person ---
        String julianImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String rodmarkImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String marlonImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String paulImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String dayritImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        
        // --- Create and Add Profile Cards ---
        PersonPanel julianPanel = new PersonPanel("JULIAN AGUSTINO", julianDesc, julianImagePath, false);
        PersonPanel rodmarkPanel = new PersonPanel("RODMARK BAUTISTA", rodmarkDesc, rodmarkImagePath, false);
        PersonPanel marlonPanel = new PersonPanel("MARLON LOZANO", marlonDesc, marlonImagePath, false);
        PersonPanel paulPanel = new PersonPanel("PAUL PASUMALA", paulDesc, paulImagePath, false);
        PersonPanel dayritPanel = new PersonPanel("ACZEL DAYRIT", dayritDesc, dayritImagePath, false);

        mainPanel.add(julianPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(rodmarkPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(marlonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(paulPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(dayritPanel);

        // --- Create Scroll Pane ---
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        scrollPane.setBorder(null); // Remove default border

        // Create a wrapper panel to hold the scroll pane
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(245, 245, 245)); // Match Dashboard main background
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        return wrapperPanel;
    }

    private static void createAndShowGui() {
        // --- Main Window Setup ---
        JFrame frame = new JFrame("About Us");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(128, 128, 128)); // A gray similar to bg-gray-500
        frame.setSize(1000, 600); // Set a fixed size for better scroll experience

        // --- Main Container Panel ---
        // This panel will hold all other components and use a BoxLayout to stack them vertically.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(frame.getContentPane().getBackground());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40)); // Add padding

        // --- Person Data ---
        String julianDesc = "Julian is a passionate software developer with expertise in Java and UI design. He loves creating user-friendly applications and has a keen eye for detail. His dedication to clean code and excellent user experience makes him a valuable team member.";
        String rodmarkDesc = "Rodmark is a skilled developer specializing in backend systems and database management. He has extensive experience in building robust and scalable applications. His problem-solving skills and technical expertise drive innovative solutions.";
        String mariaDesc = "Maria is a creative designer and front-end developer who brings visual concepts to life. She specializes in user interface design and has a talent for creating engaging user experiences that are both beautiful and functional.";
        String carlosDesc = "Carlos is a full-stack developer with strong leadership skills. He has experience in project management and team coordination. His ability to bridge technical and business requirements makes him an excellent team lead.";
        String anaDesc = "Ana is a quality assurance specialist who ensures our applications meet the highest standards. Her attention to detail and systematic testing approach helps deliver bug-free software that users can rely on.";

        // --- Image File Paths for each person ---
        String julianImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String rodmarkImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String mariaImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String carlosImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        String anaImagePath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PAUL PASUMALA (POGI).jpeg";
        
        // --- Create and Add Profile Cards ---
        PersonPanel julianPanel = new PersonPanel("JULIAN AGUSTINO", julianDesc, julianImagePath, false);
        PersonPanel rodmarkPanel = new PersonPanel("RODMARK BAUTISTA", rodmarkDesc, rodmarkImagePath, true);
        PersonPanel mariaPanel = new PersonPanel("MARLON LOZANO", mariaDesc, mariaImagePath, false);
        PersonPanel carlosPanel = new PersonPanel("PAUL PASUMALA", carlosDesc, carlosImagePath, true);
        PersonPanel anaPanel = new PersonPanel("ACZEL DAYRIT", anaDesc, anaImagePath, false);

        mainPanel.add(julianPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(rodmarkPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(mariaPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(carlosPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40))); // Space between the cards
        mainPanel.add(anaPanel);

        // --- Create Scroll Pane ---
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        scrollPane.setBorder(null); // Remove default border

        // --- Finalize and Display Window ---
        frame.getContentPane().add(scrollPane);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }
}

/**
 * A custom JPanel to display a person's profile, including an image and text.
 * The layout can be reversed.
 */
class PersonPanel extends JPanel {

    private static final int IMAGE_SIZE = 180;
    private static final Font NAME_FONT = new Font("SansSerif", Font.BOLD, 22);
    private static final Font DESC_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(128, 128, 128);

    public PersonPanel(String name, String description, String imagePath, boolean reverse) {
        setLayout(new GridBagLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        )); // Add border and padding for better visual separation
        
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Image Component ---
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
        imageLabel.setMinimumSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
        
        try {
            // Create a File object from the provided path string
            File imageFile = new File(imagePath);
            // Read the image from the file
            Image image = ImageIO.read(imageFile);
            if (image != null) {
                Image scaledImage = image.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                 imageLabel.setText("Image not found at path");
            }
        } catch (IOException e) {
            e.printStackTrace();
            imageLabel.setText("Error loading image");
            imageLabel.setForeground(Color.RED);
        }

        // --- Text Components ---
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(BACKGROUND_COLOR);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(NAME_FONT);
        nameLabel.setForeground(TEXT_COLOR);
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT); // Align left to match description text
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Ensure left alignment in BoxLayout

        // Use JTextArea for better text handling instead of HTML JLabel
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setFont(DESC_FONT);
        descriptionArea.setForeground(TEXT_COLOR);
        descriptionArea.setBackground(BACKGROUND_COLOR);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setColumns(30); // Set preferred width in characters
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT); // Ensure left alignment in BoxLayout
        // NO height constraints - let it expand as needed


        textPanel.add(nameLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between name and description
        textPanel.add(descriptionArea);
        
        // --- Layout Logic ---
        // Arrange components based on the 'reverse' flag
        if (reverse) {
            // Text on the left, Image on the right
            nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            nameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            descriptionArea.setAlignmentX(Component.RIGHT_ALIGNMENT);
            textPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

            gbc.gridx = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0; // Allow vertical expansion
            gbc.fill = GridBagConstraints.BOTH; // Fill both directions
            gbc.insets = new Insets(0, 0, 0, 40);
            gbc.anchor = GridBagConstraints.NORTHEAST;
            add(textPanel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0;
            gbc.weighty = 0; // Don't expand image vertically
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.anchor = GridBagConstraints.NORTH;
            add(imageLabel, gbc);
        } else {
            // Image on the left, Text on the right
            nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
            textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.weighty = 0; // Don't expand image vertically
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(0, 0, 0, 40);
            gbc.anchor = GridBagConstraints.NORTH;
            add(imageLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0; // Allow vertical expansion
            gbc.fill = GridBagConstraints.BOTH; // Fill both directions
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.insets = new Insets(0, 0, 0, 0);
            add(textPanel, gbc);
        }
    }
}

