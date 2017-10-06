package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TradeMarginGenerator {

	private static String TRANSACTION_TYPE_BUY = "B";
	private static String TRANSACTION_TYPE_SELL = "S";

	/**
	 * This program is used to calculate the profit/loss generated during a
	 * specific time period. The input to the program is a CSV file. The input
	 * should be in the format S.No, Stock Name, Price, Quantity, Added Date,
	 * Transaction_Type(B/S) The program ignores the S.No and Added date fields.
	 * It uses other fields to calculate profit/loss generated.
	 * 
	 * @param args
	 *            the location of the input file which contains the Trade data.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// First checks if input arguments are available, else exit
		if (args.length != 1) {
			System.out.println("Input argument missing...Exiting.");
			System.exit(0);
		}

		String filePath = args[0];

		// Read the input file
		FileReader reader = new FileReader(new File(filePath));
		BufferedReader br = new BufferedReader(reader);
		String line = null;

		Map<String, Double> buyMap = new HashMap<String, Double>();
		Map<String, Double> sellMap = new HashMap<String, Double>();
		Set<String> dateSet = new TreeSet<String>();
		double totalMargin = 0;
		int lineNo = 0;
		while ((line = br.readLine()) != null) {
			lineNo++;
			System.out.println(line);

			String[] arr = line.split(",");
			String ticker = arr[0].trim();
			String price = arr[1].trim();
			String quantity = arr[2].trim();
			String addedDate = arr[3].trim();
			String type = arr[4].trim();

			dateSet.add(addedDate);

			if (type.equalsIgnoreCase(TRANSACTION_TYPE_BUY)) {
				if (buyMap.get(ticker.toLowerCase()) != null) {
					Double existingPrice = buyMap.get(ticker.toLowerCase());
					double transPrice = (Double.parseDouble(quantity)) * (Double.parseDouble(price));
					Double totalPrice = existingPrice + transPrice;
					buyMap.put(ticker.toLowerCase(), totalPrice);
				} else {
					double transPrice = (Double.parseDouble(quantity)) * (Double.parseDouble(price));
					buyMap.put(ticker.toLowerCase(), transPrice);
				}
			} else if (type.equalsIgnoreCase(TRANSACTION_TYPE_SELL)) {
				if (sellMap.get(ticker.toLowerCase()) != null) {
					Double existingPrice = sellMap.get(ticker.toLowerCase());
					double transPrice = (Double.parseDouble(quantity)) * (Double.parseDouble(price));
					Double totalPrice = existingPrice + transPrice;
					sellMap.put(ticker.toLowerCase(), totalPrice);
				} else {
					double transPrice = (Double.parseDouble(quantity)) * (Double.parseDouble(price));
					sellMap.put(ticker.toLowerCase(), transPrice);
				}

			} else {
				System.err.println("Invalid Transaction Type on line : " + lineNo);
				System.exit(0);
			}
		}

		Iterator<String> it = buyMap.keySet().iterator();

		double totalBuyValue = 0.0;

		while (it.hasNext()) {
			String tickerName = it.next();
			System.out.println(tickerName);
			double totalBuyPrice = buyMap.get(tickerName.toLowerCase());
			double totalSellPrice = sellMap.get(tickerName.toLowerCase());
			totalBuyValue += totalBuyPrice;
			double diff = totalSellPrice - totalBuyPrice;
			totalMargin += diff;
		}

		System.out.println("Total Money Invested : " + totalBuyValue);

		System.out.println("Total Margin for the week : " + totalMargin);

		System.out.println("Total Return as a percentage of money invested : " + (totalMargin * 100) / totalBuyValue);

		// String[] dateArr = (String[]) dateSet.toArray();
		// String startDate = dateArr[0];
		// String endDate = dateArr[1];
		//
		// System.out.println("Margin Generated from " + startDate + "-" +
		// endDate + "=" + totalMargin);

	}

}
