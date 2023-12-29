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
	
	static boolean worker;
	static int restaurantSize;
	static String cmd = " ";

	public static void main(String[] args) {
		worker = false;
		restaurantSize = 5;

		readFood();
		readDrink();
		
		homepage();
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
				scan.close();
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
			
			System.out.println("Welcome, " + system.getCurrentUser().getName() + " | Score: " + system.getCurrentUser().getScore());
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
				break;
			case 2:
				scoreBoard();
				break;
			case 3:
				System.out.println("[!] byee ...");
				system.logout();
				break;
			}

			enter();
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

		do{
			do {
				System.out.print("Input a unique username [5...20]: ");
				registerUser.setName(scan.nextLine());
			}while(registerUser.getName().length() < 5 || registerUser.getName().length() > 20);
			
			do {
				System.out.print("Input your password [8..20]: ");
				registerUser.setPass(scan.nextLine());
			}while(registerUser.getPass().length() < 8 || registerUser.getPass().length() > 20);

		}while(system.isExists(registerUser.getName()));
		
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
		
		while(!cmd.equalsIgnoreCase("exit") && MyThread.stopper == false) {
			cmd = scan.nextLine();
			
			thread.serve(cmd);
		}
		
		MyThread.stopper = true;

		int decrease = 0;
		if(cmd.equalsIgnoreCase("upgrade")){
			decrease = upgradeRestaurant(thread);
		}

		system.getCurrentUser().setScore(
			system.getCurrentUser().getScore() + thread.getScore() - decrease
		);
		
		system.updateCurrUser();
		
		System.out.println("\n[*] game end, score updated");
	}

	static int upgradeRestaurant(MyThread thread){
		clear();
		
		System.out.println("Hi, " + system.getCurrentUser().getName() + " Score: " + (system.getCurrentUser().getScore() + thread.getScore()) + "\n");

		System.out.println("1. Expand Restaurant - 2,850,000");
		System.out.println("2. Hire Worker - 4,000,000");
		System.out.println("3. Help");
		System.out.println("4. Exit");
		
		int p = -1;
		do {
			try {
				System.out.print(">> ");
				p = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				p = -1;
				System.out.println("input should be a number");
			}
		}while(p < 1 || p > 4);

		switch (p) {
			case 1:
				if(restaurantSize < 20){
					restaurantSize += 1;
					System.out.println("[*] restaurant upgraded, current size is " + restaurantSize);
					return 2850000;
				} else {
					System.out.println("[*] restaurant at max level, current size is " + restaurantSize);
				}
				break;
			case 2:
				if(worker == false){
					System.out.println("[*] worker hired");
					worker = true;
					return 4000000;
				} else {
					System.out.println("[*] maximum worker is one");
				}
				break;
			case 3:
				System.out.println("[?] Expand restaurant will add 1 extra seat for customers.");
				System.out.println("[?] Workers will complete the menu based on queue (First In First Out). The Cooking time for each worker will be 10 seconds.");
				break;
		}
		return 0;
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
				+ "Click 'enter' to quit or type 'upgrade' to continue ...");
	}

	static void gameLose() {
		System.out.println("\r\n" + 
			"█▄█?█▀█?█░█? ?█░░?█▀█?█▀?▀█▀ \r\n" + 
			" █░?█▄█?█▄█? ?█▄▄?█▄█?▄█?░█░\r\n" +
			"\r\n" + "Click 'enter' to quit or type 'upgrade' to continue ..."
		);
	}
	
}
