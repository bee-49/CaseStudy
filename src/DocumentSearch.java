import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;


public class DocumentSearch {


	private String searchTerm = "";
	private int methodSelected;
	private HashMap<Integer,String> searchResults;
	private HashMap<String,Integer> indexMap;
	private List<Integer> relevanceList; 

	//variables for files
	public static File file1;
	public static File file2;
	public static File file3;
	public static Map<File,String> fileMap;

/*
 * This constructor takes in a search term and search method.
 * It instantiates a hashmap that is used to store the results
 * and calls getSearchMethod to kick off the document search
*/
	public DocumentSearch(String search, int method) throws FileNotFoundException {
		
		searchResults = new HashMap<>();
		relevanceList = new ArrayList<>();
		searchTerm = search.toLowerCase();
		methodSelected = method;

		getSearchMethod();
			
	}
/*
 * The helper function is called from main and stores the three sample files.
 * It places them into a map with user friendly names used to display the
 * results to the user.
*/
	public static void readFiles(File f1,File f2,File f3) {
	
		file1 = f1; 
		file2 = f2;
		file3 = f3;
		
		fileMap = new HashMap<>();
		
		fileMap.put(file1,"File1.txt");
		fileMap.put(file2,"File2.txt");
		fileMap.put(file3,"File3.txt");
	}
		
/*
 * Method used to call appropriate searching method based off of
 * the method type specified by the user.
*/
	private void getSearchMethod() throws FileNotFoundException {
		
		switch (methodSelected) {
		case 1:
			getStringMatch(file1);
			getStringMatch(file2);
			getStringMatch(file3);
			break;
		case 2:
			getRegEx(file1);
			getRegEx(file2);
			getRegEx(file3);
			break;
		case 3:
			getIndexed(file1);
			getIndexed(file2);
			getIndexed(file3);
			break;
		}
		
	}
/*
 * This method loops through the file line by line searching
 * for all occurrences of the given phrase or term.
 * if a match is found, count gets incremented and the final
 * results are placed in the appropriate data structure.
*/
	private void getStringMatch(File file){

// 		  int count = 0;
// 		  try (Scanner scanner = new Scanner(file)) {
// 			  while(scanner.hasNext()) {
// 				  String word = scanner.next();
// 				  if(word.equalsIgnoreCase(searchTerm)) {
// 					  count++;
// 				  }

			 while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				line.toLowerCase();
				int index = line.indexOf(searchTerm);
				while(index != -1) {
					count++;
					index = line.indexOf(searchTerm, index + searchTerm.length());
				}
				
			}
		  } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		  
		  relevanceList.add(count);
		  searchResults.put(count,fileMap.get(file));
	}
	
/*
 * Pattern Class defines the word to search for
 * Matcher Class - Used to search for the pattern
 * This method loops through the file and compares
 * word by word using RegEX. If a match if found
 * count gets incremented and the final results are
 * placed in the appropriate data structures.
*/
	private void getRegEx(File file) throws FileNotFoundException {
		//RegEx pattern
		Pattern pattern = Pattern.compile(searchTerm);
		 int count = 0;
		 try (Scanner scanner = new Scanner(file)) {
			 while(scanner.hasNext()) {
				 String word = scanner.next();
				 word.toLowerCase();
				 Matcher match = pattern.matcher(word);
				 if(match.find()) {
					 count++;
				 }
			 }
		}
		 relevanceList.add(count);
		 searchResults.put(count,fileMap.get(file));
	}
	
	
/*
 * hashmap is used to store the words and the number of 
 * times it appears within the document. Once the hashmap
 * is loaded, it is used as an index to lookup the
 * search term and store the results in results map. 
*/
	private void getIndexed(File file) throws FileNotFoundException {
		indexMap = new HashMap<>();
		int idx = 0;
		int count = 0;

			try (Scanner scanner = new Scanner(file)) {
				while(scanner.hasNext()) {
					String word = scanner.next();
					word.toLowerCase();
					if(indexMap.containsKey(word)) {
						idx = indexMap.get(word) + 1;
					}
					
					indexMap.put(word, idx);
				}
			}
			
		if(indexMap.containsKey(searchTerm)) {
			count = indexMap.get(searchTerm);
		}
		
		relevanceList.add(count);
		searchResults.put(count,fileMap.get(file));
	}
	
/*
 * method that displays the results of the search in order
 * of relevance.
*/
	public void getResults() {
		
		//List<Integer> myKeys = new ArrayList<Integer>(searchResults.keySet());
		Collections.sort(relevanceList,Collections.reverseOrder());
				
		for(int count : relevanceList) {
			if(count == 0) {
				System.out.println("This file did not return any results.");
			}
			else {
				System.out.println(searchResults.get(count) + "-" + count + " matches");
			}
			
		}

	}

	public static void main(String[] args) throws FileNotFoundException{
		
		File file1 = new File(args[0]); 
		File file2 = new File(args[1]); 
		File file3 = new File(args[2]); 
		
		readFiles(file1,file2,file3);
		
		String searchTerm = "";
		int methodSelected;
		boolean errorFlag;

		/*
		 * Performance testing
		 * reads in a file with 2M random words
		 * the test was ran with each searching method
		 * results are discussed in another document.
		*/
//		File in = new File(args[3]);
//		long start = System.currentTimeMillis();;
//		Scanner scanner = new Scanner(in);
//			while(scanner.hasNextLine()) {
//				String word = scanner.nextLine();
//				try {
//					new DocumentSearch(word,1);
//
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		long end = System.currentTimeMillis();;
//		
//		System.out.println("Elapsed time: " + (end - start) + " ms");

		
		try (Scanner userInput = new Scanner(System.in)) {
			// get user input for term/phrase to search
			System.out.println("Enter phrase or term to search: ");
			searchTerm = userInput.nextLine();

			// get user input for search method
			do {
				System.out.println("Select the search method: (1)String Match (2)Regular Expression (3)Indexed");
				methodSelected = userInput.nextInt();
				// check to make sure valid input
				if (methodSelected < 1 || methodSelected > 3) {
					System.out.println("Invalid entry.");
					errorFlag = true;
				} else {
					errorFlag = false;
				}
			} while (errorFlag);

		}
		
			
			long start = System.currentTimeMillis();;
			try {
				DocumentSearch mySearch = new DocumentSearch(searchTerm,methodSelected);
				mySearch.getResults();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();;
			
			System.out.println("Elapsed time: " + (end - start) + " ms");
			


	}

}
