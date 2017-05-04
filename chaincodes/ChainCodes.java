import java.lang.Object;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.Raster;

class ChainCodes {

	public int firstPoint(Raster image){
		final int height = myRaster.getHeight();
		final int width = myRaster.getWidth();
		int myRet = -1;

		for (myRet = 0; i < (width * height); ++i)
			if (myRaster.getSample(i / width, i % height, 0) != 255)
				return myRet;
		return -1;
	}

	public static void main(String[] args) {
		Scanner myInput = new Scanner(System.in);
		System.out.println("system: please type the image path to process:");

		File myFile = new File(myInput.next());

		BufferedImage myImage = null;
		try {
			myImage = ImageIO.read(myFile);
			Raster myRaster = myImage.getData();
			if (myImage != null){

 
 				/*
				for (int i = 0; i < (width * height); ++i)
					System.out.print(myRaster.getSample(i / width, i % height, 0));
				*/

				/*
				for (int i = 0; i < height; ++i)
					for (int j = 0; j < width; ++j)
						System.out.print(myRaster.getSample(i, j, 0));
				*/

			} else {
				System.out.println("e: can't print image.");
			}
		} catch (IOException ioe){
			System.out.println("e: something went wrong with the file opening.");
		} 
	}
}