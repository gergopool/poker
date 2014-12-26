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

  // ----------------------------------------------------------------
  //    DECISIONS
  // ----------------------------------------------------------------

  /**
   * Decides wheter ot check, call, fold, raise or bet, depending on
   * on the number of cards on the board.
   *
   * @return The decision.
   */
  public String makeDecision()
  {
    switch (Table.noOfCardsOnBoard)
    {
      case 0 : return decideBeforeFlop();
      case 3 : return decideAtFlop();
      case 4 : return decideAtTurn();
      default: return decideAtRiver();
    }
  }

  // ----------------------------------------------------------------

  /**
   * Makes a decision (bet, raise, fold, call, check) before the flop.
   *
   * @return The decision.
   */
  private String decideBeforeFlop()
  {
    //The odds
    String[] oddTable = new String[0];
    try{
      oddTable = Server.readFromFile("data/odds_from_hand.txt");
    }
    catch(Exception e)
    {
      System.out.println("File not found");
    }

    double chance = getChanceBeforeFlop(oddTable);

    System.out.println(owner + " " + chance);

    if (owner.ableToCheck())
      return "check";
    else
      return "call";
  }

  // ----------------------------------------------------------------

  /**
   * Makes a decision (bet, raise, fold, call, check) after the flop.
   *
   * @return The decision.
   */
  private String decideAtFlop()
  {
    return "check";
  }

  // ----------------------------------------------------------------

  /**
   * Makes a decision (bet, raise, fold, call, check) after the turn.
   *
   * @return The decision.
   */
  private String decideAtTurn()
  {
    return "check";
  }

  // ----------------------------------------------------------------

  /**
   * Makes a decision (bet, raise, fold, call, check) after the river.
   *
   * @return The decision.
   */
  private String decideAtRiver()
  {
    return "check";
  }

  // ----------------------------------------------------------------

  /** 
   * Looks up the correct chance of winning from a table, depending
   * on the players hand and the number of players.
   *
   * @param oddTable The table of the odds.
   *
   * @return The chance.
   */
  private double getChanceBeforeFlop(String[] oddTable)
  {
    char rank1 = owner.getHand()[0].toString().charAt(2);
    char rank2 = owner.getHand()[1].toString().charAt(2);
    rank1 = (rank1 == '1') ? 'T' : rank1;
    rank2 = (rank2 == '1') ? 'T' : rank2;

    if (owner.getHand()[0].compareTo
                          (owner.getHand()[1], Card.SortOrder.BY_RANK) < 0)
    {
      char temp = rank1;
      rank1 = rank2;
      rank2 = temp;
    }

    char relation = owner.getHand()[0].sameColour(owner.getHand()[1])
                    ? 's' : 'o';

    String lookingFor = "" + rank1 + rank2 + relation;

    int line = 0;

    for (int i = 0; i < oddTable.length; i++)
      if (oddTable[i].substring(0,3).equals(lookingFor))
        line = i;

    String[] odds = oddTable[line].split(" ");
    double chance = Double.parseDouble(odds[Table.noOfActivePlayers]) / 100;
  
    return chance;

  }
}