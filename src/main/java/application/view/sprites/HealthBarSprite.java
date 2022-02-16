package application.view.sprites;

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
		super( loadSprite( HEALTH_BAR_IMG_PATH ), 32, 32, 1.0, 1.0 );
	}
	
	
	
	
}
