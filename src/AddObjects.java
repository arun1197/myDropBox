import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;

/**
 * Created by Don on 5/18/2017 AD.
 */

//Add objects
public class AddObjects {
    //adds object to bucket when the "put" command is used
    public static void addObjectToBucket(AmazonS3 s3Client, String bucketName, String username, String keyName, String filePath) {
        TransferManager tm = new TransferManager(s3Client);
        Upload upload = tm.upload(bucketName, username+"/"+keyName, new File(filePath));
        try {
            // Or you can block and wait for the upload to finish
            try {
                upload.waitForCompletion();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (AmazonClientException amazonClientException) {
            System.out.println("Unable to upload file, upload was aborted.");
            amazonClientException.printStackTrace();
        } finally {
            tm.shutdownNow(false);
        }

    }
}
