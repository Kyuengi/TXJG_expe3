package TXJG3_2;

import java.util.ArrayList;
import java.util.List;

public class ContactService {
    private List<Contact> contacts = new ArrayList<>();
    private int nextId = 1; // 用于生成联系人ID

    public Contact addContact(String name, String address, String phone) {
        Contact contact = new Contact(nextId++, name, address, phone);
        contacts.add(contact);
        return contact;
    }

    public Contact getContact(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public void updateContact(Contact contact) {
        int index = contacts.indexOf(contact);
        if (index != -1) {
            contacts.set(index, contact);
        }
    }

    public void deleteContact(int id) {
        contacts.removeIf(contact -> contact.getId() == id);
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }
}