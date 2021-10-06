package exam;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class BankApplication {

	// Map �������� ������ Map ������ Ÿ��(Inteface)
	// = HashMap
	// = HashTable
	// = TreeMap
	//
	// �����ϴ� �ڷᱸ���� ���� ���� �޼��带 ȣ���ϴ���
	// �������� ó������� �ٸ�

	// ���¸�� Map ��ü
	// Map<ŰŸ��, ��Ÿ��> ������ = new HashMap<ŰŸ��, ��Ÿ��>();
	private static Map<String, Account> accounts = new HashMap<String, Account>();

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		boolean run = true;
		while (run) {
			System.out.println("----------------------------------------------------------");
			System.out.println("1.���»��� | 2.���¸�� | 3.���� | 4.��� | 5.����");
			System.out.println("----------------------------------------------------------");
			System.out.print("����> ");

			try {
				int selectNo = scanner.nextInt();

				if (selectNo == 1) {
					createAccount();
				} else if (selectNo == 2) {
					accountList();
				} else if (selectNo == 3) {
					deposit();
				} else if (selectNo == 4) {
					withdraw();
				} else if (selectNo == 5) {
					run = false;
				} else if (selectNo > 5 || selectNo < 1) {
					System.out.println("1~5�� ���ڸ� �Է��� �ּ���.");
				}
			} catch (InputMismatchException exception) {
				break;
			}
		}

		System.out.println("���α׷� ����");
	}

	// ���»����ϱ�(�����߰��ϱ�)
	private static void createAccount() {
		System.out.println("-----------------");
		System.out.println("���»���");
		System.out.println("-----------------");
		System.out.println("���¹�ȣ: ");

		String Ano = scanner.next();

		if (accounts.containsKey(Ano)) {
			System.out.println("�ش� ���´� �̹� �����մϴ�");
			return;
		}

		System.out.println("������: ");
		String Owner = scanner.next();

		System.out.println("�ʱ��Աݾ�: ");
		int Balance = scanner.nextInt();

		if (Balance <= 10) {
			System.out.println("�ּ� �Է� �ݾ��� 10���Դϴ�.");
		} else {
			Account account = new Account(Ano, Owner, Balance);
			accounts.put(Ano, account);
			System.out.println("���°� �����Ǿ����ϴ�.");
		}

	}

	// ���¸�Ϻ���
	private static void accountList() {
		if (accounts.isEmpty()) {
			System.out.println("���� ����� �����ϴ�.");
			return;
		}

		System.out.println("-----------");
		System.out.println("���¸��");
		System.out.println("-----------");

		for (Account account : accounts.values()) {
			String Owner = account.getOwner();
			int balance = account.getBalance();
			System.out.println(account.getAno() + "\t" + Owner + "\t" + balance);
		}

	}

	// �����ϱ�(�ʵ尪����)
	private static void deposit() {
		if (accounts.isEmpty()) {
			System.out.println("������ ���� ����� �����ϴ�.");
			return;
		}

		System.out.println("--------");
		System.out.println("����");
		System.out.println("--------");
		System.out.println("���¹�ȣ: ");
		String Ano = scanner.next();
		if (accounts.containsKey(Ano)) {
			System.out.println("���ݾ�: ");
			int depo = scanner.nextInt();
			Account account = accounts.get(Ano);
			account.setBalance(account.getBalance() + depo);
			System.out.println("���� �Ǿ����ϴ�.");
		}
	}

	// ����ϱ�(�ʵ尪����)
	private static void withdraw() {
		if (accounts.isEmpty()) {
			System.out.println("����� ���°� �����ϴ�.");
			return;
		}

		System.out.println("--------");
		System.out.println("���");
		System.out.println("--------");
		System.out.println("���¹�ȣ: ");
		String ano = scanner.next();
		if (accounts.containsKey(ano)) {
			System.out.println("��ݾ�: ");
			int withd = scanner.nextInt();
			Account account = accounts.get(ano);
			account.setBalance(account.getBalance() - withd);
			System.out.println("��� �Ǿ����ϴ�.");
		}
	}

}