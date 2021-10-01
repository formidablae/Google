package roundb;

import java.util.Arrays;
import java.util.Scanner;

public class DiverseSubarray
{
	public static int countUnique(String[] array)
	{
	    if (array.length == 0) {
	        return 0;
	    }
	    
	    int count = 1;
	    for (int i = 0; i < array.length - 1; i++)
	    {
	        if (!array[i].equals(array[i + 1])) count++;
	    }
	    
	    return count;
	}
	
	public static void main(String[] args)
    {
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
            String[] nAndS = input.nextLine().split(" ");
            int n = Integer.parseInt(nAndS[0]);
            int s = Integer.parseInt(nAndS[1]);
            
            String[] trinkets = input.nextLine().split(" ");
            
            String[] trinketsClone = trinkets.clone();
            Arrays.sort(trinketsClone);
            int count = countUnique(trinketsClone);
            
            //System.out.println("Case #" + i + ": " + count);
        }
    }
}
