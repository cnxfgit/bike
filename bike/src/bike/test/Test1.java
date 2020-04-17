package bike.test;

import java.util.Random;
import java.util.Scanner;


public class Test1 {

	public static void main(String[] args) {
				
		Random random = new Random();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("请输入红包总金额:");
		int money = scanner.nextInt();
		System.out.println("请输入发红包总人数:");
		int people = scanner.nextInt();
		
		System.out.println("总金额为:" + money);
		System.out.println("总人数为:" + people);
		
		int rist = money;
		
		for (int i = 1; i < people ; i++) {
			int y = 1 + random.nextInt((rist - 1) / people * 2);
			System.out.println("第"+ i +"个红包金额:"+ y);
			rist = rist - y;
			System.out.println("剩余金额:" + rist);
			System.out.println();
			if (i == people - 1) {
				System.out.println("最后一个红包为::" + rist);
			}
		}

	}

}
