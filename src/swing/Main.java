package swing;
import java.io.File;

import javax.swing.*;
import contatti.ContactsList;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Rubrica");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		ContactsList rubrica;
		
		try {
		    File jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
		    File file = new File(jarDir, "informazioni.txt");
		    rubrica = new ContactsList(file.getAbsolutePath());
		} catch (Exception ex) {
		    ex.printStackTrace();
		    JOptionPane.showMessageDialog(frame, "Errore nell'accesso al file delle informazioni.");
		    return;
		  
		}
		JTable table = rubrica.getTable();
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane);
		
		JButton newButton = new JButton("Nuovo");
		newButton.addActionListener(e -> {
			rubrica.deselectContact();
			new PersonEditor("Nuovo contatto", rubrica);
		});
		JButton editButton = new JButton("Modifica");
		editButton.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(frame, "Devi prima selezionare un contatto da modificare.");
				return;
			}
			new PersonEditor("Modifica contatto", rubrica);
			
		});
		
		JButton deleteButton = new JButton("Elimina");
		deleteButton.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(frame, "Devi prima selezionare un contatto da eliminare.");
				return;
			}
			int response = JOptionPane.showConfirmDialog(frame, "Eliminare la persona "+rubrica.getSelectedContact().getName().toUpperCase() + " " + rubrica.getSelectedContact().getSurname().toUpperCase() + "?",
					"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				rubrica.removeContact(rubrica.getSelectedContact());
			}
		});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		frame.getContentPane().add(buttonPanel, "South");
		
		
		
		frame.setVisible(true);

	}

}
