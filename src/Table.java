public class Table
{
  //The players and the cards
  public static Player[] players;
  public static Deck deck;

  //Positions
  public static int dealerID;
  public static int smallBlindID;
  public static int bigBlindID;
  public static int nextPlayerID;

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
    nextPlayerID = getProperID(dealerID + 3);
  }


  /**
   * Looks up who the next palyer is gonna be.
   */
  public static void determineNextPlayerID()
  {
    nextPlayerID = getProperID(nextPlayerID + 1);
    if (!players[nextPlayerID].isInRound())
      determineNextPlayerID();
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

  public static void askForBlinds()
  {
    players[smallBlindID].prepareCoins(blind.getAmount() / 2);
    players[bigBlindID].prepareCoins(blind.getAmount());
    maxPreparedCoins = new Coins(blind.getAmount());
  }

  public static void takeBids()
  {
    int i = 0;
    while(i < 10)
    {
      System.out.print(players[getProperID(nextPlayerID)] + ": ");
      String action = Init.INPUT_SCANNER.nextLine();
      
      switch(action.toLowerCase())
      {
        case "check" : players[nextPlayerID].check(); break;
        case "call"  : players[nextPlayerID].call(); break;
        case "bet"   : players[nextPlayerID].bet(); break;
        case "raise" : players[nextPlayerID].raise(); break;
        case "fold"  : players[nextPlayerID].fold(); break;
        default : i--; nextPlayerID--; break;
      }
      i++;
      determineNextPlayerID();
    }
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