package swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.NumberFormatter;

import contatti.ContactsList;
import contatti.Person;
import java.awt.*;
import java.text.NumberFormat;

public class PersonEditor {
	JFrame frame;
	JPanel fieldsPanel = new JPanel(new GridLayout(5, 4, 0, 5));
	
	JLabel nameLabel = new JLabel("Nome*:");
	JTextField nameField = new JTextField(20);
	
	JLabel surnameLabel = new JLabel("Cognome:");
	JTextField surnameField = new JTextField(20);
	
	JLabel addressLabel = new JLabel("Indirizzo:");
	JTextField addressField = new JTextField(20);
	
	JLabel phoneLabel = new JLabel("Telefono*:");
	JTextField phoneField = new JTextField(20);
	
	NumberFormatter ageFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
	JLabel ageLabel = new JLabel("Età:");
	JTextField ageField;
	
	JToolBar buttonPanel = new JToolBar();
	JButton saveButton = new JButton("Salva 💾");
	JButton cancelButton = new JButton("Annulla ❌");
	
	public PersonEditor(String title, ContactsList contactslist) {
		this.frame = new JFrame(title);
		Person selectedContact = contactslist.getSelectedContact();
		
		ageFormatter.setValueClass(Integer.class);
		ageFormatter.setMinimum(0);
		ageFormatter.setMaximum(150);
		ageFormatter.setAllowsInvalid(true);
		ageField = new JFormattedTextField(ageFormatter);
		
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 200);
		
		frame.getContentPane();
		
		if (selectedContact != null) {
			nameField.setText(selectedContact.getName());
			surnameField.setText(selectedContact.getSurname());
			addressField.setText(selectedContact.getAddress());
			phoneField.setText(selectedContact.getPhone_no());
			ageField.setText(selectedContact.getAge() == -1 ? "" : Integer.toString(selectedContact.getAge()));
		}
		

		fieldsPanel.add(new JLabel());  
		fieldsPanel.add(nameLabel);
		fieldsPanel.add(nameField);
		fieldsPanel.add(new JLabel());  

		fieldsPanel.add(new JLabel());
		fieldsPanel.add(surnameLabel);
		fieldsPanel.add(surnameField);
		fieldsPanel.add(new JLabel());

		fieldsPanel.add(new JLabel());
		fieldsPanel.add(addressLabel);
		fieldsPanel.add(addressField);
		fieldsPanel.add(new JLabel());

		fieldsPanel.add(new JLabel());
		fieldsPanel.add(phoneLabel);
		fieldsPanel.add(phoneField);
		fieldsPanel.add(new JLabel());

		fieldsPanel.add(new JLabel());
		fieldsPanel.add(ageLabel);
		fieldsPanel.add(ageField);
		fieldsPanel.add(new JLabel());
		
		
		
		frame.getContentPane().add(fieldsPanel);
		
		
		saveButton.addActionListener(e -> {
			Person newPerson;
			nameField.setBorder(new JTextField().getBorder());
			phoneField.setBorder(new JTextField().getBorder());
			if (nameField.getText().trim().isEmpty()) {
				nameField.setBorder(new LineBorder(Color.RED));
			}
			if (phoneField.getText().trim().isEmpty()) {
				phoneField.setBorder(new LineBorder(Color.RED));
			}
			if (nameField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
				return;
			}
			int age;
			try {
				age = Integer.parseInt(ageField.getText());
			}
			catch (NumberFormatException ex) {
				age = -1;
			}
			newPerson = new Person(
					nameField.getText(),
					surnameField.getText(),
					addressField.getText(),
					phoneField.getText(),
					age,
					-1
					);
			if (selectedContact == null) {
				contactslist.addContact(newPerson);
			} else {
				contactslist.updateContact(selectedContact, newPerson);
			}
			frame.dispose();
		});
		cancelButton.addActionListener(e -> {
			frame.dispose();
		});
		
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		frame.getContentPane().add(buttonPanel, "North");
		
	
		
		frame.setVisible(true);
		
	}
	
	
	
}
