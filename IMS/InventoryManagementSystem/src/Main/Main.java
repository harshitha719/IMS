package Main;

import java.util.ArrayList;
import java.util.Collections;

import Implementation.IMSImplementation;

public class Main {
public static void main(String arg[]) throws  Exception{
	IMSImplementation input = new IMSImplementation();
	input.startPoint();
	ArrayList<String> list = new ArrayList<String>();
	list.add("1110220365  ThinkPad  N1    Lenovo  012 003 470 280 0    10");
	list.add("1110220360	ThinkPad  N1	Lenovo	011	001	500	300	0	100");

	list.add("1110220374	EPSON 	  D3	Dell	012	002	250	150	5	7");
			Collections.sort(list);
	System.out.println(list);
}


}
