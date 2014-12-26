import java.util.Scanner;
import java.io.File;

/**
 * This is the server of the poker program. It is only responsible for the
 * inputs and outputs.
 *
 * @author Gergely Papp
 */
public class Server
{
  public static void main(String[] args)
  {
    //Basic preparaion
    Table.sitPlayersDown();
    Table.deck = new Deck();

    determineFirstDealer();

    //while(!endOfGame())
    {
      Table.deck.shuffle();
      dealCardsAmongPlayers();
      Table.askForBlinds();

      Table.takeBids();

      if (!Table.endOfRound())
      {
        Table.showFlop();
        Table.takeBids();
      }

      if (!Table.endOfRound())
      {
        Table.burnCard();
        Table.showCardOnBoard();
        Table.takeBids();
      }

      if (!Table.endOfRound())
      {
        Table.burnCard();
        Table.showCardOnBoard();
        Table.takeBids();
      }

      Table.shareCoinsAmongWinners();
    }

    
    // Some infos to the developer. It's just not here
    System.out.println();
    for (int i = 0; i < Init.NUMBER_OF_PLAYERS; i++)
      System.out.println(Table.players[i]);
    System.out.println(Table.getInfos());
    

  }//main


  // ----------------------------------------------------------------
  // ----------------------------------------------------------------


  /**
   * Determining the first dealer.
   * Method: Deal cards among the players, and the one
   * with the most valued card will be the dealer.
   */
  private static void determineFirstDealer()
  {
    Table.deck.shuffle();
    Player winner = Table.players[0];
    
    for(Player player : Table.players)
      player.setCard(Table.deck.getTop(), 0);

    for(Player player : Table.players)
      if (player.getHand()[0].getValueForDealing() >
                                   winner.getHand()[0].getValueForDealing())
        winner = player;

    Table.setDealer(winner.getID());
  }

  // ----------------------------------------------------------------

  /**
   * Dealing cards among the players in the form of the real game.
   */
  private static void dealCardsAmongPlayers()
  {
    for(int round = 0; round < 2; round++)
      for(int adder = 1; adder <= Init.NUMBER_OF_PLAYERS; adder++)
        Table.players[Table.getProperID(Table.dealerID + adder)]
                                       .setCard(Table.deck.getTop(), round);
  }

  // ----------------------------------------------------------------

  /**
   * Shows a card on the screen.
   *
   * @param card The card desired to show.
   */
  public static void showCard(Card card)
  {
    System.out.println(card);
  }

  // ----------------------------------------------------------------


  /**
   * The players migh bed or raise on the pot. This method
   * asks the player how much he desires to raise. If it is more
   * than the coins the player has, we kick his ass. 
   *
   * @return The amount of raise.
   */
  public static int askForAmountOfRaise()
  {
    int amount;
    System.out.print("Amount: ");
    String s_amount  = Init.INPUT_SCANNER.nextLine();
    try
    {
      amount = Integer.parseInt(s_amount);
    }
    catch(Exception e)
    {
      System.out.println("The amount must be an integer!");
      amount = askForAmountOfRaise();
    }


    int coinsLeft = Table.players[Table.currentPlayerID].getCoins().getAmount()
                                         - Table.maxPreparedCoins.getAmount();
    if (coinsLeft < amount)
    {
      System.out.println("This amount is too much for your coins!");
      amount = askForAmountOfRaise();
    }


    if(amount < Table.blind.getAmount())
    {
      System.out.println("The amount must be at least the blind!");
      amount = askForAmountOfRaise();
    } 

    return amount;
  }

  // ----------------------------------------------------------------

  /**
   * The players has different action when they "speak".
   * These actions can be: bet, call, check, fold, raise.
   * The method also handles the incompatible inputs.
   *
   * @return The action the player has chosen.
   */
  public static String askForAction()
  {
    Player thePlayer = Table.players[Table.getProperID(Table.currentPlayerID)];
    System.out.print(thePlayer.getName() + ": ");
    String action = Init.INPUT_SCANNER.nextLine();

    // Checking if the player is allowed to do the desired action
    switch(action.toLowerCase())
      {
        case "check" : if (!thePlayer.ableToCheck())
                       {
                          System.out.println("You cannot check now!");
                          return askForAction();
                       }
                       break;

        case "call"  : if (!thePlayer.ableToCall())
                       {
                          System.out.println("You cannot call now!");
                          return askForAction();
                       }
                       break;

        case "bet"   : if (!thePlayer.ableToBet())
                       {
                          System.out.println("You cannot bet now!");
                          return askForAction();
                       }
                       break;

        case "raise" : if (!thePlayer.ableToRaise())
                       {
                          System.out.println("You cannot raise now!");
                          return askForAction();
                       }
                       break;

        case "fold"  : break;


        default : System.out.println("This is not an action!");
                  return askForAction();
      }

    return action;
  }

  // ----------------------------------------------------------------

  /**
   * This method returns with the content of a file.
   *
   * @param scanner The file scanner to the file.
   *
   * @return The content of the file in String[].
   */
  public static String[] readFromFile(String fileName) throws Exception
  {
    Scanner scanner = new Scanner(new File(fileName));

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