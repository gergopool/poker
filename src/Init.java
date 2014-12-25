import java.util.Scanner;

public class Init
{

  // The number of players who sit down in the beginning
  // It must be at least 2.
  public static int NUMBER_OF_PLAYERS = 3;

  // The number of unreal players, simulated by computer
  public static int NUMBER_OF_BOTS = 3;

  // The number of coins we give to the players at the beginning of the game
  public static int COINS_AT_BEGINNING = 1000;

  public static int BLIND_AT_BEGINNING = COINS_AT_BEGINNING / 50;

  //The maximum length of a name
  public static int MAX_LENGTH_OF_NAME = 7;

  //An input scanner
  public static Scanner INPUT_SCANNER = new Scanner(System.in);
}