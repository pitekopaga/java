/* ItemOrder
 * Scott Elliott
*/
public class ItemOrder {
   private Item item; // the item being ordered
   private int quantity; // quantity of item being ordered

   public ItemOrder(Item item, int quantity) { // creates new item order
      this.item = item;
      this.quantity = quantity;
   }

   public double getPrice() { 
      return item.priceFor(quantity); // calculates and returns total price using Item class
   }

   public Item getItem() { 
      return item; // gets the item for this order
   }
}
