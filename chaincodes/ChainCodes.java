import java.lang.Object;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;

class ChainCodes {
	public static void main(String[] args) {
		Scanner myInput = new Scanner(System.in);

		System.out.println("Please type the image path to process:");

		File myFile = new File(myInput.next());

		BufferedImage myImage = ImageIO.read(myFile);

	}
}