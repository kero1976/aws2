package infrastructure;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.exceptions.MyAppException;
import domain.exceptions.MyS3Exception;
import domain.repositories.IS3GetRepository;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3Get implements IS3GetRepository {

	private static Logger log = LoggerFactory.getLogger(S3Get.class);
	private S3Client s3;

	public S3Get() {
		this(Region.AP_NORTHEAST_1);
	}

	public S3Get(Region region) {
		log.debug("**********************************************************************************************START - region:{}", region);
		s3 = S3Client.builder().region(region).build();
	}

	public S3Get(Region region, AwsCredentialsProvider credentialsProvider) {
		log.debug("START - region:{}", region);
		s3 = S3Client.builder().region(region).credentialsProvider(credentialsProvider).build();
	}
	
	@Override
	public byte[] GetBytes(String bucketName, String keyName) throws MyAppException {
		log.debug("***********************************************************************************************START - bucketName:{}, keyName:{}", bucketName, keyName);
		try {
			GetObjectRequest objectRequest = GetObjectRequest.builder().key(keyName).bucket(bucketName).build();

			ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
			return objectBytes.asByteArray();

		} catch (NoSuchKeyException e1) {
			log.error("**********************************************************************************************Key:[{}]????????????????????????????????????", keyName);
			throw new MyS3Exception(String.format("key:[%s]????????????????????????????????????", keyName), e1);
		}catch(NoSuchBucketException e2) {
			log.error("**********************************************************************************************Bucket:[{}]????????????????????????????????????", bucketName);
			throw new MyS3Exception(String.format("Bucket:[%s]????????????????????????????????????", bucketName), e2);
		}catch(S3Exception e3) {
			log.error("**********************************************************************************************??????????????????????????????");
			throw new MyS3Exception("??????????????????????????????", e3);
		}
	}

	@Override
	public void close() throws IOException {
		log.debug("**********************************************************************************************START");
		s3.close();
		log.debug("S3???????????????????????????????????????????????????");
	}

}