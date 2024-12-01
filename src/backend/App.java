package backend;

public class App {
    public static void main(String[] args) {
        /// Konzola 1 (Тук изпозлвам обектите, които са по пожелание)
        /* Създаваме апартамент
        Apartment myApartment = new Apartment("My Cozy Apartment");

        // Създаваме стаи
        Room livingRoom = new Room("Living Room");
        Room bedroom = new Room("Bedroom");

        myApartment.addRoom(livingRoom);
        myApartment.addRoom(bedroom);

        // Добавяме уреди
        livingRoom.addAppliance(new Light("Light"));
        livingRoom.addAppliance(new Fan("Fan"));
        bedroom.addAppliance(new Heater("Heater", 25));

        // Инициализираме панела за управление
        ControlPanel panel = myApartment.getControlPanel();

        // Включи всички уреди в апартамента
        panel.turnOnAll();
        System.out.println("\nInitial Status:\n" + panel.displayStatus() + "\n");

        // Включваме вентилатора
        panel.turnApplianceOFF("Living Room", "Fan");

        // Изпълни всички custom методи на подкласовете на Appliance
        panel.performAllActions();
        bedroom.findAppliance("Heater").turnOff();
        System.out.println("\n");
        panel.performAllActions();
        */

        ///  Konzola 2

        // Създаваме апартамент
        Apartment myApartment = new Apartment("My Cozy Apartment");

        //  Добавяме стаи
        Room livingRoom = new Room("Living Room");
        Room kitchen = new Room("Kitchen");

        myApartment.addRoom(livingRoom);
        myApartment.addRoom(kitchen);

        // Добавяме уреди
        livingRoom.addAppliance(new Appliance("TV"));
        livingRoom.addAppliance(new Appliance("Lamp"));

        kitchen.addAppliance(new Appliance("Fridge"));
        kitchen.addAppliance(new Appliance("Oven"));

        // Действия през панела за управление
        ControlPanel panel = myApartment.getControlPanel();

        System.out.println("Initial Status:\n" + panel.displayStatus());

        panel.turnOnAll();
        System.out.println("\nAfter turning on all appliances:\n" + panel.displayStatus());

        panel.turnRoomOff("Living Room");
        System.out.println("\nAfter turning off Living Room:\n" + panel.displayStatus());

        panel.turnApplianceON("Kitchen", "Oven");
        System.out.println("\nAfter turning on Oven:\n" + panel.displayStatus());
    }
}
