package application.entities;

import application.entities.emitter.Emitter;
import application.utils.Vector2D;
import application.view.animations.Animation;
import application.view.sprites.HealthBarSprite;
import application.view.sprites.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public abstract class Entity
{
	protected Sprite sprite;
	protected Rectangle2D hitbox;
	protected String entityType;
	protected Vector2D velocity;
	protected Vector2D pos;
	protected Vector2D drawCoords;
	protected Vector2D direction;
	protected Emitter emitter;
	
	//model stuff
	protected float maxHp;
	protected float hp;
	protected float dmg;
	protected float speed;
	protected float bodyDmg;
	protected float maxSpeed;
	protected float brakes;
	protected int attackSpeed;
	protected boolean invincible;
	protected int invincibilityFrames;
	protected int attackCD;
	
	//sprite stuff
	protected Animation currentAnimation;
	protected Animation[] animations;
	protected Image frame;
	
	//health bar
	protected HealthBarEntity healthBar;
	
		
	//To use with simple entities such as health bars, etc
	protected Entity( Entity entity, Sprite sprite ) 
	{
		//position and movement stuff
		this.pos = entity.pos;
		this.drawCoords = new Vector2D(); 
		this.sprite = sprite;
						
		//sprite and animation stuff
		this.frame = sprite.getSpriteAt( 0 );
		if( hasAnimation() )
			initEntityAnimations();
		
	}
	
	//standard constructor
	protected Entity( Sprite sprite ) 
	{
		//position and movement stuff
		this.pos = new Vector2D();
		this.drawCoords = new Vector2D(); 
		this.velocity = new Vector2D();
		this.sprite = sprite;
		this.hitbox = new Rectangle2D( 0, 0, 0, 0 );
		this.direction = new Vector2D();
		this.invincible = false;
				
		//model properties stuff
		this.maxHp = getBaseHp();
		this.hp = this.maxHp;
		this.speed = getBaseSpeed();
		this.dmg = getBaseDmg();
		this.bodyDmg = getBaseBodyDmg();
		this.maxSpeed = getBaseMaxSpeed();
		this.brakes = getBaseBrakes();
		this.attackSpeed = getBaseAttackSpeed();
		this.invincibilityFrames = 0;
		this.attackCD = 0;
		
		//sprite and animation stuff
		this.frame = sprite.getSpriteAt( 0 );
		if( hasAnimation() )
			initEntityAnimations();
		
		//health bar
		if( hasHealthBar() )
			this.healthBar = new HealthBarEntity( this, HealthBarSprite.getInstance() );

	}
	
	
	
	//override to give your entity a health bar
	protected boolean hasHealthBar() { return false; }

	//hitbox
	public void createHitbox() { this.hitbox = new Rectangle2D( getDrawCoords().getX() + getOffX(), getDrawCoords().getY() + getOffY(), getHitboxWidth(), getHitBoxHeight() ); }
	protected double getOffX() { return 0; }	
	protected double getOffY() { return 0; }
	protected double getHitBoxHeight() { return sprite.getHeight(); }
	protected double getHitboxWidth() { return sprite.getWidth(); }
	protected double getHitboxWidthScale() { return 1.0; }
	protected double getHitboxHeightScale() { return 1.0; }
	public Rectangle2D getHitbox() { return hitbox; }	
	public void setHitbox( Rectangle2D hitbox ) { this.hitbox = hitbox; }
	
	//movement and position
	public Vector2D getPos() { return pos; }		
	public Vector2D getVelocity() { return velocity; }
	public void setPos( Vector2D pos ) { this.pos = new Vector2D ( pos ); }
	public void setPos( double x, double y ) { this.pos.setX( x ); this.pos.setY( y ); }
	public void setVelocity( Vector2D velocity ) { this.velocity = new Vector2D( velocity ); }
	public void setVelocity( double x, double y ) { this.velocity.setX( x ); this.velocity.setY( y ); }
	public Vector2D getDirection() { return direction; }
	public void setDirection( Vector2D direction ) { this.direction = new Vector2D( direction ); }
	
	//properties
	public String getEntityType() { return entityType; }	
	public void takeDmg( float dmg ) { this.hp -= dmg; }	
	public boolean isAlive() { return hp > 0; }
	public float getHp() { return hp; }
	public void setHp(float hp) { this.hp = hp;	}
	public float getDmg() {	return dmg;	}
	public void setDmg(float dmg) {	this.dmg = dmg; }
	public float getSpeed() { return speed; }
	public void setSpeed(float speed) {	this.speed = speed; }
	public float getBodyDmg() {	return bodyDmg;	}
	public void setBodyDmg(float bodyDmg) {	this.bodyDmg = bodyDmg; }
	public float getMaxSpeed() { return maxSpeed; }
	public void setMaxSpeed(float maxSpeed) { this.maxSpeed = maxSpeed;	}
	public float getBrakes() { return brakes; }
	public void setBrakes(float brakes) { this.brakes = brakes;	}
	public float getAttackSpeed() {	return attackSpeed;	}	
	public void setAttackSpeed( int attackSpeed ) {	this.attackSpeed = attackSpeed;	}
	public float getMaxHp() { return maxHp;	}
	public void setMaxHp(float maxHp) {	this.maxHp = maxHp;	}
	public boolean isInvincible() { return invincible; }
	public void setInvincible( boolean i ) { invincible = i; }
	public int getInvincibilityFrames() { return invincibilityFrames; }
	public void setInvincibilityFrames(int invincibilityFrames) { this.invincibilityFrames = invincibilityFrames; }
	public int getAttackCD() { return attackCD; }
	public void setAttackCD( int attackCD ) {	this.attackCD = attackCD; if( attackCD < 0 ) attackCD = 0; }
	public void decrementAttackCD( int frames ) { this.attackCD -= frames; if( attackCD < 0 ) attackCD = 0; }
	
	//animation and drawing
	public void playAnimationOnce( Animation animation, int lastFrame ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.playOnce( lastFrame );
	}
	public void playAnimationReverseOnce( Animation animation, int lastFrame ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.playOnceReverse( lastFrame ); 
	}
		public void playAnimationReverse( Animation animation ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.playReverse(); 
	}
		public void playAnimation( Animation animation ) { 
		if( this.currentAnimation.equals( animation ) ) return;
		setCurrentAnimation( animation );
		currentAnimation.play();
	}
	public void setCurrentAnimation( Animation animation ) {
		animation.reset();
		this.currentAnimation = animation;
	}	
	public Animation getCurrentAnimation() { return currentAnimation; }
	public Image getFrame() { return frame; }
	public void updateAnimation() {
		currentAnimation.nextFrame(); 
		this.frame = currentAnimation.getFrame();
	}
	public Vector2D getDrawCoords() {
		this.drawCoords = new Vector2D( ( pos.getX() - ( this.frame.getWidth() / 2 ) ), ( pos.getY() - ( this.frame.getHeight() / 2 ) ) );
		return drawCoords;
	}
	private void initEntityAnimations() {
		this.animations = new Animation[ sprite.getAnimations().length ]; 
		for( int i = 0; i < sprite.getAnimations().length; i++ ) {
			animations[ i ] = new Animation( sprite.getAnimations()[ i ] );
		}
		this.currentAnimation = animations[ 0 ];
	}
	protected boolean hasAnimation() {
		return false;
	}
	
	//emitter
	public Emitter getEmitter() { return emitter; }
	public void setEmitter( Emitter emitter ) { this.emitter = emitter; }
	
	//base animations
	public Animation getIdleAnimation() { return null; }
	public Animation getHurtAnimation() { return null; }
	public Animation getSpawnAnimation() { return null; }
	public Animation getDeathAnimation() { return null; }
	
	//health bar
	public HealthBarEntity getHealthBar() {
		return healthBar;
	}
	public void updateHealthBarPos() {
		healthBar.setPos( this.pos );
	}
	public boolean drawHealthBar() {
		if( hasHealthBar() && hp > 0 )
			return true;
		return false;
	}
	
	//utils
	protected float getBaseHp() { return 5.0f; }
	protected float getBaseSpeed() { return 2.0f; }
	protected float getBaseDmg() { return 1.0f;	}
	protected float getBaseBodyDmg() { return 1.0f; }
	protected float getBaseMaxSpeed() { return 1.0f; }
	protected float getBaseBrakes() { return 1.0f; }
	protected int getBaseAttackSpeed() { return 40; }
	
			
}
