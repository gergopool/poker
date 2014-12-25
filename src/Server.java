import java.util.Scanner;

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

}