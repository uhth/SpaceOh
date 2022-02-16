package application.entities.enemies;

import java.util.function.Supplier;
import application.entities.Entity;
import application.entities.emitter.SightSeekerEmitter;
import application.entities.projectiles.BulletEntity;
import application.view.animations.Animation;
import application.view.sprites.enemies.SightSeekerSprite;

public class SightSeekerEntity extends Entity
{
	private SightSeekerEntity sInstance;
	protected Supplier<?> bulletSupplier;
	
	public SightSeekerEntity() {
		super( SightSeekerSprite.getInstance() );
		this.entityType = "ENEMY";
		this.sInstance = this;
		setPos( 100.0, 100.0 );	
		healthBar.setPos( this.pos );
		currentAnimation.play();
		genSupplier();
		this.emitter = new SightSeekerEmitter( pos, this ); 
		
	}
	
	
	public void genSupplier() {
		this.bulletSupplier = () -> new BulletEntity( this ) {
			@Override
			protected float getBaseDmg() {
				return sInstance.getDmg();
			}
			@Override
			protected float getBaseMaxSpeed() {
				return 2.0f;
			}
		};
	}
	
	public Supplier<?> getBulletSupplier() { return bulletSupplier; }
	
	//animation
	@Override
	protected boolean hasAnimation() { return true; }
	public Animation getIdleAnimation() { return animations[ 0 ]; }
//	public Animation getHurtAnimation() { return animations[ 1 ]; }
//	public Animation getDeathAnimation() { return animations[ 2 ]; }	

	//hitbox
	@Override
	protected float getHitboxWidth() { return 46; }
	@Override
	protected float getHitBoxHeight() { return 46; }
	@Override
	protected float getOffX() { return 24; }
	@Override
	protected float getOffY() { return 24; }
	@Override
	protected boolean hasHealthBar() { return true; }	

}
