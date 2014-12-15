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

      //while(!endOfRound())
      {
        if (needToBurn())
          burnCard();

        showCardOnBoard();
        takeBids();
      }

      shareCoinsAmongWinners();
    }

    //Some infos
    for (int i = 0; i < Init.NUMBER_OF_PLAYERS; i++)
      System.out.println(Table.players[i]);
    System.out.println(Table.getInfos());

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

    Table.setDealer(winner);
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