package HOSPITAL_MANAGEMENT_SYSTEM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url = "jdbc:mysql://localhost:3308/Hospital_Management_System";
	private static final String username = "root";
	private static final String password = "Admin";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver Loded Successfully");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		Scanner scanner = new Scanner(System.in);

		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Esatablish Successfully");
			Patient patient = new Patient(connection, scanner);
			Doctors doctor = new Doctors(connection);

			while (true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patient");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your Choice :");
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					doctor.viewDoctors();
					System.out.println();
					break;
				case 4:
					bookAppointment(patient, doctor, connection,scanner);
					System.out.println();
					break;
				case 5:
					System.out.println("THANK YOU FOR VISITING HOSPITAL MANAGEMENT SYSTEM.....");
					return;
				default:
					System.out.println("Enter valid choice");
					break;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void bookAppointment(Patient patient , Doctors doctors ,Connection connection ,Scanner scanner) {
		System.out.println("Enter patient Id: ");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor Id: ");
		int doctorId = scanner.nextInt();
		System.out.println("Enter Appoinment date(yyyy-mm-dd):");
		String appointmentDate = scanner.next();
		
		if(patient.getPatientByid(patientId) && doctors.getDoctorsByid(doctorId)){
			if(checkDoctorAvailability(doctorId, appointmentDate, connection)){
				String appointmentQuery ="insert into appointments(patient_id, doctor_id, appointment_date) values(?,?,?)";
					try {
						PreparedStatement preparedstatement = connection.prepareStatement(appointmentQuery);
						preparedstatement.setInt(1, patientId);
						preparedstatement.setInt(2, doctorId);
						preparedstatement.setString(3, appointmentDate);
						int rowsAffected = preparedstatement.executeUpdate();
						if(rowsAffected >0) {
							System.out.println("Appointment Booked !");
						}else{
							System.out.println("Failed to Book Appointment!");
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
			}else {
				System.out.println("Doctor not available on this date!!");
			}
			}
			else {
				System.out.println("Either doctor or Patient Doesn't exist!!!");
			}
		}

	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		String query = "Select count(*) from appointments where doctor_id =? and appointment_date =?";

		try {
			PreparedStatement preparedstatements = connection.prepareStatement(query);
			preparedstatements.setInt(1, doctorId);
			preparedstatements.setString(2, appointmentDate);
			ResultSet resultset = preparedstatements.executeQuery();
			if (resultset.next()) {
				int count = resultset.getInt(1);
				if (count == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}
