package roundb;

import java.util.Arrays;
import java.util.Scanner;

public class BuildingPalindromes
{
	static int NO_OF_CHARS = 256;
	
	static boolean canFormPalindrome(String str)
	{
	    int count[] = new int[NO_OF_CHARS];
	    Arrays.fill(count, 0);
	    
	    for (int i = 0; i < str.length(); i++) count[(int) (str.charAt(i))]++;
	  
	    int odd = 0;
	    
	    for (int i = 0; i < NO_OF_CHARS; i++)  
	    {
		    if ((count[i] & 1) == 1) odd++;
		    if (odd > 1) return false;
	    }
	    
	    return true;
	}
	
	static String sliceString(String str, int fromNumb, int toNumb)
	{
		return str.substring(fromNumb - 1, toNumb);
	}
	
	public static void main(String[] args)
    {
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
        	int lengthOfString = 0;
        	int numberOfQuestions = 0;
        	String thisLine = input.nextLine();
        	String[] thisLineArray = thisLine.split(" ");
        	lengthOfString = Integer.parseInt(thisLineArray[0]);
        	numberOfQuestions = Integer.parseInt(thisLineArray[1]);
        	
            String word = input.nextLine();
            int countPositives = 0;
            
            while (numberOfQuestions > 0)
            {
            	int fromNumb = -1;
            	int toNumb = -1;
            	
            	String question = input.nextLine();
            	String[] questionArray = question.split(" ");
            	fromNumb = Integer.parseInt(questionArray[0]);
            	toNumb = Integer.parseInt(questionArray[1]);
            	
	            boolean answer = canFormPalindrome(sliceString(word, fromNumb, toNumb));
	            if (answer) countPositives++;
	            
	            numberOfQuestions--;
            }
            
            System.out.println("Case #" + i + ": " + countPositives);
        }
    }
}