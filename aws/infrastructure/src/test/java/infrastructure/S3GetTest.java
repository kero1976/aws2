package infrastructure;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.exceptions.MyS3Exception;
import domain.repositories.IS3GetRepository;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

public class S3GetTest {

	@Test
	public void test正常系() throws Exception {
		try(S3Get s3 = new S3Get()){
			var data = s3.GetBytes("kero20220320a", "sample.txt");
			String s = new String(data,"SJIS");
			System.out.println(s);
		}
	}
	
	@Test
	public void test異常系_Key無し() throws Exception {
		assertThatThrownBy(() -> {
	        IS3GetRepository s3 = new S3Get();
	        s3.GetBytes("kero20220320a", "sample2.txt");
	    }).isInstanceOfSatisfying(MyS3Exception.class, e -> {
	        assertThat(e.getMessage()).startsWith("key:[sample2.txt]が見つかりませんでした。");
	    });
	}
	
	@Test
	public void test異常系_バケット無し() throws Exception {
		assertThatThrownBy(() -> {
	        IS3GetRepository s3 = new S3Get();
	        s3.GetBytes("kero20220320b", "sample2.txt");
	    }).isInstanceOfSatisfying(MyS3Exception.class, e -> {
	        assertThat(e.getMessage()).startsWith("Bucket:[kero20220320b]が見つかりませんでした。");
	    });
	}
	
	@Test
	public void test異常系_接続不可() throws Exception {
		assertThatThrownBy(() -> {
			AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("ABC", "XYZ"));
	        IS3GetRepository s3 = new S3Get(Region.AP_NORTHEAST_1, credentialsProvider);
	        s3.GetBytes("kero20220320a", "sample.txt");
	    }).isInstanceOfSatisfying(MyS3Exception.class, e -> {
	        assertThat(e.getMessage()).startsWith("接続に失敗しました");
	    });
	}
}
