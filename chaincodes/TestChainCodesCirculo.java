import java.awt.image.Raster;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

import static org.junit.Assert.assertEquals;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

public class TestChainCodesCirculo extends junit.framework.TestCase{
	private static BufferedImage testImage;
	private static Raster myRaster;
	//Test section
	@BeforeClass
	public static void setupBeforeClass(){
		File testFile = new File("circulo.png");
		try {
			testImage = ImageIO.read(testFile);			
			myRaster = testImage.getData();		
			ChainCodes.imWidth = myRaster.getWidth();
			ChainCodes.imHeight = myRaster.getHeight();
		} catch (IOException ioe){
			System.out.println("e :test image source not found.");
		}
	}

	@Test
	public void testgetWidth(){
		File testFile = new File("circulo.png");
		try {
			testImage = ImageIO.read(testFile);			
			myRaster = testImage.getData();		
			ChainCodes.imWidth = myRaster.getWidth();
			ChainCodes.imHeight = myRaster.getHeight();
		} catch (IOException ioe){
			System.out.println("e :test image source not found.");
		}

		int aux = ChainCodes.getWidth(myRaster);
		assertEquals(20, aux);
	}

	@Test
	public void testgetHeight(){
		int aux = ChainCodes.getHeight(myRaster);
		assertEquals(20, aux);
	}

	@Test
	public void testfirstPoint(){
		int aux = ChainCodes.firstPoint(myRaster);
		assertEquals(823, aux);
	}

	@Test
	public void testchainc(){
		int aux = ChainCodes.chainc(myRaster);
		assertEquals(52, aux);
	}

	@Test
	public void testperimeter(){
		double aux = ChainCodes.perimeter(myRaster);
		boolean flag = (aux > 61.9 && aux < 62.0);
		assertTrue(flag);
	}
}