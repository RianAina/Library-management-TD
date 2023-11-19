public class authorsModel {
    int id;
    private String authorName;
    private char authorSex;

    public int getId() {
        return id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public char getAuthorSex() {
        return authorSex;
    }

    public authorsModel(int id, String authorName, char authorSex) {
        this.id = id;
        this.authorName = authorName;
        this.authorSex = authorSex;
    }
}

