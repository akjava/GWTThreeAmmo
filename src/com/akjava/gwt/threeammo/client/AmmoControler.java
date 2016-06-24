package com.akjava.gwt.threeammo.client;

import java.util.List;

import com.akjava.gwt.lib.client.LogUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.materials.Material;
import com.akjava.gwt.three.client.js.math.Quaternion;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Bone;
import com.akjava.gwt.three.client.js.scenes.Scene;
import com.akjava.gwt.threeammo.client.core.Ammo;
import com.akjava.gwt.threeammo.client.core.AmmoObject;
import com.akjava.gwt.threeammo.client.core.btDiscreteDynamicsWorld;
import com.akjava.gwt.threeammo.client.core.btGeneric6DofSpringConstraint;
import com.akjava.gwt.threeammo.client.core.btQuaternion;
import com.akjava.gwt.threeammo.client.core.btRigidBody;
import com.akjava.gwt.threeammo.client.core.btTransform;
import com.akjava.gwt.threeammo.client.core.btTypedConstraint;
import com.akjava.gwt.threeammo.client.core.btVector3;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.JsArray;

public class AmmoControler {
private Scene scene;
public Scene getScene() {
	return scene;
}


public void destroyConstraintOnly(btTypedConstraint constraint){
	world.removeConstraint(constraint);
	
	garbage.push(constraint);
}

public void destroyConstraintAndMesh(ConstraintAndMesh data){
	if(data.getMesh()!=null){
		scene.remove(data.getMesh());
	}
	autoSyncingConstraints.remove(data);
	destroyConstraintOnly(data.getConstraint());
}

public void destroyBodyAndMesh(BodyAndMesh data){
	if(data.getMesh()!=null){
		scene.remove(data.getMesh());
	}
	autoSyncingBodies.remove(data);
	garbage.push(data.transform);//no need anymore
	destroyRigidBody(data.getBody());
}

private void destroyRigidBody(btRigidBody body){
	world.removeRigidBody(body);
	garbage.push(body);
}


private List<ConstraintAndMesh> autoSyncingConstraints=Lists.newArrayList();
private List<BodyAndMesh> autoSyncingBodies=Lists.newArrayList();
private JsArray<AmmoObject> garbage=JsArray.createArray().cast();
public void setScene(Scene scene) {
	this.scene = scene;
}

public btDiscreteDynamicsWorld getWorld() {
	return world;
}

public void setWorld(btDiscreteDynamicsWorld world) {
	this.world = world;
}

public AmmoControler(Scene scene, btDiscreteDynamicsWorld world) {
	super();
	this.scene = scene;
	this.world = world;
}
private btDiscreteDynamicsWorld world;


public void update(){
	update(1.0/60);
}

public void destroyWorld(){
	for(BodyAndMesh object:autoSyncingBodies){
		destroyBodyAndMesh(object);
	}
	for(ConstraintAndMesh object:autoSyncingConstraints){
		destroyConstraintAndMesh(object);
	}
	world.destroy();
}

public void update(double dt){
	world.stepSimulation(dt, 0);
	for(BodyAndMesh object:autoSyncingBodies){
		object.syncPosition();
	}
	
	for(ConstraintAndMesh object:autoSyncingConstraints){
		//TODO support sync
	}
	
	deleteGarbages();
}

public void updateConstraint(btGeneric6DofSpringConstraint newConstraint,DistanceConstraintProperties data){

	for(int i=0;i<6;i++){
		newConstraint.enableSpring(i, data.getEnableSprings().get(i));
		newConstraint.setStiffness(i, data.getStiffnesses().get(i));
		newConstraint.setDamping(i, data.getDampings().get(i));
	}
	
	newConstraint.setAngularLowerLimit(makeVector3(data.getAngularLowerLimit()));
	newConstraint.setAngularUpperLimit(makeVector3(data.getAngularUpperLimit()));
	
	newConstraint.setLinearLowerLimit(makeVector3(data.getLinearLowerLimit().clone().multiplyScalar(data.getBaseDistance())));
	newConstraint.setLinearUpperLimit(makeVector3(data.getLinearUpperLimit().clone().multiplyScalar(data.getBaseDistance())));
}


public ConstraintAndMesh createGeneric6DofSpringConstraintConstraint(btRigidBody body1,btRigidBody body2,btTransform frameInA,btTransform frameInB,boolean disableCollisionsBetweenLinkedBodies){
	btGeneric6DofSpringConstraint constraint= Ammo.btGeneric6DofSpringConstraint(body1, body2, frameInA, frameInB, true);
	getWorld().addConstraint(constraint, disableCollisionsBetweenLinkedBodies);
	
	ConstraintAndMesh cm=new ConstraintAndMesh(constraint, null);
	
	autoSyncingConstraints.add(cm);
	//TODO make line or something
	
	return cm;
}

public void addBodyMesh(BodyAndMesh object){
	if( object.getMesh()!=null){
	   scene.add( object.getMesh());
	}
	if(object.getBody()!=null){
		world.addRigidBody(object.getBody());
	}
	
	autoSyncingBodies.add(object);
}

public SphereBodyAndMesh createSphere(double radius,double mass,double x,double y,double z,Material material){
	SphereBodyAndMesh object=  BodyAndMesh.createSphere(radius, mass, x, y, z, material);
	addBodyMesh(object);
	return object;
}

/**
 * 
 * @param size (w,h,d) this is not half(sphere use radius)
 * @param mass if mass is 0 never move
 * @param x
 * @param y
 * @param z
 * @param material
 * @return
 */
public BoxBodyAndMesh createBox(Vector3 size,double mass,double x,double y,double z,Material material){
	BoxBodyAndMesh object=  BodyAndMesh.createBox(size, mass, x, y, z, material);
	addBodyMesh(object);
	return object;
}

/*
 * watch out only live until next call update
 */
public btVector3 makeVector3(Vector3 vector){
	return makeVector3(vector.getX(), vector.getY(), vector.getZ());
}
public btVector3 makeVector3(double x,double y,double z){
	btVector3 v3=Ammo.btVector3(x, y, z);
	garbage.push(v3);
	return v3;
}
/*
 * watch out only live until next call update
 */
public btQuaternion makeQuaternion(){
	btQuaternion value=Ammo.btQuaternion();
	garbage.push(value);
	return value;
}
/*
 * watch out only live until next call update
 */
public btTransform makeTransform(){
	btTransform value=Ammo.btTransformWithIdentity();//overhead 
	garbage.push(value);
	return value;
}
public void addGarbage(AmmoObject object){
	garbage.push(object);
}

public void deleteGarbages(){
	try{
	for(int i=0;i<garbage.length();i++){
		garbage.get(i).destroy();
	}
	garbage=JsArray.createArray().cast();
	}catch (Exception e) {
		/*
		 at $destroy (gwtphysicstest-0.js:535:13)
    	at $deleteGarbages (gwtphysicstest-0.js:493:5)
		If this abort() is unexpected, build with -s ASSERTIONS=1 which can give more information.
		 */
		//if already deleted make problem
		LogUtils.log(e.getMessage());
	}
}

private btTransform syncBoneTransform=Ammo.btTransformWithIdentity();

//not thread safe
public Vector3AndQuaternion getWorldTransform(BodyAndMesh bm,btTransform transform){
	if(transform==null){
		transform=syncBoneTransform;
	}
	bm.getBody().getMotionState().getWorldTransform(transform);
	return new Vector3AndQuaternion(transform);
}

public void updateBone(Bone bone,BodyAndMesh bm,btTransform transform){
	updateBone(bone, bm, transform, 1);
}

public void updateBone(Bone bone,BodyAndMesh bm,btTransform transform,double divided){
	Vector3AndQuaternion vq=getWorldTransform(bm,transform);
	updateBone(bone, vq.getVector3(), vq.getQuaternion(),divided);
}

/*
 * not thread safe
 * 
 * should call reset,before bone-loop
 * clothBoxMesh.getSkeleton().pose();
 * 
 * position and rotation is based and ammo'getWorldTransform
 */
public void updateBone(Bone bone,Vector3 position,Quaternion rotation,double divided){
	bone.updateMatrixWorld(true);
	Quaternion q2=THREE.Quaternion().setFromRotationMatrix(bone.getMatrixWorld());
	q2.conjugate();
	q2.multiply(rotation);
	Quaternion q3=THREE.Quaternion().setFromRotationMatrix(bone.getMatrix());
	q2.multiply(q3);
	bone.getQuaternion().copy(q2);
	
	Vector3 v1=THREE.Vector3();
	v1.copy(position).divideScalar(divided);//some scale modifying,
	bone.worldToLocal(v1);//what is this?
	v1.add(bone.getPosition());
	
	bone.getPosition().copy(v1);
	bone.updateMatrixWorld(true);
}
public void updateBone(Bone bone,Vector3 position,Quaternion rotation){
	updateBone(bone, position, rotation, 1);
}
/*
 * delete and recreate totally costly function,it's better to use only for static 
 *
 * 
 */
public void setRadiusWithRecreate(double radius,BodyAndMesh bm){
	double mass=bm.getBody().getMass();
	Material material=bm.getMesh().getMaterial();
	BodyAndMesh newOne=BodyAndMesh.createSphere(radius, mass, bm.getMesh().getPosition(), material);
	newOne.setName(bm.getName());
	
	destroyBodyAndMesh(bm);
	bm.copy(newOne);
	autoSyncingBodies.add(bm);//removed when destroyBodyAndMesh
	
	scene.add( bm.getMesh());
	world.addRigidBody(bm.getBody());
	
	
}


}
