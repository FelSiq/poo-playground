import java.awt.image.Raster;
import java.awt.image.BufferedImage;
import java.awt.Image;

import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;

import java.util.Scanner;

import java.lang.Object;
import java.lang.Thread;

/*
	Felipe Alves Siqueira 9847706
	Rodrigo Sena de Araújo
*/

/**
* Main class, with all functions on documentation implemented.
*/
public class ChainCodes{
	public static final int __whiteChar = 255;
	protected static int imHeight = 0;
	protected static int imWidth = 0;

	/**
	* Inner class for parallel first pixel search. Not really necessary
	* on the presented test cases, as they're symmetric (so, searching 
	* the first pixel row-wise or column-wise does not matter). However,
	* on assymetric images, both search types may be needed to find the
	* correct pixel.
	*/
	private static class SearchPixel implements Runnable{
		private static boolean searchType = false;
		private static Raster myRaster = null;
		public static int myIndex = -1;

		/**
		* Search the first non-empty pixel of the image, column-wise
		* @Return the index (x = i % width, y = i / width) of the first pixel found column-wise
		*/
		private int searchHorizontal(){
			for (int i = 0; i < (ChainCodes.imWidth * ChainCodes.imHeight); ++i)
				if (myRaster.getSample(i % ChainCodes.imWidth, i / ChainCodes.imWidth, 0) != __whiteChar)
					return i;
			return -1;
		} 

		/**
		* Search the first non-empty pixel of the image, row-wise
		* @Return the index (x = i % width, y = i / width) of the first pixel found row-wise
		*/
		private int searchVertical(){
			for (int i = 0; i < ChainCodes.imWidth; ++i)
				for (int j = 0; j < ChainCodes.imHeight; ++j)
					if(myRaster.getSample(j, i, 0) != __whiteChar)
						return i + (j * ChainCodes.imHeight);
			return -1;
		} 

		/**
		*
		* @Return
		*/
		public void run(){
			this.myIndex = searchType ? searchHorizontal() : searchVertical(); 
		}

		/**
		*
		*/
		SearchPixel(Raster myNewRaster, boolean mySearchType){
			this.searchType = mySearchType;
			this.myRaster = myNewRaster;
		}
	}

	/**
	*
	* @Return
	*/
	static public int getHeight (Raster myRaster){
		try {
			int aux = 0, result = 0;
			for (int i = 0; i < (ChainCodes.imWidth * ChainCodes.imHeight); ++i){
				if (myRaster.getSample(i / ChainCodes.imHeight, i % ChainCodes.imHeight, 0) != __whiteChar)
					++aux;
				else {
					result = (aux > result) ? aux : result;
					aux = 0;
				}
			}
			return result;
		} catch (Exception e){
			System.out.println("e: something went wrong on getting height.");
		}
		return 0;	
	}

	/**
	*
	* @Return
	*/
	static public int getWidth (Raster myRaster){
		try {
			int aux = 0, result = 0;

			for (int i = 0; i < (ChainCodes.imWidth * ChainCodes.imHeight); ++i){
				if (myRaster.getSample(i % ChainCodes.imWidth, i / ChainCodes.imWidth, 0) != __whiteChar)
					++aux;
				else {
					result = (aux > result) ? aux : result;
					aux = 0;
				}
			}
			return result;
		} catch (Exception e){
			System.out.println("e: something went wrong on getting width.");
		}
		return 0;	
	}

	/**
	*
	* @Return
	*/
	static public int firstPoint (Raster myRaster){
		SearchPixel auxInst0 = new SearchPixel(myRaster, false);
		SearchPixel auxInst1 = new SearchPixel(myRaster, true);

		Thread vertThread = new Thread(auxInst0, "Vertical Search");
		vertThread.start();
		Thread horzThread = new Thread(auxInst1, "Horizontal Search");
		horzThread.start();

		try {
			vertThread.join();
			horzThread.join();
		} catch (InterruptedException e){
			System.out.println("e: something went wrong on getting firstPoint.");
		}

		if (auxInst0.myIndex == -1)
			return auxInst1.myIndex;
		else if (auxInst1.myIndex == -1)
			return auxInst0.myIndex;
		else {
			double auxDist0 = 0, auxDist1 = 0;
			try {
				auxDist0 = Math.sqrt(
					Math.pow(auxInst0.myIndex % ChainCodes.imWidth, 2) + 
					Math.pow(auxInst0.myIndex / ChainCodes.imWidth, 2));
				auxDist1 = Math.sqrt(
					Math.pow(auxInst1.myIndex % ChainCodes.imWidth, 2) + 
					Math.pow(auxInst1.myIndex / ChainCodes.imWidth, 2));
			} catch (Exception e){
				System.out.println("e: can't handle arithmetic expression on firstPoint method.");
			} finally {
				return (auxDist0 < auxDist1 ? auxInst0.myIndex : auxInst1.myIndex);
			}
		}		
	}

	/**
	*
	* @Return
	*/
	public static int chainc(Raster myRaster){
		int result = 0, aux = 0;
		boolean [] borderPixel = new boolean[4];

		for (int i = 1; i < ChainCodes.imWidth - 1; ++i){
			for (int j = 1; j <  ChainCodes.imHeight - 1; ++j){
				if (myRaster.getSample(i, j, 0) != __whiteChar){
					borderPixel[0] = (myRaster.getSample(i + 1, j, 0) == __whiteChar);
					borderPixel[1] = (myRaster.getSample(i - 1, j, 0) == __whiteChar);
					borderPixel[2] = (myRaster.getSample(i, j + 1, 0) == __whiteChar);
					borderPixel[3] = (myRaster.getSample(i, j - 1, 0) == __whiteChar);

					for (boolean b : borderPixel)
						if (b){
							++result;
							break;
						}
				}
			}
		}
		return result;
	}

	/**
	*
	* @Return
	*/
	public static void main (String[] args) {
		System.out.println("system: please type the image filepath to process:");

		Scanner myInput = new Scanner(System.in);
		File myFile = new File(myInput.next());
		BufferedImage myImage = null;

		try {
			myImage = ImageIO.read(myFile);
			Raster myRaster = myImage.getData();
			ChainCodes.imHeight = myRaster.getHeight();
			ChainCodes.imWidth = myRaster.getWidth();

			if (myImage != null){
				int i = ChainCodes.firstPoint(myRaster);

				System.out.println("IMAGE INFORMATION:");

				System.out.println("Full Image height: " + 
					myRaster.getHeight() +
					"\nFull image width: " + 
					myRaster.getWidth());

				System.out.println("First pixel index: " + 
					i + " (" + 
					i / myRaster.getWidth()
					+ ", " + 
					i % myRaster.getHeight()
					+ ")");
					
				System.out.println("Object height: " + 
					ChainCodes.getHeight(myRaster) + 
					"\nObject width: " + 
					ChainCodes.getWidth(myRaster));

				System.out.println("Borderline pixels: " + 
					ChainCodes.chainc(myRaster));


			} else System.out.println("e: can't print image.");
			
		} catch (IOException ioe){
			System.out.println("e: something went wrong with the file opening.");
		}
	}
}