package csv;

import java.util.ArrayList;
import java.util.Arrays;

public class CSVHelper {

	// ///////////////////////
	// Protected methods //
	// ///////////////////////

	protected static String[] ArrayListToStringArray(ArrayList<String> a) {
		return (String[]) a.toArray();
	}

	protected static ArrayList<String> StringArrayToArrayList(String[] s) {
		return new ArrayList<String>(Arrays.asList(s));
	}

	protected static String join(String[] row, String separator) {
		String joinedString = "";
		for (int i = 0; i < row.length; i++) {
			String s = row[i];
			joinedString += s;
			if (i < row.length - 1)
				joinedString += separator;
		}
		return joinedString;
	}

	protected static String join(ArrayList<String> row, String separator) {
		String[] arr = row.toArray(new String[row.size()]);
		return join(arr, separator);
	}

	protected static String join(CSVRecord record, String separator) {
		return join(record.getRow(), separator);
	}

	protected static boolean checkEquality(String a, String b) {
		return (a != null && b != null && a.trim().equalsIgnoreCase(b.trim()));
	}

}
