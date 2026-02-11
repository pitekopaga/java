/* Ranked Choice Voting - Ballot
 * Scott Elliott
*/
// class for storing one voter's preferences

import java.util.*;

public class Ballot implements Comparable<Ballot> {
   private ArrayList<String> preferences;

   // constructs a ballot with the given candidate names in order
   public Ballot(String[] names) {
      preferences = new ArrayList<>();
      for (String next : names) {
         if (next.length() > 0) // skips empty strings
            preferences.add(next);
      }
   }
   
   // checks if the ballot's preferences list is empty
   public boolean isEmpty() {
      return preferences.isEmpty();
   }

   // returns the current first choice for this ballot or "none" if there are no longer any choices for this ballot
   public String getCandidate() {
      if(preferences.isEmpty())
         return "none";
      return preferences.get(0);
   }

   // eliminates the given candidate from consideration
   public void eliminate(String name) {
      for(int i = preferences.size()-1; i>=0; i--) { // Iterate backward to remove all occurrences
         if(preferences.get(i).equals(name)) 
            preferences.remove(i);
      }
   }

   // compares this ballot to another, putting them in order alphabetically by their first choice candidate
   public int compareTo(Ballot other) {
      return getCandidate().compareTo(other.getCandidate());
   }
}