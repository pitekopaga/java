/* 
 * Scott Elliott
*/
import java.util.*;
public class Birthdays {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        printIntro();
        boolean valid = false;
        int today = today(console); // Get today's date
        while (!valid) {
            valid = compare(console, today); // Compare two birthdays
            if (!valid) {
                System.out.println("Would you like to compare two more birthdays?");
                System.out.println("Type 1 and then <enter/return> to compare two more birthdays");
                System.out.println("Type 2<enter/return> to end the program");
                int retry = console.nextInt();
                if (retry == 2) {
                    System.out.println("Thank you for using the program. Have a good day!");
                    valid = true;
                }
            }
        }
    }

    public static void printIntro() {
        System.out.println("This program compares two birthdays");
        System.out.println("and displays which one is sooner.");
    }

    public static int today(Scanner console) {
        int month = 1;
        int day = 1;
        int year = 2020;
        boolean valid = false;
        while (!valid) {
            System.out.println("What is today's month and day?");
            if (console.hasNextInt()) {
                month = console.nextInt();
                if (console.hasNextInt()) {
                    day = console.nextInt();
                    if (month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                        valid = true;
                    } else {
                        System.out.println("Outside range. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid month or day. Please try again.");
                console.next();
            }
        }
        int today = calcDayOfYear(month, day, year);
        System.out.printf("Today is %d/%d/%d, day #%d of the year.\n", month, day, year, today);
        return today;
    }

    public static int birthday(Scanner console, int person, int today, int year) {
        int month = 1;
        int day = 1;
        boolean valid = false;
        while (!valid) {
            System.out.println("Person " + person + ":");
            System.out.print("What month and day were you born? ");
            if (console.hasNextInt()) {
                month = console.nextInt();
                if (console.hasNextInt()) {
                    day = console.nextInt();
                    if (month >= 1 && month <= 12 && day >= 1 && day <= 31) {
                        valid = true;
                    } else {
                        System.out.println("Outside range. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid month or day. Please try again.");
                console.next();
            }
        }
        int birthday = calcDayOfYear(month, day, year);
        int daysInYear;
        if (isLeapYear(year)) {
            daysInYear = 366;
        } else {
            daysInYear = 365;
        }
        double percent;
        System.out.printf("%d/%d/%d falls on day #%d of %d.\n", month, day, year, birthday, daysInYear);
        if (birthday > today) {
            int daysToB = birthday - today;
            System.out.printf("Your next birthday is in %d day(s).\n", daysToB);
            percent = ((double) daysToB / daysInYear) * 100.0;
            System.out.printf("That is %.1f percent of a year away.\n", percent);
        } else if (birthday < today) {
            int daysToB = daysInYear - today + birthday;
            System.out.printf("Your next birthday is in %d day(s).\n", daysToB);
            percent = ((double) daysToB / daysInYear) * 100.0;
            System.out.printf("That is %.1f percent of a year away.\n", percent);
        } else {
            System.out.println("Happy birthday!");
        }
        return birthday;
    }

    public static boolean compare(Scanner console, int today) {
        int year = 2020;
        int daysInYear;
        if (isLeapYear(year)) {
            daysInYear = 366;
        } else {
            daysInYear = 365;
        }
        int birthday1 = birthday(console, 1, today, year); // Get birthday for person 1
        int birthday2 = birthday(console, 2, today, year); // Get birthday for person 2

        int daysToB1;
        if (birthday1 > today) {
            daysToB1 = birthday1 - today;
        } else {
            daysToB1 = daysInYear - today + birthday1;
        }

        int daysToB2;
        if (birthday2 > today) {
            daysToB2 = birthday2 - today;
        } else {
            daysToB2 = daysInYear - today + birthday2;
        }

        if (daysToB1 < daysToB2) {
            System.out.println("\nPerson 1's birthday is sooner.\n");
        } else if (daysToB1 > daysToB2) {
            System.out.println("\nPerson 2's birthday is sooner.\n");
        } else {
            System.out.println("\nWow, you share the same birthday!\n");
        }
        System.out.println("Birthday fact about July 28: ");
        System.out.println("Heaviest Rainfall Record (1979): On this day, the town of Alvin, Texas,");
        System.out.println("experienced 43 inches of rain in 24 hours, setting a U.S. record.\n");
        return false; // Continue the loop
    }
   
   public static int calcDayOfYear(int month,int day,int year) {
      int feb = 28;
      int dayOfYear = day;
      if (month>1) {
         dayOfYear += 31;
      }
      if (month>2) {
         if(isLeapYear(year)) {
            dayOfYear += 1;
         }
         dayOfYear += feb;
      }
      if (month>3) {
         dayOfYear += 31;
      }
      if (month>4) {
         dayOfYear += 30;
      }
      if (month>5) {
         dayOfYear += 31;
      }
      if (month>6) {
         dayOfYear += 30;
      }
      if (month>7) {
         dayOfYear += 31;
      }
      if (month>8) {
         dayOfYear += 31;
      }
      if (month>9) {
         dayOfYear += 30;
      }
      if (month>10) {
         dayOfYear += 31;
      }
      if (month>11) {
         dayOfYear += 30;
      }
      return dayOfYear;
   }
      
   public static boolean isLeapYear(int year) {
      return (year%4 == 0 && year%100 != 0 || year%400 == 0);
   }
}
