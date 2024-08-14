import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Samlerkort {
    protected int id;
    protected int serie;
    protected String tilstand;
    protected String spillernavn;
    protected String klubb;
    protected int sesonger;
    protected int kamper;

    public Samlerkort(int id, int serie, String tilstand, String spillernavn, String klubb, int sesonger, int kamper) {
        this.id = id;
        this.serie = serie;
        this.tilstand = tilstand;
        this.spillernavn = spillernavn;
        this.klubb = klubb;
        this.sesonger = sesonger;
        this.kamper = kamper;
    }

    public void saveToDatabase() {
        // This method should be overridden by subclasses
    }
}
