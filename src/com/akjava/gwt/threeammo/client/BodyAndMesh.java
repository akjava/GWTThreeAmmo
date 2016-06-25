package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.materials.Material;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.core.Ammo;
import com.akjava.gwt.threeammo.client.core.btRigidBody;
import com.akjava.gwt.threeammo.client.core.btTransform;
import com.akjava.gwt.threeammo.client.core.btVector3;

public class BodyAndMesh extends AmmoAndThreeContainer{



private btRigidBody body;
public BodyAndMesh(btRigidBody body, Mesh mesh) {
	super();
	this.body = body;
	this.mesh = mesh;
	transform=Ammo.btTransform();
}

private Mesh mesh;
btTransform transform;

public btRigidBody getBody() {
	return body;
}
public void setBody(btRigidBody body) {
	this.body = body;
}
public Mesh getMesh() {
	return mesh;
}
public void setMesh(Mesh mesh) {
	this.mesh = mesh;
}

//for replacing when resize
public void copy(BodyAndMesh newBm){
	this.body=newBm.getBody();
	this.mesh=newBm.getMesh();
	this.name=newBm.getName();
	this.transform=newBm.transform;
}


public static SphereBodyAndMesh createSphere(double radius,double mass,Vector3 position,Material material){
	return createSphere(radius, mass, position.getX(), position.getY(), position.getZ(), material);
}

public static SphereBodyAndMesh createSphere(double radius,double mass,double x,double y,double z,Material material){
	btRigidBody body=makeSphereBody(radius,mass,x,y,z);
	
	Mesh mesh=THREE.Mesh(THREE.SphereGeometry(radius, 10, 10),material);
	mesh.setPosition(x, y, z);
	SphereBodyAndMesh sphere= new SphereBodyAndMesh(radius,body, mesh);
	return sphere;
}

public static BoxBodyAndMesh createBox(Vector3 halfSize,double mass,Vector3 position,Material material){
	return createBox(halfSize, mass, position.getX(), position.getY(), position.getZ(), material);
}


public static BoxBodyAndMesh createBox(Vector3 size,double mass,double x,double y,double z,Material material){
	btVector3 vec3=Ammo.btVector3(size.getX()/2, size.getY()/2, size.getZ()/2);
	btRigidBody body=makeBoxBody(vec3,mass,x,y,z);
	vec3.destroy();
	
	Mesh mesh=THREE.Mesh(THREE.BoxGeometry(size.getX(), size.getY(), size.getZ()),material);
	mesh.setPosition(x, y, z);
	BoxBodyAndMesh box= new BoxBodyAndMesh(size.clone(),body, mesh);
	return box;
}

//some sphere no need
private boolean rotationSync=true;
public boolean isRotationSync() {
	return rotationSync;
}
public void setRotationSync(boolean rotationSync) {
	this.rotationSync = rotationSync;
}
public void syncTransform(){
	if(body==null || mesh==null){
		return;
	}
	body.getMotionState().getWorldTransform(transform);
	mesh.getPosition().set(transform.getOrigin().x(), transform.getOrigin().y(), transform.getOrigin().z());
	if(rotationSync){
	mesh.getQuaternion().set(transform.getRotation().x(), transform.getRotation().y(), transform.getRotation().z(), transform.getRotation().w());
	}
}

public static final   btRigidBody makeSphereBody(double radius,double mass,Vector3 pos){
	return makeSphereBody(radius, mass, pos.getX(),pos.getY(),pos.getZ());
}

public static final   btRigidBody makeBoxBody(btVector3 halfSize,double mass,Vector3 pos){
	return makeBoxBody(halfSize, mass, pos.getX(),pos.getY(),pos.getZ());
}

//TODO need rotate
public static final  native btRigidBody makeSphereBody(double radius,double mass,double x,double y,double z)/*-{
var pos=new $wnd.Ammo.btVector3(x, y, z);
var form = new $wnd.Ammo.btTransform();
    form.setIdentity();
    form.setOrigin(pos);
   
    var sphere = new $wnd.Ammo.btSphereShape(radius);
    var localInertia = new $wnd.Ammo.btVector3(0, 0, 0);//
    if(mass!=0){
    	sphere.calculateLocalInertia(mass,localInertia);
    }
    var state=new $wnd.Ammo.btDefaultMotionState(form);
    var info=new $wnd.Ammo.btRigidBodyConstructionInfo(
            mass, 
            state, 
            sphere, 
            localInertia 
        );
        
    var body= new $wnd.Ammo.btRigidBody(
        info
    );
    //btSphere has nothing special method
    //body._sphereShape=sphere;
    
    body._mass=mass;

    //only can destroying here,sphere cant destroy
    $wnd.Ammo.destroy(form);
    //$wnd.Ammo.destroy(info); //can't destroy
    //$wnd.Ammo.destroy(state); //can't destroy
    $wnd.Ammo.destroy(localInertia);
    
    return body;
}-*/;

//TODO need rotate
public static final  native btRigidBody makeBoxBody(btVector3 halfSize,double mass,double x,double y,double z)/*-{
var pos=new $wnd.Ammo.btVector3(x, y, z);
var form = new $wnd.Ammo.btTransform();
  form.setIdentity();
  form.setOrigin(pos);
 
  var box = new $wnd.Ammo.btBoxShape(halfSize);
  var localInertia = new $wnd.Ammo.btVector3(0, 0, 0);//
  if(mass!=0){
  	box.calculateLocalInertia(mass,localInertia);
  }
  
  var body= new $wnd.Ammo.btRigidBody(
      new $wnd.Ammo.btRigidBodyConstructionInfo(
          mass, 
          new $wnd.Ammo.btDefaultMotionState(form), 
          box, 
          localInertia 
      )
  );
  //btSphere has nothing special method
  //body._sphereShape=sphere;
  
  body._mass=mass;

  //only can destroying here,sphere cant destroy
  $wnd.Ammo.destroy(form);
  $wnd.Ammo.destroy(localInertia);
  
  return body;
}-*/;

/*
 * seems low quality
 */
//I'm not sure how effect simulation,but only easy way to resize
public void setScale(double x, double y, double z) {
	mesh.setScale(x, y, z);
	btVector3 scale=Ammo.btVector3(x, y, z);
	body.getCollisionShape().setLocalScaling(scale);
	scale.destroy();
}

}
