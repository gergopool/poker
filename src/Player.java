public class Player
{
  // The player's identification number
  private final int id;

  // The player's name
  private final String name;

  // The player's coins he's in posession
  private Coins coins = new Coins(Init.COINS_AT_BEGINNING);

  // The player's coins he prepared for giving to the pot
  private Coins preparedCoins = new Coins(0);

  // The info if this player is bot or not
  private final boolean isItBot;

  // The cards the player has. It can be maximum 2.
  private Card[] hand = new Card[2];

  // The combination of the cards 
  private Combination combination;

  // Activity in the current round
  private boolean inRound = true;

  // The simulation in case of the player is a bot
  private Simulation bot = new Simulation(this);


  /**
   * Constructor.
   *
   * @param theID The id of this palyer.
   * @param theName The name of this player.
   * @param theIsItBot Is it bot or not.
   */
  public Player(int theID, String theName, boolean theIsItBot)
  {
    id = theID;
    name = theName;
    isItBot = theIsItBot;
  }


  // ----------------------------------------------------------------
  //   ACCESSORS --->
  // ----------------------------------------------------------------



  /**
   * Accessor.
   *
   * @return The id of the player.
   */
  public int getID()
  {
    return id;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The name of the player.
   */
  public String getName()
  {
    return name;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The status of the player.
   */
  public boolean getIsItBot()
  {
    return isItBot;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The combination of the player.
   */
  public Combination getCombination()
  {
    return combination;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return If the player is still in the round.
   */
  public boolean isInRound()
  {
    return inRound;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The simulation of the player.
   */
  public Simulation getSimulation()
  {
    return bot;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The hand.
   */
  public Card[] getHand()
  {
    return hand;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The coins.
   */
  public Coins getCoins()
  {
    return coins;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The prpared coins.
   */
  public Coins getPreparedCoins()
  {
    return preparedCoins;
  }



  // ----------------------------------------------------------------
  //   MUTATORS --->
  // ----------------------------------------------------------------


  /**
   * Sets the inRound to true.
   * Needed to start a new round.
   */
  public void beInRound()
  {
    inRound = true;
  }

  // ----------------------------------------------------------------

  /**
   * Sets the inRound to false.
   * Needed to jump over this user during the game.
   */
  public void beNotInRound()
  {
    inRound = false;
  }

  // ----------------------------------------------------------------

  /**
   * Refreshes on the combination. It is needed right after a
   * new card is shown on the board.
   */
  public void refreshCombination()
  {
    combination = new Combination(hand);
  }

  // ----------------------------------------------------------------

  /**
   * Moves a specific amount of coins from the ones in front of
   * the player to the prepared ones.
   *
   * @param howMany The amount of coins desired to move.
   */
  public void prepareCoins(int howMany)
  {
    howMany = (howMany > coins.getAmount()) ? coins.getAmount() : howMany;
    coins.subtract(howMany);
    preparedCoins.add(howMany);
  }

  // ----------------------------------------------------------------

  /**
   * Set a card into the hand of the user
   *
   * @param card The card.
   * @param place The place of the card in the user's hand (0v1)
   */
  public void setCard(Card card, int place)
  {
    hand[place] = card;
  }



  // ----------------------------------------------------------------
  //   THE ACTIONS OF A USER --->
  // ----------------------------------------------------------------



  /**
   * Action: Call
   * The player prepare the amount of coins that is the max prepared.
   */
  public void call()
  {
    int howMuchLeft = Table.maxPreparedCoins.getAmount()
                                                - preparedCoins.getAmount();

    //NEED TO ADD
      //The player might have less coins than the needed amount

    coins.subtract(howMuchLeft);
    preparedCoins.add(howMuchLeft);
  }

  // ----------------------------------------------------------------

  /**
   * Action: Check
   * The player hand over the action.
   */
  public void check()
  {
  }

  // ----------------------------------------------------------------

  /**
   * Action: Fold
   * The player quits from the round.
   */
  public void fold()
  {
    inRound = false;
  }

  // ----------------------------------------------------------------

  /**
   * Action: Raise
   * The player raise the amount of prepared money.
   */
  public void raise()
  {
    int amountOfRaise = 0;
    if (isItBot)
      amountOfRaise = bot.askForAmountOfRaise();
    else
      amountOfRaise = Server.askForAmountOfRaise();

    //First give the amount that is left and add then add the amount of raise
    int howMuchLeft = Table.maxPreparedCoins.getAmount()
                                                - preparedCoins.getAmount();
    //NEED TO ADD
      //The player might have less coins than the needed amount

    coins.subtract(howMuchLeft + amountOfRaise);
    preparedCoins.add(howMuchLeft + amountOfRaise);

    Table.maxPreparedCoins.add(amountOfRaise);
    Table.lastSpeakerID = Table.getPreviousPlayerID(id);
  }

  // ----------------------------------------------------------------


  public void bet()
  {
    int amountOfBet = 0;
    if (isItBot)
      amountOfBet = bot.askForAmountOfRaise();
    else
      amountOfBet = Server.askForAmountOfRaise();

    coins.subtract(amountOfBet);
    preparedCoins.add(amountOfBet);

    Table.maxPreparedCoins.add(amountOfBet);
    Table.lastSpeakerID = Table.getPreviousPlayerID(id);
  }

  // ----------------------------------------------------------------
  // ----------------------------------------------------------------


  /**
   * Returns wheter the player is able to check or not.
   *
   * @return The ability.
   */
  public boolean ableToCheck()
  {
    return Table.maxPreparedCoins.getAmount()
                              == preparedCoins.getAmount();
  }

  // ----------------------------------------------------------------

  /**
   * Returns wheter the player is able to call or not.
   *
   * @return The ability.
   */
  public boolean ableToCall()
  {
    return Table.maxPreparedCoins.getAmount()
                              != preparedCoins.getAmount();
  }

  // ----------------------------------------------------------------

  /**
   * Returns wheter the player is able to bet or not.
   *
   * @return The ability.
   */
  public boolean ableToBet()
  {
    return Table.maxPreparedCoins.getAmount() == 0
            && coins.getAmount() > 0;
  }

  // ----------------------------------------------------------------

  /**
   * Returns wheter the player is able to raise or not.
   *
   * @return The ability.
   */
  public boolean ableToRaise()
  {
    return Table.maxPreparedCoins.getAmount() != 0
            && coins.getAmount() - Table.maxPreparedCoins.getAmount() > 0;
  }



  // ----------------------------------------------------------------
  //   CONVERTERS --->
  // ----------------------------------------------------------------



  /**
   * Creates a readable format of the Player.
   *
   * @return The Player in String format.
   */
  public String toString()
  {
    return String.format("%02d %-" + Init.MAX_LENGTH_OF_NAME +
                         "s [%-4s %-4s] (%d) %s %s",
                         id, name, hand[0], hand[1], coins.getAmount(),
                         combination, isInRoundToString());
  }

  // ----------------------------------------------------------------

  /**
   * Converts isInRound to string.
   * It's + if it is in round, - if not.
   *
   * @return The representer char.
   */
  private String isInRoundToString()
  {
    return inRound ? "+" : "-";
  }


}