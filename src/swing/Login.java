package swing;
import java.io.File;
import java.sql.Connection;

import javax.swing.*;
import db.Database;
import contatti.User;


public class Login {
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		
		
		
		
		JPanel panel = new JPanel();
		JLabel userLabel = new JLabel("Username:");
		JTextField userText = new JTextField(20);
		JLabel passwordLabel = new JLabel("Password:");
		JPasswordField passwordText = new JPasswordField(20);
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(e -> {
			String username = userText.getText();
			String password = new String(passwordText.getPassword());
			
			try {
				var auth = User.authenticate(username, password);
				if (!auth) {
					JOptionPane.showMessageDialog(frame, "Invalid username or password.");
					return;
				}
				frame.dispose();
				Main.main(new String[]{username});
			}
			catch (Exception ex) {
			    ex.printStackTrace();
			    JOptionPane.showMessageDialog(frame, "Errore nell'accesso al db.");
			    return;
			}
		});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(userLabel);
		panel.add(userText);
		panel.add(passwordLabel);
		panel.add(passwordText);
		panel.add(loginButton);
		
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);
		
		
		
	}
}
