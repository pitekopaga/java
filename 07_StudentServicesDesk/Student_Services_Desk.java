/* Student Services Desk
 * Scott Elliott
*/
// This program creates a list of students and allows the user to lookup information about a student given the ID number.
// This is meant to loosely simulate what happens at the Student Services desk (Kodiak Corner here at Cascadia College).
// In real life when a student scans their Student ID card the computer reads their Student ID Number from the bar code
// The computer then looks up the information about the student and allows the employee to look and and modify the information.

import java.util.*;

public class Student_Services_Desk {

   private static int nextSID = 22; // must be greater than any of the Students' IDs that we pre-load

   public static void main(String[] args) {
      Scanner keyboard = new Scanner(System.in);

      Map<Integer, Student> studentInfo = new TreeMap<Integer, Student>();
      studentInfo.put(21, new Student(21, "Zog", "TheDestroyer",new ArrayList<String>(List.of("BIT 143", "MATH 411", "ENG 120"))));
      studentInfo.put(20, new Student(20, "Mary", "Sue", new ArrayList<String>(List.of("BIT 142", "MATH 142", "ENG 100"))));
      studentInfo.put(1, new Student(1, "Joe", "Bloggs", new ArrayList<String>(List.of("BIT 115", "MATH 141", "ENG 101"))));

      char choice = 'L'; // anything but 'q' is fine
      while (choice != 'q') {
         System.out.println("\nWhat would you like to do next? ");
         System.out.println("F - Find a specific student");
         System.out.println("L - List all the students (for debugging purposes)");
         System.out.println("A - Add a new student");
         System.out.println("D - Delete an existing student");
         System.out.println("M - Modify an existing student");
         System.out.println("Q - Quit (exit) the program");
         System.out.print("What is your choice?\n(type in the letter & then the enter/return key) ");
         choice = keyboard.nextLine().trim().toLowerCase().charAt(0);
         System.out.println();
         switch (choice) {
            case 'f':
               System.out.println("Find a student (based on their ID number):\n");
               System.out.print("What is the ID number of the student to find? ");
               int id = keyboard.nextInt();
               keyboard.nextLine(); // clear input buffer
               if(studentInfo.containsKey(id)) {
                  System.out.printf("%s, %s (SID=%d)\nClasses:\n", studentInfo.get(1).getLastName(), studentInfo.get(1).getFirstName(), id);
                  System.out.println(studentInfo.get(id).printClasses());
               } else 
                  System.out.println("Didn't find a student with ID # " + id);
               break;

            case 'l':
               System.out.println("The following students are registered:");
               for(Student student : studentInfo.values())
                  System.out.println(student);
               break;
            case 'a':
               System.out.println("Adding a new student\n");
               System.out.println("Please provide the following information:");
               System.out.print("Student's first name? ");
               String firstName = keyboard.next();
               
               System.out.print("Student's last name? ");
               String lastName = keyboard.next();
               keyboard.nextLine(); // clear input buffer

               List<String> classList = new ArrayList<>();
               System.out.println("Type the name of class, or leave empty to stop.  Press enter/return regardless");
               String className = keyboard.nextLine();
               while(!className.isEmpty()) {
                  classList.add(className); 
                  System.out.println("Type the name of class, or leave empty to stop.  Press enter/return regardless");
                  className = keyboard.nextLine();           
               }  
               int newSID = nextSID; // assign new SID and add student
               studentInfo.put(newSID, new Student(nextSID, firstName, lastName, classList));
               nextSID++; // increment for next student
               break;
            case 'd':
               System.out.println("Deleting an existing student\n");
               System.out.print("What is the ID number of the student to delete? ");
               int idDelete = keyboard.nextInt();
               keyboard.nextLine();
               if(studentInfo.containsKey(idDelete)) {
                  studentInfo.remove(idDelete);
                  System.out.println("Student account found and removed from the system!");
               } else 
                  System.out.println("Didn't find a student with ID # " + idDelete);
               break;
            case 'm':
               System.out.println("Modifying an existing student\n");
               System.out.print("What is the ID number of the student to modify? ");
               int idMod = keyboard.nextInt();
               keyboard.nextLine(); // clear input buffer
               if (studentInfo.containsKey(idMod)) {
                  Student student = studentInfo.get(idMod); // get the student to modify
                  System.out.print("Student account found!\nFor each of the following type in the new info or leave empty to keep the existing info.");
                  System.out.println("Press enter/return in both cases.");
                  System.out.print("Student's first name: " + student.getFirstName() + " New first name? ");
                  String firstNameMod = keyboard.nextLine();
                  if(!firstNameMod.isEmpty()) // modify first name if provided
                     student.setFirstName(firstNameMod);
                  System.out.print("Student's last name: " + student.getLastName() + " New last name? ");
                  String lastNameMod = keyboard.nextLine();
                  if(!lastNameMod.isEmpty()) // modify last name if provided
                     student.setLastName(lastNameMod);
                  System.out.println("Updating class list");
                  System.out.println("Here are the current classes: " + student.getClasses());
                  System.out.println("This program will go through all the current classes.");
                  System.out.println("For each class it will print the name of the class and then ask you if you'd like to delete or keep it.");
                  
                  List<String> updatedClasses = new ArrayList<>(student.getClasses());
                  Iterator<String> it = updatedClasses.iterator();
                  while(it.hasNext()) { // using iterator for safe removal during iteration
                     String oneClass = it.next();
                     System.out.println(oneClass + "\nType d<enter/return> to remove, or just <enter/return> to keep ");
                     String classMod = keyboard.nextLine();
                     if(classMod.equalsIgnoreCase("d")) {
                        it.remove();
                        System.out.println("Removing " + oneClass + "\n");
                     } else 
                     System.out.println("Keeping " + oneClass + "\n");
                  }
                  System.out.println("Type the name of the class you'd like to add, or leave empty to stop.  Press enter/return regardless");
                  String newClass = keyboard.nextLine();
                  while(!newClass.isEmpty()) {
                     updatedClasses.add(newClass);
                     System.out.println("Type the name of the class you'd like to add, or leave empty to stop.  Press enter/return regardless");
                     newClass = keyboard.nextLine();
                  }
                  student.setClasses(updatedClasses); // save the modified class back to the student object
                  System.out.println("Here's the student's new, updated info: " + student); // I do not understand why the SID in Canvas is 24. Shouldn't it be 23?
               } else 
                  System.out.println("Student ID not found.");
               break;
            case 'q':
               System.out.println("Thanks for using the program - goodbye!\n");
               break;
            default:
               System.out.println("Sorry, but I didn't recognize the option " + choice);
               break;
            }
        }
    }
}
