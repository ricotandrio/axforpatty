package meal;

public class Customer {
    public int patience;
    public Food cFood;
    public Drink cDrink;
    public boolean serveFood, serveDrink;

    public Customer(Food cFood, Drink cDrink, int patience){
        this.cFood = cFood;
        this.cDrink = cDrink;
        this.patience = patience;
        this.serveFood = false;
        this.serveDrink = false;
    }

}
