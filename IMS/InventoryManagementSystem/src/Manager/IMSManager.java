package Manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

import Constant.IMSConstants;
import Implementation.IMSImplementation;

public class IMSManager {
	private static File backupFile;

	public static void redirect(IMSImplementation imsImplementation) throws IOException {
		System.out.println();		
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter 0 to go back to MainMenu \n" + "" + "Enter 6 to exit");
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
		if (searchProduct != null) {
			if (imsImplementation.productMap != null && imsImplementation.productIDMap != null) {
				for (Entry<String, ArrayList<String>> entry : imsImplementation.productMap.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(searchProduct)) {
						productExist = Boolean.TRUE;
						ArrayList<String> productList = entry.getValue();
						Collections.sort(productList);
						Iterator<String> iter = productList.iterator();
						while (iter.hasNext())
							System.out.println(iter.next());
					}
				}
				for (Entry<String, ArrayList<String>> entry : imsImplementation.productIDMap.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(searchProduct)) {
						productExist = Boolean.TRUE;
						ArrayList<String> productIDList = entry.getValue();
						Collections.sort(productIDList);
						Iterator<String> iter = productIDList.iterator();
						while (iter.hasNext())
							System.out.println(iter.next());
					}
				}
			}
		}

		if (productExist == Boolean.FALSE)
			System.out.println(IMSConstants.ERRORPRODUCTNOTPRESENT);
		redirect(imsImplementation);
	}

	public void searchByOtherAttribute(IMSImplementation imsImplementation) throws IOException {
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter attributes to search");
		String otherAttribute = imsImplementation.brReader.readLine();
		if (imsImplementation.dataFileList != null) {

		}

	}

	public static void clearConsole() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
		System.out.print("Thank you for using Inventory Management System");
		for (int i = 0; i < 5; ++i)
			System.out.println();
	}

	public void readDataFile(IMSImplementation imsImplementation) throws IOException, FileNotFoundException {
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(IMSConstants.ENTERDATAFILE);
		imsImplementation.setDataFile(imsImplementation.brReader.readLine());
		if (imsImplementation.getDataFile() != null) {
			defaultDataFileRead(imsImplementation, imsImplementation.display);
		} else {
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
		if (imsImplementation.dataFileList != null) {
			imsImplementation.productMap = new HashMap<String, ArrayList<String>>();
			imsImplementation.productIDMap = new HashMap<String, ArrayList<String>>();
			ArrayList<String> listValues = null;
			for (String list : imsImplementation.dataFileList) {
				String[] words = list.split(" ");
				if (words[1] != "Product" && imsImplementation.productMap.containsKey(words[1])) {
					imsImplementation.productMap.get(words[1]).add(list);
				} else {
					listValues = new ArrayList<String>();
					listValues.add(list);
					imsImplementation.productMap.put(words[1], listValues);
				}
				if (words[0] != "ProductID" && imsImplementation.productIDMap.containsKey(words[0])) {
					imsImplementation.productIDMap.get(words[0]).add(list);
				} else {
					listValues = new ArrayList<String>();
					listValues.add(list);
					imsImplementation.productIDMap.put(words[0], listValues);
				}
			}
		}
	}

	public boolean addRecord(String filename, Scanner sc) {

		boolean updateFlag = false;
		IMSImplementation.clearscr();

		try {
			File file = displayInventory(filename);
			System.out.println("\n\n");
			
			System.out.println("\n Enter the record values to add separated by tab in the format : " + "\n"
					+ "ProductID   Product  Model Manufacturer  typeCode  LocationCode	MSRP	UNITCOST	DISCOUNTRATE	QTY");
			String record = sc.nextLine();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.newLine();
			bw.write(record);
			bw.close();
			updateFlag = true;			

		} catch (Exception e) {			
			e.printStackTrace();
		}
		return updateFlag;
	}

	public boolean deleteRecord(String filename, String choice, Scanner sc) {

		boolean updateFlag = false;
		IMSImplementation.clearscr();

		try {
			File file = displayInventory(filename);
			System.out.println("\n\n");
						
			String record;
			if (choice.equalsIgnoreCase("ID")) {
				System.out.println("\n Enter the ProductID to delete");
				record = sc.nextLine();
			} else if (choice.equalsIgnoreCase("NAME")) {
				System.out.println("\n Enter the ProductName to delete");
				record = sc.nextLine();
			} else if (choice.equalsIgnoreCase("NAMEANDMODEL")) {
				System.out.println("\n Enter the ProductName and Model to delete separated by -. eg. ThinkPad-T40");
				record = sc.nextLine();
			} else {
				System.out.println("\n Enter the Manufacturer to delete records");
				record = sc.nextLine();
			}

			BufferedReader brd = new BufferedReader(new FileReader(file));
			String strg;
			String matchrec;
			StringBuffer buff = new StringBuffer("");
			boolean recordFound = false;
			while ((strg = brd.readLine()) != null) {
				String[] token = strg.split("\t");
				if (token != null && token.length != 0) {
					if (choice.equalsIgnoreCase("ID"))
						matchrec = token[0];
					else if (choice.equalsIgnoreCase("NAME"))
						matchrec = token[1];
					else if (choice.equalsIgnoreCase("NAMEANDMODEL"))
						matchrec = token[1] + "-" + token[2];
					else
						matchrec = token[3];

					if (!matchrec.equals(record)) {
						buff.append(strg).append("\n");
					} else
						recordFound = true;
				} else {
					System.out.println("Empty File. Nothing to delete");
					updateFlag = false;
				}
			}
			brd.close();

			if (recordFound) {
				// empty the contents of file to write only the records not deleted by
				// the user
				FileWriter fw = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fw);
				pw.write("");
				pw.flush();
				pw.close();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
				bw.write(buff.toString());
				bw.close();
				updateFlag = true;
			} else
				System.out.println("\n	Requested record to delete not found. Exiting from the menu.....");
			updateFlag = false;
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return updateFlag;
	}
	

	public boolean modifyRecord(String filename, Scanner sc) {

		boolean updateFlag = false;
		IMSImplementation.clearscr();
		
		try {
			File file = displayInventory(filename);
			System.out.println("\n\n");
						
			System.out.println("\n Enter the ProductID to make updates");
			String record = sc.nextLine();
			System.out.println("\n Enter the column value to update. Press - \n" + "		7 - for MSRP \n"
					+ "		9 - for DiscountRate \n" + "		10 - for Quantity \n");
			String column = sc.nextLine();

			System.out.println("\n Enter the new value to update");
			String value = sc.nextLine();

			BufferedReader brd = new BufferedReader(new FileReader(file));
			String strg;
			String matchrec = "";
			boolean recordFound = false;
			StringBuffer buff = new StringBuffer("");
			while ((strg = brd.readLine()) != null) {
				String[] token = strg.split("\t");

				if (token != null && token.length != 0) {
					if (!token[0].equals(record)) {
						buff.append(strg).append("\n");
					} else {
						recordFound = true;
						matchrec = strg;
					}
				} else {
					System.out.println("\n Empty File. Nothing to update");
				}
			}
			brd.close();

			if (recordFound) {
				String[] recordToken = matchrec.split("\t");
				switch (column) {
				case "7":
					for (int i = 0; i < recordToken.length; i++) {
						if (i == 7)
							buff.append(value).append("\t");
						else
							buff.append(recordToken[i]).append("\t");
					}
					buff.append("\n");
					break;

				case "9":
					for (int i = 0; i < recordToken.length; i++) {
						if (i == 8) {
							Integer msrp = Integer.parseInt(recordToken[6]);
							Integer unitCost;
							Integer discount = Integer.parseInt(value);
							unitCost = msrp - ((discount * msrp) / 100);
							buff.append(unitCost.toString()).append("\t");
						} else if (i == 9)
							buff.append(value).append("\t");
						else
							buff.append(recordToken[i]).append("\t");
					}
					buff.append("\n");

					break;

				case "10":
					for (int i = 0; i < recordToken.length; i++) {
						if (i == 7)
							buff.append(value).append("\t");
						else
							buff.append(recordToken[i]).append("\t");
					}
					buff.append("\n");

					break;
				default:
					break;
				}
				// empty the contents of file to write only the records not deleted by
				// the user
				FileWriter fw = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fw);
				pw.write("");
				pw.flush();
				pw.close();
				// write the updated and non updated records to same file
				BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
				bw.write(buff.toString());
				bw.close();				
				updateFlag = true;
			} else
				System.out.println("\n	Requested ProductId to modify not found. Exiting from the menu.....");
			updateFlag = false;
		} catch (Exception e) {			
			e.printStackTrace();
		}

		return updateFlag;
	}

	public StringBuffer changeRecordPreProcessor(String filename) {

		StringBuffer strbf = new StringBuffer("");
		try {
			File file = new File(filename);

			if (filename.contains("\\")) {
				filename = filename.replace("\\", "/");
				int index = filename.lastIndexOf("/");
				filename = filename.substring(0, index);
				backupFile = new File(filename + "backup.txt");
			} else if (filename.contains("/")) {
				int index = filename.lastIndexOf("/");
				filename = filename.substring(0, index);
				backupFile = new File(filename + "backup.txt");
			} else {
				backupFile = new File("backup.txt");
			}

			BufferedReader br = new BufferedReader(new FileReader(file + IMSConstants.TXT));
			String str;

			while ((str = br.readLine()) != null) {
				strbf.append(str).append("\n");
			}
			br.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return strbf;

	}

	public void createBackup(StringBuffer buffer) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(backupFile));
			bw.write(buffer.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private File displayInventory(String filename) throws FileNotFoundException, IOException {
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
		}
		br.close();
		return file;
	}
}
