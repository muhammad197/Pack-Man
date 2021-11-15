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
	public ArrayList<PeckPoints> getBombPoints()
	{
		ArrayList<PeckPoints> bombs = new ArrayList<PeckPoints>();
		bombs.add(new PeckPoints("/src/Photos/apple.jpg"));
		bombs.add(new PeckPoints("/src/Photos/banana.jpg"));
		bombs.add(new PeckPoints("/src/Photos/burger.jpg"));
		bombs.add(new PeckPoints("/src/Photos/cherry.jpg"));
		bombs.add(new PeckPoints("/src/Photos/iceCream.jpg"));
		bombs.add(new PeckPoints("/src/Photos/orange.jpg"));
		bombs.add(new PeckPoints("/src/Photos/strawberry.jpg"));
		return bombs;
	}

}
