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

  //Activity in the current round
  private boolean inRound = true;


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


  /**
   * Accessor.
   *
   * @return The id of the player.
   */
  public int getID()
  {
    return id;
  }


  /**
   * Accessor.
   *
   * @return The name of the player.
   */
  public String getName()
  {
    return name;
  }


  /**
   * Accessor.
   *
   * @return If the player is still in the round.
   */
  public boolean isInRound()
  {
    return inRound;
  }

  /**
   * Sets the inRound to true.
   * Needed to start a new round.
   */
  public void beInRound()
  {
    inRound = true;
  }

  /**
   * Sets the inRound to false.
   * Needed to jump over this user during the game.
   */
  public void beNotInRound()
  {
    inRound = false;
  }


  /**
   * Accessor.
   *
   * @return The hand.
   */
  public Card[] getHand()
  {
    return hand;
  }

  /**
   * Accessor.
   *
   * @return The coins.
   */
  public Coins getCoins()
  {
    return coins;
  }


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


  /**
   * Creates a readable format of the Player.
   *
   * @return The Player in String format.
   */
  public String toString()
  {
    return String.format("%02d %-" + Init.MAX_LENGTH_OF_NAME +"s [%s, %s] (%d)",
                          id, name, hand[0], hand[1], coins.getAmount());
  }


}