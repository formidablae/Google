package round1b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ManhattanCrepeCart
{

	public static void main(String[] args)
    {
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
        	String[] thisLine = input.nextLine().split(" ");
        	int people = Integer.parseInt(thisLine[0]);
        	int maxxy = Integer.parseInt(thisLine[1]);
        	
        	
        	
        	ArrayList<String> result = new ArrayList<String>();
        	
        	for (int j = 0; j < people; j++)
        	{
            	int minX = -1;
            	int minY = -1;
            	int maxX = maxxy;
            	int maxY = maxxy;
            	
        		String[] thisPerson = input.nextLine().split(" ");
        		int x = Integer.parseInt(thisPerson[0]);
            	int y = Integer.parseInt(thisPerson[1]);
            	String direction = thisPerson[2];
            	
            	if (direction.equals("N"))
            	{
            		if (minY <= y) minY = y - 1;
            		if (minY == -1) minY = 0;
            		result.add("" + x + " " + (minY));
            	}
            	else if (direction.equals("S"))
            	{
            		if (maxY >= y) maxY = y - 1;
            		result.add("" + x + " " + (maxY));
            	}
            	else if (direction.equals("E"))
            	{
            		if (minX <= x) minX = x - 1;
            		if (minX == -1) minX = 0;
            		result.add("" + minX + " " + y);
            	}
            	else
            	{
            		if (maxX >= x) maxX = x - 1;
            		result.add("" + maxX + " " + y);
            	}
        	}

        	Map<String,Integer> mappa = new HashMap<String, Integer>();
            for(int k = 0; k < result.size(); k++)
            {
                Integer count = mappa.get(result.get(k));       
                mappa.put(result.get(k), count==null?1:count+1);
            }
            
            System.out.println(mappa);
        	
    		System.out.println("Case #" + i + ": ");
        }
    }
	
}