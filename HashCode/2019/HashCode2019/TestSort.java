import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class TestSort
{
	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) throws IOException
	{
		//String outputfileReadAimgs = "/path/to/Downloads/a_all_images.txt";
		//String outputfileReadAtags = "/path/to/Downloads/a_all_tags.txt";
		//String outputfileReadBimgs = "/path/to/Downloads/b_all_images.txt";
		//String outputfileReadBtags = "/path/to/Downloads/b_all_tags.txt";
		//String outputfileReadCimgs = "/path/to/Downloads/c_all_images.txt";
		//String outputfileReadCtags = "/path/to/Downloads/c_all_tags.txt";
		//String outputfileReadDimgs = "/path/to/Downloads/d_all_images.txt";
		//String outputfileReadDtags = "/path/to/Downloads/d_all_tags.txt";
		String outputfileReadEimgs = "/path/to/Downloads/testimg.txt";
		String outputfileReadEtags = "/path/to/Downloads/testtags.txt";
		String[] outputFiles = {
				//outputfileReadAimgs,
				//outputfileReadAtags,
				//outputfileReadBimgs,
				//outputfileReadBtags,
				//outputfileReadCimgs,
				//outputfileReadCtags,
				//outputfileReadDimgs,
				//outputfileReadDtags,
				outputfileReadEimgs,
				outputfileReadEtags
				};
		
		//File fileReadA = new File("/path/to/Downloads/a_example.txt");
		// fileReadB = new File("/path/to/Downloads/b_lovely_landscapes.txt");
		//File fileReadC = new File("/path/to/Downloads/c_memorable_moments.txt");
		//File fileReadD = new File("/path/to/Downloads/d_pet_pictures.txt");
		File fileReadE = new File("/path/to/Downloads/e_shiny_selfies.txt");
		File[] filesToRead = {
				//fileReadA,
				//fileReadB,
				//fileReadC,
				//fileReadD,
				fileReadE
				};
		
		for (int z = 0; z < filesToRead.length; z++)
		{
			Scanner input = new Scanner(filesToRead[z]);
			int numberOfPhotosInt = Integer.parseInt(input.nextLine());
			
			ArrayList<String> lines = new ArrayList<String>();
			
			for (int i = 0; i < numberOfPhotosInt; i++)
			{
				String line = input.nextLine();
				lines.add(line);
				
				// System.out.println("testCases = " + (z + 1) + " / " + filesToRead.length + ". Row = " + (i + 1) + " / " + numberOfPhotosInt);
				System.out.println("Row = " + (i + 1) + " / " + numberOfPhotosInt);
			}
			
			// printToFile(printInputData(listOfDataOfAllImages), outputFiles[z * 2]);

			//printToFile(printIdsOrientAndTags(listOfDataOfAllImages), outputFiles[z * 2]);
			
			sortTagsByPresencesDecesc(lines);
			
			printToFile(lines.toString(), outputFiles[z * 2 + 1]);
			
			// printToFile(computeOutput(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, z, numberOfPhotosInt, 0), outputFiles[z]);
		}
	}
	
	public static void sortTagsByPresencesDecesc(ArrayList<String> thelist)
	{
		boolean swapped = true;
		while (swapped)
		{
			swapped = false;
			int dimension = thelist.size();
		    for (int i = 1; i < dimension; i++) {
		        if (thelist.get(i - 1).length() < thelist.get(i).length())
		        {
		            String swapTemp = thelist.get(i);
		            thelist.set(i, thelist.get(i - 1));
		            thelist.set(i - 1, swapTemp);
		            swapped = true;
		            System.out.println("Sorting tags ... " + i + " / " + dimension);
		        }
		        
		    }
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String printTagsAndIds(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		StringBuilder result = new StringBuilder();
		int i = 0;
		int sizeTags = thelistOfAllUniqueTagsAndIds.size();
		for (Object[] everyObject : thelistOfAllUniqueTagsAndIds)
		{
			result.append(everyObject[0]);
			result.append(" ");
			for (int id : ((ArrayList<Integer>) everyObject[1]))
			{
				result.append(id);
				result.append(" ");
			}
			result.append("\n");
			System.out.println("calculating tags output ... " + i + " / " + sizeTags);
			i++;
		}
		
		return result.toString();
	}
	
	public static void printToFile(String textToOuputFile, String fileName) throws IOException
	{
	    String str = textToOuputFile;
	    
	    Path path = Paths.get(fileName);
	    byte[] strToBytes = str.getBytes();
	 
	    Files.write(path, strToBytes);
	}


}
