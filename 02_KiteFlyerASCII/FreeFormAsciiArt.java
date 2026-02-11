/* 
 * Scott Elliott
*/
import java.util.*;
public class FreeFormAsciiArt {
   public static void main(String[] args) {
      Scanner console = new Scanner (System.in);
      System.out.println("This program draws a scalable framed image of a person holding a kite and string.");
      System.out.print("What size would you like the kite to be? (even numbers over 4 work best): ");
      int size = console.nextInt();
      drawLine(size*2);
      drawTop(size/2);
      drawMiddle(size/2);
      drawBottom(size/2);
      drawPerson(size);
      drawLine(size*2);
   }
 
   // produces a line for top and bottom of frame
   public static void drawLine(int length) {
      System.out.print("+");
         for (int i = 1; i <= length; i++) {
            System.out.print("-");
         }
         System.out.println("+");
      }
              
   // produces the top half of the kite figure
   public static void drawTop(int size) {
      for (int line = 1; line <= size+2-1; line++) {
         System.out.print("|");
         for (int i = 1; i <= (size*2-line); i++){
            System.out.print(" ");
         }
         System.out.print("/");
         for (int i = 1; i <= 2 * (line - 1); i++) {
            System.out.print("X");
         }
         System.out.print("\\");
         for (int i = 1; i <= (size*2 - line); i++){
            System.out.print(" ");
            }
         System.out.println("|");
      }
   }
   
   // produces a line for middle of kite figure
   public static void drawMiddle(int length) {
      System.out.print("|");
      for (int spaces = 1; spaces<= length-1; spaces++) {
         System.out.print(" ");
      }
      System.out.print("+");
      int dashes = 2 * length*2 - 2 * length;
         for (int i = 1; i <= dashes; i++) {
            System.out.print("-");
         }
      System.out.print("+");
      for (int spaces = 1; spaces<= length-1; spaces++) {
         System.out.print(" ");
      }
      System.out.println("|");
   }
      
   // produces bottom half of the kite figure
   public static void drawBottom(int size) {
      for (int line = 1; line <= size+2-1; line++) {
         System.out.print("|");
         for (int i = 1; i <=line+size-2; i++){
            System.out.print(" ");
            }
         System.out.print("\\");
         for (int i = 1; i <= (size*2 - line*2+2); i++) {
            System.out.print("X");
         }
         System.out.print("/");
         for (int i = 1; i <= line+size-2; i++){
            System.out.print(" ");
         }

         System.out.println("|");
      }
   }
   
   // produces kite string
   public static void drawPerson(int size) {
      for(int i=1; i<=size/2; i++) {
         System.out.print("|");
         for(int j=1; j<=size-1; j++) {
            System.out.print(" ");
         }
         System.out.print("||");
         for(int j=1; j<=size-1; j++) {
            System.out.print(" ");
         }
         System.out.println("|");
      }
      int spaces = size*2;
      
      // arm holding kite and head
      System.out.print("|");
      for (int i = 1; i <= spaces - size-1; i++) {
          System.out.print(" ");
      }
      System.out.print("\\O");
      for (int i = 1; i <= spaces - size-1; i++) {
          System.out.print(" ");
      }
      System.out.println("|");

      // body and other arm
      System.out.print("|");
      for (int i = 1; i <= spaces - size; i++) {
          System.out.print(" ");
      }
      System.out.print("|\\");
      for (int i = 1; i <= spaces - size-2; i++) {
          System.out.print(" ");
      }
      System.out.println("|");

      // lower body segment
      System.out.print("|");
      for (int i = 1; i <= spaces - size; i++) {
          System.out.print(" ");
      }
      System.out.print("|");
      for (int i = 1; i <= spaces - size-1; i++) {
          System.out.print(" ");
      }
      System.out.println("|");

      // legs
      System.out.print("|");
      for (int i = 1; i <= spaces - size-1; i++) {
          System.out.print(" ");
      }
      System.out.print("/ \\");
      for (int i = 1; i <= spaces - size-2; i++) {
          System.out.print(" ");
      }
      System.out.println("|");
  } 
}
