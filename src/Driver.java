import java.util.Scanner;

// Main class for the application
public class Driver {
    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Menu:");
            System.out.println("1. View information about all collectible cards for a sport");
            System.out.println("2. Get information about the number of registered collectible cards");
            System.out.println("3. View information about all collectible cards that are in mint condition");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter the sport (Fotball, Basketball, Baseball): ");
                    String sport = scanner.nextLine();
                    dbHandler.getCardsBySport(sport);
                    break;
                case 2:
                    dbHandler.getTotalNumberOfCards();
                    break;
                case 3:
                    dbHandler.getMintConditionCards();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }

        dbHandler.close();
        scanner.close();
    }
}