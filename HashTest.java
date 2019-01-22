import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author spenceradams 321
 *
 *Fully functional test class for a HashTable implementation that accepts 4 different object types including string, int, long, char.
 *Tests full functionality of HashTable class and prints full debug table when requested.
 *
 */
public class HashTest {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args){
		 //Below makes sure that the user inputs all data required
		 boolean check = false;
		 for(int i = 0; i<args.length-1;i++){
			 if(args[i] == null){
				 check = true;
			 }
		 }
		 if(check == true){
			 System.out.println("Incorrect Format");
			 System.out.println("Format should be as follows:");
			 System.out.println("java HashTest [load factor] [hash table capacity] [input file] [input type] [ | debug level]");
		 }
		 if(args.length == 0){
			 System.out.println("Please Supply Inputs");
		 } else {
         float loadFactor = Float.parseFloat(args[0]);
         int hashTableCapacity = Integer.parseInt(args[1]);  // should be a prime number with another prime two less than that value
         String inputFile = args[2];
         int inputType = Integer.parseInt(args[3]);
         int debugLevel = 0;
         if(args.length -1 == 4){
         debugLevel = Integer.parseInt(args[4]);
         }
         //String file = "xxxx";
         try {
				Scanner scan = new Scanner(new File(args[2]));
				if(inputType == 0){//int
		        	 HashTable hashTableLP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.linear);
		    		 HashTable hashTableDH = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.double_hash);
		    		 HashTable hashTableQP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.quadratic);
		    			while (scan.hasNextLine() && (hashTableDH.size()+1 > hashTableDH.getMaxSize()) != true) {
		    				
		    				Scanner scanLine = new Scanner(scan.nextLine());
		    				 int value = Integer.parseInt(scanLine.next());
		    				 int key = Integer.parseInt(scanLine.next());
								hashTableLP.put(value,key);
								hashTableDH.put(value,key);
								hashTableQP.put(value,key);
		    			}
		    			printResults(hashTableLP,hashTableDH,hashTableQP,debugLevel);
		         }else if(inputType == 1){//Long
		        	 HashTable hashTableLP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.linear);
		    		 HashTable hashTableDH = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.double_hash);
		    		 HashTable hashTableQP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.quadratic);
		    		 while (scan.hasNextLine() && (hashTableLP.size()+1 > hashTableLP.getMaxSize()) != true) {
		    			 
		    			 Scanner scanLine = new Scanner(scan.nextLine());
	    				 Long value = Long.parseLong(scanLine.next());
	    				 int key = Integer.parseInt(scanLine.next());
							hashTableLP.put(value,key);
							hashTableDH.put(value,key);
							hashTableQP.put(value,key);
		    			}
		    		 printResults(hashTableLP,hashTableDH,hashTableQP,debugLevel);
		         }else if(inputType == 2){//String
		        	 HashTable hashTableLP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.linear);
		    		 HashTable hashTableDH = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.double_hash);
		    		 HashTable hashTableQP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.quadratic);
		    			 while (scan.hasNextLine()){ //&&(hashTableDH.size()+1 > hashTableDH.getMaxSize()) != true) {
		    				 
		    				 Scanner scanLine = new Scanner(scan.nextLine());
		    				 String value = scanLine.next();
		    				 int key = Integer.parseInt(scanLine.next());
								hashTableLP.put(value,key);
								hashTableDH.put(value,key);
								hashTableQP.put(value,key);
			    			}
		    			 //System.out.println(hashTableLP.getMaxSize() + " " + hashTableLP.getLoadFactor() + " " + hashTableLP.size());
		    			 //System.out.println(hashTableLP.getNumProbes());
		    		 printResults(hashTableLP,hashTableDH,hashTableQP,debugLevel);
		         }else if(inputType == 3){//Character
		        	 HashTable hashTableLP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.linear);
		    		 HashTable hashTableDH = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.double_hash);
		    		 HashTable hashTableQP = new HashTable(hashTableCapacity,loadFactor,OpenAddressType.quadratic);
		    		 while (scan.hasNextLine() && (hashTableLP.size()+1 > hashTableLP.getMaxSize()) != true) {
		    			 while (scan.hasNextLine() ){//&& ((hashTableLP.size()+1 > hashTableLP.getMaxSize()) != true)) {
		    				 
			    				 Scanner scanLine = new Scanner(scan.nextLine());
			    				 char value = scanLine.next().charAt(0);
			    				 int key = Integer.parseInt(scanLine.next());
								hashTableLP.put(value,key);
								hashTableDH.put(value,key);
								hashTableQP.put(value,key);
			    			}
		    		 }
		    		 printResults(hashTableLP,hashTableDH,hashTableQP,debugLevel);
		         }
			} catch (FileNotFoundException e) {
				System.out.println("No File Found!");
				System.out.println(args[2]);
		}
}		 
	}
	private static void printResults(HashTable linear, HashTable doubleHash,HashTable quadratic,int debug) {
		if(debug == 0){
			DecimalFormat loadFormat = new DecimalFormat("#.######");
			StringBuilder str = new StringBuilder();
			str.append("\n");
			str.append("Using linear hashing....\n");
			str.append("Inserted " + linear.getInsertions() + ", of which " + linear.getNumDuplicates() + " duplicates.\n");
			str.append("Avg. no of probes " + loadFormat.format(((double)linear.getNumProbes()/linear.getInsertions())) + "\n");
			str.append("\n");
			str.append("Using double hashing....\n");
			str.append("Inserted " + doubleHash.getInsertions() + ", of which " + doubleHash.getNumDuplicates() + " duplicates.\n");
			str.append("Avg. no of probes " + loadFormat.format((double)doubleHash.getNumProbes()/doubleHash.getInsertions()) + "\n");
			str.append("\n");
			str.append("Using Quadratic hashing....\n");
			str.append("Inserted " + quadratic.getInsertions() + ", of which " + quadratic.getNumDuplicates() + " duplicates.\n");
			str.append("Avg. no of probes " + loadFormat.format((double)quadratic.getNumProbes()/quadratic.getInsertions()) + "\n");
			System.out.println(str.toString());
		} else {
			DecimalFormat loadFormat = new DecimalFormat("#.######");
			StringBuilder str = new StringBuilder();
			str.append("\n");
			str.append("Using linear hashing....\n");
			str.append("Inserted " + linear.getInsertions() + ", of which " + linear.getNumDuplicates() + " duplicates.\n");
			str.append("Avg. no of probes " + loadFormat.format((double)linear.getNumProbes()/linear.getInsertions()) + "\n");
			str.append(linear.toString());
			str.append("\n");
			str.append("Using double hashing....\n");
			str.append("Inserted " + doubleHash.getInsertions() + ", of which " + doubleHash.getNumDuplicates() + " duplicates.\n");
			str.append("Avg. no of probes " + loadFormat.format((double)doubleHash.getNumProbes()/doubleHash.getInsertions()) + "\n");
			str.append(doubleHash.toString());
			str.append("\n");
			str.append("Using Quadratic hashing....\n");
			str.append("Inserted " + quadratic.getInsertions() + ", of which " + quadratic.getNumDuplicates() + " duplicates.\n");
			str.append("Avg. no of probes " + loadFormat.format((double)quadratic.getNumProbes()/quadratic.getInsertions()) + "\n");
			str.append(quadratic.toString());
			System.out.println(str.toString());
		}
	}
	
}


