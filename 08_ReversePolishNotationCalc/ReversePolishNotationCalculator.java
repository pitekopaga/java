/* Reverse Polish Notation Calculator
 * Scott Elliott
*/
// This program reads a Reverse Polish Notation mathematical Expression and evaluates it.  The program will show each step if the user 
// chooses that otherwise the program will only print out the end result

import java.util.*;

public class ReversePolishNotationCalculator {
   
   // This gets the remainder of the input out of the Scanner prints that remaining input (and also prints out the current contents of 
   // the stack) and then re-loads the remaining input into a new Scanner. This means that we can keep Scanning the remainder of the input
   private static Scanner printRemainingExpression(Stack<Double> numbers, Scanner scExpression) {
      String remainderOfExpr = scExpression.nextLine();
      System.out.println("Remaining expression: " + remainderOfExpr + " Stack: " + numbers);
      scExpression.close(); // may as well close out the old one before creating a new replacement
      return new Scanner(remainderOfExpr + "\n"); // return new scanner with same content
   }

   public static void main(String[] args) {
      Scanner keyboard = new Scanner(System.in);
      char evalAgain = 'y';

      ShouldWeTryAgain: while (evalAgain == 'y') { // labelled loop allows us to use continue/break from nested loops
         Stack<Double> numbers = new Stack<Double>();
         double nextNumber = 0;

         System.out.println("\nRPN calculator"); // display header and prompt
         System.out.println("\tSupported operators: + - * /");
         System.out.print("Type your RPN expression in so that it can be evaluated: ");
         String rpnExpr = keyboard.nextLine();

         boolean explain = false;
         System.out.print("Would you like me to explain how to expression is evaluated? (y or Y for yes, anything else means no) ");
         String szExplain = keyboard.nextLine().trim().toLowerCase();
         if (szExplain.length() == 1 && szExplain.charAt(0) == 'y') {
            System.out.println("We WILL explain the evaluation, step by step");
            explain = true;
         }

         Scanner scExpr = new Scanner(rpnExpr + "\n"); // add new line to ensure hasNext() works
         while(scExpr.hasNext()) { // repeat the following while there's another token left in the Scanner
            if (explain) { // if in explain mode, show current state before each step
               scExpr = printRemainingExpression(numbers, scExpr);
            }
            if (scExpr.hasNextDouble()) { // if the next thing in the expression is a number
               nextNumber = scExpr.nextDouble();
               numbers.push(nextNumber);
               if (explain)
                  System.out.println("\tPushing " + nextNumber + " onto the stack of operands (numbers)");
               continue; // skip to next token
            }             
            // if the next thing in the expression is not a number then we'll assume it's an operator
            // (unless we find out that it's not a supported operator)
            String operator = scExpr.next();
            if (operator.length() != 1) { // validate operator is a single character
               System.err.println("ERROR! Operator (non-numeric input) contains more than 1 character: " + operator);
               System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
               continue ShouldWeTryAgain; // This line will jump back to the outer loop
            }

            // "4 3 -" should be +1, not -1
            // When parsing the expression 4 is pushed first, then 3
            // the second operand (the 3) is at the top (so we'll pop that into operand2) then pop the first operand (the 4) into operand1
            if (numbers.isEmpty()) { // ensure we have enough operands (RPM needs at least 2 per operator)
               System.err.println("ERROR! Expected to find 2 operands (numbers) but we don't have any numbers on the stack!");
               System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
               continue ShouldWeTryAgain;
            
            }
            double operand2 = numbers.pop(); // get the top operand off the number stack (right operand)
               if (numbers.isEmpty()) {
                  System.err.println("ERROR! Expected to find 2 operands (numbers) but we don't have a second number on the stack!");
                  System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                  continue ShouldWeTryAgain;
               }
            double operand1 = numbers.pop(); // get the top operand off the number stack (left operand)
            double result = 0.0;
            switch (operator.charAt(0)) {
               case '+':
                  result = operand1 + operand2;
                  break;
               case '-':
                  result = operand1 - operand2;
                  break;
               case '*':
                  result = operand1 * operand2;
                  break;
               case '/':
                  result = operand1 / operand2;
                  break;
               default:
                  System.err.println("ERROR! Operator not recognized: " + operator);
                  System.out.println("Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                  continue ShouldWeTryAgain; // This line will jump back to the outer loop
            }
            numbers.push(result); // push result back onto stack
            if (explain)
               System.out.println("\tPopping " + operand1 + " and " + operand2 + " then applying " + operator + " to them, then pushing the result back onto the stack");
         } // At this point we've finished reading through the expression

         if (numbers.size() > 1) { // if there's more than 1 operand (number) left then we print this error
            System.err.println("ERROR! Ran out of operators before we used up all the operands (numbers):");
            Stack<Double> tempStack = new Stack<>();
            tempStack.addAll(numbers);
            while(!tempStack.isEmpty()) // Go through all the operands an print them out in pop order (top to bottom)
               System.err.println("\t" + tempStack.pop());
         } else if (numbers.isEmpty()) // If there wasn't any operands then print out this error message
            System.err.println("ERROR! Ran out of operands (numbers)");
         else // If there's exactly 1 operand then it must be the answer
            System.out.println("END RESULT: " + numbers.pop());

         System.out.print("\nWould you like to evaluate another expression? (y or Y for yes, anything else to exit) ");
         String repeat = keyboard.nextLine().trim().toLowerCase();
         if (repeat.length() == 1 && repeat.charAt(0) == 'y')
            evalAgain = 'y';
         else
            evalAgain = 'n';
      }
      System.out.println("Thank you for using RPN Calculator!");
   }
}