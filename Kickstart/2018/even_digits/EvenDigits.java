package even_digits;

import java.math.BigInteger;
import java.util.Scanner;

public class EvenDigits
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		int numberOfElements = Integer.parseInt(input.nextLine());
		for (int i = 0; i < numberOfElements; i++)
		{
			String numberText = input.nextLine();
			System.out.println("Case #" + (i + 1) + ": " + countButtonPresses(numberText));
			
		}
	}
	
	static BigInteger countButtonPressesAdding(String stringNumber)
	{
		BigInteger numberBigInteger = new BigInteger(stringNumber);
		BigInteger i = new BigInteger("0");
		boolean continueLoop = true;
		while (continueLoop)
		{
			continueLoop = false;
			if (numberBigInteger.toString().length() != 1)
			{
				for (int j = 1; j <= numberBigInteger.toString().length(); j++)
				{
					if (Integer.parseInt(numberBigInteger.toString().substring(j - 1, j)) % 2 != 0)
					{
						numberBigInteger = numberBigInteger.add(new BigInteger("1"));
						i = i.add(new BigInteger("1"));
						j = numberBigInteger.toString().length();
						continueLoop = true;
					}
					else continueLoop = false;
				}
			}
			else
			{
				if (Integer.parseInt(numberBigInteger.toString()) % 2 != 0)
				{
					numberBigInteger = numberBigInteger.subtract(new BigInteger("1"));
					i = i.add(new BigInteger("1"));
					continueLoop = true;
				}
			}
		}
		return i;
	}
	
	static BigInteger countButtonPressesRemoving(String stringNumber)
	{
		BigInteger numberBigInteger = new BigInteger(stringNumber);
		BigInteger i = new BigInteger("0");
		boolean continueLoop = true;
		while (continueLoop)
		{
			continueLoop = false;
			if (numberBigInteger.toString().length() != 1)
			{
				for (int j = 1; j <= numberBigInteger.toString().length(); j++)
				{
					if (Integer.parseInt(numberBigInteger.toString().substring(j - 1, j)) % 2 != 0)
					{
						numberBigInteger = numberBigInteger.subtract(new BigInteger("1"));
						i = i.add(new BigInteger("1"));
						j = numberBigInteger.toString().length();
						continueLoop = true;
					}
					else continueLoop = false;
				}
				
			}
			else
			{
				if (Integer.parseInt(numberBigInteger.toString()) % 2 != 0)
				{
					numberBigInteger = numberBigInteger.subtract(new BigInteger("1"));
					i = i.add(new BigInteger("1"));
					continueLoop = true;
				}
			}
		}
		return i;
	}
	
	static BigInteger countButtonPresses(String stringNumber)
	{
		BigInteger adding = countButtonPressesAdding(stringNumber);
		BigInteger removing = countButtonPressesRemoving(stringNumber);
		if (adding.compareTo(removing) < 0)
		{
			return adding;
		}
		else return removing;
	}
}
/*4
42
11
1
2018*/