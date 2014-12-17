

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
  public String s_getvalue()
  {
    return value;
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

    System.out.println(value);
  }

  private void sortCards(Card.SortOrder sortBy)
  {
    for(int i = 0; i < noOfCards - 1; i++)
      for (int j = i + 1; j < noOfCards; j++)
        if (allCards[i].compareTo(allCards[j], sortBy) < 0)
        {
          Card toSwap = new Card(allCards[i]);
          allCards[i] = new Card(allCards[j]);
          allCards[j] = new Card(toSwap);
        }
        
  }

  /************************* COMBINATIONS *************************/

  public String highCards()
  {
    sortCards(Card.SortOrder.BY_RANK);
    String theValue = "";
    for (int i = 0; i < noOfCards && i < 5; i++)
      theValue += allCards[i].getRankPlusOneInHex();
    return theValue;
  }

  public String changeIf_Pair(String valueWas)
  {
    return valueWas;
  }

  public String changeIf_TwoPairs(String valueWas)
  {
    return valueWas;
  }

  public String changeIf_Drill(String valueWas)
  {
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




}