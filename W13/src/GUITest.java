import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


class MyFrame extends JFrame{
	JLabel la = new JLabel("입력 후 엔터를 입력하세요");
	JTextField tf = new JTextField(20);
	JTextArea ta = new JTextArea(5, 20);
	JTextArea ta2 = new JTextArea(5, 20);
	JTextArea ta3 = new JTextArea(5, 20);
	Container c;
	MyFrame(){
		setBounds(100, 100, 300, 500);
		setTitle("키/마우스/이벤트 처리");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		tf.setFocusable(true);
		tf.requestFocus();
		
		c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(la);
		c.add(tf);
		c.add(new JScrollPane(ta));
		c.add(new JScrollPane(ta2));
		c.add(new JScrollPane(ta3));

		
		tf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.append(tf.getText() + "\n");
			}
		});
		
		tf.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				ta2.append("mouseClicked(" + x + ", " + y + ")\n");
			}
		});
		
		c.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				la.setLocation(x,y);
			}
		});
		
		tf.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int x = e.getKeyCode();
				char c = e.getKeyChar();
				String s = KeyEvent.getKeyText(x);
				ta3.append("KeyText:"+s+"  KeyCode:"+x+"  KeyChar:"+c+"\n");
				
				if(c == '%') {
					MyFrame.this.c.setBackground(Color.yellow);
				}
				else if(x == KeyEvent.VK_F1) {
					MyFrame.this.c.setBackground(Color.green);
				}
			}
		});
		
//		tf.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				ta2.append("mouseExited\n");
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				ta2.append("mouseEntered\n");
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				int x = e.getX();
//				int y = e.getY();
//				ta2.append("mouseClicked(" + x + ", " + y + ")\n");
//			}
//		});
		
		
	}
}

public class GUITest {

	public static void main(String[] args) {
		new MyFrame();
	}

}
