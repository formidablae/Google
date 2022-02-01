import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution
{
	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) throws IOException
	{/*
		String outputFileNameA = "/path/to/Downloads/outputA.txt";
		String outputFileNameB = "/path/to/Downloads/outputB.txt";
		String outputFileNameC = "/path/to/Downloads/outputC.txt";
		String outputFileNameD = "/path/to/Downloads/outputD.txt";
		String outputFileNameE = "/path/to/Downloads/outputE.txt";
		String[] outputFiles = {outputFileNameA, outputFileNameB, outputFileNameC, outputFileNameD, outputFileNameE};
		
		File fileReadA = new File("/path/to/Downloads/a_example.txt");
		File fileReadB = new File("/path/to/Downloads/b_lovely_landscapes.txt");
		File fileReadC = new File("/path/to/Downloads/c_memorable_moments.txt");
		File fileReadD = new File("/path/to/Downloads/d_pet_pictures.txt");
		File fileReadE = new File("/path/to/Downloads/e_shiny_selfies.txt");
		File[] filesToRead = {fileReadA, fileReadB, fileReadC, fileReadD, fileReadE};
		
		for (int z = 0; z < 3; z = z == 0 ? z + 2 : z + 1)
		{
			Scanner input = new Scanner(filesToRead[z]);
			int numberOfPhotosInt = Integer.parseInt(input.nextLine());
			
			ArrayList<Object[]> listOfDataOfAllImages = new ArrayList<Object[]>();
			ArrayList<Object[]> listOfAllUniqueTagsAndIds = new ArrayList<Object[]>();
			
			
			for (int i = 0; i < numberOfPhotosInt; i++)
			{
				Object[] listOfDataPerImage = new Object[4];
				String[] lineTextArray = input.nextLine().split(" ");
				ArrayList<String> listOfTags = new ArrayList<String>();
				
				for (int j = 0; j < Integer.parseInt(lineTextArray[1]); j++)
				{
					listOfTags.add(lineTextArray[(j + 2)]);
					int positionIfExistent = dicotomicResearchTags(listOfAllUniqueTagsAndIds, lineTextArray[(j + 2)]);
					
					if (positionIfExistent == -1)
					{
						Object[] tagAndIds = new Object[2];
						tagAndIds[0] = lineTextArray[(j + 2)];
						tagAndIds[1] = new ArrayList<Integer>();
						((ArrayList<Integer>) tagAndIds[1]).add(i);
						listOfAllUniqueTagsAndIds.add(tagAndIds);
					}
					else
					{
						((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(positionIfExistent))[1]).add(i);
					}
					
					mergesortList(listOfAllUniqueTagsAndIds);
				}
				
				listOfDataPerImage[0] = i;									// id
				listOfDataPerImage[1] = lineTextArray[0];					// orientation
				listOfDataPerImage[2] = Integer.parseInt(lineTextArray[1]);	// numberOfTags
				listOfDataPerImage[3] = listOfTags;
				
				listOfDataOfAllImages.add(listOfDataPerImage);
				
				System.out.println("z = " + z + " of 0-4. i = " + i + " / " + numberOfPhotosInt);
			}
			
			// System.out.println(printInputData(listOfDataOfAllImages));
			/*System.out.println("calcolating result");
			String result = printInputData(listOfDataOfAllImages);
			System.out.println("result calcolated. outputting");
			printToFile(result, outputFiles[z]);				// output to file*/
			//sortTagsByPresencesDecesc(listOfAllUniqueTagsAndIds);
			//printToFile(printTagsAndIds(listOfAllUniqueTagsAndIds), outputFiles[z]);
			/*
			printToFile(printIdsOrientAndTags(listOfDataOfAllImages), outputFiles[z]);
			
			ArrayList<Integer> idsAlreadyInSlideshow = new ArrayList<Integer>();
			String result = "";
			
			boolean hasMoreSlides = true;
			while (hasMoreSlides)
			{
				hasMoreSlides = false;
				
				int thisSlidesId = ((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(0))[1]).get(0);
				int indexThisId = dicotomicResearchIds(listOfDataOfAllImages, thisSlidesId);
				String orientat = (String) ((Object[]) listOfDataOfAllImages.get(indexThisId))[1];
				
				if (orientat.equals("H"))
				{
					result = result + thisSlidesId;
					idsAlreadyInSlideshow.add(thisSlidesId);
					((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(0))[1]).remove(0);
					
					if (((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(0))[1]).size() == 0)
					{
						listOfAllUniqueTagsAndIds.remove(0);
					}
				}
				
				
				for (int i = 0; i < listOfDataOfAllImages.size(); i++)
				{
					for (int j = 0; j < listOfAllUniqueTagsAndIds.size(); j++)
					{
						for (int k = 0; k < ((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(j))[1]).size(); k++)
						{
							int thisId = ((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(j))[1]).get(k);
							int nextId = -1;
							String thisTagFromTags = (String) ((Object[]) listOfAllUniqueTagsAndIds.get(j))[0];
							
							if (!idsAlreadyInSlideshow.contains(thisId))
							{
								result = result + thisId;
								idsAlreadyInSlideshow.add(thisId);
								
								int indexThisId = dicotomicResearchIds(listOfDataOfAllImages, thisId);
								String nextTagFromId = "";
								
								boolean equalTags = true;
								int dimensionBefore = ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(indexThisId))[3]).size();
								for (int l = 0; l < dimensionBefore && equalTags; l++)
								{
									equalTags = false;
									nextTagFromId = ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(indexThisId))[3]).get(0);
									((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(indexThisId))[3]).remove(0);
									
									if (nextTagFromId.equals(thisTagFromTags))
									{
										equalTags = true;
									}
								}
								
								if (!nextTagFromId.equals(""))
								{
									// next tag from tags output;
								}
								else
								{
									int indexNextTag = dicotomicResearchTags(listOfAllUniqueTagsAndIds, nextTagFromId);
									int thisIsTHeNextId  = ((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(indexNextTag))[1]).get(0);
								}
								
								nextId = -1; // casual Id
							}
							else
							{
								continue;
							}
							
							
						}
					}
				}
			}
		}
	*/
	}
	
	public static String getFirstTagOfId(ArrayList<Object[]> listOfDataOfAllImages, int id)
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		@SuppressWarnings("unchecked")
		String tag = ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(index))[3]).get(0);
		return tag;
	}
	
	public static String getOrientationOfId(ArrayList<Object[]> listOfDataOfAllImages, int id)
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		String orient = (String) ((Object[]) listOfDataOfAllImages.get(index))[1];
		return orient;
	}
	
	public static int getFirstIdOfTag(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		int index = dicotomicResearchTags(thelistOfAllUniqueTagsAndIds, tag);
		@SuppressWarnings("unchecked")
		int id = ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).get(0);
		return id;
	}
	
	public static int nextIdFromThisId(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, int id)
	{
		String thisTag = getFirstTagOfId(listOfDataOfAllImages, id);
		int nextId = getFirstIdOfTag(thelistOfAllUniqueTagsAndIds, thisTag);
		return nextId;
	}
	
	public static int nextIdFromThisTag(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		int id = getFirstIdOfTag(thelistOfAllUniqueTagsAndIds, tag);
		int nextId = nextIdFromThisId(listOfDataOfAllImages, thelistOfAllUniqueTagsAndIds, id);
		return nextId;
	}
	
	public static boolean idInSlideshow(ArrayList<Object[]> listOfDataOfAllImages, int id)
	{
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void sortTagsByPresencesDecesc(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		boolean swapped = true;
		while (swapped)
		{
			swapped = false;
		    for (int i = 1; i < thelistOfAllUniqueTagsAndIds.size(); i++) {
		        if (((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i - 1))[1]).size() < ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).size())
		        {
		            Object[] swapTemp = (Object[]) thelistOfAllUniqueTagsAndIds.get(i);
		            thelistOfAllUniqueTagsAndIds.set(i, (Object[]) thelistOfAllUniqueTagsAndIds.get(i - 1));
		            thelistOfAllUniqueTagsAndIds.set(i - 1, swapTemp);
		            swapped = true;
		        }
		    }
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String printTagsAndIds(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		String result = "";
		
		for (Object[] everyObject : thelistOfAllUniqueTagsAndIds)
		{
			result = result + everyObject[0] + " : ";
			for (int id : (ArrayList<Integer>) everyObject[1])
			{
				result = result + id + " ";
			}
			result = result + "\n";
		}
		
		return result;
	}
	
	public static void mergesortList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		if (thelistOfAllUniqueTagsAndIds.size() > 1)
		{
			int half = thelistOfAllUniqueTagsAndIds.size() / 2;
			ArrayList<Object[]> firstHalf = new ArrayList<Object[]>();
			ArrayList<Object[]> secondHalf = new ArrayList<Object[]>();;
			
			for (int i = 0; i < thelistOfAllUniqueTagsAndIds.size(); i++)
			{
				if (i < half)
				{
					firstHalf.add(thelistOfAllUniqueTagsAndIds.get(i));
				} else
				{
					secondHalf.add(thelistOfAllUniqueTagsAndIds.get(i));
				}
			}
			
			mergesortList(firstHalf);
			mergesortList(secondHalf);
			
			int positionFirstHalf = 0;
			int positionSecondHalf = 0;
			
			for (int i = 0; i < thelistOfAllUniqueTagsAndIds.size(); i++)
			{
				if (positionSecondHalf == secondHalf.size() ||
					positionFirstHalf < firstHalf.size() &&
					((String) ((Object[]) firstHalf.get(positionFirstHalf))[0]).compareTo((String) ((Object[]) secondHalf.get(positionSecondHalf))[0]) < 0)
				{
					thelistOfAllUniqueTagsAndIds.set(i, (Object[]) firstHalf.get(positionFirstHalf));
					positionFirstHalf++;
				} else
				{
					thelistOfAllUniqueTagsAndIds.set(i, (Object[]) secondHalf.get(positionSecondHalf));
					positionSecondHalf++;
				}
			}
		} // else it's already sorted.
	}

	public static int dicotomicResearchIds(ArrayList<Object[]> listOfDataOfAllImages, int idToSearch)
	{
		int positionLeft = 0;
		int positionRight = listOfDataOfAllImages.size() - 1;
		int half;
		
		while (positionLeft < positionRight)
		{
			half = (positionLeft + positionRight) / 2;
			
			if (half >= 0 && listOfDataOfAllImages.size() > half && idToSearch < (int) ((Object[]) listOfDataOfAllImages.get(half))[0]) positionRight = half - 1;
			else if (half >= 0 && listOfDataOfAllImages.size() > half && idToSearch > (int) ((Object[]) listOfDataOfAllImages.get(half))[0]) positionLeft = half + 1;
			else positionLeft = positionRight = half;
		}
		
		if (positionLeft >= 0 && listOfDataOfAllImages.size() > positionLeft && idToSearch == (int) ((Object[]) listOfDataOfAllImages.get(positionLeft))[0]) return positionLeft;
		else return -1;
	}
	
	public static int dicotomicResearchTags(ArrayList<Object[]> listOfAllUniqueTagsAndIds, String textToSearch)
	{
		int positionLeft = 0;
		int positionRight = listOfAllUniqueTagsAndIds.size() - 1;
		int half;
		
		while (positionLeft < positionRight)
		{
			half = (positionLeft + positionRight) / 2;
			
			if (half >= 0 && listOfAllUniqueTagsAndIds.size() > half && textToSearch.compareTo((String) ((Object[]) listOfAllUniqueTagsAndIds.get(half))[0]) < 0) positionRight = half - 1;
			else if (half >= 0 && listOfAllUniqueTagsAndIds.size() > half && textToSearch.compareTo((String) ((Object[]) listOfAllUniqueTagsAndIds.get(half))[0]) > 0) positionLeft = half + 1;
			else positionLeft = positionRight = half;
		}
		
		if (positionLeft >= 0 && listOfAllUniqueTagsAndIds.size() > positionLeft && textToSearch.compareTo((String) ((Object[]) listOfAllUniqueTagsAndIds.get(positionLeft))[0]) == 0) return positionLeft;
		else return -1;
	}
	
	public static int dicotomicResearch(ArrayList<Object[]> listOfAllUniqueTags, String textToSearch)
	{
		int positionLeft = 0;
		int positionRight = listOfAllUniqueTags.size() - 1;
		int half;
		
		while (positionLeft < positionRight)
		{
			String textToConfrontMeta = "";
			half = (positionLeft + positionRight) / 2;
			if (half >= 0 && listOfAllUniqueTags.size() > half) textToConfrontMeta = (String) listOfAllUniqueTags.get(half)[0];
			
			if (half >= 0 && listOfAllUniqueTags.size() > half && textToSearch.compareTo(textToConfrontMeta) < 0) positionRight = half - 1;
			else if (half >= 0 && listOfAllUniqueTags.size() > half && textToSearch.compareTo(textToConfrontMeta) > 0) positionLeft = half + 1;
			else positionLeft = positionRight = half;
		}
		
		String textToConfrontSx = "";
		if (positionLeft >= 0 && listOfAllUniqueTags.size() > positionLeft) textToConfrontSx = (String) listOfAllUniqueTags.get(positionLeft)[0];
		
		if (positionLeft >= 0 && listOfAllUniqueTags.size() > positionLeft && textToSearch.compareTo(textToConfrontSx) == 0) return positionLeft;
		else return -1;
	}
	
	@SuppressWarnings("unchecked")
	public static String printInputData(ArrayList<Object[]> listOfDataOfAllImages)
	{
		String result = "";
		int i = 0;
		for (Object[] theObjectArray : listOfDataOfAllImages)
		{
			//Object[] listOfDataPerImage = (Object[]) listOfDataOfAllImages.get(i);
			result = result + theObjectArray[0] + " " +
					theObjectArray[1] + " " + 
					theObjectArray[2] + " ";
			
			for (String tag : (ArrayList<String>) theObjectArray[3]) result = result + tag + " ";
			
			result = result + "\n";
			System.out.println("calculating ... " + i + " / " + listOfDataOfAllImages.size());
			i++;
		}
		
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	public static String printIdsOrientAndTags(ArrayList<Object[]> listOfDataOfAllImages)
	{
		String result = "";
		int i = 0;
		for (Object[] theObjectArray : listOfDataOfAllImages)
		{
			//Object[] listOfDataPerImage = (Object[]) listOfDataOfAllImages.get(i);
			result = result + theObjectArray[0] + " : " +
					theObjectArray[1] + " - ";
			
			for (String tag : (ArrayList<String>) theObjectArray[3]) result = result + tag + " ";
			
			result = result + "\n";
			System.out.println("calculating ... " + i + " / " + listOfDataOfAllImages.size());
			i++;
		}
		
		return result;
		
	}

	public static void printToFile(String textToOuputFile, String fileName) throws IOException
	{
	    String str = textToOuputFile;
	    
	    Path path = Paths.get(fileName);
	    byte[] strToBytes = str.getBytes();
	 
	    Files.write(path, strToBytes);
	}

}





