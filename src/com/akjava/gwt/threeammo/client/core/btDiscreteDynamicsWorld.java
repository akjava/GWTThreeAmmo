package com.akjava.gwt.threeammo.client.core;


public class btDiscreteDynamicsWorld extends AmmoObject{
protected btDiscreteDynamicsWorld(){}

/**
 * 
 * @param timeStep usually 1.0/60
 * @Ammo fixed 1.0/60 default
 */
public final  native void stepSimulation(double timeStep,int maxSubSteps)/*-{
this.stepSimulation(timeStep,maxSubSteps);
}-*/;

public final  native void addRigidBody(btRigidBody body)/*-{
this.addRigidBody(body);
}-*/;

public final  native void addRigidBody(btRigidBody body,int type,int colliedWith)/*-{
this.addRigidBody(body,type,colliedWith);
}-*/;

public final  native void removeRigidBody(btRigidBody body)/*-{
this.removeRigidBody(body);
}-*/;

public final  native void addConstraint(btTypedConstraint constraint,boolean disableCollisionsBetweenLinkedBodies)/*-{
this.addConstraint(constraint,disableCollisionsBetweenLinkedBodies);
}-*/;
public final  native void addConstraint(btTypedConstraint constraint)/*-{
this.addConstraint(constraint);
}-*/;

public final  native void removeConstraint(btTypedConstraint constraint)/*-{
this.removeConstraint(constraint);
}-*/;


public final  native btVector3 getGravity()/*-{
return this.getGravity();
}-*/;

public final  native void setGravity(btVector3 gravity)/*-{
this.setGravity(gravity);
}-*/;

}