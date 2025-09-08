/**
 * Represents a parking slot in the car park.
 * Each slot has a unique identifier, a type (staff or visitor),
 * and may hold at most one Car at a time.
 */
public class ParkingSlot {
    /**
     * Types of parking slots.
     * STAFF slots are reserved for staff members.
     * VISITOR slots are reserved for visitors.
     */
    public enum SlotType {
        /** A slot designated for staff members. */
        STAFF,
        /** A slot designated for visitors. */
        VISITOR
    }

    /**
     * Unique identifier for this slot, matching pattern [A-Z][0-9]{2}.
     */
    private String id;

    /**
     * The type of this slot (STAFF or VISITOR).
     */
    private SlotType type;

    /**
     * The Car currently parked in this slot, or {@code null} if empty.
     */
    private Car parkedCar;

    /**
     * Constructs a ParkingSlot with the specified ID and type.
     * The ID must consist of an uppercase letter followed by two digits.
     *
     * @param id   the slot identifier (e.g. "A01")
     * @param type the slot type, either {@link SlotType#STAFF} or {@link SlotType#VISITOR}
     * @throws IllegalArgumentException if {@code id} does not match [A-Z][0-9]{2}
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

    /**
     * Returns the unique identifier of this slot.
     *
     * @return the slot ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the type of this slot.
     *
     * @return the slot's {@link SlotType}
     */
    public SlotType getType() {
        return type;
    }

    /**
     * Indicates whether this slot is currently occupied by a car.
     *
     * @return {@code true} if a car is parked here; {@code false} otherwise
     */
    public boolean isOccupied() {
        return parkedCar != null;
    }

    /**
     * Returns the Car parked in this slot.
     *
     * @return the parked {@link Car}, or {@code null} if the slot is empty
     */
    public Car getParkedCar() {
        return parkedCar;
    }

    /**
     * Parks the specified Car in this slot.
     *
     * @param car the {@link Car} to park
     * @throws IllegalStateException if this slot is already occupied
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
     * @throws IllegalStateException if this slot is empty
     */
    public void removeCar() {
        if (!isOccupied()) {
            throw new IllegalStateException("Slot is empty.");
        }
        this.parkedCar = null;
    }
}