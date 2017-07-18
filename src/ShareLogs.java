import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Created by Don on 5/26/2017 AD.
 */
//This class will be use with mapper in the Share class to DynamoDB [CRUD OPERATIONS]
@DynamoDBTable(tableName = "YOUR_TABLE_NAME")
public class ShareLogs {
    private String User;
    private String Path;

    @DynamoDBHashKey(attributeName = "User")
    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        this.User = user;
    }

    @DynamoDBRangeKey(attributeName = "Path")
    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        this.Path = path;
    }

}

