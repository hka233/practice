# Web System Development Practice
**【Main Goal】**  
Create a basic web system, from Java project creation in Eclipse to building a JAR and getting it up and running on AWS.

Restrictions:
In order to improve debugging/researching skills, only a limited amount of assistance will be allowed for this project.
The only questions permitted are those pertaining to the project requirements.
The project must be uploaded to a public GitHub repository for inspection. Pushes to the repository must be relatively frequent (not once a week; ideally once a day or more).

**【Instructions】**  
1)	Read the list of development goals below.
2)	Estimate which goals can be reached within a week, and report those estimated goals to the Product Owner.
3)	After confirming the week’s goals, spend the rest of the week developing the project, aiming to reach the designated goal.
4)	Check-in with the PO for a status update. 
5)	Repeat steps 2 - 4 until project has ended.

**【Sub Goals】**  
A)	Implement a barebones local web system (via JSF, JSP, Servlets, Tomcat, etc... Whatever gets the job done, your choice)  
B)	Login system with basic hard-coded credentials. The user will be taken to a login page upon accessing the site and must enter the hard-coded username and password to reach a welcome page. 2FA not required.  
C)	Database connection; logging in with DB (MySQL, PostgreSQL, etc – your choice) user credentials  
D)	JAR build running on an AWS server (free tier)  
＊	Unit testing with ~100% coverage for all goal phases

**【Notes】**  
-	Internal frameworks are not permitted as the project will be uploaded to a public GitHub repository.
-	A free personal GitHub account/AWS account can be used to upload the code and JAR.
-	Be sure to notify the PO of the GitHub project’s public repository details so progress can be tracked.


# Web System Development Practice 2
**【Main Goal】**  
Adding to the web system project, create functions to interact with the database on web pages which logged in users can access.

Restrictions:
This time, you only need to report when you have completed a function, not once every week.
The project must be uploaded to a GitHub repository for inspection. Pushes to the repository must be relatively frequent (not once a week; ideally once a day or more).

**【Major Features】**  
A) Register – A page with an entry form to create new users.

B) Update – A page where the logged in user can change their own information (username/password/etc)

C) Delete – A page which allows the logged in user to delete their own account.

D) Search – A page which displays all the users within the database and their information. Users should be able to search for other users based on set criteria. If you only have usernames/passwords in your database, add at least 2 other columns for additional search parameters.

Bonus points if you add ascending/descending sort options to your page, but not necessary.
Note: All functions should be unit tested with ~100% coverage 

**【Instructions】**  
1) Select a feature to develop and add to your web system (I recommend saving Search for last as it is the most difficult)

2) Spend some time researching how you would like to implement the feature (including any libraries you would like to use), and estimate how long it will take, then send your plans to the PO.

3) After receiving confirmation, develop the feature.

4) Once you are confident that there are little to no bugs/design issues and that you have finished & polished the feature, check-in with the PO for a status update. 

5) Repeat steps 1 - 4 until project has ended and you have developed all features.

**【Notes】**  
If you cannot meet the deadline, contact the PO and explain what problems you encountered that slowed you down.
A major goal of this project is to practice finding bugs and other issues on your own and fixing them before submitting a finished product. 
You can use the same Git repo, make a new one, or add a new branch (your choice).

