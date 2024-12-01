package backend.java;
import java.util.ArrayList;

public class Room {
    private String name; // Име на стаята
    private ArrayList<Appliance> appliances; // Списък с уредите в стаята

    public Room(String name) {
        this.name = name;
        this.appliances = new ArrayList<>();
    }

    // Метод за добавяне на уред в стаята
    void addAppliance(Appliance appliance) {
        this.appliances.add(appliance);
    }

    // Връща текущото положение на всички уреди в стаята
    String getApplianceStatus() {
        String status = "- " + this.name + "\n";

        for (Appliance appliance : appliances) {
            status += "* " + appliance.getName() + ": " + (appliance.isOn() ? "ON" : "OFF") + "\n";
        }

        return status.trim();
    }

    // Връща името на стаята
    String getName() {
        return this.name;
    }

    // Намира уред по име
    Appliance findAppliance(String name) {
        Appliance foundAppliance = null;
        for (Appliance appliance : appliances) {
            if (appliance.getName().equals(name)) {
                foundAppliance = appliance;
            }
        }
        return foundAppliance;
    }

    // Връща списък с всички уреди
    ArrayList<Appliance> getAppliances() {
        return this.appliances;
    }
}
