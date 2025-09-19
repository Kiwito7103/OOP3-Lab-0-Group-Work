package manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import problemdomain.Appliance;
import problemdomain.Dishwasher;
import problemdomain.Microwave;
import problemdomain.Refrigerator;
import problemdomain.Vacuum;

public class ApplianceStoreManager {

    // Attributes
    private final Scanner in = new Scanner(System.in);

    ArrayList<Appliance> appliances;

    public ApplianceStoreManager() {
        appliances = new ArrayList<>();
        loadAppliancesFromFle();
        displayMenu();
    }

    private void loadAppliancesFromFle() {

    }

    private void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to Modern Appliances");
            System.out.println("How may we assist you?");
            System.out.println("1 - Check out appliance");
            System.out.println("2 - Find appliances by brand");
            System.out.println("3 - Display appliances by type");
            System.out.println("4 - Produce random appliance list");
            System.out.println("5 - Save & Exit");
            System.out.print("Enter option: ");

            String choice = in.nextLine().trim();
            switch (choice) {
                case "1" ->
                    checkout();
                case "2" ->
                    findByBrand();
                case "3" ->
                    displayByType();
                case "4" ->
                    randomList();
                case "5" -> {
                    save();
                    running = false;
                }
                default ->
                    System.out.println("Invalid option.");
            }
        }
    }

    private void checkout() {
        System.out.print("Enter the item number of an appliance: ");
        String s = in.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Invalid item number.");
            return;
        }

        for (Appliance a : appliances) {
            if (a.getItemNumber() == id) {
                if (a.getQuantity() > 0) {
                    a.reduceQuantity();
                    System.out.println("Appliance \"" + id + "\" has been checked out.");
                } else {
                    System.out.println("Sorry, that appliance is not available.");
                }
                return;
            }
        }
        System.out.println("Appliance not found.");
    }

    private void findByBrand() {
        System.out.print("Enter brand to search for: ");
        String brand = in.nextLine().trim();

        List<Appliance> matches = new ArrayList<>();
        for (Appliance a : appliances) {
            if (a.getBrand().equalsIgnoreCase(brand)) {
                matches.add(a);
            }
        }
        if (matches.isEmpty()) {
            System.out.println("No appliances found for brand: " + brand);
            return;
        }

        System.out.println("Matching Appliances:");
        for (Appliance a : matches) {
            System.out.println(a);
        }
    }

    private void displayByType() {
        System.out.println("Appliance Types:");
        System.out.println("1 - Refrigerators");
        System.out.println("2 - Vacuums");
        System.out.println("3 - Microwaves");
        System.out.println("4 - Dishwashers");
        System.out.print("Enter type of appliance: ");
        String opt = in.nextLine().trim();

        switch (opt) {
            case "1" -> {
                System.out.print("Enter number of doors (2, 3, or 4): ");
                String d = in.nextLine().trim();
                int doors;
                try {
                    doors = Integer.parseInt(d);
                } catch (Exception e) {
                    System.out.println("Invalid number of doors.");
                    return;
                }
                List<Refrigerator> list = new ArrayList<>();
                for (Appliance a : appliances) {
                    if (a instanceof Refrigerator r && r.getNumDoors() == doors) {
                        list.add(r);
                    }
                }
                if (list.isEmpty()) {
                    System.out.println("No Matching Fridges.");
                    return;
                }
                System.out.println("Matching Fridges:");
                for (Refrigerator r : list) {
                    System.out.println(r);
                }
            }
            case "2" -> {
                System.out.print("Enter battery voltage value. 18V or 24V: ");
                int voltage;
                try {
                    voltage = Integer.parseInt(in.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("Invalid voltage.");
                    return;
                }
                boolean any = false;
                for (Appliance a : appliances) {
                    if (a instanceof Vacuum v && v.getVoltage() == voltage) {
                        if (!any) {
                            System.out.println("Matching Vacuums:");
                            any = true;
                            System.out.println(v);
                        }
                    }
                    if (!any) {
                        System.out.println("No Matching Vacuums.");
                    }
                }
            }
            case "3" -> {
                System.out.print("Room where microwave will be used, K (Kitchen) or W (Work Site): ");
                String rt = in.nextLine().trim().toUpperCase();
                if (!(rt.equals("K") || rt.equals("W"))) {
                    System.out.println("Invalid.");
                    return;
                }
                boolean any = false;
                for (Appliance a : appliances) {
                    if (a instanceof Microwave m && m.getRoomType().equalsIgnoreCase(rt)) {
                        if (!any) {
                            System.out.println("Matching Microwaves:");
                            any = true;
                            System.out.println(m);
                        }
                    }
                    if (!any) {
                        System.out.println("No Matching Microwaves.");
                    }
                }
            }
            case "4" -> {
                System.out.print("Enter the sound rating of the dishwasher: Qt (Quietest), Qr (Quieter), Q (Quiet), M (Moderate): ");
                String sr = in.nextLine().trim();
                boolean any = false;
                for (Appliance a : appliances) {
                    if (a instanceof Dishwasher d && d.getSoundRating().equalsIgnoreCase(sr)) {
                        if (!any) {
                            System.out.println("Matching Dishwashers:");
                            any = true;
                        }
                        System.out.println(d);
                    }
                }
                if (!any) {
                    System.out.println("No Matching Dishwashers.");
                }
            }
            default ->
                System.out.println("Invalid option.");
        }
    }

    private void randomList() {
        System.out.print("Enter number of appliances: ");
        int n;
        try {
            n = Integer.parseInt(in.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid number.");
            return;
        }
        if (appliances.isEmpty()) {
            System.out.println("No appliances available.");
            return;
        }
        List<Appliance> copy = new ArrayList<>(appliances);
        Collections.shuffle(copy);
        int limit = Math.min(n, copy.size());
        System.out.println("Random Appliances:");
        for (int i = 0; i < limit; i++) {
            System.out.println(copy.get(i));
        }
    }

    private void save() {

    }
}
