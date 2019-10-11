package Manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import Constant.IMSConstants;
import Implementation.IMSImplementation;

/**
 * 
 * @author Vartika S
 * 
 *         This manager class encloses methods to implement Add/update/Delete
 *         features of the IMS.
 *
 */

/*
 * UML
 * 
 * - backupFile: File
 * 
 * + addRecord(String, BufferedReader): boolean + deleteRecord(String,
 * BufferedReader): boolean + modifyRecord(String, BufferedReader): boolean +
 * changeRecordPreprocessor(String): StringBuffer + createBackup(StringBuffer):
 * void + displayInventory(String): File
 * 
 * 
 */

public class IMSManagerMenu4 {
	private static File backupFile;

	/**
	 * 
	 * @param filename
	 * @param brReader
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean addRecord(String filename, BufferedReader brReader) throws FileNotFoundException, IOException {

		boolean updateFlag = false;
		IMSImplementation.clearscr();

		// Display inventory by default for the user.
		File file = displayInventory(filename);
		System.out.println("\n\n");

		System.out.println("\n Enter the record values to add separated by double tab in the format : " + "\n"
				+ "Product  Model	Manufacturer  typeCode  LocationCode	MSRP	UNITCOST	DISCOUNTRATE	QTY");
		String record = brReader.readLine();
		BufferedReader brd = new BufferedReader(new FileReader(file));

		// This logic is to read the last record from the inventory and generate the
		// next sequence ProductId to add the record.
		String strg;
		String lastRec = "";
		while ((strg = brd.readLine()) != null) {
			lastRec = strg;
		}
		String[] token = lastRec.split(IMSConstants.recordDelimiter);
		String id = token[0].substring(1);
		Integer productId = Integer.parseInt(id) + 1;
		id = productId.toString();
		int idlen = id.length();
		for (int i = 0; i < 9 - idlen; i++) {
			id = 0 + id;
		}
		// create the final record to write in file by concatenating the ProductId with
		// delimiter.
		record = "a" + id + IMSConstants.recordDelimiter + record;

		// Write the record using bufferedWriteer
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.newLine();
		bw.write(record);
		bw.close();
		brd.close();
		updateFlag = true;

		return updateFlag;
	}

	/**
	 * 
	 * @param filename
	 * @param choice
	 * @param brReader
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean deleteRecord(String filename, String choice, BufferedReader brReader)
			throws FileNotFoundException, IOException {

		boolean updateFlag = false;
		IMSImplementation.clearscr();

		File file = displayInventory(filename);
		System.out.println("\n\n");

		String record;
		if (choice.equalsIgnoreCase("ID")) {
			System.out.println("\n Enter the ProductID to delete");
			record = brReader.readLine();
		} else if (choice.equalsIgnoreCase("NAME")) {
			System.out.println("\n Enter the ProductName to delete");
			record = brReader.readLine();
		} else if (choice.equalsIgnoreCase("NAMEANDMODEL")) {
			System.out.println("\n Enter the ProductName and Model to delete separated by -. eg. ThinkPad-T40");
			record = brReader.readLine();
		} else {
			System.out.println("\n Enter the Manufacturer to delete records");
			record = brReader.readLine();
		}

		BufferedReader brd = new BufferedReader(new FileReader(file));
		String strg;
		String matchrec;
		StringBuffer buff = new StringBuffer();
		boolean recordFound = false;
		HashMap<String, String> map = new HashMap<String, String>();
		// read every record from file and split to fetch
		// ProductId/Name/Model/Manufacturer. Match this with the requested delete by
		// value provided y the user.
		while ((strg = brd.readLine()) != null) {
			String[] token = strg.split(IMSConstants.recordDelimiter);
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
			System.out.println("\n	Requested record successfully deleted.");
			updateFlag = true;
		} else {
			System.out.println("\n	Requested record to delete not found. Exiting from the menu.....");
			updateFlag = false;
		}

		return updateFlag;
	}

	/**
	 * 
	 * @param filename
	 * @param brReader
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public boolean modifyRecord(String filename, BufferedReader brReader) throws FileNotFoundException, IOException {

		boolean updateFlag = false;
		IMSImplementation.clearscr();

		File file = displayInventory(filename);
		System.out.println("\n\n");

		System.out.println("\n Enter the ProductID to make updates");
		String record = brReader.readLine();
		System.out.println("\n Enter the column value to update. Press - \n" + "		7 - for MSRP \n"
				+ "		9 - for DiscountRate \n" + "		10 - for Quantity \n");
		String column = brReader.readLine();

		System.out.println("\n Enter the new value to update");
		String value = brReader.readLine();

		BufferedReader brd = new BufferedReader(new FileReader(file));
		String strg;
		String matchrec = "";
		boolean recordFound = false;
		StringBuffer buff = new StringBuffer();

		// read every record from file and match against the value. Match this with the
		// requested update value of MSRP, Discount or Quantity provided by the user.
		while ((strg = brd.readLine()) != null) {
			String[] token = strg.split(IMSConstants.recordDelimiter);

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
			String[] recordToken = matchrec.split(IMSConstants.recordDelimiter);
			switch (column) {
			case "7":
				for (int i = 0; i < recordToken.length; i++) {
					// update to MSRP
					if (i == 6)
						buff.append(value).append(IMSConstants.recordDelimiter);

					// update to Unit Price based on MSRP update
					else if (i == 7) {
						Double msrp = Double.valueOf(value);
						Double unitCost;
						Double discount = Double.valueOf(recordToken[8]);
						unitCost = msrp - ((discount * msrp) / 100);
						buff.append(unitCost.toString()).append(IMSConstants.recordDelimiter);

						// keep the record as is for rest of the column values
					} else
						buff.append(recordToken[i]).append(IMSConstants.recordDelimiter);
				}
				buff.append("\n");
				break;

			case "9":
				for (int i = 0; i < recordToken.length; i++) {

					// update Unit Price based on updated discount rate
					if (i == 7) {
						Double msrp = Double.valueOf(recordToken[6]);
						Double unitCost;
						Double discount = Double.valueOf(value);
						unitCost = msrp - ((discount * msrp) / 100);
						buff.append(unitCost.toString()).append(IMSConstants.recordDelimiter);
					}
					// update Discount rate
					else if (i == 8)
						buff.append(value).append(IMSConstants.recordDelimiter);

					// keep the record as is for rest of the column values
					else
						buff.append(recordToken[i]).append(IMSConstants.recordDelimiter);
				}
				buff.append("\n");

				break;

			case "10":
				for (int i = 0; i < recordToken.length; i++) {

					// update quantity of product
					if (i == 9)
						buff.append(value).append(IMSConstants.recordDelimiter);
					else
						buff.append(recordToken[i]).append(IMSConstants.recordDelimiter);
				}
				// buff.append("\n");

				break;
			default:
				break;
			}
			StringBuffer strBuf = sortFileData(buff);
	        // empty the contents of file to write only the records not deleted by
			// the user
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			pw.write("");
			pw.flush();
			pw.close();
			// write the updated and non updated records to same file
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(strBuf.toString());
			bw.close();
			System.out.println("\n	Requested record successfully modified.");
			updateFlag = true;
		} else {
			System.out.println("\n	Requested ProductId to modify not found. Exiting from the menu.....");
			updateFlag = false;
		}

		return updateFlag;
	}

	/**
	 * @param buff
	 * @return
	 */
	private StringBuffer sortFileData(StringBuffer buff) {
		Map<String, String> map = new HashMap<String, String>();
		String [] rowToken = buff.toString().split("\n");
		for (String row: rowToken){
			String[] colToken = row.split(IMSConstants.recordDelimiter);
			map.put(colToken[0], row);
		}
		// TreeMap to store sorted values of HashMap 
		TreeMap<String, String> sorted = new TreeMap<>(); 
		sorted.putAll(map); 

		StringBuffer strBuf = new StringBuffer();
		for (Map.Entry<String, String> entry : sorted.entrySet()) {
			strBuf = strBuf.append(entry.getValue()).append("\n");
		}
		return strBuf;
	}

	/**
	 * This method is a pre-processor which loads the inventory in memory. This will
	 * be used to create a backup before the user exits the menu 4 options.
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public StringBuffer changeRecordPreProcessor(String filename) throws FileNotFoundException, IOException {

		StringBuffer strbf = new StringBuffer("");

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
		return strbf;

	}

	/**
	 * This method will actually write to a backup file before exit.
	 * 
	 * @param buffer
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void createBackup(StringBuffer buffer) throws FileNotFoundException, IOException {

		BufferedWriter bw = new BufferedWriter(new FileWriter(backupFile));
		bw.write(buffer.toString());
		bw.close();

	}

	/**
	 * This is a utility method to display the complete inventory to user before he
	 * makes any choice to update
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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
