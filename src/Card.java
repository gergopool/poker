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

  // The orer of sorting more cards
  public enum SortOrder {BY_COLOUR, BY_RANK};


  // ----------------------------------------------------------------

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

  // ----------------------------------------------------------------
  // ----------------------------------------------------------------




  /**
   * Accessor.
   *
   * @return The colour of the card in int.
   */
  public int getColour()
  {
    return colour;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The rank of the card in int.
   */
  public int getRank()
  {
    return rank;
  }

  // ----------------------------------------------------------------

  /**
   * Accessor.
   *
   * @return The rank in hex format.
   */
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

  // ----------------------------------------------------------------
  // ----------------------------------------------------------------

  /**
   * Converts the rank from AceUp to AceDown.
   * It means Ace will have the value of 1 instead of 14.
   *
   * @return The downrounded rank.
   */
  public int aceDownRank()
  {
    if (rank == 14)
      return 1;
    return rank;
  }

  // ----------------------------------------------------------------


  /**
   * Compares two Card by their colour and investigates wheter they are
   * equal or not.
   *
   * @param other The other card desired to compare.
   *
   * @return True/False depending on they have the same colour or not.
   */
  public boolean sameColour(Card other)
  {
    return colour == other.getColour();
  }

  // ----------------------------------------------------------------

  /**
   * Compares two Card by their rank and investigates wheter they are
   * equal or not.
   *
   * @param other The other card desired to compare.
   *
   * @return True/False depending on they have the same rank or not.
   */
  public boolean sameRank(Card other)
  {
    return rank == other.getRank();
  }

  // ----------------------------------------------------------------

  /**
   * Compares two Card.
   *
   * @param other The other card desired to compare.
   * @param order The order of order of comparing (rank or colour).
   *
   * @return Their relation in int.
   */
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

  // ----------------------------------------------------------------

  /**
   * Compares two Card, but considers the Ace as 1.
   *
   * @param other The other card desired to compare.
   * @param order The order of order of comparing (rank or colour).
   *
   * @return Their relation in int.
   */
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

  // ----------------------------------------------------------------

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
