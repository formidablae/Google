import java.util.Scanner;

public class DatBae
{
	public static void main(String[] args)
    {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
        	String[] firstLineArray = input.nextLine().split(" ");
        	int N = Integer.parseInt(firstLineArray[0]);
        	int B = Integer.parseInt(firstLineArray[1]);
        	int F = Integer.parseInt(firstLineArray[2]);
        	
        	solve(N, B, F);
        }
    }

	private static String solve(int n, int b, int f)
	{
		while(f >= 0)
		{
			Scanner in = new Scanner(System.in);
			System.out.println("11111");
			
		}
		return "";
	}

}
