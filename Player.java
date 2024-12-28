import java.util.Scanner; //for user input (only one Scanner object is initialised in the main method)

public class Player{

/* CLASS ATTRIBUTES: PLAYER NAME, 'O' OR 'X', GRID OBJECT, TURN VERIFICATION */
/* CLASS ATTRIBUTES: PLAYER NAME, 'O' OR 'X', GRID OBJECT, TURN VERIFICATION */

private String name; //player name
private char sym; //player disc character
private Grid grid; //reference to current grid object
private boolean isTurn; //player turn
private static boolean nextTurn = true; //next player object turn

/* CONSTUCTOR: PLAYER */
/* CONSTUCTOR: PLAYER */

  public Player(String name, char sym, Grid grid){
    this.name = name;
    this.sym = sym;
    this.grid = grid;
    this.isTurn = nextTurn; //player 1's turn is true
    nextTurn = !nextTurn; //player 2's turn is false
    //static attribute "nextTurn" gets updated so that an alternative boolean value is assigned
    //for the every next player object
  }

  /* METHODS: GETTERS, ENDTURN, TAKETURN */
  /* METHODS: GETTERS, ENDTURN, TAKETURN */

  public char getSym(){
    return this.sym;
  }
  
  public String getName(){
    return this.name;
  }

  public Grid getGrid(){
    return this.grid;
  }

  //Call at beginning of turn, before calling takeTurn()
  //ie. if(player1.getTurn) {"drop disc for player 1", "confrim disc insertion"}
  public boolean getTurn(){
    return this.isTurn;
  }

  //Ends player turn by updating the boolean value for isTurn for player.
  //Must be called for both players every time one player finishes turn to alternate both player object values.
  //Additional feature: if player wishes to redo their move, do not call (implemented with conditional statement in main method)
  public void endTurn(){
    this.isTurn = !this.isTurn;
  }

  //Takes turns and generates Disc obejct
  public Disc takeTurn(Scanner scanner){
    int column;
    while(true){
      try {
        System.out.print("Enter column number from 1 to 7: ");
        int input = scanner.nextInt();
        
        if(input >= 1 && input <= 7){
          // -1 from input to match grid indices
          column = input - 1;
          break;
        }
        else{
          //repromt user if index is out of bounds
          System.out.println("Please enter a valid integer (1 - 7).");
        }
      } catch (Exception e) {
        //repromt user if input was another data type
        System.out.println(e + "An error has occurred. Please enter a valid Integer (1 - 7).");
      }
      //clear scanner buffer
      scanner.nextLine();
    }
    //generate new disc object with player object symbol and inserted column value.
    return new Disc(this.getSym(), column);
  }
}