package roundb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class EnergyStones
{
	public static void recursiveCount(ArrayList<int[]> secEnergyLose, ArrayList<int[]> allPossibilities, ArrayList<Integer> energies)
	{
		ArrayList<Integer> energes = new ArrayList<Integer>();
		int energy = 0;
		
		while (allPossibilities.size() > 0)
		{
			int[] thisCase = allPossibilities.get(0);
			allPossibilities.remove(0);
			
			for (int i = 0; i < 0; ) {}
		}
	}
	
    public static void permute(ArrayList<int[]> result ,int[]  numbers, int index)
    {
	    if(index >= numbers.length - 1)
	    {
	        result.add(numbers);
	        return;
	    }

	    for(int i = index; i < numbers.length; i++)
	    { 

	        int t = numbers[index];
	        numbers[index] = numbers[i];
	        numbers[i] = t;

	        permute(result, numbers.clone(), index + 1);

	        t = numbers[index];
	        numbers[index] = numbers[i];
	        numbers[i] = t;
	    }
	}
    
    public static int[] generateNumbers(int numbOfStones)
    {
    	int[] answer = new int[numbOfStones];
    	for (int i = 1; i <= numbOfStones; i++)
    	{
    		answer[i - 1] = i;
    	}
    	return answer;
    }
	
	public static void main(String[] args)
    {
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
            int numbOfStones = Integer.parseInt(input.nextLine());
            int maxEnergy = 0;
            
            ArrayList<int[]> stones = new ArrayList<int[]>();
            
            for (int j = 0; j < numbOfStones; j++)
            {
            	String[] inputArrayStr = input.nextLine().split(" ");
            	
            	int[] inputArrayInt = new int[3];
            	
            	inputArrayInt[0] = Integer.parseInt(inputArrayStr[0]);
            	inputArrayInt[1] = Integer.parseInt(inputArrayStr[1]);
            	inputArrayInt[2] = Integer.parseInt(inputArrayStr[2]);
            	
            	stones.add(inputArrayInt);
            }
            
            int[] allNumbers = generateNumbers(numbOfStones);
            System.out.println(Arrays.toString(allNumbers));
            ArrayList<int[]> allPossibilities = new ArrayList<int[]>();
            permute(allPossibilities, allNumbers, 0);
            
            for (int[] elements : allPossibilities) System.out.println(Arrays.toString(elements));
            
            //maxEnergy = recursiveCount(stones, numbOfStones);
            
            System.out.println("Case #" + i + ": " + maxEnergy);
        }
    }
}
