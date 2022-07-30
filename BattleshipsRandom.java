/**
 * BattleshipsRandom subclass  is extends from Battleships class.
 * This class only inclusing the functions to place all battleship random.
 * Just to creat object that place all bat ob board
 */
public class BattleshipsRandom extends Battleships
{
    public BattleshipsRandom(Board b)
    {
        /* 
        * Constructor to BattleshipsRandom, Just to creat object that place all bat ob board.
        * 
        * @param b: place bat on this board 
        * @type boardPlayer: class Board
        */
       
        super(b); // call the Battleships Constructor 
        place_RANDOM_ALL_Battleships(); 
    }
    public void place_RANDOM_ALL_Battleships ()
    {
        /*/This function  place ALL Battleship by random points for Defined All size  (By function placeRandomBattleshipe-that place only one sub )
         * the function, work with arr countSub, Which contains for each size how sub.
         */
        
        for(int j = countBat.length-1; j>-1; j--)//sort all arr
        {
            while(countBat[j]>0)//if cell is empty - so not need sub with this size; (the num cell = size sub)
            {
                //function placeRandomBattleshipe-that place only one sub by size
                //the while run until was succseed place bat
                
                while(placeRandomBattleship(j)== false){}
            }
        }
    }
    public boolean placeRandomBattleship (int size) 
    {
        /*  This function place Battleship by random points for required size (They returned from function getRandomPointBoard)
            :param size: bat size to place
            :type size:int
            :return : true - if was succseed place bat 
                      false -if not
            :rtype : boolean
         */
        int points[] =  getRandomPointBoard (size);
        //if getRandomPointBoard return null - is say that not fount good point
        while(points==null)
           points = getRandomPointBoard (size); 
           
        int randomRowStart = points[0];
        int randomColStart = points[1];
        int randomRowEnd = points[2];
        int randomColEnd = points[3];    
        if(checkPlaceBat(randomRowStart,randomColStart,randomRowEnd,randomColEnd))//check if can place bat between 2 point
        {
            //System.out.println("TRUE");
            setPlaceBat(randomRowStart,randomColStart,randomRowEnd,randomColEnd);   
            return true;
        }
        else
            return false;
    }
    public int [] getRandomPointBoard (int size)
    {
        /* The func raffel 2 point that bat can place by size 
        :param size: bat size to place
        :type size:int
        :return :  2 point, represent by arr {randomRowStart,randomColStart,randomRowEnd,randomColEnd}
                   if not found legal point that return null
        :rtype : int []
        ____________________
        Math.random() - This method returns a pseudorandom double greater than or equal to 0.0 and less than 1.0.
        so  (int)(Math.random() * X) is int greater betwenn 0-(X-1). From ex: if x=4, so can random btween 0-3. if we dont start from 0' we add 1 after the prosses (like on code)
        we need  First num be 1 - that is the start place on board. and the End num - is the lenght arr.
        getLengthRow/Col()  - This method returns a the lenght Col/Row +1  of the arr ORGINAL so we Subtract 1.
        */
        int randomRowStart = (int)(Math.random() * (b.getLengthRow()-1))+1;
        int randomColStart = (int)(Math.random() * (b.getLengthCol()-1))+1;
        
        
        int randomRowEnd;
        int randomColEnd;
        if((int)(Math.random() * 2)==0)//random from 2 num
        //For if the Battleshipe is positioned vertically or horizontally
        {
            //positioned vertically
            randomRowEnd=randomRowStart; // Becuse is positioned vertically the Row on point same.
            randomColEnd=randomColStart+size-1; // the End point of col is calculation of the first col and size;
            if(randomColEnd>10)//if randomColEnd+size biger then 10 is Deviation of array that return null-  The numbers must be re-rando
                return null;
        }
        else
        {
            //positioned horizontally
            randomColEnd=randomColStart;// Becuse is horizontallyoned vertically the Col on point same.
            randomRowEnd=randomRowStart+size-1;// the End point of row is calculation of the first row and size
            if(randomRowEnd>10)//if randomRowStart+size biger then 10 is Deviation of array that return null-  The numbers must be re-random

                return null;
        }
        return new int[] {randomRowStart,randomColStart,randomRowEnd,randomColEnd}; //return the info point intro arr 
    }
}