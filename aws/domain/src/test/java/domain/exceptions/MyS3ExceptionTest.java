package domain.exceptions;

import org.junit.jupiter.api.Test;

public class MyS3ExceptionTest {

	@Test
	public void testコンストラクタ確認() {
		new MyS3Exception(null);
	}
}
