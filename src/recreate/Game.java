package recreate;

import java.util.ArrayList;

import meal.Customer;

public class Game {
	
	private int life, score, seat, time, chance;
	public ArrayList<Customer> listCustomer = new ArrayList<>();

	public Game(int life, int score, int seat, int time) {
		this.life = life;
		this.score = score;
		this.seat = seat;
		this.time = time;
		this.chance = 0;
	}
	
	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int plus) {
		this.time = time + plus;
	}
	
}
