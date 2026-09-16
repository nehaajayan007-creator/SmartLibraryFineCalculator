package library;

import java.time.LocalDate;

public class Book {

    private String isbn;
    private String title;
    private String author;
    private boolean issued;
    private String issuedTo;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return issued;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void issueBook(String memberId) {
        this.issued = true;
        this.issuedTo = memberId;
        this.issueDate = LocalDate.now();
        this.dueDate = issueDate.plusDays(14);
    }

    public void returnBook() {
        this.issued = false;
        this.issuedTo = null;
        this.issueDate = null;
        this.dueDate = null;
    }
    public void restoreIssueDetails(String memberId, LocalDate issueDate, LocalDate dueDate) {
    this.issued = true;
    this.issuedTo = memberId;
    this.issueDate = issueDate;
    this.dueDate = dueDate;
    }
    
    @Override
    public String toString() {
        return "ISBN: " + isbn
                + " | Title: " + title
                + " | Author: " + author
                + " | Issued: " + issued;
    }
}
