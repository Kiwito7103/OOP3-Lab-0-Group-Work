package manager;

import java.util.*;
import java.io.*;
import problemdomain.*;

/**
 * Console manager for the Modern Appliances app.
 * - Loads inventory from res/appliances.txt (semicolon-separated).
 * - Presents a text menu for checkout/search/filter/random list.
 * - Saves the (possibly updated) inventory back to the same file.
 *
 * NOTE: This class expects the domain classes (Refrigerator, Vacuum, Mircrowave, Dishwasher)
 * to live in package problemdomain and to expose getters used below.
 */
public class ApplianceStoreManager {

    /** Relative path to the data file (project root has /src and /res). */
    private static final String FILE_PATH = "res/appliances.txt";

    /** In-memory inventory list used by all menu actions. */
    private final ArrayList<Appliance> appliances = new ArrayList<>();

    /**
     * Single Scanner for System.in. Do NOT close it (closing closes System.in).
     * Keep one instance for the whole app.
     */
    private final Scanner in = new Scanner(System.in);

    /** Constructor: load data and start the menu loop. */
    public ApplianceStoreManager() {
        loadAppliancesFromFle();
        displayMenu();
    }

    /**
     * Load inventory from the semicolon-separated file.
     * Each line starts with an item number where the first digit encodes the type:
     * 1=Refrigerator, 2=Vacuum, 3=Mircrowave (yes, spelled that way here), 4/5=Dishwasher.
     */
    private void loadAppliancesFromFle() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Expecting well-formed lines; no header row.
                String[] parts = line.split(";");

                String itemNumber = parts[0];
                char type = itemNumber.charAt(0); // first character indicates appliance type

                switch (type) {
                    case '1': // Refrigerator: ...;doors;height;width
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

                    case '2': // Vacuum: ...;grade;voltage
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

                    case '3': // Mircrowave: ...;capacity;roomType (K/W)
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

                    // 4 and 5 both map to Dishwasher in this dataset
                    case '4':
                    case '5': // Dishwasher: ...;feature;soundRating (Qt/Qr/Qu/M)
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
                        // Unknown/invalid first digit: warn and skip
                        System.out.println("Unknown appliance type: " + type + " (skipping line)");
                        break;
                }
            }
        } catch (IOException e) {
            // If Eclipse doesn't show the updated file, remember to right-click res/appliances.txt → Refresh.
            e.printStackTrace();
        }
    }

    /** Main menu loop. Reads a choice and dispatches to the corresponding handler. */
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
                    save();       // persist changes to FILE_PATH
                    running = false;
                    break;
                }
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    /**
     * Option 1: Check out an appliance by item number.
     * - Finds the first match in the list.
     * - If quantity > 0, decrements by 1 and confirms; else tells user it's unavailable.
     * Requires Appliance to have getItemNumber(), getQuantity(), reduceQuantity() (or similar).
     */
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
                    a.reduceQuantity(); // or a.setQuantity(a.getQuantity() - 1);
                    System.out.println("Appliance \"" + id + "\" has been checked out.");
                } else {
                    System.out.println("Sorry, that appliance is not available.");
                }
                return; // stop after handling the first match
            }
        }
        System.out.println("Appliance not found.");
    }

    /**
     * Option 2: Find all appliances matching a brand (case-insensitive) and print them.
     */
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
            System.out.println(a); // human-readable toString()
        }
    }

    /**
     * Option 3: Display appliances by type with a required filter per assignment:
     * - Refrigerators by number of doors (2/3/4)
     * - Vacuums by battery voltage (18/24)
     * - Microwaves by room type (K/W)
     * - Dishwashers by sound rating (Qt/Qr/Qu/M)
     */
    private void displayByType() {
        System.out.println("Appliance Types:");
        System.out.println("1 - Refrigerators");
        System.out.println("2 - Vacuums");
        System.out.println("3 - Microwaves");
        System.out.println("4 - Dishwashers");
        System.out.print("Enter type of appliance: ");
        String opt = in.nextLine().trim();

        switch (opt) {
            case "1": { // Refrigerators by number of doors
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
                if (!any) System.out.println("No Matching Refrigerators.");
                break;
            }

            case "2": { // Vacuums by voltage
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
                if (!any) System.out.println("No Matching Vacuums.");
                break;
            }

            case "3": { // Microwaves by room type
                System.out.print("Room where microwave will be used, K (Kitchen) or W (Work Site): ");
                String rt = in.nextLine().trim().toUpperCase();
                if (!(rt.equals("K") || rt.equals("W"))) {
                    System.out.println("Invalid.");
                    return;
                }
                boolean any = false;
                for (Appliance a : appliances) {
                    // NOTE: class name here is "Mircrowave" to match your file; change to Microwave if you rename the class.
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
                if (!any) System.out.println("No Matching Microwaves.");
                break;
            }

            case "4": { // Dishwashers by sound rating
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
                if (!any) System.out.println("No Matching Dishwashers.");
                break;
            }

            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    /**
     * Option 4: Print N random appliances from the list (or all if N > size).
     * Uses a shuffled copy to avoid reordering the main list.
     */
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

    /**
     * Option 5: Save the current inventory back to FILE_PATH.
     * Writes exactly one semicolon-separated line per appliance,
     * matching the format expected by loadAppliancesFromFle().
     *
     * Tip: After running this in Eclipse, you may need to right-click the file in Package Explorer and
     * choose "Refresh" to see updated contents.
     */
    private void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Appliance a : appliances) {
                pw.println(serialize(a)); // DO NOT use toString() here; that’s for human display.
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Produce the exact line that should go into the file for a given appliance:
     * base fields + type-specific fields, all separated by semicolons.
     * This mirrors the parsing logic in loadAppliancesFromFle().
     */
    private String serialize(Appliance a) {
        // Common/base portion
        String base = a.getItemNumber() + ";" + a.getBrand() + ";" + a.getQuantity() + ";" +
                      a.getWattage() + ";" + a.getColor() + ";" + a.getPrice();

        if (a instanceof Refrigerator) {
            Refrigerator r = (Refrigerator) a;
            return base + ";" + r.getNumDoors() + ";" + r.getHeight() + ";" + r.getWidth();

        } else if (a instanceof Vacuum) {
            Vacuum v = (Vacuum) a;
            return base + ";" + v.getGrade() + ";" + v.getVoltage();

        } else if (a instanceof Mircrowave) { // change to Microwave if you rename the class
            Mircrowave m = (Mircrowave) a;
            return base + ";" + m.getCapacity() + ";" + m.getRoomType();

        } else if (a instanceof Dishwasher) {
            Dishwasher d = (Dishwasher) a;
            return base + ";" + d.getFeature() + ";" + d.getSoundRating();
        }

        // Fallback: just the base portion (shouldn't normally happen).
        return base;
    }
}