package application.model;

import java.util.ArrayList;
import java.util.List;

import application.entities.Entity;
import application.entities.PlayerEntity;

public class GameState 
{
	private static GameState gameState;
	PlayerEntity playerEntity;
	List< Entity > enemies;
	List< Entity > projectiles;
	
	private GameState() 
	{
		enemies = new ArrayList< Entity >();
		projectiles = new ArrayList< Entity >();
	}
	
	public static GameState getInstance() {
		if( gameState == null )
			gameState = new GameState();
		return gameState;
	}
	
	public void setPlayer( PlayerEntity playerEntity ) {
		this.playerEntity = playerEntity;
	}
	
	public boolean addEnemy( Entity enemyEntity ) {
		return enemies.add( enemyEntity );
	}
	
	public boolean addProjectile( Entity projectileEntity ) {
		return projectiles.add( projectileEntity );
	}
	
	public List< Entity > getEnemies() {
		return enemies;
	}

	public PlayerEntity getPlayerEntity() {
		return playerEntity;
	}
	
	public List< Entity > getProjectiles() {
		return projectiles;
	}

	public void removeEnemy( Entity entity ) {
		enemies.remove( entity );	
	}
		
	public void removeProjectile( Entity projectileEntity ) {
		projectiles.remove( projectileEntity );
	}


}
