import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

/**
 * @author Tung Tran <103432596>
 * @version JDK 21
 * Class COS70006 Tuesday 18:30
 */

/**
 * Console-based application to manage a car park.
 * Handles user input/output and delegates business logic to CarPark,
 * ParkingSlot, and Car classes.
 */
public class Application {
    /**
     * Formatter for date-time display (yyyy-MM-dd HH:mm:ss).
     */
    private static final DateTimeFormatter DATE_TIME_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * The CarPark instance that stores parking slots.
     */
    private CarPark carPark;

    /**
     * Scanner for reading console input.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Program entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Application app = new Application();
        app.initCarPark();
        app.runMenuLoop();
    }

    /**
     * Initializes the CarPark by prompting the user for the number
     * of staff and visitor slots, creates the slots, and lists them.
     */
    private void initCarPark() {
        System.out.println("=== Initialize Car Park ===");
        int staffCount = readNonNegativeInt("Enter number of staff slots: ");
        int visitorCount = readNonNegativeInt("Enter number of visitor slots: ");
        carPark = new CarPark(staffCount, visitorCount);
        System.out.println("Car park created with " + staffCount +
            " staff slots and " + visitorCount + " visitor slots.\n");
        System.out.println("Current parking slots and status:");
        listAllSlots();
        System.out.println();
    }

    /**
     * Displays the main menu and processes user choices
     * until the user selects the exit option.
     */
    private void runMenuLoop() {
        while (true) {
            printMenu();
            int choice = readNonNegativeInt("Select option: ");
            switch (choice) {
                case 1: addParkingSlot(); break;
                case 2: deleteParkingSlot(); break;
                case 3: listAllSlots(); break;
                case 4: deleteAllUnoccupied(); break;
                case 5: parkCar(); break;
                case 6: findCar(); break;
                case 7: removeCar(); break;
                case 8:
                    System.out.println("Program end!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select 1 to 8.");
            }
        }
    }

    /**
     * Prints the text menu of available operations.
     */
    private void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Add a parking slot");
        System.out.println("2. Delete a parking slot");
        System.out.println("3. List all slots");
        System.out.println("4. Delete all unoccupied slots");
        System.out.println("5. Park a car");
        System.out.println("6. Find a car");
        System.out.println("7. Remove a car");
        System.out.println("8. Exit");
    }

    /**
     * Prompts the user with the given message and reads a non-negative integer.
     * Reprompts on invalid input.
     *
     * @param prompt the message to display
     * @return a non-negative integer entered by the user
     */
    private int readNonNegativeInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int n = Integer.parseInt(line);
                if (n >= 0) {
                    return n;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter a non-negative integer.");
        }
    }

    /**
     * Prompts for a parking slot ID and adds it to the CarPark.
     * Slot type is chosen by entering 1 for staff or 0 for visitor.
     * Reports success or failure.
     */
    private void addParkingSlot() {
        System.out.println("--- Add Parking Slot ---");
        String id = readSlotId("Enter slot ID (e.g. S01): ");
        int code = readSlotTypeCode("Enter slot type (1 = staff, 0 = visitor): ");
        ParkingSlot.SlotType type =
            (code == 1) ? ParkingSlot.SlotType.STAFF : ParkingSlot.SlotType.VISITOR;
        boolean success = carPark.addSlot(new ParkingSlot(id, type));
        System.out.println(success
            ? "Slot added successfully."
            : "Failed to add slot. It may already exist.");
    }

    /**
     * Prompts for a slot ID and deletes that slot if it exists and is unoccupied.
     * Reports success or failure.
     */
    private void deleteParkingSlot() {
        System.out.println("--- Delete Parking Slot ---");
        String id = readSlotId("Enter slot ID to delete: ");
        boolean success = carPark.deleteSlot(id);
        System.out.println(success
            ? "Slot deleted."
            : "Cannot delete slot. It may not exist or is occupied.");
    }

    /**
     * Retrieves and displays all parking slots in a tabular format.
     * Includes slot ID, type, occupancy, registration, owner, park time, and fee.
     */
    private void listAllSlots() {
        System.out.println("--- List All Slots ---");
        List<ParkingSlot> slots = carPark.getAllSlots();
        if (slots.isEmpty()) {
            System.out.println("No slots in the car park.");
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        System.out.printf("%-5s %-7s %-9s %-10s %-8s %-20s %-6s%n",
            "ID", "Type", "Occupied", "RegNum", "Owner", "ParkTime", "Fee");
        for (ParkingSlot slot : slots) {
            String occupied = slot.isOccupied() ? "Yes" : "No";
            String reg = "-", owner = "-", bookTime = "-", fee = "-";
            if (slot.isOccupied()) {
                Car car = slot.getParkedCar();
                reg = car.getRegistrationNumber();
                owner = car.getOwner();
                bookTime = car.getParkTime().format(DATE_TIME_FORMAT);
                Duration d = Duration.between(car.getParkTime(), now);
                long h = d.toHours();
                long m = d.toMinutes() - h * 60;
                long s = d.getSeconds() - d.toMinutes() * 60;
                long charged = h + ((m > 0 || s > 0) ? 1 : 0);
                fee = String.format("%dh %dm %ds $%d", h, m, s, charged * 6);
            }
            System.out.printf("%-5s %-7s %-9s %-10s %-8s %-20s %-6s%n",
                slot.getId(),
                slot.getType().name().toLowerCase(),
                occupied, reg, owner, bookTime, fee);
        }
    }

    /**
     * Deletes all unoccupied parking slots from the CarPark.
     * Reports whether any slots were deleted.
     */
    private void deleteAllUnoccupied() {
        System.out.println("--- Delete All Unoccupied Slots ---");
        boolean any = carPark.deleteAllUnoccupied();
        System.out.println(any
            ? "All unoccupied slots deleted."
            : "No unoccupied slots to delete.");
    }

    /**
     * Prompts for slot ID and car details to park a car.
     * Validates slot existence, occupancy, registration format, and owner/slot type match.
     * Records and displays park timestamp.
     */
    private void parkCar() {
        System.out.println("--- Park a Car ---");
        String slotId = readSlotId("Enter slot ID: ");
        ParkingSlot slot = carPark.findSlotById(slotId);
        if (slot == null) {
            System.out.println("Slot not found.");
            return;
        }
        if (slot.isOccupied()) {
            System.out.println("Slot is already occupied.");
            return;
        }
        String reg = readRegistration("Enter car registration (e.g. T1234): ");
        System.out.print("Enter owner name: ");
        String owner = scanner.nextLine().trim();
        System.out.print("Is owner staff? (yes/no): ");
        boolean isStaff = "yes".equalsIgnoreCase(scanner.nextLine().trim());
        if (isStaff && slot.getType() != ParkingSlot.SlotType.STAFF ||
            !isStaff && slot.getType() != ParkingSlot.SlotType.VISITOR) {
            System.out.println("Car type doesn't match slot type.");
            return;
        }
        Car car = new Car(reg, owner, isStaff);
        car.setParkTime(LocalDateTime.now());
        slot.parkCar(car);
        System.out.println("Car parked at " +
            car.getParkTime().format(DATE_TIME_FORMAT));
    }

    /**
     * Prompts for a registration number, finds the parked car, and
     * displays its slot, owner, duration parked, and fee.
     * If not found, reports accordingly.
     */
    private void findCar() {
        System.out.println("--- Find a Car ---");
        System.out.print("Enter registration number: ");
        String reg = scanner.nextLine().trim();
        ParkingSlot slot = carPark.findCar(reg);
        if (slot == null) {
            System.out.println("Car not found.");
            return;
        }
        Car car = slot.getParkedCar();
        System.out.println("Found in slot " + slot.getId() +
            ", owner: " + car.getOwner());
        Duration d = Duration.between(car.getParkTime(), LocalDateTime.now());
        long h = d.toHours();
        long m = d.toMinutes() - h * 60;
        long s = d.getSeconds() - d.toMinutes() * 60;
        long charged = h + ((m > 0 || s > 0) ? 1 : 0);
        System.out.printf("Parked for %dh %dm %ds, Fee: $%d%n",
            h, m, s, charged * 6);
    }

    /**
     * Prompts for a registration number and removes the corresponding car
     * from its parking slot. Reports success or not found.
     */
    private void removeCar() {
        System.out.println("--- Remove a Car ---");
        System.out.print("Enter registration number: ");
        String reg = scanner.nextLine().trim();
        ParkingSlot slot = carPark.findCar(reg);
        if (slot == null) {
            System.out.println("Car not found.");
            return;
        }
        slot.removeCar();
        System.out.println("Car removed from slot " + slot.getId());
    }

    /**
     * Reads a slot ID from the user, ensuring it matches the pattern [A-Z][0-9]{2}.
     * Reprompts on invalid format.
     *
     * @param prompt message to display
     * @return the validated slot ID
     */
    private String readSlotId(String prompt) {
        while (true) {
            System.out.print(prompt);
            String id = scanner.nextLine().trim();
            if (id.matches("[A-Z][0-9]{2}")) {
                return id;
            }
            System.out.println(
                "Incorrect format, please enter the correct slot format e.g. S01");
        }
    }

    /**
     * Reads a car registration number from the user, ensuring it matches
     * the pattern [A-Z][0-9]{4}. Reprompts on invalid format.
     *
     * @param prompt message to display
     * @return the validated registration number
     */
    private String readRegistration(String prompt) {
        while (true) {
            System.out.print(prompt);
            String reg = scanner.nextLine().trim();
            if (reg.matches("[A-Z][0-9]{4}")) {
                return reg;
            }
            System.out.println(
                "Incorrect format, please enter the correct registration format e.g. T1234");
        }
    }

    /**
     * Reads a slot type code from the user: 1 for staff, 0 for visitor.
     * Reprompts on invalid code.
     *
     * @param prompt message to display
     * @return 1 if staff, 0 if visitor
     */
    private int readSlotTypeCode(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if ("1".equals(line) || "0".equals(line)) {
                return Integer.parseInt(line);
            }
            System.out.println("Incorrect value, please enter 1 for staff or 0 for visitor.");
        }
    }
}