package application.controller.player;
import application.entities.PlayerEntity;
import application.model.GameState;
import application.settings.PlayerSettings;
import application.settings.Settings;
import application.view.SpaceOhGraphics;

public class PlayerController
{
	PlayerEntity playerEntity;
	GameState game;
	SpaceOhGraphics g;
	private boolean idlingX;
	private boolean idlingY;
	private boolean firing;
	private int firingDelay;
	
	//animations
	private int direction;
	private double velocityScalar;
	private double lastUpdateVelocityScalar;
	
	public PlayerController( GameState game, SpaceOhGraphics g ) {
		this.game = game;
		this.g = g;
		this.playerEntity = game.getPlayerEntity();
		this.idlingX = true;
		this.idlingY = true;
		this.firing = false;
		this.firingDelay = 0;
	}
	
	public static void hurtPlayer( PlayerEntity playerEntity, float dmg ) {
		if( playerEntity.getInvincibilityFrames() == 0 ) {
			playerEntity.takeDmg( dmg );
			playerEntity.setInvincibilityFrames( 120 );
		}
	}
	
	public void accelerateRIGHT() {
		if( playerEntity.getVelocity().getX() < 0 ) brakeX();
		else playerEntity.getVelocity().add( playerEntity.getSpeed(), 0 );
		//check to stay under max speed;
		if( playerEntity.getVelocity().getX() > playerEntity.getMaxSpeed() ) playerEntity.setVelocity( playerEntity.getMaxSpeed(), playerEntity.getVelocity().getY() );
				
		this.idlingX = false;
	}
	
	public void accelerateLEFT() {
		if(playerEntity.getVelocity().getX() > 0 ) brakeX();
		else playerEntity.getVelocity().subtract( playerEntity.getSpeed(), 0 );
		//check to stay under max speed;
		if( playerEntity.getVelocity().getX() < -playerEntity.getMaxSpeed() ) playerEntity.setVelocity( -playerEntity.getMaxSpeed(), playerEntity.getVelocity().getY() );
				
		this.idlingX = false;
	}
	
	public void accelerateUP() {
		if( playerEntity.getVelocity().getY() > 0 ) brakeY();
		else playerEntity.getVelocity().subtract( 0, playerEntity.getSpeed() );
		//check to stay under max speed;
		if( playerEntity.getVelocity().getY() < -playerEntity.getMaxSpeed() ) playerEntity.setVelocity( playerEntity.getVelocity().getX(), -playerEntity.getMaxSpeed() );
		
		this.idlingY = false;
	}
	
	public void accelerateDOWN() {
		if( playerEntity.getVelocity().getY() < 0 ) brakeY();
		else playerEntity.getVelocity().add( 0, playerEntity.getSpeed() );
		//check to stay under max speed;
		if( playerEntity.getVelocity().getY() > playerEntity.getMaxSpeed() ) playerEntity.setVelocity( playerEntity.getVelocity().getX(), playerEntity.getMaxSpeed() );
		
		this.idlingY = false;
	}
	
	public void brakeX() {
		if( playerEntity.getVelocity().getX() > 0 ) {
			 playerEntity.getVelocity().subtract( playerEntity.getBrakes(), 0 );
			
			//overshoot check
			if( playerEntity.getVelocity().getX() < 0 ) playerEntity.setVelocity( 0, playerEntity.getVelocity().getY() );
		}
		
		else if( playerEntity.getVelocity().getX() < 0 ) {
			playerEntity.getVelocity().add( playerEntity.getBrakes(), 0 );
			
			//overshoot check
			if( playerEntity.getVelocity().getX() > 0 ) playerEntity.setVelocity( 0, playerEntity.getVelocity().getY() );
		}
	}
	
	public void brakeY() {
		
		if( playerEntity.getVelocity().getY() > 0 ) {
			 playerEntity.getVelocity().subtract( 0, playerEntity.getBrakes() );
			//overshoot check
			if( playerEntity.getVelocity().getY() < 0 ) playerEntity.setVelocity( playerEntity.getVelocity().getX(), 0 ); 
		}
		
		if( playerEntity.getVelocity().getY() < 0 ) {
			playerEntity.getVelocity().add( 0 , playerEntity.getBrakes() );
			//overshoot check
			if( playerEntity.getVelocity().getY() > 0 ) playerEntity.setVelocity( playerEntity.getVelocity().getX(), 0 ); 
		}
	}
		
	public void idleX() {
		if( playerEntity.getVelocity().getX() == 0 ) return;
			
		brakeX();	
	}
	
	public void idleY() {
		if( playerEntity.getVelocity().getY() == 0 ) return;
		brakeY();
	}
	
	public void fire() {
		if( firingDelay > playerEntity.getAttackSpeed() ) {
			firingDelay = 0;
			firing = true;
		}
	}
	
	private void calculateDirection() {
		if( playerEntity.getVelocity().getX() > 0 )
			direction = PlayerSettings.RIGHT;
		else if( playerEntity.getVelocity().getX() < 0 )
			direction = PlayerSettings.LEFT;
		else direction = PlayerSettings.UP;
	}
	
	
	public void updatePlayer() {
		
		//direction
		calculateDirection();
				
		//hearts
		playerEntity.updateHearts();
		
		//invincibility
		if( playerEntity.getInvincibilityFrames() > 0 )
			playerEntity.setInvincibilityFrames( playerEntity.getInvincibilityFrames() - 1 );
		
		//animation	
		playerEntity.updateAnimation();
		
		velocityScalar = Math.abs( playerEntity.getVelocity().getX() );
		
		if( velocityScalar < lastUpdateVelocityScalar ) {
			//decelerating
			if( playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeLeftAnimationLoop() ) )
				playerEntity.playAnimationReverseOnce( playerEntity.getPlayerStrafeLeftAnimation(), 0 );
			else if( playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeRightAnimationLoop() ) )
				playerEntity.playAnimationReverseOnce( playerEntity.getPlayerStrafeRightAnimation(), 0 );
		}
		else{
			//accelerating
			if( direction == PlayerSettings.RIGHT ) {
				if( playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeLeftAnimationLoop() ) )
					playerEntity.playAnimationReverseOnce( playerEntity.getPlayerStrafeLeftAnimation(), 0 );
				else if( ( !playerEntity.getCurrentAnimation().isPlaying() && !playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeRightAnimation() ) ) || playerEntity.getCurrentAnimation().equals( playerEntity.getIdleAnimation() ) ) {
					playerEntity.playAnimationOnce( playerEntity.getPlayerStrafeRightAnimation(), 0 );
				}
				else if( playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeRightAnimation() ) && !playerEntity.getCurrentAnimation().isPlaying() )
					playerEntity.playAnimation( playerEntity.getPlayerStrafeRightAnimationLoop() );
			}
			else if( direction == PlayerSettings.LEFT ) {
				if( playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeRightAnimationLoop() ) )
					playerEntity.playAnimationReverseOnce( playerEntity.getPlayerStrafeRightAnimation(), 0 );
				else if( ( !playerEntity.getCurrentAnimation().isPlaying() && !playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeLeftAnimation() ) ) || playerEntity.getCurrentAnimation().equals( playerEntity.getIdleAnimation() ) ) {
					playerEntity.playAnimationOnce( playerEntity.getPlayerStrafeLeftAnimation(), 0 );
				}
				else if( playerEntity.getCurrentAnimation().equals( playerEntity.getPlayerStrafeLeftAnimation() ) && !playerEntity.getCurrentAnimation().isPlaying() )
					playerEntity.playAnimation( playerEntity.getPlayerStrafeLeftAnimationLoop() );
			}
			else if( direction == PlayerSettings.UP ) {
				if( !playerEntity.getCurrentAnimation().isPlaying() )
					playerEntity.playAnimation( playerEntity.getIdleAnimation() );
			}
		}

		
		lastUpdateVelocityScalar = velocityScalar;
		
		//MOVEMENT
		if( idlingX ) idleX();
		if( idlingY ) idleY();

		if( playerEntity.getPos().getX() + playerEntity.getVelocity().getX() <= Settings.WINDOW_SIZE_W - 40 && playerEntity.getPos().getX() + playerEntity.getVelocity().getX() >= 20 ) {
			playerEntity.getPos().add( playerEntity.getVelocity().getX(), 0 );
			playerEntity.updateEmitter( true, false );
		}
		if( playerEntity.getPos().getY() + playerEntity.getVelocity().getY() <= Settings.WINDOW_SIZE_H - 40 && playerEntity.getPos().getY() + playerEntity.getVelocity().getY() >= 20 ) {			playerEntity.getPos().add( 0, playerEntity.getVelocity().getY() );
			playerEntity.updateEmitter( false, true );
		}
		this.idlingX = true;
		this.idlingY = true;
			
		
		//firing
		++firingDelay;
		if( firing ) {

		playerEntity.getEmitter().fire();
			
		firing = false;
		}
		
	}
		
	
	public void drawPlayer() {
		//hitbox update
		g.drawPlayer( playerEntity, playerEntity.getFrame() );
	}


}
