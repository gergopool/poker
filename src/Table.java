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
  public static Coins pot;
  public static Coins blind;


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