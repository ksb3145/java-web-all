package common.util;

public class DBTest {
	public static void main(String[] args) {
		NComDB db = new NComDB();
		db.connectDB();
		System.out.println("db연결 성공");
	}
}
