package csv;

import java.util.ArrayList;

abstract class CSVParser {

	ArrayList<String> headers;

	public CSVParser() {
		headers = new ArrayList<String>();
		setHeaders("id");
	}

	public CSVParser(String... headers) {
		this();
		setHeaders(headers);
	}

	public CSVParser(ArrayList<String> headers) {
		this();
		setHeaders(headers);
	}

	/**
	 * Set the headers for the given table. "id" must be the first header; it
	 * will be set implicitly if it is not the first argument provided.
	 * 
	 * @param headersParams
	 *            all the headers that correspond to each column of the table.
	 * @author Brian Maxwell
	 */
	public void setHeaders(String... headersParams) {
		headers.clear();
		if (headersParams == null)
			setHeaders("id");
		else {
			for (String header : headersParams)
				headers.add(header);
			if (!headers.get(0).equals("id"))
				headers.add(0, "id");
		}
	}

	/**
	 * Set the headers for the given table. "id" must be the first header; it
	 * will be set implicitly if it is not the first argument provided.
	 * 
	 * @param headersParams
	 *            all the headers that correspond to each column of the table.
	 * @author Brian Maxwell
	 */
	public void setHeaders(ArrayList<String> headers) {
		if (!headers.get(0).equals("id"))
			headers.add(0, "id");
		this.headers = headers;
	}

	/**
	 * Add a new header to the existing list of headers.
	 * 
	 * @param header
	 *            the header to add.
	 * @author Brian Maxwell
	 */
	public void addHeader(String header) {
		this.headers.add(header);
	}

	/**
	 * Retrieve the headers set for the current table. Default headers is just
	 * "id"
	 * 
	 * @return ArrayList<String> of all the headers.
	 * @author Brian Maxwell
	 */
	public ArrayList<String> getHeaders() {
		return this.headers;
	}
}




