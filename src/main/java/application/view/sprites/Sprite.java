package application.view.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.twelvemonkeys.image.ResampleOp;

import application.view.animations.Animation;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;


public abstract class Sprite {
	//sprite properties
	private SnapshotParameters parameters;
	private Image spriteSheet;
	private Rectangle2D[] viewportArray;
	private Image[] spriteArray;
	private ImageView imageView;
	private int numCells;
	private int numHCells;
	private int numVCells;
	protected Animation[] animations;
	protected BufferedImage bufferedSpriteSheet;
	protected double sWidth; //sprite width
	protected double sHeight; //sprite height
	protected double xScale = 1.0;
	protected double yScale = 1.0;
	
	protected Sprite( BufferedImage bufferedSpriteSheet, double sWidth, double sHeight, double xScale, double yScale ) {
		//sprite properties
		this.sWidth = sWidth;
		this.sHeight = sHeight;
		this.xScale = xScale;
		this.yScale = yScale;
		this.imageView = new ImageView();
		imageView.setSmooth( false );
		this.bufferedSpriteSheet = bufferedSpriteSheet;
		imageView.setImage( resizedSpritesheet( bufferedSpriteSheet ) );
	    this.spriteSheet = imageView.getImage();
	    
	    //calculates a number of cells which should fit into the image
		numHCells = (int) ( spriteSheet.getWidth() / ( sWidth * xScale ) );
		numVCells = (int) ( spriteSheet.getHeight() / ( sHeight * yScale ) );
				
		numHCells += numHCells > 0 ? 0 : 1; 
		numVCells += numVCells > 0 ? 0 : 1; 
		numCells = numHCells * numVCells;
		
		//initializes arrays and parameters
		viewportArray = new Rectangle2D[ numCells ];
		spriteArray = new Image[ numCells ];
		parameters = new SnapshotParameters();
	    parameters.setFill( Color.TRANSPARENT );
	      
	    //creates views
		int counter = 0;
		for( int y = 0; y < numVCells; y++ )
			for( int x = 0; x < numHCells ; x++ ) {
				viewportArray[ counter++ ] = new Rectangle2D( x * sWidth * xScale, y * sHeight * yScale, sWidth * xScale, sHeight * yScale );
			}
		loadImages();
	}
	
	//resizing
	private Image resizedSpritesheet( BufferedImage bufferedSpriteSheet ) {
		ResampleOp rs = new ResampleOp( ( int ) ( bufferedSpriteSheet.getWidth() * xScale ),
												( int ) ( bufferedSpriteSheet.getHeight() * yScale ),
												ResampleOp.FILTER_BOX );
		return SwingFXUtils.toFXImage( rs.filter( bufferedSpriteSheet, null ), null );
	}
	
	//cuts the spritesheet into single sprites
	private void loadImages() {
		for( int i = 0; i < numCells; i++ ) {
			parameters.setViewport( viewportArray[ i ] );			
			spriteArray[ i ] = imageView.snapshot( parameters, null );				
		}	
	}
		
	//load sprite file
	protected static BufferedImage loadSprite( String Path ) {
		try {
			return ImageIO.read( PlayerSprite.class.getResource( Path ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	//getters	
	public Animation[] getAnimations() { return animations; }
	public Image getSpriteAt( int i ) {	return spriteArray[ i ]; }
	public Image[] getSpriteArray() { return spriteArray; }
	public double getWidth() { return sWidth * xScale; }
	public double getHeight() { return sHeight * yScale; }
	
}
