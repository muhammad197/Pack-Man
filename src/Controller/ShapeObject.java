package Controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ShapeObject {
	
	/**
	 * 
	 * get the shape object (Rectangle/ Circle) to create the wall or peckPoint
	 */
	public static Shape  getShapeObject(String type, int row, int column,int size) {
		if (type.equals("Rectangle")) // create the wall by rectangle
		{
			Rectangle wall = new Rectangle(row, column, size, size) ; 		
			wall.setFill(Color.web("#191970")) ;
			wall.setStroke(Color.CORNFLOWERBLUE) ;
			wall.setStrokeWidth(2.0) ;
			return wall; 
		}
			
		else if(type.equals("Circle")) // create the peckPoint by circle
		{
			Circle peckPoint = new Circle() ; 
			peckPoint.setCenterX(row+15);  
			peckPoint.setCenterY(column+15);  
			peckPoint.setRadius(4); 
			peckPoint.setFill(Color.web("#E4CB18"));
			return peckPoint;
		}

		return null;
	}

}
