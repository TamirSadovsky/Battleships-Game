import java.util.Scanner; 
public class InputCheck 
{
    public  String inputToScan;
    private Board b;
    public boolean inputFlag; // If True = Input is submarine . If False = Input is shoot
    private String firstPart; // first part of the input string 
    private String secondPart;  // second part of the input string
    private static int biggestBat = 3; // size of the biggest battleship on the board - 1
    public boolean result; // declaration of final varriable that gives a final answer to the input if it good or not
    private static int lowBorder = 0; // decleartion of constent for low border of the board.
    private static int minAsciiCapital = 65;
    private static int maxAsciiCapital = 74;
    private static int minAscii = 97;
    private static int maxAscii = 106;
    private Scanner in;

    public InputCheck (String _inputToScan , Board _b) // Constructive function of the class
    {
        b = _b; 
        inputToScan = _inputToScan;
        if (inputToScan.length() == 5)
        {
            if (inputToScan.indexOf('-') >= 0) // checks the existence of '-' in the string 
            {
                String[] arrayTemp = inputToScan.split("-",2); // The user will put Sumbarine in this way (For Example: A2-A4)
                    
                firstPart = arrayTemp[0]; // Start Location of the Sumbarine , it takes (A2)
                secondPart = arrayTemp[1]; // End Location of the Submarine , it takes (A4)
                inputFlag = true;
            }
            
        
            else // if the there is no '-' in the string , all the variables will be null
            {    // and later the other function will sent it with false
                firstPart = null; 
                secondPart = null;
            }
        }
        
        else if (inputToScan.length() == 2)
        {
            firstPart = inputToScan.substring(0,1);
            secondPart = inputToScan.substring(1,2);
            inputFlag = false;
        }
    }
    public boolean getResult() // this function will manage all the small boolean functions 
    // that will give final solution true if the input is ok
    {
         if (inputToScan.length() == 5) // checks for input of input size 5 - BATTLESHIP
        {
            if (checkInputValid(firstPart, b) == false) // first part of input for example: (A2,A4) - checks the A2
            {
                System.out.println("Your input is illegal , Please correct the first part!"); // only first part is bad
                return result = false;
            }
            if (checkInputValid(secondPart, b) == false) // second part of input for example: (A2,A4) - checks the A4
            {
                System.out.println("Your input is illegal , Please correct the second part!"); // only second part is bad
                return result = false;
            }
            if ((checkSizeDiff(firstPart , secondPart)) == false) // checks if the differnce between the 2 points is valid for example: (A2,A4) - checks the A4-A2 = 2 is valid 
            {
                System.out.println("Your input is illegal , Please correct the battleship corrdinates!"); // only second part is bad
                return result = false;
            }
            return result = true;
        }
        else if (inputToScan.length() == 2) // checks for input of input size 2 - SHOOT
        {
           if (checkInputValid(inputToScan, b) == false) // input for example: (A2) 
           {
                System.out.println("Your shoot input is illegal , Please try again!"); 
                return false;
           }
           return result = true;
        }
        else if ((inputToScan.length() != 5) && (inputToScan.length() != 2)) // if the input does not in length 2 / 5 chars so the input is wrong
        {    
            System.out.println("Your input is illegal , Please follow the Instructions for valid Battleships"); // Length is bad
            return result = false;
        }        
        return result =false;
    }
    /**
     * this func checkInputValid checks if the input fits to the board borders.
     */
    public boolean checkInputValid (String inputPart, Board b)
    {
        if (inputPart.length() == 2)  // checks if the string has the expected length
        {
            int firstCheckInt = inputDivideFirst(inputPart);
            if ((firstCheckInt < lowBorder+1) || (firstCheckInt > b.getLengthRow()-1))   // these are limits of the ASCII numbers of the letters
            {
                return false;
            }            
            int secondCheckInt = inputDivideSecond(inputPart); 
            if ((secondCheckInt < lowBorder+1) || (secondCheckInt > b.getLengthRow()-1 )) // these are limits of the ASCII numbers of the letters
            {
                return false;
            }
            return true; 
        }
        return false; 
    }
    public int inputDivideFirst(String input) // takes the first char in the string and coverts to int
    {
        if (input.length() != 2) // checks if the shorter string has expected length
            return -1;
        char temp = input.charAt(0); // saves the first char in the string
        int ans = getFinalletterCharToInt(temp);
        //int ans = Integer.parseInt(String.valueOf(temp)); // transforms char to int       
        return ans;
    }
    public int inputDivideSecond(String input) // takes the second char in the string and converts to int
    {
        if (input.length() != 2)
            return -1;
        char temp = input.charAt(1); // saves the second char in the string
        int ans = getFinalNumberCharToInt (temp);
        //int ans = Integer.parseInt(String.valueOf(temp)); // transforms char to int
        return ans;
    }
    /**
     * after converting the letters and numbers to real values , this function will check if the differnce is good according to biggestBat parameter.
     */
    public boolean checkSizeDiff(String firstPart , String secondPart) // checks the differnce between the letters and numbers of each string . 
    {
        int letter1 = inputDivideFirst(firstPart);
        int num1 = inputDivideSecond(firstPart);
        int letter2 = inputDivideFirst(secondPart);
        int num2 = inputDivideSecond(secondPart);
        
        if (num1== num2) // checks the differnce or letters.
        {
            if (letter2 - letter1 > biggestBat)
                return result = false;
            else
                return true;
        }
        else if (letter1 == letter2) // checks the differnce or numbers.
        {
            if (num2 - num1 > biggestBat)
                return result = false;
            else 
                return true;
        }
        return false;
    }   
    // converts the ascii value of char to int.
    // this function detects between capital letters and small letters and returns the int convertion. 
    public int getFinalletterCharToInt (char c)//
    {
        int k = (int) c;
        if ((k >= minAsciiCapital) && (k <= maxAsciiCapital)) 
            return (k - minAsciiCapital+1);
        else if ((k >= minAscii) && (k <= maxAscii))
            return (k - minAscii+1);

        return -1;    
    }
    // converts the ascii value of number to int.
    public int getFinalNumberCharToInt (char c)
    {
        if((c == 'X')||(c == 'x'))
            return 10;
        else
            return ((int)c -48);
    }
    public String getFirstPart ()
    {
        return firstPart;
    }
    public String getSecondPart ()
    {
        return secondPart;
    }
    // this function shootOrBat detects if the input is a shoot or bat.
    public boolean shootOrBat()
    {
        if (inputFlag == true)
            return true; // This is Battlsehip
        
        if (inputFlag == false)
            return false; // this is shoot 
        else
            return false;
    }  
}



