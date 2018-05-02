/*
 * Created on Nov 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.brackeen.javagamebook.tilegame.sprites;
import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.ScreenManager;
import com.brackeen.javagamebook.test.TestPackageTracingEnabled;
import com.brackeen.javagamebook.tilegame.ScriptManager;
import com.brackeen.javagamebook.util.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.brackeen.javagamebook.codereflection.*;

public class Dragon extends Creature {

	private static final int MILI_PER_SECOND = 1000; 
	private long initJumpInterval = 2 * MILI_PER_SECOND; 
	private long jumpInterval = initJumpInterval;
    protected ScreenManager screen;

    private Throwable e = new Throwable();
	/**
	 * @param left
	 * @param right
	 * @param deadLeft
	 * @param deadRight
	 */
	public Dragon(Animation left, Animation right, Animation deadLeft, Animation deadRight) 
	{
		super(left, right, deadLeft, deadRight);
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		
		this.jumpSpeed = -0.65f;
		this.trackPlayer=true;
		
		health = 10;
		
		//Make Boss intelligent
		intelligent = true;
		/*screen.restoreScreen();
        Graphics2D g = screen.getGraphics();

        this.draw(g); 
        */
	}
	
    public float getMaxSpeed() {
        return 0.15f * enemySpeedMultiplier;
    }
    
    public void update(long elapsedTime) {
		// Call super to maintain animation behavior
		super.update(elapsedTime);
		
		if((totalElapsedTime % jumpInterval)-elapsedTime<0){
			//Jump is on the ground
			jump();
			jumpInterval = initJumpInterval + RandomUtil.getRandomInt(200);
		}
	}
	/*
    public void draw(Graphics2D g)
	{
    	if(CodeReflection.isTracing() && TestPackageTracingEnabled.getTestPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=3)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
		String s = "BAD GUY";

		g.setColor(Color.BLACK);
		g.setFont(new Font(g.getFont().getName(),g.getFont().getStyle(),15));
		//updateFontSize(g);
		g.drawString(s, getX(), getY() + 15 );
		//g.fillRect(width-130,30,95,45);
		
		
		/*
		if(drawLevelComplete)
		{
			g.setColor(Color.BLUE);	
			g.setFont(new Font(g.getFont().getName(),g.getFont().getStyle(),35));
			g.fillRoundRect(0, height/2-100, width-1,200,50,50);

			g.setColor(Color.GREEN);
			for(int x=0; x<10;x++)
				g.drawRoundRect(0+x, height/2-100+x, width-1-2*x,200-2*x,50,50);

			g.drawString("Level Complete!!!!", width/2-150, height/2);
		}
		
		g.setColor(oldForeColor);
		//resetFontSize(g);
		 * 
		 
		
	}
	*/
}