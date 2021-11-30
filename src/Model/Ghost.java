package Model;

import java.util.Objects;

import javafx.scene.image.ImageView;

public class Ghost {

	public int id;
	public int speed;
	public ImageView image;
	public Location CurrentLocation;
	

	public Ghost(int id, int speed, ImageView image, Location currentLocation) {
		super();
		this.id = id;
		this.speed = speed;
		this.image = image;
		CurrentLocation = currentLocation;
	}
	
	
	public Ghost() {
	super();
	}


	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Location getCurrentLocation() {
		return CurrentLocation;
	}
	public void setCurrentLocation(Location currentLocation) {
		CurrentLocation = currentLocation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public ImageView getImage() {
		return image;
	}
	public void setImage(ImageView image) {
		this.image = image;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, image, speed);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ghost other = (Ghost) obj;
		return id == other.id && Objects.equals(image, other.image) && speed== other.speed;
	}
	


}
