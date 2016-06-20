package com.akjava.gwt.threeammo.client.core;


/**
 * see http://bulletphysics.org/Bullet/BulletFull/classbtMotionState.html
 * @author aki
 *
 */
public class btMotionState extends AmmoObject{
	
protected btMotionState(){}

/**
 * 
 * @param transform
 */
public final  native void getWorldTransform(btTransform transform)/*-{
this.getWorldTransform(transform);
}-*/;

public final  native void setWorldTransform (btTransform transform)/*-{
return this.setWorldTransform (transform);
}-*/;

}