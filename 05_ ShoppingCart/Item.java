/* Item
 * Scott Elliott
*/
import java.text.*;
import java.util.*;

public class Item {
   private double price; // regular price per unit
   private int bulkQuantity; // minimum quantity required for bulk pricing
   private double bulkPrice; // special price for bulk quantities
   private NumberFormat formatter; // formats doubles to $ currency
   private String name; // item name

   public Item(String name, double price) {
      if (price < 0) { // validate price parameter
         throw new IllegalArgumentException("Price cannot be negative: " + price);
      }
      this.name = name; // creates an item without bulk pricing
      this.price = price; // initializes price if >= 0
      this.formatter = NumberFormat.getCurrencyInstance(); // initializes currency formatter
   }

   public Item(String name, double price, int bulkQuantity, double bulkPrice) {
      this(name, price); // calls first constructor to create an item with bulk pricing
      if (bulkQuantity < 0) {
          throw new IllegalArgumentException("Bulk Quantity cannot be negative: " + bulkQuantity);
      }   
      if (bulkPrice < 0) {
         throw new IllegalArgumentException("Bulk Price cannot be negative: " + bulkPrice);
      } 
      this.bulkQuantity = bulkQuantity;  
      this.bulkPrice = bulkPrice;
   }

   public double priceFor(int quantity) { // calculates total price for a given quantity
      if (quantity < 0) {
         throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
      }
      if (bulkPrice <= 0 || quantity < bulkQuantity) {
         return this.price * quantity; // regular price
      }
      // calculates bulk deals and remaining items (bulk package + remaining items)
      int bulkDeals = quantity / bulkQuantity;
      int remaining = quantity % bulkQuantity;
      return  (bulkDeals * bulkPrice) + (remaining * price); // price with bulk discount
   }

   public String toString() { // formats item info to produce a display string
      String priceStr = formatter.format(price);
      if(bulkPrice > 0) {
         String bulkPriceStr = formatter.format(bulkPrice);
         return name + ", " + priceStr + " (" + bulkQuantity + " for " + bulkPriceStr + ")";
      } else {
         return name + ", " + priceStr;
      }
   }
   
   @Override
   public boolean equals(Object o) { // compares items for equality based on all fields
      if(this==o) // self check, same object reference
         return true;
      if(o==null) // null check
         return false;
      if(getClass() != o.getClass()) // type check
         return false;
      Item item = (Item) o; // compare fields
      return   Double.compare(item.price, price) == 0 &&
               bulkQuantity == item.bulkQuantity &&
               Double.compare(item.bulkPrice, bulkPrice) == 0 &&
               Objects.equals(name, item.name);            
   }
}
