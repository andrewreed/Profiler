## _ProfileMaker_

A utility to create profiles of Netflix videos that can be used in [_dashem_](https://github.com/andrewreed/dashem).

_ProfileMaker_ uses a video's manifest to obtain the URLs for each bitrate encoding, but only 
downloads the header of the 3000 Kbps encoding's .ismv. This header is then used to 
calculate the segment sizes for the standard segment duration (4 seconds), as well as the segment 
sizes if Netflix were to use 2, 8, and 16 second segments.

### Instructions

1. Create an empty XML file.
2. Install the Firebug plugin for Firefox.
3. Open Firefox and go to Netflix.
4. Open the Firebug plugin.
5. Play a Netflix video.
6. Inside Firebug, right click the line that says __POST authorization__. Select _Copy Response Body_.
7. Paste the response body into the XML file. Save and close the file.
8. Run ProfileMaker (for this example, assume that the video is _The Hunger Games_):

		java -jar ProfileMaker.jar > hungergames.txt

9. Type the filename of the XML file and hit enter.
10. Now, use the following commands to extract the profiles for the various segment durations:

		sed -n '1p' hungergames.txt | tr ',' '\n' > hungergames_2.txt
		sed -n '2p' hungergames.txt | tr ',' '\n' > hungergames_4.txt
		sed -n '3p' hungergames.txt | tr ',' '\n' > hungergames_8.txt
		sed -n '4p' hungergames.txt | tr ',' '\n' > hungergames_16.txt

11. Cut and paste the four profiles into their respective destinations (e.g. /var/www/dashemulatordata/netflix_2sec/videos/).
12. Rename the files to remove the trailing "_#".

### Credit / Copying

As a work of the United States Government, _ProfileMaker_ is 
in the public domain within the United States. Additionally, 
Andrew Reed waives copyright and related rights in the work 
worldwide through the CC0 1.0 Universal public domain dedication 
(which can be found at http://creativecommons.org/publicdomain/zero/1.0/).
