package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Model.Board;
import Model.BombPoints;
import Utils.Direction;
import Utils.GameState;
import Model.PeckPoints;
import Model.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;

public class BoardControl implements Initializable {
	private Stage window ;
	private Scene scene ;
	
	protected static GameState status;
	
	private boolean down, up, left, right, keyActive, pause, resume, start;
	private int index_row_packMan, index_column_packMan;
	
	/**
	 * Variable to control PackMan speed
	 */
	private int RegularSpeed;
	
	private AnimationTimer time;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox hbox;

    @FXML
    private Pane pane;
    int[][] matrix1 = Board.getInstance().matrixBoard_level1;
    int [][] matrix= Board.getInstance().putRandomQuestion(matrix1);
    int ObjectSize=30;
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pane.setStyle("-fx-background-color : black") ;
		fillBoard();
		up = down = right = left = pause = resume = start = false;
		keyActive = true;
		RegularSpeed = 10;
		status = GameState.Running;
		resume();
		pressedKeys(pane);

	}
	
	/**
	 * Method to fill the board 
	 */
	private void fillBoard() {
	    int thisRow=0;
	    int thisColoum=0;
	    
		for(int i=0; i<21; i++)
		{
			for(int j=0;j<21;j++) {
				
				// update the walls on the board
				if(matrix[i][j]==1)
				{
					Rectangle wall = new Rectangle(thisRow, thisColoum, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
					wall.setFill(Color.web("#191970")) ;
					wall.setStroke(Color.CORNFLOWERBLUE) ;
					wall.setStrokeWidth(2.0) ;
					pane.getChildren().add(wall) ;
				}
				
				// update the points on the board 
				if(matrix[i][j] == 0)
				{
					Circle peckPoint = new Circle() ; // pass in x, y, width and height
					peckPoint.setCenterX(thisRow+15);  
					peckPoint.setCenterY(thisColoum+15);  
					peckPoint.setRadius(4); 
					peckPoint.setFill(Color.web("#E4CB18"));
					pane.getChildren().add(peckPoint) ;

				}
				
				// update the bomb points on the board 
				if(matrix[i][j] == 2)
				{
					/**
					 * Getting random photo 
					 */
					BombPoints bomb=new BombPoints("");
					int index = (int)(Math.random() * bomb.getBombPoints().size());
					ImageView imageView = new ImageView(bomb.getBombPoints().get(index).getImage());
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				
				// update the questions on the board
				if(matrix[i][j] == 3)
				{
					/**
					 * Getting random photo 
					 */
					Question questin=new Question();
					int index = (int)(Math.random() * questin.getPointsQuestions().size());
					ImageView imageView = new ImageView(questin.getPointsQuestions().get(index).getImage());
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				
				// update the  Pack-Man on the board
				if(matrix[i][j] == 4)
				{
					ImageView imageView = new ImageView("Photos/packMan.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;
					index_row_packMan= i;
					index_column_packMan=j;

				}
				// update the ghost son the board 
				if(matrix[i][j] == 5)
				{
					ImageView imageView = new ImageView("Photos/ghost_blue.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				if(matrix[i][j] == 6)
				{
					ImageView imageView = new ImageView("Photos/ghost_red.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				if(matrix[i][j] == 7)
				{
					ImageView imageView = new ImageView("Photos/ghost_pink.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				if(matrix[i][j] == 8)
				{
					ImageView imageView = new ImageView("Photos/ghost_orange.png");
					imageView.setFitHeight(30);
					imageView.setFitWidth(30);
					imageView.setX(thisRow);
					imageView.setY(thisColoum);
					pane.getChildren().add(imageView) ;

				}
				
				thisRow+=30;
			}
			thisColoum+=30;
			thisRow=0;
		}
	}
	
	private void pressedKeys(Pane pane)
	{
		( pane.getScene()).setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				// TODO Auto-generated method stub
				switch (key.getCode()) {
			      case UP:
			      {
			    	  if(status== GameState.Running)
			    	  {
			    		  System.out.println("click on escape");
			    	  }
			      }
			      case DOWN:
			      {
			    	  if(status== GameState.Running)
			    	  {
			    		  System.out.println("click on escape");
			    	  }
			      }
			      case LEFT:
			      {
			    	  if(status== GameState.Running)
			    	  {
			    		  System.out.println("click on escape");
			    	  }
			      }
			      case RIGHT:
			      {
			    	  if(status== GameState.Running)
			    	  {
			    		  System.out.println("click on escape");
			    	  }  
			      }
			      default:
						break;
			}
		}
	}
		);
		
	}
	private void resume()
	{
		time= new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				
			
				
			}
			
			
			
		};
		
	}

}
