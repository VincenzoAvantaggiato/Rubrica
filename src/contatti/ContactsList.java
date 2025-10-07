package contatti;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import db.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class ContactsList {

	private LinkedList<Person> contacts;
	private String filepath;
	private ContactsTableModel model;
	private JTable table;
	private Database database;
	private Connection dbConnection;
	private String owner;
	
	public ContactsList(String filepath, String owner) {
		this.filepath = filepath;
		this.model = new ContactsTableModel();
		this.contacts = new LinkedList<Person>();
		this.table = new JTable();
		this.owner = owner;
		table.setModel(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		try {
			database = new Database(filepath, "rubrica");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			dbConnection = database.connect();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		readFromDB();
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
		int newId =saveContactToDB(p);
		p.setId(newId);
		refreshTable();
	}
	
	public void updateContact(Person oldP, Person newP) {
		newP.setId(oldP.getId());
		updateContactToDB(newP);
		contacts.set(contacts.indexOf(oldP), newP);
		refreshTable();
	}
	
	public void removeContact(Person p) {
		contacts.remove(p);
		removeContactFromDB(p);
		refreshTable();
	}
	
	public int saveContactToDB(Person p) {
		try {
			
			PreparedStatement insertQuery=dbConnection.prepareStatement("INSERT INTO Contatti (nome, cognome, indirizzo, telefono, eta, owner_username) VALUES (?, ?, ?, ?, ?, ?)");
			insertQuery.setString(1, p.getName());
			insertQuery.setString(2, p.getSurname());
			insertQuery.setString(3, p.getAddress());
			insertQuery.setString(4, p.getPhone_no());
			insertQuery.setInt(5, p.getAge());
			insertQuery.setString(6, owner);
			insertQuery.executeUpdate();
			PreparedStatement idQuery=dbConnection.prepareStatement("SELECT LAST_INSERT_ID() AS id");
			var result=idQuery.executeQuery();
			if (result.next()) {
				int id=result.getInt("id");
				return id;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}
	
	public void updateContactToDB(Person p) {
		try {
			PreparedStatement updateQuery=dbConnection.prepareStatement("UPDATE Contatti SET nome=?, cognome=?, indirizzo=?, telefono=?, eta=? WHERE id=? and owner_username=?");
			updateQuery.setString(1, p.getName());
			updateQuery.setString(2, p.getSurname());
			updateQuery.setString(3, p.getAddress());
			updateQuery.setString(4, p.getPhone_no());
			updateQuery.setInt(5, p.getAge());
			updateQuery.setInt(6, p.getId());
			updateQuery.setString(7, owner);
			updateQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void removeContactFromDB(Person p) {
		try {
			PreparedStatement deleteQuery=dbConnection.prepareStatement("DELETE FROM Contatti WHERE id=? and owner_username=?");
			deleteQuery.setInt(1, p.getId());
			deleteQuery.setString(2, owner);
			deleteQuery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void saveToDB() {
		try {
			PreparedStatement deleteQuery=dbConnection.prepareStatement("DELETE FROM Contatti");
			deleteQuery.executeUpdate();
			
			for (Person p : contacts) {
				PreparedStatement insertQuery=dbConnection.prepareStatement("INSERT INTO Contatti (nome, cognome, indirizzo, telefono, eta, owner_username) VALUES (?, ?, ?, ?, ?, ?)");
				insertQuery.setString(1, p.getName());
				insertQuery.setString(2, p.getSurname());
				insertQuery.setString(3, p.getAddress());
				insertQuery.setString(4, p.getPhone_no());
				insertQuery.setInt(5, p.getAge());
				insertQuery.setString(6, owner);
				insertQuery.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void readFromDB() {
		contacts.clear();
		
		try {
			PreparedStatement query=dbConnection.prepareStatement("SELECT * FROM Contatti WHERE owner_username=?");
			query.setString(1, owner);
			var result=query.executeQuery();
			while(result.next()) {
				String name=result.getString("nome");
				String surname=result.getString("cognome");
				String address=result.getString("indirizzo");
				String phone_no=result.getString("telefono");
				int age=result.getInt("eta");
				int id=result.getInt("id");
				Person person = new Person(name, surname, address, phone_no, age, id);
				contacts.add(person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
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
