package Model;

import java.util.Objects;

public class PeckPoints {
	
public static int ID=0;
public String image;

/**
 * Class Constructor:
 * @ID- peckPoint ID
 * @image- image for peckPoint
 */
public PeckPoints(String image) {
	super();
	ID=ID+1;
	this.image = image;
}


/**
 * @return the iD
 */
public static int getID() {
	return ID;
}


/**
 * @param iD the iD to set
 */
public static void setID(int iD) {
	ID = iD;
}


/**
 * @return the image
 */
public String getImage() {
	return image;
}


/**
 * @param image the image to set
 */
public void setImage(String image) {
	this.image = image;
}


@Override
public int hashCode() {
	return Objects.hash(image);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	PeckPoints other = (PeckPoints) obj;
	return Objects.equals(image, other.image);
}



}
