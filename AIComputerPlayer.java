hc /**
 * AIComputerPlayer class is object that responsible the computer game:
 * Manager of the round games and choose whice cell to shoot.
 * 
 *  @param b: The board for player
 *  @type b: class Board
 */

import java.util.*; 

public class AIComputerPlayer { 
    private Board rivalBoard; //rival Board
    private Vector<int []> vector_shootPoint;//includes all possible shoot points(create by func createShootPointBySizeBat() ).
    private int spacePointVector;// indicative the skips between points that add to vector_shootPoint(It also means looking for a size battleship)
    private int[] lastPointShoot;// save what was the last shoot point 
    private boolean middle_attack;//Flag to know if the computer is currently detecting a submarine and trying to destroy it.
    private int[] firstHitPointAttack;// save the first hit point at new attack (when middle_attack become true).
    private int row_board;// row board
    private int col_board;// col board
    
    public AIComputerPlayer (Board rivalBoard)
    {
        this.rivalBoard = rivalBoard;
        this.vector_shootPoint = new Vector<int []>();
        this.lastPointShoot = new int []{1,1};//Sets a default value. At the first moment when trying to access this variable there will be no error
        this.middle_attack=false;//Sets a default value.
        this.row_board=rivalBoard.getLengthRow();//Extract the exact value from the clipboard
        this.col_board=rivalBoard.getLengthCol();//Extract the exact value from the clipboard
        
        this.spacePointVector=3; //set that computer shoot on differnce of 3 point, It also means looking for a battleship of 3.
        createShootPointBySizeBat(spacePointVector);//This func creates the vector_shootPoint, that includes all possible shoot points
    }
    // getRandomPoint will return random point for pc to shoot.
    public int[] getRandomPoint()
    {
        /*This function get random point to shot, and also save the point at lastPointShoot
         * @return : random point
         * @rtype: int[]
         */
        int randomRow = (int)(Math.random() * (row_board-1))+1;
        int randomCol = (int)(Math.random() * (col_board-1))+1;
        lastPointShoot[0] = randomRow;
        lastPointShoot[1] = randomCol;
        return lastPointShoot;
    }
    public int[] yourRound()
    // this function called from GAME class, it gives the comuter indication that now PC's turn and choose-return point to shoot.
    { 
        if (spacePointVector==1) // in this stage there only possible battleships in size 1 so the algorithem automaticly randoms points for attack.
        {
            return  getRandomPoint();
        }
        
        char charSquareLastShoot = rivalBoard.getSquare(lastPointShoot[0], lastPointShoot[1]);
        if(charSquareLastShoot == Constants.HIT)
        {//When the previous hit damaged the submarine
            if(middle_attack==false)// When first hitting a new bat
            {
                firstHitPointAttack = lastPointShoot; // save the first hit point for later (to know where to go in case of miss)
                lastPointShoot = aroundHit(lastPointShoot);//update the point for return
            }
            else
            {
                lastPointShoot = continueAttack(firstHitPointAttack);//update the point for return
            }  
            middle_attack=true;//Updating the flag - which starts an attack on a submarine that has been detected.
            return lastPointShoot;//return the point for shoot ( is point that update) 
        }
        else if(charSquareLastShoot == Constants.DESTROY)
        {//When the previous injury is destroyed the submarine
            middle_attack=false;//Updating the flag - the offensive ended and the submarine destroyed.
            /** Creates a new vector with possible points because there was a change in the ex-destroyer submarine board./**/
            if(rivalBoard.haveMoreBatBySize(spacePointVector)==false && rivalBoard.haveMoreBatBySize(spacePointVector+1)==false)
            {
                spacePointVector--; // downs to next vector array.
                if(spacePointVector>1) // initialize the vector array with new size.
                    createShootPointBySizeBat(spacePointVector);
                else // in this stage there only possible battleships in size 1 so the algorithem automaticly randomes points for attack.
                    return  getRandomPoint();
            }
              
            lastPointShoot = getPointFromVector(vector_shootPoint); //get point from vector_shootPoint, return it &  for later on lastPointShoot.
            return lastPointShoot;
        }
        else if(charSquareLastShoot == Constants.MISS)
        {//When the previous hit did not hurt the submarine
            if(middle_attack==true)
            {//When the computer was in the middle of an attack on a submarine that was detected, and the last shooting was not a hit.
                //So it calls for action that will continue to look for the bat from the last hit point it knows.
                lastPointShoot = continueAttack(firstHitPointAttack);
                return lastPointShoot;
            }
            else
            { // when middle_attack==false.
                lastPointShoot = getPointFromVector(vector_shootPoint); //get point from vector_shootPoint, return it &  for later on lastPointShoot.
                return lastPointShoot;
            }
        }
        //for first time 
        lastPointShoot = getPointFromVector(vector_shootPoint); //get point from vector_shootPoint, return it &  for later on lastPointShoot.
        return lastPointShoot;
    }
    /**
     * aroundHit function get array in size 2 of a point (it contains the row and col of point) and returns 1 random point the possible around it. 
     */
    public int [] aroundHit(int [] point)
    {
        int row = point[0]; // takes the row from the array of the point.
        int col = point[1]; // takes the col from the array of the point.
        int[] nextPoint = new int[2]; // the array of point the will be returned by the function.
        Random rand = new Random(); // random decleration.
        int rolleta = (int)(Math.random() * 4); // first time decleration of number between 0-3.
        char charSquare; // this char will hold the particular square of check.
        boolean flag = false; // when the while is still working to find a "good possible random point" the flag will be false , when the function will find "good possible 
        // random point" the flag will get true and the loop will finish.
        int lowBorder = 0 ;
        while (flag == false)
        {
            if (rolleta == 0) // this if hold the option to go down from the point.
            {
                if (row + 1 <= rivalBoard.getLengthRow()-1) // it will go down just if the board size allows him.
                {
                    charSquare = rivalBoard.getSquare(row+1,col) ; // takes the sign in the particular square for check.
                    if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP)//áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                    { 
                        // if the board size and the square sign allows so this point will be randomised for next hit.
                        nextPoint[0] = row + 1; // for row.
                        nextPoint[1] = col; // for col.
                        flag = true; // finish the loop because find good point to random.
                        return nextPoint;
                    }
                }
            }
            else if (rolleta == 1) // this if hold the option to go up from the point.
            {
                if (row - 1 >= lowBorder + 1)  // it will go up just if the board size allows him.
                {
                    charSquare = rivalBoard.getSquare(row-1,col) ; // takes the sign in the particular square for check.
                    if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP)//áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                    {
                        // if the board size and the square sign allows so this point will be randomised for next hit.
                        nextPoint[0] = row - 1; // for row.
                        nextPoint[1] = col; // for col.
                        flag = true; // finish the loop because find good point to random.
                        return nextPoint;
                    }
                }
            }

            else if (rolleta == 2) // this if hold the option to go left from the point.
            {
                if (col - 1 >= lowBorder + 1) // it will go left just if the board size allows him.
                {
                    charSquare = rivalBoard.getSquare(row,col-1) ; // takes the sign in the particular square for check.
                    if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP)//áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                    {
                        // if the board size and the square sign allows so this point will be randomised for next hit.
                        nextPoint[0] = row; // for row.
                        nextPoint[1] = col -1; // for col.
                        flag = true; // finish the loop because find good point to random.
                        return nextPoint;
                    }
                } 
            }  

            else if (rolleta == 3) // this if hold the option to go right from the point.
            {
                if (col + 1 <= rivalBoard.getLengthRow()-1) // it will go right just if the board size allows him.
                {
                    charSquare = rivalBoard.getSquare(row,col+1) ; // takes the sign in the particular square for check.
                    if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP)//áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                    {
                        // if the board size and the square sign allows so this point will be randomised for next hit.
                        nextPoint[0] = row; // for row.
                        nextPoint[1] = col + 1; // for col.
                        flag = true; // finish the loop because find good point to random.
                        return nextPoint;
                    }
                }
            }
            rolleta = (int)(Math.random() * 4); // if the randomed point "is not good" , the function will random another point.
        }
        // if there is problem with the function it will get -1 on row and col as false indicator
        nextPoint [0] = -1;
        nextPoint [1] = -1;
        return nextPoint;
    }
    /**
     * this function starts to work after the computer already hit square from bat size bigger than two and after it hit one more correct square, this function starts.  
     * givesTwoPossiblePoints gets a point the computer hit + direction (same as bring another point that already the pc hit). This function will return another 
     * random possible point to destroy the battleship.
     */
    public int [] givesTwoPossiblePoints(int [] point, char direction)
    {
        int row = point[0]; // row of given point.
        int col = point[1]; // col of given point.
        int[] nextPoint = new int[]{0,0,0,0}; // new array that will contain the two points that will be randmomed later , in default it set to 0,0,0,0 to detect problem.
        // in the function.
        boolean PointIndicator1 = false; // vertical flag - when the while is still working to find a "good possible random point" the flag will be false
        // when the function will find "good possible random point" the flag will get true and the loop will finish.
        boolean PointIndicator2 = false; // horizontal flag - when the while is still working to find a "good possible random point" the flag will be false 
        // when the function will find "good possible random point" the flag will get true and the loop will finish.
        char charSquare;
        int i=0; // This variable will run to find a point in vertical.
        int j=0; // This variable will run to find a point in horizontal.
        int lowBorder=0; // constant.
        switch (direction)
        {
            case 'v': // if the direction is vertical we need to random between the two points if that possible in the sides.
            i=row; // initializing the pointer to run from the point that the function get.
            j=row;
            if (row + 1 > rivalBoard.getLengthRow()-1) // if it is not possible to go down in the board so the option to random point from there is not valid.
            { 
                PointIndicator1 = true; // flag initialized to true so the function can end the while from down the point.
                j = row; // indicates that second while starts from this row and go up , col stays the same.
            }

            else if (row - 1 <= lowBorder) // if it is not possible to go up in the board so the option to random point from there is not valid.
            {
                PointIndicator2 = true; // flag initialized to true so the function can end the while from up the point.
                i = row + 1; // indicates that second while starts from this row and go down , col stays the same.
            }
            while((PointIndicator1 == false)||(PointIndicator2 == false)) // the while we finish if it get at least 1 point to the array of 2 points.
            {
                if (PointIndicator1 == false) // enters this if only if there still posibility to get point from down.
                {
                    if (i + 1 <= rivalBoard.getLengthRow()-1) // checks if there no board size problem.
                    {
                        charSquare = rivalBoard.getSquare(i+1,col); // initializing the particular square for checks.
                        if (charSquare == Constants.HIT) // if this next square is already hit by pc continue to next point as possible target.
                        {
                            i = i + 1; // initializing the variable to next row.
                        }
                        else if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP) //áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                        {
                            // if the point stands in all checks so it can be added to array as possible point for random.
                            nextPoint[0] = i+1; // initializing the row.
                            nextPoint[1] = col; // initializing the col.
                            PointIndicator1 = true; // flag will be true as indicator we found point in down.
                        }
                        else
                            PointIndicator1 = true; // if this square has different sign so no option for point down, while continues.
                    }
                    else // if it out of board size return no point down.
                    {
                        PointIndicator1 = true;
                    }
                }

                if (PointIndicator2 == false) // enters this if only if there still posibility to get point from up.
                {
                    if (j - 1 >= lowBorder + 1) // checks if there no board size problem.
                    {
                        charSquare = rivalBoard.getSquare(j-1,col); // initializing the particular square for checks.
                        if (charSquare == Constants.HIT) // if this next square is already hit by pc continue to next point as possible target.
                        {
                            j = j - 1; // initializing the variable to next row.
                        }

                        else if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP) //áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                        {
                             // if the point stands in all checks so it can be added to array as possible point for random.
                            nextPoint[2] = j-1; // initializing the row.
                            nextPoint[3] = col; // initializing the col.
                            PointIndicator2 = true; // flag will be true as indicator we found point upper.
                        } 
                        else  // if this square has different sign so no option for point up, while continues.
                            PointIndicator2 = true;
                    }
                    else // if it out of board size return no point up.
                    {
                        PointIndicator2 = true;
                    }
                }
            }
            break;
            case 'h': // if the direction is horizontal we need to random between the two points if that possible in the sides.
            i=col; // initializing the pointer to run from the point that the function get.
            j=col;
            if (col + 1 > rivalBoard.getLengthCol()-1) // if it is not possible to go right in the board so the option to random point from there is not valid.
            { 
                PointIndicator1 = true; // flag initialized to true so the function can end the while from down the point.
                j = col; // indicates that second while starts from this col and go right , row stays the same.
            }
            else if (col - 1 <= lowBorder) // if it is not possible to go left in the board so the option to random point from there is not valid.
            {
                PointIndicator2 = true; // flag initialized to true so the function can end the while from up the point.
                i = col + 1; // indicates that second while starts from this col and go left ,row stays the same.
            }
            while((PointIndicator1 == false)||(PointIndicator2 == false)) // the while we finish if it get at least 1 point to the array of 2 points.
            {
                if (PointIndicator1 == false) // enters this if only if there still posibility to get point from right.
                {
                    if (i + 1 <= rivalBoard.getLengthCol()-1) // checks if there no board size problem.
                    {
                        charSquare = rivalBoard.getSquare(row,i+1); // initializing the particular square for checks.
                        if (charSquare == Constants.HIT) // if this next square is already hit by pc continue to next point as possible target.
                        {
                            i = i + 1; // initializing the variable to next col.
                        }
                        else if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP) //áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()  
                        {
                            // if the point stands in all checks so it can be added to array as possible point for random.
                            nextPoint[0] = row; // initializing the row.
                            nextPoint[1] = i+1; // initializing the col.
                            PointIndicator1 = true; // flag will be true as indicator we found point right.
                        }
                        else // if this square has different sign so no option for point right, while continues.
                            PointIndicator1 = true;
                    }
                    else // if it out of board size return no point right.
                    {
                        PointIndicator1 = true;
                    }
                }

                if (PointIndicator2 == false) // enters this if only if there still posibility to get point from left.
                {
                    if (j - 1 >= lowBorder + 1) // checks if there no board size problem.
                    {
                        charSquare = rivalBoard.getSquare(row,j-1); // initializing the particular square for checks.
                        if (charSquare == Constants.HIT) // if this next square is already hit by pc continue to next point as possible target.
                        {
                            j = j - 1; // initializing the variable to next col.
                        }

                        else if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP) //áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                        {
                            // if the point stands in all checks so it can be added to array as possible point for random.
                            nextPoint[2] = row; // initializing the row.
                            nextPoint[3] = j-1; // initializing the col.
                            PointIndicator2 = true; // flag will be true as indicator we found point left.
                        }
                        else // if this square has different sign so no option for point left, while continues.
                            PointIndicator2 = true;
                    }
                    else // if it out of board size return no point left.
                    {
                        PointIndicator2 = true;
                    }
                }
            }
            break;
        }
        return nextPoint;
    }
    /**
     * this continueAttack function will send the pc attack to the right function. If the pc shot hit randomly first , it send to aroundHit function that will random 
     * next hit if possible from 4 points. If the pc shot 2 hits in 1 battleship thats its size bigger than 2 the function will sent his next shoot to aroundSidesHit that will give 
     * him if possible 2 point to random from.
     */
    public int[] continueAttack (int [] point)
    {
        int row = point[0];
        int col = point[1];
        int min_start_col_board = 1;
        int min_start_row_board = 1;

        if (row + 1 <= this.row_board-1) // Down cell from point - Vertical bat
        {
            char charSquare = rivalBoard.getSquare(row + 1,col);
            if(charSquare == Constants.HIT)
                return aroundSidesHit(point,'v');
        }    
        if (row - 1 >= min_start_row_board)// Up cell from point - Vertical bat
        {
            char charSquare = rivalBoard.getSquare(row - 1,col);
            if(charSquare == Constants.HIT)
                return aroundSidesHit(point,'v'); 
        }
        if (col + 1 <= this.col_board-1)// Right cell from point - Horizontal bat
        {
            char charSquare = rivalBoard.getSquare(row , col + 1);
            if(charSquare == Constants.HIT)
                return aroundSidesHit(point,'h');   
        }  
        if (col - 1 >= min_start_col_board)// Left cell from point - Horizontal bat
        {
            char charSquare = rivalBoard.getSquare(row , col - 1);
            if(charSquare == Constants.HIT)
                return aroundSidesHit(point,'h');
        }
        //Unknown bat continued - Only one cell was found.
        return aroundHit(point);
    }
    /** this function was written as sub function that will connect with givesTwoPossiblePoints. 
     * this function aroundSidesHit gets a point that the pc hit and direction to pass to next function.
     * it will random between 2 points from the given array in the givesTwoPossiblePoints function and return 1 of them.
     */ 
    public int[] aroundSidesHit (int [] point, char direction)
    {
        int [] twoPossiblePoints = givesTwoPossiblePoints(point,direction);
        //check if one of  possible points isnt vaild, if yes is return the other point, if not return random, from points.
        if (twoPossiblePoints[0]==0)
        {//if  point represent '0' - is meaning that is not vaild point.
            return new int[] {twoPossiblePoints[2],twoPossiblePoints[3]};
        }
        if (twoPossiblePoints[2]==0)
        {//if  point represent '0' - is meaning that is not vaild point.
            return new int[] {twoPossiblePoints[0],twoPossiblePoints[1]};
        }
        int random = (int) (Math.random()*2);
        if (random==0)
        {
            return new int[] {twoPossiblePoints[0],twoPossiblePoints[1]};
        }
        else
        {
            return new int[] {twoPossiblePoints[2],twoPossiblePoints[3]};
        }
    }    
    public int[] getPointFromVector(Vector<int []> v)
    {
        //The function chooses randomly from vector v, returns it and removes from vector v.
        int size_vec = v.size();//Number of organs in vector
        int randomIndex = (int) (Math.random()*size_vec); //random number which is one of the vector indexes
        int[] point  = v.get(randomIndex);
        v.remove(randomIndex);
        return point;
    }
    /**
     * this function createShootPointBySizeBat get a parameter size and will decleare new array of vector that will contain all of the possible points to shoot.
     * this vector array will update according to the stage in the game. for example: in the beginning of the game the pc will look to destroy the big battleships to
     * get advantage. so in the satrt the array will initialize in possible points with 3 (or the bigger bat in the game) difference squares.
     * the algroithm works as it takes all the points on diagonal line and from it goes to right and left with 3+ or 3- from the square on the diagnol.
     * as this algorithm covers all the options to hit the biggest bat on the board.
     * then it sent to another function that can detect if the pc already destroyed the big battleships so it can pass to next vector size.
     */
    public void createShootPointBySizeBat(int size)
    {
        vector_shootPoint = new Vector<int []>(); 
        int min_start_col_board = 1;
        for (int i=1; i<this.row_board;i++)
        {
            for (int j_right=i; j_right<this.col_board; j_right = j_right+size)//îåñéó àú äð÷åãåú îéîéï ìàìëñåï äøàùé
            {
                if(j_right<this.col_board)//áåã÷ àí äð÷åãä ìà îçåõ ììåç
                {
                    char charSquare = rivalBoard.getSquare(i,j_right) ;
                    if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP)//áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                    {
                        vector_shootPoint.add(new int[] {i,j_right});//îåñéó ìîòøê ð÷åãä àôùøé ìéøé
                    }
                }
            }
            for (int j_left=i-size; j_left>=min_start_col_board; j_left = j_left-size)//îåñéó àú äð÷åãåú ùîàì ìàìëñåï äøàùé
            {   //j_left is start from i-size bat, becouse i is alrdey point was add at previous for.
                //so is the i start(row) is jump left by size.
                if(j_left>=min_start_col_board)//áåã÷ àí äð÷åãä ìà îçåõ ììåç
                {                
                    char charSquare = rivalBoard.getSquare(i,j_left) ;
                    if(charSquare == Constants.EMPTY || charSquare == Constants.BATTLESHIP)//áåã÷ àí äð÷åãä äéà ìà éøé "àáåã"-ëìåîø éåøä ø÷ àí æä úà øé÷ àå úà ùîëéì öåììú(äîçùá ìà éåãò àí æä áàîú úà öåììú()
                    {
                        vector_shootPoint.add(new int[] {i,j_left});//îåñéó ìîòøê ð÷åãä àôùøé ìéøé
                    }
                }
            }
        }
    }
}
