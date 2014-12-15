/**
 * This is the server of the poker program.
 *
 * @author Gergely Papp
 */
public class Server
{

  public static void main()
  {
    sitPlayersDown();
    determineFirstDealer();

    while(!endOfGame())
    {
      setDeck();
      shuffleDeck();
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
    }

  }//main



  private static void sitPlayersDown()
  {

  }


  private static void determineFirstDealer()
  {

  }


  private static boolean endOfGame()
  {
    return false;
  }


  private static void setDeck()
  {

  }


  private static void shuffleDeck()
  {

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