package swing;
import java.io.File;

import javax.swing.*;
import contatti.ContactsList;

public class Main {

	public static void main(String[] args) {
		String username = args.length > 0 ? args[0] : "admin";
		
		JFrame frame = new JFrame("Rubrica");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		ContactsList rubrica;
		
		try {
		    File jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
		    File file = new File(jarDir, "credenziali_database.properties");
		    rubrica = new ContactsList(file.getAbsolutePath(), username);
		} catch (Exception ex) {
		    ex.printStackTrace();
		    JOptionPane.showMessageDialog(frame, "Errore nell'accesso al db.");
		    return;
		  
		}
		JTable table = rubrica.getTable();
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane);
		
		ImageIcon addContactIcon = new ImageIcon(Main.class.getResource("/resources/person_add_24dp_000000_FILL0_wght400_GRAD0_opsz24.png"));
		JButton newButton = new JButton(addContactIcon);
		newButton.addActionListener(e -> {
			rubrica.deselectContact();
			new PersonEditor("Nuovo contatto", rubrica);
		});
		
		ImageIcon editContactIcon = new ImageIcon(Main.class.getResource("/resources/person_edit_24dp_000000_FILL0_wght400_GRAD0_opsz24.png"));
		JButton editButton = new JButton(editContactIcon);
		editButton.addActionListener(e -> {
			if (table.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(frame, "Devi prima selezionare un contatto da modificare.");
				return;
			}
			new PersonEditor("Modifica contatto", rubrica);
			
		});
		
		ImageIcon deleteContactIcon = new ImageIcon(Main.class.getResource("/resources/person_remove_24dp_000000_FILL0_wght400_GRAD0_opsz24.png"));
		JButton deleteButton = new JButton(deleteContactIcon);
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
		JToolBar buttonPanel = new JToolBar();
		buttonPanel.add(newButton);
		buttonPanel.add(editButton);
		buttonPanel.add(deleteButton);
		frame.getContentPane().add(buttonPanel, "North");
		
		
		
		frame.setVisible(true);

	}

}
