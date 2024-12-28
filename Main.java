import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    //initialise grid object as playing board
    Grid grid = new Grid();
    //initialise scanner object
    Scanner scanner = new Scanner(System.in);

    //Get Player 1 Information from user input
    //Get Player 1 name
    System.out.print("Enter P1 Name: ");
    String p1Name = scanner.nextLine();
    while(p1Name == ""){
      System.out.print("Enter at least 1 character: ");
      p1Name = scanner.nextLine();
    }
    //Get Player 1 Symbol, validate if the input.length == 1 and either x or o
    char p1Symbol;
    while(true){
      try {
        System.out.print("Enter P1 Symbol (X or O): ");
        String input = scanner.nextLine().toUpperCase();
        if(input.length() == 1 && (input.charAt(0) == 'X' || input.length() == 1 && input.charAt(0) == 'O')){
          p1Symbol = input.charAt(0);
          break;
        }
        else{
          System.out.print("Please enter a valid input (X or O)");
        }
      } catch (Exception e) {
        System.out.println("An error has occurred. Please enter a valid symbol.");
        scanner.nextLine();
      }
    }
    //Initialise Player1 object using user input above and pass in grid object
    Player p1 = new Player(p1Name, p1Symbol, grid);
    
    //Get Player 2 Information from User
    //Get Player 2 name
    //Repromt if user enters same name
    System.out.print("Enter P2 Name: ");
    String p2Name = scanner.nextLine();
    while (p2Name.equals(p1Name) || p2Name == ""){
      if (p2Name.equals(p1Name)){
        System.out.print("Please enter a different name: ");
        p2Name = scanner.nextLine();
      }
      else if(p2Name == ""){
        System.out.print("Enter at least 1 character: ");
        p2Name = scanner.nextLine();
      }
    }
    //Assign opposite symbol to Player 1 automatically (if p1Symbol is o, assign x and vice versa)
    char p2Symbol;
    if(p1Symbol == 'X'){
      p2Symbol = 'O';
    }
    else p2Symbol = 'X';
    //Initialise Player 2 object
    Player p2 = new Player(p2Name, p2Symbol, grid);

    /* MAIN LOOP FOR GAME */
    /* MAIN LOOP FOR GAME */

    while(!grid.checkFull()){
      System.out.println(grid.toString());
      //validate turn for player. currentPlayer is the main player for this turn (a single loop)
      Player currentPlayer;
      if (p1.getTurn() == true) currentPlayer = p1;
      else currentPlayer = p2;

      System.out.println(currentPlayer.getName() + "'s Turn");
      
      //call takeTurn and initialise disc object
      //end loop if disc has been inserted into the grid
      Disc currentDisc;
      boolean validMove = false;
      do {
        Scanner col = new Scanner(System.in);
        currentDisc = currentPlayer.takeTurn(col);
        
        if(grid.dropDisc(currentDisc)){
          validMove = true;
        }
      }
      while(!validMove);
      

      //Check for winning conditions
      if (grid.winHorizontal(currentDisc) || grid.winVertical(currentDisc) || grid.winDiagonal(currentDisc)){
        System.out.println(grid.toString());
        System.out.println(String.format(" %s Wins This Game.", currentPlayer.getName()));
        break;
      }
      //Check whether grid is full
      //Loop automatically ends
      grid.checkFull();
      if (grid.checkFull() == true){
        System.out.println(grid.toString());
        System.out.println("The Game is a Tie. GG.");
      }
      
      /* IMPLEMENTATION OF ADDITIONAL FEATURE */
      /* IMPLEMENTATION OF ADDITIONAL FEATURE */
      
      //Check if player wants to redo their move
      boolean undid = grid.confTurn(currentDisc, scanner);
      
      //if confTurn wasn't true (player said "yes" when asked if they wanted to undo their turn), switch turns between player1 and player2, to progress.
      //if confTurn was true (player decides to redo their turn) do not switch player turns
      if (!undid){
        p1.endTurn();;
        p2.endTurn();
      }
    }
    scanner.close();
  }
}
