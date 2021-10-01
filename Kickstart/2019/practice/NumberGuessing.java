package practice;

import java.util.Scanner;

public class NumberGuessing
{

    static Scanner input = new Scanner(System.in);;

	public static void main(String[] args)
    {
        int testCases = Integer.parseInt(input.nextLine());
        
        while (testCases > 0)
        {
            String thisLine = input.nextLine();
            String[] aAndB = thisLine.split(" ");
            int a = Integer.parseInt(aAndB[0]);
            int b = Integer.parseInt(aAndB[1]);
            
            int maxGuesses = Integer.parseInt(input.nextLine());
            
            boolean foundOrNot = guess(a, b, maxGuesses);
            
            if (!foundOrNot) testCases = 0;
            else testCases--;
        }
    }
    
    public static boolean guess(int aInput, int bInput, int numOfGuesses)
    {
    	int positionLeft = aInput + 1;
    	int positionRight = bInput;
    	int middle;
    	
    	boolean found = false;
		
		while (!found && !(positionLeft > positionRight) && numOfGuesses != 0)
		{
			middle = (positionLeft + positionRight) / 2;
			System.out.println(middle);
			String response = input.nextLine();
			
			if (response.equals("TOO_BIG")) positionRight = middle - 1;
			else if (response.equals("TOO_SMALL")) positionLeft = middle + 1;
			else if (response.equals("CORRECT"))
			{
				found = true;
			}
			else
			{
				positionLeft = positionRight + 1;
				found = false;
			}
			
			numOfGuesses--;
		}
		
		return found;
    }

}