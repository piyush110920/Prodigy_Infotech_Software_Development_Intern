import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Contact {
    String name;
    String phone;
    String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "," + phone + "," + email;
    }
}

public class PRODIGY_SD_03 {
    private static final String FILENAME = "contacts.txt";
    private List<Contact> contacts;
    private JTextArea contactArea;
    private JTextField nameField, phoneField, emailField;

    public PRODIGY_SD_03() {
        contacts = loadContacts();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Contact Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 100, 30);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(120, 20, 200, 30);
        frame.add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 60, 100, 30);
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(120, 60, 200, 30);
        frame.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 100, 30);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 100, 200, 30);
        frame.add(emailField);

        JButton addButton = new JButton("Add Contact");
        addButton.setBounds(20, 140, 150, 30);
        frame.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        JButton viewButton = new JButton("View Contacts");
        viewButton.setBounds(200, 140, 150, 30);
        frame.add(viewButton);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewContacts();
            }
        });

        JButton editButton = new JButton("Edit Contact");
        editButton.setBounds(20, 180, 150, 30);
        frame.add(editButton);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editContact();
            }
        });

        JButton deleteButton = new JButton("Delete Contact");
        deleteButton.setBounds(200, 180, 150, 30);
        frame.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        contactArea = new JTextArea();
        contactArea.setBounds(20, 220, 350, 120);
        frame.add(contactArea);

        frame.setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            contacts.add(new Contact(name, phone, email));
            saveContacts();
            JOptionPane.showMessageDialog(null, "Contact added successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
        }
    }

    private void viewContacts() {
        StringBuilder contactList = new StringBuilder();
        for (Contact contact : contacts) {
            contactList.append(contact.name).append(", ").append(contact.phone).append(", ").append(contact.email).append("\n");
        }
        contactArea.setText(contactList.toString());
    }

    private void editContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
            for (Contact contact : contacts) {
                if (contact.name.equals(name)) {
                    contact.phone = phone;
                    contact.email = email;
                    saveContacts();
                    JOptionPane.showMessageDialog(null, "Contact updated successfully.");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Contact not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
        }
    }

    private void deleteContact() {
        String name = nameField.getText();

        if (!name.isEmpty()) {
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).name.equals(name)) {
                    contacts.remove(i);
                    saveContacts();
                    JOptionPane.showMessageDialog(null, "Contact deleted successfully.");
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Contact not found.");
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a name to delete.");
        }
    }

    private List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                contacts.add(new Contact(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            System.out.println("No existing contacts found.");
        }
        return contacts;
    }

    private void saveContacts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Contact contact : contacts) {
                bw.write(contact.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PRODIGY_SD_03());
    }
}