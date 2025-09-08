import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages a collection of ParkingSlot objects.
 * Can add/remove slots, find slots or cars, and list slots.
 */
public class CarPark {
    private List<ParkingSlot> slots = new ArrayList<>();

    /**
     * Constructs a CarPark with a given number of staff and visitor slots.
     * Staff slots are labeled S01… and visitor slots V01….
     *
     * @param staffSlots   number of staff-only slots
     * @param visitorSlots number of visitor-only slots
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
     * Adds a new slot if its ID is unique.
     *
     * @param slot the ParkingSlot to add
     * @return true if added, false if a slot with same ID exists
     */
    public boolean addSlot(ParkingSlot slot) {
        if (findSlotById(slot.getId()) != null) {
            return false;
        }
        slots.add(slot);
        return true;
    }

    /**
     * Removes an unoccupied slot by ID.
     *
     * @param id the slot ID to delete
     * @return true if removed, false if not found or occupied
     */
    public boolean deleteSlot(String id) {
        ParkingSlot slot = findSlotById(id);
        if (slot == null || slot.isOccupied()) {
            return false;
        }
        return slots.remove(slot);
    }

    /**
     * Retrieves all slots.
     *
     * @return list of all ParkingSlot objects
     */
    public List<ParkingSlot> getAllSlots() {
        return new ArrayList<>(slots);
    }

    /**
     * Deletes all unoccupied slots.
     *
     * @return true if at least one slot was deleted
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
     * Finds a slot by its ID.
     *
     * @param id slot identifier
     * @return ParkingSlot or null if not found
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
     * Finds which slot a car is parked in by its registration.
     *
     * @param registrationNumber car registration
     * @return ParkingSlot containing the car, or null if none
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