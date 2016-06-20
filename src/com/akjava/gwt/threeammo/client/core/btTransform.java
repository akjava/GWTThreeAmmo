package com.akjava.gwt.threeammo.client.core;


public class btTransform extends AmmoObject{
protected btTransform(){}

public final  native btVector3 getOrigin()/*-{
return this.getOrigin();
}-*/;

public final  native btQuaternion getRotation()/*-{
return this.getRotation();
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