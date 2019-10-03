package Manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import Constant.IMSConstants;

import java.util.Scanner;

import Implementation.IMSImplementation;

public class IMSManager {
	public IMSConstants constants = new IMSConstants();

	public void redirect(IMSImplementation imsImplementation)
			throws IOException {
		System.out.println();
		System.out.println("1. Enter 0 to go back to MainMenu \n" + ""
				+ "2. Enter 6 to exit");
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(
				System.in));
		imsImplementation.setSelectedInput(imsImplementation.brReader
				.readLine());
		if (imsImplementation.getSelectedInput().equalsIgnoreCase("0")) {
			imsImplementation.startPoint();
		}
		if (imsImplementation.getSelectedInput().equalsIgnoreCase("6")) {

		}

	}

	public void searchByproductAndID(IMSImplementation imsImplementation)
			throws IOException {
		String searchProduct;
		boolean productExist = Boolean.FALSE;
		System.out.println(imsImplementation.constants.ENTERPRODUCT);
		searchProduct = imsImplementation.brReader.readLine();
		if (searchProduct != null) {
			if (imsImplementation.productMap != null
					&& imsImplementation.productIDMap != null) {
				for (Entry<String, ArrayList<String>> entry : imsImplementation.productMap
						.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(searchProduct)) {
						Iterator<String> iter = entry.getValue().iterator();
						while (iter.hasNext())
							System.out.println(iter.next());
						productExist = Boolean.TRUE;
					}
				}
				for (Entry<String, ArrayList<String>> entry : imsImplementation.productIDMap
						.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(searchProduct)) {
						Iterator<String> iter = entry.getValue().iterator();
						while (iter.hasNext())
							System.out.println(iter.next());
						productExist = Boolean.TRUE;
					}
				}
			}
		}
		if (productExist == Boolean.FALSE)
			System.out
					.println(imsImplementation.constants.ERRORPRODUCTNOTPRESENT);
		redirect(imsImplementation);
	}

	public void readDataFile(IMSImplementation imsImplementation)
			throws IOException, FileNotFoundException {
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.println(imsImplementation.constants.ENTERDATAFILE);
		imsImplementation.setDataFile(imsImplementation.brReader.readLine());
		if (imsImplementation.getDataFile() != null
				&& imsImplementation.getDataFile().equalsIgnoreCase(
						imsImplementation.constants.HARDWARE)) {
			imsImplementation.display = Boolean.TRUE;
			imsImplementation.manager.defaultDataFileRead(imsImplementation,
					imsImplementation.display);
		} else {
			System.out.println(imsImplementation.constants.ERRORINVALIDFILE);

		}
		redirect(imsImplementation);
	}

	public void defaultDataFileRead(IMSImplementation imsImplementation,
			Boolean display) throws FileNotFoundException {
		Scanner scanInput1 = new Scanner(new File(
				imsImplementation.getDataFile() + constants.TXT));
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
		Iterator<String> iter = imsImplementation.dataFileList.iterator();
		if (display) {
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}
		}
		if (imsImplementation.dataFileList != null) {
			imsImplementation.productMap = new HashMap<String, ArrayList<String>>();
			imsImplementation.productIDMap = new HashMap<String, ArrayList<String>>();
			ArrayList<String> productList = null;
			ArrayList<String> productIDList = null;
			for (String list : imsImplementation.dataFileList) {
				String[] words = list.split(" ");
				if (words[1] != "Product"
						&& imsImplementation.productMap.containsKey(words[1])) {
					productList.add(list);
				} else {
					productList = new ArrayList<String>();
					productList.add(list);
					imsImplementation.productMap.put(words[1], productList);
				}
				if (words[0] != "Product"
						&& imsImplementation.productIDMap.containsKey(words[0])) {
					productIDList.add(list);
				} else {
					productIDList = new ArrayList<String>();
					productIDList.add(list);
					imsImplementation.productIDMap.put(words[0], productIDList);
				}
			}
		}
	}

	public void addRecord(String filename) throws IOException,
			FileNotFoundException {

		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
		}
		Scanner sc = new Scanner(System.in);
		System.out
				.println("\n Enter the record to add in the format : "
						+ "\n"
						+ "ProductID   Product  Model Manufacturer  typeCode  LocationCode	MSRP	UNITCOST	DISCOUNTRATE	QTY");
		String record = sc.nextLine();
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.newLine();
		bw.write(record);
		bw.close();
		//sc.close();
		System.out.println(record);

	}

	public void deleteRecord(String filename, String choice)
			throws FileNotFoundException, IOException {

		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
		}
		br.close();
		Scanner sc = new Scanner(System.in);
		System.out
				.println("\n Enter the ProductID or ProductName to delete based on earlier choice");
		String record = sc.nextLine();

		BufferedReader brd = new BufferedReader(new FileReader(file));
		String strg;
		String matchrec;
		StringBuffer buff = new StringBuffer("");
		while ((strg = brd.readLine()) != null) {
			String[] token = strg.split("\t");
			if (token != null && token.length != 0) {
				if (choice.equalsIgnoreCase("ID"))
					matchrec = token[0];
				else
					matchrec = token[1];

				if (!matchrec.equals(record)) {
					buff.append(strg).append("\n");
				}
			} else {
				System.out.println("Empty File. Nothing to delete");
			}
		}
		brd.close();

		// empty the contents of file to write only the records not deleted by
		// the user
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		pw.write("");
		pw.flush();
		pw.close();

		// write the non deleted records to same file
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		// bw.newLine();
		bw.write(buff.toString());
		bw.close();
		//sc.close();
	}

	public void modifyRecord(String filename) throws IOException,
			FileNotFoundException {

		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
		}
		br.close();
		Scanner sc = new Scanner(System.in);
		System.out.println("\n Enter the ProductID to make updates");
		String record = sc.nextLine();
		System.out.println("\n Enter the column value to update. Press - \n"
				+ "		7 - for MSRP \n" + "		9 - for DiscountRate \n"
				+ "		10 - for Quantity \n");
		String column = sc.nextLine();

		System.out.println("\n Enter the new value to update");
		String value = sc.nextLine();

		BufferedReader brd = new BufferedReader(new FileReader(file));
		String strg;
		String matchrec = "";
		StringBuffer buff = new StringBuffer("");
		while ((strg = brd.readLine()) != null) {
			String[] token = strg.split("\t");

			if (token != null && token.length != 0) {
				if (!token[0].equals(record)) {
					buff.append(strg).append("\n");
				} else {
					matchrec = strg;
				}
			} else {
				System.out.println("\n Empty File. Nothing to update");
			}
		}
		brd.close();
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
					unitCost = msrp - ((discount*msrp)/100);
					buff.append(unitCost.toString()).append("\t");
				}
				else if (i == 9)
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
		//sc.close();

	}

	public void createBackup(String filename) throws IOException,
			FileNotFoundException {

		File file = new File(filename);
		File fileOut = new File("backup.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileOut, true));
		String str;
		while ((str = br.readLine()) != null) {
			bw.newLine();
			bw.write(str);
		}
		br.close();
		bw.close();

		System.out
				.println("\n File backup with the name backup.txt created at the same location of original file.");

	}

}
