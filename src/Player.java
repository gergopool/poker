public class Player
{
  // The player's identification number
  private final int id;

  // The player's name
  private final String name;

  // The player's coins he's in posession
  private int noOfCoins;

  // The info if this player is bot or not
  private final boolean isItBot;

  // The cards the player has. It can be maximum 2.
  private Card[] hand = new Card[2];


  /**
   * Constructor.
   * Also sets the number of coins the user start with.
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

    noOfCoins = Init.COINS_AT_THE_BEGINNING;
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
                          id, name, hand[0], hand[1], noOfCoins);
  }


}