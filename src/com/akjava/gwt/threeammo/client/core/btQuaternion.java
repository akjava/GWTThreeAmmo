package com.akjava.gwt.threeammo.client.core;
import com.akjava.gwt.three.client.js.math.Quaternion;
public class btQuaternion extends AmmoObject{
protected btQuaternion(){}

public final  native double x()/*-{
return this.x();
}-*/;

public final  native double y()/*-{
return this.y();
}-*/;

public final  native double z()/*-{
return this.z();
}-*/;

public final  native double w()/*-{
return this.w();
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
public final  native void setW(double w)/*-{
this.setW(w);
}-*/;

public final  native void set(double x,double y,double z,double w)/*-{
this.setX(x);
this.setY(y);
this.setZ(z);
this.setW(w);
}-*/;

public final  native void copy(Quaternion quaternion)/*-{
this.setX(quaternion.x);
this.setY(quaternion.y);
this.setZ(quaternion.z);
this.setW(quaternion.w);
}-*/;

public final  native void copyTo(Quaternion quaternion)/*-{
	quaternion.set(this.x(),this.y(),this.z(),this.w());
}-*/;



}