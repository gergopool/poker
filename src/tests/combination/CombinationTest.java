import java.io.File;
import java.util.Scanner;

/**
 * The program asks for a test data from test_data.txt file
 * and matches all the combinations with the virtual server.
 *
 * An example line in test_data:
 * SK, C6, H7, C5, SJ, S8, H9; 490000
 *
 * If the test does not match with the one returned from the machine,
 * the program exits and puts its issue onto the screen.
 *
 * @author Gergely Papp
 *
 */


public class CombinationTest
{
  public static void main(String[] args) throws Exception
  {
    Scanner fileScanner = new Scanner(new File("test_data.txt"));
    String[] content = readFromFile(fileScanner);
    int contentLength = content.length;

    //The array of the cards to simulate
    String[][] cards = new String[contentLength][];

    //The expected results from the machine
    String[] values = new String[contentLength];

    //Initializing the cards and the values arrays
    String split[];
    String[] splitedCards;
    for (int i = 0; i < contentLength; i++)
    {
      //Cards and values separator
      split = content[i].split("; ");

      //The cards
      splitedCards = split[0].split(", ");
      cards[i] = new String[splitedCards.length];
      for (int j = 0; j < splitedCards.length; j++)
        cards[i][j] = splitedCards[j];
      
      //The values
      values[i] = split[1];

    }

    // Matching the values with the machine results

    String currentResultOfMachine;
    for(int i = 0; i < contentLength; i++)
    {
      currentResultOfMachine = getResult(cards[i]);
      if (!currentResultOfMachine.equals(values[i]))
      {
        // The results do not match
        System.out.println("The results does not match here: ");
        for (int j = 0; j < cards[i].length - 1; j++)
          System.out.print(cards[i][j] + ", ");
        System.out.print(cards[i][cards[i].length - 1] + "; ");
        System.out.print(values[i] + " != " + currentResultOfMachine);
        System.out.println(" (" + (i + 1) + ")");
        System.exit(0);
      }

    }

    //We did not exit, so this message can appear
    System.out.println("Your testing was successfull!");

  }//main




  /**
   * Forwards the cards to the main program and returns with
   * the combination the server responded.
   *
   * @param cards The cards from the file
   *
   * @return The combination defined by server.
   */
  private static String getResult(String[] cards)
  {
    Card[] theCards = new Card[cards.length];

    int rank = 0;
    int colour = 0;


    for (int i = 0; i < cards.length; i++)
    {     
      colour = getColour(cards[i].charAt(0));
      rank = getRank(cards[i].substring(1, cards[i].length()));

      if (colour == -1 || rank == -1)
      {
        System.out.println("Misstyping at row: " + (i + 1));
        System.exit(0);
      }
      
      theCards[i] = new Card(colour, rank);

    }

    
    Combination comb = new Combination(theCards);

    return comb.s_getValue();
  }




  // Converts colour from readable to number format
  private static int getColour(char colour)
  {
    switch(Character.toUpperCase(colour))
    {
      case 'S' : return 3;
      case 'H' : return 2;
      case 'D' : return 1;
      case 'C' : return 0;
      default  : return -1;
    }
  }


  // Converts rank from readable to number format
  private static int getRank(String rank)
  {
    switch(rank)
    {
      case "A" : return 14;
      case "K" : return 13;
      case "Q" : return 12;
      case "J" : return 11;
      case "10": return 10;
      case "9" : return 9;
      case "8" : return 8;
      case "7" : return 7;
      case "6" : return 6;
      case "5" : return 5;
      case "4" : return 4;
      case "3" : return 3;
      case "2" : return 2;
      default  : return -1;
    }
  }




  /**
   * This method returns with the content of a file.
   *
   * @param scanner The file scanner to the file.
   *
   * @return The content of the file in String[].
   */
  private static String[] readFromFile(Scanner scanner)
  {
    int INITAL_ARRAY_SIZE = 2;
    int ARRAY_RESIZE_FACTOR = 2;

    //The content of the file the method returns
    String[] fileContent = new String[INITAL_ARRAY_SIZE];

    int numberOfLines = 0;
    while(scanner.hasNextLine())
    {
      //Read in one line from the file
      String currentLine = scanner.nextLine();

      //If the array is too small, extend it
      if(fileContent.length == numberOfLines)
      {
        String[] biggerArray =
                       new String[fileContent.length * ARRAY_RESIZE_FACTOR];
        for(int index = 0; index < fileContent.length; index++)
          biggerArray[index] = fileContent[index];

        fileContent = biggerArray;
      }

      //Add the current line to the content and increase the number of lines
      fileContent[numberOfLines] = currentLine;
      numberOfLines++;
    }

    //Returning with the correct size of the array
    String[] returnArray = new String[numberOfLines];
    for(int index = 0; index < numberOfLines; index++)
          returnArray[index] = fileContent[index];


    return returnArray;
  }
}
