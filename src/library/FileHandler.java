package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class FileHandler {

    private static final String FILE_PATH = "data/library.txt";

    // Save library data to file
    public static void saveLibrary(Library library) {

        try {

            File file = new File(FILE_PATH);

            file.getParentFile().mkdirs();

            PrintWriter writer =
                    new PrintWriter(new FileWriter(file));

            writer.println("BOOKS");

            for (Book book : library.getBooks()) {

                writer.println(
                        book.getIsbn() + "|" +
                        book.getTitle() + "|" +
                        book.getAuthor() + "|" +
                        book.isIssued() + "|" +
                        book.getIssuedTo() + "|" +
                        book.getIssueDate() + "|" +
                        book.getDueDate()
                );
            }

            writer.println("MEMBERS");

            for (Member member : library.getMembers()) {

                writer.println(
                        "STUDENT" + "|" +
                        member.getMemberId() + "|" +
                        member.getName() + "|" +
                        member.getEmail()
                );
            }

            writer.close();

            System.out.println("Library data saved successfully.");

        } catch (IOException e) {

            System.out.println(
                    "Error saving library data: " + e.getMessage()
            );
        }
    }

    // Load library data from file
    public static void loadLibrary(Library library) {

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader(file));

            String line;
            boolean readingBooks = false;
            boolean readingMembers = false;

            while ((line = reader.readLine()) != null) {

                if (line.equals("BOOKS")) {
                    readingBooks = true;
                    readingMembers = false;
                    continue;
                }

                if (line.equals("MEMBERS")) {
                    readingBooks = false;
                    readingMembers = true;
                    continue;
                }

                String[] data = line.split("\\|");

                if (readingBooks && data.length == 7) {

                    String isbn = data[0];
                    String title = data[1];
                    String author = data[2];

                    boolean issued =
                            Boolean.parseBoolean(data[3]);

                    Book book =
                            new Book(isbn, title, author);

                    if (issued) {

                        String memberId = data[4];

                        LocalDate issueDate =
                                LocalDate.parse(data[5]);

                        LocalDate dueDate =
                                LocalDate.parse(data[6]);

                        book.restoreIssueDetails(
                                memberId,
                                issueDate,
                                dueDate
                        );
                    }

                    library.getBooks().add(book);
                }

                if (readingMembers && data.length == 4) {

                    String memberId = data[1];
                    String name = data[2];
                    String email = data[3];

                    StudentMember member =
                            new StudentMember(
                                    memberId,
                                    name,
                                    email
                            );

                    library.getMembers().add(member);
                }
            }

            reader.close();

            // Reconnect issued books with their members
            for (Book book : library.getBooks()) {

                if (book.isIssued()) {

                    for (Member member : library.getMembers()) {

                        if (member.getMemberId()
                                .equalsIgnoreCase(book.getIssuedTo())) {

                            member.addBook(book);
                        }
                    }
                }
            }

            System.out.println("Library data loaded successfully.");

        } catch (IOException | RuntimeException e) {

            System.out.println(
                    "Error loading library data: " + e.getMessage()
            );
        }
    }
}