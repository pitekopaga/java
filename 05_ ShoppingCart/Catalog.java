/* Catalog
 * Scott Elliott
*/
import java.util.*;

public class Catalog {
   private ArrayList<Item> items; // stores a list of items in the catalog
   private String name; // name of the catalog (groceries, materials, etc.)

   public Catalog(String name) { // constructor to create an empty catalog with given name
      this.name = name;
      this.items = new ArrayList<>();
   }

   public void add(Item next) {
      items.add(next); // adds an item to the end of the catalog list
   }

   public int size() {
      return items.size(); // returns current size of ArrayList
   }

   public Item get(int index) {
      if(index<0) {
         throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
      }
      return items.get(index); // retrieves an item by its index position
   }

   public String getName() {
      return name; // simple getter for catalog name
   }
}
