import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Basketballkort extends Samlerkort {
    private int fgPercent;
    private int ftPercent;
    private double poengsnitt;

    public Basketballkort(int id, int serie, String tilstand, String spillernavn, String klubb, int sesonger, int kamper, int fgPercent, int ftPercent, double poengsnitt) {
        super(id, serie, tilstand, spillernavn, klubb, sesonger, kamper);
        this.fgPercent = fgPercent;
        this.ftPercent = ftPercent;
        this.poengsnitt = poengsnitt;
    }
    

    public int getFgPercent() {
		return fgPercent;
	}


	public void setFgPercent(int fgPercent) {
		this.fgPercent = fgPercent;
	}


	public int getFtPercent() {
		return ftPercent;
	}


	public void setFtPercent(int ftPercent) {
		this.ftPercent = ftPercent;
	}


	public double getPoengsnitt() {
		return poengsnitt;
	}


	public void setPoengsnitt(double poengsnitt) {
		this.poengsnitt = poengsnitt;
	}


	@Override
    public void saveToDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Basketballkort (id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper, FGPercent, FTPercent, Poengsnitt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                stmt.setInt(2, serie);
                stmt.setString(3, tilstand);
                stmt.setString(4, spillernavn);
                stmt.setString(5, klubb);
                stmt.setInt(6, sesonger);
                stmt.setInt(7, kamper);
                stmt.setInt(8, fgPercent);
                stmt.setInt(9, ftPercent);
                stmt.setDouble(10, poengsnitt);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
