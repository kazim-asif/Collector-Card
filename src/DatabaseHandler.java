import java.sql.*;
import java.util.Scanner;

// Class for handling the database operations
class DatabaseHandler {
    private Connection connection;

    public DatabaseHandler() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetches all cards for a given sport
    public void getCardsBySport(String sport) {
        String query = "";
        switch (sport.toLowerCase()) {
            case "fotball":
                query = "SELECT * FROM Fotballkort f JOIN SamlerkortSerie s ON f.Serie = s.id WHERE s.Sport = 'Fotball'";
                break;
            case "basketball":
                query = "SELECT * FROM Basketballkort b JOIN SamlerkortSerie s ON b.Serie = s.id WHERE s.Sport = 'Basketball'";
                break;
            case "baseball":
                query = "SELECT * FROM Baseballkort bb JOIN SamlerkortSerie s ON bb.Serie = s.id WHERE s.Sport = 'Baseball'";
                break;
            default:
                System.out.println("Invalid sport!");
                return;
        }
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int serie = rs.getInt("Serie");
                String tilstand = rs.getString("Tilstand");
                String spillernavn = rs.getString("Spillernavn");
                String klubb = rs.getString("Klubb");
                int sesonger = rs.getInt("Sesonger");
                int kamper = rs.getInt("Kamper");

                System.out.println("ID: " + id + ", Serie: " + serie + ", Tilstand: " + tilstand +
                                   ", Spillernavn: " + spillernavn + ", Klubb: " + klubb +
                                   ", Sesonger: " + sesonger + ", Kamper: " + kamper);

                if (sport.equalsIgnoreCase("fotball")) {
                    int seriescoringer = rs.getInt("Seriescoringer");
                    int cupscoringer = rs.getInt("Cupscoringer");
                    System.out.println("Seriescoringer: " + seriescoringer + ", Cupscoringer: " + cupscoringer);
                } else if (sport.equalsIgnoreCase("basketball")) {
                    int fgPercent = rs.getInt("FGPercent");
                    int ftPercent = rs.getInt("FTPercent");
                    double poengsnitt = rs.getDouble("Poengsnitt");
                    System.out.println("FG Percent: " + fgPercent + ", FT Percent: " + ftPercent + ", Poengsnitt: " + poengsnitt);
                } else if (sport.equalsIgnoreCase("baseball")) {
                    int homeruns = rs.getInt("Homeruns");
                    System.out.println("Homeruns: " + homeruns);
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Gets the total number of collectible cards
    public void getTotalNumberOfCards() {
        String query = "SELECT COUNT(*) AS total FROM (" +
                       "SELECT id FROM Fotballkort " +
                       "UNION ALL " +
                       "SELECT id FROM Basketballkort " +
                       "UNION ALL " +
                       "SELECT id FROM Baseballkort) AS total_cards";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("Total number of registered collectible cards: " + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetches all cards that are in mint condition
    public void getMintConditionCards() {
        String query = "SELECT Sport, id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper, Extra1, Extra2, Extra3 " +
                       "FROM (" +
                       "SELECT 'Fotball' AS Sport, id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper, " +
                       "Seriescoringer AS Extra1, Cupscoringer AS Extra2, NULL AS Extra3 " +
                       "FROM Fotballkort WHERE Tilstand = 'Mint' " +
                       "UNION ALL " +
                       "SELECT 'Basketball' AS Sport, id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper, " +
                       "FGPercent AS Extra1, FTPercent AS Extra2, Poengsnitt AS Extra3 " +
                       "FROM Basketballkort WHERE Tilstand = 'Mint' " +
                       "UNION ALL " +
                       "SELECT 'Baseball' AS Sport, id, Serie, Tilstand, Spillernavn, Klubb, Sesonger, Kamper, " +
                       "Homeruns AS Extra1, NULL AS Extra2, NULL AS Extra3 " +
                       "FROM Baseballkort WHERE Tilstand = 'Mint') AS mint_cards";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            int mintCount = 0;
            while (rs.next()) {
                String sport = rs.getString("Sport");
                int id = rs.getInt("id");
                int serie = rs.getInt("Serie");
                String tilstand = rs.getString("Tilstand");
                String spillernavn = rs.getString("Spillernavn");
                String klubb = rs.getString("Klubb");
                int sesonger = rs.getInt("Sesonger");
                int kamper = rs.getInt("Kamper");
                int extra1 = rs.getInt("Extra1");
                Integer extra2 = (Integer) rs.getObject("Extra2");  // Use Integer to handle possible NULL values
                Double extra3 = (Double) rs.getObject("Extra3");  // Use Double to handle possible NULL values

                System.out.println("Sport: " + sport + ", ID: " + id + ", Serie: " + serie + ", Tilstand: " + tilstand +
                                   ", Spillernavn: " + spillernavn + ", Klubb: " + klubb +
                                   ", Sesonger: " + sesonger + ", Kamper: " + kamper);

                if (sport.equals("Fotball")) {
                    System.out.println("Seriescoringer: " + extra1 + ", Cupscoringer: " + extra2);
                } else if (sport.equals("Basketball")) {
                    System.out.println("FG Percent: " + extra1 + ", FT Percent: " + extra2 + ", Poengsnitt: " + extra3);
                } else if (sport.equals("Baseball")) {
                    System.out.println("Homeruns: " + extra1);
                }
                System.out.println();
                mintCount++;
            }
            System.out.println("Total number of mint condition cards: " + mintCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Close the database connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
