 /**
 * 
 * Battleships class is superclass that  Battleships and BattleshipsRandom class being inherited from.
 * This class only inclusing the functions  & params that used for all class that inherits from her.
 * 
 * @param FIRST_BAT_SIZE: Size first bat - is final variable
 * @param SECOND_BAT_SIZE: Size second bat - is final variable
 * @param THIRD_BAT_SIZE: Size third bat - is final variable
 * @param FOURTH_BAT_SIZE: Size fourth bat - is final variable
 * @param b: place bat on this board
 * @param countBat: arr with all counter of Battleship by size
 * @type FIRST_BAT_SIZE:  int
 * @type SECOND_BAT_SIZE: int
 * @type THIRD_BAT_SIZE: int
 * @type FOURTH_BAT_SIZE: int
 * @type boardPlayer: class Board
 * @type countBat: arr <int>
 */
public abstract class Battleships  
{
    protected Board b; 
    protected int [] countBat; 
    public Battleships (Board b)
    {
        /* 
        * Constructor to Battleships.
        * creat the conter arr bat (countBat) with number of bat for every size.
        * @param b: place bat on this board 
        * @type boardPlayer: class Board
        */
        this.b=b;
        this.countBat = new int []{0,4,3,2,1,0}; // count of Battleship by size {0,FIRST_BAT_SIZE,SECOND_BAT_SIZE,THIRD_BAT_SIZE,FOURTH_BAT_SIZE,0} (the num cell = size sub)
        this.b.setCountBat(new int []{0,4,3,2,1,0});
    }
    public void setPlaceBat (int rowStart, int colStart,int rowEnd, int colEnd)
    {
        /*The function Get 2 point and set Battleship on board.
         * first point : (rowStart,colStart) 
         * second point:  (rowEnd,colEnd)
         * */
         
        if(rowStart==rowEnd)//For if the Battleship is positioned vertically 
        {
            for (int i=colStart;i<=colEnd;i++)//do all cell from start point to end point
            { 
                b.setSquare(rowStart,i,Constants.BATTLESHIP);//this function on class board. that change only one cell on board to Battleship char
            }
        }
        else if (colStart == colEnd)
        {//For if the Battleship is positioned horizontally
            for (int i=rowStart;i<=rowEnd;i++)//do all cell from start point to end point
            { 
                b.setSquare(i,colStart,Constants.BATTLESHIP); //this function on class board. that change only one cell on board to Battleship char
            }
        }
        countBat[sizeFrom2Points(rowStart,colStart,rowEnd,colEnd)]--;//update the arr that sub place.
    }
    public boolean checkIfAllBatPlace ()
    {
        /*The function check if all Battleshipes are already on board.
         * function work with arr countSub, Which contains for each size how sub; (the num cell = size sub)
         * return true if all Battleshipes are already on board
         * return false if not all Battleshipes are already on board
         */
        for(int i=0;i<countBat.length;i++)
            if(countBat[i]!=0)//if cell is not ematy - so have sub to place 
                return false;
        return true;
    }  
    public boolean checkPlaceBat (int rowStart, int colStart,int rowEnd, int colEnd) 
    {
        /*The function Get 2 point and check if place Battleship on board.
         * first point : (rowStart,colStart) 
         * second point:  (rowEnd,colEnd)
         * return true if can place
         * return false if canot place
         * This function if all squares between points not have bat
         * after this is call func checkAroundBat() - is around bat have bat.
         * 
         */
        if (rowStart == rowEnd)
        {
            for (int col_index=colStart; col_index<=colEnd; col_index++)
            {
                if (b.getSquare(rowStart,col_index) == Constants.BATTLESHIP)
                {
                    return false;
                }
            }
        }
        else // when colStart==colEnd
        {
            for (int row_index=rowStart; row_index<=rowEnd; row_index++)
            {
                if (b.getSquare(row_index,colEnd) == Constants.BATTLESHIP)
                {
                    return false;
                }
            }
        }
        if (checkAroundBat(rowStart,colStart,rowEnd,colEnd) == true)
            return true;
        else
            return false;
    } 
    public int sizeFrom2Points (int rowStart, int colStart,int rowEnd, int colEnd)
    {
        /*Get 2 point on arr and calculate the distance between.
         * first point : (rowStart,colStart) 
         * second point:  (rowEnd,colEnd)
         * ex: for (1,1) and (1,4) return 4;
         */
        if(rowStart==rowEnd)
        {
            return (colEnd - colStart +1); 
        }
        else
        {
            return ( rowEnd - rowStart +1);
        }
    }
    public char verticalOrHorizontal(int originNum, int originLetter,int destinationNum, int destinationLetter)
    {
        if (originNum == destinationNum)
            return 'h';
        else if (originLetter == destinationLetter)
            return 'v';
        else 
            return 'p';
        
    }
    
    /**
     * @param originNum - �?ספר שורה ש�? התח�?ת הצו�?�?�
     * @param originLetter - �?ות �?טור ש�? התח�?ת הצו�?�?ת
     * @param destinationNum - �?ספר שורה ש�? סוף הצו�?�?ת
     * @param destinationLetter - �?ות �?טור ש�? סוף הצו�?�?ת
     * The function checks if there is no neighbor battleship that can disturb.
     * Another explaination: Our program doesnt allow linking of 2 battlsehips so one side of battlsehip will touch other side of another battleship 
     * This function uses getSquare Board function to assume information about the cell.
     * 
     * 
     */ 
    public boolean checkAroundBat(int originNum, int originLetter,int destinationNum, int destinationLetter)
    {     
     
        if (originNum == destinationNum) // This checks if we should check points of Vertical Battleship
        {
            for (int i = originLetter - 1; i <= destinationLetter + 1; i++) // origin letter - 1 to check 1 cell up and 1 cell down around the battlsehip 
            {
                if (originNum + 1 <= 10) // This checks if there is no exceeding array boundaries
                {
                    if (i>=1 && i<=10) // This checks if there is no exceeding array boundaries
                    {
                        if (b.getSquare(originNum + 1,i) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                            return false;
                    }
                }
                if (originNum - 1 > 0) // This checks if there is no exceeding array boundaries
                {
                    if (i>=1 && i<=10) // This checks if there is no exceeding array boundaries
                    {
                        if (b.getSquare(originNum - 1,i) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                        return false;
                    }
                }
            }
            if ((originNum - 1 > 0) && (originNum <=10)) // This checks if there is no exceeding array boundaries
            {
                if (b.getSquare(originNum,originLetter-1) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                {
                         return false;
                }
            }
            if ((originNum + 1 <= 10) && ((destinationNum <= 10)) && (destinationLetter+1 <=10)) // This checks if there is no exceeding array boundaries
            {
                if (b.getSquare(destinationNum,destinationLetter+1) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                {
                    return false;
                }
            }
            
        }
        else if (originLetter == destinationLetter) // This checks if we should check points of Horizonatl Battleship
        {
            for (int i = originNum - 1; i <= destinationNum + 1; i++)
            {
                if (destinationLetter + 1 <= 10) // This checks if there is no exceeding array boundaries
                {
                        if (i>=1 && i<=10) // This checks if there is no exceeding array boundaries
                    {
                        if (b.getSquare(i,destinationLetter + 1) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                            return false;

                    }
                }
                if ((destinationLetter - 1 > 0) && (destinationLetter - 1 <=10)) // This checks if there is no exceeding array boundaries
                {
                    if (i>=1 && i<=10) // This checks if there is no exceeding array boundaries
                   {
                       if (b.getSquare(i,destinationLetter - 1) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                       {
                             return false;             
                       }
                   }
                }
                }
                if (originNum - 1 > 0) // This checks if there is no exceeding array boundaries
                {
                    if (b.getSquare(originNum-1,originLetter) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                    {
                        return false;
                    }
                }
                if (destinationNum + 1 <= 10) // This checks if there is no exceeding array boundaries
                {
                    if (b.getSquare(destinationNum +1 ,originLetter) == Constants.BATTLESHIP) // if there is battlship already in this cell the function will return false
                    {
                        return false;
                    }
                }
            }
       
        
        else
            return false;
        return true; 
    }
}



