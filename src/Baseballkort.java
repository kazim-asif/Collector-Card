import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Baseballkort extends Samlerkort {
    private int homeruns;

    public Baseballkort(int id, int serie, String tilstand, String spillernavn, String klubb, int sesonger, int kamper, int homeruns) {
        super(id, serie, tilstand, spillernavn, klubb, sesonger, kamper);
        this.homeruns = homeruns;
    }

    public int getHomeruns() {
		return homeruns;
	}

	public void setHomeruns(int homeruns) {
		this.homeruns = homeruns;
	}

	@Override
    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Baseballkort (id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper, Homeruns) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setInt(2, serie);
                stmt.setString(3, tilstand);
                stmt.setString(4, spillernavn);
                stmt.setString(5, klubb);
                stmt.setInt(6, sesonger);
                stmt.setInt(7, kamper);
                stmt.setInt(8, homeruns);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
