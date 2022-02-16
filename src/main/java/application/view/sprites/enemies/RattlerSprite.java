package application.view.sprites.enemies;

import application.view.animations.Animation;
import application.view.sprites.Sprite;
import javafx.scene.image.Image;

public class RattlerSprite extends Sprite
{
	
	private static final String RATTLER_IMG_PATH = "/application/images/enemies/rattlerSpriteSheetV1.png";
	
	private static RattlerSprite rattlerSprite;
	
	public static RattlerSprite getInstance() {
		if( rattlerSprite == null )
			rattlerSprite = new RattlerSprite();
		return rattlerSprite;
	}
	
	private RattlerSprite() {
		init();
		initAnimations();
	}
	
	private void init() {
		Image rattlerSpriteSheet = new Image( getClass().getResourceAsStream( RATTLER_IMG_PATH ) );
		loadSprites( rattlerSpriteSheet );
	}
	
	private void initAnimations() {
		animations = new Animation[ 3 ];
		animations[ 0 ] = Animation.createAnimation( 8, getSpriteArray(), 0, 7 );
		animations[ 1 ] = Animation.createAnimation( 2, getSpriteArray(), 8, 7 );
		animations[ 2 ] = Animation.createAnimation( 7, getSpriteArray(), 10, 7 );
	}
	
	@Override
	protected float getScaledX() {
		return 3;
	}
	
	@Override
	protected float getScaledY() {
		return 3;
	}
	
}
