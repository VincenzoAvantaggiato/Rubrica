package contatti;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;



public class ContactsList {

	private LinkedList<Person> contacts;
	private String filepath;
	private ContactsTableModel model;
	private JTable table;
	
	public ContactsList(String filepath) {
		this.filepath = filepath;
		this.model = new ContactsTableModel();
		this.contacts = new LinkedList<Person>();
		this.table = new JTable();
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		readFromFile();
	}
	
	private class ContactsTableModel extends DefaultTableModel {
		String[] columnNames = {"Nome", "Cognome", "Telefono"};
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false; 
		}
		@Override
	    public int getRowCount() {
	        return contacts == null ? 0 : contacts.size();
	    }
	    @Override
	    public int getColumnCount() {
	        return columnNames.length; 
	    }
	    @Override
	    public Object getValueAt(int row, int col) {
	        Person p = contacts.get(row);
	        if (p == null) return null;
	        if (col < 0 || col >= getColumnCount()) return null;
	        if (row < 0 || row >= getRowCount()) return null;
	        if (col == 0) return p.getName();
	        if (col == 1) return p.getSurname();
	        if (col == 2) return p.getPhone_no();
	        return null;
	        
	    }
	    
	    @Override
	    public String getColumnName(int col) {
	        return columnNames[col];
	    }

	    public void refresh() {
	        fireTableDataChanged();
	    }
	}
	
	public LinkedList<Person> getContacts() {
		return contacts;
	}
	
	
	public void addContact(Person p) {
		contacts.add(p);
		saveToFile();
		refreshTable();
	}
	
	public void updateContact(Person oldP, Person newP) {
		int index = contacts.indexOf(oldP);
		if (index != -1) {
			contacts.set(index, newP);
			saveToFile();
			refreshTable();
		}
	}
	
	public void removeContact(Person p) {
		contacts.remove(p);
		saveToFile();
		refreshTable();
	}
	
	public void saveToFile() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(this.filepath));
			for (Person p : contacts) {
				writer.println(p.getName() + ";" + p.getSurname() + ";" + p.getAddress() + ";" + p.getPhone_no() + ";" + p.getAge());
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Error writing to file: " + this.filepath);
		}
	}
	
	public void readFromFile() {
		contacts.clear();
		try {
			Scanner scanner = new Scanner(new File(this.filepath));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(";");
				if (parts.length == 5) {
					String name = parts[0].trim();
					String surname = parts[1].trim();
					String address = parts[2].trim();
					String phone_no = parts[3].trim();
					int age = Integer.parseInt(parts[4].trim());
					Person person = new Person(name, surname, address, phone_no, age);
					contacts.add(person);
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found: " + this.filepath);
		}
	}
	
	public JTable getTable() {	
		return table;
	}
	
	public void refreshTable() {
		model.refresh();
	}
	
	public Person getSelectedContact() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			return contacts.get(selectedRow);
		}
		return null;
	}
	
	public void deselectContact() {
		table.clearSelection();
	}
	
}
