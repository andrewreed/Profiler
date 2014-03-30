import java.io.*;
import java.util.*;
import java.net.*;

public class Encoding {
	// Amazon Segment Duration
	private static final int SEGMENT_DURATION = 20020000;

	// HTTP headers for a valid GET request to Amazon
	private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	private static final String ACCEPT_ENCODING = "gzip, deflate";
	private static final String ACCEPT_LANGUAGE = "en-US,en;q=0.5";
	private static final String CONNECTION = "keep-alive";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; rv:27.0) Gecko/20100101 Firefox/27.0";

	private long lastSegment;
	private String urlOfIsm;
	private String host;
	private List<Integer> segmentSizes = new LinkedList<Integer>();
	private int[] segmentSizes_2s, segmentSizes_4s, segmentSizes_8s, segmentSizes_16s;

	// Constructor
	public Encoding(String url) {
		parseUrl(url);
		getDefaultSegmentSizes();
		calculateAllSegmentSizes();
	}

	public int[] getSegmentSizes_2s() {
		return segmentSizes_2s;
	}

	public int[] getSegmentSizes_4s() {
		return segmentSizes_4s;
	}

	public int[] getSegmentSizes_8s() {
		return segmentSizes_8s;
	}

	public int[] getSegmentSizes_16s() {
		return segmentSizes_16s;
	}

	private void parseUrl(String url) {
		String afterEquals = url.split("=")[1];
		lastSegment = Long.parseLong(afterEquals.substring(0, (afterEquals.length()-1)));

		host = url.split("/")[2];

		int indexOfIsm = url.indexOf(".ism");
		urlOfIsm = url.substring(0, (indexOfIsm+4));
	}

	private void getDefaultSegmentSizes() {
		Long currentTime = 0L;

		URL url;
		HttpURLConnection conn;

		while (currentTime <= lastSegment) {
			try {
				url = new URL(urlOfIsm + "/QualityLevels(6000000)/Fragments(video=" + currentTime +")");

				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", ACCEPT);
				conn.setRequestProperty("Accept-Encoding", ACCEPT_ENCODING);
				conn.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
				conn.setRequestProperty("Connection", CONNECTION);
				conn.setRequestProperty("Host", host);
				conn.setRequestProperty("User-Agent", USER_AGENT);

				segmentSizes.add(conn.getContentLength());

				conn.disconnect();

				currentTime += SEGMENT_DURATION;

			} catch (Exception e) {
				System.out.println("ERROR: Unable to retrieve segment sizes.");
				System.exit(0);
			}
		}
	}

	private void calculateAllSegmentSizes() {
		int numSegments = segmentSizes.size();

		segmentSizes_2s = new int[numSegments];
		segmentSizes_4s = new int[(int)Math.ceil(numSegments / 2.0)];
		segmentSizes_8s = new int[(int)Math.ceil(numSegments / 4.0)];
		segmentSizes_16s = new int[(int)Math.ceil(numSegments / 8.0)];

		int i = 0;

		for (int size : segmentSizes) {
			segmentSizes_2s[i] += size;
			segmentSizes_4s[i/2] += size;
			segmentSizes_8s[i/4] += size;
			segmentSizes_16s[i/8] += size;

			i++;
		}
	}
}
