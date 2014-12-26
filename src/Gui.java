import java.awt.Container;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Gui extends JPanel
{

  public Gui()
  {
  }

  public void addComponentsToPane(Container pane)
  {
    pane.setLayout(null);

    // Buttons
    JButton callButton = new JButton("Call");
    JButton checkButton = new JButton("Check");
    JButton raiseButton = new JButton("Raise");
    JButton betButton = new JButton("Bet");
    JButton foldButton = new JButton("Fold");

    pane.add(callButton);
    pane.add(checkButton);
    pane.add(raiseButton);
    pane.add(betButton);
    pane.add(foldButton);


    Insets insets = pane.getInsets();


    Dimension size = callButton.getPreferredSize();
    callButton.setBounds(150 + insets.left, 540 + insets.top,
                                                      size.width, size.height);
    size = checkButton.getPreferredSize();
    checkButton.setBounds(250 + insets.left, 540 + insets.top,
                                                      size.width, size.height);
    size = raiseButton.getPreferredSize();
    raiseButton.setBounds(350 + insets.left, 540 + insets.top,
                                                      size.width, size.height);
    size = betButton.getPreferredSize();
    betButton.setBounds(450 + insets.left, 540 + insets.top,
                                                      size.width, size.height);
    size = foldButton.getPreferredSize();
    foldButton.setBounds(550 + insets.left, 540 + insets.top,
                                                      size.width, size.height);

    }
 
    //--------------------------------------------------------------

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
      //Create and set up the window.
      JFrame frame = new JFrame("Poker");
      frame.setContentPane(new JLabel(new ImageIcon("images/background.jpg")));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Set up the content pane.
      addComponentsToPane(frame.getContentPane());

      //Size and display the window.
      //Size : 760x600
      Insets insets = frame.getInsets();
      frame.setSize(760 + insets.left + insets.right,
                    600 + insets.top + insets.bottom);

      frame.setVisible(true);
    }



   
}