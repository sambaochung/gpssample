# gpssample
Sample project with Spring Boot - a GPS sample with H2 database in memory

#@SpringBootApplication Application.java

#For Unit Test 
Execute GPSControllerTest.java

Some sample test case
1.GPSControllerTest.testUploadMaps() 
	Load GPX file into Database 
   Copy sample\sample.gpx into D:\\sample.gpx  or change the location in GPSControllerTest.java
2. GPSControllerTest.testUploadMaps2() 
	Load GPX file into Database 
    Copy sample\sample2.gpx into D:\\sample.gpx  or change the location in GPSControllerTest.java
3. GPSControllerTest.testGetUser()
	Test users information after upload the map
4. testGetLatestPosition()
   Get latest position of user.

5. GPSControllerTest.getUserMaps()
Get List TrackPoint of user   


