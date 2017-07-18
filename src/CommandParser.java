import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Don on 5/18/2017 AD.
 */
public class CommandParser {
    //Command line parser
    public static void parser() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
        String bucketName = "PUT_YOUR_BUCKET_NAME_HERE";
        Scanner input = new Scanner(System.in);
        boolean quit = false;
        System.out.println("Please input command (newuser username password password, login\n" +
                " username password, put filename filepath, get filename user||fromUser\n" +
                ", share filename toUser, view, or logout).\n" +
                " If you want to quit the program just type quit.");
        while (!quit) {
            String inputLine = input.nextLine();
            String user = new String();
            if(inputLine.equals("quit")){
                quit = true;
                System.out.println("======================================================\n" +
                        " Thank you for using myDropbox.\n" +
                        "See you again!");
            }
            if(inputLine.contains("newuser")){
                String[] hello = inputLine.split(" ");
                DBConnector.addUser(hello[1],hello[3]);
                System.out.println("OK");
            }
            if(inputLine.contains("view") || inputLine.contains("logout") || inputLine.contains("get") || inputLine.contains("put")){
                System.out.println("Please log-in to be able to use these commands.");
            }
            if(inputLine.contains("login")){
                try {
                    String[] login = inputLine.split(" ");
                    if(DBConnector.checkLogin(login[1],login[2])){
                        user = login[1];
                        System.out.println("OK");
                        boolean loggedOut = false;
                        while(!loggedOut){
                            String inCommands = input.nextLine();
                            if(inCommands.contains("put")){
                                String[] put = inCommands.split(" ");
                                AddObjects.addObjectToBucket(s3Client,bucketName, user,put[1],put[2]);
                                System.out.println("OK");
                            }
                            if(inCommands.contains("get")){
                                String[] get = inCommands.split(" ");
                                String key = get[1];
                                String userToGet = get[2];
                                List<String> checkPath = Share.getItem(user,userToGet+"/"+key);
                                if(checkPath.contains(userToGet+"/"+key)){
                                    GetObjects.getObjects(s3Client,bucketName,userToGet+"/"+key);
                                    System.out.println("OK");
                                }else{
                                    System.out.println("Access Denied or file does not exist.");
                                }
                            }
                            if(inCommands.contains("share")){
                                String[] share = inCommands.split(" ");
                                String path = user+"/"+share[1];
                                String userToShare = share[2];
                                if (DBConnector.userExist(userToShare)){
                                    Share.putItem(userToShare,path);
                                    System.out.println("OK");
                                }else{
                                    System.out.println("User does not exist.");
                                }
                            }

                            if(inCommands.equals("view")){
                                System.out.println("OK");
                                List<String> checkPath = Share.getItem(user,"");
                                if (checkPath.size()!=0){
                                    for(String i: checkPath){
                                        ViewObjects.viewObjectsInBucket(s3Client,bucketName,i);
                                    }
                                }
                                ViewObjects.viewObjectsInBucket(s3Client,bucketName,user);
                            }

                            if(inCommands.equals("logout")){
                                System.out.println("OK");
                                loggedOut=true;
                            }
                        }
                    }
                    else System.out.println("Incorrect username or password");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
