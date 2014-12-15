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
   * Creates a readable format of the Player.
   *
   * @return The Player in String format.
   */
  public String toString()
  {
    return String.format("%02d %-" + Init.MAX_LENGTH_OF_NAME + "s (%d)",
                          id, name, noOfCoins);
  }


}