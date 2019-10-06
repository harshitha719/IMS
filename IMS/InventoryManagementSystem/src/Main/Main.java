package Main;

import Implementation.IMSImplementation;

public class Main {
	public static void main(String arg[]) {
		try {
			IMSImplementation input = new IMSImplementation();
			input.startPoint();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
