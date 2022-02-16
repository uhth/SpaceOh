package application.view.sprites;

import application.settings.Settings;
import application.view.animations.Animation;

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
		super( loadSprite( BACKGROUND_IMG_PATH ), Settings.WINDOW_SIZE_W, Settings.WINDOW_SIZE_H, 1.0, 1.0 );
	}
	
		
}
