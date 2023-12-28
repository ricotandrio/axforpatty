package meal;

public abstract class Meal {
	protected String name;
	protected int time, price;
	
	public Meal(String name, int time, int price) {
		this.name = name;
		this.price = price;
		this.time = time;
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

}
