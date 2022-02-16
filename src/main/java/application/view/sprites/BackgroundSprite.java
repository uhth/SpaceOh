package application.view.sprites;

import application.settings.Settings;
import application.view.animations.Animation;
import javafx.scene.image.Image;

public class BackgroundSprite extends Sprite
{
	private static final String BACKGROUND_IMG_PATH = "/application/images/background/backgroundSpriteSheetV2.png";
	
	private static BackgroundSprite backgroundSprite;
	
	public static BackgroundSprite getInstance() {
		if( backgroundSprite == null )
			backgroundSprite = new BackgroundSprite();
		return backgroundSprite;
	}
	
	public Animation bgAnimation;
	
	private BackgroundSprite() {
		init();
	}
	
	private void init() {
		Image backgroundSpriteSheet = new Image( getClass().getResourceAsStream( BACKGROUND_IMG_PATH ) );
		loadSprites( backgroundSpriteSheet );
	}
	
	@Override
	protected float getSWidth() {
		return Settings.WINDOW_SIZE_W;
	}
	
	@Override
	protected float getSHeight() {
		return Settings.WINDOW_SIZE_H;	
	}
		
}
