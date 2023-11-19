import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class booksCRUD implements crudOperations<booksModel> {

    private Connection connection;
    private Statement statement;

    public booksCRUD() {
        this.connection = dbConnection.get_connection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<booksModel> findAll() {
        String sql = "SELECT * FROM books;";
        List<booksModel> resultList = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                resultList.add(new booksModel(
                        result.getInt("id"),
                        result.getString("author_name"),
                        result.getString("book_name"),
                        result.getInt("page_numbers"),
                        result.getString("topic"),
                        result.getDate("release_date"),
                        result.getBoolean("is_available")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public List<booksModel> saveAll(List<booksModel> toSave) {
        return null;
    }

    @Override
    public booksModel save(booksModel toSave) {
        String sql = "INSERT INTO books (author_name, book_name, page_numbers, topic, release_date, is_available) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement prepared = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prepared.setString(1, toSave.getAuthorName());
            prepared.setString(2, toSave.getBookName());
            prepared.setInt(3, toSave.getPageNumbers());
            prepared.setString(4, toSave.getTopic());
            prepared.setDate(5, (Date) toSave.getReleaseDate());
            prepared.setBoolean(6, toSave.isAvailable());
            prepared.executeUpdate();

            ResultSet generatedKeys = prepared.getGeneratedKeys();
            if (generatedKeys.next()) {
                toSave.id = generatedKeys.getInt(1);
            } else {
                throw new SQLException("La sauvegarde a échoué, aucun ID généré.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toSave;
    }


    @Override
    public booksModel delete(booksModel toDelete) {
        String sql = "DELETE FROM books WHERE id = ?;";
        try {
            PreparedStatement prepared = connection.prepareStatement(sql);
            prepared.setInt(1, toDelete.getId());
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toDelete;
    }
}
