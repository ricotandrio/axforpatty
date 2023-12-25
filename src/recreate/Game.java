package recreate;

public class Game {
	
	private int life, score, patience, seat, time;

	public Game(int life, int score, int patience, int seat, int time) {
		this.life = life;
		this.score = score;
		this.patience = patience;
		this.seat = seat;
		this.time = time;
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

	public int getPatience() {
		return patience;
	}

	public void setPatience(int plus) {
		this.patience = patience + plus;
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
