package com.brackeen.javagamebook.tilegame;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.tilegame.sprites.*;
import com.brackeen.javagamebook.codereflection.*;


/**
    The ResourceManager class loads and manages tile Images and
    "host" Sprites used in the game. Game Sprites are cloned from
    "host" Sprites.
*/
public class ResourceManager {

    private ArrayList tiles;
    private int currentMap;
    private GraphicsConfiguration gc;

    // host sprites used for cloning
    private Sprite playerSprite;
    private Sprite musicSprite;
    private Sprite coinSprite;
    private Sprite goalSprite;
    private Sprite warpSprite;
    private Sprite healthSprite;
    
    private Sprite[] enemySprites;
    private ScriptManager s;
    private Throwable e = new Throwable();

    /**
        Creates a new ResourceManager with the specified
        GraphicsConfiguration.
    */
    public ResourceManager(GraphicsConfiguration gc) {
        this.gc = gc;
        
        s = ScriptManager.getScriptManagerInstance();
                
        currentMap = -1;	//flag value
        
        loadTileImages();
        loadCreatureSprites();
        loadPowerUpSprites(); 
    }


    /**
        Gets an image from the images/ directory.
    */
    public Image loadImage(String name) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        String filename = "images/" + name;
        return new ImageIcon(filename).getImage();
    }


    public Image getMirrorImage(Image image) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return getScaledImage(image, -1, 1);
    }


    public Image getFlippedImage(Image image) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        return getScaledImage(image, 1, -1);
    }


    private Image getScaledImage(Image image, float x, float y) {

    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // set up the transform
        AffineTransform transform = new AffineTransform();
        transform.scale(x, y);
        transform.translate(
            (x-1) * image.getWidth(null) / 2,
            (y-1) * image.getHeight(null) / 2);

        // create a transparent (not translucent) image
        Image newImage = gc.createCompatibleImage(
            image.getWidth(null),
            image.getHeight(null),
            Transparency.BITMASK);

        // draw the transformed image
        Graphics2D g = (Graphics2D)newImage.getGraphics();
        g.drawImage(image, transform, null);
        g.dispose();

        return newImage;
    }


    public TileMap loadNextMap() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        TileMap map = null;
        
        while (map == null) {
        	currentMap++;
            try {
                map = loadMap(
                    "maps/"+s.getMap(currentMap));
            }
            catch (IOException ex) {
                if (currentMap == 1) {
                    // no maps to load!
                    return null;
                }
                currentMap = 0;
                map = null;
            }
        }

        return map;
    }
    public TileMap loadWarpMap(int x) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        TileMap map = null;
        currentMap=x;
        
        while (map == null) {
            try {
            	if(s.getMap(currentMap)!=null)
            		map = loadMap("maps/"+s.getMap(currentMap));
            	else{ 
            		map = null;
            		currentMap = 1;
            	}
            }
            catch (IOException ex) {
                if (currentMap == 1) {
                    // no maps to load!
                    return null;
                }
                currentMap = 1;
                map = null;
            }
        }
        return map;
    }


    public TileMap reloadMap() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        try {
            return loadMap(
            		 "maps/"+s.getMap(currentMap));
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private TileMap loadMap(String filename)
        throws IOException
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        // read every line in the text file into the list
        BufferedReader reader = new BufferedReader(
            new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }

        // parse the lines to create a TileEngine
        height = lines.size();
        TileMap newMap = new TileMap(width, height);
        for (int y=0; y<height; y++) {
            String line = (String)lines.get(y);
            for (int x=0; x<line.length(); x++) {
                char ch = line.charAt(x);

                // check if the char represents tile A, B, C etc.
                int tile = ch - 'A';
                if (tile >= 0 && tile < tiles.size()) {
                    newMap.setTile(x, y, (Image)tiles.get(tile));
                }

                // check if the char represents a sprite
                else if (ch == 'o') {
                    addSprite(newMap, coinSprite, x, y);
                }
                else if (ch == '!') {
                    addSprite(newMap, musicSprite, x, y);
                }
                else if (ch == '*') {
                    addSprite(newMap, goalSprite, x, y);
                }
                else if (ch == '^') {
                    addSprite(newMap, healthSprite, x, y);
                }
                else if ((ch>='0')&&(ch<='9')){
                	((PowerUp.Warp)warpSprite).setWarpValue((ch-'0')+2+currentMap);
                	addSprite(newMap, warpSprite, x, y);
                }
                else	
                	for(char i='a'; i<'a'+s.getNumberOfEnemies();i++)
                		if (ch == i) {
                			addSprite(newMap, enemySprites[(int)(i-'a')], x, y);
                		}
            }
        }

        // add the player to the map
        Sprite player = (Sprite)playerSprite.clone();
        player.setX(TileMapRenderer.tilesToPixels(3));
        player.setY(0);
        newMap.setPlayer(player);

        return newMap;
    }


    private void addSprite(TileMap map,
        Sprite hostSprite, int tileX, int tileY)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        if (hostSprite != null) {
            // clone the sprite from the "host"
            Sprite sprite = (Sprite)hostSprite.clone();
            
            if(hostSprite instanceof PowerUp.Warp)	//if it's a warp, add warp value
            	((PowerUp.Warp)sprite).setWarpValue(((PowerUp.Warp)hostSprite).getWarpValue());
          
            if(hostSprite instanceof Boss)
            	((Boss)sprite).setHealth(10);
            
            // center the sprite
            sprite.setX(
                TileMapRenderer.tilesToPixels(tileX) +
                (TileMapRenderer.tilesToPixels(1) -
                sprite.getWidth()) / 2);

            // bottom-justify the sprite
            sprite.setY(
                TileMapRenderer.tilesToPixels(tileY + 1) -
                sprite.getHeight());

            // add it to the map
            map.addSprite(sprite);
        }
    }


    // -----------------------------------------------------------
    // code for loading sprites and images
    // -----------------------------------------------------------


    public void loadTileImages() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ directory
        tiles = new ArrayList();
        char ch = 'A';
        while (true) {
            String name = "tile_" + ch + ".png";
            File file = new File("images/" + name);
            if (!file.exists()) {
                break;
            }
            tiles.add(loadImage(name));
            ch++;
        }
    }

    public void loadCreatureSprites() {
    	
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	int sprites, enemies, imageIndex=0;
    	
    	sprites=s.getNumberOfSprites();		//get total number of sprites
    	Image[][] images = new Image[4][];

        // load left-facing images
        images[0] = new Image[sprites] ;
        for(int x=0;x<sprites;x++)
        	images[0][x]= loadImage(s.getSpriteImage(x));

        images[1] = new Image[images[0].length];
        images[2] = new Image[images[0].length];
        images[3] = new Image[images[0].length];
        
        for (int i=0; i<images[0].length; i++) {
            // right-facing images
            images[1][i] = getMirrorImage(images[0][i]);
            // left-facing "dead" images
            images[2][i] = getFlippedImage(images[0][i]);
            // right-facing "dead" images
            images[3][i] = getFlippedImage(images[1][i]);
        }

        // create creature animations
        Animation[] playerAnim = new Animation[4];
        /*Animation[] flyAnim = new Animation[4];
        Animation[] grubAnim = new Animation[4];
        Animation[] monkeyAnim = new Animation[4];*/
        enemies= s.getNumberOfEnemies();
        
        Animation[][] enemyAnim =new Animation[enemies][4]; 
        
        //Create Animation
        for (int i=0; i<4; i++) {
        	imageIndex=0;
            playerAnim[i] = createPlayerAnim(
                images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            for(int x=0; x<enemies;x++)
            	if(s.getArchType(x).compareTo("grub")==0)
                    enemyAnim[x][i] = createGrubAnim(
                            images[i][imageIndex++], images[i][imageIndex++]);	
            	else
            	if(s.getArchType(x).compareTo("fly")==0)
            		enemyAnim[x][i] = createFlyAnim(
            			images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("monkey")==0)
            		enemyAnim[x][i]=createMonkeyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("boss")==0)
            		enemyAnim[x][i]=createBossAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("sinuousfly")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("homingfly")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("randomfly")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else
            	if(s.getArchType(x).compareTo("balloon")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	//create new enemies anim here
            	else 
            	if(s.getArchType(x).compareTo("superfly")==0)
            		enemyAnim[x][i]=createFlyAnim(
            				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("lazyfly")==0)
                		enemyAnim[x][i]=createFlyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("slug")==0)
                		enemyAnim[x][i]=createMonkeyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("thugglide")==0)
                		enemyAnim[x][i]=createMonkeyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("firegiant")==0)
                		enemyAnim[x][i]=createMonkeyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("icegiant")==0)
                		enemyAnim[x][i]= createIceGiantAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++],
        						images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++],
								images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++],
								images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("spider")==0)
                		enemyAnim[x][i]=createMonkeyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("redDragon")==0)
                		enemyAnim[x][i]=createFlyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("wyvern")==0)
                		enemyAnim[x][i]=createFlyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("zombie")==0)
                		enemyAnim[x][i]=createMonkeyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
               	else 
                	if(s.getArchType(x).compareTo("demon")==0)
                		enemyAnim[x][i]=createFlyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("saber")==0)
                		enemyAnim[x][i]=createAlienAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("alien")==0)
                		enemyAnim[x][i]=createAlienAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("dragon")==0)
                		enemyAnim[x][i]=createDragonAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++],
                				images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("boku")==0)
                		enemyAnim[x][i]=createBokuAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                				images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("darkangel")==0)
                		enemyAnim[x][i]=createFlyAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++]);
            	else 
                	if(s.getArchType(x).compareTo("spirit")==0)
                		enemyAnim[x][i]= createSpiritAnim(
                				images[i][imageIndex++], images[i][imageIndex++], images[i][imageIndex++], 
                				images[i][imageIndex++], images[i][imageIndex++]);

    }

        // create creature sprites
        playerSprite = new Player(playerAnim[0], playerAnim[1],
            playerAnim[2], playerAnim[3]);
       
        enemies = s.getNumberOfEnemies();      
        enemySprites= new Creature[enemies]; 	//initialize space

        //Create Enemies
        for(int x=0; x<enemies;x++)
        	if(s.getArchType(x).compareTo("grub")==0)
        		enemySprites[x]=new Grub(enemyAnim[x][0], enemyAnim[x][1],
        				enemyAnim[x][2], enemyAnim[x][3]);
        	else
        	if(s.getArchType(x).compareTo("fly")==0)
        		enemySprites[x] = new Fly(enemyAnim[x][0], enemyAnim[x][1],
        				enemyAnim[x][2], enemyAnim[x][3]);
        	else
        	if(s.getArchType(x).compareTo("monkey")==0)
        		enemySprites[x]=new Monkey(enemyAnim[x][0], enemyAnim[x][1],
        				enemyAnim[x][2], enemyAnim[x][3]);   
        	else
            if(s.getArchType(x).compareTo("boss")==0)
            	enemySprites[x]=new Boss(enemyAnim[x][0], enemyAnim[x][1],
            			enemyAnim[x][2], enemyAnim[x][3]); 
            else
            if(s.getArchType(x).compareTo("sinuousfly")==0)
            	enemySprites[x]=new SinuousFly(enemyAnim[x][0], enemyAnim[x][1],
            			enemyAnim[x][2], enemyAnim[x][3]);
            else
            if(s.getArchType(x).compareTo("homingfly")==0)
            	enemySprites[x]=new HomingFly(enemyAnim[x][0], enemyAnim[x][1],
            			enemyAnim[x][2], enemyAnim[x][3]);
            else
            if(s.getArchType(x).compareTo("randomfly")==0)
            	enemySprites[x]=new RandomFly(enemyAnim[x][0], enemyAnim[x][1],
            			enemyAnim[x][2], enemyAnim[x][3]);
            else
            if(s.getArchType(x).compareTo("balloon")==0)
            	enemySprites[x]=new Balloon(enemyAnim[x][0], enemyAnim[x][1],
            			enemyAnim[x][2], enemyAnim[x][3]);
        	//create new enemies here
            else
            if(s.getArchType(x).compareTo("superfly")==0)
            	enemySprites[x]=new SuperFly(enemyAnim[x][0], enemyAnim[x][1],
            			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("lazyfly")==0)
                	enemySprites[x]=new LazyFly(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("slug")==0)
                	enemySprites[x]=new Slug(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("thugglide")==0)
                	enemySprites[x]=new ThugGlide(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("firegiant")==0)
                	enemySprites[x]=new FireGiant(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("icegiant")==0)
                	enemySprites[x]=new IceGiant(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("spider")==0)
                	enemySprites[x]=new Spider(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("wyvern")==0)
                	enemySprites[x]=new Wyvern(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("redDragon")==0)
                	enemySprites[x]=new RedDragon(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("zombie")==0)
                	enemySprites[x]=new Zombie(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("demon")==0)
                	enemySprites[x]=new Demon(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("saber")==0)
                	enemySprites[x]=new Saber(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("alien")==0)
                	enemySprites[x]=new Alien(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("dragon")==0)
                	enemySprites[x]=new Dragon(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("boku")==0)
                	enemySprites[x]=new Boku(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("darkangel")==0)
                	enemySprites[x]=new DarkAngel(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
            else
                if(s.getArchType(x).compareTo("spirit")==0)
                	enemySprites[x]=new Spirit(enemyAnim[x][0], enemyAnim[x][1],
                			enemyAnim[x][2], enemyAnim[x][3]);
       }
    
    public String levelBackground()
    {	//return the image name of the background for the current map
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	if(currentMap==-1) //first instance load
    		return(s.getLevelImage(1));
    	return(s.getLevelImage(currentMap));
    }
    
    public String levelMusic()
    {	//return the music name for the current map
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	if(currentMap==-1) //first instance load
    		return(s.getlevelMusic(1));
    	return(s.getlevelMusic(currentMap));
    }
    
    public int getLevel()
    {	//return the current level the player is on
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(currentMap);
    }
    
    public String getBoopSound()
    {	//return the sound for jumping on an enemy
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("boop"));
    }
    
    public String getStarSound()
    {	//return the sound for the star
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("star"));
    }
    
    public String getNoteSound()
    {	//return the sound for the note
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("note"));
    }
    
    public String getWarpSound()
    {	//return the sound for the warp
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("warp"));
    }
    
    public String getEndOfLevelSound()
    {	//return the sound for the warp
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("endoflevel"));
    }
    
    public String getDieSound()
    {	//return the sound for the player dying
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("die"));
    }
    
    public String getHealthSound()
    {	//return the sound for receiving a health powerup
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("health"));
    }
    
    public String getHurtSound()
    {	//return the sound for receiving damage, but not dying
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s.getSoundByReference("hurt"));
    }
    
    public ScriptManager getScriptClass()
    {	//return your instance of s
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	return(s);
    }
    
    
    private Animation createPlayerAnim(Image player1,
        Image player2, Image player3, Image player4, Image player5,
        Image player6, Image player7, Image player8, Image player9 )
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(player1, 250);
        anim.addFrame(player2, 250);
        anim.addFrame(player3, 250);
        anim.addFrame(player4, 250);
        anim.addFrame(player5, 250);
        anim.addFrame(player6, 250);
        anim.addFrame(player7, 250);
        anim.addFrame(player8, 250);
        anim.addFrame(player9, 250);
        return anim;
    }


    private Animation createFlyAnim(Image img1, Image img2,
        Image img3)
    {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 50);
        anim.addFrame(img2, 50);
        anim.addFrame(img3, 50);
        anim.addFrame(img2, 50);
        return anim;
    }


    private Animation createGrubAnim(Image img1, Image img2) {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        Animation anim = new Animation();
        anim.addFrame(img1, 250);
        anim.addFrame(img2, 250);
        return anim;
    }

    //TODO Create animation sequence for creature
    private Animation createMonkeyAnim(Image img1, Image img2,
            Image img3)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 50);
            //Up anim state
            anim.addFrame(img2, 50);
            //Normal state
            anim.addFrame(img1, 50);
            //down anim state
            anim.addFrame(img3, 50);
            return anim;
        }
    //TODO Create animation sequence for creature
    private Animation createIceGiantAnim(Image img1, Image img2,
            Image img3, Image img4, Image img5, Image img6, Image img7,
            Image img8, Image img9, Image img10)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 250);
            //Up anim state
            anim.addFrame(img2, 250);
            anim.addFrame(img3, 250);
            //down anim state
            anim.addFrame(img4, 250);
            anim.addFrame(img5, 250);
            //Up anim state
            anim.addFrame(img6, 250);
            //Normal state
            anim.addFrame(img7, 250);
            //down anim state
            anim.addFrame(img8, 250);          
            //Up anim state
            anim.addFrame(img9, 250);
            //Normal state
            anim.addFrame(img10, 250);
            return anim;
        } 
    	//TODO Create animation sequence for creature
    	private Animation createSpiritAnim(Image img1, Image img2,
            Image img3, Image img4, Image img5)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 250);
            //Up anim state
            anim.addFrame(img2, 250);
            anim.addFrame(img3, 250);
            //down anim state
            anim.addFrame(img4, 250);
            anim.addFrame(img5, 250);
            return anim;
        } 
    //TODO Create animation sequence for creature
    private Animation createDragonAnim(Image img1, Image img2,
            Image img3, Image img4, Image img5, Image img6, Image img7,
            Image img8)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 150);
            //Up anim state
            anim.addFrame(img2, 150);
            anim.addFrame(img3, 150);
            //down anim state
            anim.addFrame(img4, 150);
            anim.addFrame(img5, 150);
            //Up anim state
            anim.addFrame(img6, 150);
            //Normal state
            anim.addFrame(img7, 150);
            //down anim state
            anim.addFrame(img8, 150);          
            return anim;
        }
    //TODO Create animation sequence for creature
    private Animation createBokuAnim(Image img1, Image img2,
            Image img3, Image img4, Image img5, Image img6, Image img7,
            Image img8, Image img9, Image img10, Image img11, Image img12,
            Image img13, Image img14, Image img15, Image img16)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 150);
            //Up anim state
            anim.addFrame(img2, 150);
            anim.addFrame(img3, 150);
            //down anim state
            anim.addFrame(img4, 150);
            anim.addFrame(img5, 150);
            //Up anim state
            anim.addFrame(img6, 150);
            //Normal state
            anim.addFrame(img7, 150);
            //down anim state
            anim.addFrame(img8, 150);          
            //Up anim state
            anim.addFrame(img9, 150);
            //Normal state
            anim.addFrame(img10, 150);
            //Normal state
            anim.addFrame(img11, 150);
            //Up anim state
            anim.addFrame(img12, 150);
            anim.addFrame(img13, 150);
            //down anim state
            anim.addFrame(img14, 150);
            anim.addFrame(img15, 150);
            //Up anim state
            anim.addFrame(img16, 150);
            return anim;
        } 
    //TODO Create animation sequence for creature
    private Animation createAlienAnim(Image img1, Image img2,
            Image img3)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 150);
            //Up anim state
            anim.addFrame(img2, 150);
            //down anim state
            anim.addFrame(img3, 150);
            return anim;
        }
    private Animation createBossAnim(Image img1, Image img2,
            Image img3)
        {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
            Animation anim = new Animation();
            //Normal state
            anim.addFrame(img1, 50);
            //Up anim state
            anim.addFrame(img2, 50);
            //Normal state
            anim.addFrame(img1, 50);
            //down anim state
            anim.addFrame(img3, 50);
         
            return anim;
        }
    
    private void loadPowerUpSprites() {
    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=0)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        // create "goal" sprite
        Animation anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("eol",1)), 150);
        anim.addFrame(loadImage(s.getItemImage("eol",2)), 150);
        anim.addFrame(loadImage(s.getItemImage("eol",3)), 150);
        anim.addFrame(loadImage(s.getItemImage("eol",4)), 150);
        goalSprite = new PowerUp.Goal(anim);

        // create "star" sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("coin",1)), 100);
        anim.addFrame(loadImage(s.getItemImage("coin",2)), 100);
        anim.addFrame(loadImage(s.getItemImage("coin",3)), 100);
        anim.addFrame(loadImage(s.getItemImage("coin",4)), 100);
        coinSprite = new PowerUp.Star(anim);

        // create "music" sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("note",1)), 150);
        anim.addFrame(loadImage(s.getItemImage("note",2)), 150);
        anim.addFrame(loadImage(s.getItemImage("note",3)), 150);
        anim.addFrame(loadImage(s.getItemImage("note",4)), 150);
        musicSprite = new PowerUp.Music(anim);
        
        // create warp sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("warp",1)), 55);
        anim.addFrame(loadImage(s.getItemImage("warp",2)), 55);
        anim.addFrame(loadImage(s.getItemImage("warp",3)), 55);
        anim.addFrame(loadImage(s.getItemImage("warp",4)), 55);
        warpSprite = new PowerUp.Warp(anim);
        
        // create health sprite
        anim = new Animation();
        anim.addFrame(loadImage(s.getItemImage("health",1)),100);
        anim.addFrame(loadImage(s.getItemImage("health",2)),100);
        anim.addFrame(loadImage(s.getItemImage("health",3)),100);
        anim.addFrame(loadImage(s.getItemImage("health",4)),100);
        healthSprite = new PowerUp.Health(anim);
        
    }
	 public void setLevelMappingFile(String scriptFile)
	 {	//This tells what script file to use for level loading
	    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	s.setLevelMappingFile(scriptFile);
	 }
	 
	 public String getLevelMappingFile()
	 {	//Tell what script file is being used to load levels
	    	if(CodeReflection.isTracing() && TilegamePackageTracingEnabled.getTilegamePackageTracingEnabledInstance().isEnabled()) {
	        	if(CodeReflection.getAbstactionLevel()>=0)
	        	{//check to make sure it's this level of abstraction
	        		e.fillInStackTrace();		
	        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
	        								e.getStackTrace()[0].getMethodName());
	        	}
	    	}
	 	return(s.getLevelMappingFile());
	 }

}
