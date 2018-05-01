package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.codereflection.*;

/**
    A Fly is a Creature that fly slowly in the air.
*/
public class Wyvern extends Fly {

//    private Throwable e = new Throwable();
    
    public Wyvern(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
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
    	health = 3;
        flying = true;
    }

    public void update(long elapsedTime) 
    {//Override the call to update
    	
		// Call super to maintain animation behavior
		super.update(elapsedTime);
		
		//Code Tracing
    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
        	if(CodeReflection.getAbstactionLevel()>=4)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
    	
        if (!onGround && isAlive()) 
        {
            //onGround = false;
        	setHealth(3);
            setVelocityX(0.2f * enemySpeedMultiplier);
            setVelocityY(0.1f * enemySpeedMultiplier);
        }
	}
}
