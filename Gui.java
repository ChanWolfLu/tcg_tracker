package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Gui extends JFrame implements ActionListener {

	JButton button;
	Gui() {
		ImageIcon = icon = new ImageIcon();
		button = new JButton();
		button.setBounds (100, 100, 250, 100);
		button.addActionListener(this);
		button.setText("Button");
		button.setFocusable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(500,500);
		this.setVisible(true);
		this.add(button);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button) {
			System.out.println("poo");
		}
	}
}
