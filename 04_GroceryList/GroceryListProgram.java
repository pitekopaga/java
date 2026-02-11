/* GroceryListProgram
 * Scott Elliott
*/
import java.util.Scanner;

public class GroceryListProgram {
   public static final int BANANAS_QUANTITY_STARTING = 10;
   public static final int BANANAS_QUANTITY_ENDING = 7;
   
   public static final double BANANA_COST = 0.20;

   public static void main(String[] args) {
      testClasses();
      
      System.out.println("Welcome to the Grocery Program!");
      System.out.println("");
      boolean addedLastItem = true;
      Scanner console = new Scanner(System.in);
      GroceryList shoppingList = new GroceryList();
         
      while (addedLastItem) {
         System.out.println("What would you like to do?");
         System.out.println(" 1. Add a new item to my list");
         System.out.println(" 2. See the list of items and the total cost thereof");
         System.out.println(" 3. Quit the program");

         int userChoice = console.nextInt();
         console.nextLine();
         switch (userChoice) {
            case 1:
               System.out.println("What would you like to add?");
               String name = console.nextLine();
               System.out.println("How many \"" + name + "\" would you like to add?");
               int quant = console.nextInt();
               System.out.println("What is the price per \"" + name + "\"?");
               double price = console.nextDouble();

               GroceryItemOrder newItem = new GroceryItemOrder(name, quant, price);

               addedLastItem = shoppingList.add(newItem);
               if (addedLastItem == false) {
                  System.out.println("We couldn't add that last item because your list is out of space. The program will now exit");
               }

               System.out.println("List of items:\n" + shoppingList);
               System.out.println("Total cost: " + shoppingList.getTotalCost());

               break;
            case 2:
               System.out.println("List of items:\n" + shoppingList);
               System.out.println("Total cost: " + shoppingList.getTotalCost());
               break;
            case 3:
               System.out.println("Thanks for using this program!");
               addedLastItem = false;
               break;
            default:
               System.out.println("I didn't recognize the option " + userChoice + "\nPlease choose from the list of options");
               break;
            }
         }   
         console.close();
      }

   public static void testClasses() {
      System.out.println("\nTESTS START HERE!");
      int numberOfFailedTests = 0;

      System.out.println("\n==========\n Testing GroceryItemOrder\n==========\n");

      GroceryItemOrder bananas = new GroceryItemOrder("Banana", BANANAS_QUANTITY_STARTING, BANANA_COST);

      if (bananas.getCost() == BANANAS_QUANTITY_STARTING * BANANA_COST)
         System.out.println("Test Passed: bananas.getCost correctly returned " + BANANAS_QUANTITY_STARTING * BANANA_COST);
      else {
         System.out.println("TEST FAILED: bananas.getCost should have returned " + BANANAS_QUANTITY_STARTING * BANANA_COST + " but instead actually returned " + bananas.getCost());
         numberOfFailedTests++;
      }

      if (bananas.toString().equals(BANANAS_QUANTITY_STARTING + " of Banana"))
         System.out.println("Test Passed: bananas.toString() correctly returned\n\t" + BANANAS_QUANTITY_STARTING + " of Banana");
      else {
         System.out.println("TEST FAILED: bananas.toString should have returned\n\t" + BANANAS_QUANTITY_STARTING + " of Banana\n" + "but instead actually returned\n\t" + bananas.toString());
         numberOfFailedTests++;
      }


      // Change how many bananas we have, then test it all again
      bananas.setQuantity(BANANAS_QUANTITY_ENDING);
      System.out.println("\nAttempted to change the quantity of bananas to " +
      BANANAS_QUANTITY_ENDING + "\n");

      // We could have eliminated some redundancy here,but instead we'll write it out a second time so that these tests are easier to understand
      if (bananas.getCost() == BANANAS_QUANTITY_ENDING * BANANA_COST)
         System.out.println("Test Passed: bananas.getCost correctly returned " + BANANAS_QUANTITY_ENDING * BANANA_COST);
      else {
         System.out.println("TEST FAILED: bananas.getCost should have returned " + BANANAS_QUANTITY_ENDING * BANANA_COST + " but instead actually returned " + bananas.getCost());
         numberOfFailedTests++;
      }

      if (bananas.toString().equals(BANANAS_QUANTITY_ENDING + " of Banana"))
         System.out.println("Test Passed: bananas.toString() correctly returned\n\t" + BANANAS_QUANTITY_ENDING + " of Banana");
      else {
         System.out.println("TEST FAILED: bananas.toString should have returned\n\t" + BANANAS_QUANTITY_ENDING + " of Banana\n" + "but instead actually returned\n\t" + bananas.toString());
         numberOfFailedTests++;
      }

      System.out.println("\n==========\n Testing GroceryList\n==========\n");
      GroceryList emptyList = new GroceryList();

      if (emptyList.getTotalCost() == 0.0)
         System.out.println("Test Passed: emptyList.getTotalCost() correctly returned 0.0");
      else {
         System.out.println("TEST FAILED: emptyList.getTotalCost() should have returned 0, but instead actually returned " + emptyList.getTotalCost());
         numberOfFailedTests++;
      }

      GroceryList singleItemList = new GroceryList();

      if (singleItemList.add(bananas) == true)
         System.out.println("Test Passed: Added 'bananas' to 'singleItemList'");
      else {
         System.out.println("TEST FAILED: singleItemList.add(bananas) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (singleItemList.getTotalCost() == bananas.getCost())
         System.out.println("Test Passed: singleItemList.getTotalCost() correctly returned " + bananas.getCost());
      else {
         System.out.println("TEST FAILED: singleItemList.getTotalCost() should have returned " + bananas.getCost() + ", but instead actually returned " + singleItemList.getTotalCost());
         numberOfFailedTests++;
      }

      GroceryItemOrder apple = new GroceryItemOrder("Apple", 1, 1.70);
      GroceryItemOrder orange = new GroceryItemOrder("Orange", 3, 1.50);
      double threeItemListTotalCost = apple.getCost() + bananas.getCost() + orange.getCost();

      GroceryList threeItemList = new GroceryList();

      if (threeItemList.add(apple) == true)
         System.out.println("Test Passed: Added 'apple' to 'threeItemList'");
      else {
         System.out.println("TEST FAILED: threeItemList.add(apple) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (threeItemList.add(bananas) == true)
         System.out.println("Test Passed: Added 'bananas' to 'threeItemList'");
      else {
         System.out.println("TEST FAILED: threeItemList.add(bananas) should have returned true, but actually returned false"); numberOfFailedTests++;
      }

      if (threeItemList.add(orange) == true)
         System.out.println("Test Passed: Added 'orange' to 'threeItemList'");
      else {
         System.out.println("TEST FAILED: threeItemList.add(orange) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (threeItemList.getTotalCost() == threeItemListTotalCost)
         System.out.println("Test Passed: threeItemList.getTotalCost() correctly returned " + threeItemList.getTotalCost());
         else {
            System.out.println("TEST FAILED: threeItemList.getTotalCost() should have returned " + threeItemListTotalCost + ", but instead actually returned " + emptyList.getTotalCost());
            numberOfFailedTests++;
      }

      System.out.println(threeItemList.toString());

      GroceryItemOrder pear = new GroceryItemOrder("Pear", 3, 2.40);
      GroceryItemOrder baklava = new GroceryItemOrder("Orange", 17, 2.23);

      double fullListTotalCost = apple.getCost() + bananas.getCost() + orange.getCost() + pear.getCost() + baklava.getCost();

      GroceryList fullList = new GroceryList();

      if (fullList.add(apple) == true)
         System.out.println("Test Passed: Added 'apple' to 'fullList'");
      else {
         System.out.println("TEST FAILED: fullList.add(apple) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (fullList.add(bananas) == true)
         System.out.println("Test Passed: Added 'bananas' to 'fullList'");
      else {
         System.out.println("TEST FAILED: fullList.add(bananas) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (fullList.add(orange) == true)
         System.out.println("Test Passed: Added 'orange' to 'fullList'");
      else {
         System.out.println("TEST FAILED: fullList.add(orange) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (fullList.add(pear) == true)
         System.out.println("Test Passed: Added 'pear' to 'fullList'");
      else {
         System.out.println("TEST FAILED: fullList.add(pear) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (fullList.add(baklava) == true)
         System.out.println("Test Passed: Added 'baklava' to 'fullList'");
      else {
         System.out.println("TEST FAILED: fullList.add(baklava) should have returned true, but actually returned false");
         numberOfFailedTests++;
      }

      if (fullList.getTotalCost() == fullListTotalCost)
         System.out.println("Test Passed: fullList.getTotalCost() correctly returned " + fullList.getTotalCost());
      else {
         System.out.println("TEST FAILED: fullList.getTotalCost() should have returned" + fullListTotalCost + ", but instead actually returned " + emptyList.getTotalCost());
         numberOfFailedTests++;
      }

      // There's only space for 5 items in the list, so try adding a 6th and verify that we get the expected 'false' response:
      GroceryItemOrder rustyNailsAndBrokenGlass = new GroceryItemOrder("Rusty Nails and Broken Glass", 2, .1);
      if (fullList.add(rustyNailsAndBrokenGlass) == false)
         System.out.println("Test Passed: We are (correctly) not able to add 'rustyNailsAndBrokenGlass' to 'fullList'");
      else {
         System.out.println("TEST FAILED: fullList.add(rustyNailsAndBrokenGlass) should have returned false, but actually returned true");
         numberOfFailedTests++;
      }

      if (numberOfFailedTests == 0)
         System.out.println("\nAll tests passed!\n");
      else
         System.out.println("\nERROR!  " + numberOfFailedTests + " TESTS FAILED!\nIn the transcript above look for the phrase TEST FAILED to find the code that isn't doing what it's expected to do\n");
   }
}
