package domain.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * ベース例外.
 * @author kero
 *
 */
public abstract class MyAppException extends Exception{

	private Exception e;
	private String msg;
	
	public MyAppException(String msg) {
		this(msg, null);
	}
	
	public MyAppException(String msg, Exception e) {
		this.e = e;
		this.msg = msg;
	}
	
	public String getMessage() {
		if(e == null) {
			return msg;
		}
		return String.format("%s, InnerException:%s",msg, e.toString());
	}
	
	public String getStackTraceString() {
		if(e == null) {
			return getStackTraceString(this);
		}else {
			return getStackTraceString(e);
		}
	}
	
	private String getStackTraceString(Exception e) {
		try(StringWriter sw = new StringWriter()){
			try(PrintWriter pw = new PrintWriter(sw)){
				e.printStackTrace(pw);
				pw.flush();
				return sw.toString();
			}
		}catch(Exception e1){
			return e1.toString();
		}
	}
}