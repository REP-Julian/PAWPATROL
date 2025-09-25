import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class CreateAccount extends JFrame {

    // Define colors for easy customization
    private static final Color COLOR_BACKGROUND = new Color(30, 41, 59); // bg-slate-800
    private static final Color COLOR_PANEL = new Color(255, 255, 255, 15); // rgba(255, 255, 255, 0.05) with more visibility
    private static final Color COLOR_TEXT = new Color(209, 213, 219); // text-gray-300
    private static final Color COLOR_BUTTON_SUBMIT = new Color(22, 163, 74); // bg-green-600
    private static final Color COLOR_BUTTON_SUBMIT_HOVER = new Color(21, 128, 61); // bg-green-700
    private static final Color COLOR_BUTTON_BACK = new Color(220, 38, 38); // bg-red-600
    private static final Color COLOR_BUTTON_BACK_HOVER = new Color(185, 28, 28); // bg-red-700
    private static final Color COLOR_ERROR = new Color(248, 113, 113); // text-red-400
    private static final Color COLOR_SUCCESS = new Color(74, 222, 128); // text-green-400

    // Form components
    private RoundedTextField fullNameField, usernameField;
    private RoundedPasswordField passwordField, repeatPasswordField;
    private RoundedTextField contactNumberField, emailField;

    // Error labels
    private JLabel fullNameError, usernameError, passwordError, repeatPasswordError, contactNumberError, emailError, successMessage;

    public CreateAccount() {
        setTitle("Create Account - PawTrack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        getContentPane().setBackground(COLOR_BACKGROUND);
        setLayout(new GridLayout(1, 2, 32, 0)); 
        getRootPane().setBorder(new EmptyBorder(16, 16, 16, 16));


        add(createFormPanel());

        // Right Panel (Info)
        add(createInfoPanel());
    }

    private JComponent createFormPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);

        RoundedPanel formPanel = new RoundedPanel(32, COLOR_PANEL);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(450, 620));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 40, 0, 40);

        // Logo
        // --- IMPORTANT: REPLACE WITH YOUR ACTUAL LOGO PATH ---
        JLabel logo = new RoundedImageLabel("https://placehold.co/96x96/FFFFFF/1E293B?text=PT", 96);
        logo.setPreferredSize(new Dimension(96, 96));
        gbc.gridy = 0;
        gbc.insets = new Insets(15, 40, 10, 40);
        formPanel.add(logo, gbc);

        // --- Form Fields ---
        gbc.insets = new Insets(2, 40, 0, 40);

        // Full Name
        gbc.gridy++;
        formPanel.add(createLabel("FULL NAME:"), gbc);
        gbc.gridy++;
        fullNameField = new RoundedTextField(20);
        formPanel.add(fullNameField, gbc);
        gbc.gridy++;
        fullNameError = createErrorLabel();
        formPanel.add(fullNameError, gbc);

        // Username
        gbc.gridy++;
        formPanel.add(createLabel("USERNAME:"), gbc);
        gbc.gridy++;
        usernameField = new RoundedTextField(20);
        formPanel.add(usernameField, gbc);
        gbc.gridy++;
        usernameError = createErrorLabel();
        formPanel.add(usernameError, gbc);

        // Password
        gbc.gridy++;
        formPanel.add(createLabel("PASSWORD:"), gbc);
        gbc.gridy++;
        passwordField = new RoundedPasswordField(20);
        formPanel.add(passwordField, gbc);
        gbc.gridy++;
        passwordError = createErrorLabel();
        formPanel.add(passwordError, gbc);

        // Repeat Password
        gbc.gridy++;
        formPanel.add(createLabel("REPEAT PASSWORD:"), gbc);
        gbc.gridy++;
        repeatPasswordField = new RoundedPasswordField(20);
        formPanel.add(repeatPasswordField, gbc);
        gbc.gridy++;
        repeatPasswordError = createErrorLabel();
        formPanel.add(repeatPasswordError, gbc);
        
        // Contact Number
        gbc.gridy++;
        formPanel.add(createLabel("CONTACT NUMBER:"), gbc);
        gbc.gridy++;
        contactNumberField = new RoundedTextField(20);
        formPanel.add(contactNumberField, gbc);
        gbc.gridy++;
        contactNumberError = createErrorLabel();
        formPanel.add(contactNumberError, gbc);

        // Email
        gbc.gridy++;
        formPanel.add(createLabel("EMAIL:"), gbc);
        gbc.gridy++;
        emailField = new RoundedTextField(20);
        formPanel.add(emailField, gbc);
        gbc.gridy++;
        emailError = createErrorLabel();
        formPanel.add(emailError, gbc);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 16, 0));
        buttonPanel.setOpaque(false);
        JButton submitButton = createStyledButton("SUBMIT", COLOR_BUTTON_SUBMIT, COLOR_BUTTON_SUBMIT_HOVER);
        submitButton.addActionListener(_ -> handleSubmit());
        JButton backButton = createStyledButton("BACK", COLOR_BUTTON_BACK, COLOR_BUTTON_BACK_HOVER);
        backButton.addActionListener(_ -> {
            // Close create account window and return to login
            this.dispose();
            SwingUtilities.invokeLater(() -> new PawTrackLogin().setVisible(true));
        });
        
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton);
        gbc.gridy++;
        gbc.insets = new Insets(15, 40, 0, 40);
        formPanel.add(buttonPanel, gbc);
        
        // Success Message
        successMessage = new JLabel("", SwingConstants.CENTER);
        successMessage.setFont(new Font("Inter", Font.BOLD, 14));
        successMessage.setForeground(COLOR_SUCCESS);
        gbc.gridy++;
        gbc.insets = new Insets(10, 40, 10, 40);
        formPanel.add(successMessage, gbc);

        wrapper.add(formPanel);
        return wrapper;
    }

    private JComponent createInfoPanel() {
        JPanel infoWrapper = new JPanel();
        infoWrapper.setOpaque(false);
        infoWrapper.setLayout(new BoxLayout(infoWrapper, BoxLayout.Y_AXIS));

        // Top Panel
        RoundedPanel topPanel = new RoundedPanel(32, COLOR_PANEL);
        topPanel.setLayout(new BorderLayout(0, 16));
        topPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        // --- IMPORTANT: REPLACE WITH YOUR ACTUAL IMAGE PATH ---
        JLabel imageLabel = new RoundedImageLabel("https://placehold.co/600x400/a3e635/1E293B?text=Find+a+Friend", 24);
        imageLabel.setPreferredSize(new Dimension(100, 150)); // Height is arbitrary here, layout manager will handle it
        topPanel.add(imageLabel, BorderLayout.NORTH);

        JTextArea topText = new JTextArea(
            "The PawPatrol System is a digital platform created to support the adoption of pets that have been abandoned or surrendered by their owners. Its main goal is to provide a safe and reliable way for these animals to find a new family that will love and care for them."
        );
        topText.setEditable(false);
        topText.setOpaque(false);
        topText.setForeground(COLOR_TEXT);
        topText.setFont(new Font("Inter", Font.PLAIN, 14));
        topText.setLineWrap(true);
        topText.setWrapStyleWord(true);
        topPanel.add(topText, BorderLayout.CENTER);

        // Bottom Panel
        RoundedPanel bottomPanel = new RoundedPanel(32, COLOR_PANEL);
        bottomPanel.setLayout(new BorderLayout(0, 16));
        bottomPanel.setBorder(new EmptyBorder(24, 24, 24, 24));
        
        JLabel bottomTitle = new JLabel("How the System Works:");
        bottomTitle.setFont(new Font("Inter", Font.BOLD, 20));
        bottomTitle.setForeground(Color.WHITE);
        bottomPanel.add(bottomTitle, BorderLayout.NORTH);

        JTextArea bottomText = new JTextArea(
            "1. Pet Registration - Shelters, rescuers, or former owners can register pets in the system by providing details such as age, breed, health status, and personality.\n\n" +
            "2. Pet Listings - All registered animals are displayed with photos and descriptions so adopters can easily browse.\n\n" +
            "3. Adoption Application - Interested adopters can submit an adoption request directly through the system.\n\n" +
            "4. Screening Process - The system ensures that pets are matched with responsible owners by checking applications.\n\n" +
            "5. Final Adoption - Once approved, the adopter can officially welcome the pet into their home."
        );
        bottomText.setEditable(false);
        bottomText.setOpaque(false);
        bottomText.setForeground(COLOR_TEXT);
        bottomText.setFont(new Font("Inter", Font.PLAIN, 14));
        bottomText.setLineWrap(true);
        bottomText.setWrapStyleWord(true);
        bottomPanel.add(bottomText, BorderLayout.CENTER);
        
        infoWrapper.add(topPanel);
        infoWrapper.add(Box.createRigidArea(new Dimension(0, 32))); // Spacer
        infoWrapper.add(bottomPanel);
        
        return infoWrapper;
    }

    private void handleSubmit() {
        successMessage.setText("");
        if (validateForm()) {
            successMessage.setText("Account created successfully!");
            // After successful account creation, redirect to dashboard
            Timer timer = new Timer(2000, _ -> {
                this.dispose();
                SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private boolean validateForm() {
        boolean isValid = true;
        clearErrors();

        // Full Name
        if (fullNameField.getText().trim().isEmpty()) {
            fullNameError.setText("Full name is required.");
            isValid = false;
        }

        // Username
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            usernameError.setText("Username is required.");
            isValid = false;
        } else if (username.length() < 3) {
            usernameError.setText("Username must be at least 3 characters.");
            isValid = false;
        } else if (!username.matches("^[a-zA-Z0-9_]+$")) {
            usernameError.setText("Username can only contain letters, numbers, and underscores.");
            isValid = false;
        }

        // Password
        String pass = new String(passwordField.getPassword());
        if (pass.trim().isEmpty()) {
            passwordError.setText("Password is required.");
            isValid = false;
        } else if (pass.length() < 8) {
            passwordError.setText("Password must be at least 8 characters.");
            isValid = false;
        }

        // Repeat Password
        String repeatPass = new String(repeatPasswordField.getPassword());
        if (repeatPass.trim().isEmpty()) {
            repeatPasswordError.setText("Please confirm your password.");
            isValid = false;
        } else if (!pass.equals(repeatPass)) {
            repeatPasswordError.setText("Passwords do not match.");
            isValid = false;
        }

        // Contact Number
        String contact = contactNumberField.getText().trim();
        if (contact.isEmpty()) {
            contactNumberError.setText("Contact number is required.");
            isValid = false;
        } else if (!contact.matches("\\d+")) {
            contactNumberError.setText("Please enter a valid number.");
            isValid = false;
        }
        
        // Email
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            emailError.setText("Email is required.");
            isValid = false;
        } else if (!Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
                .matcher(email).matches()) {
            emailError.setText("Please enter a valid email address.");
            isValid = false;
        }

        return isValid;
    }

    private void clearErrors() {
        fullNameError.setText(" ");
        usernameError.setText(" ");
        passwordError.setText(" ");
        repeatPasswordError.setText(" ");
        contactNumberError.setText(" ");
        emailError.setText(" ");
    }
    
    // --- Helper methods for creating components ---
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.BOLD, 12));
        label.setForeground(COLOR_TEXT);
        return label;
    }

    private JLabel createErrorLabel() {
        JLabel label = new JLabel(" "); // Start with a space to reserve height
        label.setFont(new Font("Inter", Font.PLAIN, 12));
        label.setForeground(COLOR_ERROR);
        return label;
    }

    private JButton createStyledButton(String text, Color bg, Color hoverBg) {
        return new RoundedButton(text, bg, hoverBg);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CreateAccount().setVisible(true));
    }
}

// --- Custom Rounded Components ---

class RoundedPanel extends JPanel {
    private final int cornerRadius;
    private final Color backgroundColor;

    public RoundedPanel(int radius, Color bgColor) {
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

class RoundedTextField extends JTextField {
    public RoundedTextField(int size) {
        super(size);
        setOpaque(false);
        setBackground(new Color(209, 213, 219));
        setForeground(new Color(31, 41, 55));
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

class RoundedPasswordField extends JPasswordField {
    public RoundedPasswordField(int size) {
        super(size);
        setOpaque(false);
        setBackground(new Color(209, 213, 219));
        setForeground(new Color(31, 41, 55));
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

class RoundedImageLabel extends JLabel {
    private Image image;
    private final int cornerRadius;

    public RoundedImageLabel(String path, int cornerRadius) {
        this.cornerRadius = cornerRadius;
        try {
            if (path.startsWith("http")) {
                this.image = ImageIO.read(new java.net.URL(path));
            } else {
                this.image = ImageIO.read(new File(path));
            }
        } catch (IOException e) {
            setText("Image not found");
            setHorizontalAlignment(SwingConstants.CENTER);
            System.err.println("Could not load image from: " + path);
            this.image = null;
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            RoundRectangle2D roundRect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2d.setClip(roundRect);
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            g2d.dispose();
        } else {
            super.paintComponent(g);
        }
    }
}

class RoundedButton extends JButton {
    private final Color backgroundColor;
    private final Color hoverBackgroundColor;

    public RoundedButton(String text, Color bg, Color hoverBg) {
        super(text);
        this.backgroundColor = bg;
        this.hoverBackgroundColor = hoverBg;

        // Style the button
        setFont(new Font("Inter", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(100, 45));
        
        // Remove default button painting
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Use ButtonModel to check for hover state
        if (getModel().isRollover()) {
            g2d.setColor(hoverBackgroundColor);
        } else {
            g2d.setColor(backgroundColor);
        }

        // Paint the rounded background
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
        
        // Let the parent class (JButton) paint the text on top
        super.paintComponent(g);
        
        g2d.dispose();
    }
}

