package application.controller.enemies;

import java.util.ArrayList;
import java.util.List;
import application.controller.ai.RattlerAI;
import application.entities.Entity;
import application.entities.enemies.RattlerEntity;
import application.entities.enemies.SightSeekerEntity;
import application.model.GameState;
import application.settings.Settings;
import application.utils.Vector2D;
import application.view.SpaceOhGraphics;

public class EnemiesController implements RattlerAI
{

	List< Entity > killBuffer;
	GameState game;
	SpaceOhGraphics g;
	Vector2D playerPos;
	
	public EnemiesController( GameState game, SpaceOhGraphics g ) 
	{
		this.game = game;
		this.g = g;
		this.killBuffer = new ArrayList< Entity >();
	}
	
	public static void hurtEnemy( Entity entity, float dmg, boolean ignoreInvicibility ) {
		if( entity.getInvincibilityFrames() == 0 ) {
			entity.takeDmg( dmg );
			entity.setInvincibilityFrames( 120 );
			if( entity.getHurtAnimation() != null )
				entity.playAnimationOnce( entity.getHurtAnimation(), 0 );
		}
		else if( ignoreInvicibility ) {
			entity.takeDmg( dmg );
			if( entity.getHurtAnimation() != null )
				entity.playAnimationOnce( entity.getHurtAnimation(), 0 );
		}
	}
	
	public void update() {
		List< Entity > enemies = game.getEnemies();
		
		playerPos = game.getPlayerEntity().getPos();
		
		if( enemies.isEmpty() ) {
			return;
		}
		
		for( Entity entity : enemies  )
		{
			if( ( entity instanceof RattlerEntity ) ) {
			
			RattlerEntity rattlerEntity = ( RattlerEntity ) entity;
									
			//animation
			rattlerEntity.updateAnimation();
			
			if( !rattlerEntity.isAlive() ) {
				if( !rattlerEntity.getCurrentAnimation().equals( rattlerEntity.getDeathAnimation() ) ) 
					rattlerEntity.playAnimationOnce( rattlerEntity.getDeathAnimation(), 0 );
				else if( !rattlerEntity.getCurrentAnimation().isPlaying() )
					killBuffer.add( rattlerEntity );
			}
			
			if( rattlerEntity.getCurrentAnimation() == null || !rattlerEntity.getCurrentAnimation().isPlaying() )
				rattlerEntity.playAnimation( rattlerEntity.getIdleAnimation() );
						
			//velocity
			accelerateX( entity, playerPos );
			accelerateY( entity, playerPos);
			
			//health bar
			rattlerEntity.updateHealthBarPos();
			
			//movement
			move( entity );
			
			}
			
			else if( entity instanceof SightSeekerEntity ) {
				SightSeekerEntity sightSeekerEntity = ( SightSeekerEntity ) entity;
				
				//animation
				sightSeekerEntity.updateAnimation();
				
				if( !sightSeekerEntity.isAlive() ) {
						killBuffer.add( sightSeekerEntity );
				}
										
				//velocity
				accelerateX( entity, playerPos );
				accelerateY( entity, playerPos);
				
				//health bar
				sightSeekerEntity.updateHealthBarPos();
				
				//attack
				if( entity.getAttackCD() == 0 ) {
					entity.getEmitter().fire();
					entity.setAttackCD( 20 );
				}
				else entity.decrementAttackCD( 1 );
			}
			
			//invincibility
			if( entity.getInvincibilityFrames() > 0 )
				entity.setInvincibilityFrames( entity.getInvincibilityFrames() - 1 );
			
		}
	}
	
	public void killEnemies() {
		for( Entity entity : killBuffer ) 
			game.removeEnemy( entity );
		killBuffer.clear();
		
		
	}
	
	private void move( Entity entity ) {
		entity.getPos().add( entity.getVelocity() );
		if( entity.getPos().getX() > Settings.WINDOW_SIZE_W || entity.getPos().getX() < 0 || entity.getPos().getY() > Settings.WINDOW_SIZE_H || entity.getPos().getY() < 0 ) {
			entity.takeDmg( entity.getMaxHp() );
			entity.setVelocity( 0.0, 0.0 );
		}
	}

	public void drawEnemies() {
		g.drawEnemies( game.getEnemies() );
	}	
	
}
