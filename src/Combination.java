/****************************************************************
| This file includes those methods and functions that only      |
| static, they have no really connectinos to the board. Here    |
| you find the refreshments of the cards, besides all of the    |
| combinations and their codes are here.                        |
| Briefly the codes:                                            |
|                                                               |
|              CODE | COMBINATIONS        |  CARDS FIGURES      |
|                0: | undefined           |  undefined          |
|                1: | high card           |  2                  |
|                2: | pair                |  3                  |
|                3: | two pairs           |  4                  |
|                4: | drill               |  5                  |
|                5: | straight            |  6                  |
|                6: | flush               |  7                  |
|                7: | full house          |  8                  |
|                8: | poker               |  9                  |
|                9: | stright flush       |  10                 |
|                A: | royal flush         |  J                  |
|                B: |                     |  Q                  |
|                C: |                     |  K                  |
|                D: |                     |  A                  |
|                                                               |
| Code:                                                         |
| 1. digit: Type of combination                                 |
| 2. digit: First figure of the combination                     |
| 3. digit: Second figure of the combination                    |
| 4. digit: First high card                                     |
| 5. digit: Second high card                                    |
| 6. digit: Third high card                                     |
|                                                               |
| In case of combination is high card, we need all digits       |
| to represent the cards.                                       |
****************************************************************/

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
      allCards[i] = someCards[i];
    for (int j = 0; j < Table.noOfCardsOnBoard; j++)
      allCards[i] = Table.board[i + j];

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
    /*value = highCards();

    value = changeIf_Pair(value);
    value = changeIf_TwoPairs(value);
    value = changeIf_Drill(value);
    value = changeIf_Straigh(value);
    value = changeIf_Flush(value);
    value = changeIf_FullHouse(value);
    value = changeIf_Poker(value);
    value = changeIf_StraightFlush(value);
    value = changeIf_RoyalFlush(value);*/
  }


}