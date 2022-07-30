/**
 * BattleshipsUser subclass  is extends from Battleships class.
 * This class only inclusing the functions to place all battleship of user-player. .
 * 
 */
public class BattleshipsUser extends Battleships
{
    public BattleshipsUser(Board b)
    {
        /* 
        * Constructor to BattleshipsUser.
        * 
        * @param b: place bat on this board 
        * @type boardPlayer: class Board
        */
        super(b); // call the Battleships Constructor 
    }
    public void setPlaceSubUser (int rowStart, int colStart,int rowEnd, int colEnd)
    {
        /*The function Get 2 point and set Battleship on board.
         * first point : (rowStart,colStart) 
         * second point:  (rowEnd,colEnd)
         */
        if(checkInputUserPlaceBat(rowStart,colStart,rowEnd,colEnd))
        //check if thepoints received from user is ok (if size sub is ok and positioned)
        //if isnt so the function checkInputUserPlaceSub prints a error message accordingly
        {
            if(checkPlaceBat(rowStart,colStart,rowEnd,colEnd))//The function checkPlaceSub check if place Battleship on board.
            {//can place 
                setPlaceBat(rowStart,colStart,rowEnd,colEnd);// call function that set Battleship on board
            }
            else
            {//can not place - print error message accordingly 
               System.out.println("You can not place Battleship here, try another point"); 
            }
        }
    }
    public boolean checkInputUserPlaceBat (int rowStart, int colStart,int rowEnd, int colEnd)
    {
        /*Get 2 point on arr Received from the player, and is check if it possible to place a Battleship. By functions: checkPosition,sizeFrom2Point,checkIfUseSubBySize
         *if isnt is prints an error message accordingly
         *
         * first point : (rowStart,colStart) 
         * second point:  (rowEnd,colEnd)
         * 
         */
        if(!checkPosition(rowStart,colStart,rowEnd,colEnd))//Check if the position is OK
        {
            System.out.println("You can not place Battleship Because it has illegal length position");
            return false;
        }
        int size = sizeFrom2Points(rowStart,colStart,rowEnd,colEnd);//Battleship length
        if(!(size==Constants.FIRST_BAT_SIZE || size==Constants.SECOND_BAT_SIZE ||size==Constants.THIRD_BAT_SIZE ||size==Constants.FOURTH_BAT_SIZE ))//Check if the Battleship length is OK
        {
             System.out.println("You can not place Battleship Because it has illegal length position "+size);
             return false;
        }
        if(!checkIfUseBatBySize(size))//Check if the Battleship length is not used
        {
             System.out.println(countBat[size]);
             System.out.println("You can not place Battleship length "+size+" Because you already placed it");
             return false;
        }
        return true;
    }
    public boolean checkPosition (int rowStart, int colStart,int rowEnd, int colEnd)
    {
        /*Get 2 point on arr and is check if on the same column/row, To know if the Battleshipe is positioned vertically or horizontally.
         * first point : (rowStart,colStart) 
         * second point:  (rowEnd,colEnd)
         * ex: for (1,1) and (1,4) return true;
         *     for (1,1) and (4,4) return false;
         */
        if((rowStart==rowEnd)|| (colStart==colEnd))
            return true;
        else
            return false;
    }
    public boolean checkIfUseBatBySize (int size)
    {
        /*Get size of Battleship, and return if we van place it on board.
         * ex: if alraedy placed sumarin size 4, so for input size 4, the return will false;
         */
        if(countBat[size]>0)
            return true;
         else 
            return false;
    }
}
