package library;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        
        FileHandler.loadLibrary(library);

        boolean running = true;

        System.out.println("======================================");
        System.out.println("      LIBRARY FINE CALCULATOR");
        System.out.println("======================================");

        while (running) {

            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addMember();
                    break;

                case 2:
                    addBook();
                    break;

                case 3:
                    issueBook();
                    break;

                case 4:
                    returnBook();
                    break;

                case 5:
                    library.viewIssuedBooks();
                    break;

                case 6:
                    searchBook();
                    break;

                case 7:
                    searchMember();
                    break;

                case 8:
                    library.displayAllBooks();
                    break;

                case 9:
                    library.displayAllMembers();
                    break;
                
                case 10:
                    FileHandler.saveLibrary(library);

                    running = false;

                    System.out.println(
                        "\nThank you for using Library Fine Calculator!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println("\n------------- MENU -------------");
        System.out.println("1. Add Member");
        System.out.println("2. Add Book");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. View All Issued Books");
        System.out.println("6. Search Book");
        System.out.println("7. Search Member");
        System.out.println("8. View All Books");
        System.out.println("9. View All Members");
        System.out.println("10. Exit");
        System.out.println("--------------------------------");
    }

    private static void addMember() {

        System.out.println("\n===== ADD MEMBER =====");

        String id = readString("Enter member ID: ");
        String name = readString("Enter member name: ");
        String email = readString("Enter email: ");
        
        System.out.println("\nSelect member type:");
        System.out.println("1. Student");

        int type = readInt("Enter type: ");

        Member member;

        if (type == 1) {

                    member = new StudentMember(id, name, email);

        } else {

            System.out.println("Invalid member type.");
            return;
        }
    

        library.addMember(member);
    }

    private static void addBook() {

        System.out.println("\n===== ADD BOOK =====");

        String isbn = readString("Enter ISBN: ");
        String title = readString("Enter book title: ");
        String author = readString("Enter author name: ");

        Book book = new Book(isbn, title, author);

        library.addBook(book);
    }

    private static void issueBook() {

        System.out.println("\n===== ISSUE BOOK =====");

        String isbn = readString("Enter book ISBN: ");
        String memberId = readString("Enter member ID: ");

        try {

            library.issueBook(isbn, memberId);

        } catch (BookNotAvailableException e) {

            System.out.println(
                    "Issue failed: " + e.getMessage()
            );

        } catch (MemberNotFoundException e) {

            System.out.println(
                    "Issue failed: " + e.getMessage()
            );
        }
    }

    private static void returnBook() {

        System.out.println("\n===== RETURN BOOK =====");

        String isbn = readString("Enter book ISBN: ");

        try {

            library.returnBook(isbn);

        } catch (BookNotAvailableException e) {

            System.out.println(
                    "Return failed: " + e.getMessage()
            );
        }
    }

    private static void searchBook() {

        System.out.println("\n===== SEARCH BOOK =====");

        String keyword =
                readString("Enter ISBN, title or author: ");

        library.searchBook(keyword);
    }

    private static void searchMember() {

        System.out.println("\n===== SEARCH MEMBER =====");

        String memberId =
                readString("Enter member ID: ");

        library.searchMember(memberId);
    }

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
    private static String readString(String message) {

    while (true) {

        System.out.print(message);

        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            return input;
        }

        System.out.println("Input cannot be empty. Please try again.");
    }
}

}