package library;

import java.util.ArrayList;

public class Member {

    private String memberId;
    private String name;
    private String email;
    private ArrayList<Book> issuedBooks;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.issuedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public void addBook(Book book) {
        issuedBooks.add(book);
    }

    public void removeBook(Book book) {
        issuedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "ID: " + memberId
                + " | Name: " + name
                + " | Email: " + email
                + " | Books Issued: " + issuedBooks.size();
    }
}