import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

/**
 * A dedicated panel for managing and displaying pets in a grid layout.
 * Each pet is represented as a "card" with its image, name, status, and action buttons.
 */
public class PawManagement extends JPanel {

    // Define colors for consistency
    private final Color MAIN_BACKGROUND = new Color(245, 245, 245);

    public PawManagement() {
        setLayout(new BorderLayout());
        setBackground(MAIN_BACKGROUND);

        // --- Create the main grid panel for pet cards ---
        JPanel imageGridPanel = new JPanel(new GridLayout(0, 3, 20, 20)); // 0 rows, 3 columns, 20px gaps
        imageGridPanel.setBackground(MAIN_BACKGROUND);
        imageGridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Sample Data ---
        // In a real application, this data would be loaded from a database or API.
        String[][] petData = {
            {"Paul", "Available", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Lucy", "Adopted", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Max", "Available", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Daisy", "Available", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Charlie", "In Foster", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Sadie", "Available", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Rocky", "Adopted", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Molly", "Available", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"},
            {"Zoe", "Available", "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png"}
        };

        // --- Create and add pet cards to the grid ---
        for (String[] data : petData) {
            imageGridPanel.add(createPetCard(data[2], data[0], data[1]));
        }

        // --- Make the grid scrollable ---
        JScrollPane scrollPane = new JScrollPane(imageGridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates a single "card" for a pet, including its image, details, and action buttons.
     * @param imagePath The file path to the pet's image.
     * @param petName   The name of the pet.
     * @param status    The adoption status (e.g., "Available", "Adopted").
     * @return A JPanel representing the pet card.
     */
    private JPanel createPetCard(String imagePath, String petName, String status) {
        JPanel cardPanel = new JPanel(new BorderLayout(0, 10));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        // --- Image Area ---
        JLabel imageLabel;
        if (imagePath != null && new File(imagePath).exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImage));
        } else {
            // Placeholder icon if the image is not found
            imageLabel = new JLabel("🐾", SwingConstants.CENTER);
            imageLabel.setFont(new Font("SansSerif", Font.BOLD, 100));
            imageLabel.setForeground(new Color(230, 230, 230));
        }
        imageLabel.setPreferredSize(new Dimension(250, 200));
        
        // --- Details Panel (Name and Status) ---
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        JLabel nameLabel = new JLabel(petName);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Set status color based on the status text
        switch (status.toLowerCase()) {
            case "available":
                statusLabel.setForeground(new Color(40, 167, 69)); // Green
                break;
            case "adopted":
                statusLabel.setForeground(new Color(220, 53, 69)); // Red
                break;
            case "in foster":
                statusLabel.setForeground(new Color(255, 193, 7)); // Orange/Yellow
                break;
            default:
                statusLabel.setForeground(Color.DARK_GRAY);
                break;
        }
        
        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        detailsPanel.add(statusLabel);
        
        // --- Buttons Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 10, 5));

        JButton viewButton = new JButton("View");
        viewButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        viewButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Displaying details for " + petName));

        JButton adoptButton = new JButton("Adopt");
        adoptButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        adoptButton.setBackground(new Color(23, 162, 184)); // Teal
        adoptButton.setForeground(Color.WHITE);
        adoptButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Starting adoption process for " + petName));
        
        buttonPanel.add(viewButton);
        buttonPanel.add(adoptButton);

        // --- Assemble the Card ---
        cardPanel.add(imageLabel, BorderLayout.NORTH);
        cardPanel.add(detailsPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        return cardPanel;
    }
}
