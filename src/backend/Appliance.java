package backend;

public class Appliance {
    private String name; // Име на уреда
    private boolean powerStatus; // Текущо положение на уреда (включен/изключен)

    public Appliance(String name) {
        this.name = name;
        this.powerStatus = false; // Уредът при създаване е изключен
    }

    // Метод за включване на уреда
    void turnOn() {
        if (this.powerStatus) { return; }
        this.powerStatus = true;
    }

    // за изключване на уреда
    void turnOff() {
        if (!this.powerStatus) { return; }
        this.powerStatus = false;
    }

    // Проверява текущото положение на уреда
    boolean isOn() {
        return this.powerStatus;
    }

    // Връща името на уреда
    String getName() {
        return this.name;
    }

    public void performAction() {}
}
