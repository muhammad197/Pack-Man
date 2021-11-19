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
		bombs.add(new PeckPoints("Photos/apple.png"));
		bombs.add(new PeckPoints("Photos/banana.png"));
		bombs.add(new PeckPoints("Photos/burger.png"));
		bombs.add(new PeckPoints("Photos/cherry.png"));
		bombs.add(new PeckPoints("Photos/iceCream.png"));
		bombs.add(new PeckPoints("Photos/orange.png"));
		bombs.add(new PeckPoints("Photos/strawberry.png"));
		return bombs;
	}

}
