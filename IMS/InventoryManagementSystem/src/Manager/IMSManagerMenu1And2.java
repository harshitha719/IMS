package Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import Constant.IMSConstants;
import Implementation.IMSImplementation;
/**
 * 
 * IMSManagerMenu1And2
 *
 */
public class IMSManagerMenu1And2 {
	/**
	 * redirect
	 * This method is used to redirect user back to main menu or exit the application.
	 * @param imsImplementation
	 * @throws IOException
	 */
	public static void redirect(IMSImplementation imsImplementation) throws IOException {
		System.out.println();
		System.out.println("Enter 0 to go back to MainMenu \n" + "" +
		"Enter 6 to exit" ) ;
		//ask user if he wants to exit or return to main menu
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		imsImplementation.setSelectedInput(imsImplementation.brReader.readLine());
		//zero entry take user to main menu else exit the application.
		if (imsImplementation.getSelectedInput().equalsIgnoreCase(IMSConstants.ZERO)) {
			imsImplementation.firstTime = Boolean.FALSE;
			imsImplementation.startPoint();
		}
		else if (imsImplementation.getSelectedInput().equalsIgnoreCase(IMSConstants.SIX)) {
			clearConsole();
		}
		else {
			//invalid entry apart from 0 or 6
			System.out.println(IMSConstants.SELECTZEROORSIX);
			redirect(imsImplementation);
		}
	}
	/**
	 * searchByproductAndID
	 * This method is used in Menu 2 to search the product by ID and name in 2.1
	 * @param imsImplementation
	 * @throws IOException
	 */
	public void searchByproductAndID(IMSImplementation imsImplementation) throws IOException {
		String searchProduct;
		boolean productExist = Boolean.FALSE;
		boolean listClear = Boolean.TRUE;
		System.out.println(IMSConstants.ENTERPRODUCT);
		searchProduct = imsImplementation.brReader.readLine();
		String trigger = null;
		//All the files chosen from user are appended to a common file data file list.
		//To avoid duplicate files being added contains is used.
		 if(!imsImplementation.fileNameList.contains(imsImplementation.getDataFile())) {
			 	 trigger = IMSConstants.ITERATE;
				 listClear = Boolean.TRUE;
				fileReadToList(imsImplementation,listClear,trigger);
				if(imsImplementation.dataFileList!=null) {
					 populateMapWithProductAndID(imsImplementation,imsImplementation.dataFileList);
				}
		} else {
			trigger = IMSConstants.NOITERATE;
			listClear = Boolean.FALSE;
			fileReadToList(imsImplementation,listClear,trigger);
			populateMapWithProductAndID(imsImplementation,imsImplementation.searchList);
		}
		//we are iterating the map and checking if the map key contains the user input value Product ,if match found we are displaying.
		 //Final out is sorted
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
			//we are iterating the map and checking if the map key contains the user input value product ID,if match found we are displaying.
			 //Final out is sorted
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
		//IF NO MATCH FOUND ERROR MESSAGE IS DISPLAYED
		if(productExist==Boolean.FALSE)
			System.out.println(IMSConstants.ERRORPRODUCTNOTPRESENT);
		}
	
	/**
	 * searchByOtherAttribute
	 * This method is used in Menu 2 to search the product by any attribute in 2.2
	 * @param imsImplementation
	 * @throws IOException
	 */
	public void searchByOtherAttribute(IMSImplementation imsImplementation) throws IOException {
		boolean productExist = Boolean.FALSE;
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(IMSConstants.ENTERATTRIBUTESTOSEARCH);
		Boolean listClear = Boolean.TRUE;
		String otherAttribute = imsImplementation.brReader.readLine();
		imsImplementation.setDataFile(IMSConstants.HARDWARE);
		String trigger = IMSConstants.ITERATE;
		fileReadToList(imsImplementation,listClear,trigger);
		populateMapWithProductAndID(imsImplementation,imsImplementation.dataFileList);
		if(imsImplementation.dataFileList!=null) {
			Collections.sort(imsImplementation.dataFileList);
			Iterator it = imsImplementation.dataFileList.iterator();
			while(it.hasNext()) {
				String data = (String) it.next();
				data = data.toLowerCase();
				if(data.contains(otherAttribute.toLowerCase())) {
					productExist = Boolean.TRUE;
					System.out.println(data);
				}
			}
		}
		if(productExist==Boolean.FALSE)
			System.out.println(IMSConstants.NOKEYWORDMATCHFOUND);
	}
	/**
	 * clearConsole
	 * Clear the console while exiting and display Thank you message.
	 */
	public static void clearConsole() {
		//CREATING A BLANK MESSAGE TO CLEAR CONSOLE 
		//AND DISPLAYING THANK YOU MESSAGE.
		for (int i = 0; i < 50; ++i) System.out.println();
		System.out.print(IMSConstants.THANKYOU);
		for (int i = 0; i <5; ++i) System.out.println();
	}
	/**
	 * readDataFile
	 * This method is called when user selects Menu 1,it loads the default data file 
	 * and also a option for user to enter data file is provided.
	 * @param imsImplementation
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readDataFile(IMSImplementation imsImplementation) throws IOException, FileNotFoundException {
		//ASK THE USER TO ENTER THE INPUT FILE
			imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(IMSConstants.ENTERDATAFILE);
			imsImplementation.setDataFile(imsImplementation.brReader.readLine());
			//AFTER INPUT FILE IS READ CALL defaultDataFileRead TO POPULLATE THE LIST
			if (imsImplementation.getDataFile() != null ) {
				defaultDataFileRead(imsImplementation);
			}
			//IF NOT PRESENT DISPLAY THE ERROR MESSAGE
			else {
				System.out.println(IMSConstants.ERRORINVALIDFILE);
			}
			redirect(imsImplementation);
		}
	/**
	 * defaultDataFileRead
	 * This method reads the default data file.
	 * @param imsImplementation
	 * @throws IOException 
	 */
	public void defaultDataFileRead(IMSImplementation imsImplementation) throws IOException {
		  //when application is loaded for first time this method is called to load the default data file to list.
		   if(!imsImplementation.fileNameList.contains(imsImplementation.getDataFile())) {
			Boolean listClear = Boolean.FALSE;
			String trigger = IMSConstants.ITERATE;
			fileReadToList(imsImplementation,listClear,trigger);
			//product ID and name map method is called to group all the similar product id and product
			if(imsImplementation.dataFileList!=null) {
				 populateMapWithProductAndID(imsImplementation,imsImplementation.dataFileList);
			}
		   }
		   else {
			   System.out.println(" File" +imsImplementation.getDataFile()+ "already exist");
		   }
		   //Add the files to file name list to tract all the read data files to avoid duplication when appending except hardware
		   if(!imsImplementation.getDataFile().equalsIgnoreCase(IMSConstants.HARDWARE)){
			   imsImplementation.fileNameList.add(imsImplementation.getDataFile());
		   }
	}
	/**
	 * fileReadToList
	 * This method is used to append all the data files that is been read
	 * @param imsImplementation
	 * @param listClear
	 * @param trigger
	 * @throws FileNotFoundException,IOException 
	 */
	private void fileReadToList(IMSImplementation imsImplementation,Boolean listClear,String trigger) throws FileNotFoundException,IOException {
		String str;
		//file is read and updated the arrayList. 
		File file = new File(imsImplementation.getDataFile() + IMSConstants.TXT);
		BufferedReader br = new BufferedReader(new FileReader(file));
		//list needs to cleared then we are re initializing the data list
		if(listClear) {
			imsImplementation.dataFileList = new ArrayList<String>();
		}
		if(trigger.equalsIgnoreCase(IMSConstants.NOITERATE)) {
			imsImplementation.searchList = new ArrayList<String>();
		}
		//Depending on iterate or not appropriate list is updated
		while ((str = br.readLine()) != null) {
			if(trigger.equalsIgnoreCase(IMSConstants.ITERATE)) {
				imsImplementation.dataFileList.add(str);
				} else {
					imsImplementation.searchList.add(str);
				}
			
	}
	}
	/**
	 * populateMapWithProductAndID
	 * This method is used to group all the products and product ID by their 
	 * respective values which is used by search in menu 2.1 
	 * @param imsImplementation
	 * @param ProductList
	 */
	private void populateMapWithProductAndID(IMSImplementation imsImplementation, ArrayList<String> ProductList) {
		//Product and ID map is initialized
		imsImplementation.productMap = new HashMap<String,ArrayList<String>>();
		 imsImplementation.productIDMap = new HashMap<String,ArrayList<String>>();
		ArrayList<String> listValues = null;
		//list is iterated to group the rows by product and productID 
		//and finally updated in map has key and value pairs.
		for (String list:ProductList) {
			String[] words=list.split(IMSConstants.recordDelimiter);
			//ProductMap update with coloumn1 unique values as keys
				if(words[1] != IMSConstants.PRODUCT && imsImplementation.productMap.containsKey(words[1])) {
					imsImplementation.productMap.get(words[1]).add(list);
				} else {
					listValues = new ArrayList<String>();
					listValues.add(list);
					imsImplementation.productMap.put(words[1], listValues);
				}
				//ProductMap update with coloumn 0 unique values as keys
				if(words[0] != IMSConstants.PRODUCTID && imsImplementation.productIDMap.containsKey(words[0])) {
					imsImplementation.productIDMap.get(words[0]).add(list);
				} else {
					listValues = new ArrayList<String>();
					listValues.add(list);
					imsImplementation.productIDMap.put(words[0], listValues);
				}
			}
	}
}
