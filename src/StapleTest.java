import java.io.IOException;
import java.util.Scanner;

public class StapleTest {
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		try {
			ComicStapler.staple("C:\\Users\\olive\\Desktop\\merge", keyboard);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
