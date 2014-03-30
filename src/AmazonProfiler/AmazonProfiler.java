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

public class AmazonProfiler {

	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);

		String url = sc.nextLine();

		Encoding encoding = new Encoding(url);

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
}
