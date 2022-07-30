
/**
 * The class is responsible for getting a point shoot and change in accordance the board.
 * @param boardPlayer: The board for player
 * @param boardComputer: The board for computer
 * @type boardPlayer: class Board
 * @type boardComputer: class Board
 */
public class Shoot
{
    Board boardPlayer; // The board for player
    Board boardComputer;// The board for computer

    /**
     * The Constructor gets boardPlayer & boardComputer
     */
    public Shoot(Board boardPlayer,Board boardComputer)
    {
        this.boardPlayer=boardPlayer;
        this.boardComputer=boardComputer;
    }
    
    /*This function gets point to shoot, and creates shoot on Player Board,
     * the func checks if the shoot hit or miss
     * if shoot is hit :
     *  1.change the char on board the HIT char
     *  2.call func checkAndUpdateHitBat -  check if the point is part of bat that destory & update board in accordance
     *  3. return true
     * if shoot is miss :
     *  1.hange the char on board the MISS char
     *  2.return false
     *  
     * @param point: point to shoot on board.
     *               point[0] - row
     *               point[0] - col
     * @type point: int[].
     * @return : true - if is the shoot is hit 
     *           false - if is the was miss
     * @rtype: boolean
     */
    public boolean shootOnPlayerBoard(int [] point){
        int row = point[0];
        int col = point[1];
        if(boardPlayer.getSquare(row, col)== Constants.BATTLESHIP) { //shoot to bat
            boardPlayer.setSquare(row, col, Constants.HIT);
            checkAndUpdateHitBat(row, col, boardPlayer);
            return true;
        }
        else if(boardPlayer.getSquare(row, col) == Constants.MISS) 
            return true;
        else {
            if (boardPlayer.getSquare(row, col) == Constants.EMPTY)
                boardPlayer.setSquare(row, col, Constants.MISS);
            return false;
        }
    }
    public boolean shootOnCompBoard(int [] point){
        /*This function get point to shot, and do shot on Computer Board,
         * the func check is the shoot is hit or miss
         * if shoot is hit :
         *  1.change the char on board the HIT char
         *  2.call func checkAndUpdateHitBat -  check if the point is part of bat that destory & update board in accordance
         *  3. return true
         * if shoot is miss :
         *  1.hange the char on board the MISS char
         *  2.return false
         *  
         * @param point: point to shoot on board.
         *               point[0] - row
         *               point[0] - col
         * @type point: int[].
         * @return : true - if is the shoot is hit 
         *           false - if is the was miss
         * @rtype: boolean
         */
        int row = point[0];
        int col = point[1];
        if(boardComputer.getSquare(row, col)== Constants.BATTLESHIP){//shoot to bat
            boardComputer.setSquare(row, col, Constants.HIT);
            checkAndUpdateHitBat(row, col, boardComputer);
            return true;
        }
        else {//shoot to empty 
            if (boardComputer.getSquare(row, col) == Constants.EMPTY)
                boardComputer.setSquare(row, col, Constants.MISS);
            return false;
        }
    }
    public void checkAndUpdateHitBat (int row, int col, Board b){
        /*The func get point and checik if the point that is part of bat that destory.
         *If yes, is update and change board in accordance:
         * 1.call func updateCountBat on Board - is updat the count bat that destroy bat
         * 2.func printDestroyBat- change all squares of bat(from HIT char) to DESTROY char
         * 3.func printAroundBat- change all around squares of bat(from EMPTY/MISS char) to TERRITORY char
         * 
         * @param row: row of point that hit bat square.
         * @param col: row of point that hit bat square.
         * @param b: The board where the point appears.
         * @type row: int.
         * @type col: int.
         * @type b: Board.
         * 
         *//** Another explanation in the document Shoot.‪.docx‬ */
         
        int pointBat[] = findHitBat(row,col,b);
        if(pointBat!=null){
            int sizeBat=sizeFrom2Points(pointBat[0],pointBat[1],pointBat[2],pointBat[3]);
            b.updateCountBat(sizeBat);// updat the count bat on board that destroy bat
            printDestroyBat(pointBat[0],pointBat[1],pointBat[2],pointBat[3],b);
            printAroundBat(pointBat[0],pointBat[1],pointBat[2],pointBat[3],b);
        }
    }
    
    /*This func get point on board and check if point that is part of bat that destory.
     * 
     * @param row: row of point that hit bat square.
     * @param col: row of point that hit bat square.
     * @param b: The board where the point appears.
     * @type row: int.
     * @type col: int.
     * @type b: Board.
     * @return : if the point is part of bat that destory:
     *                   retuen :arr with  {int rowStart, int colStart,int rowEnd, int colEnd} - is point that bat start and point for end bat.
     *           Else -  return: null - if is not part of destory bat.
     * @rtype: int[]
     * 
     *//** more explanation about algorithms in the document Shoot.‪.docx‬ */   
    public int[] findHitBat (int row, int col, Board b){
        int pointBatVerticall[] = findHitBatVertical(row,col,row,col,b);//�?�?ונ�?
        int poinBatHorizontal[] = findHitBatHorizontal(row,col,row,col,b);//�?�?וז�?
        if(pointBatVerticall!=null && poinBatHorizontal !=null){ //if one off the arr point is null so the point not part of bat that destory
            if (pointBatVerticall[0] != row || pointBatVerticall[2] != row) //check if is the all bat is Verticall by check if the rowStart OR rowEnd are change from row parameter
                return pointBatVerticall;
            return poinBatHorizontal;
            //if the point bat size 1 - so 2 arr point iclude same points. so is not matter what return.
        }
        return null;
    }
    
    /*This recursive func get start point and on board and check if point that is part of horizontal bat that destory.
     * 
     * @param rowStart: row of start point that hit bat square.
     * @param colStart: row of start point that hit bat square.
     * @param rowEnd: row of end point that hit bat square.
     * @param colEnd: row of end point that hit bat square.
     * @param b: The board where the point appears.
     * @type rowStart: int.
     * @type colStart: int.
     * @type rowEnd: int.
     * @type colEnd: int.
     * @type b: Board 
     * @return : if the point is part of bat that destory:
     *                   retuen :arr with  {int rowStart, int colStart,int rowEnd, int colEnd} - is point that bat start and point for end bat.
     *           Else -  return: null - if is not part of destory bat.
     * @rtype: int[]
     * 
     *//** more explanation about algorithms in the document Shoot.‪.docx‬ */
    
    public int[] findHitBatHorizontal (int rowStart, int colStart,int rowEnd, int colEnd, Board b){
        if ( colStart-1 >= 0){
            if (b.getSquare(rowStart, colStart-1) == Constants.HIT)
                return findHitBatHorizontal(rowStart ,colStart-1, rowEnd ,colEnd, b);
            if (b.getSquare(rowStart, colStart-1) == Constants.BATTLESHIP)
                return null;
        }
        if ( colEnd+1 < b.getLengthCol()){
            if (b.getSquare(rowEnd, colEnd+1) == Constants.HIT)
                return findHitBatHorizontal(rowStart ,colStart, rowEnd ,colEnd+1, b);
            if (b.getSquare(rowEnd, colEnd+1) == Constants.BATTLESHIP)
                return null;
        }
        return new int [] {rowStart ,colStart, rowEnd ,colEnd};
    }
    
    /*This recursive func get start point and on board and check if point that is part of vertical bat that destory.
     * 
     * @param rowStart: row of start point that hit bat square
     * @param colStart: row of start point that hit bat square
     * @param rowEnd: row of end point that hit bat square
     * @param colEnd: row of end point that hit bat square
     * @param b: The board where the point appears
     * @type rowStart: int
     * @type colStart: int
     * @type rowEnd: int
     * @type colEnd: int
     * @type b: Board
     * @return : if the point is part of bat that destory:
     *           retuen :arr with  {int rowStart, int colStart,int rowEnd, int colEnd} - is point that bat start and point for end bat
     *           Else -  return: null - if is not part of destory bat.
     * @rtype: int[]
     * 
     *//** more explanation about algorithms in the document Shoot.‪.docx‬ */
    
    public int[] findHitBatVertical (int rowStart, int colStart,int rowEnd, int colEnd, Board b){ 
        if ( rowStart-1 >= 0){
            if (b.getSquare(rowStart-1, colStart) == Constants.HIT)
                return findHitBatVertical(rowStart-1 ,colStart, rowEnd ,colEnd, b);
            if (b.getSquare(rowStart-1, colStart) == Constants.BATTLESHIP)
                return null;
        }
        if ( rowEnd+1 < b.getLengthRow()){
            if (b.getSquare(rowEnd+1, colEnd) == Constants.HIT)
                return findHitBatVertical(rowStart ,colStart, rowEnd+1 ,colEnd, b);
            if (b.getSquare(rowEnd+1, colEnd) == Constants.BATTLESHIP)
                return null;
        }
        return new int [] {rowStart ,colStart, rowEnd ,colEnd};
    }
    
    /*Get 2 point on arr and calculate the distance between.
     * first point : (rowStart,colStart) 
     * second point:  (rowEnd,colEnd)
     * ex: for (1,1) and (1,4) return 4;
     */
    public int sizeFrom2Points (int rowStart, int colStart,int rowEnd, int colEnd){

        if(rowStart==rowEnd)
            return colEnd - colStart +1; 
        
            return rowEnd - rowStart +1;
    }
    
    /*The function Get 2 point and change the square to DESTROY char.
     * first point : (rowStart,colStart) 
     * second point:  (rowEnd,colEnd)
     * @param rowStart: row of start point that destroy bat square.
     * @param colStart: row of start point that destroy bat square.
     * @param rowEnd: row of end point that destroy bat square.
     * @param colEnd: row of end point that destroy bat square.
     * @param b: The board where the points appears.
     * @type rowStart: int.
     * @type colStart: int.
     * @type rowEnd: int.
     * @type colEnd: int.
     * @type b: Board.
     * 
     */
    
    public void printDestroyBat(int rowStart, int colStart,int rowEnd, int colEnd, Board b){

        char charPrint = Constants.DESTROY;
        if (rowStart == rowEnd){
            for (int col_index=colStart; col_index<=colEnd; col_index++)
                b.setSquare(rowStart, col_index, charPrint);
        }
        else {
            for (int row_index=rowStart; row_index<=rowEnd; row_index++)
                b.setSquare(row_index, colEnd, charPrint);
        }
    }
    /**
     * this function is similar to checkAroundBat from Battleships class.
     */
    public void printAroundBat(int rowStart, int colStart,int rowEnd, int colEnd, Board b){     
        if (rowStart == rowEnd)
        	printHorizontal(rowStart, rowEnd, colStart, colEnd, b);
        else if (colStart == colEnd)
        	printVertical(rowStart, rowEnd, colStart, colEnd, b);
    }
    
    public void printHorizontal(int rowStart , int rowEnd , int colStart , int colEnd , Board b){  
        	char charPrint = Constants.TERRITORY;
            for (int i = colStart - 1; i <= colEnd + 1; i++){
                if (rowStart + 1 <= 10)
                {
                    if (i >= 1 && i <= 10)
                    	b.setSquare(rowStart + 1,i,charPrint);
                }
                if (rowStart - 1 > 0){
                    if (i >= 1 && i <= 10)
                        b.setSquare(rowStart - 1,i,charPrint);
                }
            }
            if ((colStart - 1 > 0))
                b.setSquare(rowStart,colStart-1,charPrint);

            if (colEnd + 1 <= 10)
                b.setSquare(rowEnd,colEnd+1,charPrint);
 }

    public void printVertical(int rowStart , int rowEnd , int colStart , int colEnd , Board b){
    		char charPrint = Constants.TERRITORY;
            for (int i = rowStart - 1; i <= rowEnd + 1; i++){
                if (colEnd + 1 <= 10){
                    if (i >= 1 && i <= 10)
                        b.setSquare(i,colEnd + 1,charPrint);
                }
                if ((colEnd - 1 > 0) && (colEnd - 1 <=10)){
                    if (i >= 1 && i <= 10)
                       b.setSquare(i,colEnd - 1,charPrint);
                }
                }
            if (rowStart - 1 > 0)
            	b.setSquare(rowStart-1,colStart,charPrint);

            if (rowEnd + 1 <= 10)
                b.setSquare(rowEnd +1 ,colStart,charPrint);
    }
}
        
    

             
    

