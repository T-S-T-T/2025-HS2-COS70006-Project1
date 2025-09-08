/**
 * Represents a parking slot, which may be for staff or visitors,
 * and can hold at most one Car.
 */
public class ParkingSlot {
    /**
     * Enum for slot types.
     */
    public enum SlotType {
        STAFF, VISITOR
    }

    private String id;
    private SlotType type;
    private Car parkedCar;

    /**
     * Creates a ParkingSlot with an ID and type.
     * ID must match [A-Z][0-9]{2}.
     *
     * @param id   slot identifier
     * @param type slot type STAFF or VISITOR
     * @throws IllegalArgumentException if ID is invalid
     */
    public ParkingSlot(String id, SlotType type) {
        if (!id.matches("[A-Z][0-9]{2}")) {
            throw new IllegalArgumentException(
                "Slot ID must be a capital letter followed by two digits.");
        }
        this.id = id;
        this.type = type;
        this.parkedCar = null;
    }

    public String getId() {
        return id;
    }

    public SlotType getType() {
        return type;
    }

    /**
     * @return true if there is a Car parked in this slot.
     */
    public boolean isOccupied() {
        return parkedCar != null;
    }

    /**
     * @return the Car parked here, or null if empty.
     */
    public Car getParkedCar() {
        return parkedCar;
    }

    /**
     * Parks a Car in this slot.
     *
     * @param car the Car to park
     * @throws IllegalStateException if already occupied
     */
    public void parkCar(Car car) {
        if (isOccupied()) {
            throw new IllegalStateException("Slot is already occupied.");
        }
        this.parkedCar = car;
    }

    /**
     * Removes the parked Car from this slot.
     *
     * @throws IllegalStateException if slot is empty
     */
    public void removeCar() {
        if (!isOccupied()) {
            throw new IllegalStateException("Slot is empty.");
        }
        this.parkedCar = null;
    }
}