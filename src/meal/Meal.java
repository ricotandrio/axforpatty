package meal;

public abstract class Meal {
	protected String name;
	protected int time, price;
	protected boolean serve;
	
	public Meal(String name, int time, int price) {
		this.name = name;
		this.price = price;
		this.time = time;
		this.serve = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void setServe(boolean status) {
		this.serve = status;
	}
	
	public boolean getServe() {
		return this.serve;
	}
}
