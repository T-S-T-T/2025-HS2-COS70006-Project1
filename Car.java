import java.time.LocalDateTime;

/**
 * Represents a car that can be parked in a parking slot.
 * Each Car has a registration number, an owner name,
 * a flag indicating staff status, and a timestamp of when it was parked.
 */
public class Car {
    /**
     * The car's registration number, matching pattern [A-Z][0-9]{4}.
     */
    private String registrationNumber;

    /**
     * The name of the car's owner.
     */
    private String owner;

    /**
     * True if the owner is a staff member; false for a visitor.
     */
    private boolean staffOwner;

    /**
     * Timestamp when the car was parked.
     */
    private LocalDateTime parkTime;

    /**
     * Constructs a Car with the given registration number, owner, and staff status.
     *
     * @param registrationNumber the car's registration number; must match [A-Z][0-9]{4}
     * @param owner              the owner's name
     * @param staffOwner         true if the owner is a staff member
     * @throws IllegalArgumentException if registrationNumber is not a capital letter followed by four digits
     */
    public Car(String registrationNumber, String owner, boolean staffOwner) {
        if (!registrationNumber.matches("[A-Z][0-9]{4}")) {
            throw new IllegalArgumentException(
                "Registration must be a capital letter followed by four digits.");
        }
        this.registrationNumber = registrationNumber;
        this.owner = owner;
        this.staffOwner = staffOwner;
    }

    /**
     * Returns the car's registration number.
     *
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Returns the owner's name.
     *
     * @return the owner's name
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Indicates whether the car's owner is a staff member.
     *
     * @return true if staff owner; false otherwise
     */
    public boolean isStaffOwner() {
        return staffOwner;
    }

    /**
     * Returns the timestamp when the car was parked.
     *
     * @return the park time, or null if not yet parked
     */
    public LocalDateTime getParkTime() {
        return parkTime;
    }

    /**
     * Records the time at which the car was parked.
     *
     * @param parkTime the LocalDateTime when parking occurred
     */
    public void setParkTime(LocalDateTime parkTime) {
        this.parkTime = parkTime;
    }
}