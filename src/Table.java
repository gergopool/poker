public class Table
{
  //The players and the cards
  public static Player[] players;
  public static Deck deck;

  //Positions
  public static int dealerID;
  public static int smallBlindID;
  public static int bigBlindID;

  //Coins
  public static Coins pot = new Coins(0);
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
  public static void moveCoinsToPot(int playerID, int theAmount)
  {
    players[playerID].getCoins().subtract(theAmount);
    pot.add(theAmount);
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
    return "Dealer: " + players[dealerID].getName() +
           "  Small blind: " + players[smallBlindID].getName() +
           "  Big Blind: " + players[bigBlindID].getName();
  }

}