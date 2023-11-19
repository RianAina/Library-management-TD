import java.util.Date;

public class booksModel {
    int id;
    private String authorName;
    private String bookName;
    private int pageNumbers;
    private String topic;
    private Date releaseDate;
    private boolean isAvailable;

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public int getPageNumbers() {
        return pageNumbers;
    }

    public String getTopic() {
        return topic;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public booksModel(int id, String authorName, String bookName, int pageNumbers, String topic, Date releaseDate, boolean isAvailable) {
        this.id = id;
        this.authorName = authorName;
        this.bookName = bookName;
        this.pageNumbers = pageNumbers;
        this.topic = topic;
        this.releaseDate = releaseDate;
        this.isAvailable = isAvailable;
    }
}

