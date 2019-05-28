import java.util.Scanner;
import java.lang.Math;

   /* Purpose: Assig2 Class contains static main class methods.
    */
public class Assig2 
{  
   static Scanner keyboard = new Scanner(System.in);

   /* Purpose: The main function that runs the program.
    * Preconditions: TripleString object.
    * Postconditions: Sets the TripleString object, calls other functions to
    * display and execute the program in a constant loop. Quits when the user inputs
    * the kill command "0".
    */
   public static void main(String[] args)
   {
      TripleString storedPull = new TripleString();
      int userBet = 1;
      while(userBet != 0)
      {
         userBet = getBet();
         if(userBet == 0)
         {
            System.out.print(storedPull.displayWinnings());
         }
         else 
         {
            storedPull = pull();
            int multiplier = getPayMultiplier(storedPull);
            int winnings = userBet*multiplier;
            display(storedPull, winnings);
            storedPull.saveWinnings(winnings);
            if(storedPull.saveWinnings(winnings) == false)
            {
               System.out.print(storedPull.displayWinnings());
               userBet = 0;
            }
         } 
      }
      keyboard.close();
   }
   
   /* Purpose: Interacts with the user for value input.
    * Preconditions: Requires a Scanner object for user input and a boolean to check validity.
    * Postconditions: Returns only valid user responses; continues to loop otherwise.
    */
   public static int getBet()
   {
      System.out.print("How much would you like to bet (1-100)"
      + " or 0 to quit?");
      int bet = keyboard.nextInt();
      boolean isValid = false;
      while(isValid == false)
      {
         if(bet == 0)
         {
            isValid = true;
         }
         else if(bet >= 1 && bet <= 100)
         {
            isValid = true;
         }
         else
         {
            System.out.print("How much would you like to bet (1-100)"
                  + " or 0 to quit?");
            bet = keyboard.nextInt();
         }
      }
      return bet;
   }
   
   /* Purpose: Sets the string attributes for the TripleString object.
    * Preconditions: Requires the helper function randString(), accessor and mutator functions.
    * Postconditions: Returns the TripleString object with randomly selected strings.
    */
   public static TripleString pull()
   {
      TripleString randomThree = new TripleString();
      randomThree.getString1();
      randomThree.setString1(randString());
      randomThree.getString2();
      randomThree.setString2(randString());
      randomThree.getString3();
      randomThree.setString3(randString());
      return randomThree;
   }
   
   /* Purpose: Randomly chooses the string attribute for TripleString pull().
    * Preconditions: Requires the Math.random module to generate a random number between 0 and 1.
    * Postconditions: Returns the randomly generated string to the pull() invocation.
    */
   private static String randString()
   {
      int randNum = (int)(Math.random()*1000);
      String phrase = "";
      if(randNum <= 500)
      {
         phrase = "BAR";
      }
      else if(randNum >= 501 && randNum <= 751)
      {
         phrase = "cherries";
      }
      else if(randNum >= 752 && randNum <= 877)
      {
         phrase = "space";
      }
      else
      {
         phrase = "7";
      }
      return phrase;
   }
   
   /* Purpose: Evaluates the appropriate multiplier.
    * Preconditions: Requires access to the string attributes of the TripleString object.
    * Postconditions: Returns the determined value multiplier.
    */
   public static int getPayMultiplier(TripleString thePull)
   {
      int multiplier = 0;
      String string1 = thePull.getString1();
      String string2 = thePull.getString2();
      String string3 = thePull.getString3();
      //Considers when all strings are the same.
      if(string1.contentEquals(string2) && string1.contentEquals(string3))
      {
         if(string1 == "cherries" && string2 == "cherries" && string3 == "cherries")
         {         
            multiplier = 30;
         }
         else if(string1 == "BAR" && string2 == "BAR" && string3 == "BAR")
         {
            multiplier = 50;
         }
         else if(string1 == "7" && string2 == "7" && string3 == "7")
         {
            multiplier = 100;
         }
         else
            multiplier = 0;
      }
      //Considers only when string1 and string2 are the same.
      else if(string1.contentEquals(string2) && !string1.contentEquals(string3))
      {
         if(string1 == "cherries" && string2 == "cherries")
         {
            multiplier = 15;
         }
         else
            multiplier = 0;
      }
      //Considers when no strings match.
      else if(!string1.contentEquals(string2))
      {
         if(string1 == "cherries")
         {
            multiplier = 5;
         }
         else
            multiplier = 0;
      }
      return multiplier;
   }
   
   /* Purpose: Displays the win or loss message after each loop.
    * Preconditions: Requires the TripleString object, and the current winnings.
    * Postconditions: Returns the win or loss message including the winnings of current turn.
    */
   public static void display(TripleString thePull, int winnings)
   {
      int checkMultiplier = getPayMultiplier(thePull);
      System.out.println(thePull.toString());
      if(checkMultiplier != 0)
      {
         System.out.println("Congrats, you won: " + winnings);
      }
      else 
         System.out.println("Sorry, you lose.");
   }  
}

/* Purpose: TripleString Class creates TripleString objects; contains methods 
 * that account for three string attributes, an array that accounts
 * for saving the value of player pulls, and the count of pulls.
 */
class TripleString 
{
   private String string1;
   private String string2;
   private String string3;
   public final static int MAX_LEN = 20;
   public final static int MAX_PULLS = 40;
   private static int[] pullWinnings = new int[MAX_PULLS];
   private static int numPulls = 0;

   /* Purpose: Default no argument constructor.
    */
   public TripleString()
   {
      string1 = "";
      string2 = "";
      string3 = "";
   }
   /* Purpose: Evaluates that the string is valid for the mutator methods.
    * Preconditions: Requires a string object to check.
    * Postconditions: Returns a true or false value.
    */
   private boolean validString(String str)
   {
      if(str != null && str.length() <= MAX_LEN)
      {
         return true;
      }
      else
         return false;
   }
   
   /* Purpose: Accessor and Mutator methods.
    * Preconditions: Accessor Methods must return the value of the TripleString string attributes.
    * Mutator methods must use the helper function validString prior to setting the string
    * attribute to the value of the argument. Mutator methods must return a true or false value.
    * Postconditions: If validString returns true, the method sets the string attribute
    * to the value of the argument. Otherwise, the method returns false.
    */
   public String getString1()
   {
     return string1;
   }
   
   public boolean setString1(String str)
   {
      if(validString(str))
      {
         string1 = str;
         return true;
      }
      else
         return false;
   }
   
   public String getString2()
   {
     return string2;
   }
   
   public boolean setString2(String str)
   {
      if(validString(str))
      {
         string2 = str;
         return true;
      }
      else
         return false;
   }
   
   public String getString3()
   {
     return string3;
   }
 
   public boolean setString3(String str)
   {
      if(validString(str))
      {
         string3 = str;
         return true;
      }
      else
         return false;
   }
   
   /* Purpose: Displays the TripleString string.
    * Preconditions: Requires access to the string attributes.
    * Postconditions: Returns the string attributes as a single string.
    */
   public String toString()
   {
      return string1 + " " + string2 + " " + string3;
   }
   
   /* Purpose: Saves the user winnings of each pull.
    * Preconditions: Requires the count for numPulls and MAX_PULLS.
    * Postconditions: Returns the values of each pull into the pullWinnings array.
    */
   public boolean saveWinnings(int winnings)
   {
      if (numPulls < MAX_PULLS)
      {
         pullWinnings[numPulls] = winnings;
         numPulls += 1;
         return true;
      }
      else
         return false;
   }
   
   /* Purpose: Displays the overall winnings.
    * Preconditions: User must have given user input "0". This must be displayed in the main().
    * Postconditions: Returns the string of the total winnings.
    */
   public String displayWinnings()
   {
      int total = 0;
      System.out.println("Thanks for playing at the Casino!");
      for(int i = 0; i < numPulls; i++)
      {
         total += pullWinnings[i];
         System.out.print(pullWinnings[i] + " ");
      }
      return ("\nYour total winnings were: $" + total);
   }
}

/*
--Output--
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR BAR
Congrats, you won: 5000
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR BAR
Congrats, you won: 5000
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR BAR
Congrats, you won: 5000
How much would you like to bet (1-100) or 0 to quit?100
7 BAR BAR
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR BAR
Congrats, you won: 5000
How much would you like to bet (1-100) or 0 to quit?100
space BAR BAR
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
BAR space cherries
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
space BAR BAR
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR BAR
Congrats, you won: 5000
How much would you like to bet (1-100) or 0 to quit?100
cherries BAR BAR
Congrats, you won: 500
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR cherries
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR space
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
space BAR BAR
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
space BAR BAR
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR BAR
Congrats, you won: 5000
How much would you like to bet (1-100) or 0 to quit?100
cherries BAR cherries
Congrats, you won: 500
How much would you like to bet (1-100) or 0 to quit?100
cherries space BAR
Congrats, you won: 500
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR cherries
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
BAR space BAR
Sorry, you lose.
How much would you like to bet (1-100) or 0 to quit?100
cherries space BAR
Congrats, you won: 500
How much would you like to bet (1-100) or 0 to quit?100
BAR BAR space
Sorry, you lose.
Thanks for playing at the Casino!
5000 5000 5000 5000 5000 5000 0 0 5000 5000 0 0 0 0 0 0 5000 5000 500 500 0 0 0 0 0 0 0 0 5000 5000 500 500 500 500 0 0 0 0 500 500 
Your total winnings were: $64000
*/