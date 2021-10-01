package practice;

import java.util.Scanner;

public class NumberGuessingV2
{

	public static void main(String[] args)
    {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        while (testCases > 0)
        {
            String thisLine = input.nextLine();
            String[] aAndB = thisLine.split(" ");
            int a = Integer.parseInt(aAndB[0]);
            int b = Integer.parseInt(aAndB[1]);
            
            int maxGuesses = Integer.parseInt(input.nextLine());
            
            int positionLeft = a + 1;
        	int positionRight = b;
        	int middle;
        	
        	boolean found = false;
    		
    		while (!found && !(positionLeft > positionRight) && maxGuesses != 0)
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
    			
    			maxGuesses--;
    		}
            
            if (!found) testCases = 0;
            else testCases--;
        }
    }

}