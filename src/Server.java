import java.util.Scanner;

/**
 * This is the server of the poker program.
 *
 * @author Gergely Papp
 */
public class Server
{
  public static void main(String[] args)
  {
    //Basic preparaion
    sitPlayersDown();
    Table.deck = new Deck();

    determineFirstDealer();

    //while(!endOfGame())
    {
      Table.deck.shuffle();
      dealCardsAmongPlayers();
      getMoneyFromBlinds();

      //Some infos
      for (int i = 0; i < Init.NUMBER_OF_PLAYERS; i++)
        System.out.println(Table.players[i]);
      System.out.println(Table.getInfos());

      Table.takeBids();

      //while(!endOfRound())
      {
        if (needToBurn())
          burnCard();

        showCardOnBoard();
        //takeBids();
      }

      shareCoinsAmongWinners();
    }


  }//main






  /**
   * This method defines some players who will play later on.
   * In order to define a player, we need its id, name,
   * and wheter if it is bot or not.
   */
  private static void sitPlayersDown()
  {
    Table.players = new Player[Init.NUMBER_OF_PLAYERS];
    for(int id = 0; id < Init.NUMBER_OF_PLAYERS; id++)
      Table.players[id] = new Player(id, "Name_" + id, false);
  }


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


  private static boolean endOfGame()
  {
    return false;
  }


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


  private static void getMoneyFromBlinds()
  {
    Table.askForBlinds();
  }


  private static boolean endOfRound()
  {
    return false;
  }


  private static boolean needToBurn()
  {
    return false;
  }


  private static void burnCard()
  {

  }


  private static void showCardOnBoard()
  {

  }


  private static void shareCoinsAmongWinners()
  {

  }


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

    if(amount < Table.blind.getAmount())
    {
      System.out.println("The amount must be at least the blind!");
      amount = askForAmountOfRaise();
    } 

    return amount;
  }

}