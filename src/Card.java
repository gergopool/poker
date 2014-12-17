

/**
 * This class represent the 52 cards on the table, the burnt cards
 * and the cards the players have.
 *
 * @author Gergely Papp
 */
public class Card
{
  // The colour of the card
  private final int colour;

  // The rank of the card
  private final int rank;


  public enum SortOrder {BY_COLOUR, BY_RANK};


  /**
   * Constructor.
   *
   * @param theColour The colour of the Card
   * @param theRank The rank of the Card
   */
  public Card(int theColour, int theRank)
  {
    colour = theColour;
    rank = theRank;
  }

  public int getColour()
  {
    return colour;
  }

  public int getRank()
  {
    return rank;
  }

  public boolean sameColour(Card other)
  {
    return colour == other.getColour();
  }

  public boolean sameRank(Card other)
  {
    return rank == other.getRank();
  }

  public int compareTo(Card other, SortOrder order)
  {
    switch (order)
    {
      case BY_RANK:   if (!this.sameRank(other))
                        return this.rank - other.getRank();
                      else
                        return this.colour - other.getColour();

      case BY_COLOUR: if (!this.sameColour(other))
                        return this.colour - other.getColour();
                      else
                        return this.rank - other.getRank();

      default: break;
    }

    return 0;
    
  }


  /**
   * Determines the players value for being a dealer.
   *
   * @return The value.
   */
  public int getValueForDealing()
  {
    return colour * 100 + rank;
  }
  

  /**
   * Card in readable format.
   */
  public String toString()
  {
    String s_card = "";

    switch (colour)
    {
      case 0 : s_card += "C"; break;
      case 1 : s_card += "D"; break;
      case 2 : s_card += "H"; break;
      case 3 : s_card += "S"; break;
      default: break;
    }

    s_card += "_";

    switch (rank)
    {
      case  0 : s_card += "2"; break;
      case  1 : s_card += "3"; break;
      case  2 : s_card += "4"; break;
      case  3 : s_card += "5"; break;
      case  4 : s_card += "6"; break;
      case  5 : s_card += "7"; break;
      case  6 : s_card += "8"; break;
      case  7 : s_card += "9"; break;
      case  8 : s_card += "10"; break;
      case  9 : s_card += "J"; break;
      case 10 : s_card += "Q"; break;
      case 11 : s_card += "K"; break;
      case 12 : s_card += "A"; break;
      default: break;
    }

    return s_card;
  }

}
