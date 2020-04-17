package bike.test;

import java.util.Scanner;


public class Test3 {
	
	static int[] num = {1,2,3};
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int x = scanner.nextInt();
		
		name(x);
	}
	
	public static void name(int num) {
		
		for (int i : Test3.num) {
			if (i  == num) {
				try {
					throw new TestException("´íÎó");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					return;
				}
				
			}
		}

		System.out.println("ÎÞ´ë");
	}

}
