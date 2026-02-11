/* GroceryItemOrder
 * Scott Elliott
*/
// This class stores information about a single grocery item being ordered.

public class GroceryItemOrder {
   private String name;
   private int quantity;
   private double pricePerUnit;
   
   // constructs a grocery item order with a name, quantity, and price per unit
   public GroceryItemOrder(String name, int quantity, double pricePerUnit) {        
      this.name = name;
      this.quantity = quantity;
      this.pricePerUnit = pricePerUnit;
   }
   
   // sets quantity of an item
   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }
   
   // gets and calculates cost of each item based on its quantity
   public double getCost() {
      return quantity * pricePerUnit;
   }
   
   // returns a string for each line item 
   public String toString() {
      return quantity + " of " + this.name;   
   }
}
