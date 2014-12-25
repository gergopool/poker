import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class represent the 52 cards on the table
 *
 * @author Gergely Papp
 */
public class Deck
{
  //The deck itself
  private List<Card> cards;

  //The card from the top of the deck
  private int topID = 51;

  // ----------------------------------------------------------------

  /**
   * Constructor. It creates all the 52 cards in the deck.
   */
  public Deck()
  {
    cards = new ArrayList<Card>();

    for(int colour = 0; colour < 4; colour++)
      for (int rank = 2; rank <= 14; rank++)
        cards.add(new Card(colour, rank));
  }

  // ----------------------------------------------------------------
  // ----------------------------------------------------------------



  /**
   * Randomizing the cards in the deck.
   */
  public void shuffle()
  {
    long currentNanoTime = System.nanoTime();
    Collections.shuffle(cards);
  }

  // ----------------------------------------------------------------

  /**
   * Accessor for the top card of the deck.
   *
   * @return The top card of the deck.
   */
  public Card getTop()
  {
    return cards.get(topID--);
  }

}