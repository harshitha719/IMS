package Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import Constant.IMSConstants;

import java.util.Scanner;

import Implementation.IMSImplementation;

public class IMSManager {
	public IMSConstants constants = new IMSConstants();
	public void redirect(IMSImplementation imsImplementation) throws IOException {
		System.out.println();
		System.out.println("1. Enter 0 to go back to MainMenu \n" + "" +
		"2. Enter 6 to exit" ) ;
		imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
		imsImplementation.setSelectedInput(imsImplementation.brReader.readLine());
		if (imsImplementation.getSelectedInput().equalsIgnoreCase("0")) {
			imsImplementation.startPoint();
		}
		if (imsImplementation.getSelectedInput().equalsIgnoreCase("6")) {
			
		}
		
	}

	public void searchByproductAndID(IMSImplementation imsImplementation) throws IOException {
		String searchProduct;
		boolean productExist = Boolean.FALSE;
		System.out.println(imsImplementation.constants.ENTERPRODUCT);
		searchProduct = imsImplementation.brReader.readLine();
		if(searchProduct!=null) {
			if(imsImplementation.productMap!=null && imsImplementation.productIDMap!=null ) {
					for (Entry<String, ArrayList<String>> entry : imsImplementation.productMap.entrySet())  {
						if(entry.getKey().equalsIgnoreCase(searchProduct)) {
							Iterator<String> iter = entry.getValue().iterator();
							while(iter.hasNext())
							System.out.println(iter.next());
							productExist = Boolean.TRUE;
					}
				}
				for (Entry<String, ArrayList<String>> entry : imsImplementation.productIDMap.entrySet())  {
						if(entry.getKey().equalsIgnoreCase(searchProduct)) {
							Iterator<String> iter = entry.getValue().iterator();
							while(iter.hasNext())
							System.out.println(iter.next());
							productExist = Boolean.TRUE;
						}
					}
				}
			}
		if(productExist==Boolean.FALSE)
			System.out.println(imsImplementation.constants.ERRORPRODUCTNOTPRESENT);
		redirect(imsImplementation);
		}

	public void readDataFile(IMSImplementation imsImplementation) throws IOException, FileNotFoundException {
			imsImplementation.brReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(imsImplementation.constants.ENTERDATAFILE);
			imsImplementation.setDataFile(imsImplementation.brReader.readLine());
			if (imsImplementation.getDataFile() != null && imsImplementation.getDataFile().equalsIgnoreCase(imsImplementation.constants.HARDWARE)) {
				imsImplementation.display = Boolean.TRUE;
				imsImplementation.manager.defaultDataFileRead(imsImplementation, imsImplementation.display);
			} else {
				System.out.println(imsImplementation.constants.ERRORINVALIDFILE);
				
			}
			redirect(imsImplementation);
		}

	public void defaultDataFileRead(IMSImplementation imsImplementation, Boolean display) throws FileNotFoundException {
			Scanner scanInput1 = new Scanner(new File(imsImplementation.getDataFile() + constants.TXT));
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
			if(display) {
			while (iter.hasNext()) {
					System.out.println(iter.next());
				}
			}
			if(imsImplementation.dataFileList!=null) {
				 imsImplementation.productMap = new HashMap<String,ArrayList<String>>();
				 imsImplementation.productIDMap = new HashMap<String,ArrayList<String>>();
				ArrayList<String> productList = null;
				ArrayList<String> productIDList = null;
				for (String list:imsImplementation.dataFileList) {
					String[] words=list.split(" ");
						if(words[1] != "Product" && imsImplementation.productMap.containsKey(words[1])) {
							productList.add(list);
						} else {
							productList = new ArrayList<String>();
							productList.add(list);
							imsImplementation.productMap.put(words[1], productList);
						}
						if(words[0] != "Product" && imsImplementation.productIDMap.containsKey(words[0])) {
							productIDList.add(list);
						} else {
							productIDList = new ArrayList<String>();
							productIDList.add(list);
							imsImplementation.productIDMap.put(words[0], productIDList);
						}
					}
			}
		}
}
