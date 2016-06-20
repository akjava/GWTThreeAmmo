package com.akjava.gwt.threeammo.client.core;
import com.akjava.gwt.three.client.js.math.Vector3;
public class btVector3 extends AmmoObject{
protected btVector3(){}

public final  native double x()/*-{
return this.x();
}-*/;

public final  native double y()/*-{
return this.y();
}-*/;

public final  native double z()/*-{
return this.z();
}-*/;


public final  native void setX(double x)/*-{
this.setX(x);
}-*/;

public final  native void setY(double y)/*-{
this.setY(y);
}-*/;

public final  native void setZ(double z)/*-{
this.setZ(z);
}-*/;

public final  native void set(double x,double y,double z)/*-{
this.setX(x);
this.setY(y);
this.setZ(z);
}-*/;

public final  native void copy(Vector3 vector)/*-{
this.setX(vector.x);
this.setY(vector.y);
this.setZ(vector.z);
}-*/;

public final  native void copyTo(Vector3 vector)/*-{
vector.x=this.x();
vector.y=this.y();
vector.z=this.z();
}-*/;
}