package singleton;

// static ����� ����
// static �ʵ�, �޼���

// ��ü�� ���� Ŭ������ �ƴ�
// �ʵ尪, �޼��带 ������� �������� �����ϴ� Ŭ����
// �̱���(singleton) Ŭ������ ����� �ܺο��� ��ü ������ ���ϰ���
public class Calculator {
	private static Calculator calc = new Calculator();

	private final static double PI = 3.141592;

	// �⺻�����ڸ� �ܺο��� ���ٸ��ϰ���
	private Calculator() {

	}

	private static Calculator getInstance() {
		return calc;
	}

	static int plus(int a, int b) {
		return a + b;
	}

	static int minus(int a, int b) {
		return a - b;
	}

	static double getAreaCircle(int r) {
		return r * r * PI;
	}
}