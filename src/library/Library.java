package library;

import java.util.ArrayList;

public class Library {

    private ArrayList<Book> books;
    private ArrayList<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }
    
    // Add a book to the library 
    public void addBook(Book book) {

    if (findBook(book.getIsbn()) != null) {
        System.out.println("A book with this ISBN already exists.");
        return;
    }

    books.add(book);
    System.out.println("Book added successfully.");
    }
    

    // Add a member to the library
    public void addMember(Member member) {

    for (Member existingMember : members) {

        if (existingMember.getMemberId()
                .equalsIgnoreCase(member.getMemberId())) {

            System.out.println(
                    "A member with this ID already exists."
            );
            return;
        }
    }

    members.add(member);
    System.out.println("Member added successfully.");
}
    // Find a book using ISBN
    public Book findBook(String isbn) {

        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return book;
            }
        }

        return null;
    }

    // Find a member using member ID
    public Member findMember(String memberId)
            throws MemberNotFoundException {

        for (Member member : members) {

            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }

        throw new MemberNotFoundException(
                "Member with ID " + memberId + " was not found."
        );
    }

    // Issue a book to a member
    public void issueBook(String isbn, String memberId)
            throws BookNotAvailableException,
                    MemberNotFoundException {

        Book book = findBook(isbn);

        if (book == null) {
            throw new BookNotAvailableException(
                    "Book with ISBN " + isbn + " was not found."
            );
        }

        if (book.isIssued()) {
            throw new BookNotAvailableException(
                    "Book is already issued."
            );
        }

        Member member = findMember(memberId);

        book.issueBook(memberId);
        member.addBook(book);

        System.out.println("Book issued successfully.");
        System.out.println("Issue Date: " + book.getIssueDate());
        System.out.println("Due Date: " + book.getDueDate());
    }

    // Return a book
    public double returnBook(String isbn)
            throws BookNotAvailableException {

        Book book = findBook(isbn);

        if (book == null) {
            throw new BookNotAvailableException(
                    "Book with ISBN " + isbn + " was not found."
            );
        }

        if (!book.isIssued()) {
            throw new BookNotAvailableException(
                    "This book is not currently issued."
            );
        }

        Member member;

        try {
            member = findMember(book.getIssuedTo());
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
            return 0;
        }

        java.time.LocalDate returnDate =
                java.time.LocalDate.now();

        long daysLate = 0;

        if (returnDate.isAfter(book.getDueDate())) {
            daysLate =
                    java.time.temporal.ChronoUnit.DAYS.between(
                            book.getDueDate(),
                            returnDate
                    );
        }

        FinePolicy finePolicy =
                new StudentFinePolicy();

        double fine =
                finePolicy.calculateFine(daysLate);

        member.removeBook(book);
        book.returnBook();

        System.out.println("Book returned successfully.");
        System.out.println("Return Date: " + returnDate);
        System.out.println("Days Late: " + daysLate);
        System.out.println("Fine: Rs. " + fine);

        return fine;
    }

    // Display all currently issued books
    public void viewIssuedBooks() {

        boolean found = false;

        System.out.println("\n===== ISSUED BOOKS =====");

        for (Book book : books) {

            if (book.isIssued()) {

                found = true;

                System.out.println(
                        "ISBN: " + book.getIsbn()
                        + " | Title: " + book.getTitle()
                        + " | Issued To: " + book.getIssuedTo()
                        + " | Due Date: " + book.getDueDate()
                );
            }
        }

        if (!found) {
            System.out.println("No books are currently issued.");
        }
    }

    // Search for a book
    public void searchBook(String keyword) {

    boolean found = false;

    keyword = keyword.toLowerCase();

    for (Book book : books) {

        if (book.getIsbn().toLowerCase().contains(keyword)
                || book.getTitle().toLowerCase().contains(keyword)
                || book.getAuthor().toLowerCase().contains(keyword)) {

            System.out.println(book);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No matching book found.");
        }
    }
    

    // Search for a member
    public void searchMember(String memberId) {

        for (Member member : members) {

            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                System.out.println(member);
                return;
            }
        }

        System.out.println("No matching member found.");
    }

    // Display all books
    public void displayAllBooks() {

        System.out.println("\n===== ALL BOOKS =====");

        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Display all members
    public void displayAllMembers() {

        System.out.println("\n===== ALL MEMBERS =====");

        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        for (Member member : members) {
            System.out.println(member);
        }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }
}