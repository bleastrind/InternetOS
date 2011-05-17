package xieyu.jsp.shareonce.kernel;

public class ShareLink {
	private String baseUrl; // The share link url with blank for the link url
	private int urlInsertAt; // The index to insert the link url in front
	private String name; // The name for this share link
	
	/**
	 * Constructor with 2 parameters
	 */
	public ShareLink(String n, String u) {
		this.baseUrl = u;
		this.urlInsertAt = u.length();
		this.name = n;
	}
	
	/**
	 * Given a reference url returns the full share link
	 */
	public String getFullLink(String url) {
		String fullUrl = baseUrl.substring(0, this.urlInsertAt);
		fullUrl += url;
		fullUrl += baseUrl.substring(this.urlInsertAt);
		return fullUrl;
	}
	
	/**
	 * getter for name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * getter for url
	 */
	public String getUrl() {
		return this.baseUrl;
	}
}
