

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

  /**
   * Constructor (For copying)
   *
   */
  public Card(Card other)
  {
    colour = other.colour;
    rank = other.rank;
  }

  public int getColour()
  {
    return colour;
  }

  public String getRankInHex()
  {
    String s_rank = "0";
    switch(rank)
    {
      case 2  : s_rank = "2"; break;
      case 3  : s_rank = "3"; break;
      case 4  : s_rank = "4"; break;
      case 5  : s_rank = "5"; break;
      case 6  : s_rank = "6"; break;
      case 7  : s_rank = "7"; break;
      case 8  : s_rank = "8"; break;
      case 9  : s_rank = "9"; break;
      case 10 : s_rank = "A"; break;
      case 11 : s_rank = "B"; break;
      case 12 : s_rank = "C"; break;
      case 13 : s_rank = "D"; break;
      case 14 : s_rank = "E"; break;
      default : s_rank = "0"; break;
    }

    return s_rank;
  }


  public int getRank()
  {
    return rank;
  }

  public int aceDownRank()
  {
    if (rank == 14)
      return 1;
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

  public int compareToAceDown(Card other, SortOrder order)
  {
    switch (order)
    {
      case BY_RANK:   if (!this.sameRank(other))
                        return this.aceDownRank() - other.aceDownRank();
                      else
                        return this.colour - other.getColour();

      case BY_COLOUR: if (!this.sameColour(other))
                        return this.colour - other.getColour();
                      else
                        return this.aceDownRank() - other.aceDownRank();

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
      case  2 : s_card += "2"; break;
      case  3 : s_card += "3"; break;
      case  4 : s_card += "4"; break;
      case  5 : s_card += "5"; break;
      case  6 : s_card += "6"; break;
      case  7 : s_card += "7"; break;
      case  8 : s_card += "8"; break;
      case  9 : s_card += "9"; break;
      case 10 : s_card += "10"; break;
      case 11 : s_card += "J"; break;
      case 12 : s_card += "Q"; break;
      case 13 : s_card += "K"; break;
      case 14 : s_card += "A"; break;
      default: break;
    }

    return s_card;
  }

}
