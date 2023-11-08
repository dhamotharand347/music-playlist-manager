package com.xmplar.music.playlist.console;

import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonObject;

public class ConsoleMenu {
	
	/*
	 * For role selection "Admin" and "User"
	 */

	public void start() {
		try {
			Scanner scanner = new Scanner(System.in);
			boolean exit = false;

			while (!exit) {
				displayRoleMenu();
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					adminRole();
					exit = true;
					break;
				case 2:
					exit = true;
					System.out.println(String.format("\033[3mThank you for using the application!\033[0m"));
					break;
				default:
					System.out.println(String.format("\033[3mInvalid choice. Please select again.\033[0m"));
				}
			}
		} catch (Exception e) {
			System.out.println(String.format("\033[3mInvalid choice. Please select again.\033[0m"));
			start();
		}

	}
	/*
	 * For User related options
	 */
	private void adminRole() {

		try {
			Scanner scanner = new Scanner(System.in);
			boolean exit = false;

			while (!exit) {
				displayAdminMenu();
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					addUser();
					break;
				case 2:
					start();
					exit = true;
					break;

				default:
					System.out.println(String.format("\033[3mInvalid choice. Please select again.\033[0m"));
				}
			}
		} catch (Exception e) {
			System.out.println(String.format("\033[3mInvalid choice. Please enter again.\033[0m"));
			adminRole();
		}

	}

	/*
	 * Display options (Menu functions) start from here
	 */

	private void displayRoleMenu() {
		System.out.println();
		System.out.println(String.format("\033[4mHotel Management System:\033[0m"));
		System.out.println("1. Admin");
		System.out.print("Select an option: ");
	}

	private void displayAdminMenu() {
		System.out.println();
		System.out.println(String.format("\033[4mAdmin role:\033[0m"));
		System.out.println("1. Add Room");
		System.out.println("2. Exit");
		System.out.print("Select an option: ");
	}

	/*
	 * User role functions start from here
	 */

	/*
	 * STEPS: 1) Create a JsonObject to represent input data. 2) Add required
	 * properties to the JsonObject. 3) Convert the JsonObject to a JSON payload in
	 * string format. 4) Perform an HTTP operation to add the input data with the
	 * specified JSON payload and specific role. 5) Return the response message
	 */

	private void addUser() {

		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter admin name: ");
			String username = scanner.nextLine();
			
			System.out.print("Enter password: ");
			String password = scanner.nextLine();


				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("username", username);

				String jsonPayload = jsonObject.toString();
//				String response = HttpControllerClient.performOperation("addUser", jsonPayload, "user");
//				System.out.println(response);

		} catch (Exception e) {
			System.out.println(String.format("\033[3mEnter the correct username.\033[0m") + e.getMessage());
		}

	}

}
