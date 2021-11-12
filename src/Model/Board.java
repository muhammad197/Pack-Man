package Model;
import java.util.Objects;

import Model.Ghost;

public class Board {
public String wallspassageWay;
public Ghost ghosts;
public String getWallspassageWay() {
	return wallspassageWay;
}
public void setWallspassageWay(String wallspassageWay) {
	this.wallspassageWay = wallspassageWay;
}
public Ghost getGhosts() {
	return ghosts;
}
public void setGhosts(Ghost ghosts) {
	this.ghosts = ghosts;
}
@Override
public int hashCode() {
	return Objects.hash(ghosts, wallspassageWay);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Board other = (Board) obj;
	return Objects.equals(ghosts, other.ghosts) && Objects.equals(wallspassageWay, other.wallspassageWay);
}
public Board(String wallspassageWay, Ghost ghosts) {
	super();
	this.wallspassageWay = wallspassageWay;
	this.ghosts = ghosts;
}

}
