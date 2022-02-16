package application;

import application.controller.gamestate.GameStateController;
import application.settings.Settings;
import application.view.SpaceOhGraphics;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SpaceOhMain extends Application
{

	@Override
	public void start( Stage primaryStage ) throws Exception 
	{
		GameStateController controller = GameStateController.getInstance();
		SpaceOhGraphics g = SpaceOhGraphics.getInstance();
		g.setController( controller );
		Scene scene = new Scene( g );
		primaryStage.setScene( scene );
		primaryStage.setHeight( Settings.WINDOW_SIZE_H );
		primaryStage.setWidth( Settings.WINDOW_SIZE_W );
		primaryStage.setResizable( false );
		primaryStage.show();
				
	}
	
	public static void main( String[] args ) 
	{
		launch( args );
	}
	
}
