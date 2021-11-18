

package View;

import java.io.IOException;

import java.net.URL;

import Controller.SysData;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import View.ViewLogic;

public class ViewLogic {
	// ------------------------------ Variables ------------------------------
	
	protected static final Rectangle2D FULL_SCREEN = Screen.getPrimary().getBounds();
	protected static final Rectangle2D VISIBLE_SCREEN = Screen.getPrimary().getVisualBounds();
	protected static SysData sysdata = SysData.getInstance();
	protected static QuestionsManagmentController questionsManagmntController;
	protected static startgamepagecontroller startgamepagecontroller;
	

	// ------------------------------ Methods ------------------------------
	/**
	 * this method starts the windows in the system
	 */
	public static void initUI() {
		BoardWindow();
	}

	
	protected static void newWindow(URL fxmlLocation, Stage stage, String title, boolean resizable, boolean waitFor) {
		//
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					FXMLLoader loader = new FXMLLoader(fxmlLocation);
					Parent root = loader.load();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					
					stage.setResizable(resizable);
					
					if (title != null && !title.isEmpty() && !title.trim().isEmpty())
						stage.setTitle(title);
					
					if (waitFor)
						stage.initModality(Modality.APPLICATION_MODAL);
					
					stage.showAndWait();
					
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// =================================== Game ========================================
		protected static void gameWindow() {
			Stage stage = new Stage();

			newWindow(ViewLogic.class.getResource("PlayGame.fxml"),
					stage,
					"Packman!",
					false,
					false);

		}

	// ================================== Main page ==================================
	/**
	 * Open Main page Window
	 */

	//TODO
	protected static void mainPageWindow() {
		Stage stage = new Stage();

		newWindow(ViewLogic.class.getResource("MainPage.fxml"),
				stage,
				"Packman",
				false,
				false);
	}
	// ================================== Game-Board page ==================================
		/**
		 * Open Main page Window
		 */

		//TODO
		protected static void BoardWindow() {
			Stage stage = new Stage();

			newWindow(ViewLogic.class.getResource("Board.fxml"),
					stage,
					"Packman",
					false,
					false);
		}
	
	// ============================= Leader Board =============================
	/**
	 * Open Leader Board Window
	 */

	//TODO
	protected static void leaderBoardWindow() {
		Stage stage = new Stage();

		newWindow(ViewLogic.class.getResource("leaderboard.fxml"),
				stage,
				"Leader Board",
				false,
				false);
	}

	
	
	// ============================= Enter Name =============================
	/**
	 * Open Enter Name Window
	 */

	//TODO
	protected static void StartgameWindow() {
		Stage stage = new Stage();
		
		newWindow(ViewLogic.class.getResource("startgamepage.fxml"),
				stage,
				"enter your name",
				false,
				false);
		
	}
	
	
	// ============================= Questions Manager =============================
	/**
	 * Open Question Management Window
	 */

	//TODO
	protected static void questionsManagmentWindow() {
		Stage stage = new Stage();

		newWindow(ViewLogic.class.getResource("QuestionsManagment.fxml"),
				stage,
				"Questions Managment",
				false,
				true);
	}
	
	// ============================= Add/Update Question =============================
	/**
	 * Open Edit Question Window
	 */

	//TODO
	protected static void editQuestionWindow() {
		Stage stage = new Stage();

		newWindow(ViewLogic.class.getResource("EditQuestion.fxml"),
				stage,
				"Edit a Question",
				false,
				true);
	}
	
	// ============================= How To Play =============================
	/**
	 * Open How To Play Window
	 */

	//TODO
	protected static void howToPlayWindow() {
		Stage stage = new Stage();

		newWindow(ViewLogic.class.getResource("Howtoplay.fxml"),
				stage,
				"How To Play?",
				false,
				false);

	}
	
	
	
}
