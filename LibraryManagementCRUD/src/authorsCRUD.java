import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class authorsCRUD implements crudOperations<authorsModel> {

    private Connection connection;

    public authorsCRUD() {
        this.connection = dbConnection.get_connection();
    }

    @Override
    public List<authorsModel> findAll() {
        String sql = "SELECT * FROM authors;";
        List<authorsModel> resultList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                resultList.add(new authorsModel(
                        result.getInt("id"),
                        result.getString("author_name"),
                        result.getString("sex").charAt(0)
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public List<authorsModel> saveAll(List<authorsModel> toSave) {
        String sql = "INSERT INTO authors (author_name, sex) VALUES (?, ?);";
        try (PreparedStatement prepared = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (authorsModel author : toSave) {
                prepared.setString(1, author.getAuthorName());
                prepared.setString(2, String.valueOf(author.getAuthorSex()));
                prepared.addBatch();
            }

            prepared.executeBatch();

            ResultSet generatedKeys = prepared.getGeneratedKeys();
            int i = 0;
            while (generatedKeys.next()) {
                toSave.get(i).id = generatedKeys.getInt(1);
                i++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toSave;
    }

    @Override
    public authorsModel save(authorsModel toSave) {
        String sql = "INSERT INTO authors (author_name, sex) VALUES (?, ?);";
        try (PreparedStatement prepared = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepared.setString(1, toSave.getAuthorName());
            prepared.setString(2, String.valueOf(toSave.getAuthorSex()));
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
    public authorsModel delete(authorsModel toDelete) {
        String sql = "DELETE FROM authors WHERE id = ?;";
        try (PreparedStatement prepared = connection.prepareStatement(sql)) {
            prepared.setInt(1, toDelete.getId());
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toDelete;
    }
}
