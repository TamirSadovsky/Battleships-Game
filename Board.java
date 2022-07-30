
public class Board
{
    private static int LengthRow; // constent for rows.   
    private static int LengthCol; // constent for cols.
    private char[][] boardGame;
    private int [] countBat; // count of Battleship by size {0,FIRST_BAT_SIZE,SECOND_BAT_SIZE,THIRD_BAT_SIZE,FOURTH_BAT_SIZE} (the num cell = size sub)
    
 /* * Constructor to BattleShips board
    * @param _row represents the row in the constructing function
    * @param _col represents the column in the constructing function
    * The board represented by numbers for rows and letters for column (X = 10 , cant   represent 10 as char)
    */
    //becuse to the board adds ABC.. line and 123.. line, so Length is pluse 1
    
    public Board(int _row, int _col){
        this.LengthRow=_row+1;
        this.LengthCol=_col+1;
        this.boardGame = new char[LengthRow][LengthCol];
        //LengthRow=boardGame.length;
        //LengthCol=boardGame[0].length;
        createBoard(); // calling to createBoard function
    }
    
    //*function get the arr countBat with the count bat, is call from Constructor Battleships superclass ( BattleshipsUser OR BattleshipsRandom)
    public void setCountBat(int [] countBat){ 
        this.countBat=countBat;
    }
    public void updateCountBat(int index)
    {
        /*function work with arr countSub, Which contains for each size how sub; (the num cell = size sub).
         * get indwx and subtract 1 from count.
         */
        this.countBat[index]--;
    }
    public boolean haveMoreBatBySize (int size)
    {
        //the func gets bat size and check if have more bat from this size  on board.
        if(countBat[size]>0)
            return true;
        else
            return false;
    }
    public boolean haveMoreBat ()
    {
        /*The function check if all Battleshipes are already destroy on board.
         * function work with arr countSub, Which contains for each size how sub; (the num cell = size sub)
         * return true if all Battleshipes destroy 
         * return false if not all Battleshipes are not 
         */
        for(int i=0;i<countBat.length;i++)
            if(countBat[i]!=0)
                return false;
        return true;
    }
    public void printCountBat ()
    {
        /*The function prints the count battleships with info for user*/
        for(int i=1;i<countBat.length;i++)
        {
            System.out.println("Battleships size "+i+": "+countBat[i]+" left");
        }
    }
    public String[] getLinesCountBat ()
    {
        /*The function print the count bat with info for user*/
        String str [] = new String [countBat.length];
        for(int i=1;i<countBat.length-1;i++)
            str[i]= "Bat size"+i+":"+countBat[i]+" left";
        return str;
    }
    public static void printKeySymbols()
    {
        //fun that print all key symbols
        System.out.println("Key symbols:");
        System.out.println(Constants.HIT + " - hit on Battleships");
        System.out.println(Constants.DESTROY + " - destroy Battleships");
        System.out.println(Constants.MISS + " - miss shoot");
        System.out.println(Constants.BATTLESHIP + " - your Battleships");
        System.out.println(Constants.EMPTY + " - empty square");
        System.out.println(Constants.TERRITORY + " - territory square");
    }
    public void createBoard()
    {
        char[] letters = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'}; // An array that contains letters cols of the board
        char[] numbers = {' ', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'}; // An array that contains number rows of the board
        for (int _row = 0 ; _row < boardGame.length; _row++) // ריצה על המערך
            for (int _col = 0 ; _col < boardGame[0].length; _col++)
            {
                if  (_row == 0) // 
                    boardGame[_row][_col] = letters[_col];
                else if  (_col == 0) // start of col - letter
                    boardGame[_row][_col] =  numbers[_row];
                else if ((_row == 0) && (_col == 0)) // space between rows and cols.
                    boardGame[_row][_col] = letters[0];
                else // default mark to square
                    boardGame[_row][_col] = Constants.EMPTY; 
            }
    }
    public void printBoard () //print the all board
    {
        for(int LengthRow=0; LengthRow<boardGame.length;LengthRow++) // printing the array
        { 
            System.out.println();   
            for(int LengthCol=0; LengthCol<boardGame[0].length;LengthCol++)
            {
                System.out.print(boardGame[LengthRow][LengthCol]);
            }
        }
    }
    public void printBoardWithoutBatt() // print the all board without BATTLESHIP
    {
        for(LengthRow=0; LengthRow<boardGame.length;LengthRow++) // printing the array
        { 
            System.out.println();   
            for(LengthCol=0; LengthCol<boardGame[0].length;LengthCol++)
            {
                if (boardGame[LengthRow][LengthCol] ==Constants.BATTLESHIP)
                    System.out.print(Constants.EMPTY);
                else
                    System.out.print(boardGame[LengthRow][LengthCol]);
            }
        }
    }
    public String getLineBoard(int index_Row) // get board line by index and return string of this line for print
    {
        String str ="";
        for(int index_Col=0; index_Col<boardGame[index_Row].length;index_Col++)
        {
               str=str+boardGame[index_Row][index_Col];
        }
        return str;
    }
    public String getLineBoardWithoutBatt(int index_Row) // get board line by index and return string of this line for print, and instead BATTLESHIP char is replaces square hidden 
    {
        String str ="";
        for(int index_Col=0; index_Col<boardGame[index_Row].length;index_Col++)
        {
            if (boardGame[index_Row][index_Col] ==Constants.BATTLESHIP)
                str=str+Constants.EMPTY;
            else
                str=str+boardGame[index_Row][index_Col];
        }
        return str;
    }
    public char getSquare (int r,int c) // return the char on square by row & Col
    {
        return boardGame[r][c];
    }
    public void setSquare (int r,int c, char ch) // return the char on square by row & Col
    {
         boardGame[r][c]= ch;
    }
    public int getLengthRow () // return the lenght row arr borad
    {
        return LengthRow;
    }
    public int getLengthCol ()// return the lenght col arr borad
    {
        return LengthCol;
    }     
}
