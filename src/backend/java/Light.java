package backend.java;

public class Light extends Appliance {
    public Light(String name) {
        super(name);
    }

    public void performAction() {
        if (isOn()) {
            System.out.println(getName() + " is on.");
        } else {
            System.out.println(getName() + " is off.");
        }
    }
}
