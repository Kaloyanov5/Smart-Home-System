package backend.java;

public class Fan extends Appliance {
    public Fan(String name) {
        super(name);
    }

    public void performAction() {
        if (isOn()) {
            System.out.println(getName() + " is spinning.");
        } else {
            System.out.println(getName() + " is off.");
        }
    }
}
