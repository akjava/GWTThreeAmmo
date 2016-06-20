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
import com.akjava.gwt.threeammo.client.core.btQuaternion;
import com.akjava.gwt.threeammo.client.core.btRigidBody;
import com.akjava.gwt.threeammo.client.core.btTransform;
import com.akjava.gwt.threeammo.client.core.btTypedConstraint;
import com.akjava.gwt.threeammo.client.core.btVector3;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.JsArray;

public class ThreeAmmoControler {
private Scene scene;
public Scene getScene() {
	return scene;
}


public void destroyConstraint(btTypedConstraint constraint){
	world.removeConstraint(constraint);
	
	garbage.push(constraint);
}

public void destroyBodyAndMesh(BodyAndMesh data){
	scene.remove(data.getMesh());
	objects.remove(data);
	garbage.push(data.transform);//no need anymore
	destroyRigidBody(data.getBody());
}

private void destroyRigidBody(btRigidBody body){
	world.removeRigidBody(body);
	garbage.push(body);
}


private List<BodyAndMesh> objects=Lists.newArrayList();
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

public ThreeAmmoControler(Scene scene, btDiscreteDynamicsWorld world) {
	super();
	this.scene = scene;
	this.world = world;
}
private btDiscreteDynamicsWorld world;


public void update(){
	update(1.0/60);
}

public void update(double dt){
	world.stepSimulation(dt, 0);
	for(BodyAndMesh object:objects){
		object.syncPosition();
	}
	
	deleteGarbages();
}
public BodyAndMesh createSphere(double radius,double mass,double x,double y,double z,Material material){
	BodyAndMesh object=  BodyAndMesh.createSphere(radius, mass, x, y, z, material);
	scene.add( object.getMesh());
	world.addRigidBody(object.getBody());
	
	objects.add(object);
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
	Vector3AndQuaternion vq=getWorldTransform(bm,transform);
	updateBone(bone, vq.getVector3(), vq.getQuaternion());
}

/*
 * not thread safe
 * 
 * should call reset,before bone-loop
 * clothBoxMesh.getSkeleton().pose();
 * 
 * position and rotation is based and ammo'getWorldTransform
 */
public void updateBone(Bone bone,Vector3 position,Quaternion rotation){
	bone.updateMatrixWorld(true);
	Quaternion q2=THREE.Quaternion().setFromRotationMatrix(bone.getMatrixWorld());
	q2.conjugate();
	q2.multiply(rotation);
	Quaternion q3=THREE.Quaternion().setFromRotationMatrix(bone.getMatrix());
	q2.multiply(q3);
	bone.getQuaternion().copy(q2);
	
	Vector3 v1=THREE.Vector3();
	v1.copy(position);
	bone.worldToLocal(v1);//what is this?
	v1.add(bone.getPosition());
	
	bone.getPosition().copy(v1);
	bone.updateMatrixWorld(true);
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
	objects.add(bm);//removed when destroyBodyAndMesh
	
	scene.add( bm.getMesh());
	world.addRigidBody(bm.getBody());
	
	
}


}
