package domain.repositories;

import java.io.Closeable;

import domain.exceptions.MyAppException;

public interface IS3GetRepository extends Closeable{


	/**
	 * S3からデータをデータをbyte[]で取得する.
	 * @param bucketName バケット名
	 * @param keyName キー名
	 * @return データ
	 * @throws MyAppException S3の例外
	 */
	byte[] GetBytes(String bucketName, String keyName) throws MyAppException;
}