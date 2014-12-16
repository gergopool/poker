public class Table
{
  //The players and the cards
  public static Player[] players;
  public static Deck deck;

  //Positions
  public static int dealerID;
  public static int smallBlindID;
  public static int bigBlindID;
  public static int currentPlayerID;
  public static int lastRaiserID;

  //Coins
  public static Coins pot = new Coins(0);
  public static Coins maxPreparedCoins = new Coins(0);
  public static Coins blind = new Coins(Init.BLIND_AT_BEGINNING);


  /**
   * Sets up the first dealer and also determines the small
   * and the big blind.
   *
   * @param theDealer The dealer.
   */
  public static void setDealer(int theDealerID)
  {
    dealerID = getProperID(theDealerID);
    smallBlindID = getProperID(dealerID + 1);
    bigBlindID = getProperID(dealerID + 2);
    currentPlayerID = getProperID(dealerID + 3);
    lastRaiserID = currentPlayerID;
  }


  /**
   * Looks up who the next palyer is gonna be.
   */
  public static int determineNextPlayerID()
  {
    int nextID = getProperID(currentPlayerID + 1);
    if (!players[nextID].isInRound())
      determineNextPlayerID();

    return nextID;
  }


  /**
   * For over Bound execption, we determine the proper id of a big id.
   *
   * @return The proper id.
   */
  public static int getProperID(int currentID)
  {
    return currentID % Init.NUMBER_OF_PLAYERS;
  }

  /**
   * Moving coins from a player to the pot.
   *
   * @param playerID The player's id who move's money to the pot
   * @param theAmount The amount of coins desired to move.
   */
  public static void moveCoinsToPot(int playerID)
  {
    int amount = players[playerID].getPreparedCoins().getAmount();
    players[playerID].getPreparedCoins().subtract(amount);
    pot.add(amount);
  }

  /**
   * Prepares the blind's coins and alter the maxPreparedCoins to it.
   */
  public static void askForBlinds()
  {
    //Preparing the blinds' coins and increase the maxPreparedCoins
    players[smallBlindID].prepareCoins(blind.getAmount() / 2);
    players[bigBlindID].prepareCoins(blind.getAmount());
    maxPreparedCoins = new Coins(blind.getAmount());
  }

  /**
   * A round of game.
   * It is not done until there has been no raise for a whole round.
   */
  public static void takeBids()
  {
    int i = 0;
    String action = "";
    lastRaiserID = currentPlayerID;

    do
    {
      action = Server.askForAction();
      
      //Passing the action to the player
      switch(action.toLowerCase())
      {
        //Cases
        case "check" : players[currentPlayerID].check(); break;
        case "call"  : players[currentPlayerID].call(); break;
        case "bet"   : players[currentPlayerID].bet(); break;
        case "raise" : players[currentPlayerID].raise(); break;
        case "fold"  : players[currentPlayerID].fold(); break;

        // This should never happen
        default : System.out.println("WTF!"); break;
      }

      currentPlayerID = determineNextPlayerID();

    } while(lastRaiserID != currentPlayerID);
  }


  /**
   * Shifts the dealer button.
   */
  public static void shiftDealer()
  {
    setDealer(dealerID + 1);
  }

  /**
   * Returns with the infos of the table.
   */
  public static String getInfos()
  {
    //Dealer: name  Small blind: name  Big blind: name
    return "Dealer: " + players[dealerID].getName() +
           "  Small blind: " + players[smallBlindID].getName() +
           "  Big blind: " + players[bigBlindID].getName();
  }

}