

public class Combination
{
  //The value of the combination
  private String value;

  //Every card
  private Card[] allCards = new Card[7];

  //The number of cards
  private int noOfCards = 0;


  public Combination(Card[] someCards)
  {
    noOfCards = someCards.length + Table.noOfCardsOnBoard;
    int i;

    for (i = 0; i < someCards.length; i++)
      allCards[i] = new Card(someCards[i]);
    for (int j = 0; j < Table.noOfCardsOnBoard; j++)
      allCards[i + j] = new Card(Table.board[j]);

    determineCombination();
  }

  /**
   * Accessor.
   *
   * @return The value of the combination 
   */
  public String s_getValue()
  {
    return value;
  }

  /**
   * Get name of the combination
   */
  private String getName()
  {
    String name = "Unknown";
    switch(value.charAt(0))
    {
      case '0' : name = "High card"; break;
      case '1' : name = "Pair"; break;
      case '2' : name = "Two Pairs"; break;
      case '3' : name = "Drill"; break;

      default: break;
    }

    return name;
  }

  /**
   * Updating the value.
   */
  public void determineCombination()
  {
    value = highCards();
    value = changeIf_Pair(value);
    value = changeIf_TwoPairs(value);
    value = changeIf_Drill(value);
    value = changeIf_Straigh(value);
    value = changeIf_Flush(value);
    value = changeIf_FullHouse(value);
    value = changeIf_Poker(value);
    value = changeIf_StraightFlush(value);
    value = changeIf_RoyalFlush(value);
  }

  private Card[] sortCards(Card.SortOrder sortBy)
  {
    Card[] sortedArray = new Card[noOfCards];
    for (int i = 0; i < noOfCards; i++)
      sortedArray[i] = allCards[i];

    for(int i = 0; i < noOfCards - 1; i++)
      for (int j = i + 1; j < noOfCards; j++)
        if (sortedArray[i].compareTo(sortedArray[j], sortBy) < 0)
        {
          Card toSwap = new Card(sortedArray[i]);
          sortedArray[i] = new Card(sortedArray[j]);
          sortedArray[j] = new Card(toSwap);
        }
        
    return sortedArray;
  }


  /************************* COMBINATIONS *************************/

  public String highCards()
  {
    Card[] sorted = sortCards(Card.SortOrder.BY_RANK);
    String theValue = "0";

    for (int i = 0; i < noOfCards && i < 5; i++)
      theValue += sorted[i].getRankPlusOneInHex();

    return theValue;
  }





  public String changeIf_Pair(String valueWas)
  {
    Card[] sorted = sortCards(Card.SortOrder.BY_RANK);
    String theHighCards = "";
    String pair = "";

    int i = 0;
    //Going through the cards
    while (i < noOfCards - 1)
    {
      //If pair found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i+1].getRank()) {
        pair += sorted[i].getRankPlusOneInHex();
        i++;
      }
      else 
        theHighCards += sorted[i].getRankPlusOneInHex();
      
      i++;
    }

    //If the last one was not pair, we add it
    if (i == noOfCards - 1)
      theHighCards += sorted[i].getRankPlusOneInHex();

    //The length of highcards might be less than 3, eg. before flop
    int subLength = (theHighCards.length() <= 3) ? theHighCards.length() : 3;

    //If pair found, we return the appropriate value
    if (!pair.equals(""))
      return "1" + pair.charAt(0) + "0" + theHighCards.substring(0,subLength);
      
    return valueWas;
  }





  public String changeIf_TwoPairs(String valueWas)
  {
    Card[] sorted = sortCards(Card.SortOrder.BY_RANK);
    String theHighCards = "";
    String pair = "";

    int i = 0;
    //Going through the cards
    while (i < noOfCards - 1)
    {
      //If pair found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i+1].getRank()) {
        pair += sorted[i].getRankPlusOneInHex();
        i++;
      }
      else 
        theHighCards += sorted[i].getRankPlusOneInHex();
      
      i++;
    }

    //If the last one was not pair, we add it
    if (i == noOfCards - 1)
      theHighCards += sorted[i].getRankPlusOneInHex();

    //If pair found, we return the appropriate value
    if (pair.length() >= 2)
      return "2" + pair.charAt(0) + pair.charAt(1)
                                      + theHighCards.substring(0,1) + "00";
      
    return valueWas;
  }

  public String changeIf_Drill(String valueWas)
  {
    Card[] sorted = sortCards(Card.SortOrder.BY_RANK);
    String theHighCards = "";
    String drill = "";

    int i = 0;
    //Going through the cards
    while (i < noOfCards - 2)
    {
      //If drill found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i+1].getRank()
          && sorted[i+1].getRank() == sorted[i+2].getRank())
      {
        drill += sorted[i].getRankPlusOneInHex();
        i += 2;
      }
      else
        theHighCards += sorted[i].getRankPlusOneInHex();
      
      i++;
    }

    //If the last one was not drill, we add it
    if (i == noOfCards - 2)
      theHighCards += sorted[i].getRankPlusOneInHex()
                    + sorted[i+1].getRankPlusOneInHex();

    //If drill found, we return the appropriate value
    if (drill.length() != 0)
      return "3" + drill.charAt(0) + "0"
                                      + theHighCards.substring(0,2) + "0";
      
    return valueWas;
  }

  public String changeIf_Straigh(String valueWas)
  {
    return valueWas;
  }

  public String changeIf_Flush(String valueWas)
  {
    return valueWas;
  }

  public String changeIf_FullHouse(String valueWas)
  {
    return valueWas;
  }

  public String changeIf_Poker(String valueWas)
  {
    return valueWas;
  }

  public String changeIf_StraightFlush(String valueWas)
  {
    return valueWas;
  }

  public String changeIf_RoyalFlush(String valueWas)
  {
    return valueWas;
  }


  public String toString()
  {
    return "[" + getName() + ": " + value + "]";
  }

}