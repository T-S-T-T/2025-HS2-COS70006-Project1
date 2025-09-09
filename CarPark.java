import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Tung Tran <103432596>
 * @version JDK 21
 * Class COS70006 Tuesday 18:30
 */

/**
 * Manages a collection of ParkingSlot objects.
 * Provides operations to create, add, remove, list slots, and locate parked cars.
 */
public class CarPark {
    /**
     * Internal list of all parking slots in this car park.
     */
    private List<ParkingSlot> slots = new ArrayList<>();

    /**
     * Constructs a CarPark populated with the given number of staff-only
     * and visitor-only slots. Staff slots are labeled S01, S02, …;
     * visitor slots are labeled V01, V02, ….
     *
     * @param staffSlots   number of staff-only slots to create
     * @param visitorSlots number of visitor-only slots to create
     */
    public CarPark(int staffSlots, int visitorSlots) {
        for (int i = 1; i <= staffSlots; i++) {
            String id = String.format("S%02d", i);
            slots.add(new ParkingSlot(id, ParkingSlot.SlotType.STAFF));
        }
        for (int i = 1; i <= visitorSlots; i++) {
            String id = String.format("V%02d", i);
            slots.add(new ParkingSlot(id, ParkingSlot.SlotType.VISITOR));
        }
    }

    /**
     * Adds a new parking slot if no existing slot has the same ID.
     *
     * @param slot the ParkingSlot to add
     * @return true if the slot was added; false if a slot with the same ID already exists
     */
    public boolean addSlot(ParkingSlot slot) {
        if (findSlotById(slot.getId()) != null) {
            return false;
        }
        slots.add(slot);
        return true;
    }

    /**
     * Deletes an unoccupied slot identified by its ID.
     *
     * @param id the identifier of the slot to delete
     * @return true if the slot was found and deleted; false if not found or if occupied
     */
    public boolean deleteSlot(String id) {
        ParkingSlot slot = findSlotById(id);
        if (slot == null || slot.isOccupied()) {
            return false;
        }
        return slots.remove(slot);
    }

    /**
     * Returns a snapshot list of all parking slots in this car park.
     *
     * @return a new List containing all ParkingSlot objects
     */
    public List<ParkingSlot> getAllSlots() {
        return new ArrayList<>(slots);
    }

    /**
     * Deletes every slot that is currently unoccupied.
     *
     * @return true if at least one unoccupied slot was deleted; false otherwise
     */
    public boolean deleteAllUnoccupied() {
        boolean removedAny = false;
        Iterator<ParkingSlot> it = slots.iterator();
        while (it.hasNext()) {
            if (!it.next().isOccupied()) {
                it.remove();
                removedAny = true;
            }
        }
        return removedAny;
    }

    /**
     * Finds a parking slot by its unique identifier.
     *
     * @param id the slot ID to search for
     * @return the ParkingSlot with the matching ID, or null if none exists
     */
    public ParkingSlot findSlotById(String id) {
        for (ParkingSlot slot : slots) {
            if (slot.getId().equals(id)) {
                return slot;
            }
        }
        return null;
    }

    /**
     * Searches all slots for a parked car with the given registration number.
     *
     * @param registrationNumber the registration number of the car to find
     * @return the ParkingSlot where the car is parked, or null if the car is not in any slot
     */
    public ParkingSlot findCar(String registrationNumber) {
        for (ParkingSlot slot : slots) {
            if (slot.isOccupied()
                && slot.getParkedCar().getRegistrationNumber().equals(registrationNumber)) {
                return slot;
            }
        }
        return null;
    }
}