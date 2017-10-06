package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AllocationGenerator {
	/**
	 * Input to this program would be a CSV file. The format of the File would be like below. 
	 * Stock_Name, Sector, Quantity, Price, Added_Date
	 * @param args the path to the input file. 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length!=1){
			System.out.println("Invalid number of input arguments..");
			System.exit(0);
		}
		
		// Read the input file
		FileReader reader = new FileReader(new File(args[0]));
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		Map<String, Double> sectorMap = new HashMap<String, Double>();
		
		double totalValue = 0.0;
		while((line=br.readLine())!=null){
			String[] arr = line.split(",");
			//String name = arr[0];
			String sector = arr[1].trim();
			int quantity = Integer.parseInt(arr[2].trim());
			double price = Double.parseDouble(arr[3].trim());
			//String date = arr[4];
		    double value = quantity * price;
		    totalValue += value;
		    
		    double exstValue = 0.0;
		    
		    if(sectorMap.get(sector.toLowerCase())!=null){
		    	 exstValue = sectorMap.get(sector.toLowerCase());
		    }
		    double newVluae = exstValue + value;
	    	sectorMap.put(sector.toLowerCase(), newVluae);
	    
		    
		    //Share share = new Share(name, sector, quantity, price);
		}
		br.close();
		
		Map<String, Double> allocationMap = generateAllocation(sectorMap,totalValue);
		
		Iterator<String> it = allocationMap.keySet().iterator();
		Map<String, Double> sectorAllPerMap = new HashMap<String, Double>();
		while(it.hasNext()){
			String sector = it.next();
			double allocPer = allocationMap.get(sector);
			System.out.println(sector+"="+allocPer);			
		}
		
		
	}

	private static Map<String, Double> generateAllocation(Map<String, Double> sectorMap, double totalValue) {
		
		Iterator<String> it = sectorMap.keySet().iterator();
		Map<String, Double> sectorAllPerMap = new HashMap<String, Double>();
		while(it.hasNext()){
			String sector = it.next();
			double allocPer = sectorMap.get(sector) * 100 / totalValue;
			sectorAllPerMap.put(sector, allocPer);			
		}
		return sectorAllPerMap;
		
	}

}
