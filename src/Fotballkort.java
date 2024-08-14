import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Fotballkort extends Samlerkort {
    private int seriescoringer;
    private int cupscoringer;

    public Fotballkort(int id, int serie, String tilstand, String spillernavn, String klubb, int sesonger, int kamper, int seriescoringer, int cupscoringer) {
        super(id, serie, tilstand, spillernavn, klubb, sesonger, kamper);
        this.seriescoringer = seriescoringer;
        this.cupscoringer = cupscoringer;
    }
    
    
    public int getSeriescoringer() {
		return seriescoringer;
	}

	public void setSeriescoringer(int seriescoringer) {
		this.seriescoringer = seriescoringer;
	}

	public int getCupscoringer() {
		return cupscoringer;
	}

	public void setCupscoringer(int cupscoringer) {
		this.cupscoringer = cupscoringer;
	}


	@Override
    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Fotballkort (id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper, Seriescoringer, Cupscoringer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setInt(2, serie);
                stmt.setString(3, tilstand);
                stmt.setString(4, spillernavn);
                stmt.setString(5, klubb);
                stmt.setInt(6, sesonger);
                stmt.setInt(7, kamper);
                stmt.setInt(8, seriescoringer);
                stmt.setInt(9, cupscoringer);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
