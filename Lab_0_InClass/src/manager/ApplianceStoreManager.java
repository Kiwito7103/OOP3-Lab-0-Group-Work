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

	private void displayMenu() 
	{
		// guys this is my test shi
		System.out.println("heres sum fridges");
		for (Appliance appliance : appliances) 
		{
			if (appliance instanceof Refrigerator) 
			{
				System.out.println(appliance);
			}
		}
		System.out.println("\nfridge prices");
		for (Appliance appliance : appliances) 
		{
			if (appliance instanceof Refrigerator) 
			{
				System.out.println("itemNumber: " + appliance.getItemNumber() + " = moolah $" + String.format("%,.2f", appliance.getPrice()));
			} // bruh the string formatting is so wierd in java wtf
		}
	}
}
