package com.akjava.gwt.threeammo.client.core;
public class btGeneric6DofSpringConstraint extends btGeneric6DofConstraint{
protected btGeneric6DofSpringConstraint(){}


public final  native void enableSpringAngular(boolean enabled)/*-{
this.enableSpring(3,enabled);
this.enableSpring(4,enabled);
this.enableSpring(5,enabled);
//btGeneric6DofConstraint 
}-*/;

public final  native void enableSpringLinear(boolean enabled)/*-{
this.enableSpring(0,enabled);
this.enableSpring(1,enabled);
this.enableSpring(2,enabled);
}-*/;

public final  native void enableSpringAll(boolean enabled)/*-{
this.enableSpring(0,enabled);
this.enableSpring(1,enabled);
this.enableSpring(2,enabled);
this.enableSpring(3,enabled);
this.enableSpring(4,enabled);
this.enableSpring(5,enabled);
}-*/;

//need and,how effect?
public final  native void enableFeedback(boolean enabled)/*-{
this.enableFeedback(0,enabled);
this.enableFeedback(1,enabled);
this.enableFeedback(2,enabled);
this.enableFeedback(3,enabled);
this.enableFeedback(4,enabled);
this.enableFeedback(5,enabled);
}-*/;

public final  native void setStiffnessAll(double stiffness)/*-{
this.setStiffness(0,stiffness);
this.setStiffness(1,stiffness);
this.setStiffness(2,stiffness);
this.setStiffness(3,stiffness);
this.setStiffness(4,stiffness);
this.setStiffness(5,stiffness);
}-*/;

/**
 * 
 * @param index  0-2 linear-xyz ,3-5 angular-xyz

 */

public final  native void enableSpring(int index,boolean enabled)/*-{
this.enableSpring(index,enabled);
}-*/;
/**
 * 
 * @param index  0-2 linear-xyz ,3-5 angular-xyz

 */
public final  native void setStiffness(int index,double stiffness)/*-{
this.setStiffness(index,stiffness);
}-*/;


//not exist?
//public final  native double getAngle(int axis)/*-{
//return this.getAngle(axis);
//}-*/;

 final  native void setEquilibriumPoint()/*-{
console.log("setEquilibriumPoint():skipped not seems support yet.constraint.setEquilibriumPoint is not a function");
console.log(this);

}-*/;

public final  native void setDampingAll(double damping)/*-{
this.setDamping(0,damping);
this.setDamping(1,damping);
this.setDamping(2,damping);
this.setDamping(3,damping);
this.setDamping(4,damping);
this.setDamping(5,damping);
}-*/;
/**
 * 
 * @param index  0-2 linear-xyz ,3-5 angular-xyz
 * @param damping
 */
public final  native void setDamping(int index,double damping)/*-{
this.setDamping(index,damping);
}-*/;


}