package com.akjava.gwt.threeammo.client.core;


public class btCollisionShape extends AmmoObject{
protected btCollisionShape(){}



public final  native btSphereShape castToSphereShape()/*-{
return this;
}-*/;


public final  native void setLocalScaling(btVector3 scalling)/*-{
this.setLocalScaling(scalling);
}-*/;

}