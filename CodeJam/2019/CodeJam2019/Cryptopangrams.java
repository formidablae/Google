import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Cryptopangrams
{
	public static void main(String[] args)
    {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        
        for (int i = 1; i <= testCases; i++)
        {
    		BigInteger zero = new BigInteger("0");
    		
    		String firstLine = input.nextLine();
        	String[] cipheredTextArray = input.nextLine().split(" ");
        	
        	ArrayList<BigInteger> allFactors = new ArrayList<BigInteger>();
        	
        	BigInteger firstProductOftwoPrimes = new BigInteger(cipheredTextArray[0]);
    		ArrayList<BigInteger> twoFactors = primeFactorsOf(firstProductOftwoPrimes);
    		
    		BigInteger secondPoductOfTwoPrimes = new BigInteger(cipheredTextArray[1]);
    		BigInteger nextFactor;
    		
    		if (secondPoductOfTwoPrimes.mod((BigInteger) twoFactors.get(0)).equals(zero))
    		{
    			allFactors.add((BigInteger) twoFactors.get(1));
    			allFactors.add((BigInteger) twoFactors.get(0));
    			nextFactor = (BigInteger) twoFactors.get(0);
    		}
    		else
    		{
    			allFactors.add((BigInteger) twoFactors.get(0));
    			allFactors.add((BigInteger) twoFactors.get(1));
    			nextFactor = (BigInteger) twoFactors.get(1);
    		}
        	
        	for (int j = 1; j < cipheredTextArray.length; j++)
        	{
        		secondPoductOfTwoPrimes = new BigInteger(cipheredTextArray[j]);
        		nextFactor = secondPoductOfTwoPrimes.divide(nextFactor);
        		allFactors.add(nextFactor);
        	}
        	
        	ArrayList<BigInteger> primes = new ArrayList<BigInteger>();
        	Set<BigInteger> set = new HashSet<BigInteger>(allFactors);
        	primes.addAll(set);
        	
        	Collections.sort(primes);
        	
        	String decipheredText = "";
        	
        	for (int j = 0; j < cipheredTextArray.length + 1; j++)
        	{
        		int indexOfLetter = primes.indexOf(allFactors.get(j));
        		decipheredText = buildStringFromKey(decipheredText, indexOfLetter);
        		
        	}
        	
        	System.out.println("Case #" + i + ": " + decipheredText);
        }
    }
	
	public static String buildStringFromKey(String stringNow, int indexOfLetter)
	{
		String decipheredText = stringNow;
		switch(indexOfLetter)
		{
		  case 0:
			  decipheredText = decipheredText + "A";
		    break;
		  case 1:
			  decipheredText = decipheredText + "B";
		    break;
		  case 2:
			  decipheredText = decipheredText + "C";
		    break;
		  case 3:
			  decipheredText = decipheredText + "D";
		    break;
		  case 4:
			  decipheredText = decipheredText + "E";
		    break;
		  case 5:
			  decipheredText = decipheredText + "F";
		    break;
		  case 6:
			  decipheredText = decipheredText + "G";
		    break;
		  case 7:
			  decipheredText = decipheredText + "H";
		    break;
		  case 8:
			  decipheredText = decipheredText + "I";
		    break;
		  case 9:
			  decipheredText = decipheredText + "J";
		    break;
		  case 10:
			  decipheredText = decipheredText + "K";
		    break;
		  case 11:
			  decipheredText = decipheredText + "L";
			  break;
		  case 12:
			  decipheredText = decipheredText + "M";
		    break;
		  case 13:
			  decipheredText = decipheredText + "N";
		    break;
		  case 14:
			  decipheredText = decipheredText + "O";
		    break;
		  case 15:
			  decipheredText = decipheredText + "P";
		    break;
		  case 16:
			  decipheredText = decipheredText + "Q";
		    break;
		  case 17:
			  decipheredText = decipheredText + "R";
		    break;
		  case 18:
			  decipheredText = decipheredText + "S";
		    break;
		  case 19:
			  decipheredText = decipheredText + "T";
		    break;
		  case 20:
			  decipheredText = decipheredText + "U";
		    break;
		  case 21:
			  decipheredText = decipheredText + "V";
			  break;
		  case 22:
			  decipheredText = decipheredText + "W";
		    break;
		  case 23:
			  decipheredText = decipheredText + "X";
		    break;
		  case 24:
			  decipheredText = decipheredText + "Y";
		    break;
		  case 25:
			  decipheredText = decipheredText + "Z";
		    break;
		  default:
		}
		return decipheredText;
	}
	
	public static ArrayList<BigInteger> primeFactorsOf(BigInteger number)
	{
		BigInteger zero = new BigInteger("0");
		BigInteger one = new BigInteger("1");
		BigInteger two = one.add(one);
		BigInteger limit = number.divide(two);

		ArrayList<BigInteger> result = new ArrayList<BigInteger>();
		for (BigInteger i = two; !(i.compareTo(limit) > 0); i = i.add(one))
		{
			if (number.mod(i).equals(zero))
			{
				result.add(i);
				result.add(number.divide(i));
				break;
			}
		}
		
		return result;
	}
}
