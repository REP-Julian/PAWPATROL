import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.util.Date;

public class VetAppointment extends JPanel {

    // Define colors and fonts to match the HTML design
    private static final Color BG_COLOR = new Color(248, 250, 252); // bg-gray-50
    private static final Color SECTION_BG_COLOR = new Color(243, 244, 246); // bg-gray-100
    private static final Color TEXT_COLOR = new Color(17, 24, 39); // text-gray-900
    private static final Color BORDER_COLOR = new Color(229, 231, 235); // border-gray-200
    private static final Color INPUT_BG_COLOR = Color.WHITE;
    private static final Color BLUE_ACCENT = new Color(59, 130, 246); // bg-blue-600
    private static final Color GRAY_BUTTON_BG = new Color(209, 213, 219); // bg-gray-300

    private static final Font FONT_HEADER = new Font("Inter", Font.BOLD, 36);
    private static final Font FONT_TITLE = new Font("Inter", Font.BOLD, 20);
    private static final Font FONT_LABEL = new Font("Inter", Font.PLAIN, 14);
    private static final Font FONT_INPUT = new Font("Inter", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Inter", Font.BOLD, 14);

    // Form components to be accessed for clearing
    private JTextField petNameField, petTypeField, ageBreedField;
    private JTextField petIdField, ownerNameField, petWeightField, emergencyContactField;
    private JComboBox<String> vetSelector, genderComboBox;
    private JFormattedTextField dateField, contactNumberField, lastVaccinationField;
    private JSpinner timeSpinner;
    private JTextArea medicalHistoryArea, allergiesArea;

    public VetAppointment() {
        setBackground(BG_COLOR);
        setLayout(new BorderLayout(32, 0));
        setBorder(new EmptyBorder(32, 32, 32, 32));

        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);

        // Main content area with two columns
        add(createMainContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(BG_COLOR);
        
        JLabel titleLabel = new JLabel("Vet Appointment");
        titleLabel.setFont(FONT_HEADER);
        titleLabel.setForeground(TEXT_COLOR);
        
        headerPanel.add(titleLabel);
        headerPanel.setBorder(new EmptyBorder(0, 0, 24, 0));
        return headerPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Force alignment to northwest (left-top)

        // Left column: Form panel (increased width, aligned left)
        gbc.gridx = 0;
        gbc.weightx = 0.75; // Increased width
        gbc.insets = new Insets(0, 0, 0, 16);
        contentPanel.add(createFormPanel(), gbc);

        // Right column: Vet info panel (decreased width)
        gbc.gridx = 1;
        gbc.weightx = 0.25; // Decreased width
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPanel.add(createVetInfoPanel(), gbc);
        
        return contentPanel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment

        // Create Pet Details section with scrolling
        JPanel petDetailsPanel = createPetDetailsFields();
        JPanel petDetailsSection = createScrollableSectionPanel("Pet Details", petDetailsPanel, 300);
        panel.add(petDetailsSection);
        
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Reduced spacing
        panel.add(createSectionPanel("Appointment Details", createAppointmentDetailsFields()));
        
        panel.add(Box.createVerticalGlue()); 
        panel.add(createActionButtons());

        return panel;
    }

    private JPanel createSectionPanel(String title, JPanel fieldsPanel) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(SECTION_BG_COLOR);
        section.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment
        
        Border lineBorder = new LineBorder(BORDER_COLOR, 1);
        Border emptyBorder = new EmptyBorder(16, 16, 16, 16); // Reduced padding

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            new CompoundBorder(lineBorder, emptyBorder),
            title,
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            FONT_TITLE,
            TEXT_COLOR
        );
        section.setBorder(titledBorder);
        section.add(fieldsPanel, BorderLayout.CENTER);
        return section;
    }

    private JPanel createScrollableSectionPanel(String title, JPanel fieldsPanel, int maxHeight) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(SECTION_BG_COLOR);
        section.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment
        
        Border lineBorder = new LineBorder(BORDER_COLOR, 1);
        Border emptyBorder = new EmptyBorder(16, 16, 16, 16); // Reduced padding

        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            new CompoundBorder(lineBorder, emptyBorder),
            title,
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            FONT_TITLE,
            TEXT_COLOR
        );
        section.setBorder(titledBorder);
        
        // Create scroll pane for the fields
        JScrollPane scrollPane = new JScrollPane(fieldsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); // Remove border as it's handled by the section
        scrollPane.setBackground(SECTION_BG_COLOR);
        scrollPane.getViewport().setBackground(SECTION_BG_COLOR);
        
        // Configure smooth scrolling
        configureScrollPaneForSmoothScrolling(scrollPane);
        
        // Set preferred size to control when scrolling kicks in
        scrollPane.setPreferredSize(new Dimension(0, maxHeight));
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));
        
        section.add(scrollPane, BorderLayout.CENTER);
        return section;
    }

    private JPanel createPetDetailsFields() {
        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);
        GridBagConstraints gbc = createGbc();

        // Pet ID & Owner Name (first row)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        petIdField = createTextField();
        addField(fields, gbc, "Pet ID", petIdField);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        ownerNameField = createTextField();
        addField(fields, gbc, "Owner Name", ownerNameField);

        // Pet Name (second row, full width)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        petNameField = createTextField();
        addField(fields, gbc, "Pet Name", petNameField);
        
        // Pet Type & Age/Breed (third row)
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        petTypeField = createTextField();
        addField(fields, gbc, "Pet Type", petTypeField);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        ageBreedField = createTextField();
        addField(fields, gbc, "Age/Breed", ageBreedField);

        // Gender & Pet Weight (fourth row)
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        String[] genders = {"Male", "Female", "Neutered Male", "Spayed Female"};
        genderComboBox = new JComboBox<>(genders);
        styleComboBox(genderComboBox);
        addField(fields, gbc, "Gender", genderComboBox);
        
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        petWeightField = createTextField();
        addField(fields, gbc, "Pet Weight (kg)", petWeightField);

        // Last Vaccination Date & Emergency Contact (fifth row)
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        lastVaccinationField = createDateField();
        addField(fields, gbc, "Last Vaccination", createFieldWithIcon(lastVaccinationField, "💉"));
        
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        emergencyContactField = createTextField();
        addField(fields, gbc, "Emergency Contact", emergencyContactField);

        // Medical History (sixth row, full width)
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        medicalHistoryArea = createTextArea(3);
        JScrollPane medicalHistoryScroll = new JScrollPane(medicalHistoryArea);
        medicalHistoryScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        medicalHistoryScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        medicalHistoryScroll.setBorder(new LineBorder(BORDER_COLOR, 1));
        // Configure smooth scrolling for medical history
        configureScrollPaneForSmoothScrolling(medicalHistoryScroll);
        addField(fields, gbc, "Medical History", medicalHistoryScroll);

        // Allergies (seventh row, full width)
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        allergiesArea = createTextArea(2);
        JScrollPane allergiesScroll = new JScrollPane(allergiesArea);
        allergiesScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        allergiesScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        allergiesScroll.setBorder(new LineBorder(BORDER_COLOR, 1));
        // Configure smooth scrolling for allergies
        configureScrollPaneForSmoothScrolling(allergiesScroll);
        addField(fields, gbc, "Known Allergies", allergiesScroll);

        return fields;
    }
    
    private JPanel createAppointmentDetailsFields() {
        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);
        GridBagConstraints gbc = createGbc();

        // Select Vet & Date (first row, properly aligned)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        String[] vets = {"Dr. Marlon Paul Agustino", "Dr. Anna Kendrick", "Dr. Peter Jones"};
        vetSelector = new JComboBox<>(vets);
        styleComboBox(vetSelector);
        addField(fields, gbc, "Select Vet", vetSelector);

        gbc.gridx = 1;
        gbc.gridy = 0; // Same row as select vet
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        dateField = createDateField();
        addField(fields, gbc, "Date", createFieldWithIcon(dateField, "📅"));

        // Time & Contact Number (second row, properly aligned)
        gbc.gridx = 0;
        gbc.gridy = 2; // Skip past the first row
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        timeSpinner = createTimeField();
        addField(fields, gbc, "Time", createFieldWithIcon(timeSpinner, "🕒"));
        
        gbc.gridx = 1;
        gbc.gridy = 2; // Same row as time
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        contactNumberField = createContactField();
        addField(fields, gbc, "Contact Number", contactNumberField);
        
        return fields;
    }
    
    private JPanel createActionButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0)); // Reduced gap
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setBorder(new EmptyBorder(16, 0, 0, 0)); // Reduced top margin
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment

        JButton bookButton = new JButton("Book Appointment");
        styleButton(bookButton, BLUE_ACCENT, Color.WHITE);
        bookButton.addActionListener(_ -> {
            // Simulate booking success
            JOptionPane.showMessageDialog(this, 
                "Your appointment has been successfully scheduled.",
                "Appointment Booked!",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        JButton clearButton = new JButton("Clear Form");
        styleButton(clearButton, GRAY_BUTTON_BG, TEXT_COLOR);
        clearButton.addActionListener(_ -> clearForm());

        buttonPanel.add(bookButton);
        buttonPanel.add(clearButton);
        return buttonPanel;
    }

    private JPanel createVetInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(SECTION_BG_COLOR);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment
        panel.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            new EmptyBorder(16, 16, 16, 16) // Reduced padding
        ));

        panel.add(createVetCard(
            "Dr. Marlon Paul Agustino", "0912-345-6789", "Dr.MpA@vetelinnic.com",
            "Happy Paws Veterinary Center", "Brgy. San Isidro, Quezon City"
        ));
        panel.add(Box.createRigidArea(new Dimension(0, 16))); // Reduced spacing
        
        panel.add(createPlaceholderCard());
        panel.add(Box.createRigidArea(new Dimension(0, 16))); // Reduced spacing

        JButton viewMore = new JButton("View More Vets  ▼");
        styleButton(viewMore, new Color(229, 231, 235), TEXT_COLOR);
        viewMore.setAlignmentX(Component.LEFT_ALIGNMENT); // Force left alignment
        panel.add(viewMore);
        
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createVetCard(String name, String phone, String email, String clinic, String address) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(INPUT_BG_COLOR);
        card.setBorder(new CompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true), new EmptyBorder(16, 16, 16, 16)
        ));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, card.getPreferredSize().height));

        card.add(createCardLabel(name, FONT_TITLE));
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(createCardLabel("Contact: " + phone, FONT_LABEL));
        card.add(createCardLabel("Email: " + email, FONT_LABEL));
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(createCardLabel(clinic, new Font("Inter", Font.BOLD, 14)));
        card.add(createCardLabel(address, FONT_LABEL));
        return card;
    }

    private void clearForm() {
        // Clear existing fields
        petNameField.setText("");
        petTypeField.setText("");
        ageBreedField.setText("");
        vetSelector.setSelectedIndex(0);
        dateField.setValue(null); // Clears the formatted text field
        timeSpinner.setValue(new Date()); // Reset time to current
        contactNumberField.setValue(null);
        
        // Clear new pet detail fields
        petIdField.setText("");
        ownerNameField.setText("");
        petWeightField.setText("");
        emergencyContactField.setText("");
        genderComboBox.setSelectedIndex(0);
        lastVaccinationField.setValue(null);
        medicalHistoryArea.setText("");
        allergiesArea.setText("");
    }
    
    // --- Helper and Styling Methods ---

    private JPanel createPlaceholderCard() {
        JPanel card = new JPanel();
        card.setBackground(INPUT_BG_COLOR);
        card.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setPreferredSize(new Dimension(100, 160)); 
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        return card;
    }

    
    private JLabel createCardLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST; // Force northwest alignment (left-top)
        gbc.insets = new Insets(2, 2, 2, 2); // Reduced insets for tighter layout
        return gbc;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, Component component) {
        // Use the provided grid position (don't auto-calculate)
        int currentGridX = gbc.gridx;
        int currentGridY = gbc.gridy;
        int currentGridWidth = gbc.gridwidth;

        // Add label
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(FONT_LABEL);
        jLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.insets = new Insets(2, 4, 2, 4);
        panel.add(jLabel, gbc);
        
        // Add component below the label
        gbc.gridy = currentGridY + 1;
        gbc.insets = new Insets(0, 4, 12, 4);
        panel.add(component, gbc);

        // Restore original grid settings for next use
        gbc.gridx = currentGridX;
        gbc.gridy = currentGridY;
        gbc.gridwidth = currentGridWidth;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(FONT_INPUT);
        textField.setBackground(INPUT_BG_COLOR);
        textField.setBorder(new CompoundBorder(new LineBorder(BORDER_COLOR, 1), new EmptyBorder(10, 10, 10, 10)));
        return textField;
    }

    private JTextArea createTextArea(int rows) {
        JTextArea textArea = new JTextArea(rows, 0);
        textArea.setFont(FONT_INPUT);
        textArea.setBackground(INPUT_BG_COLOR);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    private JFormattedTextField createDateField() {
        try {
            MaskFormatter formatter = new MaskFormatter("####-##-##");
            formatter.setPlaceholderCharacter(' ');
            JFormattedTextField field = new JFormattedTextField(formatter);
            styleTextField(field, false);
            return field;
        } catch (ParseException e) { return new JFormattedTextField("Date Error"); }
    }

    private JSpinner createTimeField() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinner, "hh:mm a");
        spinner.setEditor(timeEditor);
        spinner.setFont(FONT_INPUT);
        // Style the inner text field
        JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        tf.setBackground(INPUT_BG_COLOR);
        tf.setBorder(new EmptyBorder(8, 10, 8, 10));
        // Remove spinner's own border to fit inside the icon panel
        spinner.setBorder(null);
        return spinner;
    }
    
    private JPanel createFieldWithIcon(Component field, String icon) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(INPUT_BG_COLOR);
        panel.setBorder(new LineBorder(BORDER_COLOR, 1));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(FONT_LABEL);
        iconLabel.setBorder(new EmptyBorder(0, 8, 0, 8));
        
        panel.add(field, BorderLayout.CENTER);
        panel.add(iconLabel, BorderLayout.EAST);
        return panel;
    }
    
    private JFormattedTextField createContactField() {
        try {
            MaskFormatter formatter = new MaskFormatter("+#-###-###-####");
            formatter.setPlaceholderCharacter(' ');
            JFormattedTextField field = new JFormattedTextField(formatter);
            styleTextField(field, true);
            return field;
        } catch (ParseException e) { return new JFormattedTextField("Phone Error"); }
    }

    private void styleTextField(JFormattedTextField field, boolean hasIcon) {
        field.setFont(FONT_INPUT);
        field.setBackground(INPUT_BG_COLOR);
        // The border will be on the parent panel if there's an icon
        if (!hasIcon) {
            field.setBorder(new CompoundBorder(new LineBorder(BORDER_COLOR, 1), new EmptyBorder(10, 10, 10, 10)));
        } else {
             field.setBorder(new EmptyBorder(10, 10, 10, 10));
        }
    }
    
    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(FONT_INPUT);
        comboBox.setBackground(INPUT_BG_COLOR);
        comboBox.setBorder(new EmptyBorder(8, 5, 8, 5));
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 24, 12, 24));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void configureScrollPaneForSmoothScrolling(JScrollPane scrollPane) {
        // Configure smooth scrolling behavior
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scroll increment
        scrollPane.getVerticalScrollBar().setBlockIncrement(64); // Page scroll increment
        scrollPane.setWheelScrollingEnabled(true); // Enable mouse wheel scrolling
        
        // Optional: Style the scroll bar for better appearance
        scrollPane.getVerticalScrollBar().setBackground(SECTION_BG_COLOR);
        scrollPane.getVerticalScrollBar().setForeground(BORDER_COLOR);
    }

    // Main method to allow running this panel as a standalone application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Vet Appointment");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.add(new VetAppointment());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

