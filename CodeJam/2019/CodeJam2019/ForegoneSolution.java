import java.util.Scanner;

public class ForegoneSolution
{

	public static void main(String[] args)
    {
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
        	String thisLine = input.nextLine();
    		String foursToThrees = thisLine.replace("4","3");
    		String foursToOnes = thisLine.replace("4","F");
    		foursToOnes = foursToOnes.replaceAll("[0-9]", "0");
    		foursToOnes = foursToOnes.replace("F","1");
    		System.out.println("Case #" + i + ": " + foursToOnes + " " + foursToThrees);
        }
    }
}