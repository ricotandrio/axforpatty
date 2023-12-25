package recreate;

import java.util.ArrayList;
import java.util.Scanner;

import meal.Drink;
import meal.Food;

import java.io.File;

public class Main {
	static Scanner scan = new Scanner(System.in);
	
	static Account system = new Account();
	static ArrayList<Food> foodList = new ArrayList<>();
	static ArrayList<Drink> drinkList = new ArrayList<>();

	static File foodFile = new File("./src/db/food.txt");
	static File drinkFile = new File("./src/db/drink.txt");
	static File accountFile = new File("./src/db/players.txt");
	
	public static void main(String[] args) {
		readFood();
		readDrink();
		
		homepage();
		
		scan.close();
	}
	
	static void homepage() {
		int option;
		
		do {			
			clear();
			logo();

			system.readAccount();
			
			System.out.println("1. Login");
			System.out.println("2. Register");
			System.out.println("3. Exit");
			do {
				try {
					System.out.print(">> ");
					option = Integer.parseInt(scan.nextLine());
				} catch (Exception e) {
					option = -1;
					System.out.println("[!] input should be a number");
				}
			}while(option < 1 || option > 3);
			
			switch (option) {
			case 1:
				login();
				break;
			case 2:
				register();
				break;
			case 3:
				System.out.println("[!] byee ...");
				break;
			}
			
			while(system.getCurrentUser() != null) {
				gamemenu();
			}
		}while(option != 3);
	}
	
	static void gamemenu() {
		int option;
		do {
			clear();
			logo();
			
			System.out.println("Welcome, " + system.getCurrentUser().getName());
			System.out.println("1. Play Game");
			System.out.println("2. View Scoreboard");
			System.out.println("3. Exit");
			
			do {
				try {
					System.out.print(">> ");
					option = Integer.parseInt(scan.nextLine());
				} catch (Exception e) {
					option = -1;
				}
			}while(option < 1 || option > 3);
			
			switch(option) {
			case 1:
				game();
				enter();
				break;
			case 2:
				scoreBoard();
				enter();
				break;
			case 3:
				System.out.println("[!] byee ...");
				system.logout();
				enter();
				break;
			}
		}while(option != 3);
	}
	
	static void login() {
		clear();
		String name, pass;
		do {
			System.out.print("Username : ");
			name = scan.nextLine();
			System.out.print("Password : ");
			pass = scan.nextLine();
		}while(system.accountAuth(name, pass) == false);
	}
	
	static void register() {
		clear();
		User registerUser = new User();
		do {
			System.out.print("Input a unique username [5...20]: ");
			registerUser.setName(scan.nextLine());
		}while(registerUser.getName().length() < 5 || registerUser.getName().length() > 20);
		
		do {
			System.out.print("Input your passwordd [8..20]: ");
			registerUser.setPass(scan.nextLine());
		}while(registerUser.getPass().length() < 8 || registerUser.getPass().length() > 20);
		
		registerUser.setScore(100);
		system.writeAccount(registerUser);
		System.out.println("[*] account successfully created");
		
		enter();
	}
	
	static void scoreBoard() {
		System.out.println("Score Board");
		System.out.println("===============");
		system.displayRank();
	}
	
	static void game() {
		MyThread thread = new MyThread(foodList, drinkList);
		
		thread.start();
		
		String cmd = " ";
		while(!cmd.equalsIgnoreCase("exit")) {
			cmd = scan.nextLine();
			
			thread.serve(cmd);
		}
		
		thread.setStopper(true);
		system.getCurrentUser().setScore(
			system.getCurrentUser().getScore() + thread.getScore()
		);
		
		system.updateCurrUser();
		
		System.out.println("[*] game end, score updated");
	}
	
	static void readFood() {
		try {
			Scanner scanFood = new Scanner(foodFile);
			
			while(scanFood.hasNextLine()) {
				String[] result = system.deserialize(scanFood.nextLine());
				
				if(result.length > 0) {
					foodList.add(new Food(result[0], Integer.parseInt(result[1]), Integer.parseInt(result[2])));
				}
			}
				
			scanFood.close();
		} catch (Exception e) {
			System.out.println("[!] fail to read food file");
		}
	}
	
	static void readDrink() {
		try {
			Scanner scanDrink = new Scanner(drinkFile);
			
			while(scanDrink.hasNextLine()) {
				String[] result = system.deserialize(scanDrink.nextLine());
				
				if(result.length > 0) {
					drinkList.add(new Drink(result[0], Integer.parseInt(result[1]), Integer.parseInt(result[2])));
				}
			}
			
			scanDrink.close();
		} catch (Exception e) {
			System.out.println("[!] fail to read drink file");
		}
	}
	
	static void enter() {
		System.out.println("[*] press enter to continue ...");
		scan.nextLine();
	}
	
	static void clear() {
		for(int i = 0; i < 32; i++) {
			System.out.println("");
		}
	}
	
	static void logo() {
		System.out.println("\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "▄▀█?▀▄▀?█▀▀?█▀█?█▀█?█▀█?▄▀█?▀█▀?▀█▀?█▄█\r\n"
				+ "█▀█?█░█?█▀░?█▄█?█▀▄?█▀▀?█▀█?░█░?░█░?░█░\r\n"
				+ "");

	}
	
	static void gameEnd() {
		System.out.println("\r\n" + 
				  "█░░?█▀▀?█░█?█▀▀?█░░? ?█▀▀?█░░?█▀▀?▄▀█?█▀█?█▀▀?█▀▄\r\n"
				+ "█▄▄?██▄?▀▄▀?██▄?█▄▄? ?█▄▄?█▄▄?██▄?█▀█?█▀▄?██▄?█▄▀\r\n"
				+ "\r\n"
				+ "Type exit to stop playing or continue to continue...");
		scan.nextLine();
	}

}
