package com.indus.training.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.indus.training.persist.entity.Student;
import com.indus.training.persist.exceptions.InvalidStudentDataException;
import com.indus.training.persist.impl.StudentDaoImpl;

/**
 * Provides User Interface where User can insert, delete, update, fetch Student
 * Details
 */
public class IndusStudentUserInterface {

	/**
	 * Displays Menu to the User
	 */
	private static void displayMenu() {
		System.out.println("******************************************************************************");
		System.out.println("*                          STUDENT MANAGEMENT SYSTEM                         *");
		System.out.println("*                           1. Insert Student                                *");
		System.out.println("*                           2. Delete Student                                *");
		System.out.println("*                           3. Fetch Student                                 *");
		System.out.println("*                           4. Update Student First Name                     *");
		System.out.println("*                           5. Update Student Last Name                      *");
		System.out.println("*                           6. Exit                                          *");
		System.out.println("******************************************************************************");

	}

	/**
	 * Gets choice from the User
	 * 
	 * @param scanner the Scanner object
	 * @return the choice entered by the user
	 */
	private static int getUserChoice(Scanner scanner) {
		int choice = 0;
		while (true) {
			System.out.print("Enter your choice: ");
			try {
				choice = Integer.parseInt(scanner.nextLine());
				if (choice < 1 || choice > 6) {
					System.out.println("Please enter a number between 1 and 6.");
				} else {
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number.");
			}
		}
		return choice;
	}

	/**
	 * Inserts a student into the data source
	 * 
	 * @param scanner    the Scanner Object
	 * @param studentDao the StudentDao Object
	 */
	private static void insertStudent(Scanner scanner, StudentDaoImpl studentDao) {
		try {
			int studentId = promptForStudentId(scanner);
			String studentFirstName = promptForStudentFirstName(scanner);
			String studentLastName = promptForStudentLastName(scanner);

			Student student = new Student(studentId, studentFirstName, studentLastName);

			boolean isInserted = studentDao.insertStudent(student);
			if (isInserted) {
				System.out.println("Student inserted successfully.");
			} else {
				System.out.println("A student with the provided ID already exists.");
			}
		} catch (SQLException e) {
			System.err.println("An error occurred while inserting the student: " + e.getMessage());
		}
	}

	/**
	 * Deletes a student from the data source
	 * 
	 * @param scanner    the Scanner Object
	 * @param studentDao the StudentDao Object
	 */
	private static void deleteStudent(Scanner scanner, StudentDaoImpl studentDao) {
		System.out.println("Enter a Student ID to delete");
		try {
			int studentId = promptForStudentId(scanner);
			boolean isDeleted = studentDao.deleteStudent(studentId);
			if (isDeleted) {
				System.out.println("Student Deleted Successfully");

			} else {
				System.out.println("A student with the provided ID does not exist.");
			}
		} catch (SQLException e) {
			System.err.println("An error occurred while deleting the student: " + e.getMessage());
		}

	}

	/**
	 * Fetches a student from the data source
	 * 
	 * @param scanner    the Scanner Object
	 * @param studentDao the StudentDao Object
	 */
	private static void fetchStudent(Scanner scanner, StudentDaoImpl studentDao) {
		System.out.println("Enter a Student ID to fetch");
		try {
			int studentId = promptForStudentId(scanner);
			Student stuObj = studentDao.fetchStudent(studentId);
			if (stuObj != null) {
				System.out.println("Student ID: " + stuObj.getStudentId());
				System.out.println("Student First Name: " + stuObj.getFirstName());
				System.out.println("Student Last Name: " + stuObj.getLastName());

			} else {
				System.out.println("A student with the provided ID does not exist.");
			}
		} catch (SQLException e) {
			System.err.println("An I/O error occurred while fetching the student: " + e.getMessage());
		} catch (InvalidStudentDataException e) {
			System.err.println("Invalid student data: " + e.getMessage());
		}

	}

	/**
	 * Updates Student First Name in the data source
	 * 
	 * @param scanner    the Scanner Object
	 * @param studentDao the StudentDao Object
	 */
	private static void updateStudentFirstName(Scanner scanner, StudentDaoImpl studentDao) {
		System.out.println("Enter a Student ID to Update");
		try {
			int studentId = promptForStudentId(scanner);
			String stuFirstName = promptForStudentFirstName(scanner);
			boolean isUpdated = studentDao.updateStudentFirstName(studentId, stuFirstName);
			if (isUpdated) {
				System.out.println("Student First Name Updated Successfully");
			} else {
				System.out.println("A student with the provided ID does not exist.");
			}

		} catch (SQLException e) {
			System.err.println("An I/O error occurred while updating the student First Name: " + e.getMessage());
		}
	}

	/**
	 * Updates Student Last Name in the data source
	 * 
	 * @param scanner    the Scanner Object
	 * @param studentDao the StudentDao Object
	 */
	private static void updateStudentLastName(Scanner scanner, StudentDaoImpl studentDao) {
		System.out.println("Enter a Student ID to Update");
		try {
			int studentId = promptForStudentId(scanner);
			String stuLastName = promptForStudentLastName(scanner);
			boolean isUpdated = studentDao.updateStudentLastName(studentId, stuLastName);
			if (isUpdated) {
				System.out.println("Student Last Name Updated Successfully");
			} else {
				System.out.println("A student with the provided ID does not exist.");
			}

		} catch (SQLException e) {
			System.err.println("An I/O error occurred while updating the student Last Name: " + e.getMessage());
		}
	}

	/**
	 * Prompts User for Student ID
	 * 
	 * @param scanner the Scanner Object
	 * @return the Student ID
	 */
	private static int promptForStudentId(Scanner scanner) {
		System.out.print("Enter Student ID: ");
		while (!scanner.hasNextInt()) {
			System.out.println("Invalid ID. Please enter a valid integer.");
			scanner.next();
		}
		int studentId = scanner.nextInt();
		scanner.nextLine();
		return studentId;
	}

	/**
	 * Prompts User for Student First Name
	 * 
	 * @param scanner the Scanner Object
	 * @return the Student First Name
	 */
	private static String promptForStudentFirstName(Scanner scanner) {
		System.out.print("Enter Student First Name: ");
		return scanner.nextLine().trim();
	}

	/**
	 * Prompts User for Student Last Name
	 * 
	 * @param scanner the Scanner Object
	 * @return the Student Last Name
	 */
	private static String promptForStudentLastName(Scanner scanner) {
		System.out.print("Enter Student Last Name: ");
		return scanner.nextLine().trim();
	}

	public static void main(String[] args) {
		try (

				Scanner scanner = new Scanner(System.in)) {
			StudentDaoImpl studentDao = new StudentDaoImpl();

			while (true) {
				displayMenu();
				int choice = getUserChoice(scanner);

				switch (choice) {
				case 1:
					insertStudent(scanner, studentDao);
					break;
				case 2:
					deleteStudent(scanner, studentDao);
					break;
				case 3:
					fetchStudent(scanner, studentDao);
					break;
				case 4:
					updateStudentFirstName(scanner, studentDao);
					break;
				case 5:
					updateStudentLastName(scanner, studentDao);
					break;
				case 6:
					System.out.println("Exiting application...");
					scanner.close();
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}
		} catch (Exception e) {
			System.err.println("An unexpected error occurred: " + e.getMessage());
		}
	}

}
