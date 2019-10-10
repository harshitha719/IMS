package Implementation;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import Constant.IMSConstants;
import Manager.IMSManagerMenu1And2;
import Manager.IMSManagerMenu4;
import java.io.FileNotFoundException;
import Manager.IMSManagerMenu3;
/**
 * IMSImplementation
 */
public class IMSImplementation {
public IMSManagerMenu1And2 manager;
public IMSManagerMenu4 menu4manager;
public String selectedInput;
public String dataFile;
public BufferedReader brReader = null;
public Boolean display = Boolean.FALSE;
public Boolean firstTime = Boolean.TRUE;
public Map<String,ArrayList<String>> productMap = null;
public Map<String,ArrayList<String>>  productIDMap = null;
public int count = 0;
public ArrayList<String> dataFileList = new ArrayList<String>();
public ArrayList<String> fileNameList = new ArrayList<String>();
public ArrayList<String>searchList = null;
/**
 * startPoint
 * This is the initial method of the application,which provided various options 
 * of Menu to users to choose by and proceed ahead
 * @throws IOException
 */
public void startPoint() throws IOException {
	manager = new IMSManagerMenu1And2();
	menu4manager = new IMSManagerMenu4();
	System.out.println(IMSConstants.MAINMENU);
	System.out.println("1. Input Data \n" +
			"2. Search Inventory \n" +
			"3. Show Inventory \n" +
			"4. Change record \n" +
			"5. Mail \n" +
			"6. Exit");
	if(firstTime) {
		this.setDataFile(IMSConstants.HARDWARE);
		manager.defaultDataFileRead(this);
	}
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	System.out.println(IMSConstants.ENTERMAINMENU);
	this.setSelectedInput(br.readLine());
	
	this.readInputFromUser();
}
/**
 * readInputFromUser
 * This method is the trigger point of all the main menu items.
 * @throws IOException
 */
public void readInputFromUser() throws IOException{
	if(this.getSelectedInput()!=null) {
		if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.ONE)) {
			manager.readDataFile(this);
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.TWO)) {
			searchProduct();
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.THREE)) {
			showInventoryMenu();
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.FOUR)) {
			addRecordmenu();
			clearscr();
			IMSManagerMenu1And2.redirect(this);
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.FIVE)) {
			
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.SIX)) {
			IMSManagerMenu1And2.clearConsole();
		}
		else {
			System.out.println("Please select valid number from Menu \n");
			startPoint();
		}
	}
}
/**
 * addRecordmenu
 * This method calls the sub category of menu 4 items and their respective functionality.
 * @throws IOException
 */
private void addRecordmenu() throws IOException, FileNotFoundException {

	brReader = new BufferedReader(new InputStreamReader(System.in));
	IMSManagerMenu4 manager = new IMSManagerMenu4();
	boolean updateFlag = false;
	StringBuffer buffer = manager.changeRecordPreProcessor(this.getDataFile());

	String mainMenuContd = "Y";
	while (mainMenuContd.equalsIgnoreCase("Y")) {

		clearscr();
		System.out.println("Menu 4 \n\n" + "	1. Add Record \n" + "	2. Remove Record \n"
				+ "	3. Change record \n" + "	4. Main Menu \n");
		System.out.println("\n Make a selection from the menu in the format 4.x");
		String selection = brReader.readLine();
		String submenuContd = "Y";

		while (submenuContd.equalsIgnoreCase("Y")) {

			switch (selection) {
			case "4.1":
				updateFlag = manager.addRecord(this.getDataFile() + IMSConstants.TXT, brReader);
				System.out.println("Do you want to add more Y/N?");
				submenuContd = brReader.readLine();
				break;

			case "4.2":
				System.out.println(
						"\n Choose between DeleteByProductId or DeleteByProductName or DeleteByProductNameAndModel or DeleteByManufacturer.\n"
								+ "\n\t Applicable choices are : ID / NAME / NAMEANDMODEL / MANUFACTURER");
				String choice = brReader.readLine();
				updateFlag = manager.deleteRecord(this.getDataFile() + IMSConstants.TXT, choice, brReader);
				System.out.println("Do you want to remove more Y/N?");
				submenuContd = brReader.readLine();
				break;

			case "4.3":
				updateFlag = manager.modifyRecord(this.getDataFile() + IMSConstants.TXT, brReader);
				System.out.println("Do you want to update more records Y/N?");
				submenuContd = brReader.readLine();
				break;

			case "4.4":
				submenuContd = "N";
				mainMenuContd = "N";
				if (!updateFlag) {
					manager.createBackup(buffer);
					System.out.println(
							"\n File backup with the name backup.txt created at the same location of original file.");
					System.out.println("\n\n Exiting from the menu.........");
				}
				break;

			default:
				submenuContd = "N";
				System.out.println("\n Invalid menu selection. Exiting from the sub-menu.....");
				break;
			}

		}
		if (mainMenuContd.equalsIgnoreCase("Y")) {
			System.out.println("Do you want to continue in menu 4 or return to main menu? Continue -  Y, Return - N");
			mainMenuContd = brReader.readLine();
		}
	}
	if (updateFlag) {			
		manager.createBackup(buffer);
		clearscr();
		System.out.println(
				"\n 	File backup with the name backup.txt created at the same location of original file.");
		System.out.println("\n\n	Returning to main menu.........");
		try{
			Thread.sleep(3000);
		} catch(InterruptedException e) {}
		clearscr();			
	}
}

public static void clearscr() {
	for (int i = 0; i < 20; ++i)
		System.out.println();
}
/**
 * searchProduct
 * This method calls the sub category of menu 2 items and their respective functionality.
 * @throws IOException
 */
private void searchProduct() throws IOException {
	String searchMenu ="";
	brReader = new BufferedReader(new InputStreamReader(System.in));
	while(searchMenu!="2.3")
	{
	System.out.println("Menu 2 \n" 
						+ " "
						+ "2.1 Search Product \n"
						+ " "
						+"2.2 Search Product by other attribute \n"
						+ " "
						+"2.3 Main Menu \n");
	
	searchMenu=brReader.readLine();
	switch(searchMenu)
	{
	case "2.1":
		manager.searchByproductAndID(this);
		break;
	case "2.2":
		manager.searchByOtherAttribute(this);
		break;
	case "2.3":
		startPoint();
		break;
	}
	}
}
/**
 * showInventoryMenu
 * This method calls the sub category of menu 3 items and their respective functionality. 
 * @throws FileNotFoundException
 * @throws IOException
 */

public void showInventoryMenu() throws FileNotFoundException, IOException
{
	//Function for displaying options for Menu 3 - search the inventory
	//Scanner to get user input
	Scanner sc = new Scanner(System.in);
	//Created an object of IMSManagerMenu3 class
	IMSManagerMenu3 manager = new IMSManagerMenu3();
	String choice="";
	String userInput=" ";
	int columnNumber=10;
	//while loop to show Menu 3 until user inputs 3.6 to go back to the main Menu
	while(choice!="3.6")
	{
		//print the Menu 3 options to the user
		System.out.println("\nShow Inventory Menu - Menu 3");
		System.out.println("\n3.1 Show entire inventory \n3.2 Show inventory by Manufacturer \n"
			+ "3.3 Show inventory by Type \n3.4 Show inventory by Location \n3.5 Show current discount items"
			+ "\n3.6 Main Menu");
	
		//get user input
		choice = sc.nextLine();
		//Switch statement for getting inputs from user and calling showInventoryResult class functions based on the user inputs
		switch(choice)
		{
		case "3.1":
			//function call to show entire inventory, passing the file name as input
			manager.showInventory(this.getDataFile() + IMSConstants.TXT);
			break;	
	
		case "3.2":
			System.out.println("Enter Manufacturer name\n");
			//get the manufacturer name from the user
			userInput=sc.nextLine();
			columnNumber=3;
			//function call to show data for the manufacturer entered by the user, passing file name, manufacturer name and column number
			manager.showInventoryResults(this.getDataFile() + IMSConstants.TXT, userInput,columnNumber);
			break;	
		case "3.3":
			System.out.println("Enter Type\n");
			userInput=sc.nextLine();
			columnNumber=4;
			//function call to show the data for Type entered by the user
			manager.showInventoryResults(this.getDataFile() + IMSConstants.TXT, userInput,columnNumber);
			break;
			
		case "3.4":
			System.out.println("Enter Location\n");
			userInput=sc.nextLine();
			columnNumber=5;
			//function call to show the data for location entered by the user
			manager.showInventoryResults(this.getDataFile() + IMSConstants.TXT, userInput,columnNumber);
			break;
			
		case "3.5":
			columnNumber=8;
			//function call to show the data for all lines with non-zero discounts
			manager.showInventoryResults(this.getDataFile() + IMSConstants.TXT, userInput,columnNumber);
			break;
			
		case "3.6":
			//function call to go back to the main menu
			startPoint();
			choice="3.6";
			break;
		}
	}
	sc.close();
}
/**
 * getSelectedInput
 * @return
 */
public String getSelectedInput() {
	return selectedInput;
}
/**
 * setSelectedInput
 * @param selectedInput
 */
public void setSelectedInput(String selectedInput) {
	this.selectedInput = selectedInput;
}
/**
 * getDataFile
 * @return
 */
public String getDataFile() {
	return dataFile;
}
/**
 * setDataFile
 * @param dataFile
 */
public void setDataFile(String dataFile) {
	this.dataFile = dataFile;
}

}
