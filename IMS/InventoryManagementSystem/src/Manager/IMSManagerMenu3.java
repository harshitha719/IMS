package Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class IMSManagerMenu3 {
	
	
	public void showInventory(String filename) throws FileNotFoundException, IOException {
		//function to display the entire inventory
		File file = new File(filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList<String> a1 = new ArrayList<String>(); 
		String str;
		//read the file data and store in the array list
		while ((str = br.readLine()) != null)
			a1.add(str);
		
		//sort the data in the array list
        Collections.sort(a1);
		//a1.sort(null);
        
        //display the data in array list
      //  System.out.println(a1.get(a1.size()-1));
        for (int i=0; i<a1.size(); i++) 
            System.out.println(a1.get(i));
        
		br.close();
	}
	
	public void showInventoryResults(String filename, String userInput,int columnNumber) throws FileNotFoundException, IOException {
		
		File file = new File(filename);
		BufferedReader brd = new BufferedReader(new FileReader(file));
		String strg;
		boolean recordFound = false;
		
		ArrayList<String> a1 = new ArrayList<String>(); 	
	        
	    //traverse the file, split each row based on tab delimiter and store the data in array list based on the search criteria
		while ((strg = brd.readLine()) != null) {
			String[] token = strg.split("\t\t");

			if (token != null && token.length != 0 && !token[0].equals("ProductID")) {
				if (columnNumber==8) //check if we have to show discounted items
				{
					if(!token[columnNumber].matches("0"))
					{
						//System.out.println(strg);
						a1.add(strg);
						recordFound=true;
					}
						
				}
				//filter the data based on the search criteria and store it into an array list
				else if (token[columnNumber].equals(userInput))
				{
					//System.out.println(strg);
					recordFound=true;
					a1.add(strg);
				}
			}

		}
        Collections.sort(a1);
        //a1.sort(null);
        //display the filtered data from the file
        if(recordFound)
        {
        	System.out.println("ProductID   Product  Model Manufacturer  typeCode  LocationCode	MSRP	UNITCOST	DISCOUNTRATE	QTY");
        	for (int i=0; i<a1.size(); i++) 
                System.out.println(a1.get(i));	
        }
        else System.out.println("No record found!");
        
		brd.close();
	}

}
