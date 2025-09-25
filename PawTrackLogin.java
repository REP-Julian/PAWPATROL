import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PawTrackLogin extends JFrame {

    // Define colors for easy customization
    private static final Color COLOR_BACKGROUND = new Color(30, 41, 59); // bg-gray-800
    private static final Color COLOR_PANEL = Color.WHITE;
    private static final Color COLOR_BUTTON = new Color(30, 41, 59); // bg-gray-800
    private static final Color COLOR_BUTTON_HOVER = new Color(55, 65, 81); // bg-gray-700
    private static final Color COLOR_TEXT = new Color(55, 65, 81); // text-gray-700
    private static final Color COLOR_PLACEHOLDER = new Color(156, 163, 175); // text-gray-400

    public PawTrackLogin() {
        setTitle("PawTrack Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(COLOR_BACKGROUND);
        setLayout(new GridLayout(1, 2, 0, 0)); // 1 row, 2 columns

        // Left Panel (Login)
        add(createLoginPanel());

        // Right Panel (Information)
        add(createInfoPanel());
    }

    private JPanel createLoginPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(COLOR_BACKGROUND);
        leftPanel.setLayout(new GridBagLayout()); // To center the form panel

        // The white, rounded form container
        CustomRoundedPanel formPanel = new CustomRoundedPanel(40, COLOR_PANEL);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(380, 500));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 35, 10, 35);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // 1. Logo
        JLabel logoLabel = new RoundedImageComponent("c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/PawTrack.png", 30); // Rounded square
        logoLabel.setPreferredSize(new Dimension(150, 150));
        logoLabel.setMinimumSize(new Dimension(96, 96));
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 35, 20, 35); // More space below logo
        formPanel.add(logoLabel, gbc);

        // Reset insets
        gbc.insets = new Insets(5, 35, 5, 35);

        // 2. Username Label
        JLabel userLabel = new JLabel("USERNAME:");
        userLabel.setFont(new Font("Inter", Font.BOLD, 12));
        userLabel.setForeground(COLOR_TEXT);
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(userLabel, gbc);

        // 3. Username Field
        RoundedTextFieldCustom usernameField = new RoundedTextFieldCustom(20);
        usernameField.setText("Enter your username");
        addPlaceholderStyle(usernameField);
        gbc.gridy = 2;
        formPanel.add(usernameField, gbc);
        
        // 4. Password Label
        JLabel passLabel = new JLabel("PASSWORD:");
        passLabel.setFont(new Font("Inter", Font.BOLD, 12));
        passLabel.setForeground(COLOR_TEXT);
        gbc.gridy = 3;
        formPanel.add(passLabel, gbc);
        
        RoundedPasswordFieldCustom passwordField = new RoundedPasswordFieldCustom(20);
        passwordField.setText("Enter your password");
        addPlaceholderStyle(passwordField);
        gbc.gridy = 4;
        formPanel.add(passwordField, gbc);

        JButton loginButton = createStyledButton("LOGIN");
        loginButton.addActionListener(_ -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (!username.isEmpty() && !password.isEmpty() && 
                !username.equals("Enter your username") && !password.equals("Enter your password")) {
                this.dispose();
                SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
            } else {
                JOptionPane.showMessageDialog(this, "Please enter valid username and password", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 35, 10, 35);
        formPanel.add(loginButton, gbc);

        JPanel linksPanel = new JPanel(new BorderLayout());
        linksPanel.setOpaque(false);
        JButton createAccountButton = createLinkButton("CREATE ACCOUNT");
        createAccountButton.addActionListener(_ -> {

            this.dispose();
            SwingUtilities.invokeLater(() -> new CreateAccount().setVisible(true));
        });
        JButton forgotPasswordButton = createLinkButton("FORGOT PASSWORD");
        forgotPasswordButton.addActionListener(_ -> {
            JOptionPane.showMessageDialog(this, "Forgot password functionality not implemented yet.", 
                "Feature Not Available", JOptionPane.INFORMATION_MESSAGE);
        });
        linksPanel.add(createAccountButton, BorderLayout.WEST);
        linksPanel.add(forgotPasswordButton, BorderLayout.EAST);
        
        gbc.gridy = 6;
        gbc.insets = new Insets(5, 35, 10, 35);
        formPanel.add(linksPanel, gbc);

        JLabel watermarkLabel = new JLabel("WATERMARK");
        watermarkLabel.setFont(new Font("Inter", Font.PLAIN, 10));
        watermarkLabel.setForeground(COLOR_PLACEHOLDER);
        watermarkLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7;
        formPanel.add(watermarkLabel, gbc);

        leftPanel.add(formPanel); 
        return leftPanel;
    }

    private JPanel createInfoPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(COLOR_BACKGROUND);
        
        
        CustomRoundedPanel infoPanel = new CustomRoundedPanel(40, COLOR_PANEL);
        infoPanel.setLayout(new BorderLayout(10, 10));
        infoPanel.setPreferredSize(new Dimension(380, 500));
        infoPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        
        JLabel imageLabel = new RoundedImageComponent("c:/Users/agust/Documents/Visual Studio Code/Paw Track Management/Paw-Track/image/DOG.png", 20);
        imageLabel.setPreferredSize(new Dimension(330, 220));
        infoPanel.add(imageLabel, BorderLayout.NORTH);

        
        JPanel descriptionPanel = new JPanel(new BorderLayout(0, 5));
        descriptionPanel.setOpaque(false);

        JLabel descTitle = new JLabel("Description:");
        descTitle.setFont(new Font("Inter", Font.BOLD, 14));
        descTitle.setForeground(COLOR_TEXT);
        descriptionPanel.add(descTitle, BorderLayout.NORTH);

        JTextArea descriptionArea = new JTextArea(
            "The PawPatrol System is a digital platform designed to help abandoned and " +
            "surrendered pets find new, loving homes. It connects individuals who can no " +
            "longer care for their pets with responsible adopters who are ready to welcome " +
            "them as part of their family. Through the system, users can view available pets, " +
            "learn about their background and needs, and apply for adoption."
        );
        descriptionArea.setFont(new Font("Inter", Font.PLAIN, 12));
        descriptionArea.setForeground(COLOR_TEXT);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setOpaque(false);
        descriptionArea.setEditable(false);
        descriptionPanel.add(descriptionArea, BorderLayout.CENTER);
        infoPanel.add(descriptionPanel, BorderLayout.CENTER);
        
        rightPanel.add(infoPanel);
        return rightPanel;
    }

    private void addPlaceholderStyle(JTextField textField) {
        Font placeholderFont = new Font("Inter", Font.ITALIC, textField.getFont().getSize());
        Font defaultFont = new Font("Inter", Font.PLAIN, textField.getFont().getSize());
        String placeholder = textField.getText();

        textField.setFont(placeholderFont);
        textField.setForeground(COLOR_PLACEHOLDER);
        
        if (textField instanceof JPasswordField) {
            ((JPasswordField) textField).setEchoChar((char) 0);
        }

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getForeground() == COLOR_PLACEHOLDER) {
                    textField.setText("");
                    textField.setFont(defaultFont);
                    textField.setForeground(COLOR_TEXT);
                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar('•');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setFont(placeholderFont);
                    textField.setForeground(COLOR_PLACEHOLDER);
                    textField.setText(placeholder);
                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(COLOR_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 45));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(COLOR_BUTTON_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(COLOR_BUTTON);
            }
        });
        
        return button;
    }

    private JButton createLinkButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.PLAIN, 12));
        button.setForeground(COLOR_TEXT.darker());
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PawTrackLogin().setVisible(true));
    }
}

// Custom component classes for rounded styling

class CustomRoundedPanel extends JPanel {
    private final int cornerRadius;
    private final Color backgroundColor;

    public CustomRoundedPanel(int radius, Color bgColor) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
        g2d.dispose();
    }
}

class RoundedTextFieldCustom extends JTextField {
    public RoundedTextFieldCustom(int size) {
        super(size);
        setOpaque(false);
        setBackground(new Color(229, 231, 235)); // bg-gray-200
        setForeground(new Color(30, 41, 59));   // text-gray-800
        setBorder(new EmptyBorder(12, 18, 12, 18));
        setFont(new Font("Inter", Font.PLAIN, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight()); // Creates a pill shape
        super.paintComponent(g2d);
        g2d.dispose();
    }
}

class RoundedPasswordFieldCustom extends JPasswordField {
    public RoundedPasswordFieldCustom(int size) {
        super(size);
        setOpaque(false);
        setBackground(new Color(229, 231, 235));
        setForeground(new Color(30, 41, 59));
        setBorder(new EmptyBorder(12, 18, 12, 18));
        setFont(new Font("Inter", Font.PLAIN, 14));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
        super.paintComponent(g2d);
        g2d.dispose();
    }
}

class RoundedImageComponent extends JLabel {
    private Image originalImage;
    private final int cornerRadius;

    public RoundedImageComponent(String imagePath, int cornerRadius) {
        this.cornerRadius = cornerRadius;
        try {
            this.originalImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            setText("Image not found");
            setHorizontalAlignment(SwingConstants.CENTER);
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

