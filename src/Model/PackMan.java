package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PackMan {
	
	public Location CurrentLocation;
	public Color color;
	
	
	public PackMan(Location currentLocation, Color color) {
		super();
		CurrentLocation = currentLocation;
		this.color=color;
	}
	public Location getCurrentLocation() {
		return CurrentLocation;
	}
	public void setCurrentLocation(Location currentLocation) {
		CurrentLocation = currentLocation;
	}
	/**
	 * Definition of: pack-man face 
	 */
	public static final String packManFace = "/src/Photos/packMan.png";
	
	/**
	 * Definition of: pack-man back
	 */
	public static final String packManBack = "/src/Photos/packManBack.png";
	
	/**
	 * Definition of: pack-man eat left
	 */
	public static final String packManEatLeft = "/src/Photos/packManEatLeft.png";
	
	/**
	 * Definition of: pack-man eat right 
	 */
	public static final String packManEatRight = "/src/Photos/packManEatRight.png";
	
	/**
	 * Definition of: pack-man left 
	 */
	public static final String packManLeft = "/src/Photos/packManLeft.png";
	
	/**
	 * Definition of: pack-man right 
	 */
	public static final String packManRight = "/src/Photos/packManRight.png";

	/**
	 * Get pack-man face
	 */
	public static String getPackmanface() {
		return packManFace;
	}
	/**
	 * Get pack-man back
	 */
	public static String getPackmanback() {
		return packManBack;
	}
	/**
	 * Get pack-man eat left
	 */
	public static String getPackmaneatleft() {
		return packManEatLeft;
	}
	/**
	 * Get pack-man eat right image
	 */
	public static String getPackmaneatright() {
		return packManEatRight;
	}
	/**
	 * Get pack-man left
	 */
	public static String getPackmanleft() {
		return packManLeft;
	}
	/**
	 * Get pack-man right 
	 */
	public static String getPackmanright() {
		return packManRight;
	}
	
	

}
