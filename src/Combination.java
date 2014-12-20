

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
      case '4' : name = "Straight"; break;
      case '5' : name = "Flush"; break;
      case '6' : name = "Full House"; break;
      case '7' : name = "Poker"; break;
      case '8' : name = "Stright Flush"; break;
      case '9' : name = "Royal Flush"; break;

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
    value = changeIf_Straigh(value, true);
    value = changeIf_Straigh(value, false);
    value = changeIf_Flush(value, true);
    value = changeIf_Flush(value, false);
    value = changeIf_FullHouse(value);
    value = changeIf_Poker(value);
    value = changeIf_StraightFlush(value);
    value = changeIf_RoyalFlush(value);
  }

  private Card[] sortCardsDesc(Card.SortOrder sortBy, boolean aceDown)
  {
    int i, j;
    Card[] sortedArray = new Card[noOfCards];
    for (i = 0; i < noOfCards; i++)
      sortedArray[i] = allCards[i];

    if (!aceDown)
    {

      for(i = 0; i < noOfCards - 1; i++)
        for (j = i + 1; j < noOfCards; j++)
          if (sortedArray[i].compareTo(sortedArray[j], sortBy) < 0)
          {
            Card toSwap = new Card(sortedArray[i]);
            sortedArray[i] = new Card(sortedArray[j]);
            sortedArray[j] = new Card(toSwap);
          }
    }
    else
    {

      for(i = 0; i < noOfCards - 1; i++)
        for (j = i + 1; j < noOfCards; j++)
          if (sortedArray[i].compareToAceDown(sortedArray[j], sortBy) < 0)
          {
            Card toSwap = new Card(sortedArray[i]);
            sortedArray[i] = new Card(sortedArray[j]);
            sortedArray[j] = new Card(toSwap);
          }
    }
        
    return sortedArray;
  }


  private Card[] makeUniqueInRanks(Card[] cards)
  {
    int noOfUniqueCards = 1;
    for(int i = 1; i < cards.length; i++)
      if (cards[i].getRank() != cards[i - 1].getRank())
        noOfUniqueCards++;

    Card[] unique = new Card[noOfUniqueCards];
    unique[0] = cards[0];
    int counter = 1;
    for(int i = 1; i < cards.length; i++)
      if (cards[i].getRank() != cards[i - 1].getRank())
      {
        unique[counter] = cards[i];
        counter++;
      }

   return unique;
  }


  private int hexToInt(char hex)
  {
    String s_hex = "" + hex;
    int result = Integer.parseInt(s_hex, 16);
    if (result == 1)
      return 14;

    return result;
  }

  private char intToHex(int n)
  {
    if (n == 1)
      n = 14;
    return Integer.toHexString(n).toUpperCase().charAt(0);
  }


  /************************* COMBINATIONS *************************/

  /**
   * Determines the highest cards out of the given cards
   *
   * @return The high cards.
   */
  private String highCards()
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_RANK, false);
    String theValue = "0";

    int i;
    for (i = 0; i < noOfCards && i < 5; i++)
      theValue += sorted[i].getRankInHex();

    while(i < 5)
    {
      theValue += "0";
      i++;
    }


      return theValue;
  }


  // - - - - - - - - - - - - - - - PAIR - - - - - - - - - - - - - - - //
  /**
   * If there is a pair, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  private String changeIf_Pair(String valueWas)
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_RANK, false);
    String theHighCards = "";
    String pair = "";

    int i = 0;
    //Going through the cards
    while (i < noOfCards - 1)
    {
      //If pair found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i + 1].getRank()
          && pair.length() == 0)
      {
        pair += sorted[i].getRankInHex();
        i++;
      }
      else
      {
        theHighCards += sorted[i].getRankInHex();
      }
      
      i++;
    }

    //If the last one was not pair, we add it
    if (i == noOfCards - 1)
      theHighCards += sorted[i].getRankInHex();

    while(theHighCards.length() < 3)
      theHighCards += "0";

    //If pair found, we return the appropriate value
    if (!pair.equals(""))
      return "1" + pair.charAt(0) + "0" + theHighCards.substring(0,3);
      
    return valueWas;
  }




  // - - - - - - - - - - - - - - TWO PAIRS - - - - - - - - - - - - - - //
  /**
   * If there are two pairs, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  private String changeIf_TwoPairs(String valueWas)
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_RANK, false);
    String theHighCards = "";
    String pair = "";

    int i = 0;
    //Going through the cards
    while (i < noOfCards - 1)
    {
      //If pair found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i+1].getRank()
          && pair.length() <= 1)
      {
        pair += sorted[i].getRankInHex();
        i++;
      }
      else 
        theHighCards += sorted[i].getRankInHex();
      
      i++;
    }

    //If the last one was not pair, we add it
    if (i == noOfCards - 1)
      theHighCards += sorted[i].getRankInHex();

    //If pair found, we return the appropriate value
    if (pair.length() >= 2)
      {
      theHighCards = "" + theHighCards.charAt(0);
      return "2" + pair.charAt(0) + pair.charAt(1)
                                      + theHighCards + "00";
      }

      
    return valueWas;
  }




  // - - - - - - - - - - - - - - - DRILL - - - - - - - - - - - - - - - //
  /**
   * If there is a drill, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  public String changeIf_Drill(String valueWas)
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_RANK, false);
    String theHighCards = "";
    String drill = "";

    int i = 0;
    //Going through the cards
    while (i < noOfCards - 2)
    {
      //If drill found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i + 1].getRank()
          && sorted[i+1].getRank() == sorted[i + 2].getRank()
          && drill.length() == 0)
      {
        drill += sorted[i].getRankInHex();
        i += 2;
      }
      else
        theHighCards += sorted[i].getRankInHex();
      
      i++;
    }

    //If the last chars were not drill, we add it
    while(i < noOfCards)
    {
      theHighCards += sorted[i].getRankInHex();
      i++;
    }


    //If drill found, we return the appropriate value
    if (drill.length() != 0)
    {
      theHighCards = "" + theHighCards.charAt(0) + theHighCards.charAt(1);
      return "3" + drill.charAt(0) + "0" + theHighCards + "0";
    }
      
    return valueWas;
  }




  // - - - - - - - - - - - - - - - STRIGHT - - - - - - - - - - - - - - - //
  /**
   * If there is a straight, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  private String changeIf_Straigh(String valueWas, boolean aceDown)
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_RANK, aceDown);
    sorted = makeUniqueInRanks(sorted);

    int i = 0;
    while(i < sorted.length - 4)
    {
      if(sorted[i].getRank() == sorted[i + 1].getRank() + 1
         && sorted[i + 1].getRank() == sorted[i + 2].getRank() + 1
         && sorted[i + 2].getRank() == sorted[i + 3].getRank() + 1
         && sorted[i + 3].getRank() == sorted[i + 4].aceDownRank() + 1)
      {
          return "4" + sorted[i].getRankInHex() + "0000";
      }

      i++;
    }

    return valueWas;
  }




  // - - - - - - - - - - - - - - - FLUSH - - - - - - - - - - - - - - - //
  /**
   * If there is a flush, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  private String changeIf_Flush(String valueWas, boolean aceDown)
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_COLOUR, aceDown);
    String flush = "";

    int i = 0;
    while(i < noOfCards - 4)
    {
      if (sorted[i].sameColour(sorted[i + 4]) && flush.length() == 0)
        flush = "" + sorted[i].getRankInHex()
                   + sorted[i + 1].getRankInHex()
                   + sorted[i + 2].getRankInHex()
                   + sorted[i + 3].getRankInHex()
                   + sorted[i + 4].getRankInHex();
      i++;
    }

    if (flush.length() != 0)
      return "5" + flush;

    return valueWas;
  }



  // - - - - - - - - - - - - - - FULL HOUSE - - - - - - - - - - - - - - //
  /**
   * If there is a full, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  private String changeIf_FullHouse(String valueWas)
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_RANK, false);
    String theHighCards = "";
    String drill = "";
    String pair= "";

    int i = 0;

    //Going through the cards
    while (i < noOfCards - 2)
    {
      //If drill or pair found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i+1].getRank())
      {
          if (sorted[i + 1].getRank() == sorted[i + 2].getRank()
              && drill.length() == 0)
          {
            drill += sorted[i].getRankInHex();
            i += 2;
          }
          else
          {
            pair += sorted[i].getRankInHex();
            i += 1;
          }
      }
      else
      {
        theHighCards += sorted[i].getRankInHex();
      }
      
      i++;
    }


    //If the last one was not drill, we add it
    while(i < noOfCards)
    {
      theHighCards += sorted[i].getRankInHex();
      i++;
    }


    //If drill and pair found, we return the appropriate value
    if (drill.length() != 0 && pair.length() != 0)
      return "6" + drill.charAt(0) + pair.charAt(0) + "000";

    return valueWas;
  }




  // - - - - - - - - - - - - - - - POKER - - - - - - - - - - - - - - - //
  /**
   * If there is a poker, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  private String changeIf_Poker(String valueWas)
  {
    Card[] sorted = sortCardsDesc(Card.SortOrder.BY_RANK, false);
    String theHighCards = "";
    String poker = "";


    int i = 0;

    //Going through the cards
    while (i < noOfCards - 3)
    {
      //If poker found save it. Otherwise add everything to
      //theHighCards, and we will cut it later on if needed.
      if (sorted[i].getRank() == sorted[i + 1].getRank()
          && sorted[i].getRank() == sorted[i + 2].getRank()
            && sorted[i].getRank() == sorted[i + 3].getRank())
      {
        poker += sorted[i].getRankInHex();
        i += 3;
      }
      else
      {
        theHighCards += sorted[i].getRankInHex();
      }
      
      i++;
    }


    //If the last chars were not poker, we add it
    while(i < noOfCards)
    {
      theHighCards += sorted[i].getRankInHex();
      i++;
    }


    //If drill found, we return the appropriate value
    if (poker.length() != 0)
      return "7" + poker + "0" + theHighCards.charAt(0) + "00";
      

    return valueWas;
  }





  // - - - - - - - - - - - - - STRAIGHT FLUSH - - - - - - - - - - - - - //
  /**
   * If there is a straight flush, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  public String changeIf_StraightFlush(String valueWas)
  {
    int i = 0;

    // Checking if there is stright or flush with random strings
    // The results will be 2 chars long.
    String straightAceUp = changeIf_Straigh("pppppp", false);
    String flushAceUp = changeIf_Flush("qqqqqq", false);
    String straightAceDown = changeIf_Straigh("rrrrrr", true);
    String flushAceDown = changeIf_Flush("ssssss", true);

    char aceUpLast = 'g';
    if (straightAceUp.charAt(0) != 'p')
      aceUpLast = intToHex(hexToInt(straightAceUp.charAt(1)) - 4);

    char aceDownLast = 'g';
    if (straightAceDown.charAt(0) != 'r')
      aceDownLast = intToHex(hexToInt(straightAceDown.charAt(1)) - 4);


    if (straightAceUp.charAt(1) == flushAceUp.charAt(1)
        && aceUpLast == flushAceUp.charAt(5))
      return "8" + straightAceUp.charAt(1) + "0000";

    if (straightAceDown.charAt(1) == flushAceDown.charAt(1)
        && aceDownLast == flushAceDown.charAt(5))
      return "8" + straightAceDown.charAt(1) + "0000";

    return valueWas;
  }





  // - - - - - - - - - - - - - - ROYAL FLUSH - - - - - - - - - - - - - - //
  /**
   * If there is a royal flush, we change the previous value.
   *
   * @param valueWas What the current value is before calling method.
   */
  public String changeIf_RoyalFlush(String valueWas)
  {
    if (valueWas.equals("8E0000"))
      return "900000";
    return valueWas;
  }




  public String toString()
  {
    return "[" + getName() + ": " + value + "]";
  }

}