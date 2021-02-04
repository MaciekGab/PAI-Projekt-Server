package pl.dmcs.model;

import pl.dmcs.enums.BookCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Book implements Serializable {

    private static final long serialVersionUID = 1;

    private String title;
    private String author;
    private BookCategory category;

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public static List<Book> randomBooksList(int amount) {
        List<Book> books = new ArrayList<>();

        String[] authors = new String[]{"Autor Pierwszy", "Autor Drugi", "Autor Trzeci", "Autor Czwarty"};
        String[] titles = new String[]{"Tytuł Pierwszy", "Tytuł Drugi", "Tytuł Trzeci", "Tytuł Czwarty"};

        for (int i = 0; i < amount; i++) {
            Random r = new Random();
            int authorIndex = r.nextInt(3);
            int titleIndex = r.nextInt(3);
            int categoryIndex = r.nextInt(3);

            Book book = new Book();
            book.setAuthor(authors[authorIndex]);
            book.setTitle(titles[titleIndex]);
            book.setCategory(BookCategory.getByOrdinal(categoryIndex));

            books.add(book);

        }

        return books;
    }
}
