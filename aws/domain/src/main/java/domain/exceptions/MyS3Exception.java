package domain.exceptions;

public class MyS3Exception extends MyAppException {

	public MyS3Exception(String msg) {
		super(msg);
	}
	
	public MyS3Exception(String msg, Exception e) {
		super(msg, e);
	}
}