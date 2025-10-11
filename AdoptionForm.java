import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Main application class that sets up the JFrame window for the adoption form.
 */
public class AdoptionForm {
    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Adoption Form - Java Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create and add our custom drawing panel
            FormCanvas formCanvas = new FormCanvas();
            frame.add(formCanvas);

            frame.pack(); // Sizes the frame to fit the preferred size of its component
            frame.setLocationRelativeTo(null); // Center the window on the screen
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}

/**
 * The custom JPanel that acts as our canvas, handling all drawing and user interaction.
 */
class FormCanvas extends JPanel {

    // --- State Management ---
    private final Map<String, String> formState = new HashMap<>();
    private final Map<String, Rectangle> uiElements = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order
    private BufferedImage petImage = null;
    private String activeElementId = null;
    private boolean cursorVisible = true;
    private JFrame parentFrame = null; // Add reference to parent frame

    private final Font LABEL_FONT = new Font("Roboto", Font.BOLD, 14);
    private final Font TEXT_FONT = new Font("Roboto", Font.PLAIN, 16);
    private final Font TITLE_FONT = new Font("Roboto", Font.BOLD, 22);
    private final Font HEADER_FONT = new Font("Roboto", Font.BOLD, 28);
    private final Font BUTTON_FONT = new Font("Roboto", Font.BOLD, 16);


    public FormCanvas() {
        // Set the fixed size for the non-scrollable panel
        setPreferredSize(new Dimension(1200, 900));
        setBackground(Color.decode("#ffffff"));
        initializeFormState();
        initializeUIElements();
        setupInputHandling();
        setupCursorBlinking();
    }

    /**
     * Initializes the map that holds the text data for each form field.
     */
    private void initializeFormState() {
        String[] keys = {
            "ownerLastname", "contactNumber", "ownerAge", "ownerFirstname", "ownerAddress", "ownerSex",
            "ownerMiddlename", "petColor", "vetInfo", "petAge", "petBreed", "reasonForAdopt",
            "petSex", "petStatus", "dogName"
        };
        for (String key : keys) {
            formState.put(key, "");
        }
    }

    /**
     * Defines the positions and sizes of all UI elements using Rectangle objects.
     */
    private void initializeUIElements() {
        // Header Buttons
        uiElements.put("backButton", new Rectangle(930, 15, 110, 40));
        uiElements.put("submitButton", new Rectangle(1060, 15, 110, 40));

        // Image Section
        uiElements.put("dogName", new Rectangle(865, 340, 280, 40));
        uiElements.put("uploadButton", new Rectangle(865, 400, 280, 45));

        // Owner Info Section
        uiElements.put("ownerLastname", new Rectangle(50, 170, 230, 40));
        uiElements.put("contactNumber", new Rectangle(300, 170, 230, 40));
        uiElements.put("ownerAge", new Rectangle(550, 170, 230, 40));
        uiElements.put("ownerFirstname", new Rectangle(50, 250, 230, 40));
        uiElements.put("ownerMiddlename", new Rectangle(300, 250, 230, 40));
        uiElements.put("ownerSex", new Rectangle(550, 250, 230, 40));
        uiElements.put("ownerAddress", new Rectangle(50, 330, 730, 90));

        // Pet Info Section (reverted to original positions)
        uiElements.put("petColor", new Rectangle(50, 550, 230, 40));
        uiElements.put("vetInfo", new Rectangle(300, 550, 480, 40));
        uiElements.put("petAge", new Rectangle(50, 630, 230, 40));
        uiElements.put("petBreed", new Rectangle(300, 630, 230, 40));
        uiElements.put("petSex", new Rectangle(550, 630, 230, 40));
        uiElements.put("reasonForAdopt", new Rectangle(50, 710, 480, 90));
        uiElements.put("petStatus", new Rectangle(550, 710, 230, 40));
    }
    
    /**
     * Sets the parent frame reference for window management
     */
    public void setParentFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    /**
     * Sets up mouse and keyboard listeners.
     */
    private void setupInputHandling() {
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow(); // Ensure panel has focus on click
                Point p = e.getPoint();
                boolean clickedOnInput = false;

                for (Map.Entry<String, Rectangle> entry : uiElements.entrySet()) {
                    if (entry.getValue().contains(p)) {
                        if (isInputField(entry.getKey())) {
                            activeElementId = entry.getKey();
                            clickedOnInput = true;
                        } else if (entry.getKey().equals("uploadButton")) {
                            handleImageUpload();
                        } else if (entry.getKey().equals("submitButton")) {
                            System.out.println("Form Submitted: " + formState);
                            JOptionPane.showMessageDialog(FormCanvas.this, "Form data has been logged to the console.");
                        } else if (entry.getKey().equals("backButton")) {
                            handleBackButton();
                        }
                    }
                }

                if (!clickedOnInput) {
                    activeElementId = null;
                }
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (activeElementId != null) {
                    char typedChar = e.getKeyChar();
                    String currentText = formState.get(activeElementId);
                    if (typedChar == KeyEvent.VK_BACK_SPACE) {
                        if (currentText != null && !currentText.isEmpty()) {
                            formState.put(activeElementId, currentText.substring(0, currentText.length() - 1));
                        }
                    } else if (Character.isDefined(typedChar) && typedChar >= ' ') { // Printable chars
                        formState.put(activeElementId, currentText + typedChar);
                    }
                    repaint();
                }
            }
        });
    }
    
    /**
     * Opens a file chooser and loads the selected image.
     */
    private void handleImageUpload() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                petImage = ImageIO.read(selectedFile);
                repaint();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading image.", "Image Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isInputField(String id) {
        return !id.equals("uploadButton") && !id.equals("submitButton") && !id.equals("backButton");
    }

    /**
     * Starts a Swing Timer to make the cursor blink in active input fields.
     */
    private void setupCursorBlinking() {
        Timer timer = new Timer(500, _ -> {
            cursorVisible = !cursorVisible;
            if (activeElementId != null) {
                repaint();
            }
        });
        timer.start();
    }


    /**
     * The main drawing method, equivalent to the JavaScript draw() function.
     * This is called automatically by Swing when the component needs to be redrawn.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother text and shapes
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw Header
        g2d.setColor(Color.decode("#343a40"));
        g2d.fillRect(0, 0, getWidth(), 70);
        g2d.setColor(Color.WHITE);
        g2d.setFont(HEADER_FONT);
        g2d.drawString("NEW ADOPTION", 30, 45);
        drawButton(g2d, uiElements.get("backButton"), "BACK", Color.decode("#dc3545"));
        drawButton(g2d, uiElements.get("submitButton"), "SUBMIT", Color.decode("#28a745"));

        // Draw Sections
        drawSection(g2d, new Rectangle(30, 100, 780, 350), "OWNER INFORMATION");
        drawSection(g2d, new Rectangle(30, 480, 780, 390), "PET INFORMATION");
        drawSection(g2d, new Rectangle(840, 100, 330, 440), null); // Image section has no title

        // Draw Image Area
        drawButton(g2d, uiElements.get("uploadButton"), "UPLOAD IMAGE", Color.decode("#6c757d"));
        Rectangle placeholder = new Rectangle(865, 120, 280, 200);
        if (petImage != null) {
            g2d.drawImage(petImage, placeholder.x, placeholder.y, placeholder.width, placeholder.height, null);
        } else {
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 80));
            drawCenteredString(g2d, "🐕", placeholder, Color.decode("#adb5bd"));
        }
        g2d.setColor(Color.decode("#adb5bd"));
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{6}, 0);
        g2d.setStroke(dashed);
        g2d.draw(new RoundRectangle2D.Double(placeholder.x, placeholder.y, placeholder.width, placeholder.height, 15, 15));
        g2d.setStroke(new BasicStroke(1));


        // Draw all input fields
        drawInput(g2d, uiElements.get("dogName"), "NAME OF DOG");
        drawInput(g2d, uiElements.get("ownerLastname"), "LASTNAME");
        drawInput(g2d, uiElements.get("contactNumber"), "CONTACT NUMBER");
        drawInput(g2d, uiElements.get("ownerAge"), "AGE");
        drawInput(g2d, uiElements.get("ownerFirstname"), "FIRST NAME");
        drawInput(g2d, uiElements.get("ownerMiddlename"), "MIDDLE NAME");
        drawInput(g2d, uiElements.get("ownerSex"), "SEX");
        drawInput(g2d, uiElements.get("ownerAddress"), "ADDRESS");
        drawInput(g2d, uiElements.get("petColor"), "PET COLOR");
        drawInput(g2d, uiElements.get("vetInfo"), "VET INFO (INJECTION ON RABIES)");
        drawInput(g2d, uiElements.get("petAge"), "AGE");
        drawInput(g2d, uiElements.get("petBreed"), "PET BREED");
        drawInput(g2d, uiElements.get("petSex"), "SEX");
        drawInput(g2d, uiElements.get("reasonForAdopt"), "REASON FOR ADOPT");
        drawInput(g2d, uiElements.get("petStatus"), "STATUS OF PET");
    }

    private void drawSection(Graphics2D g2d, Rectangle bounds, String title) {
        g2d.setColor(Color.decode("#f8f9fa"));
        g2d.fill(new RoundRectangle2D.Double(bounds.x, bounds.y, bounds.width, bounds.height, 15, 15));
        g2d.setColor(Color.decode("#e9ecef"));
        g2d.draw(new RoundRectangle2D.Double(bounds.x, bounds.y, bounds.width, bounds.height, 15, 15));

        if (title != null) {
            g2d.setColor(Color.decode("#343a40"));
            g2d.setFont(TITLE_FONT);
            g2d.drawString(title, bounds.x + 20, bounds.y + 35);
            g2d.setColor(Color.decode("#e9ecef"));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(bounds.x + 20, bounds.y + 45, bounds.x + bounds.width - 20, bounds.y + 45);
            g2d.setStroke(new BasicStroke(1));
        }
    }

    private void drawButton(Graphics2D g2d, Rectangle rect, String text, Color color) {
        g2d.setColor(color);
        g2d.fill(new RoundRectangle2D.Double(rect.x, rect.y, rect.width, rect.height, 10, 10));
        g2d.setFont(BUTTON_FONT);
        drawCenteredString(g2d, text, rect, Color.WHITE);
    }
    
    private void drawInput(Graphics2D g2d, Rectangle rect, String label) {
        // Draw Label
        g2d.setFont(LABEL_FONT);
        g2d.setColor(Color.decode("#6c757d"));
        g2d.drawString(label, rect.x, rect.y - 8);

        // Draw Input Box
        boolean isActive = rect.equals(uiElements.get(activeElementId));
        g2d.setColor(Color.WHITE);
        g2d.fill(new RoundRectangle2D.Double(rect.x, rect.y, rect.width, rect.height, 10, 10));
        g2d.setColor(isActive ? Color.decode("#007bff") : Color.decode("#ced4da"));
        g2d.setStroke(new BasicStroke(isActive ? 2 : 1));
        g2d.draw(new RoundRectangle2D.Double(rect.x, rect.y, rect.width, rect.height, 10, 10));
        g2d.setStroke(new BasicStroke(1));

        // Draw Text and Cursor
        String text = formState.get(getKeyFromRect(rect));
        g2d.setFont(TEXT_FONT);
        g2d.setColor(Color.decode("#495057"));
        
        // Set a clipping region to prevent text from overflowing the input box
        Shape oldClip = g2d.getClip();
        g2d.clip(new Rectangle(rect.x + 10, rect.y, rect.width - 20, rect.height));
        
        FontMetrics fm = g2d.getFontMetrics();
        int textY = rect.y + (rect.height - fm.getHeight()) / 2 + fm.getAscent();
        g2d.drawString(text, rect.x + 10, textY);
        
        g2d.setClip(oldClip); // Restore the original clipping region

        // Draw blinking cursor
        if (isActive && cursorVisible) {
            int textWidth = fm.stringWidth(text);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(rect.x + 10 + textWidth, rect.y + 8, rect.x + 10 + textWidth, rect.y + rect.height - 8);
        }
    }
    
    // Helper to draw a string centered in a rectangle
    private void drawCenteredString(Graphics2D g2d, String text, Rectangle rect, Color color) {
        FontMetrics metrics = g2d.getFontMetrics();
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    // Helper to find the ID of a UI element from its Rectangle object
    private String getKeyFromRect(Rectangle rect) {
        for (Map.Entry<String, Rectangle> entry : uiElements.entrySet()) {
            if (entry.getValue().equals(rect)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Handles the back button click - closes current window and returns to dashboard
     */
    private void handleBackButton() {
        // Show confirmation dialog
        int option = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to go back? Any unsaved changes will be lost.",
            "Confirm Back",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (option == JOptionPane.YES_OPTION) {
            // Close the current window and return to dashboard
            SwingUtilities.invokeLater(() -> {
                try {
                    // Try to create Dashboard instance
                    Object dashboardInstance = Class.forName("Dashboard").getDeclaredConstructor().newInstance();
                    
                    if (dashboardInstance instanceof JFrame) {
                        JFrame dashboardFrame = (JFrame) dashboardInstance;
                        dashboardFrame.setVisible(true);
                        dashboardFrame.toFront();
                        dashboardFrame.requestFocus();
                    }
                    
                    // Close the current adoption form window
                    if (parentFrame != null) {
                        parentFrame.dispose();
                    } else {
                        // If no parent frame reference, find the window containing this component
                        Window window = SwingUtilities.getWindowAncestor(FormCanvas.this);
                        if (window != null) {
                            window.dispose();
                        }
                    }
                    
                } catch (Exception e) {
                    System.err.println("Error returning to dashboard: " + e.getMessage());
                    
                    // Fallback: just close the current window
                    if (parentFrame != null) {
                        parentFrame.dispose();
                    } else {
                        Window window = SwingUtilities.getWindowAncestor(FormCanvas.this);
                        if (window != null) {
                            window.dispose();
                        }
                    }
                    
                    JOptionPane.showMessageDialog(FormCanvas.this, 
                        "Returned to previous screen. Please reopen Dashboard if needed.",
                        "Info", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
    }
}

