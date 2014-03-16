import java.util.*;
import java.math.*;
import java.io.*;
import java.net.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class ProfileMaker {

	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);

		String pathToManifest = sc.nextLine();

		Encoding encoding = retrieveEncodings(pathToManifest);

		int[] segmentSizes;

		segmentSizes = encoding.getSegmentSizes_2s();

		for (int i = 0; i < segmentSizes.length; i++) {
			System.out.print(segmentSizes[i]);
			if (i < (segmentSizes.length - 1)) {
				System.out.print(",");
			}
		}

		System.out.print("\n");

		segmentSizes = encoding.getSegmentSizes_4s();

		for (int i = 0; i < segmentSizes.length; i++) {
			System.out.print(segmentSizes[i]);
			if (i < (segmentSizes.length - 1)) {
				System.out.print(",");
			}
		}

		System.out.print("\n");

		segmentSizes = encoding.getSegmentSizes_8s();

		for (int i = 0; i < segmentSizes.length; i++) {
			System.out.print(segmentSizes[i]);
			if (i < (segmentSizes.length - 1)) {
				System.out.print(",");
			}
		}

		System.out.print("\n");

		segmentSizes = encoding.getSegmentSizes_16s();

		for (int i = 0; i < segmentSizes.length; i++) {
			System.out.print(segmentSizes[i]);
			if (i < (segmentSizes.length - 1)) {
				System.out.print(",");
			}
		}

		sc.close();
	}

	private static Encoding retrieveEncodings(String pathToManifest) {
		Encoding encoding = null;
		boolean found_3000 = false;		

		try {
			File xmlFile = new File(pathToManifest);
			String xmlString = new String("");

			Scanner sc = new Scanner(xmlFile);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();

				if (line.contains("<HMAC>")) {
					continue;
				}
				else {
					xmlString += line + "\n";
				}
			}

			sc.close();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("nccp:videodownloadable");

			for (int temp = 0; temp < nList.getLength(); temp++) {
 
				Node nNode = nList.item(temp);
 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 
					Element eElement = (Element) nNode;
 
					String bitrate = eElement.getElementsByTagName("nccp:bitrate").item(0).getTextContent();
					String url = eElement.getElementsByTagName("nccp:url").item(0).getTextContent();

					if (bitrate.equals("3000")) {
						encoding = new Encoding(bitrate, url);
						found_3000 = true;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR: Unable to retrieve encodings.");
			System.exit(0);
		}

		if (!found_3000) {
			System.out.println("ERROR: No 3000 Kbps bitrate.");
			System.exit(0);
		}

		return encoding;
	}
}
