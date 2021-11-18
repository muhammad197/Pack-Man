package Model;

import java.util.ArrayList;

public class BombPoints extends PeckPoints {

	public BombPoints(String image) {
		super(image);
		// TODO Auto-generated constructor stub
	}
/**
 * The functions returned the photos of the bomb points
 * @return bombs
 */
	public static ArrayList<PeckPoints> getBombPoints()
	{
		ArrayList<PeckPoints> bombs = new ArrayList<PeckPoints>();
		bombs.add(new PeckPoints("/src/Photos/apple.png"));
		bombs.add(new PeckPoints("/src/Photos/banana.png"));
		bombs.add(new PeckPoints("/src/Photos/burger.png"));
		bombs.add(new PeckPoints("/src/Photos/cherry.png"));
		bombs.add(new PeckPoints("/src/Photos/iceCream.png"));
		bombs.add(new PeckPoints("/src/Photos/orange.png"));
		bombs.add(new PeckPoints("/src/Photos/strawberry.png"));
		return bombs;
	}

}
