public class Table
{
  //The players and the cards
  public static Player[] players;
  public static Deck deck;
  public static Card[] board = new Card[5];
  public static int noOfCardsOnBoard;
  public static int noOfActivePlayers = Init.NUMBER_OF_PLAYERS;

  //Positions
  public static int dealerID;
  public static int smallBlindID;
  public static int bigBlindID;
  public static int currentPlayerID;
  public static int lastSpeakerID;

  //Coins
  public static Coins pot = new Coins(0);
  public static Coins maxPreparedCoins = new Coins(0);
  public static Coins blind = new Coins(Init.BLIND_AT_BEGINNING);


  // ----------------------------------------------------------------

   /**
   * This method defines some players who will play later on.
   * In order to define a player, we need its id, name,
   * and wheter if it is bot or not.
   */
  public static void sitPlayersDown()
  {
    players = new Player[Init.NUMBER_OF_PLAYERS];
    for(int id = 0; id < Init.NUMBER_OF_PLAYERS; id++)
    {
      boolean bot = id < Init.NUMBER_OF_BOTS;
      players[id] = new Player(id, "Name_" + id, bot);
    }
  }

  // ----------------------------------------------------------------

  /**
   * Sets up the first dealer and also determines the small
   * and the big blind.
   *
   * @param theDealerID The dealer's id.
   */
  public static void setDealer(int theDealerID)
  {
    dealerID = getProperID(theDealerID);
    smallBlindID = getProperID(dealerID + 1);
    bigBlindID = getProperID(dealerID + 2);
    currentPlayerID = getProperID(dealerID + 3);
  }

  // ----------------------------------------------------------------

  /**
   * Shows 3 card on the board.
   */
  public static void showFlop()
  {
    showCardOnBoard();
    showCardOnBoard();
    showCardOnBoard();
  }

  // ----------------------------------------------------------------

  /** 
   * Determines if it is the end of the round.
   *
   * @return If the end of the round.
   */
  public static boolean endOfRound()
  {
    return getNextPlayerID(currentPlayerID) == currentPlayerID;
  }

  // ----------------------------------------------------------------

  /**
   * Shows a card on the board, on the screen.
   */
  public static void showCardOnBoard()
  {
    board[noOfCardsOnBoard] = deck.getTop();
    Server.showCard(board[noOfCardsOnBoard]);
    noOfCardsOnBoard++;
  }

  // ----------------------------------------------------------------

  /**
   * Burns a card from the top of the deck.
   */
  public static void burnCard()
  {
    deck.getTop();
  }

  // ----------------------------------------------------------------

  /**
   * Looks up who the next palyer is gonna be.
   */
  public static void determineNextPlayerID()
  {
    currentPlayerID = getProperID(currentPlayerID + 1);
    if (!players[currentPlayerID].isInRound())
      determineNextPlayerID();
  }

  // ----------------------------------------------------------------

  /**
   * Determines the previous player's ID.
   *
   * @param idNow The current id.
   *
   * @return The id.
   */
  public static int getPreviousPlayerID(int idNow)
  {
    int previousID = getProperID(idNow - 1);
    if (!players[previousID].isInRound())
      return getPreviousPlayerID(previousID);

    return previousID;
  }

  // ----------------------------------------------------------------

  /**
   * Determines the next player's ID.
   *
   * @param idNow The current id.
   *
   * @return The id.
   */
  public static int getNextPlayerID(int idNow)
  {
    int nextID = getProperID(idNow + 1);
    if (!players[nextID].isInRound())
      return getNextPlayerID(nextID);

    return nextID;
  }

  // ----------------------------------------------------------------

  /**
   * For over Bound execption, we determine the proper id of a big id.
   *
   * @param currentID The current id that might be greater than no. of players.
   *
   * @return The proper id.
   */
  public static int getProperID(int currentID)
  {
    return (currentID + Init.NUMBER_OF_PLAYERS) % Init.NUMBER_OF_PLAYERS;
  }

  // ----------------------------------------------------------------

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

    maxPreparedCoins.erase();
  }

  // ----------------------------------------------------------------

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

  // ----------------------------------------------------------------

  /**
   * A round of game.
   * It is not done until there has been no raise for a whole round.
   */
  public static void takeBids()
  {
    int i = 0;
    String action = "";

    refreshOnPlayersCombinations();
    lastSpeakerID = getPreviousPlayerID(currentPlayerID);

    do
    {
      // Asking fr action from player or bot
      if (players[currentPlayerID].getIsItBot())
        action = players[currentPlayerID].getSimulation().makeDecision();
      else
        action = Server.askForAction();

      // Passing the action to the player
      switch(action.toLowerCase())
      {
        // Cases
        case "check" : players[currentPlayerID].check(); break;
        case "call"  : players[currentPlayerID].call(); break;
        case "bet"   : players[currentPlayerID].bet(); break;
        case "raise" : players[currentPlayerID].raise(); break;
        case "fold"  : players[currentPlayerID].fold(); break;

        // This should never happen
        default : System.out.println("WTF!"); break;
      }

      if(currentPlayerID == lastSpeakerID)
        break;

      determineNextPlayerID();

    } while(!endOfRound());

    moveCoinsToPot();
  }

  // ----------------------------------------------------------------

  /**
   * Refreshes the combination of all players, even on inactive ones.
   */
  public static void refreshOnPlayersCombinations()
  {
    for(int playerID = 0; playerID < Init.NUMBER_OF_PLAYERS; playerID++)
      players[playerID].refreshCombination();
  }

  // ----------------------------------------------------------------

  /**
   * Shifts the dealer button.
   */
  public static void shiftDealer()
  {
    setDealer(dealerID + 1);
  }

  // ----------------------------------------------------------------

  /**
   * Asks for the dealer and the blinds.
   *
   * @return The blind and the dealers.
   */
  public static String getInfos()
  {
    //Dealer: name  Small blind: name  Big blind: name
    return "Dealer: " + players[dealerID].getName() +
           "  Small blind: " + players[smallBlindID].getName() +
           "  Big blind: " + players[bigBlindID].getName();
  }

  // ----------------------------------------------------------------

  /**
   * Sorts the combination into descending order, by their combination.
   *
   * @param thePlayers The unsorted Player array.
   *
   * @return The sorted array of players.
   */
  private static Player[] sortPlayersByCombinationDesc(Player[] thePlayers)
  {
    Player[] somePlayers = new Player[thePlayers.length];
    for(int i = 0; i < thePlayers.length; i++)
      somePlayers[i] = thePlayers[i];


    for(int i = 0; i < somePlayers.length - 1; i++)
      for (int j = i + 1; j < somePlayers.length; j++)
        if (somePlayers[i].getCombination().compareTo
                                  (somePlayers[j].getCombination()) < 0)
        {
          Player temp = somePlayers[i];
          somePlayers[i] = somePlayers[j];
          somePlayers[j] = temp;
        }

    return somePlayers;
  }

  // ----------------------------------------------------------------

  /**
   * Get the coins of the pot and share them among the winners.
   */
  public static void shareCoinsAmongWinners()
  {
    Player[] playersInRank = sortPlayersByCombinationDesc(players);
    playersInRank = filterOutInactivePlayers(playersInRank);
    int noOfWinners = 0;

    do
    { noOfWinners++; }
    while(noOfWinners < playersInRank.length
          && playersInRank[noOfWinners].getCombination().compareTo
                      (playersInRank[noOfWinners - 1].getCombination()) == 0);

    int prize = pot.getAmount() / noOfWinners;

    System.out.println();
    System.out.println("The winners: ");
    for(int i = 0; i < noOfWinners; i++)
    {
      playersInRank[i].getCoins().add(prize);
      System.out.println(playersInRank[i]);
    }

    pot.erase();
  }

  // ----------------------------------------------------------------

  /**
   * Determines an array of active players, who are still in round.
   *
   * @param thePlayers The inactive and active players.
   *
   * @return Only the active players.
   */
  private static Player[] filterOutInactivePlayers(Player[] thePlayers)
  {
    Player[] activePlayers = new Player[thePlayers.length];
    int theNoOfActivePlayers = 0;

    for (int i = 0; i < thePlayers.length; i++)
      if (thePlayers[i].isInRound())
      {
        activePlayers[theNoOfActivePlayers] = thePlayers[i];
        theNoOfActivePlayers++;
      }

    Player[] correctSizedPlayers = new Player[theNoOfActivePlayers];
    for (int i = 0; i< theNoOfActivePlayers; i++)
      correctSizedPlayers[i] = activePlayers[i];

    noOfActivePlayers = theNoOfActivePlayers;

    return correctSizedPlayers;
  }


}