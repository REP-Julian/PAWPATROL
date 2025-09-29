import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * A simple dashboard with a sidebar navigation and a main content area.
 * Uses Java Swing for the graphical user interface.
 */
public class Dashboard extends JFrame {

    private JPanel sidebar;
    private JPanel mainContent;
    private JPanel headerPanel;
    private CardLayout cardLayout;

    // Define colors for a modern look
    private final Color SIDEBAR_BACKGROUND = new Color(34, 40, 49); // Dark Gray
    private final Color BUTTON_BACKGROUND = new Color(57, 62, 70);  // Lighter Gray
    private final Color TEXT_COLOR = new Color(238, 238, 238);      // Light Gray/White
    private final Color MAIN_BACKGROUND = new Color(245, 245, 245); // Off-white

    public Dashboard() {
        // --- 1. Frame Setup ---
        setTitle("Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the window

        // --- 2. Create Components ---
        createSidebar();
        createHeaderPanel();
        createMainContentPanel();

        // --- 3. Add Components to Frame ---
        add(sidebar, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }

    /**
     * Creates the sidebar panel with the logo and navigation buttons.
     */
    private void createSidebar() {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BACKGROUND);
        sidebar.setPreferredSize(new Dimension(250, getHeight()));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // Top, Left, Bottom, Right padding

        // --- Logo ---
        // The file path for your logo.
        String logoPath = "c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PawTrack.png";
        JLabel logoLabel;

        File logoFile = new File(logoPath);
        if (logoFile.exists()) {
            // Create a rounded logo with a nice corner radius
            logoLabel = new RoundedImageComponent(logoPath, 20);
            // Set both preferred and minimum size to ensure the logo is visible
            logoLabel.setPreferredSize(new Dimension(100, 120));
            logoLabel.setMinimumSize(new Dimension(100, 120));
            logoLabel.setMaximumSize(new Dimension(100, 120));
            System.out.println("Logo loaded successfully from: " + logoPath);
        } else {
   
            logoLabel = new JLabel("PawTrack Logo");
            logoLabel.setForeground(TEXT_COLOR);
            logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
            logoLabel.setPreferredSize(new Dimension(120, 120));
            logoLabel.setMinimumSize(new Dimension(120, 120));
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoLabel.setVerticalAlignment(SwingConstants.CENTER);
            System.err.println("Logo file not found at: " + logoPath); 
        }
        
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(new EmptyBorder(0, 0, 40, 0));
        sidebar.add(logoLabel);
        
    
        sidebar.add(createSidebarButton("🐾 Paw", "PAW_PANEL"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(createSidebarButton("📜 Vet", "LICENSE_PANEL"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(createSidebarButton("🛠️ Support", "SUPPORT_PANEL"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(createSidebarButton("📞 Contact", "CONTACT_PANEL"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        sidebar.add(createSidebarButton("About Us !", "ABOUT_PANEL"));
    }

    /**
     * Creates the header panel with search bar and logout button.
     */
    private void createHeaderPanel() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250)); // Light gray background
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));

        // --- Search Bar Panel ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(248, 249, 250)); // Match header background
        
        // Create a search field with integrated search icon
        JPanel searchFieldPanel = new JPanel(new BorderLayout());
        searchFieldPanel.setBackground(Color.WHITE);
        searchFieldPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        searchFieldPanel.setPreferredSize(new Dimension(320, 30));
        
        // Search icon label
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Arial", Font.PLAIN, 16));
        searchIcon.setForeground(new Color(100, 100, 100));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 5));
        
        // Search text field
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 10));
        searchField.setBackground(Color.WHITE);
        
        // Add placeholder text functionality
        String placeholder = "Search paws, licenses, or contacts...";
        searchField.setText(placeholder);
        searchField.setForeground(Color.GRAY);
        
        searchField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals(placeholder)) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText(placeholder);
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        
        // Add action listener for search
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText();
                if (!searchText.equals(placeholder) && !searchText.trim().isEmpty()) {
                    performSearch(searchText);
                }
            }
        });
        
        // Assemble the search field panel
        searchFieldPanel.add(searchIcon, BorderLayout.WEST);
        searchFieldPanel.add(searchField, BorderLayout.CENTER);
        
        searchPanel.add(searchFieldPanel);

        // --- Logout Button ---
        JButton logoutButton = new JButton("🚪 Logout");
        logoutButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        logoutButton.setBackground(new Color(220, 53, 69)); // Red color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(new EmptyBorder(8, 15, 8, 15));
        logoutButton.setPreferredSize(new Dimension(100, 35));
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogout();
            }
        });

        // Add components to header
        headerPanel.add(searchPanel, BorderLayout.WEST);
        headerPanel.add(logoutButton, BorderLayout.EAST);
    }

    /**
     * Handles the search functionality.
     */
    private void performSearch(String searchText) {
        // Here you can implement your search logic
        JOptionPane.showMessageDialog(this, 
            "Searching for: " + searchText + "\n\nThis is where you would implement the actual search functionality.",
            "Search", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the logout functionality.
     */
    private void handleLogout() {
        int option = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (option == JOptionPane.YES_OPTION) {
            // Close the current dashboard
            this.dispose();
            
            // Return to login screen
            SwingUtilities.invokeLater(() -> new PawTrackLogin().setVisible(true));
        }
    }

    /**
     * Creates the main content area that switches between different panels.
     */
    private void createMainContentPanel() {
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        mainContent.setBackground(MAIN_BACKGROUND);

        // Create a separate panel for each navigation item
        mainContent.add(new PawManagement(), "PAW_PANEL"); // Use actual PawManagement component
        mainContent.add(new VetAppointment(), "LICENSE_PANEL"); // Use actual VetAppointment component
        mainContent.add(createContentPanel("Support Center"), "SUPPORT_PANEL");
        mainContent.add(createContentPanel("Contact Us"), "CONTACT_PANEL");
        mainContent.add(createContentPanel("About Our Company"), "ABOUT_PANEL");
    }

    /**
     * A helper method to create a styled sidebar button.
     *
     * @param text      The text and icon for the button.
     * @param panelName The name of the panel to switch to when clicked.
     * @return A styled JButton.
     */
    private JButton createSidebarButton(String text, final String panelName) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 18));
        button.setBackground(BUTTON_BACKGROUND);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(15, 25, 15, 25));
        button.setHorizontalAlignment(SwingConstants.CENTER); // Center the button text
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button in the sidebar

        // Set maximum size to make all buttons the same width
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));

        // Action Listener to switch panels in the main content area
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainContent, panelName);
            }
        });

        return button;
    }

    /**
     * A helper method to create a basic content panel.
     *
     * @param title The title to display in the panel.
     * @return A JPanel with the specified title.
     */
    private JPanel createContentPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MAIN_BACKGROUND);
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    /**
     * The main method to run the application.
     */
    public static void main(String[] args) {
        // Run the GUI code on the Event Dispatch Thread (EDT) for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }
}

/**
 * A custom JLabel that displays an image with rounded corners.
 */
class RoundedImageComponent extends JLabel {
    private Image originalImage;
    private final int cornerRadius;

    public RoundedImageComponent(String imagePath, int cornerRadius) {
        this.cornerRadius = cornerRadius;
        try {
            this.originalImage = ImageIO.read(new File(imagePath));
            if (originalImage != null) {
                System.out.println("Image loaded successfully: " + imagePath + 
                    " (Size: " + originalImage.getWidth(null) + "x" + originalImage.getHeight(null) + ")");
            }
        } catch (IOException e) {
            setText("Image not found");
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            System.err.println("Could not load image from path: " + imagePath);
            this.originalImage = null;
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (originalImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Maintain aspect ratio
            int componentWidth = getWidth();
            int componentHeight = getHeight();
            
            // Ensure we have valid dimensions
            if (componentWidth <= 0 || componentHeight <= 0) {
                g2d.dispose();
                return;
            }
            
            int imageWidth = originalImage.getWidth(this);
            int imageHeight = originalImage.getHeight(this);

            double imageAspect = (double) imageWidth / (double) imageHeight;
            double componentAspect = (double) componentWidth / (double) componentHeight;

            int newWidth, newHeight, x, y;

            if (imageAspect > componentAspect) {
                // Image is wider than the component, scale by width
                newWidth = componentWidth;
                newHeight = (int) (newWidth / imageAspect);
                x = 0;
                y = (componentHeight - newHeight) / 2;
            } else {
                // Image is taller or has same aspect ratio, scale by height
                newHeight = componentHeight;
                newWidth = (int) (newHeight * imageAspect);
                y = 0;
                x = (componentWidth - newWidth) / 2;
            }

            // Clip the area to a rounded rectangle before drawing
            RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2d.setClip(roundRect);

            // Draw the image scaled and centered
            g2d.drawImage(originalImage, x, y, newWidth, newHeight, this);
            g2d.dispose();
        } else {
            super.paintComponent(g); // If image is null, just behave like a normal JLabel
        }
    }
}

