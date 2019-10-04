package Implementation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import Constant.IMSConstants;
import Manager.IMSManager;


import java.util.TreeMap;


public class IMSImplementation {


public IMSManager manager;
public String selectedInput;
public String dataFile;
public BufferedReader brReader = null;
public Boolean display = Boolean.FALSE;
public Boolean firstTime = Boolean.TRUE;
public Map<String,ArrayList<String>> productMap = null;
public Map<String,ArrayList<String>>  productIDMap = null;
public int count = 0;
public ArrayList<String> dataFileList = null;

public void startPoint() throws IOException {
	manager = new IMSManager();
	System.out.println("IMS Main Menu");
	System.out.println("1. Input Data \n" +
			"2. Search Inventory \n" +
			"3. Show Inventory \n" +
			"4. Change record \n" +
			"5. Mail \n" +
			"6. Exit");
	if(firstTime) {
		this.setDataFile(IMSConstants.HARDWARE);
	}
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	System.out.println(IMSConstants.ENTERMAINMENU);
	this.setSelectedInput(br.readLine());
	manager.defaultDataFileRead(this, display);
	this.readInputFromUser();
}

public void readInputFromUser() throws IOException{
	if(this.getSelectedInput()!=null) {
		if (this.getSelectedInput().equalsIgnoreCase(IMSConstants.ONE)) {
			manager.readDataFile(this);
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.TWO)) {
			searchProduct();
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.THREE)) {
			
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.FOUR)) {
			
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.FIVE)) {
					
		}
		else if(this.getSelectedInput().equalsIgnoreCase(IMSConstants.SIX)) {
			manager.clearConsole();
		}
		else {
			System.out.println("Please select valid number from Menu \n");
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
	if(searchMenu!=null && searchMenu.equalsIgnoreCase(IMSConstants.TWODOTONE)) {
		manager.searchByproductAndID(this);
	}
	else if(searchMenu!=null && searchMenu.equalsIgnoreCase(IMSConstants.TWODOTTWO)) {
		manager.searchByOtherAttribute(this);
	}
	else if(searchMenu!=null && searchMenu.equalsIgnoreCase(IMSConstants.TWODOTTHREE)) {
	startPoint();
	}
	else {
		System.out.println(IMSConstants.ERRORINVALIDMENU2);
		searchProduct();
	}
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
