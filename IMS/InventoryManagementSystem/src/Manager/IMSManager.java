package Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import Constant.IMSConstants;
import Implementation.IMSImplementation;

public class IMSManager {
	
	public void redirect(IMSImplementation imsImplementation) throws IOException {
		System.out.println();
		System.out.println("Enter 0 to go back to MainMenu \n" + "" +
		"Enter 6 to exit" ) ;
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		imsImplementation.setSelectedInput(imsImplementation.brReader.readLine());
		if (imsImplementation.getSelectedInput().equalsIgnoreCase(IMSConstants.ZERO)) {
			imsImplementation.firstTime = Boolean.FALSE;
			imsImplementation.startPoint();
		}
		if (imsImplementation.getSelectedInput().equalsIgnoreCase(IMSConstants.SIX)) {
			clearConsole();
		}
		
	}

	public void searchByproductAndID(IMSImplementation imsImplementation) throws IOException {
		String searchProduct;
		boolean productExist = Boolean.FALSE;
		System.out.println(IMSConstants.ENTERPRODUCT);
		searchProduct = imsImplementation.brReader.readLine();
		if(searchProduct!=null) {
			if(imsImplementation.productMap!=null && imsImplementation.productIDMap!=null ) {
					for (Entry<String, ArrayList<String>> entry : imsImplementation.productMap.entrySet())  {
						if(entry.getKey().equalsIgnoreCase(searchProduct)) {
							productExist=Boolean.TRUE;
							ArrayList<String> productList = entry.getValue();
							Collections.sort(productList);
							Iterator<String> iter = productList.iterator();
							while(iter.hasNext())
							System.out.println(iter.next());
					}
				}
				for (Entry<String, ArrayList<String>> entry : imsImplementation.productIDMap.entrySet())  {
						if(entry.getKey().equalsIgnoreCase(searchProduct)) {
							productExist=Boolean.TRUE;
							ArrayList<String> productIDList = entry.getValue();
							Collections.sort(productIDList);
							Iterator<String> iter = productIDList.iterator();
							while(iter.hasNext())
							System.out.println(iter.next());
						}
					}
				}
			}
		
		if(productExist==Boolean.FALSE)
			System.out.println(IMSConstants.ERRORPRODUCTNOTPRESENT);
			redirect(imsImplementation);
		}
	
	
	public void searchByOtherAttribute(IMSImplementation imsImplementation) throws IOException {
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter attributes to search");
		String otherAttribute = imsImplementation.brReader.readLine();
		if(imsImplementation.dataFileList!=null) {
			
		}
		
	}
	public void clearConsole() {
		for (int i = 0; i < 50; ++i) System.out.println();
		System.out.print("Thank you for using Inventory Management System");
		for (int i = 0; i <5; ++i) System.out.println();
	}
	public void readDataFile(IMSImplementation imsImplementation) throws IOException, FileNotFoundException {
			imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(IMSConstants.ENTERDATAFILE);
			imsImplementation.setDataFile(imsImplementation.brReader.readLine());
			if (imsImplementation.getDataFile() != null ) {
				defaultDataFileRead(imsImplementation, imsImplementation.display);
			}
			else {
				System.out.println(IMSConstants.ERRORINVALIDFILE);
			}
			redirect(imsImplementation);
		}

	public void defaultDataFileRead(IMSImplementation imsImplementation, Boolean display) throws FileNotFoundException {
			Scanner scanInput1 = new Scanner(new File(imsImplementation.getDataFile() + IMSConstants.TXT));
			String row = "";
			String value = "";
			imsImplementation.dataFileList = new ArrayList<String>();
			while (scanInput1.hasNext()) {
				imsImplementation.count++;
				value = scanInput1.next();
				row = row + value.concat(" ");
				if (imsImplementation.count % 10 == 0) {
					imsImplementation.dataFileList.add(row);
					row = "";
				}
			}
			if(imsImplementation.dataFileList!=null) {
				 imsImplementation.productMap = new HashMap<String,ArrayList<String>>();
				 imsImplementation.productIDMap = new HashMap<String,ArrayList<String>>();
				ArrayList<String> listValues = null;
				for (String list:imsImplementation.dataFileList) {
					String[] words=list.split(" ");
						if(words[1] != "Product" && imsImplementation.productMap.containsKey(words[1])) {
							imsImplementation.productMap.get(words[1]).add(list);
						} else {
							listValues = new ArrayList<String>();
							listValues.add(list);
							imsImplementation.productMap.put(words[1], listValues);
						}
						if(words[0] != "ProductID" && imsImplementation.productIDMap.containsKey(words[0])) {
							imsImplementation.productIDMap.get(words[0]).add(list);
						} else {
							listValues = new ArrayList<String>();
							listValues.add(list);
							imsImplementation.productIDMap.put(words[0], listValues);
						}
					}
			}
		}
}
