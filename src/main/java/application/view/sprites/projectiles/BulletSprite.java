package application.view.sprites.projectiles;

import application.view.animations.Animation;
import application.view.sprites.Sprite;
import javafx.scene.image.Image;

public class BulletSprite extends Sprite
{
	private static final String BULLET_IMAGE_PATH = "/application/images/projectiles/bulletSpriteSheetV1.png";
	
	private static BulletSprite bulletSprite;
		
	public static BulletSprite getInstance() {
		if( bulletSprite == null )
			bulletSprite = new BulletSprite();
		return bulletSprite;
	}
	
	private BulletSprite() {
		init();
		initAnimations();
	}
	
	private void initAnimations() {
		animations = new Animation[ 2 ];
		animations[ 0 ] = Animation.createAnimation( 6, getSpriteArray(), 0, 8 );
		animations[ 1 ] = Animation.createAnimation( 4, getSpriteArray(), 6, 8 );
	}
	
	private void init() {
		Image playerSpriteSheet = new Image( getClass().getResourceAsStream( BULLET_IMAGE_PATH ) );
		loadSprites( playerSpriteSheet );
	}
	
	@Override
	protected float getScaledX() {
		return 2;
	}
	
	@Override
	protected float getScaledY() {
		return 2;
	}
	

}
