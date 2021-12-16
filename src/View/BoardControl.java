package View;

import java.beans.EventHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Controller.SysData;
import Model.Board;
import Model.BombPoints;
import Model.Game;
import Model.Ghost;
import Model.Location;
import Model.PackMan;
import Model.PeckPoints;
import Model.Question;
import Utils.GameState;
import Utils.Level;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.skin.TextInputControlSkin.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BoardControl implements Initializable {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label namelab;

    @FXML
    private ImageView life3;

    @FXML
    private ImageView life2;

 
    @FXML
    private ImageView life1;

   

    
    @FXML
    private Label scorelab;

    @FXML
    private ImageView stopIcon;

    @FXML
    private Pane pane;

 
	    private PackMan pacmanLocation;
	    private Direction red_movingAt= Direction.UP, blue_movingAt=Direction.UP, pink_movingAt=Direction.LEFT ;

	    //get board from board model( a matrix that describes the board- see Model\Board)
		int[][] matrix1 = Board.getInstance().matrixBoard_level1;
		int[][] matrix = Board.getInstance().putRandomQuestion(matrix1);
		
		//object size on the board is set to 30 
		int ObjectSize=30;
		
		private Timeline timeline,timeline2,timeline3,timeline4;
		
		private Scene scene ;
		
		// save previous and new directions of the pacman
		private Direction newDir=Direction.RIGHT;
		private Direction prevDir=Direction.RIGHT;
		
		KeyFrame pacman_keyFrame;
		
		public boolean levelUp=false;
		public boolean bonusEaten=false;
		public Level level=Level.easy;
		private Game game;
		private ArrayList<Circle> peckpointlist = new ArrayList<Circle>() ;
		private ArrayList<Rectangle> wallList = new ArrayList<Rectangle>() ;
		private ArrayList<ImageView> bonusList = new ArrayList<ImageView>() ;
		private ArrayList<ImageView> packmanMoves = new ArrayList<ImageView>() ;
		private ArrayList<ImageView> questionsPoints = new ArrayList<ImageView>() ;	
		
		private Ghost redGhost= new Ghost();
		private Ghost blueGhost= new Ghost();
		private Ghost pinkGhost= new Ghost();
		
		boolean firstRedGhostMove=true;
		
		/**
		 * Variable to control PackMan speed
		 */
				
		private KeyFrame retrunPeckPoints,ghosts_keyFrame,retrunBombPoints;
		
		protected int ghostSpeed=270;
		
		private String playername= SysData.CurrentPlayer;
		
		protected boolean isbonusUsed=true;
		
				
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			pacmanLocation= new PackMan(new Location(300, 570), Model.Color.yellow);
			namelab.setText(playername);
			pane.setStyle("-fx-background-color : black") ;//set background to black
			fillBoard();
			scorelab.setText("0");
			game=new Game(0, 3, 300, GameState.Started, 0); 
			// o must be the id of the game maching with the history json
			pressedKeys(pane);
		
			
		}
		
		private boolean isWall(Direction theDir, double x_coord, double y_coord)
		{
			int wallSize= ObjectSize;
		
			for(int counter = 0 ; counter < wallList.size() ; counter++)
			{
				double checkX = wallList.get(counter).getX() ;
				double checkY = wallList.get(counter).getY() ;
		
				if(theDir == Direction.UP && checkY < y_coord)
				{
					if((x_coord == checkX || (x_coord < checkX+wallSize && x_coord > checkX) || x_coord== checkX) && y_coord-wallSize <= checkY)
						return true ;
				}
				else if(theDir == Direction.DOWN && checkY > y_coord)
				{
					if((x_coord == checkX || (x_coord < checkX+wallSize && x_coord > checkX) || x_coord== checkX) && y_coord+wallSize >= checkY)
						return true ;
				}
				else if(theDir == Direction.LEFT && checkX < x_coord)
				{
					if(x_coord-wallSize <= checkX && (y_coord == checkY || (y_coord < checkY+wallSize && y_coord > checkY) || y_coord== checkY))
						return true ;
				}
				else if(theDir == Direction.RIGHT && checkX > x_coord)
				{
					if(x_coord+wallSize >= checkX && (y_coord == checkY || (y_coord < checkY+wallSize && y_coord > checkY) || y_coord== checkY))
						return true ;
				}
			}
		
			return false ;
			
			
			
		}
		
		private void movement(Direction newDir) {
			
			if(isWall(newDir, pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn()) == false) {
				prevDir= newDir;
			}
			else
				newDir= prevDir;
			if(isWall(newDir, pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn()) == false) {
		
				if(newDir == Direction.RIGHT)
				{
					if(pacmanLocation.getCurrentLocation().getRow()== 600 && pacmanLocation.getCurrentLocation().getColumn()==300) {
						movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), 0, pacmanLocation.getCurrentLocation().getColumn());
						pacmanLocation.getCurrentLocation().setRow(0);
					}
		
					else {
						movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), pacmanLocation.getCurrentLocation().getRow()+30, pacmanLocation.getCurrentLocation().getColumn());
						pacmanLocation.getCurrentLocation().setRow((pacmanLocation.getCurrentLocation().getRow())+30);
					}
				}
				if(newDir == Direction.LEFT)
				{
					if(pacmanLocation.getCurrentLocation().getRow()== 0 && pacmanLocation.getCurrentLocation().getColumn()==300) {
						movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), 600, pacmanLocation.getCurrentLocation().getColumn());
						pacmanLocation.getCurrentLocation().setRow(600);
					}
					else {
					movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), pacmanLocation.getCurrentLocation().getRow()-30, pacmanLocation.getCurrentLocation().getColumn());
					pacmanLocation.getCurrentLocation().setRow((pacmanLocation.getCurrentLocation().getRow())-30);
					}
				}
					
				
				if(newDir == Direction.UP)
				{
					if(pacmanLocation.getCurrentLocation().getRow()== 90 && pacmanLocation.getCurrentLocation().getColumn()==0) {
						movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), pacmanLocation.getCurrentLocation().getRow(), 600);
						pacmanLocation.getCurrentLocation().setColumn(600);
					}
					else {
					movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn()-30);
					pacmanLocation.getCurrentLocation().setColumn(pacmanLocation.getCurrentLocation().getColumn()-30);
					}
				}
				if(newDir == Direction.DOWN)
				{
					if(pacmanLocation.getCurrentLocation().getRow()== 90 && pacmanLocation.getCurrentLocation().getColumn()==600) {
						movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), pacmanLocation.getCurrentLocation().getRow(), 0);
						pacmanLocation.getCurrentLocation().setColumn(0);
					}
					else {
					movePackman(newDir,pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn(), pacmanLocation.getCurrentLocation().getRow(), pacmanLocation.getCurrentLocation().getColumn()+30);
					pacmanLocation.getCurrentLocation().setColumn(pacmanLocation.getCurrentLocation().getColumn()+30);
					}
				}
			
			}
		
			
		}
		
		private void pressedKeys(Pane pane2) {
			
			pane.setOnMouseMoved(new javafx.event.EventHandler<Event>() {		//get scene 
		
		@Override
		public void handle(Event arg0) {
			if(game.gameState==GameState.Started ) {
				game.setGameState(GameState.Running);
			setScene(pane.getScene());
			stopIcon.setOnMouseClicked(new javafx.event.EventHandler<Event>() {

				@Override
				public void handle(Event arg0) {
					pauseOrUnPauseGame();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Delete Question");
					alert.setHeaderText("Are you sure you want to delete this question?");
					alert.setContentText("QURST");
					ButtonType buttonYes = new ButtonType("Yes", ButtonData.YES);
					ButtonType buttonNo = new ButtonType("No", ButtonData.NO);
					alert.getButtonTypes().setAll(buttonYes, buttonNo);
					Optional<ButtonType> answer = alert.showAndWait();
					if (answer.get().getButtonData() == ButtonData.YES) {
						try {
							pauseOrUnPauseGame();
						} catch (Exception e) {
						}
					}				
				}
			});
			scene.setOnKeyPressed(new javafx.event.EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					
					if(game.gameState==GameState.Running) {
					System.out.println("Pressed");
				switch(event.getCode()) 
				{
					case UP : newDir = Direction.UP ;
							  break ;
		
					case DOWN : newDir = Direction.DOWN ;
								break ;
		
					case LEFT : newDir = Direction.LEFT ;
								break ;
		
					case RIGHT : newDir = Direction.RIGHT ;
							 break ;
				}
				}
			}
		});
		
		/*
		pane.setOnMouseClicked(new javafx.event.EventHandler<Event>() {
		
			@Override
			public void handle(Event arg0) {
				if(game.gameState==GameState.Paused) {
					game.gameState=GameState.Running;
					movePackmanAtSpeed();
					moveGhostAtSpeed();
		
				}
				else if(game.gameState==GameState.Running) {
					game.gameState=GameState.Paused;
					pauseGame();
			}
				
			}
		});
		*/
		if(game.gameState==GameState.Running) {
			System.out.println("PROBLEM");
						movePackmanAtSpeed();
						moveGhostAtSpeed();
					}
		
		
				}
					
					
					
				}
			
					});}	
		
		
		public void moveGhostAtSpeed() {
			
			ghosts_keyFrame = new KeyFrame(Duration.millis(ghostSpeed), e->
			{
			 movePink();
			 moveRed();
			 moveBlue();
			 
			});
			timeline3 = new Timeline(ghosts_keyFrame) ;
			timeline3.setCycleCount(Timeline.INDEFINITE) ;
			timeline3.play() ;
			
		}
		
				
		public void moveGhost(Direction newDir, double d, double e, Ghost ghost)
			{
				
				double toX=d, toY=e;
				if(newDir == Direction.RIGHT)
					toX+=30;
				else if(newDir == Direction.LEFT)
					toX-=30;
				else if(newDir == Direction.UP)
					toY-=30;
				else if(newDir == Direction.DOWN)
					toY+=30;
				ImageView imageView= new ImageView();
				if(ghost.getId()==2)
					imageView = new ImageView("Photos/ghost_red.png");
				if(ghost.getId()==3)
					imageView = new ImageView("Photos/ghost_pink.png");
				if(ghost.getId()==1)
					imageView = new ImageView("Photos/ghost_blue.png");
				imageView.setFitHeight(30);
				imageView.setFitWidth(30);
				imageView.setX(toX);
				imageView.setY(toY);
				pane.getChildren().remove(ghost.getImage()) ;	
				pane.getChildren().add(imageView) ;
				if(ghost.getId()==2) {
					redGhost.getCurrentLocation().setRow((int) toX);
					redGhost.getCurrentLocation().setColumn((int) toY);
					redGhost.setImage(imageView);
				}
				if(ghost.getId()==3) {
					pinkGhost.getCurrentLocation().setRow((int) toX);
					pinkGhost.getCurrentLocation().setColumn((int) toY);
					pinkGhost.setImage(imageView);
				}
				if(ghost.getId()==1) {
					blueGhost.getCurrentLocation().setRow((int) toX);
					blueGhost.getCurrentLocation().setColumn((int) toY);
					blueGhost.setImage(imageView);
				}
			}

		
		
		
		public void movePackman(Direction dir,int fromX, int fromY, int toX, int toY)
		{
			boolean isQuestion=false;
			
			for(int m = 0 ; m < questionsPoints.size() ; m++)
			{
				if((questionsPoints.get(m).getX()== toX) && (questionsPoints.get(m).getY() == toY))
				{
					isQuestion=true;
					pane.getChildren().remove(questionsPoints.get(m)) ;
					questionsPoints.remove(m) ;
					Question q= SysData.getInstance().getQuestions().get(0);
					pauseOrUnPauseGame();
					AnchorPane anchorpane= new AnchorPane();
					anchorpane.setLayoutX(151);
					anchorpane.setLayoutY(135);
					anchorpane.setPrefWidth(400);
					anchorpane.setPrefHeight(334);
					anchorpane.setStyle("-fx-background-color: #ffffff");
					Label question = new Label(q.getQuestion());
					question.setLayoutX(23);
					question.setLayoutY(30);
					question.setPrefWidth(314);
					question.setPrefHeight(27);
					CheckBox ans1= new CheckBox(q.getAnswers().get(0).getContent());
					ans1.setLayoutX(29);
					ans1.setLayoutY(66);
					ans1.setPrefWidth(322);
					ans1.setPrefHeight(41);
					
					CheckBox ans2= new CheckBox(q.getAnswers().get(1).getContent());
					ans2.setLayoutX(29);
					ans2.setLayoutY(106);
					ans2.setPrefWidth(322);
					ans2.setPrefHeight(41);
					
					CheckBox ans3= new CheckBox(q.getAnswers().get(2).getContent());
					ans3.setLayoutX(29);
					ans3.setLayoutY(146);
					ans3.setPrefWidth(322);
					ans3.setPrefHeight(41);
					
					CheckBox ans4= new CheckBox(q.getAnswers().get(3).getContent());
					ans4.setLayoutX(29);
					ans4.setLayoutY(186);
					ans4.setPrefWidth(322);
					ans4.setPrefHeight(41);
					
					Button submit= new Button("Submit Answer");
					submit.setLayoutX(95);
					submit.setLayoutY(246);
					anchorpane.getChildren().add(question);
					anchorpane.getChildren().add(ans1);
					anchorpane.getChildren().add(ans2);
					anchorpane.getChildren().add(ans3);
					anchorpane.getChildren().add(ans4);
					anchorpane.getChildren().add(submit);

					pane.getChildren().add(anchorpane);
					submit.setOnMouseClicked(new javafx.event.EventHandler<Event>() {
						@Override
						public void handle(Event arg0) {
							pane.getChildren().remove(anchorpane);
							pauseOrUnPauseGame();
							
						}
					});

				}
			
			}
			
			for(int n = 0 ; n < peckpointlist.size() ; n++)
			{
				if((peckpointlist.get(n).getCenterX()-15)== toX && (peckpointlist.get(n).getCenterY()-15)== toY)
				{
					pane.getChildren().remove(peckpointlist.get(n)) ;
					peckpointlist.remove(n) ;
					game.setScore(game.getScore()+1);
					scorelab.setText(String.valueOf(game.getScore()));
					updateLevel();
		
				}
				
			}
			
			
			for(int b = 0 ; b < bonusList.size() ; b++)
			{
				if((bonusList.get(b).getX()== toX) && (bonusList.get(b).getY() == toY))
				{
					pane.getChildren().remove(bonusList.get(b)) ;
					bonusList.remove(b) ;
					game.setScore(game.getScore()+1);
					scorelab.setText(String.valueOf(game.getScore()));
					updateLevel();
					bonusEaten=true;
					isbonusUsed=false;
				}
				
		
			}
			for(int n = 0 ; n < packmanMoves.size() ; n++)
			{
				if((packmanMoves.get(n).getX())== fromX && (packmanMoves.get(n).getY())== fromY )
				{
					pane.getChildren().remove(packmanMoves.get(n)) ;
					packmanMoves.remove(n) ;
				}
			}
			ImageView imageView =new ImageView();
			if(bonusEaten==true) {
				
			//return bombPoints 30 seconds after eating them
			retrunBombPoints = new KeyFrame(Duration.millis(30000), e->
			{
				
				BombPoints bomb=new BombPoints("");
						int index = (int)(Math.random() * bomb.getBombPoints().size());
						ImageView imageView2 = new ImageView(bomb.getBombPoints().get(index).getImage());
						imageView2.setFitHeight(30);
						imageView2.setFitWidth(30);
						imageView2.setX(toX);
						imageView2.setY(toY);
						
		
						boolean toadd= true;
						for(int n = 0 ; n < bonusList.size() ; n++)
						{
							if((bonusList.get(n).getX())== toX && (bonusList.get(n).getY())== toY)
									toadd=false;
						}
						
						if(toadd == true) {
						pane.getChildren().add(imageView2) ;
						bonusList.add(imageView2) ;
						}
						
						
						
					});
					timeline4 = new Timeline(retrunBombPoints) ;
					timeline4.setCycleCount(Timeline.INDEFINITE) ;
					timeline4.play() ;
					
			
			pauseGhost();
		
			if(pacmanLocation.color == Model.Color.yellow) {
					pacmanLocation.color= Model.Color.green;	
					
			}
			else if(pacmanLocation.color== Model.Color.green)
			{
				pacmanLocation.color= Model.Color.yellow;
		
			}
		
		}
			
			if(pacmanLocation.color==Model.Color.yellow) {
				imageView = new ImageView("Photos/packMan.png");
		if(dir==Direction.LEFT)
			imageView= new ImageView("Photos/packManLeft.png");
		else if(dir==Direction.RIGHT)
			imageView= new ImageView("Photos/packManRight.png");
		}
		else {
			imageView = new ImageView("Photos/pacManGreen.png");
		if(dir==Direction.LEFT)
			imageView= new ImageView("Photos/pacManGreenLeft.png");
		else if(dir==Direction.RIGHT)
			imageView= new ImageView("Photos/PacManGreenRight.png");
				}
				bonusEaten=false;
				if(isQuestion==false) {
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(toX);
			imageView.setY(toY);
			pane.getChildren().add(imageView) ;
			packmanMoves.add(imageView) ;
			returnPeckPoints(fromX,fromY);
				}
			
		
		}
		
		private void returnPeckPoints(int fromX, int fromY) {
			//return peckPoints 30 seconds after eating them
			retrunPeckPoints = new KeyFrame(Duration.millis(30000), e->
			{
				Circle peckPoint = new Circle() ; // pass in x, y, width and height
			peckPoint.setCenterX(fromX+15);  
			peckPoint.setCenterY(fromY+15);  
			peckPoint.setRadius(4); 
			peckPoint.setFill(Color.web("#E4CB18"));
			
			boolean toadd= true;
			for(int n = 0 ; n < peckpointlist.size() ; n++)
			{
				if((peckpointlist.get(n).getCenterX()-15)== fromX && (peckpointlist.get(n).getCenterY()-15)== fromY)
				{
					toadd=false;
				}
				if(n< bonusList.size())
					if((bonusList.get(n).getX())== fromX && (bonusList.get(n).getY())== fromY)
						toadd=false;
				
				if(pacmanLocation.getCurrentLocation().getRow()== fromX && pacmanLocation.getCurrentLocation().getColumn() == fromY)
				{
					toadd=false;
				}
			}
			
			if(toadd == true) {
			pane.getChildren().add(peckPoint) ;
			peckpointlist.add(peckPoint) ;
			}
				
		});
			timeline2 = new Timeline(retrunPeckPoints) ;
			timeline2.setCycleCount(Timeline.INDEFINITE) ;
			timeline2.play() ;
					
	}
	
		private void pauseGhost() {
			pane.setOnMouseClicked(new javafx.event.EventHandler<Event>() {
	
				@Override
				public void handle(Event arg0) {
					System.out.println("YESSSSSSS");
					if(isbonusUsed==false) {
				
					if(isbonusUsed==false && checkGhostInRadar(redGhost)==true) {
						if(pacmanLocation.color == Model.Color.yellow) {
							pacmanLocation.color= Model.Color.green;	
							
					}
					else if(pacmanLocation.color== Model.Color.green)
					{
						pacmanLocation.color= Model.Color.yellow;
	
					}
						timeline3.stop();
						timeline3.getKeyFrames().clear();
	
						ghosts_keyFrame = new KeyFrame(Duration.millis(ghostSpeed), e->
						{
						 movePink();
						 moveBlue();
						 
						});
						timeline3 = new Timeline(ghosts_keyFrame) ;
						timeline3.setCycleCount(Timeline.INDEFINITE) ;
						timeline3.play() ;
						
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								timeline3.stop();
								timeline3.getKeyFrames().clear();
								moveGhostAtSpeed();
								}
						}, 5000);
	
						isbonusUsed=true;
					}
					
					if(isbonusUsed==false && checkGhostInRadar(blueGhost)==true) {
						if(pacmanLocation.color == Model.Color.yellow) {
							pacmanLocation.color= Model.Color.green;	
							
					}
					else if(pacmanLocation.color== Model.Color.green)
					{
						pacmanLocation.color= Model.Color.yellow;
	
					}
						timeline3.stop();
						timeline3.getKeyFrames().clear();
	
						ghosts_keyFrame = new KeyFrame(Duration.millis(ghostSpeed), e->
						{
						 movePink();
						 moveRed();
						 
						});
						timeline3 = new Timeline(ghosts_keyFrame) ;
						timeline3.setCycleCount(Timeline.INDEFINITE) ;
						timeline3.play() ;
						
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								timeline3.stop();
								timeline3.getKeyFrames().clear();
								moveGhostAtSpeed();
							}
						}, 5000);
						isbonusUsed=true;
								}
					if(isbonusUsed==false && checkGhostInRadar(pinkGhost)==true) {
						if(pacmanLocation.color == Model.Color.yellow) {
							pacmanLocation.color= Model.Color.green;	
							
					}
					else if(pacmanLocation.color== Model.Color.green)
					{
						pacmanLocation.color= Model.Color.yellow;
	
					}
						timeline3.stop();
						timeline3.getKeyFrames().clear();
						ghosts_keyFrame = new KeyFrame(Duration.millis(ghostSpeed), e->
						{
						 moveBlue();
						 moveRed();
						 
						});
						timeline3 = new Timeline(ghosts_keyFrame) ;
						timeline3.setCycleCount(Timeline.INDEFINITE) ;
						timeline3.play() ;
						
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								timeline3.stop();
								timeline3.getKeyFrames().clear();
								moveGhostAtSpeed();
								}
						}, 5000);
						isbonusUsed=true;
						}
	
					}
	
				}
				
			});
	
	
			
	
	}
		
		
		private boolean checkGhostInRadar(Ghost ghost) {
			boolean toReturn=false;
			int pacmanX= pacmanLocation.getCurrentLocation().getRow();
			int pacmanY= pacmanLocation.getCurrentLocation().getColumn();
			int ghostX= (int) ghost.getCurrentLocation().getRow();
			int ghostY= (int) ghost.getCurrentLocation().getColumn();
			if((pacmanY-ghostY <= 90 && pacmanX==ghostX)
					|| (pacmanX-ghostX <= 90 && pacmanY==ghostY)|| (ghostY-pacmanY <= 90 && ghostX==pacmanX)|| (ghostX-pacmanX <= 90 && ghostY==pacmanY))
				toReturn=true;
			return toReturn;
			
		}
	
		private void updateLevel() {
				if(game.score>50 && game.score<=100) {
					level=Level.medium;
					levelUp= true;
				}
				if(game.score>100 && game.score<=150) {
					level=Level.hard;
					game.setSpeed(250);
					levelUp= true;
		
				}
				if(game.score>150 && game.score<=200) {
					level=Level.super_hard;
					game.setSpeed(150);
					ghostSpeed=120;
					levelUp= true;
		
				}
				
				
				 
				 
				if(level == level.medium && levelUp==true)	{
					 for(int c = 0 ; c < wallList.size() ; c++)
						{
							if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 0)
							{
								pane.getChildren().remove(wallList.get(c)) ;
								wallList.remove(c);
							}
							if((wallList.get(c).getX())== 90 && (wallList.get(c).getY())== 600)
							{
								pane.getChildren().remove(wallList.get(c)) ;
								wallList.remove(c);
		
							}
							
							if((wallList.get(c).getX())== 0 && (wallList.get(c).getY())== 300)
							{
								pane.getChildren().remove(wallList.get(c)) ;
								wallList.remove(c);
		
							}
							if((wallList.get(c).getX())== 600 && (wallList.get(c).getY())== 300)
							{
								pane.getChildren().remove(wallList.get(c)) ;
								wallList.remove(c);
		
							}
						}
					 levelUp=false;
		
				}
				
				
				if(level == level.hard && levelUp==true)	{
					Rectangle wall1 = new Rectangle(90, 0, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
		wall1.setFill(Color.web("#191970")) ;
		wall1.setStroke(Color.CORNFLOWERBLUE) ;
		wall1.setStrokeWidth(2.0) ;
		
		Rectangle wall2 = new Rectangle(90, 600, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
		wall2.setFill(Color.web("#191970")) ;
		wall2.setStroke(Color.CORNFLOWERBLUE) ;
		wall2.setStrokeWidth(2.0) ;
		
		Rectangle wall3 = new Rectangle(0, 300, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
		wall3.setFill(Color.web("#191970")) ;
		wall3.setStroke(Color.CORNFLOWERBLUE) ;
		wall3.setStrokeWidth(2.0) ;
		
		
		Rectangle wall4 = new Rectangle(600, 300, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
		wall4.setFill(Color.web("#191970")) ;
					wall4.setStroke(Color.CORNFLOWERBLUE) ;
					wall4.setStrokeWidth(2.0) ;
		
		
					pane.getChildren().add(wall1) ;
					pane.getChildren().add(wall2) ;
					pane.getChildren().add(wall3) ;
					pane.getChildren().add(wall4) ;
		
					wallList.add(wall1);
					wallList.add(wall2);
					wallList.add(wall3);
					wallList.add(wall4);
		
					timeline.stop();  
					timeline.getKeyFrames().clear();
					levelUp=false;	
					movePackmanAtSpeed();
					
				}			
				
				if(level == level.super_hard && levelUp==true)	{
					timeline.stop();  
					timeline.getKeyFrames().clear();
					timeline3.stop();  
					timeline3.getKeyFrames().clear();
					levelUp=false;	
					movePackmanAtSpeed();
					moveGhostAtSpeed();
					
				}			
		}
		
			/**
		* will check if the ghost has caught pacman 
		* @param x_ghost
		* @param y_ghost
		* @param x_pacMan
		* @param y_pacMan
		* @return
		*/
		public Boolean caughtPacman(int x_ghost, int y_ghost , int x_pacMan,int y_pacMan)		
		{		
		if((x_ghost- ObjectSize == x_pacMan && y_ghost == y_pacMan) || (x_ghost+ ObjectSize == x_pacMan && y_ghost == y_pacMan))
			return true ;
		if((x_ghost == x_pacMan && y_ghost-ObjectSize == y_pacMan) || (x_ghost == x_pacMan && y_ghost+ObjectSize == y_pacMan))
			return true ;
		if(x_ghost == x_pacMan && y_ghost == y_pacMan)
			return true ;
		
		return false ;
		}
		
		private void movePackmanAtSpeed() {
			pacman_keyFrame = new KeyFrame(Duration.millis(game.speed), e->
			{
				if(caughtPacman((int) redGhost.getCurrentLocation().getRow(),(int) redGhost.getCurrentLocation().getColumn(),pacmanLocation.getCurrentLocation().getRow(),pacmanLocation.getCurrentLocation().getColumn())==true)
				{
					System.out.println("Reeeed BYEEEEE");
			game.setLive(game.getLive()-1);
			
			if(game.getLive()==0)
			{
				Runtime.getRuntime().exit(0);
			}
			else 
			{
				
				if(game.getLive()<=2)
				{
					if(game.getLive()==2)
					{
						life3.setVisible(false);
					}
					if(game.getLive()==1)
					{		
						life3.setVisible(false);
		
						life2.setVisible(false);
					}
					pacmanLocation.getCurrentLocation().setRow(300);
					pacmanLocation.getCurrentLocation().setColumn(570);
					pane.getChildren().removeAll();
					redGhost.getCurrentLocation().setRow(300);
					redGhost.getImage().setX(300);
					redGhost.getCurrentLocation().setColumn(240);
					redGhost.getImage().setY(240);
					blueGhost.getCurrentLocation().setRow(270);
					blueGhost.getImage().setX(270);
					blueGhost.getCurrentLocation().setColumn(240);
					blueGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setColumn(240);
					pinkGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setRow(330);
					pinkGhost.getImage().setX(330);
		
					resume();
				}
				
				
			}
			
		}
		if(caughtPacman((int) blueGhost.getCurrentLocation().getRow(),(int)blueGhost.getCurrentLocation().getColumn(),pacmanLocation.getCurrentLocation().getRow(),pacmanLocation.getCurrentLocation().getColumn())==true)
		{
			System.out.println("Bluee BYEEEEE");
			game.setLive(game.getLive()-1);
			if(game.getLive()==0)
			{
				Runtime.getRuntime().exit(0);
			}
			else
			{
				
				if(game.getLive()<=2)
				{
					if(game.getLive()==2)
					{
						life3.setVisible(false);
					}
					if(game.getLive()==1)
					{		
						life3.setVisible(false);
		
						life2.setVisible(false);
					}
					pane.getChildren().removeAll();
					pacmanLocation.getCurrentLocation().setRow(300);
					pacmanLocation.getCurrentLocation().setColumn(570);
					redGhost.getCurrentLocation().setRow(300);
					redGhost.getImage().setX(300);
					redGhost.getCurrentLocation().setColumn(240);
					redGhost.getImage().setY(240);
					blueGhost.getCurrentLocation().setRow(270);
					blueGhost.getImage().setX(270);
					blueGhost.getCurrentLocation().setColumn(240);
					blueGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setColumn(240);
					pinkGhost.getImage().setY(240);
					pinkGhost.getCurrentLocation().setRow(330);
					pinkGhost.getImage().setX(330);
					resume();
				}
				
			}
			
		}
		if(caughtPacman((int) pinkGhost.getCurrentLocation().getRow(),(int)pinkGhost.getCurrentLocation().getColumn(),pacmanLocation.getCurrentLocation().getRow(),pacmanLocation.getCurrentLocation().getColumn())==true)
		{
			System.out.println("pinkkk BYEEEEE");
					game.setLive(game.getLive()-1);
					if(game.getLive()==0)
					{
						Runtime.getRuntime().exit(0);
					}
					else 
					{
						if(game.getLive()<=2)
						{
							if(game.getLive()==2)
							{
								life3.setVisible(false);
							}
							if(game.getLive()==1)
							{		
								life3.setVisible(false);
		
								life2.setVisible(false);
							}
							pane.getChildren().removeAll();
							pacmanLocation.getCurrentLocation().setRow(300);
							
							pacmanLocation.getCurrentLocation().setColumn(570);
							redGhost.getCurrentLocation().setRow(300);
							redGhost.getImage().setX(300);
							redGhost.getCurrentLocation().setColumn(240);
							redGhost.getImage().setY(240);
							blueGhost.getCurrentLocation().setRow(270);
							blueGhost.getImage().setX(270);
							blueGhost.getCurrentLocation().setColumn(240);
							blueGhost.getImage().setY(240);
							pinkGhost.getCurrentLocation().setColumn(240);
							pinkGhost.getImage().setY(240);
							pinkGhost.getCurrentLocation().setRow(330);
							pinkGhost.getImage().setX(330);
							resume();
						}
					}
					
				}
			 movement(newDir);
			});
			timeline = new Timeline(pacman_keyFrame) ;
			timeline.setCycleCount(Timeline.INDEFINITE) ;
			timeline.play() ;		
		}
		
		//fill board acccording to the matrix. every object on the board has it's defining number(see on Model\Board) for example - 0:wall
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
			wallList.add(wall);
		
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
			peckpointlist.add(peckPoint) ;
		
		
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
			bonusList.add(imageView);
		
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
			questionsPoints.add(imageView);
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
			packmanMoves.add(imageView);
		
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
			blueGhost.setId(1);
			blueGhost.setSpeed(ghostSpeed);
			blueGhost.setImage(imageView);
			blueGhost.setCurrentLocation(new Location(thisRow, thisColoum));
		
		
		}
		if(matrix[i][j] == 6)
		{
			ImageView imageView = new ImageView("Photos/ghost_red.png");
			imageView.setFitHeight(30);
			imageView.setFitWidth(30);
			imageView.setX(thisRow);
			imageView.setY(thisColoum);
		
			pane.getChildren().add(imageView) ;
			redGhost.setId(2);
			redGhost.setSpeed(ghostSpeed);
			redGhost.setImage(imageView);
			redGhost.setCurrentLocation(new Location(thisRow, thisColoum));
			}
		
		if(matrix[i][j] == 7)
		{
			ImageView imageView = new ImageView("Photos/ghost_pink.png");
						imageView.setFitHeight(30);
						imageView.setFitWidth(30);
						imageView.setX(thisRow);
						imageView.setY(thisColoum);
		
						pane.getChildren().add(imageView) ;
						pinkGhost.setId(3);
						pinkGhost.setSpeed(ghostSpeed);
						pinkGhost.setImage(imageView);
						pinkGhost.setCurrentLocation(new Location(thisRow, thisColoum));
		
					}
					
					
					thisRow+=30;
				}
				thisColoum+=30;
				thisRow=0;
			}
		}
			
		
		private void resume()
		{
			int thisRow=0;
		    int thisColoum=0;
		    int thisRow1=0;
		    int thisColoum1=0;
		    pane.getChildren().removeAll(bonusList);
		    pane.getChildren().removeAll(packmanMoves);
		    pane.getChildren().removeAll(peckpointlist);
		    pane.getChildren().removeAll(wallList);
		    pane.getChildren().removeAll(questionsPoints);
		    
			peckpointlist = new ArrayList<Circle>() ;
			wallList = new ArrayList<Rectangle>() ;
			bonusList = new ArrayList<ImageView>() ;
			packmanMoves = new ArrayList<ImageView>() ;
			questionsPoints= new ArrayList<ImageView>() ;
			boolean addWall=false;
			
			for(int i=0; i<21; i++)
			{
				for(int j=0;j<21;j++) {
					
					if(matrix1[i][j]==3)
						matrix1[i][j]=0;
					
					thisRow1+=30;
				}
				thisColoum1+=30;
				thisRow1=0;
			}
			
		    matrix = Board.getInstance().putRandomQuestion(matrix1);
			 
			for(int i=0; i<21; i++)
			{
				for(int j=0;j<21;j++) {
					
					// update the walls on the board
	if(matrix[i][j]==1)
	{
			if(level==Level.medium) {
				if(((thisRow!=90 && thisColoum==0) || (thisRow!=90 && thisColoum!=600) ||
						(thisRow!=0 && thisColoum!=300) || (thisRow!=600 && thisColoum!=300) ))
					addWall=true;
		}	
			else
				addWall=true;
		
			if(addWall==true) {
				Rectangle wall = new Rectangle(thisRow, thisColoum, ObjectSize, ObjectSize) ; 		// pass in x, y, width and height
	wall.setFill(Color.web("#191970")) ;
				wall.setStroke(Color.CORNFLOWERBLUE) ;
				wall.setStrokeWidth(2.0) ;
				pane.getChildren().add(wall) ;
				wallList.add(wall);
				addWall=false;
			}
		
		
		
	
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
		peckpointlist.add(peckPoint) ;
	
	
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
		bonusList.add(imageView);
	
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
					questionsPoints.add(imageView);
	
	
				}					
				thisRow+=30;
			}
			thisColoum+=30;
			thisRow=0;
		}
		
	}
		/** method that'll return a random direction 
		 * 
		 * @return dirc
		 */
		private Direction getRandomDir()
		{
			Direction dirc = null ;		
			int num = (int)(Math.random() * 4) ;
		
			switch(num) 
			{
				case 0 : dirc = Direction.UP ;
						 break ;
				case 1 : dirc = Direction.DOWN ;
						 break ;
				case 2 : dirc = Direction.LEFT ;
						 break ;
				case 3 : dirc = Direction.RIGHT ;
						 break ;
			}		
		
			return dirc ;
		}
		
		/** returns the opposite direction of the specified dirrection
		 * 
		 * @param d
		 * @return
		 */
		private Direction oppositeDir(Direction d) 
		{
			Direction dir = null ;
		
			if(d == Direction.UP) 
				dir = Direction.DOWN ;
			else if(d == Direction.DOWN) 
				dir = Direction.UP ;
			else if(d == Direction.LEFT)
				dir = Direction.RIGHT ;
			else if(d == Direction.RIGHT)
				dir = Direction.LEFT ;
		
			return dir ;
		}
		
		// method to move the red ghost
		private void moveRed()
		{
			Direction dontGo = null ;
		
			if(isWall(red_movingAt, redGhost.getCurrentLocation().getRow(),  redGhost.getCurrentLocation().getColumn()) == true && numOfTurns(red_movingAt, redGhost.getCurrentLocation().getRow(),  redGhost.getCurrentLocation().getColumn()) == 1)
			{							// if the ghost runs to a dead end it goes in the direction opposite to its current direction
			red_movingAt = oppositeDir(red_movingAt) ;
		}
		else
		{
			for(;;)
			{
				
					if(tailPacman(redGhost, 10) != null)
					{
						red_movingAt = tailPacman(redGhost, 10) ;	// check if pacman is in red's radar. If pacman is 7 walls away from red, red will follow him
			break ;
		}
		
		
		// move in a random direction
					Direction direction = getRandomDir() ;
					if(isWall(direction, redGhost.getCurrentLocation().getRow(),  redGhost.getCurrentLocation().getColumn()) == false)
					{
						if(dontGo != null && direction != dontGo)
						{
							red_movingAt = direction ;
							break ;
						}
						else if(direction != oppositeDir(red_movingAt))
						{
							red_movingAt = direction ;
							break ;
						}
					}
				}
			}
			if(redGhost.getCurrentLocation().getRow()==90 &&  redGhost.getCurrentLocation().getColumn()==0) {
				red_movingAt=Direction.DOWN;
			}
			if(redGhost.getCurrentLocation().getRow()==90 &&  redGhost.getCurrentLocation().getColumn()==600) {
				red_movingAt=Direction.UP;
			}if(redGhost.getCurrentLocation().getRow()==0 &&  redGhost.getCurrentLocation().getColumn()==300) {
				red_movingAt=Direction.RIGHT;
			}if(redGhost.getCurrentLocation().getRow()==600 &&  redGhost.getCurrentLocation().getColumn()==300) {
				red_movingAt=Direction.LEFT;
			}
		
			moveGhost(red_movingAt, redGhost.getCurrentLocation().getRow(), redGhost.getCurrentLocation().getColumn(), redGhost);
		
		}
		
		// method to move the blue ghost
		private void moveBlue()
				{
					Direction dontGo = null ;
		
					if(isWall(blue_movingAt, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn()) == true && numOfTurns(blue_movingAt, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn()) == 1)
					{							// if the ghost runs to a dead end it goes in the direction opposite to its current direction
			blue_movingAt = oppositeDir(blue_movingAt) ;
		}
		else
		{
			for(;;)
			{
				
					if(tailPacman(blueGhost, 5) != null)
					{
						blue_movingAt = tailPacman(blueGhost, 5) ;	// check if pacman is in red's radar. If pacman is 7 walls away from red, red will follow him
			break ;
		}
		
		
		// move in a random direction
					Direction direction = getRandomDir() ;
					if(isWall(direction, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn()) == false)
					{
						if(dontGo != null && direction != dontGo)
						{
							blue_movingAt = direction ;
							break ;
						}
						else if(direction != oppositeDir(blue_movingAt))
						{
							blue_movingAt = direction ;
							break ;
						}
					}
				}
			}
			if(blueGhost.getCurrentLocation().getRow()==90 && blueGhost.getCurrentLocation().getColumn()==0) {
				blue_movingAt=Direction.DOWN;
			}if(blueGhost.getCurrentLocation().getRow()==90 && blueGhost.getCurrentLocation().getColumn()==600) {
				blue_movingAt=Direction.UP;
			}if(blueGhost.getCurrentLocation().getRow()==0 && blueGhost.getCurrentLocation().getColumn()==300) {
				blue_movingAt=Direction.RIGHT;
			}if(blueGhost.getCurrentLocation().getRow()==600 && blueGhost.getCurrentLocation().getColumn()==300) {
				blue_movingAt=Direction.LEFT;
			}
		
			moveGhost(blue_movingAt, blueGhost.getCurrentLocation().getRow(), blueGhost.getCurrentLocation().getColumn(), blueGhost);
		
		}
		
		// method to move the pink ghost
		private void movePink()
			{
				Direction dontGo = null ;
		
				if(isWall(pink_movingAt, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn()) == true && numOfTurns(pink_movingAt, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn()) == 1)
				{							// if the ghost runs to a dead end it goes in the direction opposite to its current direction
			pink_movingAt = oppositeDir(pink_movingAt) ;
		}
		else
		{
			for(;;)
			{
					if(tailPacman(pinkGhost, 4) != null)
					{
						pink_movingAt = tailPacman(pinkGhost, 4) ;	// check if pacman is in pink's radar. If pacman is 4 walls away from pink, pink will follow him
			break ;
		}
		
		
		// move in a random direction
						Direction direction = getRandomDir() ;
						if(isWall(direction, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn()) == false)
						{
							if(dontGo != null && direction != dontGo)
							{
								pink_movingAt = direction ;
								break ;
							}
							else if(direction != oppositeDir(pink_movingAt))
							{
								pink_movingAt = direction ;
								break ;
							}
						}
					}
				}
				if(pinkGhost.getCurrentLocation().getRow()==90 && pinkGhost.getCurrentLocation().getColumn()==0) {
					pink_movingAt=Direction.DOWN;
				}
				if(pinkGhost.getCurrentLocation().getRow()==90 && pinkGhost.getCurrentLocation().getColumn()==600) {
					pink_movingAt=Direction.UP;
				}if(pinkGhost.getCurrentLocation().getRow()==0 && pinkGhost.getCurrentLocation().getColumn()==300) {
					pink_movingAt=Direction.RIGHT;
				}if(pinkGhost.getCurrentLocation().getRow()==600 && pinkGhost.getCurrentLocation().getColumn()==300) {
					pink_movingAt=Direction.LEFT;
				}
		
				moveGhost(pink_movingAt, pinkGhost.getCurrentLocation().getRow(), pinkGhost.getCurrentLocation().getColumn(), pinkGhost);
		
			}
		
			
		private Direction pacmanAt(Ghost redGhost2)
			{
				double x = redGhost2.getCurrentLocation().getRow();
				double y = redGhost2.getCurrentLocation().getColumn();
				double pacmanX= pacmanLocation.getCurrentLocation().getRow();
				double pacmanY= pacmanLocation.getCurrentLocation().getColumn();
		
				if(y == pacmanY && (pacmanX-x) > 0 && (pacmanX-x) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.RIGHT) == false)
					return Direction.RIGHT ;
				else if(y == pacmanY && (x-pacmanX) > 0 && (x-pacmanX) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.LEFT) == false)
					return Direction.LEFT ;
				else if(x == pacmanX && (pacmanY-y) > 0 && (pacmanY-y) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.DOWN) == false)
					return Direction.DOWN ;
				else if(x == pacmanX && (y-pacmanY) > 0 && (y-pacmanY) < 100 && checkForWallsBetween(x, y, pacmanX, pacmanY, Direction.UP) == false)
					return Direction.UP ;
		
				return null ;
			}
			
			
		// method that'll check for walls between 2 positions in a specific direction
		private Boolean checkForWallsBetween(double from_x, double from_y, double to_x, double to_y, Direction direction)
			{
				
				boolean wall_present = false ;
		
				if(direction == Direction.UP)
				{
					while(from_y > to_y && wall_present == false)
					{
						wall_present = isWall(direction, from_x, from_y) ;
						from_y-=ObjectSize ;
					}
				}
				else if(direction == Direction.DOWN)
				{
					while(from_y < to_y && wall_present == false)
					{
						wall_present = isWall(direction, from_x, from_y) ;
						from_y+=ObjectSize ;
					}
				}
				else if(direction == Direction.LEFT)
				{
					while(from_x > to_x && wall_present == false)
					{
						wall_present = isWall(direction, from_x, from_y) ;
						from_x-=ObjectSize ;
					}
				}
				else if(direction == Direction.RIGHT)
				{
					while(from_x < to_x && wall_present == false)
					{
						wall_present = isWall(direction, from_x, from_y) ;
						from_x+=ObjectSize ;
					}
				}
		
				return wall_present ;
			}
			
		private Direction tailPacman(Ghost redGhost2, int wallCount) {
			Direction direction = null ;
			double ghostX = redGhost2.getCurrentLocation().getRow();
			double ghostY = redGhost2.getCurrentLocation().getColumn();	
			double pacmanX = pacmanLocation.getCurrentLocation().getRow() ;	
			double pacmanY = pacmanLocation.getCurrentLocation().getColumn() ;	
		
			// from the ghost's current position find out in which direction pacman is
			
				Direction pacmanDir = pacmanAt(redGhost2) ;	
				int wallSize= ObjectSize;
		
				if(pacmanDir == Direction.DOWN && pacmanY-ghostY <= (wallSize*wallCount))
				{
					if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.DOWN) == false)	direction = Direction.DOWN ;				
				}
				else if(pacmanDir == Direction.UP && ghostY-pacmanY <= (wallSize*wallCount))
				{
					if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.UP) == false)	direction = Direction.UP ;				
				}
				else if(pacmanDir == Direction.LEFT && ghostX-pacmanX <= (wallSize*wallCount))
				{
					if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.LEFT) == false)	direction = Direction.LEFT ;				
				}
				else if(pacmanDir == Direction.RIGHT && pacmanX-ghostX <= (wallSize*wallCount))
				{
					if(checkForWallsBetween(ghostX, ghostY, pacmanX, pacmanY, Direction.RIGHT) == false)	direction = Direction.RIGHT ;				
				}
		
				return direction ;
				}
		
		private int numOfTurns(Direction currentDir, double x, double y) {
	
			int numOfTurns = 0 ;
	
			if(currentDir != Direction.UP && isWall(Direction.UP, x, y) == false)	numOfTurns++ ;
	
			if(currentDir != Direction.DOWN && isWall(Direction.DOWN, x, y) == false)	numOfTurns++ ;
	
			if(currentDir != Direction.LEFT && isWall(Direction.LEFT, x, y) == false)	numOfTurns++ ;
	
			if(currentDir != Direction.RIGHT && isWall(Direction.RIGHT, x, y) == false)	numOfTurns++ ;
	
			return numOfTurns ;
			}
	
		public Pane getPane() {
			return pane;
		}
	
		public void setScene(Scene scene) {
			this.scene = scene;
		}
		
		public void pauseOrUnPauseGame() {
				if(game.gameState==GameState.Paused) {
					game.gameState=GameState.Running;
					movePackmanAtSpeed();
					moveGhostAtSpeed();
		
				}
				else if(game.gameState==GameState.Running) {
					game.gameState=GameState.Paused;
					if(timeline!=null) {
						timeline.stop();
						timeline.getKeyFrames().clear();
					}
					if(timeline2!=null) {
			
						timeline2.stop();
						timeline2.getKeyFrames().clear();
					}
					if(timeline3!=null) {
						timeline3.stop();
						timeline3.getKeyFrames().clear();
					}
					if(timeline4!=null) {
						timeline4.stop();
						timeline4.getKeyFrames().clear();
					}
			}
		
			
		
				
				}		
		
		
		
		
		}
