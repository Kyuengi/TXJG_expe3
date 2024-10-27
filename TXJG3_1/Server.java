package TXJG3_1;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 1234;
    private static final Map<String, Contact> contacts = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is running...");

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String response = handleInput(inputLine);
                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String handleInput(String input) {
            String[] parts = input.split(" ", 3);
            String command = parts[0];
            String name = parts[1];
            String details = parts[2];

            switch (command) {
                case "ADD":
                    contacts.put(name, new Contact(name, details));
                    return "Contact added";
                case "UPDATE":
                    Contact contact = contacts.get(name);
                    if (contact != null) {
                        contact.setDetails(details);
                        return "Contact updated";
                    }
                    return "Contact not found";
                case "DELETE":
                    if (contacts.remove(name) != null) {
                        return "Contact deleted";
                    }
                    return "Contact not found";
                default:
                    return "Invalid command";
            }
        }
    }

    static class Contact {
        private String name;
        private String details;

        public Contact(String name, String details) {
            this.name = name;
            this.details = details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return name + ": " + details;
        }
    }
}