package Model;

import Utils.Color;
import javafx.scene.image.ImageView;

public abstract class GameObject {

	private int id;
	private int speed;
	private ImageView image;
	private Location CurrentLocation;
	private Color color;
	
	
	public GameObject(int id, int speed, ImageView image, Location currentLocation, Color color) {
		super();
		this.id = id;
		this.speed = speed;
		this.image = image;
		CurrentLocation = currentLocation;
		this.color = color;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public ImageView getImage() {
		return image;
	}
	public void setImage(ImageView image) {
		this.image = image;
	}
	public Location getCurrentLocation() {
		return CurrentLocation;
	}
	public void setCurrentLocation(Location currentLocation) {
		CurrentLocation = currentLocation;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}



}
