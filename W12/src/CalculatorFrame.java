import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CalculatorFrame extends JFrame implements ActionListener {
	JButton[] btn = new JButton[24];
	JTextField result = new JTextField(20);
	double left = 0;
	double right = 0;
	char operation;
	//숫자를 입력할 때, 계속 입력받을 것인지, 연산자 클릭 후 다시 새로운 숫자를 입력할 것인지 판단하기 위해 도입
	boolean numberInputFinished = false;
	//결과 출력 이후 바로 이어서 연산하거나, 새로운 숫자를 입력하면 결과를 지우기 위해 도입
	boolean afterResult = false;

	CalculatorFrame() {
		setTitle("12171630 배성준 계산기");
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
		btn[21] = new JButton("←");

		btn[22] = new JButton("");
		btn[23] = new JButton("");

		for (int i = 0; i <= 21; i++) {
			btn[i].addActionListener(this);
		}

		result.setEditable(false);
		result.setText("" + 0);
		result.setFont(new Font("바탕", Font.PLAIN, 25));
		result.setHorizontalAlignment(JTextField.RIGHT);

		// 패널에 버튼, textfield 추가하고, 팬에 패널 추가
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
		String current = result.getText(); // 현재 Text로 적혀있는 값을 가져온다

		int idx = 0;
		// btn 배열 중 클릭한 버튼이 몇번째 index인지 찾음
		for (int i = 0; i <= 21; i++) {
			if (btn[i] == button) {
				idx = i;
				break;
			}
		}

		// 숫자버튼
		if (idx >= 0 && idx <= 9) {
			//결과 출력 이후에 바로 숫자를 입력할 경우, 이전 결과값을 사용하지 않는 것으로 판단하여 새로운 숫자입력받음
			if(afterResult) {
				left = 0;
				right = 0;
				afterResult = false;
				numberInputFinished = true;
			}
			//windows 계산기에서 0에서 시작하는 것을 보고 따라하다가 생긴 코드
			//0밖에 입력되어 있지 않는 경우 0을 지우고 출력
			//이전에 연산자를 클릭했을 경우 숫자를 새로 입력받음
			if ((current.length() == 1 && current.charAt(0) == '0') || numberInputFinished) {
				result.setText("" + idx);
				numberInputFinished = false;
			}
			//이전 입력된 숫자에 이어서 출력
			else
				result.setText(current + idx);
		}

		// 소수점
		if (idx == 10) {
			//소수점 여러번 입력하는 것을 방지하기 위함
			if(current.contains("."))
				return;
			else
				result.setText(current + '.');
		}

		// 단항연산자
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
			
			// left, right에 이미 들어있는 값을 단항연산자로 계산했을 경우
			// 그 위치에 그대로 넣어 1 + 2^2나 2^2 + 1과 같은 연산을 가능하게 함
			if(left == num) {
				left = n;
				right = 0;
			}
			else {
				right = n;
			}
		}

		// 이항연산자
		if (idx >= 15 && idx <= 18) {
			// 결과값에 바로 연산자를 입력할 경우 바로 이용할 것이라고 판단
			if(afterResult) {
				afterResult = false;
			}
			// 1+1 입력되어 있는 상황에서 연산자를 클릭했을 경우 1+1의 결과를 출력하고
			// 이어서 연산하기 위해 도입.
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
						result.setText("0으로 나눌 수 없습니다.");
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

		// =버튼
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
				// 0으로 나눌 경우 예외처리
				if (right == 0) {
					result.setText("0으로 나눌 수 없습니다.");
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

		// C버튼
		if (idx == 20) {
			result.setText("" + 0);
			left = 0;
			right = 0;
		}

		// ←버튼
		if (idx == 21) {
			int lastIndex = current.length() - 1;
			// 결과 출력 후 뒤로가기 버튼을 누르면 전부 삭제
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