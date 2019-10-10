package Manager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Constant.IMSConstants;
import Implementation.IMSImplementation;
public class IMSManagerMenu4 {
	private static File backupFile;	

	public boolean addRecord(String filename, BufferedReader brReader) throws FileNotFoundException, IOException {

		boolean updateFlag = false;
		IMSImplementation.clearscr();

		File file = displayInventory(filename);
		System.out.println("\n\n");

		System.out.println("\n Enter the record values to add separated by double tab in the format : " + "\n"
				+ "Product  Model	Manufacturer  typeCode  LocationCode	MSRP	UNITCOST	DISCOUNTRATE	QTY");
		String record = brReader.readLine();
		BufferedReader brd = new BufferedReader(new FileReader(file));
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
		for (int i=0; i< 9-idlen; i++) {
			id = 0 + id;
		}
		record = "a" + id + IMSConstants.recordDelimiter + record;
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.newLine();
		bw.write(record);
		bw.close();
		brd.close();
		updateFlag = true;

		return updateFlag;
	}

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
		StringBuffer buff = new StringBuffer("");
		boolean recordFound = false;
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
		StringBuffer buff = new StringBuffer("");
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
					if (i == 6)
						buff.append(value).append(IMSConstants.recordDelimiter);
					else if (i==7) {
						Integer msrp = Integer.parseInt(value);
						Integer unitCost;
						Integer discount = Integer.parseInt(recordToken[8]);
						unitCost = msrp - ((discount * msrp) / 100);
						buff.append(unitCost.toString()).append(IMSConstants.recordDelimiter);
					}
					else
						buff.append(recordToken[i]).append(IMSConstants.recordDelimiter);
				}
				buff.append("\n");
				break;

			case "9":
				for (int i = 0; i < recordToken.length; i++) {
					if (i == 7) {
						Integer msrp = Integer.parseInt(recordToken[6]);
						Integer unitCost;
						Integer discount = Integer.parseInt(value);
						unitCost = msrp - ((discount * msrp) / 100);
						buff.append(unitCost.toString()).append(IMSConstants.recordDelimiter);
					} else if (i == 8)
						buff.append(value).append(IMSConstants.recordDelimiter);
					else
						buff.append(recordToken[i]).append(IMSConstants.recordDelimiter);
				}
				buff.append("\n");

				break;

			case "10":
				for (int i = 0; i < recordToken.length; i++) {
					if (i == 7)
						buff.append(value).append(IMSConstants.recordDelimiter);
					else
						buff.append(recordToken[i]).append(IMSConstants.recordDelimiter);
				}
				//buff.append("\n");

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
			System.out.println("\n	Requested record successfully modified.");
			updateFlag = true;
		} else {
			System.out.println("\n	Requested ProductId to modify not found. Exiting from the menu.....");
			updateFlag = false;
		}

		return updateFlag;
	}

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

	public void createBackup(StringBuffer buffer) throws FileNotFoundException, IOException {

		BufferedWriter bw = new BufferedWriter(new FileWriter(backupFile));
		bw.write(buffer.toString());
		bw.close();

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
