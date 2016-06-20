package com.akjava.gwt.threeammo.client.core;

import java.util.Map;

import com.google.common.collect.Maps;


public class Ammo {
private Ammo(){}

public static final  native btTransform btTransform()/*-{
return new $wnd.Ammo.btTransform();
}-*/;

public static final  native btTransform btTransformWithIdentity()/*-{
var bt= new $wnd.Ammo.btTransform();
bt.setIdentity();
return bt;
}-*/;

public static final  native btVector3 btVector3(double x,double y,double z)/*-{
return new $wnd.Ammo.btVector3(x,y,z);
}-*/;

public static final  native btQuaternion btQuaternion()/*-{
return new $wnd.Ammo.btQuaternion();
}-*/;

public static final  native btGeneric6DofSpringConstraint btGeneric6DofSpringConstraint(btRigidBody bodyA,btRigidBody bodyB,btTransform frameInA,btTransform frameInB,boolean useLinearReferenceFrameA)/*-{
return new $wnd.Ammo.btGeneric6DofSpringConstraint(bodyA,bodyB,frameInA,frameInB,useLinearReferenceFrameA);
}-*/;

public static final  native btGeneric6DofConstraint btGeneric6DofConstraint(btRigidBody bodyA,btRigidBody bodyB,btTransform frameInA,btTransform frameInB,boolean useLinearReferenceFrameA)/*-{
return new $wnd.Ammo.btGeneric6DofConstraint(bodyA,bodyB,frameInA,frameInB,useLinearReferenceFrameA);
}-*/;


public static final int  ACTIVE_TAG =1;
public static final int ISLAND_SLEEPING =2;
public static final int WANTS_DEACTIVATION =3;
public static final int DISABLE_DEACTIVATION =4;
public static final int DISABLE_SIMULATION =5;



public static final btDiscreteDynamicsWorld initWorld(){
	return initWorld(0,-10,0);
}

public static native final btDiscreteDynamicsWorld initWorld(double x,double y,double z)/*-{

    var collisionConfiguration = new $wnd.Ammo.btDefaultCollisionConfiguration();
    var dispatcher = new $wnd.Ammo.btCollisionDispatcher(collisionConfiguration);
    var overlappingPairCache = new $wnd.Ammo.btDbvtBroadphase();
    var solver = new $wnd.Ammo.btSequentialImpulseConstraintSolver();
    var dynamicsWorld = new $wnd.Ammo.btDiscreteDynamicsWorld(
        dispatcher, 
        overlappingPairCache, 
        solver, 
        collisionConfiguration
    );
    dynamicsWorld.setGravity(new $wnd.Ammo.btVector3(x, y, z));
    return dynamicsWorld;

return world;
}-*/;
}