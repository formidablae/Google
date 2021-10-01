import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SolutionFastFileRead
{
	@SuppressWarnings({ "unchecked", "resource" })
	public static void main(String[] args) throws IOException
	{
		//String outputFileNameA = "/path/to/Downloads/outputA.txt";
		//String outputFileNameB = "/path/to/Downloads/outputB.txt";
		String outputFileNameC = "/path/to/Downloads/outputC2.txt";
		//String outputFileNameD = "/path/to/Downloads/outputD.txt";
		//String outputFileNameE = "/path/to/Downloads/outputE.txt";
		String[] outputFiles = {
				//outputFileNameA,
				//outputFileNameB,
				outputFileNameC,
				//outputFileNameD,
				//outputFileNameE
				};
		
		//File fileReadAimgs = new File("/path/to/Downloads/a_all_images.txt");
		//File fileReadAtags = new File("/path/to/Downloads/a_all_tags.txt");
		//File fileReadBimgs = new File("/path/to/Downloads/b_all_images.txt");
		//File fileReadBtags = new File("/path/to/Downloads/b_all_tags.txt");
		File fileReadCimgs = new File("/path/to/Downloads/c_all_images.txt");
		File fileReadCtags = new File("/path/to/Downloads/c_all_tags.txt");
		//File fileReadDimgs = new File("/path/to/Downloads/d_all_images.txt");
		//File fileReadDtags = new File("/path/to/Downloads/d_all_tags.txt");
		//File fileReadEimgs = new File("/path/to/Downloads/e_all_images.txt");
		//File fileReadEtags = new File("/path/to/Downloads/e_all_tags.txt");
		File[] filesToRead = {
				//fileReadAimgs,
				//fileReadAtags,
				//fileReadBimgs,
				//fileReadBtags,
				fileReadCimgs,
				fileReadCtags,
				//fileReadDimgs,
				//fileReadDtags,
				//fileReadEimgs,
				//fileReadEtags
				};
		
		ArrayList<Object[]> listOfDataOfAllImages = null;
		ArrayList<Object[]> listOfAllUniqueTagsAndIds = null;
		
		for (int z = 0; z < filesToRead.length; z++)
		{
			Scanner input = new Scanner(filesToRead[z]);
			
			if (z % 2 == 0)		// ids data of images
			{
				listOfDataOfAllImages = new ArrayList<Object[]>();
				int y = 0;
				while (input.hasNextLine())
				{
					String[] lineTextArray = input.nextLine().split(" ");
					Object[] listOfDataPerImage = new Object[4];
					
					listOfDataPerImage[0] = Integer.parseInt(lineTextArray[0]);
					listOfDataPerImage[1] = lineTextArray[1];
					listOfDataPerImage[3] = new ArrayList<String>();
					
					for (int i = 3; i < lineTextArray.length; i++) ((ArrayList<String>) listOfDataPerImage[3]).add(lineTextArray[i]);
					
					listOfDataOfAllImages.add(listOfDataPerImage);
					
					System.out.println("fileRead = " + (z + 1) + " / " + filesToRead.length + ". Row = " + (y + 1));
					y++;
				}
			}
			else		// tags
			{
				listOfAllUniqueTagsAndIds = new ArrayList<Object[]>();
				int y = 0;
				while (input.hasNextLine())
				{
					String[] lineTextArray = input.nextLine().split(" ");
					Object[] tagAndIds = new Object[2];
					
					tagAndIds[0] = lineTextArray[0];
					tagAndIds[1] = new ArrayList<Integer>();
					for (int i = 1; i < lineTextArray.length; i++) ((ArrayList<Integer>) tagAndIds[1]).add(Integer.parseInt(lineTextArray[i]));
					
					listOfAllUniqueTagsAndIds.add(tagAndIds);
					System.out.println("fileRead = " + (z + 1) + " / " + filesToRead.length + ". Row = " + (y + 1));
					y++;
				}
			}
			
			if (z % 2 != 0)
			{
				// printToFile(printInputData(listOfDataOfAllImages), outputFiles[z * 2]);
	
				// printToFile(printIdsOrientAndTags(listOfDataOfAllImages), outputFiles[z * 2]);
				System.out.println("Before computing");
				// printToFile(printTagsAndIds(listOfAllUniqueTagsAndIds), outputFiles[z * 2 + 1]);
				String computedString = computeOutput(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, (z - 1) / 2, listOfDataOfAllImages.size() - 1, 0);
				System.out.println("After computing");
				printToFile(computedString, outputFiles[(z - 1) / 2]);
				System.out.println("End");
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static String computeOutput(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> listOfAllUniqueTagsAndIds, int z, int numberOfPhotosInt, int randomNum)
	{
		StringBuilder result = new StringBuilder();
		String nextTag = (String) ((Object[]) listOfAllUniqueTagsAndIds.get(0))[0];
		String nextNewTag = "";
		int progress = 0;
		int dimensi = listOfAllUniqueTagsAndIds.size();
		
		while (dimensi > 1)
		{
			int thisId  = getFirstIdOfTag(listOfAllUniqueTagsAndIds, nextTag);
			String orientation = getOrientationOfId(listOfDataOfAllImages, thisId);
			
			if (orientation.equals("V"))
			{
				removeTagFromIdList(listOfDataOfAllImages, nextTag, thisId);
				nextNewTag = nextTagFromThisId(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, thisId);
				
				removeIdFromIdsList(listOfDataOfAllImages, thisId);
				dimensi = dimensi - removeEmptyIdsFromIdList(listOfDataOfAllImages);
				
				removeIdFromTagsList(listOfAllUniqueTagsAndIds, thisId);
				removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
				
				nextTag = nextNewTag;
				
				int idOtherVertical = findIdFirstVertical(listOfDataOfAllImages);
				
				removeIdFromIdsList(listOfDataOfAllImages, idOtherVertical);
				dimensi = dimensi - removeEmptyIdsFromIdList(listOfDataOfAllImages);
				removeIdFromTagsList(listOfAllUniqueTagsAndIds, idOtherVertical);
				removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
				
				result.append(thisId);
				result.append(" ");
				result.append(idOtherVertical);
				result.append("\n");
				
				System.out.println("computing output ... " + progress + " / " + numberOfPhotosInt);
				progress = progress + 2;
			}
			else
			{
				removeTagFromIdList(listOfDataOfAllImages, nextTag, thisId);
				nextNewTag = nextTagFromThisId(listOfDataOfAllImages, listOfAllUniqueTagsAndIds, thisId);
				
				removeIdFromIdsList(listOfDataOfAllImages, thisId);
				dimensi = dimensi - removeEmptyIdsFromIdList(listOfDataOfAllImages);
				
				removeIdFromTagsList(listOfAllUniqueTagsAndIds, thisId);
				removeEmptyTagsFromTagList(listOfAllUniqueTagsAndIds);
				
				result.append(thisId);
				result.append("\n");
				
				nextTag = nextNewTag;
				System.out.println("computing output ... " + progress + " / " + numberOfPhotosInt);
				
				progress++;
			}
			
			int indexNow = searchIndexOfTag(listOfAllUniqueTagsAndIds, nextTag);
			
			while (nextTag == null || indexNow == -1 ||
					((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(indexNow))[1]).size() == 0)
			{
				if (nextTag != null && indexNow >= 0
						&& dimensi > 1 &&
						((ArrayList<Integer>) ((Object[]) listOfAllUniqueTagsAndIds.get(indexNow))[1]).size() != 0)
				{
					listOfAllUniqueTagsAndIds.remove(indexNow);
					dimensi--;
					nextTag = (String) ((Object[]) listOfAllUniqueTagsAndIds.get(0))[0];
					indexNow = searchIndexOfTag(listOfAllUniqueTagsAndIds, nextTag);
				}
				else
				{
					nextTag = (String) ((Object[]) listOfAllUniqueTagsAndIds.get(0))[0];
					indexNow = searchIndexOfTag(listOfAllUniqueTagsAndIds, nextTag);
				}
				
				if (dimensi == 1) break;
			}
			
			if (dimensi == 2) break;
		}
		
		return result.toString();
	}
	
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	
	public static String getOrientationOfId(ArrayList<Object[]> listOfDataOfAllImages, int id)			// Done
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if (index >= 0) return ((String) ((Object[]) listOfDataOfAllImages.get(index))[1]);
		else return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public static int getFirstIdOfTag(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)			// Done
	{
		int index = searchIndexOfTag(thelistOfAllUniqueTagsAndIds, tag);
		
		if(index >= 0) return ((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(index))[1]).get(0);
		else return -1;
	}
	
	
	@SuppressWarnings("unchecked")
	public static int nextIdFromThisId(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, int id)
	{
		String thisTag;
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if (index >= 0) thisTag = ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(index))[3]).get(0);
		else thisTag = null;
		
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
	
	
	public static String nextTagFromThisId(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, int id)			// Done
	{
		if(id >= 0)
		{
			int index = dicotomicResearchIds(listOfDataOfAllImages, id);
			if (index >= 0) return ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(index))[3]).get(0);
			else return null;
		}
		else if (thelistOfAllUniqueTagsAndIds.size() > 0)
		{
			return (String) ((Object[]) thelistOfAllUniqueTagsAndIds.get(0))[0];
		}
		else return null;
	}
	
	public static String nextTagFromThisTag(ArrayList<Object[]> listOfDataOfAllImages, ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, String tag)
	{
		int nextId = nextIdFromThisTag(listOfDataOfAllImages, thelistOfAllUniqueTagsAndIds, tag);
		int index = dicotomicResearchIds(listOfDataOfAllImages, nextId);
		if (index >= 0) return ((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(index))[3]).get(0);
		else return null;
	}
	
	public static boolean idInSlideshow(ArrayList<Integer> slideshowIncluded, int id)
	{
		if (slideshowIncluded.contains(id)) return true;
		else return false;
	}
	
	public static boolean removeIdFromIdsList(ArrayList<Object[]> listOfDataOfAllImages, int id)			// Done
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if (index >= 0)
		{
			listOfDataOfAllImages.remove(index);
			return true;
		}
		else return false;
	}
	
	public static int removeEmptyIdsFromIdList(ArrayList<Object[]> listOfDataOfAllImages)			// Done
	{
		int count = 0;
		int dim = listOfDataOfAllImages.size();
		for (int i = 0; i < dim; i++)
		{
			if (((ArrayList<String>) ((Object[]) listOfDataOfAllImages.get(i))[3]).size() == 0)
			{
				listOfDataOfAllImages.remove(i);
				count++;
				dim--;
			}
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean removeIdFromTagsList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds, int id)		// Done
	{
		boolean removed = false;
		int dim = thelistOfAllUniqueTagsAndIds.size();
		for (int i = 0; i < dim; i++)
		{
			if (((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).contains(id))
			{
				((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).remove(new Integer(id));
				removed = true;
			}
		}
		if (removed) return true;
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public static int findIdFirstVertical(ArrayList<Object[]> listOfDataOfAllImages)			// Done Changed
	{
		int dim = listOfDataOfAllImages.size();
		for (int i = 0; i < dim; i++)
		{
			if (((String) ((Object[]) listOfDataOfAllImages.get(i))[1]).equals("V"))
			{
					return (int) ((Object[]) listOfDataOfAllImages.get(i))[0];
			}
		}
		return -1;
	}
	
	
	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public static boolean removeTagFromIdList(ArrayList<Object[]> listOfDataOfAllImages, String tag, int id)			// Done
	{
		int index = dicotomicResearchIds(listOfDataOfAllImages, id);
		if (index >= 0)
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
	public static int removeEmptyTagsFromTagList(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)				// Done
	{
		int count = 0;
		int dim = thelistOfAllUniqueTagsAndIds.size();
		for (int i = 0; i < dim; i++)
		{
			if (((ArrayList<Integer>) ((Object[]) thelistOfAllUniqueTagsAndIds.get(i))[1]).size() == 0)
			{
				thelistOfAllUniqueTagsAndIds.remove(i);
				count++;
				dim--;
			}
		}
		return count;
	}
	
	/////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public static void sortTagsByPresencesDecesc(ArrayList<Object[]> thelistOfAllUniqueTagsAndIds)				// Done
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
		int i = 0;
		for (Object[] everyObject : thelistOfAllUniqueTagsAndIds)
		{
			result = result + everyObject[0] + " ";
			for (int id : (ArrayList<Integer>) everyObject[1])
			{
				result = result + id + " ";
			}
			result = result + "\n";
			System.out.println("calculating tags output ... " + i + " / " + thelistOfAllUniqueTagsAndIds.size());
			i++;
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
			if (half >= 0 &&
					listOfDataOfAllImages.size() > half &&
					idToSearch <
					(int) ((Object[]) listOfDataOfAllImages.get(half))[0])
			{
				positionRight = half - 1;
			}
			else if (half >= 0 && listOfDataOfAllImages.size() > half && idToSearch > (int) ((Object[]) listOfDataOfAllImages.get(half))[0]) positionLeft = half + 1;
			else positionLeft = positionRight = half;
		}
		
		if (positionLeft >= 0 && listOfDataOfAllImages.size() > positionLeft && idToSearch == (int) ((Object[]) listOfDataOfAllImages.get(positionLeft))[0])
		{
			return positionLeft;
		}
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
