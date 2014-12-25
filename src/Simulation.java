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

  public Simulation(Player theOwner)
  {
    owner = theOwner;
  }

  public String makeDecision()
  {
    double random = Math.random();
    String chosen = "";
    if (random > 0.7 && owner.ableToCall())
      chosen = "call";
    else if (random > 0.1 && owner.ableToCheck())
      chosen = "check";
    else
      return makeDecision();


    System.out.println("Computer chose: " + chosen);
    return chosen;
  }

}