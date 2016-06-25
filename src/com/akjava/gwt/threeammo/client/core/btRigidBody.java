package com.akjava.gwt.threeammo.client.core;

import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector3;


public class btRigidBody extends AmmoObject{
protected btRigidBody(){}



/**
 * @deprecated
 * 
 * btSphereShape seems same as btCollisionShape
 * @return
 */
public final  native btSphereShape getSphereShape()/*-{
return this._sphereShape;
}-*/;


//auto setted if you create via AmmoControler
public final  native double getMass()/*-{
return this._mass;
}-*/;

public final  native void setMass(double  param)/*-{
this._mass=param;
}-*/;

/*
 * for static object
 */
public final Vector3 getReadOnlyPosition(){
	btTransform transform=getTransform();
	if(transform==null){
		transform=Ammo.btTransformWithIdentity();
		setTransform(transform);
		getDestroyWiths().push(transform);
	}
	this.getMotionState().getWorldTransform(transform);
	return transform.getOrigin().copyTo(THREE.Vector3());
}

public final void setPosition(Vector3 position){
	setPosition(position.getX(), position.getY(), position.getZ());
}
public final void setPosition(double x,double y,double z){
	btTransform transform=getTransform();
	if(transform==null){
		transform=Ammo.btTransformWithIdentity();
		setTransform(transform);
		getDestroyWiths().push(transform);
	}
	transform.getOrigin().set(x,y,z);
	
	this.setCenterOfMassTransform(transform);
	this.getMotionState().setWorldTransform(transform);
}



private final  native btTransform getTransform()/*-{
return this._transform;

}-*/;
private final  native void setTransform(btTransform transform)/*-{
this._transform=transform;
}-*/;


public final  native btCollisionShape getCollisionShape()/*-{
return this.getCollisionShape();

}-*/;


public final  native btMotionState getMotionState()/*-{
return this.getMotionState();
}-*/;

public final  native void updateInertiaTensor()/*-{
this.updateInertiaTensor();
}-*/;

/**
 * 
 * @param mass
 * @param localInertia must be calcurated
 */
public final  native void setMassProps (double mass,btVector3 localInertia)/*-{
this.setMassProps (mass,localInertia);
}-*/;


/**
 * not working?
 */
public final  native void activate()/*-{
this.activate();
}-*/;

/**
 * https://github.com/bulletphysics/bullet3/issues/306
#define ACTIVE_TAG 1
#define ISLAND_SLEEPING 2
#define WANTS_DEACTIVATION 3
#define DISABLE_DEACTIVATION 4
#define DISABLE_SIMULATION 5

 * @param state
 */
public final  native void setActivationState(int state)/*-{
this.setActivationState(state);
}-*/;


//TODO should share it?
public final  void setLinearVelocity(Vector3 velocity){
	btVector3 vec=Ammo.btVector3(velocity);
	setLinearVelocity(vec);
	vec.destroy();
}
public final  void setAngularVelocity(Vector3 velocity){
	btVector3 vec=Ammo.btVector3(velocity);
	setAngularVelocity(vec);
	vec.destroy();
}

public final  native void setAngularVelocity(btVector3 velocity)/*-{
this.setAngularVelocity(velocity);
}-*/;
public final  native void setLinearVelocity(btVector3 velocity)/*-{
this.setLinearVelocity(velocity);
}-*/;

public final  native void setAngularFactor(btVector3 factor)/*-{
this.setAngularFactor(factor);
}-*/;

public final  native void setLinearFactor(btVector3 factor)/*-{
this.setLinearFactor(factor);
}-*/;

public final  native btTransform  getCenterOfMassTransform()/*-{
return this.getCenterOfMassTransform();
}-*/;

public final  native void  setCenterOfMassTransform(btTransform transform)/*-{
this.setCenterOfMassTransform(transform);
}-*/;

public final  native void setFriction(double friction)/*-{
this.setFriction(friction);
}-*/;

public final  native void setRestitution(double restitution)/*-{
this.setRestitution(restitution);
}-*/;

public final  native void setDamping(double linearDamping,double angularDamping)/*-{
this.setDamping(linearDamping,angularDamping);
}-*/;


}