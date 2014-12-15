public class Coins
{
  //The amount of coins
  private int amount;


  /**
   * Constuctor
   * 
   * @param theAmount The amount of coins
   */
  public Coins(int theAmount)
  {
    amount = theAmount;
  }


  /**
   * Accessor.
   *
   * @return The amount of coins.
   */
  public int getAmount()
  {
    return amount;
  }


  /**
   * Add some coins to the amount.
   *
   * @param howMany How many coins wished to add.
   */
  public void add(int howMany)
  {
    amount += howMany;
  }


  /**
   * Subtract some coins to the amount.
   *
   * @param howMany How many coins wished to subtract.
   */
  public void subtract(int howMany)
  {
    amount -= howMany;
  }

}