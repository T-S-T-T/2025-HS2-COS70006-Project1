import java.time.LocalDateTime;

/**
 * Represents a car with registration, owner info,
 * staff status, and park timestamp.
 */
public class Car {
    private String registrationNumber;
    private String owner;
    private boolean staffOwner;
    private LocalDateTime parkTime;

    /**
     * Constructs a Car.
     * Registration must match [A-Z][0-9]{4}.
     *
     * @param registrationNumber unique registration
     * @param owner              owner name
     * @param staffOwner         true if owner is staff
     * @throws IllegalArgumentException if registration invalid
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isStaffOwner() {
        return staffOwner;
    }

    public LocalDateTime getParkTime() {
        return parkTime;
    }

    /**
     * Records the time when the car was parked.
     *
     * @param parkTime timestamp of parking
     */
    public void setParkTime(LocalDateTime parkTime) {
        this.parkTime = parkTime;
    }
}