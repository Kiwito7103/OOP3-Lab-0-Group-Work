package manager;

import java.util.*;
import java.io.*;
import problemdomain.*;

public class ApplianceStoreManager {
    // Attributes

    private final ArrayList<Appliance> appliances = new ArrayList<>();
    private final Scanner in = new Scanner(System.in);

    public ApplianceStoreManager() {
        loadAppliancesFromFle();
        displayMenu();
    }

    private void loadAppliancesFromFle() {
        String filePath = "Lab_0_InClass/res/appliances.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                String itemNumber = parts[0];
                char type = itemNumber.charAt(0);

                switch (type) {
                    case '1': // Fridge
                        appliances.add(new Refrigerator(
                                Integer.parseInt(itemNumber),
                                parts[1],
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4],
                                Double.parseDouble(parts[5]),
                                Integer.parseInt(parts[6]),
                                Double.parseDouble(parts[7]),
                                Double.parseDouble(parts[8])
                        ));
                        break;
                    case '2': // Vacuum
                        appliances.add(new Vacuum(
                                Integer.parseInt(itemNumber),
                                parts[1],
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4],
                                Double.parseDouble(parts[5]),
                                parts[6],
                                Integer.parseInt(parts[7])
                        ));
                        break;
                    case '3': // Mircrowave
                        appliances.add(new Mircrowave(
                                Integer.parseInt(itemNumber),
                                parts[1],
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4],
                                Double.parseDouble(parts[5]),
                                Double.parseDouble(parts[6]),
                                parts[7]
                        ));
                        break;
                    case '4': // for clarification, if it gets a 4, because there is no break, itl just go to the next
                    case '5': // block of code, which is 5. so that catches both id types of dishwashers.
                        appliances.add(new Dishwasher(
                                Integer.parseInt(itemNumber),
                                parts[1],
                                Integer.parseInt(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4],
                                Double.parseDouble(parts[5]),
                                parts[6],
                                parts[7]
                        ));
                        break;
                    default:
                        System.out.println("why is there a random number in here: " + type);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                case "1":
                    checkout();
                    break;
                case "2":
                    findByBrand();
                    break;
                case "3":
                    displayByType();
                    break;
                case "4":
                    randomList();
                    break;
                case "5": {
                    save();
                    running = false;
                    break;
                }
                default:
                    System.out.println("Invalid option.");
                    break;
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
            case "1": {
                System.out.print("Enter number of doors (2, 3, or 4): ");
                int doors;
                try {
                    doors = Integer.parseInt(in.nextLine().trim());
                } catch (Exception e) {
                    System.out.println("Invalid number of doors.");
                    return;
                }
                boolean any = false;
                for (Appliance a : appliances) {
                    if (a instanceof Refrigerator) {
                        Refrigerator r = (Refrigerator) a;
                        if (r.getNumDoors() == doors) {
                            if (!any) {
                                System.out.println("Matching Refrigerators:");
                                any = true;
                            }
                            System.out.println(r);
                        }
                    }
                }
                if (!any) {
                    System.out.println("No Matching Refrigerators.");
                }
                break;
            }

            case "2": {
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
                    if (a instanceof Vacuum) {
                        Vacuum v = (Vacuum) a;
                        if (v.getVoltage() == voltage) {
                            if (!any) {
                                System.out.println("Matching Vacuums:");
                                any = true;
                            }
                            System.out.println(v);
                        }
                    }
                }
                if (!any) {
                    System.out.println("No Matching Vacuums.");
                }
                break;
            }
            case "3": {
                System.out.print("Room where microwave will be used, K (Kitchen) or W (Work Site): ");
                String rt = in.nextLine().trim().toUpperCase();
                if (!(rt.equals("K") || rt.equals("W"))) {
                    System.out.println("Invalid.");
                    return;
                }
                boolean any = false;
                for (Appliance a : appliances) {
                    if (a instanceof Mircrowave) {
                        Mircrowave m = (Mircrowave) a;
                        if (m.getRoomType().equalsIgnoreCase(rt)) {
                            if (!any) {
                                System.out.println("Matching Microwaves:");
                                any = true;
                            }
                            System.out.println(m);
                        }
                    }
                }
                if (!any) {
                    System.out.println("No Matching Microwaves.");
                }
                break;
            }
            case "4": {
                System.out.print("Enter the sound rating of the dishwasher: Qt (Quietest), Qr (Quieter), Q (Quiet), M (Moderate): ");
                String sr = in.nextLine().trim();
                boolean any = false;
                for (Appliance a : appliances) {
                    if (a instanceof Dishwasher) {
                        Dishwasher d = (Dishwasher) a;
                        if (d.getSoundRating().equalsIgnoreCase(sr)) {
                            if (!any) {
                                System.out.println("Matching Dishwashers:");
                                any = true;
                            }
                            System.out.println(d);
                        }
                    }
                }
                if (!any) {
                    System.out.println("No Matching Dishwashers.");
                }
                break;
            }
            default:
                System.out.println("Invalid option.");
                break;
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
        List<Appliance> copy = new ArrayList<Appliance>(appliances);
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
