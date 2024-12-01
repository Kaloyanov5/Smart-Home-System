package backend.java;

public class Heater extends Appliance {
    private int targetTemperature;
    private int currentTemperature;

    public Heater(String name, int targetTemperature) {
        super(name);
        this.targetTemperature = targetTemperature;
        this.currentTemperature = 20; // Начална температура
    }

    public void performAction() {
        if (isOn()) {
            if (currentTemperature < targetTemperature) {
                currentTemperature = targetTemperature;
                System.out.println(getName() + " is heating. Current temp: " + currentTemperature + " celsius.");
            } else {
                System.out.println(getName() + " reached target temp: " + targetTemperature + " celsius.");
            }
        } else {
            System.out.println(getName() + " is off.");
        }
    }

    // Променя targetTemperature
    public void setTargetTemperature(int targetTemperature) {
        this.targetTemperature = targetTemperature;
        System.out.println(getName() + " target temp is changed to " + targetTemperature + " celsius.");
    }
}
