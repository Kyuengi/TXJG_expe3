package TXJG3_2;

import javax.swing.*;
import java.awt.*;

public class ContactApp extends JFrame {
    private ContactService contactService;
    private JTextField nameField, addressField, phoneField;
    private JList<Contact> contactList;
    private DefaultListModel<Contact> listModel;

    public ContactApp() {
        contactService = new ContactService();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // 创建输入字段
        JPanel inputPanel = new JPanel();
        nameField = new JTextField(10);
        addressField = new JTextField(10);
        phoneField = new JTextField(10);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Phone:"));
        inputPanel.add(phoneField);

        // 创建联系人列表
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        JScrollPane listScroller = new JScrollPane(contactList);

        // 创建操作按钮
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton modifyButton = new JButton("Modify");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        // 添加组件到窗口
        add(inputPanel, BorderLayout.NORTH);
        add(listScroller, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // 添加事件监听器
        addButton.addActionListener(e -> addContact());
        modifyButton.addActionListener(e -> modifyContact());
        deleteButton.addActionListener(e -> deleteContact());

        setVisible(true);
    }

    private void addContact() {
        String name = nameField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        Contact contact = contactService.addContact(name, address, phone);
        listModel.addElement(contact);
        clearFields();
    }

    private void modifyContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            Contact selectedContact = contactList.getModel().getElementAt(selectedIndex);
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            selectedContact.setName(name);
            selectedContact.setAddress(address);
            selectedContact.setPhone(phone);
            contactService.updateContact(selectedContact);
            listModel.set(selectedIndex, selectedContact);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to modify.");
        }
    }

    private void deleteContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            Contact selectedContact = contactList.getModel().getElementAt(selectedIndex);
            contactService.deleteContact(selectedContact.getId());
            listModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a contact to delete.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactApp());
    }
}