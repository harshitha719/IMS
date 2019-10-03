package Implementation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import Constant.IMSConstants;
import Manager.IMSManager;

import java.util.Map;


public class IMSImplementation {

public IMSConstants constants;
public IMSManager manager;
public String selectedInput;
public String dataFile;
public BufferedReader brReader = null;
public Boolean display = Boolean.FALSE;
public Map<String,ArrayList<String>> productMap = null;
public Map<String,ArrayList<String>>  productIDMap = null;
public int count = 0;
public ArrayList<String> dataFileList = null;

public void startPoint() throws IOException {
	constants = new IMSConstants();
	manager = new IMSManager();
	System.out.println("IMS Main Menu");
	System.out.println("1. Input Data \n" +
			"2. Search Inventory \n" +
			"3. Show Inventory \n" +
			"4. Change record \n" +
			"5. Mail \n" +
			"6. Exit");
	this.setDataFile(constants.HARDWARE);
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	System.out.println(constants.ENTERMAINMENU);
	this.setSelectedInput(br.readLine());
	display = Boolean.FALSE;
	manager.defaultDataFileRead(this, display);
	this.readInputFromUser();
}

public void readInputFromUser() throws IOException{
	if(this.getSelectedInput()!=null) {
		if (this.getSelectedInput().equalsIgnoreCase(constants.ONE)) {
			manager.readDataFile(this);
		}
		else if(this.getSelectedInput().equalsIgnoreCase(constants.TWO)) {
			searchProduct();
		}
		else if(this.getSelectedInput().equalsIgnoreCase(constants.THREE)) {
			
		}
		else if(this.getSelectedInput().equalsIgnoreCase(constants.FOUR)) {
			addRecordmenu();
		}
		else {
			System.out.println("Please select valid number from Menu");
			startPoint();
		}
	}
}

private void searchProduct() throws IOException {
	String searchMenu ;
	String searchProduct;
	System.out.println("Menu 2 \n" 
						+ " "
						+ "1. Search Product \n"
						+ " "
						+"2. Search Product by other attribute \n"
						+ " "
						+"3. Main Menu \n");
	brReader = new BufferedReader(new InputStreamReader(System.in));
	searchMenu=brReader.readLine();
	if(searchMenu!=null && searchMenu.equalsIgnoreCase(constants.TWODOTONE)) {
		manager.searchByproductAndID(this);
	}
	else if(searchMenu!=null && searchMenu.equalsIgnoreCase(constants.TWODOTTWO)) {
		
	}
	else if(searchMenu!=null && searchMenu.equalsIgnoreCase(constants.TWODOTTHREE)) {
	startPoint();
	}
	else {
		System.out.println(constants.ERRORINVALIDMENU2);
		searchProduct();
	}
}


private void addRecordmenu() throws IOException{
	
	Scanner sc = new Scanner(System.in);
	IMSManager manager = new IMSManager();
		
	String mainMenuContd = "Y"; 	
	while (mainMenuContd.equalsIgnoreCase("Y")){
		
		System.out.println("Menu 4 \n\n" 			
				+ "	1. Add Record \n"
				+"	2. Remove Record \n"
				+"	3. Change record \n"
				+"	4. Main Menu \n");
		System.out.println("\n Make a selection from the menu in the format 4.x");
		String selection = sc.nextLine();
		String submenuContd = "Y";	
		
		while (submenuContd.equalsIgnoreCase("Y")){

			switch (selection) {
			case "4.1":
				manager.addRecord(this.getDataFile() + constants.TXT);
				System.out.println("Do you want to add more Y/N?");
				submenuContd = sc.nextLine();
				break;
				
			case "4.2":
				System.out.println("\n Choose between DeleteByProductId or DeleteByProductName ID/NAME");
				String choice = sc.nextLine();
				manager.deleteRecord(this.getDataFile() + constants.TXT, choice);
				System.out.println("Do you want to remove more Y/N?");
				submenuContd = sc.nextLine();
				break;

			case "4.3":
				manager.modifyRecord(this.getDataFile() + constants.TXT);
				System.out.println("Do you want to update more Y/N?");
				submenuContd = sc.nextLine();
				break;
				
			default:
				manager.createBackup(this.getDataFile() + constants.TXT);
				startPoint();
				break;
			}				
					
		}
		System.out.println("Do you want to perform another menu operation Y/N?");
		mainMenuContd = sc.nextLine();
	}
	
	sc.close();
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
