/* GroceryList
 * Scott Elliott
*/
// This class keeps track of a grocery list of items

public class GroceryList {
   private static final int MAX_ITEMS = 5;
   private GroceryItemOrder[] items;
   private int numItems;
   
   // constructs an empty grocery list with fixed capacity of MAX_ITEMS
   public GroceryList() {
      this.items = new GroceryItemOrder[MAX_ITEMS];
      this.numItems = 0;
   }
   
   // adds an item to the grocery list and returns true if successful
   public boolean add(GroceryItemOrder item) {
      if (numItems < items.length) {
         this.items[numItems] = item;
         numItems++;
         return true;
      } else {
         return false;
      }
   }
   
   // gets the total cost of all items in the grocery list
   public double getTotalCost() {
      double total = 0.0;
      for (int i = 0; i < numItems; i++) {
         total += this.items[i].getCost();
      }
      return total;
   }  
   
   // returns a string of all line items
   public String toString() {
      String output = "";
      for (int i = 0; i < numItems; i++) {
         output += this.items[i].toString() + "\n";
      }
      return output;
   }
}
