package bike.test;

import java.util.Random;
import java.util.Scanner;


public class Test1 {

	public static void main(String[] args) {
				
		Random random = new Random();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("���������ܽ��:");
		int money = scanner.nextInt();
		System.out.println("�����뷢���������:");
		int people = scanner.nextInt();
		
		System.out.println("�ܽ��Ϊ:" + money);
		System.out.println("������Ϊ:" + people);
		
		int rist = money;
		
		for (int i = 1; i < people ; i++) {
			int y = 1 + random.nextInt((rist - 1) / people * 2);
			System.out.println("��"+ i +"��������:"+ y);
			rist = rist - y;
			System.out.println("ʣ����:" + rist);
			System.out.println();
			if (i == people - 1) {
				System.out.println("���һ�����Ϊ::" + rist);
			}
		}

	}

}
