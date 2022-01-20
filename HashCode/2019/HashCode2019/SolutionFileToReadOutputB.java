import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SolutionFileToReadOutputB
{
	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) throws IOException
	{
		//String outputfileReadAimgs = "/path/to/Downloads/a_all_images.txt";
		//String outputfileReadAtags = "/path/to/Downloads/a_all_tags.txt";
		//String outputfileReadBimgs = "/path/to/Downloads/b_all_images.txt";
		//String outputfileReadBtags = "/path/to/Downloads/b_all_tags.txt";
		String outputfileReadCimgs = "/path/to/Downloads/c_all_images.txt";
		String outputfileReadCtags = "/path/to/Downloads/c_all_tags.txt";
		//String outputfileReadDimgs = "/path/to/Downloads/d_all_images.txt";
		//String outputfileReadDtags = "/path/to/Downloads/d_all_tags.txt";
		//String outputfileReadEimgs = "/path/to/Downloads/e_all_images.txt";
		//String outputfileReadEtags = "/path/to/Downloads/e_all_tags.txt";
		String[] outputFiles = {
				//outputfileReadAimgs,
				//outputfileReadAtags,
				//outputfileReadBimgs,
				//outputfileReadBtags,
				outputfileReadCimgs,
				outputfileReadCtags,
				//outputfileReadDimgs,
				//outputfileReadDtags,
				//outputfileReadEimgs,
				//outputfileReadEtags
				};
		
		//File fileReadA = new File("/path/to/Downloads/a_example.txt");
		// fileReadB = new File("/path/to/Downloads/b_lovely_landscapes.txt");
		File fileReadC = new File("/path/to/Downloads/c_memorable_moments.txt");
		//File fileReadD = new File("/path/to/Downloads/d_pet_pictures.txt");
		//File fileReadE = new File("/path/to/Downloads/e_shiny_selfies.txt");
		File[] filesToRead = {
				//fileReadA,
				//fileReadB,
				fileReadC,
				//fileReadD,
				//fileReadE
				};
		
		for (int z = 0; z < filesToRead.length; z++)
		{
			Scanner input = new Scanner(filesToRead[z]);
			int numberOfPhotosInt = Integer.parseInt(input.nextLine());
			
			ArrayList<Object[]> listOfAllUniqueTagsAndIds = new ArrayList<Object[]>();
			
			ArrayList<String> tagsAdded = new ArrayList<String>();
			
			for (int i = 0; i < numberOfPhotosInt; i++)
			{
				String[] lineTextArray = input.nextLine().split(" ");
				
				int numberOfTags = Integer.parseInt(lineTextArray[1]);
				
				for (int j = 0; j < numberOfTags; j++)
				{
					String tagToAdd = lineTextArray[(j + 2)];
					int index = tagsAdded.indexOf(tagToAdd);
					if (index >= 0) ((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(index))[1]).add(i);
					else
					{
						Object[] tagAndIds = new Object[2];
						ArrayList<Integer> tags = new ArrayList<Integer>();
						tags.add(i);
						tagAndIds[0] = tagToAdd;
						tagAndIds[1] = tags;
						listOfAllUniqueTagsAndIds.add(tagAndIds);
						
						tagsAdded.add(tagToAdd);
					}
				}
				
				// System.out.println("testCases = " + (z + 1) + " / " + filesToRead.length + ". Row = " + (i + 1) + " / " + numberOfPhotosInt);
				System.out.println("Row = " + (i + 1) + " / " + numberOfPhotosInt);
			}
			
			// printToFile(printInputData(listOfDataOfAllImages), outputFiles[z * 2]);

			//printToFile(printIdsOrientAndTags(listOfDataOfAllImages), outputFiles[z * 2]);
			
			sortTagsByPresencesDecesc(listOfAllUniqueTagsAndIds);
			
			printToFile(printTagsAndIds(listOfAllUniqueTagsAndIds), outputFiles[z * 2 + 1]);
			
			// printToFile(computeOutput(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, z, numberOfPhotosInt, 0), outputFiles[z]);
		}
	}
	
	public static String computeOutput(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> listOfAllUniqueTagsAndIds, int z, int numberOfPhotosInt, int randomNum)
	{
		ArrayList<Integer> idsAlreadyInSlideshow = new ArrayList<Integer>();
		String result = "";
		String thisTag = getFirstTagInList(listOfAllUniqueTagsAndIds);
		String nextTag = thisTag;
		String nextNewTag = "";
		int progress = 0;
		int randomNumber = randomNum;
		
		while (listOfAllUniqueTagsAndIds.size() > 0)
		{
			int thisId  = getFirstIdOfTag(listOfAllUniqueTagsAndIds, nextTag);
			if (thisId >= 0 && !idsAlreadyInSlideshow.contains(thisId)) System.out.println("computing output ... " + progress + " / " + numberOfPhotosInt);
			
			String orientation = getOrientationOfId(listOfDataOfAllImages, thisId);
			
			if (idsAlreadyInSlideshow.contains(thisId) || thisId < 0 || orientation == null)
			{
				nextNewTag = nextTagFromThisId(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, thisId);
				
				removeIdFromTagsList(listOfAllUniqueTagsAndIds, thisId);
				removeTagFromIdList(listOfDataOfAllImages, nextTag, thisId);
				removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
				
				nextTag = nextNewTag;
			}
			else if (orientation.equals("V"))
			{
				idsAlreadyInSlideshow.add(thisId);
				nextNewTag = nextTagFromThisId(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, thisId);
				
				removeIdFromTagsList(listOfAllUniqueTagsAndIds, thisId);
				removeTagFromIdList(listOfDataOfAllImages, nextTag, thisId);
				removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
				
				nextTag = nextNewTag;
				
				boolean notGoodOtherVertical = true;
				while (notGoodOtherVertical)
				{
					int idOtherVertical = findIdFirstVertical(listOfDataOfAllImages, listOfAllUniqueTagsAndIds);
					notGoodOtherVertical = false;
					
					if (idsAlreadyInSlideshow.contains(idOtherVertical) || idOtherVertical < 0 || getOrientationOfId(listOfDataOfAllImages, idOtherVertical) == null)
					{
						notGoodOtherVertical = true;
						removeIdFromTagsList(listOfAllUniqueTagsAndIds, idOtherVertical);
						removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
					}
					else
					{
						idsAlreadyInSlideshow.add(idOtherVertical);
						result = result + thisId + " " + idOtherVertical + "\n";
						
						removeIdFromTagsList(listOfAllUniqueTagsAndIds, idOtherVertical);
						removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
					}
				}
				progress = progress + 2;
			}
			else
			{
				idsAlreadyInSlideshow.add(thisId);
				result = result + thisId + "\n";
				
				nextNewTag = nextTagFromThisId(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, thisId);
				
				removeIdFromTagsList(listOfAllUniqueTagsAndIds, thisId);
				removeTagFromIdList(listOfDataOfAllImages, nextTag, thisId);
				removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
				
				nextTag = nextNewTag;
				progress++;
			}
			
			if (nextTag == null)
			{
				nextTag = getFirstTagInList(listOfAllUniqueTagsAndIds);
			}
			
			if (listOfAllUniqueTagsAndIds.size() == 0) System.out.println("computing output ... " + progress + " / " + numberOfPhotosInt);
		}
		
		return result;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	@SuppressWarnings("unchecked")
	public static int sizeFirstTagInList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		if (thelistOfAllUniqueTagsAndIds.size() > 0 && ((Object[]) thelistOfAllUniqueTagsAndIds.get(0)).length > 0)
		{
			return ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(0))[1]).size();
		}
		else return -1;
	}
	
	@SuppressWarnings("unchecked")
	public static int variableFirstIdInTagList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		
		int index = searchIndexOfTag(thelistOfAllUniqueTagsAndIds, tag);
		int dim = -1;
		if (index >= 0) dim = ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).size();
		
		if (index >= 0 && dim > 0)
		{
			if (dim == 1)
			{
				System.out.println("return" + (int) ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).get(0));
				return (int) ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).get(0);
			}
			else
			{
				System.out.println("dim = " + dim);
				int indexInTags = randInt(0, dim - 1);
				return (int) ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).get(indexInTags);
			}
		}
		else return getFirstIdOfTag(thelistOfAllUniqueTagsAndIds, tag);
	}
	
	public static String getFirstTagInList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		if (thelistOfAllUniqueTagsAndIds.size() > 0)
		{
			return (String) ((Object[]) thelistOfAllUniqueTagsAndIds.get(0))[0];
		}
		else return null;
	}
	
	@SuppressWarnings("unchecked")
	public static String getFirstTagOfId(ArrayList<Object[]> listOfDataOfAllImages, int id)
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if (index >= 0 && listOfDataOfAllImages.size() > index && ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(index))[3]).size() > 0)
		{
			return ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(index))[3]).get(0);
		}
		else return null;
	}
	
	public static String getOrientationOfId(ArrayList<Object[]> listOfDataOfAllImages, int id)
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if(index >= 0)
		{
			return (String) ((Object[]) listOfDataOfAllImages.get(index))[1];
		}
		else return null;
	}
	
	@SuppressWarnings("unchecked")
	public static int getFirstIdOfTag(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		int index = searchIndexOfTag(thelistOfAllUniqueTagsAndIds, tag);
		
		if(index >= 0 && ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).size() > 0)
		{
			int id = ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).get(0);
			return id;
		}
		else return -1;
	}
	
	public static int nextIdFromThisId(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, int id)
	{
		String thisTag = getFirstTagOfId(listOfDataOfAllImages, id);
		int nextId = -1;
		
		if (thisTag != null)
		{
			nextId = getFirstIdOfTag(thelistOfAllUniqueTagsAndIds, thisTag);
		}
		return nextId;
	}
	
	public static int nextIdFromThisTag(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		int id = getFirstIdOfTag(thelistOfAllUniqueTagsAndIds, tag);
		int nextId = -1;
		if (id != -1)
		{
			nextId = nextIdFromThisId(listOfDataOfAllImages, thelistOfAllUniqueTagsAndIds, id);
		}
		return nextId;
	}
	
	public static String nextTagFromThisId(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, int id)
	{
		if(id >= 0)
		{
			int nextId = nextIdFromThisId(listOfDataOfAllImages, thelistOfAllUniqueTagsAndIds, id);
			return getFirstTagOfId(listOfDataOfAllImages, nextId);
		}
		else
		{
			return getFirstTagInList(thelistOfAllUniqueTagsAndIds);
		}
	}
	
	public static String nextTagFromThisTag(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		int nextId = nextIdFromThisTag(listOfDataOfAllImages, thelistOfAllUniqueTagsAndIds, tag);
		return getFirstTagOfId(listOfDataOfAllImages, nextId);
	}
	
	public static boolean idInSlideshow(ArrayList<Integer> slideshowIncluded, int id)
	{
		if (slideshowIncluded.contains(id)) return true;
		else return false;
	}
	
	public static boolean removeIdFromIdsList(ArrayList<Object[]> listOfDataOfAllImages, int id)
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if (index >= 0 && listOfDataOfAllImages.size() >= index + 1)
		{
			listOfDataOfAllImages.remove(index);
			return true;
		}
		else return false;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean removeIdFromTagsList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, int id)
	{
		for (int i = 0; i < thelistOfAllUniqueTagsAndIds.size(); i++)
		{
			if (((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).contains(id))
			{
				((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).remove(new Integer(id));
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static int findIdFirstVertical(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		for (int i = 0; i < thelistOfAllUniqueTagsAndIds.size(); i++)
		{
			for (int j = 0; j < ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).size(); j++)
			{
				int id = (int) ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).get(j);
				
				if (getOrientationOfId(listOfDataOfAllImages, id).equals("V"))
				{
					return id;
				}
			}
		}
		return -1;
	}
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public static boolean removeTagFromIdList(ArrayList<Object[]> listOfDataOfAllImages, String tag, int id)
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if (index >= 0 && listOfDataOfAllImages.size() >= index + 1)
		{
			((ArrayList<Integer>) ((Object[]) listOfDataOfAllImages.get(index))[3]).remove(new String(tag));
			return true;
		}
		else return false;
	}
	
	public static boolean removeTagFromTagsList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		int index = searchIndexOfTag(thelistOfAllUniqueTagsAndIds, tag);
		if (index >= 0 && thelistOfAllUniqueTagsAndIds.size() >= index + 1)
		{
			thelistOfAllUniqueTagsAndIds.remove(index);
			return true;
		}
		else return false;
	}
	
	@SuppressWarnings("unchecked")
	public static int removeEmptyTagsFromTagList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		int count = 0;
		for (int i = 0; i < thelistOfAllUniqueTagsAndIds.size(); i++)
		{
			if (((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).size() == 0)
			{
				thelistOfAllUniqueTagsAndIds.remove(i);
				count++;
			}
		}
		return count;
	}

	/////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public static void sortTagsByPresencesDecesc(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)
	{
		boolean swapped = true;
		while (swapped)
		{
			swapped = false;
			int dimension = thelistOfAllUniqueTagsAndIds.size();
		    for (int i = 1; i < dimension; i++) {
		        if (((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i - 1))[1]).size() < ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).size())
		        {
		            Object[] swapTemp = (Object[]) thelistOfAllUniqueTagsAndIds.get(i);
		            thelistOfAllUniqueTagsAndIds.set(i, (Object[]) thelistOfAllUniqueTagsAndIds.get(i - 1));
		            thelistOfAllUniqueTagsAndIds.set(i - 1, swapTemp);
		            swapped = true;
		        }
		        System.out.println("Sorting tags ... " + i + " / " + dimension);
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
		if (textToSearch == null)
		{
			return -1;
		}
		else
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
	
	public static int searchIndexOfTag(ArrayList<Object[]> listOfAllUniqueTagsAndIds, String tag)
	{
		for (int i = 0; i < listOfAllUniqueTagsAndIds.size(); i++)
		{
			if (((String) ((Object[]) listOfAllUniqueTagsAndIds.get(i))[0]).equals(tag))
			{
				return i;
			}
		}
		return -1;
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
					theObjectArray[1] + " "
					// + theObjectArray[2] + " "
					;
			
			for (String tag : (ArrayList<String>) theObjectArray[3]) result = result + tag + " ";
			
			result = result + "\n";
			System.out.println("calculating ids output ... " + i + " / " + listOfDataOfAllImages.size());
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
			result = result + theObjectArray[0] + " " +
					theObjectArray[1] + " ";
			
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
