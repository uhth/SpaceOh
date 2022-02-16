package application.entities.projectiles;

import application.entities.Entity;
import application.view.animations.Animation;
import application.view.sprites.projectiles.BulletSprite;

public class BulletEntity extends Entity	
{
				
	protected Entity source;
	
	public BulletEntity( Entity source ) {
		super( BulletSprite.getInstance() );
		this.source = source;
		this.emitter = source.getEmitter();
		this.direction = emitter.getDirection();
		this.entityType = "BULLET";
		posDuringSpawn();
		currentAnimation.playOnce( 0 );
	}
	
	public void posDuringSpawn() {
		this.setPos( emitter.getPivot().getX(), emitter.getPivot().getY() );
	}
		
	//animation
	@Override
	protected boolean hasAnimation() { return true; }
	public Animation getBulletSpawnAnimation() { return animations[ 0 ]; }
	public Animation getBulletFlyAnimation() { return animations[ 1 ]; }
		
	//hitbox
	@Override
	protected float getOffX() {	return 24; }
	@Override
	protected float getOffY() {	return 24; }
	@Override
	protected float getHitboxWidth() { return 16; }
	@Override
	protected float getHitBoxHeight() {	return 16; }

	public Entity getSource() { return source; }
	public void setSource(Entity source) { this.source = source; }
		
	
}
