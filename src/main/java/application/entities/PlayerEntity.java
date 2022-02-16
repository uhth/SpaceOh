package application.entities;

import java.util.ArrayList;
import java.util.function.Supplier;

import application.entities.emitter.PlayerEmitter;
import application.entities.projectiles.BulletEntity;
import application.settings.PlayerSettings;
import application.utils.Vector2D;
import application.view.animations.Animation;
import application.view.sprites.PlayerSprite;

public class PlayerEntity extends Entity
{
		
	private ArrayList< HeartEntity > hearts;
	protected Supplier<?> bulletSupplier;
	private PlayerEntity pInstance;
	
	public PlayerEntity() {
		super( PlayerSprite.getInstance() );
		this.entityType = "PLAYER";
		this.pInstance = this;
		setPos( PlayerSettings.STARTING_X, PlayerSettings.STARTING_Y );
		this.hearts = new ArrayList< HeartEntity >();
		this.emitter = new PlayerEmitter( new Vector2D( this.pos.getX(), this.pos.getY() - 60 ), this );
		genSupplier();
		updateHearts();
		
		playAnimation( currentAnimation );
	}
	
	//bullets
	public void genSupplier() {
		this.bulletSupplier = () -> new BulletEntity( this ) {
			@Override
			protected float getBaseMaxSpeed() {
				return 4.0f;
			}
			@Override
			protected float getBaseDmg() {
				return pInstance.getDmg();
			}
		};
	}
	
	public Supplier<?> getBulletSupplier() { return bulletSupplier; }

	//animations
	@Override
	protected boolean hasAnimation() { return true; }
	//base
	@Override
	public Animation getIdleAnimation() { return animations[ 0 ]; }
	//advanced
	public Animation getPlayerStrafeRightAnimation() { return animations[ 1 ]; }
	public Animation getPlayerStrafeLeftAnimation() { return animations[ 2 ]; }
	public Animation getPlayerStrafeRightAnimationLoop() { return animations[ 3 ]; }
	public Animation getPlayerStrafeLeftAnimationLoop() { return animations[ 4 ]; }
	
	
	public void updateEmitter( boolean x, boolean y ) {
		if( x ) {
			emitter.getPivot().add( velocity.getX(), 0 );
			emitter.getPoint().add( velocity.getX(), 0 );
		}
		if( y ) {
			emitter.getPivot().add( 0, velocity.getY() );
			emitter.getPoint().add( 0, velocity.getY() );
		}
	}
	
	//utils
	@Override
	protected float getBaseMaxSpeed() {
		return PlayerSettings.BASE_MAX_SPEED;
	}
	@Override
	protected float getBaseBrakes() {
		return PlayerSettings.BASE_BRAKES;
	}
	@Override
	protected float getBaseSpeed() {
		return PlayerSettings.BASE_SPEED;
	}
	@Override
	protected int getBaseAttackSpeed() {
		return PlayerSettings.BASE_ATTACK_SPEED;
	}
	@Override
	protected float getBaseBodyDmg() {
		return PlayerSettings.BASE_BODY_DMG;
	}
	@Override
	protected float getBaseDmg() {
		return PlayerSettings.BASE_DMG;
	}
		
	//updates hud hearts
	public void updateHearts() {
		while( hearts.size() < hp ) {
			HeartEntity h = new HeartEntity();
			if( hearts.size() == 0 ) {
				h.setPos( PlayerSettings.HEARTS_X, PlayerSettings.HEARTS_Y  );
			}
			else {
				h.setPos( hearts.get( hearts.size() - 1 ).getPos().getX() + 30, hearts.get( hearts.size() - 1 ).getPos().getY() );
			}
			hearts.add( h );
		}
		
		while( hearts.size() > hp ) {
			if( hearts.size() == 0 ) break;
				hearts.remove( hearts.size() - 1 );
		}
			
	}
	
	public ArrayList<HeartEntity> getHearts() {	return hearts; }
	
}
