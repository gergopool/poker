/**
 * This class simulates te actions of the players.
 * This is a kind of bot-program, later-on based on AI.
 *
 * @author Gergely Papp
 */
public class Simulation
{
  // The cards needed for the appropriate decisions
  private Player owner;


  /**
   * Constructor.
   *
   * @param theOwner The owner player of the simulation.
   */
  public Simulation(Player theOwner)
  {
    owner = theOwner;
  }

  // ----------------------------------------------------------------

  /**
   * Decides wheter ot check, call, fold, raise or bet.
   *
   * @return The decision.
   */
  public String makeDecision()
  {
    double random = Math.random();
    String chosen = "";

    if (random > 0.9 && owner.ableToBet())
      chosen = "bet";
    else if (random > 0.8 && owner.ableToRaise())
      chosen = "raise";
    else if (random > 0.7 && owner.ableToCall())
      chosen = "call";
    else if (random > 0.1 && owner.ableToCheck())
      chosen = "check";
    else if (random < 0.05)
      chosen = "fold";
    else
      return makeDecision();


    System.out.println("Computer chose: " + chosen);
    return chosen;
  }

  // ----------------------------------------------------------------

  /**
   * Asks for the combination value of the board.
   *
   * @return The value of the board's combination.
   */
  private String getBoardCombination()
  {
    Card[] nullCards = new Card[0];
    Combination boardCombination = new Combination(nullCards);

    return boardCombination.s_getValue();
  }

  // ----------------------------------------------------------------

  /**
   * Gives how many coins the computer desire to raise or bet.
   *
   * @return The amount of coins.
   */
  public int askForAmountOfRaise()
  {
    int coinsLeft = owner.getCoins().getAmount()
                                         - Table.maxPreparedCoins.getAmount();

    if (coinsLeft < 140)
      return coinsLeft;
    else
      return 140;
  }

}