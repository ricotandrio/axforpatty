package recreate;

import java.util.Comparator;

public class User {
	private String name, pass;
	private int score;
	
	public User(String name, String pass, int score) {
		this.name = name;
		this.pass = pass;
		this.score = score;
	}
	
	public User() { }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
