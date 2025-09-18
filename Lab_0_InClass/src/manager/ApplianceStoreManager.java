package manager;

import java.util.ArrayList;

import problemdomain.*;
import java.io.*;

public class ApplianceStoreManager 
{
	// Attributes
	ArrayList<Appliance> appliances;
	
	public ApplianceStoreManager()
	{
		appliances = new ArrayList<>();
		loadAppliancesFromFle();
		displayMenu();
	}
	
	private void loadAppliancesFromFle() 
	{
		String filePath = "Lab_0_InClass/res/appliances.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
		{
			String line;
			while ((line = br.readLine()) != null) 
			{
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
                case "1" ->
                    checkout();
                case "2" ->
                    findByBrand();
                case "3" ->
                    displayByType();
                case "4" ->
                    produceRandomList();
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
                    a.reduceQuantity(1);
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
        String brand = in.nexLine().trim();

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
                    if (a instanceof Refrigerator r && r.getNumberOfDoors() == doors) {
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
        }
    }
}
