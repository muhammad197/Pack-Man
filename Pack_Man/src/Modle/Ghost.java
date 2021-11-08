package Modle;

import java.util.Objects;

public class Ghost {

	public int id;
	public int regularSpeed;
	public int levelUpspeed;
	public String image;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRegularSpeed() {
		return regularSpeed;
	}
	public void setRegularSpeed(int regularSpeed) {
		this.regularSpeed = regularSpeed;
	}
	public int getLevelUpspeed() {
		return levelUpspeed;
	}
	public void setLevelUpspeed(int levelUpspeed) {
		this.levelUpspeed = levelUpspeed;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, image, levelUpspeed, regularSpeed);
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
		return id == other.id && Objects.equals(image, other.image) && levelUpspeed == other.levelUpspeed
				&& regularSpeed == other.regularSpeed;
	}
	public Ghost(int id, int regularSpeed, int levelUpspeed, String image) {
		super();
		this.id = id;
		this.regularSpeed = regularSpeed;
		this.levelUpspeed = levelUpspeed;
		this.image = image;
	}
	


}
