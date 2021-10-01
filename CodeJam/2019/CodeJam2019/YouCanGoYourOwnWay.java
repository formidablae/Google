import java.util.Scanner;

public class YouCanGoYourOwnWay
{

	public static void main(String[] args)
    {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
        	input.nextLine();
        	String path = input.nextLine();
        	String newPath =  path.replace("S","SOUTH");
        	newPath = newPath.replace("E","S");
        	newPath = newPath.replace("SOUTH","E");
        	
        	System.out.println("Case #" + i + ": " + newPath);
        }
    }
}