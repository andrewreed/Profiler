## _Profiler_

A utility to create profiles of Netflix and Amazon videos that can be used in [_dashem_](https://github.com/andrewreed/dashem).

_Profiler_ will generate profiles for 2-, 4-, 8-, and 16-second durations (regardless of a service's default segment duration).

### Instructions

First, install [_Firefox_](http://www.mozilla.org/en-US/firefox/new/) and [_Firebug_](https://getfirebug.com/).

#### Netflix

1. Create an empty XML file.
2. Open Firefox and go to Netflix.
3. Open the Firebug plugin.
4. Play a Netflix video (must be HD).
5. Inside Firebug, right click the line that says __POST authorization__. Select _Copy Response Body_.
6. Paste the response body into the XML file. Save and close the file.
7. Run NetflixProfiler (for this example, assume that the video is _The Hunger Games_):

		java -jar NetflixProfiler.jar > hungergames.txt

8. Type the filename of the XML file and hit enter.
9. Now, use the following command to extract the profiles for the various segment durations:

		./extract hungergames

10. Cut and paste the four profiles into their respective destinations (e.g. /var/www/dashemulatordata/netflix_2sec/videos/).
11. Rename the files to remove the trailing "_#".

#### Amazon

1. Open Firefox and go to Amazon Prime Instant Video.
2. Open the Firebug plugin.
3. Play an Amazon video (must be HD).
4. Skip to the final 1-2 minutes of the video.
5. Observe Firebug. Look for the URLs of the form:

		QualityLevels(2000000)/Fragments(video=85505420000)

6. Do not worry about the _QualityLevel_. AmazonProfiler will generate a profile from the 6 Mbps encoding.
7. Once the final segment has been requested, right click on its URL in Firebug and select _Copy Location_.
8. Run AmazonProfiler (for this example, assume that the video is _The Hunger Games_):

		java -jar AmazonProfiler.jar > hungergames.txt

9. Paste the URL and hit enter.
10. Now, use the following command to extract the profiles for the various segment durations:

		./extract hungergames

11. Cut and paste the four profiles into their respective destinations (e.g. /var/www/dashemulatordata/amazon_4sec/videos/).
12. Rename the files to remove the trailing "_#".

__Note:__ AmazonProfiler will take longer than NetflixProfiler because it must connect to the streaming server
and request the _ContentLength_ of each video segment. NetflixProfiler, on the other hand, is able to calculate
the segment sizes from the header of the 3 Mbps .ismv.

### Credit / Copying

As a work of the United States Government, _Profiler_ is 
in the public domain within the United States. Additionally, 
Andrew Reed waives copyright and related rights in the work 
worldwide through the CC0 1.0 Universal public domain dedication 
(which can be found at http://creativecommons.org/publicdomain/zero/1.0/).
