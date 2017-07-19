Once you have this project on your machine, make sure to clean & build the project. This would generate a jar file and store it in the ‘dist’ folder.
The jar file can be found in the ‘dist’ folder.

Important: If you want a jar file that includes all the libraries. 
Go to Files->build.xml-> right click on build.xml-> Run Target -> Other Target -> package-for-store. The jar file will then be stored in the ‘store’ folder. You can use this jar file anywhere since it has all the dependencies that the application needs.

To run this application; enter <java -jar ‘thefile’>

Once you’ve executed this command, you’ll see something like this 


Just follow these commands in order to use the application.

- To register: enter "newuser username password password"

- To login: enter "login username password"

- To upload a file: enter "put filename filepath"

- To download a file: enter "get filename user||fromUser"

- To share a file: eneter "share filename userToShare"

- To view the files: enter "view"

- To logout: enter "logout"

- To quit the application: "quit"


Note
- If you want to change the bucketname, you can do that by going to myDropbox class and change String bucketname = “toyourbucketname”.

- When downloading a file, the file will be saved to the current directory that you’re in; meaning where every you’re running the jar file, the file will be saved in that directory.

- Endpoint, dbname and dbpass can be changed in the DBConnector class. If you want to change it to your db instance, make sure to change it in both the functions; checkLogin and addUser.
