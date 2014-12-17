public class Table
{
  //The players and the cards
  public static Player[] players;
  public static Deck deck;
  public static Card[] board = new Card[5];
  public static int noOfCardsOnBoard;

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

  public static void showFlop()
  {
    showCardOnBoard();
    showCardOnBoard();
    showCardOnBoard();
  }

  public static void showCardOnBoard()
  {
    board[noOfCardsOnBoard] = deck.getTop();
    Server.showCard(board[noOfCardsOnBoard]);
    noOfCardsOnBoard++;
  }

  public static void burnCard()
  {
    deck.getTop();
  }

  public static boolean endOfRound()
  {
    return currentPlayerID == getNextPlayerID(currentPlayerID);
  }


  /**
   * Looks up who the next palyer is gonna be.
   */
  public static void determineNextPlayerID()
  {
    currentPlayerID = getProperID(currentPlayerID + 1);
    if (!players[currentPlayerID].isInRound())
      determineNextPlayerID();
  }

  public static int getNextPlayerID(int idNow)
  {
    int nextID = getProperID(idNow + 1);
    if (!players[nextID].isInRound())
      return getNextPlayerID(nextID);

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
   * Moving coins from players to the pot.
   */
  public static void moveCoinsToPot()
  {
    for (int playerID = 0; playerID < Init.NUMBER_OF_PLAYERS; playerID++)
    {
      int amount = players[playerID].getPreparedCoins().getAmount();
      players[playerID].getPreparedCoins().erase();
      pot.add(amount);
    }

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
    if (noOfCardsOnBoard != 0)
    {
      maxPreparedCoins.erase();
    }
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

      determineNextPlayerID();

    } while(lastRaiserID != currentPlayerID && !endOfRound());

    moveCoinsToPot();
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