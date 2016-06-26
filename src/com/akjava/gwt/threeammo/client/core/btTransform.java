package com.akjava.gwt.threeammo.client.core;


public class btTransform extends AmmoObject{
protected btTransform(){}

public final  native btVector3 getOrigin()/*-{
return this.getOrigin();
}-*/;

/**
 * dont' try to set to getted value, no effect
 * @param rotation
 */

public final  native btQuaternion getRotation()/*-{
return this.getRotation();
}-*/;


public final  native void setRotation(btQuaternion rotation)/*-{
this.setRotation(rotation);
}-*/;



//maybe this is no eefect
public final  native void getBasisRotation(btQuaternion q)/*-{
this.getBasis().getRotation(q);
}-*/;

/*
 * maybe initialize pos & rot for safe
 */
public final  native void setIdentity()/*-{
this.setIdentity();
}-*/;


}