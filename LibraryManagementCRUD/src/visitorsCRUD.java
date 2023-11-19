import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class visitorsCRUD implements crudOperations<visitorsModel> {

    private Connection connection;

    public visitorsCRUD() {
        this.connection = dbConnection.get_connection();
    }

    @Override
    public List<visitorsModel> findAll() {
        String sql = "SELECT * FROM visitors;";
        List<visitorsModel> resultList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            while (result.next()) {
                resultList.add(new visitorsModel(
                        result.getInt("id"),
                        result.getString("visitor_name"),
                        result.getString("reference")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public List<visitorsModel> saveAll(List<visitorsModel> toSave) {
        String sql = "INSERT INTO visitors (visitor_name, reference) VALUES (?, ?);";
        try (PreparedStatement prepared = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (visitorsModel visitor : toSave) {
                prepared.setString(1, visitor.getVisitorName());
                prepared.setString(2, visitor.getVisitorReference());
                prepared.addBatch();
            }

            prepared.executeBatch();

            ResultSet generatedKeys = prepared.getGeneratedKeys();
            int i = 0;
            while (generatedKeys.next()) {
                // Mise à jour de l'ID directement dans l'objet sans utiliser setId
                toSave.get(i).id = generatedKeys.getInt(1);
                i++;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toSave;
    }

    @Override
    public visitorsModel save(visitorsModel toSave) {
        String sql = "INSERT INTO visitors (visitor_name, reference) VALUES (?, ?);";
        try (PreparedStatement prepared = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepared.setString(1, toSave.getVisitorName());
            prepared.setString(2, toSave.getVisitorReference());
            prepared.executeUpdate();

            ResultSet generatedKeys = prepared.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Mise à jour de l'ID directement dans l'objet sans utiliser setId
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
    public visitorsModel delete(visitorsModel toDelete) {
        String sql = "DELETE FROM visitors WHERE id = ?;";
        try (PreparedStatement prepared = connection.prepareStatement(sql)) {
            prepared.setInt(1, toDelete.getId());
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return toDelete;
    }
}
