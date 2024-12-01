package backend.java;
import java.util.ArrayList;

public class Apartment {
    private String name; // Име на апартамента
    private ArrayList<Room> rooms; // Списък със стаите в апартамента
    private ControlPanel controlPanel; // Контролен панел за управление

    public Apartment(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        // Свързваме контролния панел с апартамента
        this.controlPanel = new ControlPanel(this);
    }

    // Метод за добавяне на стая в апартамента
    void addRoom(Room room) {
        this.rooms.add(room);
    }

    // Връща текущото положение на всички стаи и уреди в апартамента
    String getStatus() {
        String status = "| Apartment Status |\n";

        for (Room room : rooms) {
            String currentRoom = "- " + room.getName() + "\n";
            for (Appliance appliance : room.getAppliances()) {
                currentRoom += "* " + appliance.getName() + ": " + (appliance.isOn() ? "ON" : "OFF") + "\n";
            }
            status += currentRoom + "\n";
        }

        return status.trim();
    }

    // Връща контролния панел на апартамента
    ControlPanel getControlPanel() {
        return this.controlPanel;
    }

    // Търси стая по име
    Room findRoom(String roomName) {
        Room foundRoom = null;
        for (Room room : rooms) {
            if (room.getName().equals(roomName)) {
                foundRoom = room;
            }
        }
        return foundRoom;
    }

    // Връша списък с всички стаи
    ArrayList<Room> getRooms() {
        return this.rooms;
    }
}