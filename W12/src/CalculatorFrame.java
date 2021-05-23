import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CalculatorFrame extends JFrame implements ActionListener {
	JButton[] btn = new JButton[24];
	JTextField result = new JTextField(20);
	double left = 0;
	double right = 0;
	char operation;
	//���ڸ� �Է��� ��, ��� �Է¹��� ������, ������ Ŭ�� �� �ٽ� ���ο� ���ڸ� �Է��� ������ �Ǵ��ϱ� ���� ����
	boolean numberInputFinished = false;
	//��� ��� ���� �ٷ� �̾ �����ϰų�, ���ο� ���ڸ� �Է��ϸ� ����� ����� ���� ����
	boolean afterResult = false;

	CalculatorFrame() {
		setTitle("12171630 �輺�� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 550);
		setVisible(true);
		setResizable(false);

		Container c = getContentPane();
		JPanel resultPanel = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));
		resultPanel.setBackground(Color.gray);
		buttonPanel.setBackground(Color.white);

		btn[0] = new JButton("0");
		btn[1] = new JButton("1");
		btn[2] = new JButton("2");
		btn[3] = new JButton("3");
		btn[4] = new JButton("4");
		btn[5] = new JButton("5");
		btn[6] = new JButton("6");
		btn[7] = new JButton("7");
		btn[8] = new JButton("8");
		btn[9] = new JButton("9");
		btn[10] = new JButton(".");

		btn[11] = new JButton("1/x");
		btn[12] = new JButton("x^2");
		btn[13] = new JButton("sqrt(x)");
		btn[14] = new JButton("+/-");

		btn[15] = new JButton("+");
		btn[16] = new JButton("-");
		btn[17] = new JButton("*");
		btn[18] = new JButton("/");

		btn[19] = new JButton("=");

		btn[20] = new JButton("C");
		btn[21] = new JButton("��");

		btn[22] = new JButton("");
		btn[23] = new JButton("");

		for (int i = 0; i <= 21; i++) {
			btn[i].addActionListener(this);
		}

		result.setEditable(false);
		result.setText("" + 0);
		result.setFont(new Font("����", Font.PLAIN, 25));
		result.setHorizontalAlignment(JTextField.RIGHT);

		// �гο� ��ư, textfield �߰��ϰ�, �ҿ� �г� �߰�
		resultPanel.add(result);

		buttonPanel.add(btn[22]);
		buttonPanel.add(btn[23]);
		buttonPanel.add(btn[20]);
		buttonPanel.add(btn[21]);

		buttonPanel.add(btn[11]);
		buttonPanel.add(btn[12]);
		buttonPanel.add(btn[13]);
		buttonPanel.add(btn[18]);

		buttonPanel.add(btn[7]);
		buttonPanel.add(btn[8]);
		buttonPanel.add(btn[9]);
		buttonPanel.add(btn[17]);

		buttonPanel.add(btn[4]);
		buttonPanel.add(btn[5]);
		buttonPanel.add(btn[6]);
		buttonPanel.add(btn[16]);

		buttonPanel.add(btn[1]);
		buttonPanel.add(btn[2]);
		buttonPanel.add(btn[3]);
		buttonPanel.add(btn[15]);

		buttonPanel.add(btn[14]);
		buttonPanel.add(btn[0]);
		buttonPanel.add(btn[10]);
		buttonPanel.add(btn[19]);

		c.add(resultPanel, BorderLayout.NORTH);
		c.add(buttonPanel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String current = result.getText(); // ���� Text�� �����ִ� ���� �����´�

		int idx = 0;
		// btn �迭 �� Ŭ���� ��ư�� ���° index���� ã��
		for (int i = 0; i <= 21; i++) {
			if (btn[i] == button) {
				idx = i;
				break;
			}
		}

		// ���ڹ�ư
		if (idx >= 0 && idx <= 9) {
			//��� ��� ���Ŀ� �ٷ� ���ڸ� �Է��� ���, ���� ������� ������� �ʴ� ������ �Ǵ��Ͽ� ���ο� �����Է¹���
			if(afterResult) {
				left = 0;
				right = 0;
				afterResult = false;
				numberInputFinished = true;
			}
			//windows ���⿡�� 0���� �����ϴ� ���� ���� �����ϴٰ� ���� �ڵ�
			//0�ۿ� �ԷµǾ� ���� �ʴ� ��� 0�� ����� ���
			//������ �����ڸ� Ŭ������ ��� ���ڸ� ���� �Է¹���
			if ((current.length() == 1 && current.charAt(0) == '0') || numberInputFinished) {
				result.setText("" + idx);
				numberInputFinished = false;
			}
			//���� �Էµ� ���ڿ� �̾ ���
			else
				result.setText(current + idx);
		}

		// �Ҽ���
		if (idx == 10) {
			//�Ҽ��� ������ �Է��ϴ� ���� �����ϱ� ����
			if(current.contains("."))
				return;
			else
				result.setText(current + '.');
		}

		// ���׿�����
		if (idx >= 11 && idx <= 14) {
			double num = Double.parseDouble(current);
			
			double n = 0;
			numberInputFinished = true;
			switch (idx) {
			case 11:
				n = 1 / num;
				break;
			case 12:
				n = num * num;
				break;
			case 13:
				n = Math.sqrt(num);
				break;
			case 14:
				n = -1 * num;
				break;
			}
			result.setText("" + n);
			
			// left, right�� �̹� ����ִ� ���� ���׿����ڷ� ������� ���
			// �� ��ġ�� �״�� �־� 1 + 2^2�� 2^2 + 1�� ���� ������ �����ϰ� ��
			if(left == num) {
				left = n;
				right = 0;
			}
			else {
				right = n;
			}
		}

		// ���׿�����
		if (idx >= 15 && idx <= 18) {
			// ������� �ٷ� �����ڸ� �Է��� ��� �ٷ� �̿��� ���̶�� �Ǵ�
			if(afterResult) {
				afterResult = false;
			}
			// 1+1 �ԷµǾ� �ִ� ��Ȳ���� �����ڸ� Ŭ������ ��� 1+1�� ����� ����ϰ�
			// �̾ �����ϱ� ���� ����.
			else if(left != 0 && right == 0) {
				right = Double.parseDouble(current);
				switch (operation) {
				case '+':
					result.setText("" + (left + right));
					break;
				case '-':
					result.setText("" + (left - right));
					break;
				case '*':
					result.setText("" + (left * right));
					break;
				case '/':
					if (right == 0) {
						result.setText("0���� ���� �� �����ϴ�.");
						left = 0;
						right = 0;
						return;
					} else {
						result.setText("" + (left / right));
					}
					break;
				}
				left = Double.parseDouble(result.getText());
				right = 0;
			}
			else {
				left = Double.parseDouble(current);
			}
			numberInputFinished = true;
			switch (idx) {
			case 15:
				operation = '+';
				break;
			case 16:
				operation = '-';
				break;
			case 17:
				operation = '*';
				break;
			case 18:
				operation = '/';
				break;
			}
		}

		// =��ư
		if (idx == 19) {
			right = Double.parseDouble(current);
			numberInputFinished = true;
			switch (operation) {
			case '+':
				result.setText("" + (left + right));
				break;
			case '-':
				result.setText("" + (left - right));
				break;
			case '*':
				result.setText("" + (left * right));
				break;
			case '/':
				// 0���� ���� ��� ����ó��
				if (right == 0) {
					result.setText("0���� ���� �� �����ϴ�.");
					left = 0;
					right = 0;
					return;
				} else {
					result.setText("" + (left / right));
				}
				break;
			}
			left = Double.parseDouble(result.getText());
			right = 0;
			afterResult = true;
		}

		// C��ư
		if (idx == 20) {
			result.setText("" + 0);
			left = 0;
			right = 0;
		}

		// ���ư
		if (idx == 21) {
			int lastIndex = current.length() - 1;
			// ��� ��� �� �ڷΰ��� ��ư�� ������ ���� ����
			if (afterResult) {
				left = 0;
				right = 0;
				afterResult = false;
				result.setText("" + 0);
			}
			else if (lastIndex == 0 && current.charAt(lastIndex) == '0')
				return;
			else if (lastIndex == 0)
				result.setText("" + 0);
			else
				result.setText(current.substring(0, lastIndex));
		}
	}
}