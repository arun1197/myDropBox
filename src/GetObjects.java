import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;

import java.io.File;

/**
 * Created by Don on 5/18/2017 AD.
 */
public class GetObjects {

    //downloads the object to the current directory when the "get" command is used
    public static void getObjects(AmazonS3 s3Client, String bucketName, String key){
        try {
//            System.out.println("Downloading");
            int begin = key.indexOf("/")+1;
            int end  = key.length();
            s3Client.getObject(new GetObjectRequest(bucketName,key), new File("YOUR_FILE_DIRECTORY"+key.substring(begin,end)));
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which" +
                    " means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means"+
                    " the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
