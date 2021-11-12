package Model;

import java.util.Objects;

public class PeckPoints {
public int id;
public String image;
public PeckPoints(int id, String image) {
	super();
	this.id = id;
	this.image = image;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
@Override
public int hashCode() {
	return Objects.hash(id, image);
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
	return id == other.id && Objects.equals(image, other.image);
}
}
