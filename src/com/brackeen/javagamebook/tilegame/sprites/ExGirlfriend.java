package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.codereflection.CodeReflection;
import com.brackeen.javagamebook.graphics.Animation;


public class ExGirlfriend extends Creature{
	  public ExGirlfriend(Animation left, Animation right,
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
		    	health =8;
		    	intelligent = true;
		    }
	  	

		    public float getMaxSpeed() {
		    	if(CodeReflection.isTracing() && SpritesPackageTracingEnabled.getSpritesPackageTracingEnabledInstance().isEnabled()) {
		        	if(CodeReflection.getAbstactionLevel()>=2)
		        	{//check to make sure it's this level of abstraction
		        		e.fillInStackTrace();		
		        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
		        								e.getStackTrace()[0].getMethodName());
		        	}
		    	}
		        return 0.1f * enemySpeedMultiplier;
		    }

		}
