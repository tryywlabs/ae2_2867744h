import java.util.ArrayList; //used to keep track of discs inserted in order
import java.util.Scanner; //for user input (only one Scanner object is initialised in the main method)

public class Grid {

  /* CLASS ATTRIBUTES: TOTAL ROWS, COLUMNS, 2D ARRAY REPRESENTATION OF GRID, ARRAYLIST OF DISCS */
  /* CLASS ATTRIBUTES: TOTAL ROWS, COLUMNS, 2D ARRAY REPRESENTATION OF GRID, ARRAYLIST OF DISCS */

  private int rows = 6;
  private int columns = 7;
  private Disc[][] grid = new Disc[rows][columns];
  public ArrayList<Disc> discList = new ArrayList<Disc>();

  /* CONSTRUCTOR GRID */
  /* CONSTRUCTOR GRID */

  //initialises a 6 by 7 (6 rows, 7 columns) grid of Disc objects
  //assuming that, when iterating, we will go through the rows first and then columns.
  public Grid(){
    this.grid = new Disc[rows][columns];
    //initialised array list of Discs to keep track of inserted discs in order
    this.discList = new ArrayList<Disc>();
  }

  /* METHODS: GETTERS */
  /* METHODS: GETTERS */

  public int getRows(){
    return this.rows;
  }

  public int getCols(){
    return this.columns;
  }
  
  public Disc[][] getGrid(){
    return this.grid;
  }
  
  /* METHODS: DROP DISC INTO GRID */
  /* METHODS: DROP DISC INTO GRID */

  public boolean dropDisc (Disc disc){
    //look for the lowest possible insert row starting from botton of the grid for the given column integer value
    //set the row value for the disc when dropped
    int row = rows - 1;
    int col = disc.getCol();
    while(row >= 0){
      if (grid[row][col] == null){
        grid[row][col] = disc;
        disc.setRow(row);
        //add to list of discs, organised in insertion order
        discList.add(disc);
        return true;
      }
      row--;
    }
    System.out.println("Cannot insert into full column. Please select another column.");
    return false;
  }

  /* METHODS: CONFIRM TURN FOR EACH PLAYER TURN */
  /* METHODS: CONFIRM TURN FOR EACH PLAYER TURN */

  /*EXTENSION FEATURE: CONFIRM TURN
  As part of the 5th Criteria, I've implemented the confTurn (confirm turn) method for each player turn.
  When a player makes a move, the move is denoted in the grid with the character '#' instead of 'O' or 'X'.
  It then asks the player if they are sure if they wish to make the move.

  The '#' allows the player to recognise more easily which disc they have just inserted.
  It then prompts the user to confirm if they would like to redo their move by typing 'Y', or "Yes", 'N', or "No" case-insensitively.

  The ArrayList, apart from the 'currentDisc' variable in the main method, was implemented to ensure later scalability and keep a better track of the most recent disc, where the most recent insertion would be ArrayList[n-1].
  When the response is yes, the most recent disc is deleted from the ArrayList of inserted discs and the grid. It would skip the 'takeTurn()' condition for both players, resulting in a repeated turn for the same player.
  When the response is no, the disc object's symbol changes to that of the player ('O' or 'X') and the playing player is alternated and moves on for the next player to take their turn.

  The method confTurn() is included in the grid class, since it manipulates the disc insertion and deletion of the grid object.*/

  public boolean confTurn(Disc disc, Scanner scanner){
    //boolean variable to check wheter a move was re-done. Used as terminating condition for redo process
    boolean redoComplete = false;
    //if no moves have been made (ArrayList is empty) return false
    if (discList.isEmpty()){
      return redoComplete;
    }

    //Delineate the current disc character so that player can recognise it more easily 
    //Set symbol of disc as '#', store original symbol in oriSym
    //switch back to oriSym when dropDisc is confirmed
    char oriSym = disc.getSym();
    disc.setSym('#');
    //Set the delineated '#' disc character back to its original symbol after printing
    System.out.println(this.toString());
    disc.setSym(oriSym);
    System.out.print("Check your disc's place in the board. Would you like to proceed with your move?(Y/N): ");
    
    //loop until process finishes
    while(!redoComplete){
      String input = scanner.nextLine();
      try {
        //if player wishes to redo their move (n or no):
        if ((input.toUpperCase().charAt(0) == 'N' && input.length() == 1) || input.toUpperCase() == "NO"){
          //lastDisc is the move recently added disc: the disc the player wishes to remove
          Disc lastDisc = discList.get(discList.size() - 1);
          //remove lastDisc from grid object
          grid[lastDisc.getRow()][lastDisc.getCol()] = null;
          //remove lastDisc from discList ArrayList
          discList.remove(discList.size() - 1);
          //switch value of redoComplete to terminate loop and return true
          redoComplete = true;
        }
        //If player wishes to proceed (y or yes), return false
        else if ((input.toUpperCase().charAt(0) == 'Y' && input.length() == 1) || input.toUpperCase() == "YES"){
          return redoComplete;
        }
        //Any other value, repromt user
        else {
          System.out.print("Please enter a valid input (Y or N): ");
        }
        //catch exceptions such as type mismatch
      } catch (Exception e) {
        System.out.print("Please enter a valid input (Y or N): ");
      }
    }
    return redoComplete;
  }

  /* METHODS: CHECK WINNING CONDITIONS AND FULL GRID */
  /* METHODS: CHECK WINNING CONDITIONS AND FULL GRID */

  //Check win condition: Horizontal
  public boolean winHorizontal(Disc disc){
    int counter = 0;
    char sym = disc.getSym();
    int row = disc.getRow();
    int col = disc.getCol();
    //Check for consecutive symbols for player left to the recent disc
    do{
      if(grid[row][col].getSym() == sym){
        counter++;
        if(counter >= 4){
          return true;
        }
      }
      else break;
      col--;
    }
    while(col >= 0 && grid[row][col] != null);
    //Reset indices, counter-- to avoid duplicate counting of recent disc
    counter--;
    row = disc.getRow();
    col = disc.getCol();
    //Count right side of recent disc
    do{
      if(grid[row][col].getSym() == sym){
        counter++;
        if(counter >= 4){
          return true;
        }
      }
      else break;
      col++;
    }
    while(col < columns && grid[row][col] != null);
    return false;
  }

  //Check win condition: Vertical
  public boolean winVertical(Disc disc){
    int counter = 0;
    int col = disc.getCol();
    int row = disc.getRow();
    //Iterate for current column from the current disc, iterate downwards within column
    while (row < rows){
      if (grid[row][col].getSym() == disc.getSym()){
        counter++;
        if (counter >= 4){
          return true;
        }
      }
      else break;
      row++;
    }
    return false;
  }

  //Check win condition: Diagonals
  //This method consists of 2 parts:
    //counting the right-down diagonal (\)
    //counting the right-up diagonal (/)
  public boolean winDiagonal(Disc disc){
    //Diagonal direction left up to right down (\), starting from most recently inserted disc
    int counter = 0;
    int row = disc.getRow();
    int col = disc.getCol();
    //count left up side
    //loop while symbol is the same and index is within grid
    while (row >= 0 && col >= 0  && grid[row][col] != null){
      if (grid[row][col].getSym() == disc.getSym()){
        counter++;
        if (counter >= 4){
          return true;
        }
      }
      else break;
      row--;
      col--;
    }
    //reset index, count right down. counter-- to prevent counting index twice
    row = disc.getRow();
    col = disc.getCol();
    counter--;
    while (row < rows && col < columns && grid[row][col] != null){
      if (grid[row][col].getSym() == disc.getSym()){
        counter++;
        if (counter >= 4){
          return true;
        }
      }
      else break;
      row++;
      col++;
    }

    //Diagonal direction left down to right up (/) starting from most recently inserted disc
    counter = 0;
    row = disc.getRow();
    col = disc.getCol();
    //count left down side
    while (row < rows && col >= 0 && grid[row][col] != null){
      if (grid[row][col].getSym() == disc.getSym()){
        counter++;
        if (counter >= 4){
          return true;
        }
      }
      else break;
      row++;
      col--;
    }
    //reset index, count right up
    row = disc.getRow();
    col = disc.getCol();
    counter--;
    while (row >= 0 && col < columns && grid[row][col] != null){
      if (grid[row][col].getSym() == disc.getSym()){
        counter++;
        if (counter >= 4){
          return true;
        }
      }
      else break;
      row--;
      col++;
    }
    return false;
  }

  //Check full grid
  public boolean checkFull(){
    for (int i = 0; i < 6; i++){
      for (int j = 0; j < 7; j++){
        if (grid[i][j] == null) return false;
      }
    }
    return true;
  }

  /* METHODS: TOSTRING FOR GRID */
  /* METHODS: TOSTRING FOR GRID */

  @Override
  public String toString(){
    String gridPrint = "";
    //Print column numbers at top of grid (i+1 for readibliity)
    for (int i = 0; i < columns; i++){
      gridPrint += String.format("  %d ", i + 1);
    }
    gridPrint += "\n";
    for (int i = 0; i < rows; i++){
      //concatenate | to delineate each column for the row
      gridPrint += "|";
      for (int j = 0; j < columns; j++){
        //initialise char vairable for index at grid
        char res;
        //if no disc is inserted in index, print blank space
        if (grid[i][j] == null){
          res = ' ';
        }
        else{
          //else print character of disc. When grid.toString is called for confTurn(), this will print # since that is the set symbol of the disc object.
          res = grid[i][j].getSym();
        }
        //concatenate result above to string
        gridPrint += String.format(" %s |", res);
      }
      gridPrint += "\n";
    }
    //return the overall String
    return gridPrint;
  }
}