
import java.util.Scanner;

/*
 * Game class is object that responsible to the game:
 * It creates computer board.
 * Responsible that player place all bat on the board.
 * Manager of the round games
 * 
 *  @param boardPlayer: The board for player
 *  @param boardComputer: THe board for computer
 *  @type boardPlayer: class Board
 *  @type boardComputer: class Board
 */

	class Game{
    Board boardPlayer ; // The board for player
    Board boardComputer ;// The board for computer
    AIComputerPlayer ai; 
    Shoot shoot; //is object that do the shoot
    
    /* 
    * Constructor to Game, creat Game object that responsible on the game.
    */
    public Game(){       
    	printWelcome();
        // Create board for player
        boardPlayer = new Board(10,10);
        // Create board for computer
        boardComputer = new Board(10,10);

        // place all battleship by random
        new BattleshipsRandom(boardComputer);
        // place user bat by By choosing it random/manually
        placeBatUser();

        // creates new target shoot for particular board
        shoot = new Shoot (boardPlayer,boardComputer);
        // responisble for the moves of the computer
        ai = new AIComputerPlayer(boardPlayer);
        //printRoundInStyle();
        doRound ();
    }

    public void placeBatUser()
    {
        // input from user wish random or manually place the bats
        Scanner in=new Scanner (System.in);
        printAskToPlaceBattleshipsMessage();
        printManualOrRandMessage();
        
        String inputStr = in.next(); 
        boolean wasProperInput = false;
        while(wasProperInput == false)
        {
            //manually
            if (inputStr.equals("m")){
                //call func that input where he want place bat
                placeManuallyBatUser();
                wasProperInput = true;
            }
            //random
            else if (inputStr.equals("r")){
                //call func that place all battleships random
                placeRandomBatUser();
                wasProperInput = true;
            }
            //Invalid input
            else{
                printInputIsIllegalMessage();
                printManualOrRandMessage();
                inputStr = in.next();
            }
        }
        //in.close();
    }
    public void placeManuallyBatUser(){
        // gets input from the user to place a bat
        BattleshipsUser batUser = new BattleshipsUser(boardPlayer);
        Scanner in=new Scanner (System.in);
        // loop until player place all bat
        while ( batUser.checkIfAllBatPlace() == false)
        {
            System.out.println();
            printTypeYourPointsMessage();
            String inputStr = in.next();
            //is get the input and check is good(by func getResult()), and later we get the points from this input
            InputCheck ic = new InputCheck(inputStr,boardPlayer);
            // getResult - is check if the input is good and the user input right point. shootOrBat - is check if is was shoot input
            if(ic.getResult()==true && ic.shootOrBat()==true)
            {
                //Extracts from the input the row point start
                int rowStart=ic.getFinalNumberCharToInt(ic.getFirstPart().charAt(1));
                //Extracts from the input the col point start
                int colStart=ic.getFinalletterCharToInt(ic.getFirstPart().charAt(0));
                //Extracts from the input the row point end
                int rowEnd=ic.getFinalNumberCharToInt(ic.getSecondPart().charAt(1));
                //Extracts from the input the col point end
                int colEnd=ic.getFinalletterCharToInt(ic.getSecondPart().charAt(0));
                //place the bat by point on board, if the point was illegal repeat
                batUser.setPlaceSubUser(rowStart,colStart,rowEnd,colEnd);
                //boardPlayer.printBoard();
                printRoundInStyle();
            }
            else
                printInputIsIllegalMessage();
        }
        printFinishedToPlaceBatsMessage();
        in.close();
    }
    public void placeRandomBatUser(){
        //The function place random bats, and input from user if he want re-random or do manually place.
        Scanner in=new Scanner (System.in);

        //place random, bat on board player
        new BattleshipsRandom(boardPlayer);
        boardPlayer.printBoard();
        
        printAreYouPleasedMessage();
        printSatisfiedFromManualOrRandMessage();

        //input answer from user
        String inputStr = in.next();
        boolean wasProperInput = false;
        
        while(wasProperInput == false){
            //good random
            if (inputStr.equals("y")){
                wasProperInput = true;
            }
            //random again
            else if (inputStr.equals("r")){
                //Resets the board player
                boardPlayer = new Board(10,10);
                //place random, bat on board player
                new BattleshipsRandom(boardPlayer);
                boardPlayer.printBoard();
                
                printAreYouPleasedMessage();
                printSatisfiedFromManualOrRandMessage();
                
                inputStr = in.next();
            }
            //manually
            else if (inputStr.equals("m")){
                //call for func that manually place bat
                placeManuallyBatUser();
                wasProperInput = true;
            }
            //Invalid input
            else{
            	printSatisfiedFromManualOrRandMessage();
                inputStr = in.next();
            }
        }
        //printSpace();
        //in.close();
    }
    
    /*  runs the rounds, detects in random who will start.
        Also checks who win and print this.
    */
    public void doRound (){
        //flags detect who will win first
        boolean winComp =false;
        boolean winUser =false;
        //computer starts
        if((int)(Math.random() * 2)==0)
        	doRoundPCStarts(winComp, winUser);
        //User starts
        else
        	doRoundUserStarts(winComp, winUser);

        printRoundInStyle();
        //checks who win and prints appropriate message
        if (winComp==true)
        	printYouLooseMessage();
        else
        	printYouWinMessage();
    }
    
    /* responsible for executing the computer shooting.
     * @return : true - if all  bat destroy by comp  
     *           false - not all  bat destroy by comp
     * @rtype: boolean
     */
    
    public boolean shotByComp(){
       //get shoot point by AIComputerPlayer
       int [] point = ai.yourRound();
       //make the shoot and checks if the shoot was hit or miss
       //if func shootOnPlayerBoard() return true - is HIT and comp get Another round. by his enter to while
       //if func shootOnPlayerBoard() return false -is MISS and not enter while
       while(shoot.shootOnPlayerBoard(point) == true){


           //check if all bat destroy by comp
           if (boardPlayer.haveMoreBat()==true)
                return true;
           //print the board after the shooting update
           printRoundInStyle();
           //get new shoot point by AIComputerPlayer
           point =  ai.yourRound();
       }
       System.out.println("Board after computer shot:");
       //prints the board after the shooting update
       printRoundInStyle();
       return false;
    }
    
    /*This func responsible for executing the user shooting.
     * @return : true - if all  bat destroyed by user  
     *           false - not all  bat destroyed by user
     * @type: boolean
     */
    public boolean shotByUser(){
       //func that gets shoot point from user
       int [] point = getUserPointMainFunc();
       //make the shoot and check if the shoot was hit or mess
       //if func shootOnCompBoard() return true -is HIT and user get Another round, by his enter to while
       //if func shootOnCompBoard() return false -is MESS and not enter while
       while(shoot.shootOnCompBoard(point) == true)
       {
           //check if all bat destroy by user
           if (boardComputer.haveMoreBat()==true)
                return true;
           //print the board after the shooting update
           printRoundInStyle();
           //func that get shoot point from user
           point = getUserPointMainFunc();
       }
       System.out.println("Board after user shot:");
       //print the board after the shooting update
       printRoundInStyle();
       return false;
    }
    // this shootOnCompBoard functions gets point to shoot on board then prints the board
        public void shootOnCompBoard(int r, int c){
        shoot.shootOnCompBoard(new int [] {r,c});
        printRoundInStyle();
    }
    /**
     * getUserPoint function gets an input from the user and knows to adapt it. if the point available for shoot it will be done.
     * else the function shotByUser will send the input again until we get valid point for shoot.
     */
    public int [] getUserPoint (String input){
        // initialize the inputCheck variable to detect if there problem from inputCheck class
        InputCheck ic = new InputCheck(input,boardPlayer);
        // this array will get back to shotByUser function to continue attack
        int [] point = new int [2];
        // true only if both bad point was already given and the input is ok according to inputCheck class checks
        if ((point[0] == 0) && (ic.getResult() == true))
        {
            // use function from inputCheck to initialize the row
            int row = ic.getFinalNumberCharToInt(ic.getSecondPart().charAt(0));
            // use function from inputCheck to initialize the col
            int col=  ic.getFinalletterCharToInt(ic.getFirstPart().charAt(0));
            
            // enter the point to the array
            point [0] = row; 
            point [1] = col;
        }
        // if the point is invalid then the array will get 0,0 as default point to get another point in next entrance to getUserPoint function
        else
        { 
          point [0] = 0;
          point [1] = 0;
        }
        return point;
    }
    // this function gets row and col of point and the particular board and returns true if the (row,col) square is empty or contains bat.
    // returns false else.
    public boolean possibleOptionsForShootOnBoard(int row , int col , Board b)
    {
        if ((b.getSquare(row,col) == Constants.EMPTY) || (b.getSquare(row,col) == Constants.BATTLESHIP))
            return true;
        else
            return false;
    }
    public int [] getUserPointMainFunc()
    {
        int [] point =  new int [2];
        boolean flag = false;
        // loop will work until it gets valid point as target
        while (flag == false)
        {
            System.out.println("Shoot on the computer board:");
            Scanner in = new Scanner (System.in); 
            String inputStr = in.next();
            point = getUserPoint(inputStr);
            // first if cares about bad input from user
            if ((point[0] == 0) || (possibleOptionsForShootOnBoard(point[0] , point[1] , boardComputer) == false))
            // second one cares that user will shoot only on valid squares
            {
            	printInputIsIllegalMessage();
                flag = false;
            }
            // finish when get valid point to shoot
            else
            {
                flag = true;
            }
            //in.close();
        }
        return point;      
    } 
    
    public void doRoundPCStarts(boolean winComp , boolean winUser){    	
    	System.out.println("The computer will start the battle!");
    	int counter = 1;
        while(winComp == false && winUser == false){ //loop until someone wins 
        	System.out.println("Round number "+counter+":");
            //call func that make shoot by comp, if was hit his get anthoer round (is  progress at func shotByComp())
            // - this func return if all bat destrored by comp
            winComp=shotByComp();
            //call func that make shoot by user, if was hit his get anthoer round (is  progress at func shotByUser())
            // - this func return if all bat destrored by user
            winUser=shotByUser();
            counter++;
        }
    }
    
    public void doRoundUserStarts(boolean winComp , boolean winUser){
    	System.out.println("The user will start the battle!");
    	int counter = 1;
        //loop until someone wins
        while(winComp == false && winUser == false)
        {
        	System.out.println("Round number "+counter+":");
            //call func that make shoot by user, if was hit his get anthoer round (is  progress at func shotByUser())
            // - this func returns true if all bat destrory by user
            winUser=shotByUser();
            //call func that make shoot by comp, if was hit his get anthoer round (is  progress at func shotByComp())
            // - this func returns true if all bat destrory by comp
            winComp=shotByComp();
            counter++;
        }
    }
    
    
    public void printRoundInStyle()
    {
        //The func prints boardPlayer and boardComputer, then prints the counter bat
        System.out.println("_______________________________");
        System.out.println();
        System.out.println();
        //boardPlayer.printKeySymbols();
        System.out.println();
        System.out.println("Your board:            Computer board:");
        System.out.println();
        
        for(int i=0 ;i<boardPlayer.getLengthRow();i++)
        {
            System.out.print(boardPlayer.getLineBoard(i));
            System.out.print("           | ");
            System.out.print(boardComputer.getLineBoardWithoutBatt(i));
            System.out.println();
        }
        String strCountBatPlayer[] = boardPlayer.getLinesCountBat();
        String strCountBatComputer[] = boardComputer.getLinesCountBat();
        for(int i=1;i<strCountBatPlayer.length-1;i++)
        {
            //gets a string with 16 chars
            System.out.print(strCountBatPlayer[i]);
            System.out.print("      | ");
            //gets a string with 16 chars
            System.out.print(strCountBatComputer[i]);
            System.out.println();
        }
        System.out.println();
    }
    
    public void printWelcome()
    {
        //This function print the name game.
                 System.out.println("");
        System.out.println("              _      _                   ");
        System.out.println("   ______ ___| |____| |____ ___  __   __.");
        System.out.println("  |  __  |_  |____  |____  |_  | \\ \\ / / ");
        System.out.println("  | |  | | | |   / /    / /  | |  \\ V /  ");
        System.out.println(" _| |  | | | |  / /    / /   | |___\\  \\  ");
        System.out.println("|___|  |_| |_| /_/    /_/    |_|_______|  ");
        System.out.println("                                         ");
        System.out.println("");
    } 
    
    public void printAreYouPleasedMessage(){
        System.out.println();
        System.out.println();
        System.out.println("Are you pleased with the battleships arrangement?");
    }
    
    public void printAskToPlaceBattleshipsMessage(){
    	System.out.println("Hey, you need to place your battleships now.");
    }
    
    public void printManualOrRandMessage(){
        System.out.println("Type 'm' - If you prefer to place them manually.");
        System.out.println("Type 'r' - If you prefer that we place a random one for you.");
    }
    
    public void printSatisfiedFromManualOrRandMessage(){
    	System.out.println("Type 'y' - If it's good, and you are ready to play!!");
        System.out.println("Type 'r' - If you are not satisfied and want a new arrangement");
        System.out.println("Type 'm' - If you prefer to place them manually.");
    }
    
    public void printInputIsIllegalMessage(){
    	System.out.println("Your input is illegal, Try again!");
    }
    
    public void printTypeYourPointsMessage(){
    	System.out.println("Type your points please.");
    	System.out.println("For example: 'a3' - 'a6' or 'B5' - 'B6'");
    }
    
    public void printFinishedToPlaceBatsMessage(){
        System.out.println("You finished to place all the battleships!");
        System.out.println("Let's start play!");
    }
    
    public void printYouWinMessage(){
    	System.out.println("YOU WIN!!!");
    }
    public void printYouLooseMessage(){
    	System.out.println("YOU LOSE - PC WIN");
    }
    
/*    public void printClearScreenMessage() {   
    	System.out.print("\033[H\033[2J");  
        System.out.flush();
       }
       */   
    
    public void printSpace(){
    	for(int i = 0 ; i < 40; i++){
    		System.out.println(" ");
    	}
    }
}
 
