package application.entities.enemies;

import application.entities.Entity;
import application.view.animations.Animation;
import application.view.sprites.enemies.RattlerSprite;

public class RattlerEntity extends Entity
{
		
	public RattlerEntity() {
		super( RattlerSprite.getInstance() );
		this.entityType = "ENEMY";
		setPos( 100.0, 100.0 );	
		healthBar.setPos( this.pos );
		playAnimation(currentAnimation);
	}
	
	public RattlerEntity( double x, double y ) {
		super( RattlerSprite.getInstance() );
		this.entityType = "ENEMY";
		setPos( x, y );	
		healthBar.setPos( this.pos );
		playAnimation(currentAnimation);
	}
		
	
	//animation
	@Override
	protected boolean hasAnimation() { return true; }
	//base
	@Override
	public Animation getIdleAnimation() { return animations[ 0 ]; }
	@Override
	public Animation getHurtAnimation() { return animations[ 1 ]; }
	@Override
	public Animation getDeathAnimation() { return animations[ 2 ]; }	
	
	//hitbox
	@Override
	protected float getHitboxWidth() { return 80; }
	@Override
	protected float getHitBoxHeight() { return 50; }
	@Override
	protected float getOffX() { return 8; }
	@Override
	protected float getOffY() { return 20; }
	@Override
	protected boolean hasHealthBar() { return true; }	
	
	
}