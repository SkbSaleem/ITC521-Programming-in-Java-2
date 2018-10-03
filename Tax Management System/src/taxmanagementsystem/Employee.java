package taxmanagementsystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.TreeMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;

public class Employee {
	private int employeeID;
	private double annualIncome;


	/** Default constructor */
	public Employee() {
		this(1000, 2.5);
	}

	/** Construct an employee with specific Employee ID and Annual Income
	 */
	public Employee(int employeeID, double annualIncome) {
		this.employeeID = employeeID;
		this.annualIncome = annualIncome;
	}

	/** Return employeeID */
	public int getemployeeID() {
		return employeeID;
	}

	/** Set a new employeeID */
	public void setemployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	/** Return annualIncome */
	public double getannualIncome() {
		return annualIncome;
	}

	/** Set a new annualIncome */
	public void setannualIncome(double annualIncome) {
		this.annualIncome = annualIncome;
	}


	/** Find tax payment 
	 * @throws FileNotFoundException */
	public String gettax(double annualIncome) {
		this.annualIncome = annualIncome;
		File taxRates = new File("src/taxrates.txt");
		Scanner scanner;
		try {
			DecimalFormat df = new DecimalFormat("#.00");
			scanner = new Scanner(taxRates);
			NavigableMap<Double, Double[]> taxTreeMap = new TreeMap<Double, Double[]>();
			scanner.nextLine();

			/* While loop is to convert the file into TreeMap where key is the
			 * start of the tax bracket and value is list of form [base, limit, percent]
			 * description of that tax bracket.
			 */
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				// Setting key of TreeMap start of the tax bracket
				String[] splitLine = line.split(" "); 
				Double key = Double.parseDouble(splitLine[0].replaceAll("[^0-9.]+","")); 

				// Setting value of TreeMap Double[base, limit, percent]
				String[] splitLine1 = line.split("\t"); 
				String[] description = splitLine1[splitLine1.length - 1].split(" ");
				if (description.length == 1){
					Double[] value = {0.0, 0.0, 0.0};
					taxTreeMap.put(key, value);
				}
				else if (description.length == 6) {
					Double base = 0.0;
					Double limit = Double.parseDouble(description[description.length - 1].replaceAll("[^0-9.]+", ""));
					Double percent = Double.parseDouble(description[0].replaceAll("[^0-9.]+", ""));
					Double[] value = {base, limit, percent};
					taxTreeMap.put(key, value);
				}
				else {
					Double base = Double.parseDouble(description[0].replaceAll("[^0-9.]+", ""));
					Double limit = Double.parseDouble(description[description.length - 1].replaceAll("[^0-9.]+", ""));
					Double percent = Double.parseDouble(description[2].replaceAll("[^0-9.]+", ""));
					Double[] value = {base, limit, percent};
					taxTreeMap.put(key, value);
				}			
			} // end while loop


			/* Following code is to check in which bracket the annual income falls
			 * and then perform the calculations accordingly. Formula is;
			 * finalTax = base + ((annualIncome - limit) * (percent/100))
			 */
			Double[] finalTaxArray = taxTreeMap.floorEntry(annualIncome).getValue();
			Double finalTax = finalTaxArray[0] + ((annualIncome - finalTaxArray[1]) * (finalTaxArray[2]/100));
			//df.format(finalTax)
			return ("" + df.format(finalTax));
		} catch (FileNotFoundException e) {
			return "Error: Please provide file src/taxrates.tax";
		}

	}

}
