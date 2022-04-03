package api;

import java.io.IOException;

import domain.exceptions.MyAppException;
import domain.repositories.IS3GetRepository;
import infrastructure.S3Get;

public class Sample {

	public static void main(String[] args) {
		System.out.println("処理を開始します");
		try(IS3GetRepository s3 = new S3Get()){
			var data = s3.GetBytes("kero20220320a", "sample.txt");
			String s = new String(data,"SJIS");
			System.out.println(s);
		}catch(IOException e) {
			System.out.println("IO Exception");
		} catch (MyAppException e) {
			System.out.println(e.getStackTraceString());
		}
		System.out.println("処理を終了します");
	}

}
