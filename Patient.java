package HOSPITAL_MANAGEMENT_SYSTEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;
	
	public Patient(Connection connection , Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;		
	}
	
	public void addPatient() {
		System.out.println("Enter patient Name :");
		String name = scanner.next();
		
		System.out.println("Enter patient Age : ");
		int age = scanner.nextInt();
		
		System.out.println("Enter patient Gender :");
		String gender = scanner.next();
		
		try {
			String query ="insert into patients(name,age,gender)values(?,?,?)";
			PreparedStatement preparedstatemnt = connection.prepareStatement(query);
			preparedstatemnt.setString(1,name);
			preparedstatemnt.setInt(2, age);
			preparedstatemnt.setString(3,gender);
			
			int rowsaffected = preparedstatemnt.executeUpdate();
			if(rowsaffected>0) {
				System.out.println("patient Added Successfully!!");
			}else {
				System.out.println("Failed to add Patient!!");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void viewPatients() {
		String query = "Select*from patients";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			ResultSet resultset = preparedstatement.executeQuery();
			System.out.println("Patients: ");
			System.out.println("");
			while(resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				int age = resultset.getInt("age");
				String  gender = resultset.getString("gender");
				System.out.println(id + " " + name + " " + age + " " + gender);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getPatientByid(int id) {
		String query ="select * from Patients where id = ? ";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement (query);
			preparedstatement.setInt(1,id);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
