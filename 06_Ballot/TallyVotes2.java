/* Ranked Choice Voting - TallyVotes2
 * Scott Elliott
*/
// Program to perform ranked choice voting algorithm using a data file of voting preferences. This file should be *almost* 
// identical to the code explained in the book. Lines have been added to main to ask the user for the file to read

import java.util.*;
import java.io.*;

public class TallyVotes2 {
   private static int originalBallotCount; // stores original ballot count
   
   public static void main(String[] args) throws FileNotFoundException {
      Scanner keyboard = new Scanner(System.in);
      System.out.println("What file contains the ballot information? (type \"quit\" to end the program)");
      String fileName = keyboard.nextLine();
      while(!fileName.equals("quit")) { // repeats prompts until user enters "quit"
         Scanner input = new Scanner(new File(fileName));
         ArrayList<Ballot> ballots = readFile(input);
         originalBallotCount = ballots.size(); // sets original count
         int round = 1;
         boolean done = false;
         while (!done) { // process each voting round until a winner is found
            System.out.println("Round #" + round);
            Collections.sort(ballots); // sorts ballots by current first choice
            done = oneRound(ballots); // process one round of voting
            System.out.println("------------------------------");
            round++;
         }
         System.out.println("What file contains the ballot information? (Enter \"quit\" to quit)");
         fileName = keyboard.nextLine();
      }
   }

   // Reads a data file of voter preferences, returning a list of the resulting ballots. Candidate names are listed 
   // in order of preference with tabs separating choices.
   public static ArrayList<Ballot> readFile(Scanner input) {
      ArrayList<Ballot> result = new ArrayList<>();
      while (input.hasNextLine()) {
         String text = input.nextLine();
         if(!text.isEmpty()) { // skip blank lines
            String[] candidates = text.split("\t"); // split on tabs
            // only add if there's at least one non-empty candidate
            boolean hasValidCandidate = false;
            for(String candidate : candidates) {
               if(candidate.length()>0) {
                  hasValidCandidate = true;
               }
            }
            if(hasValidCandidate) 
               result.add(new Ballot(candidates));
         }
      }
      return result;
   }
   
   // Performs one round of ranked choice voting. The candidate with the lowest vote total is eliminated until some candidate 
   // gets a majority or until we reach a tie between only two candidates. Assumes the list is in order by candidate name.
   public static boolean oneRound(ArrayList<Ballot> ballots) {
      if (ballots.isEmpty()) { // check for empty ballots to avoid invalid input
         System.out.println("Election has no winner");
         return true;
      }
      String top = null;
      String bottom = null;
      int topCount = 0;
      int bottomCount = ballots.size() + 1;
      int candidateCount = 0;
      int nonZeroCandidates = 0;
      HashMap<String, Integer> voteCounts = new HashMap<>(); // use hash map to store vote counts for each candidate
      int index = 0;
      while (index < ballots.size()) { // iterate trhough ballots
         String next = ballots.get(index).getCandidate();
         if (!next.equals("none")) { // skip ballots with no valid candidates
            int count = processVotes(next, index, ballots); // count votes for each candidate
            voteCounts.put(next, count); // store vote count in hash map
            if (count > topCount) { // update top candidate (highest votes)
               topCount = count;
               top = next;
            }
            index += count; // move index past all ballots for this candidate
            candidateCount++;
            if (count > 0) {
               nonZeroCandidates++;
            }
         } else {
            index++;
         }
      }
      //  tiebreaker prioritizes lowest vote count, selects last candidate in tie. Find the minimum vote count.
      int minVotes = ballots.size() +1;
      for (int count : voteCounts.values()) {
         if (count > 0 && count < minVotes) {
            minVotes = count;
         }
      }

      // Select the last candidate with minVotes
      String minCandidate = null;
      for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
         if (entry.getValue() == minVotes) {
            minCandidate = entry.getKey(); // Last candidate overwrites
         }
      }
      bottom = minCandidate;
      bottomCount = minVotes;
        
      if (candidateCount == 0) { // check if no candidates exist
         System.out.println("No valid candidates.");
         return true;
      }
      if (nonZeroCandidates >= 2 && topCount == bottomCount) { // handles tie case
         System.out.println("Election has no winner (tie)");
         return true;
      }
      int majorityThreshold = (originalBallotCount / 2) + 1; 
      if (topCount >= majorityThreshold || nonZeroCandidates <= 1) { // check for winner (majority or last candidate)
         System.out.println("Winner is " + top + " with " + topCount + " votes (needed " + majorityThreshold + ")");
         return true;
      }
      if (bottom == null) { // check if no candidate can be eliminated
         System.out.println("No valid candidates remaining.");
         return true;
      }
      System.out.println("no winner, eliminating " + bottom); // eliminate candidate with lowest votes
      eliminate(bottom, ballots);
      return false;
   }
   
   // Counts and reports the votes for the next candidate starting at the given index in the ballots list.
   public static int processVotes(String name, int index, ArrayList<Ballot> ballots) {
      int count = 0;
      while (index < ballots.size() && ballots.get(index).getCandidate().equals(name)) {
         index++;
         count++; // count votes
      }
      double percent = 100.0 * count / ballots.size();
      System.out.printf("%d votes for %s (%4.1f%%)\n", count,name, percent);
      return count;
   }

   // Eliminates the given candidate from all ballots, and removes empty ballots
   public static void eliminate(String candidate, ArrayList<Ballot> ballots) {
      Iterator<Ballot> itr = ballots.iterator();
      while(itr.hasNext()) {
         Ballot b = itr.next();
         b.eliminate(candidate);
         if (b.isEmpty())
            itr.remove();
      }
   }
}