package backend;

public class ControlPanel {
    private Apartment apartment; // Апартамент, свързан с панела за управление

    public ControlPanel(Apartment apartment) {
        this.apartment = apartment;
    }

    // Включва всички уреди в апартамента
    void turnOnAll() {
        for (Room room : apartment.getRooms()) {
            for (Appliance appliance : room.getAppliances()) {
                appliance.turnOn();
            }
        }
    }

    // Изключва всички уреди в апартамента
    void turnOffAll() {
        for (Room room : apartment.getRooms()) {
            for (Appliance appliance : room.getAppliances()) {
                appliance.turnOff();
            }
        }
    }

    // Включва всички уреди в дадена стая
    void turnRoomOn(String roomName) {
        for (Appliance appliance : apartment.findRoom(roomName).getAppliances()) {
            appliance.turnOn();
        }
    }

    // Изключва всички уреди в дадена стая
    void turnRoomOff(String roomName) {
        for (Appliance appliance : apartment.findRoom(roomName).getAppliances()) {
            appliance.turnOff();
        }
    }

    // Включва определен уред в дадена стая
    void turnApplianceON(String roomName, String applianceName) {
        apartment.findRoom(roomName).findAppliance(applianceName).turnOn();
    }

    // Изключва определен уред в дадена стая
    void turnApplianceOFF(String roomName, String applianceName) {
        apartment.findRoom(roomName).findAppliance(applianceName).turnOff();
    }

    // Връща текущото положение на всички уреди във всяка стая от апартамента
    String displayStatus() {
        String status = "| Apartment Status |\n";

        for (Room room : apartment.getRooms()) {
            String currentRoom = "- " + room.getName() + "\n";
            for (Appliance appliance : room.getAppliances()) {
                currentRoom += "* " + appliance.getName() + ": " + (appliance.isOn() ? "ON" : "OFF") + "\n";
            }
            status += currentRoom + "\n";
        }

        return status.trim();
    }

    // Включва всички уреди със custom дейности в целия апартамент
    public void performAllActions() {
        for (Room room : apartment.getRooms()) {
            for (Appliance appliance : room.getAppliances()) {
                appliance.performAction();
            }
        }
    }
}
