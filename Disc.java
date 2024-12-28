public class Disc{
  
  /* CLASS ATTRIBUTES: SYMBOL, ROW, COLUMN VALUES OF DISC OBJECT */
  /* CLASS ATTRIBUTES: SYMBOL, ROW, COLUMN VALUES OF DISC OBJECT */
  
  private char sym;
  private int row;
  private int col;

  /* CONSTRUCTOR DISC */
  /* CONSTRUCTOR DISC */

  public Disc (char sym, int col){
    this.sym = sym;
    this.col = col;
    //row value is not set in the constructor, since it is determined after object initialisation at dropDisc()
  }

  /* METHODS: GETTER AND SETTERS */
  /* METHODS: GETTER AND SETTERS */

  public char getSym(){
    return this.sym;
  }

  //setter for symbol was needed for the additional redo feature
  public void setSym(char x){
    this.sym = x;
  }

  public int getCol(){
    return this.col;
  }
  
  public int getRow(){
    return this.row;
  }

  public void setRow(int row){
    this.row = row;
  }
}
