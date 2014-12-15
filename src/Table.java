public class Table
{
  //The players and the cards
  public static Player[] players;
  public static Deck deck;

  //Positions
  public static Player dealer;
  public static Player smallBlind;
  public static Player bigBlind;

  //Coins
  public static Coins pot;
  public static Coins blind;


  /**
   * Sets up the first dealer and also determines the small
   * and the big blind.
   *
   * @param theDealer The dealer.
   */
  public static void setDealer(Player theDealer)
  {
    int newDealerID = theDealer.getID();

    dealer = players[newDealerID];
    smallBlind = players[getProperID(newDealerID + 1)];
    bigBlind = players[getProperID(newDealerID + 2)];
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
    int newDealerID = (dealer.getID() + 1);

    dealer = players[getProperID(newDealerID)];
    smallBlind = players[getProperID(newDealerID + 1)];
    bigBlind = players[getProperID(newDealerID + 2)];
  }

  /**
   * Returns with the infos of the table.
   */
  public static String getInfos()
  {
    return "Dealer: " + dealer.getName() +
           "  Small blind: " + smallBlind.getName() +
           "  Big Blind: " + bigBlind.getName();
  }

}