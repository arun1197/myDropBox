import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Don on 5/26/2017 AD.
 */
public class Share {

    //Creates item in Dynamodb
    public static void putItem(String username, String path){
        AmazonDynamoDB dynamoClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion("ap-southeast-1").build();

        DynamoDBMapper mapper = new DynamoDBMapper(dynamoClient);

        ShareLogs item = new ShareLogs();
        item.setUser(username);
        item.setPath(path);
        mapper.save(item);
    }

    //Retrieve item from Dynamodb
    public static List<String> getItem(String username, String path){
        List<String> ans = new ArrayList<>();
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion("ap-southeast-1").build();

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        ShareLogs partitionKey = new ShareLogs();

        partitionKey.setUser(username);
        partitionKey.setPath(path);
        DynamoDBQueryExpression<ShareLogs> queryExpression = new DynamoDBQueryExpression<ShareLogs>()
                .withHashKeyValues(partitionKey);

        List<ShareLogs> itemList = mapper.query(ShareLogs.class,queryExpression);

        for (int i = 0; i < itemList.size(); i++) {
            if(!ans.contains(itemList.get(i).getUser())){
                ans.add(itemList.get(i).getUser());
            }
            if(!ans.contains(itemList.get(i).getPath())){
                ans.add(itemList.get(i).getPath());
            }
//            System.out.println(itemList.get(i).getUser());
//            System.out.println(itemList.get(i).getPath());

        }
        return ans;
    }
}
