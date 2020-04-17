package bike.test;

import java.util.ArrayList;
import java.util.Collections;

public class Test2 {
	public static void main(String[] args) {
		
		ArrayList<String> poker = new ArrayList<String>();
		
		String[] colors = {"♠", "♥", "♣", "♦"};
		String[] num = {"2", "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3"};
		
		poker.add("大王");
		poker.add("小王");
		
		for (String string : num) {
			for (String string2 : colors) {
				poker.add(string2+string);
			}	
		}
		
		System.out.println(poker);
		
		ArrayList<String> p1 = new ArrayList<String>();
		ArrayList<String> p2 = new ArrayList<String>();
		ArrayList<String> p3 = new ArrayList<String>();
		ArrayList<String> d = new ArrayList<String>();
		
		Collections.shuffle(poker);
		
		for (int i = 0; i < poker.size(); i++) {
			if (i>50) {
				d.add(poker.get(i));
			}
			else if (i%3 == 0) {
				p1.add(poker.get(i));
			}
			else if (i%3 == 1) {
				p2.add(poker.get(i));
			}
			else if (i%3 == 2) {
				p3.add(poker.get(i));
			}
		}
		
		
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
		System.out.println(d);

	}
}
