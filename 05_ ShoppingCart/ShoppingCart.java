/* ShoppingCart
 * Scott Elliott
*/
import java.util.*;

public class ShoppingCart { // represents a shopping cart that holds item orders and calculates totals
   private List<ItemOrder> orders; // list of item orders in shopping cart
   private boolean discount; // whether there is a discount
   public static final double DISCOUNT_PERCENT = 0.9; // sentinal discount rate

   public ShoppingCart() { // constructs an empty shopping cart with no discount
      this.orders = new ArrayList<>();
      this.discount = false;
   }

   public void add(ItemOrder next) { // adds or replaces an item order in the cart
      for (int i = 0; i < orders.size(); i++) {
         if (orders.get(i).getItem().equals(next.getItem())) {
            orders.set(i, next);  // Replace at index i
            return;  // Exit early after replacement
         }
      }
      orders.add(next);
   }

   public void setDiscount(boolean discount) { // sets whether cart qualifies for discount
      this.discount = discount;
   }

   public double getTotal() { // calculates total cost of all items in cart
      double total = 0.0;
      for(ItemOrder order : orders) {
         total += order.getPrice();
      }
      if(discount) {
         total *= DISCOUNT_PERCENT;
      }
      return total;
   }
}
