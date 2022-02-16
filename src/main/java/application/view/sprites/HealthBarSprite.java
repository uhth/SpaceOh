package application.view.sprites;

import javafx.scene.image.Image;

public class HealthBarSprite extends Sprite
{
	
	private static final String HEALTH_BAR_IMG_PATH = "/application/images/ui/healthBarSpriteSheetV1.png";
	
	private static HealthBarSprite healthBarSprite;
	
	public static HealthBarSprite getInstance() {
		if( healthBarSprite == null )
			healthBarSprite = new HealthBarSprite();
		return healthBarSprite;
	}
	
	private HealthBarSprite() {		
		init();
	}
	

	private void init() {
		Image healthBarSpriteSheet = new Image( getClass().getResourceAsStream( HEALTH_BAR_IMG_PATH ) );
		loadSprites( healthBarSpriteSheet );
	}
	
	@Override
	protected float getScaledX() {
		return 2;
	}
	
}
