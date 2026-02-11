/*
 * Scott Elliott
*/
import java.util.*;
public class AssignedAsciiArt {
   public static void main (String[] args) {
      Scanner console = new Scanner (System.in);
      System.out.print("What size space needle would you like to see? ");
      int size = console.nextInt();
      bars(size);
      triangle(size);
      line(size);
      trapezoid(size);
      bars(size);
      tower(size);
      triangle(size);
      line(size);      
   }
   
   // produces narrow parts of figure
   public static void bars (int  x) {
      for(int i=1; i<=x; i++) {
         for(int j=1; j<=x*3; j++) {
            System.out.print(" ");
         }
         System.out.println("||");
      }
   }
   
   // produces triangle shape
   public static void triangle (int x) {
      for(int i=1; i<=x; i++) {
         for(int j=1; j<=(i*-3)+(x*3); j++) {
            System.out.print(" ");
         }
         System.out.print("__/");
         for(int k=1; k<=i*3-3; k++) {
            System.out.print(":");
         }
         System.out.print("||");
         for(int k=1; k<=i*3-3; k++) {
            System.out.print(":");
         }
         System.out.println("\\__");
      }
   }
   
   //produces a line of quotes between bars
   public static void line (int x) {
      System.out.print("|");
      for(int i=1; i<=x*6; i++) {
         System.out.print("\"");
      }
      System.out.println("|");      
   }
   
   // produces trapezoid shape
   public static void trapezoid (int x) {
      for(int i=1; i<=x; i++) {
            for(int j=1; j<=i*2-2; j++) {
               System.out.print(" ");
            }
         System.out.print("\\_");
         for(int k=1; k<=(i*-2)+x*3+1; k++) {
            System.out.print("/\\");
         }
         System.out.println("_/");
      }
   }
   
   // produces wider tower part of figure
   public static void tower (int x) {
      for(int i=1; i<=x*x; i++) { 
      //changed x-4 to x*x above for limit parameter
         for(int j=1; j<=x*3-x+1; j++) {
            System.out.print(" ");
         }
         System.out.print("|");
         for(int k=1; k<=x-2; k++) {
            System.out.print("%");
         }
         System.out.print("||");
         for(int k=1; k<=x-2; k++) {
            System.out.print("%");
         }
         System.out.println("|");
      }
   }
}
