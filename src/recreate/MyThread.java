package recreate;

import java.util.ArrayList;
import java.util.Scanner;

import meal.Drink;
import meal.Food;

public class MyThread extends Thread {
	Scanner scan = new Scanner(System.in);
	ArrayList<Food> foodList;
	ArrayList<Drink> drinkList;
	
	Game engine = new Game(5, 0, 14, 10, 0);
	private boolean stopper;
	
	public MyThread (ArrayList<Food> foodList, ArrayList<Drink> drinkList) {
		this.foodList = foodList;
		this.drinkList = drinkList;
		setStopper(false);
		
		for (Food food : foodList) {
			food.setServe(false);
		}
		for (Drink drink : drinkList) {
			drink.setServe(false);
		}
	}

	public synchronized void ui() {
		System.out.println("\n");
		System.out.println(stopper);
		System.out.println("Time : " + engine.getTime() + " || " + "Life : " + engine.getLife() + " || " + "Score : " + engine.getScore() + "\n");
		System.out.println("====================================================================================");
		System.out.println(" No  |  Patience     |  Food                |   Drink                  |  Price    |");
		System.out.println("====================================================================================");
		
		
		for(int j = 0; j < engine.getTime() / 10 + 1; j++) {
			System.out.printf(" %-2d  | ", (j+1));
			for(int i = 0; i < 14; i++) {
				System.out.print((i < engine.getPatience() + j) ? "#" : " ");
			}
			
			System.out.printf("| %-16s [%s] | %-20s [%s] | %-9d |\n", 
				foodList.get(j).getName(), 
				foodList.get(j).getServe() == true ? "v" : " ",
				drinkList.get(j).getName(),
				drinkList.get(j).getServe() == true ? "v" : " ",
				foodList.get(j).getPrice() + drinkList.get(j).getPrice()
			);			
		}

		System.out.println("====================================================================================");

		System.out.println("Type the order to serve (case insensitive)");
		System.out.println("Type exit to stop playing");
	}
	
	@Override
	public void run() {
		
		while(this.stopper == false) {
			ui();
			
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(engine.getTime() % 5 == 0) {
				engine.setPatience(-1);
			}
			engine.setTime(1);
		}
		
	}
	
	public int getScore() {
		return engine.getScore();
	}
	
	public void setStopper(boolean val) {
		this.stopper = val;
	}
	
	public void serve(String serve) {
		boolean isFound = false;
		for (Drink drink : drinkList) {
			if(drink.getName().equalsIgnoreCase(serve)) {
				isFound = true;
				drink.setServe(true);
				engine.setScore(engine.getScore() + 10);
				break;
			}
		}
		
		for (Food food : foodList) {
			if(food.getName().equalsIgnoreCase(serve)) {
				isFound = true;
				food.setServe(true);
				engine.setScore(engine.getScore() + 10);
				break;
			}
		}
		
		if(isFound == false) {
			engine.setLife(engine.getLife() - 1);
		}
	}
}
