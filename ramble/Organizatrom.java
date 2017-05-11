import java.util.List;
import java.util.ArrayList;

public class Organizatrom {
	static public ArrayList <Penguim> myBirdList = new ArrayList <Penguim> ();

	static public Bird <Penguim> getAPenguim (List <Penguim> myPenguimFamily, String myPenguimName) {
		try {
			for (int i = 0; i < myPenguimFamily.size(); ++i)
				if (myPenguimFamily.get(i).penguimName.equals(myPenguimName))
					return myPenguimFamily.get(i);
		} catch (NullPointerException npe) {
			System.out.println("This penguim family is empty :( !");
		} finally {
			System.out.println("sys: *random penguim noise*");
		}
		return null;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; ++i)
			Organizatrom.myBirdList.add(new Penguim("A great penguim No." + i));

		Organizatrom.getAPenguim(Organizatrom.myBirdList, "A great penguim No.7").makeNoise();
	}
}