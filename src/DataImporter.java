import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataImporter {
    
    public static void readAndInsertData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read series data
            while ((line = br.readLine()) != null && !line.equals("-------")) {
                if (line.startsWith("Samlerkortserier:")) {
                	br.readLine();
                    continue; // Skip the header and count
                }
                // Read series details
                int id = Integer.parseInt(line);
                String utgiver = br.readLine();
                int utgitt = Integer.parseInt(br.readLine());
                String sport = br.readLine();
                int antall = Integer.parseInt(br.readLine());

                SamlerkortSerie series = new SamlerkortSerie(id, utgiver, utgitt, sport, antall);
                series.saveToDatabase();
            }

            // Read card data
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Kort:")) {
                	br.readLine();
                    continue; // Skip the header and count
                }
                if (line.equals("-------")) {
                    continue; // Skip the delimiter
                }
                // Read card details
                int id = Integer.parseInt(line);
                int serie = Integer.parseInt(br.readLine());
                String tilstand = br.readLine();
                String spillernavn = br.readLine();
                String klubb = br.readLine();
                int sesonger = Integer.parseInt(br.readLine());
                int kamper = Integer.parseInt(br.readLine());
                String sport = br.readLine();

                switch (sport) {
                    case "Fotball":
                        int seriescoringer = Integer.parseInt(br.readLine());
                        int cupscoringer = Integer.parseInt(br.readLine());
                        Fotballkort fotballkort = new Fotballkort(id, serie, tilstand, spillernavn, klubb, sesonger, kamper, seriescoringer, cupscoringer);
                        fotballkort.saveToDatabase();
                        break;
                    case "Basketball":
                        int fgPercent = Integer.parseInt(br.readLine());
                        int ftPercent = Integer.parseInt(br.readLine());
                        double poengsnitt = Double.parseDouble(br.readLine());
                        Basketballkort basketballkort = new Basketballkort(id, serie, tilstand, spillernavn, klubb, sesonger, kamper, fgPercent, ftPercent, poengsnitt);
                        basketballkort.saveToDatabase();
                        break;
                    case "Baseball":
                        int homeruns = Integer.parseInt(br.readLine());
                        Baseballkort baseballkort = new Baseballkort(id, serie, tilstand, spillernavn, klubb, sesonger, kamper, homeruns);
                        baseballkort.saveToDatabase();
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
