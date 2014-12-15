/**
 * This is the server of the poker program.
 *
 * @author Gergely Papp
 */
public class Server
{

  public static void main(String[] args)
  {
    sitPlayersDown();
    determineFirstDealer();

    /*while(!endOfGame())
    {
      deck = new Deck();
      deck.shuffle();

      dealCardsAmongPlayers();
      getMoneyFromBlinds();

      while(!endOfRound())
      {
        if (needToBurn())
          burnCard();

        showCardOnBoard();
        takeBids();
      }

      shareCoinsAmongWinners();
    }*/


    for (int i = 0; i < Init.NUMBER_OF_PLAYERS; i++)
      System.out.println(players[i]);

  }//main



  //The players who are currently playing
  private static Player[] players;

  //The deck
  private static Deck deck;






  /**
   * This method defines some players who will play later on.
   * In order to define a player, we need its id, name,
   * and wheter if it is bot or not.
   */
  private static void sitPlayersDown()
  {
    players = new Player[Init.NUMBER_OF_PLAYERS];
    for(int id = 0; id < Init.NUMBER_OF_PLAYERS; id++)
      players[id] = new Player(id, "Name_" + id, false);
  }


  private static void determineFirstDealer()
  {
    deck = new Deck();
    deck.shuffle();
    
    for(Player player : players)
      player.setCard(deck.getTop(), 0);

  }


  private static boolean endOfGame()
  {
    return false;
  }



  private static void dealCardsAmongPlayers()
  {
    
  }


  private static void getMoneyFromBlinds()
  {

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


  private static void takeBids()
  {

  }


  private static void shareCoinsAmongWinners()
  {

  }




}