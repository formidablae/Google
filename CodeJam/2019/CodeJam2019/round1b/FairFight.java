package round1b;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class FairFight
{

	public static void main(String[] args)
    {
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
        	String[] thisLine = input.nextLine().split(" ");
        	int Ntypes = Integer.parseInt(thisLine[0]);
        	int Kdistance = Integer.parseInt(thisLine[1]);
        	int countTopCharles = 1;
        	int countTopDelia = 1;
        	int topSwordCharles = 0;
        	int topSwordDelia = 0;
        	int fairPlays = 0;
        	
        	String[] skillsOfCharles = input.nextLine().split(" ");
        	ArrayList<Integer> skillsOfCharlesInt = new ArrayList<Integer>();
        	
        	String[] skillsOfDelia = input.nextLine().split(" ");
        	ArrayList<Integer> skillsOfDeliaInt = new ArrayList<Integer>();
        	
        	for (int j = 0; j < Ntypes; j++)
        	{
        		skillsOfCharlesInt.add(Integer.parseInt(skillsOfCharles[j]));
        		skillsOfDeliaInt.add(Integer.parseInt(skillsOfDelia[j]));
        	}
        	
        	Comparator c = Collections.reverseOrder();
        	Collections.sort(skillsOfCharlesInt,c);
        	Collections.sort(skillsOfDeliaInt,c);
        	
        	int l = 0;
        	
        	while(skillsOfCharlesInt.size() != 0 & skillsOfDeliaInt.size() != 0 & l < skillsOfCharlesInt.size() * skillsOfDeliaInt.size())
        	{
    			topSwordCharles = skillsOfCharlesInt.get(0);
            	topSwordDelia = skillsOfDeliaInt.get(0);
            	
    			int distance = topSwordCharles - topSwordDelia;
            	if (distance < 0) distance = distance * (-1);
            	
        		if (distance <= Kdistance)
        		{
        			fairPlays++;
        			
        			skillsOfCharlesInt.remove(0);
        			skillsOfDeliaInt.remove(0);
        			
        			for (int k = 0; k < skillsOfCharlesInt.size(); k++)
        			{
        				if (topSwordCharles == skillsOfCharlesInt.get(k))
        				{
        					fairPlays++;
        					skillsOfCharlesInt.remove(k);
        				}
        				break;
        			}
        			
        			for (int k = 0; k < skillsOfDeliaInt.size(); k++)
        			{
        				if (topSwordDelia == skillsOfDeliaInt.get(k))
        				{
        					fairPlays++;
        					skillsOfDeliaInt.remove(k);
        				}
        				else break;
        			}
        		}
        		
        		System.out.println("fairPlays = " + fairPlays);
        		
        		l++;
        	}
        	
    		System.out.println("Case #" + i + ": " + fairPlays);
        }
    }
	
}