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
	Rodrigo Anes Sena de Araujo 9763064
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
		* Method from the runnable interface.
		* @Return
		*/
		public void run(){
			this.myIndex = searchType ? searchHorizontal() : searchVertical(); 
		}

		/**
		* Constructor of the ChainCodes inner class.
		*/
		SearchPixel(Raster myNewRaster, boolean mySearchType){
			this.searchType = mySearchType;
			this.myRaster = myNewRaster;
		}
	}

	/**
	* Get the image Height.
	* @Return The height of non-null parts of the image.
	*/
	static public int getHeight (Raster myRaster){
		try {
			int aux = 0, result = 0;
			//(x = i/image_height, y = i % image_height)
			for (int i = 0; i < (ChainCodes.imWidth * ChainCodes.imHeight); ++i){
				if (myRaster.getSample(i / ChainCodes.imHeight, i % ChainCodes.imHeight, 0) != __whiteChar)
					++aux;
				else {
					//Get always the largest possible sequence
					result = (aux > result) ? aux : result;
					aux = 0;
				}
			}
			return result;
		} catch (Exception e){
			System.out.println("e: something went wrong on getting image object height.");
		}
		return 0;	
	}

	/**
	* Get the image width.
	* @Return Width of non-null parts of the image.
	*/
	static public int getWidth (Raster myRaster){
		try {
			int aux = 0, result = 0;
			//(x = i % image_width, y = i / image_width)
			for (int i = 0; i < (ChainCodes.imWidth * ChainCodes.imHeight); ++i){
				if (myRaster.getSample(i % ChainCodes.imWidth, i / ChainCodes.imWidth, 0) != __whiteChar)
					++aux;
				else {
					//Get always the largest possible sequence
					result = (aux > result) ? aux : result;
					aux = 0;
				}
			}
			return result;
		} catch (Exception e){
			System.out.println("e: something went wrong on getting image object width.");
		}
		return 0;	
	}

	/**
	* Get the image first pixel.
	* @Return The (up and leftmost) first valid pixel of the image.
	*/
	static public int firstPoint (Raster myRaster){
		//Create thread objects of the inner class
		SearchPixel auxInst0 = new SearchPixel(myRaster, false);
		SearchPixel auxInst1 = new SearchPixel(myRaster, true);

		//Open the threads, for the column-wise and row-wise first pixel search
		Thread vertThread = new Thread(auxInst0, "Vertical Search");
		vertThread.start();
		Thread horzThread = new Thread(auxInst1, "Horizontal Search");
		horzThread.start();

		//Join threads, because both results is needed for the next step
		try {
			vertThread.join();
			horzThread.join();
		} catch (InterruptedException e){
			System.out.println("e: something went wrong on getting firstPoint.");
		}

		//If a thread "returned" a invalid value, return the result of the other thread
		if (auxInst0.myIndex == -1)
			return auxInst1.myIndex;
		else if (auxInst1.myIndex == -1)
			return auxInst0.myIndex;
		else {
			double auxDist0 = 0, auxDist1 = 0;
			try {
				//Check for the pixel with the smallest distance from the origin (0, 0)
				auxDist0 = Math.sqrt(
					Math.pow(auxInst0.myIndex % ChainCodes.imWidth, 2) + 
					Math.pow(auxInst0.myIndex / ChainCodes.imWidth, 2));
				auxDist1 = Math.sqrt(
					Math.pow(auxInst1.myIndex % ChainCodes.imWidth, 2) + 
					Math.pow(auxInst1.myIndex / ChainCodes.imWidth, 2));
			} catch (Exception e){
				System.out.println("e: can't handle arithmetic expression on firstPoint method.");
			} finally {
				//Return the pixel with less distance from the (0, 0) 
				return (auxDist0 < auxDist1 ? auxInst0.myIndex : auxInst1.myIndex);
			}
		}		
	}

	/**
	* Check if (i, j) image pixel is a borderline pixel.
	* @Return true, if image has a 255 pixel adjacent. 
	*/
	static private boolean checkAdjacency(Raster myRaster, int i, int j){
		//Boolean vector to check pixel adjacent other pixels
		boolean [] borderPixel = new boolean[4];

		borderPixel[0] = (myRaster.getSample(i + 1, j, 0) == __whiteChar);
		borderPixel[1] = (myRaster.getSample(i - 1, j, 0) == __whiteChar);
		borderPixel[2] = (myRaster.getSample(i, j + 1, 0) == __whiteChar);
		borderPixel[3] = (myRaster.getSample(i, j - 1, 0) == __whiteChar);

		//Check if current pixel is a border pixel (i.e if there's a adjacent white (255) pixel) 
		for (boolean b : borderPixel)
			if (b) return true;
		return false;
	}

	/**
	* Execute the a simple ChainCodes algorithm and find all the borderline pixels on the image.
	* Works on images with inner holes and separated parts.
	* @Return Number of borderline pixes.
	*/
	public static int chainc(Raster myRaster){
		int result = 0, aux = 0;

		//Travel through the image pixels
		for (int i = 1; i < ChainCodes.imWidth - 1; ++i)
			for (int j = 1; j <  ChainCodes.imHeight - 1; ++j)
				if (myRaster.getSample(i, j, 0) != __whiteChar)
					result += (checkAdjacency(myRaster, i, j) ? 1 : 0);

		return result;
	}

	/**
	* Given an index on the boolean matrix, get the next adjacent borderline pixel,
	* mark the previous one invalid, and calculate de distance between these two pixels.
	* @Return the index of the next valid pixel on the boolean matrix. 
	*/
	private static int getAdjacentValidPixel(int i, boolean [][] imCopy, double [] distance){
		int retVal = i;
		final int x = i % ChainCodes.imWidth;
		final int y = i / ChainCodes.imWidth;

		//Checks for horizontal or vertical adjacency
		if (imCopy[x + 1][y]) retVal = (x + 1) + (y * ChainCodes.imWidth);
		else if (imCopy[x - 1][y]) retVal = (x - 1) + (y * ChainCodes.imWidth);
		else if (imCopy[x][y + 1]) retVal = (x) + ((y + 1) * ChainCodes.imWidth);
		else if (imCopy[x][y - 1]) retVal = (x) + ((y - 1) * ChainCodes.imWidth);
		
		if (retVal != i) {
			//If it's the case, distance + 1
			distance[0] += 1;
			imCopy[x][y] = false;
		} else {
			//If not, then check for diagonal adjacency
			if (imCopy[x + 1][y + 1]) retVal = (x + 1) + ((y + 1) * ChainCodes.imWidth);
			else if (imCopy[x - 1][y - 1]) retVal = (x - 1) + ((y - 1) * ChainCodes.imWidth);
			else if (imCopy[x - 1][y + 1]) retVal = (x - 1) + ((y + 1) * ChainCodes.imWidth);
			else if (imCopy[x + 1][y - 1]) retVal = (x + 1) + ((y - 1) * ChainCodes.imWidth);
			if (retVal != i) {
				//This time add sqrt(2)
				distance[0] += Math.sqrt(2);
				imCopy[x][y] = false;
			}
		}
		//Return the next adjacent pixel, in any
		return retVal;
	}

	/**
	* Get the image outer perimeter (does not consider inner holes on images). 
	* @Return The image outer perimeter.
	*/
	public static double perimeter(Raster myRaster){
		double [] result = new double[1];

		//Get the up leftmost valid pixel on the valid
		final int start = ChainCodes.firstPoint(myRaster);

		if (start > 0){
			//Boolean matrix to "copy" the image on a compact way
			boolean [][] imCopy = new boolean[ChainCodes.imWidth][ChainCodes.imHeight];

			//Check all the image for borderlines pixels, and mark then on the
			//boolean matrix.
			for (int i = 1; i < ChainCodes.imHeight - 1; ++i)
				for (int j = 1; j < ChainCodes.imWidth - 1; ++j)
					if (myRaster.getSample(j, i, 0) != __whiteChar)
						imCopy[j][i] = checkAdjacency(myRaster, j, i);

			/*
			//Debug purposes. This prints the boolean matrix in numeric format.
			for (boolean []b : imCopy){
				for (boolean b2 : b)
					System.out.print((b2) ? 1 : 0);
				System.out.print("\n");
			}
			*/

			int i = getAdjacentValidPixel(start, imCopy, result);
			//The first pixel must be demarked, for optimization on the loop
			imCopy[start % ChainCodes.imWidth][start / ChainCodes.imWidth] = true;
			while (i != start)
				i = getAdjacentValidPixel(i, imCopy, result);

			return result[0];
		} else System.out.print("e: invalid image.");

		return 0.0;
	}

	/**
	* Main function, for testing purposes
	*/
	public static void main (String[] args) {
		System.out.println("system: please insert filepath of the image to process:");

		//Ask user for the image filepath
		Scanner myInput = new Scanner(System.in);
		File myFile = new File(myInput.next());
		BufferedImage myImage = null;

		try {
			//Try to open the imagefile
			myImage = ImageIO.read(myFile);

			if (myImage != null){
				//Get the basics information about the image
				Raster myRaster = myImage.getData();
				ChainCodes.imHeight = myRaster.getHeight();
				ChainCodes.imWidth = myRaster.getWidth();
				int i = ChainCodes.firstPoint(myRaster);

				//Print gathered information
				System.out.println("IMAGE INFORMATION:");

				System.out.println("Full Image height: " + 
					myRaster.getHeight() +
					"\nFull image width: " + 
					myRaster.getWidth());

				//Get the first (up and lefmost) pixel of the image
				System.out.println("First pixel index (column-wise): " + 
					i + " (" + 
					i / myRaster.getWidth()
					+ ", " + 
					i % myRaster.getWidth()
					+ ")");

				//Print the calculated height and width of the image object					
				System.out.println("Object height: " + 
					ChainCodes.getHeight(myRaster) + 
					"\nObject width: " + 
					ChainCodes.getWidth(myRaster));

				//Print the quantity of pixels on the image border
				System.out.println("Borderline pixels: " + 
					ChainCodes.chainc(myRaster));

				//Print the image perimeter
				System.out.printf("Image perimeter: %.2f\n",
					ChainCodes.perimeter(myRaster));

			} else System.out.println("e: can't print image.");
			
		} catch (IOException ioe){
			//Happens when the given path is invalid or permission constraints
			System.out.println("e: something went wrong with the file opening.");
		}
	}
}