package HOSPITAL_MANAGEMENT_SYSTEM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {
	private Connection connection;
	
	public Doctors(Connection connection ) {
		this.connection = connection;
	}
	
	
	public void viewDoctors() {
		String query = "Select*from doctors";
		
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			ResultSet resultset = preparedstatement.executeQuery();
			System.out.println("Doctors : ");
			System.out.println("");
			while(resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				String Specialization = resultset.getString("specialization");

				
				System.out.println(id + " " + name + " " + Specialization );
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorsByid(int id) {
		String query ="select * from doctors where id = ? ";
		
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
