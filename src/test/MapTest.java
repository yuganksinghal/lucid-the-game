package test;

import edu.virginia.engine.map.Map;

public class MapTest {

	public static void main(String[] args) {
		int i = 0;
		for (int j = 0; j < 2; j++) {
			switch (i) {
			case 0:
				System.out.println(i + " incrementing");
				i++;
				break;
			case 1:
				System.out.println(i);
				break;
			}
		}
	}

}
