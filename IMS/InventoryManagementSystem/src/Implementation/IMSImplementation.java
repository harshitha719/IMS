package Implementation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import Constant.IMSConstants;
import Manager.IMSManager;

public class IMSImplementation {

	public IMSManager manager;
	public String selectedInput;
	public String dataFile;
	public BufferedReader brReader = null;
	public Boolean display = Boolean.FALSE;
	public Boolean firstTime = Boolean.TRUE;
	public Map<String, ArrayList<String>> productMap = null;
	public Map<String, ArrayList<String>> productIDMap = null;
	public int count = 0;
	public ArrayList<String> dataFileList = null;

	public void startPoint() throws IOException {
		manager = new IMSManager();
		System.out.println("IMS Main Menu");
		System.out.println("1. Input Data \n" + "2. Search Inventory \n" + "3. Show Inventory \n"
				+ "4. Change record \n" + "5. Mail \n" + "6. Exit");
		if (firstTime) {
			this.setDataFile(IMSConstants.HARDWARE);
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(IMSConstants.ENTERMAINMENU);
		this.setSelectedInput(br.readLine());
		manager.defaultDataFileRead(this, display);
		this.readInputFromUser();
	}

	public void readInputFromUser() throws IOException, FileNotFoundException {
		if (this.getSelectedInput() != null) {
			if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.ONE)) {
				manager.readDataFile(this);
			} else if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.TWO)) {
				searchProduct();
			} else if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.THREE)) {

			} else if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.FOUR)) {
				addRecordmenu();				
				IMSManager.redirect(this);
			} else if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.FIVE)) {

			} else if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.SIX)) {
				IMSManager.clearConsole();
			} else {
				System.out.println("Please select valid number from Menu \n");
				startPoint();
			}
		}
	}

	private void searchProduct() throws IOException {
		String searchMenu;
		String searchProduct;
		System.out.println("Menu 2 \n" + " " + "1. Search Product \n" + " " + "2. Search Product by other attribute \n"
				+ " " + "3. Main Menu \n");
		brReader = new BufferedReader(new InputStreamReader(System.in));
		searchMenu = brReader.readLine();
		if (searchMenu != null && searchMenu.equalsIgnoreCase(IMSConstants.TWODOTONE)) {
			manager.searchByproductAndID(this);
		} else if (searchMenu != null && searchMenu.equalsIgnoreCase(IMSConstants.TWODOTTWO)) {
			manager.searchByOtherAttribute(this);
		} else if (searchMenu != null && searchMenu.equalsIgnoreCase(IMSConstants.TWODOTTHREE)) {
			startPoint();
		} else {
			System.out.println(IMSConstants.ERRORINVALIDMENU2);
			searchProduct();
		}
	}

	private void addRecordmenu() throws IOException, FileNotFoundException {

		brReader = new BufferedReader(new InputStreamReader(System.in));
		IMSManager manager = new IMSManager();
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
		for (int i = 0; i < 50; ++i)
			System.out.println();
	}

	public String getSelectedInput() {
		return selectedInput;
	}

	public void setSelectedInput(String selectedInput) {
		this.selectedInput = selectedInput;
	}

	public String getDataFile() {
		return dataFile;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

}
