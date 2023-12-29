package recreate;

import java.util.ArrayList;

import meal.Customer;
import meal.Drink;
import meal.Food;

public class MyThread extends Thread {
	
	Game engine = new Game(5, 0, 5, 0);
	static boolean stopper;

	public MyThread (ArrayList<Food> foodList, ArrayList<Drink> drinkList) {
		stopper = false;
		engine.listCustomer.add(randomCustomer());
	}

	public Customer randomCustomer(){
		int foodListSize = Main.foodList.size();
		int randomFood = (int)(Math.random() * (foodListSize - 1) + 1);
		Food currFood = Main.foodList.get(randomFood);
		
		int drinkListSize = Main.drinkList.size();
		int randomDrink = (int)(Math.random() * (drinkListSize - 1) + 1);
		Drink currDrink = Main.drinkList.get(randomDrink);	

		return new Customer(currFood, currDrink, 10);
	}

	public synchronized void ui(int r) {
		System.out.println("\n");
		// System.out.println(stopper + " " + r + " " + engine.getChance() + " " + engine.getSeat() + " " + engine.listCustomer.size());
		System.out.println("Time : " + engine.getTime() + " || " + "Life : " + engine.getLife() + " || " + "Score : " + engine.getScore() + "\n");
		System.out.println("========================================================================================");
		System.out.println(" No  |  Patience     |  Food                    |   Drink                  |  Price    |");
		System.out.println("========================================================================================");
		
		for(int j = 0; j < engine.listCustomer.size(); j++) {
			Customer currCust = engine.listCustomer.get(j);

			System.out.printf(" %-2d  | ", (j+1));

			for(int i = 0; i < 14; i++) {
				System.out.print((i < currCust.patience) ? "#" : " ");
			}
			
			System.out.printf("| %-20s [%s] | %-20s [%s] | %-9d |\n", 
				currCust.cFood.getName(),
				currCust.serveFood == true ? "v" : " ",
				currCust.cDrink.getName(),
				currCust.serveDrink == true ? "v" : " ",
				(currCust.cFood.getPrice() + currCust.cDrink.getPrice()) * currCust.patience
			);			

			checkCustomer(currCust, j);
		}

		System.out.println("========================================================================================");

		System.out.println("Type the order to serve (case insensitive)");
		System.out.println("Type exit to stop playing");
	}
	
	@Override
	public void run() {
		
		while(stopper == false && engine.getLife() > 0 && engine.getTime() <= 60) {
			int rand = (int)(Math.random() * 100);
			ui(rand);
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(rand <= engine.getChance()) {
				for (Customer cust : engine.listCustomer) {
					cust.patience -= 1;
				}

				if(engine.listCustomer.size() < engine.getSeat()){
					engine.listCustomer.add(randomCustomer());
				}

				engine.setChance(0);
			}

			engine.setChance(engine.getChance() + 10);
			engine.setTime(1);
		}
		
		if(engine.getTime() >= 60) {
			Main.gameEnd();
		} else {
			Main.gameLose();
		}
		
		stopper = true;
	}
	
	public int getTime(){
		return engine.getTime();
	}

	public int getScore() {
		return engine.getScore();
	}

	public void serve(String serve) {
		boolean isFound = false;
		for(int i = 0; i < engine.listCustomer.size(); i++){
			Customer currCust = engine.listCustomer.get(i);

			if(currCust.cDrink.getName().equalsIgnoreCase(serve) && currCust.serveDrink == false){
				isFound = true;
				currCust.serveDrink = true;
				return;
			}

			if(currCust.cFood.getName().equalsIgnoreCase(serve) && currCust.serveFood == false){
				isFound = true;
				currCust.serveFood = true;
				return;
			}
		}
		
		if(isFound == false) {
			engine.setLife(engine.getLife() - 1);
		}
	}

	private void checkCustomer(Customer currCust, int index){
		if(currCust.serveDrink == true && currCust.serveFood == true) {
			int points = (currCust.cFood.getPrice() + currCust.cDrink.getPrice()) * currCust.patience;
			engine.setScore(engine.getScore() + points);
			engine.listCustomer.remove(index);
		}

		if(currCust.patience <= 0){
			engine.listCustomer.remove(index);
		}
	}
}
