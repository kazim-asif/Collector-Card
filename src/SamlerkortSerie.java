import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SamlerkortSerie {
    private int id;
    private String utgiver;
    private int utgitt;
    private String sport;
    private int antall;

    public SamlerkortSerie(int id, String utgiver, int utgitt, String sport, int antall) {
        this.id = id;
        this.utgiver = utgiver;
        this.utgitt = utgitt;
        this.sport = sport;
        this.antall = antall;
    }

    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO SamlerkortSerie (id, Utgiver, Utgitt, Sport, Antall) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setString(2, utgiver);
                stmt.setInt(3, utgitt);
                stmt.setString(4, sport);
                stmt.setInt(5, antall);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters...
}
