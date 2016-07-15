package com.akjava.gwt.threeammo.client;

import static com.google.common.base.Preconditions.checkArgument;

import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.core.Geometry;
import com.akjava.gwt.three.client.js.materials.Material;
import com.akjava.gwt.three.client.js.math.Matrix4;
import com.akjava.gwt.three.client.js.math.Quaternion;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.core.Ammo;
import com.akjava.gwt.threeammo.client.core.btRigidBody;
import com.akjava.gwt.threeammo.client.core.btTransform;
import com.akjava.gwt.threeammo.client.core.btVector3;

public class BodyAndMesh extends AmmoAndThreeContainer{

public static final int TYPE_SPHERE=0;
public static final int TYPE_BOX=1;
public static final int TYPE_CYLINDER=2;
public static final int TYPE_CAPSULE=3;
public static final int TYPE_CONE = 4;

private int shapeType;
public int getShapeType() {
	return shapeType;
}
public void setShapeType(int shapeType) {
	this.shapeType = shapeType;
}

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

public static BoxBodyAndMesh createBox(Vector3 size,double mass,Vector3 position,Material material){
	return createBox(size, mass, position.getX(), position.getY(), position.getZ(), material);
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

public static BoxBodyAndMesh createCylinder(double radius,double halfHeight,double mass,Vector3 position,Material material){
	return createCylinder(radius,halfHeight, mass, position.getX(), position.getY(), position.getZ(), material);
}


public static BoxBodyAndMesh createCylinder(double radius,double height,double mass,double x,double y,double z,Material material){
	btVector3 vec3=Ammo.btVector3(radius, height/2, radius);
	btRigidBody body=makeCylinderBody(vec3,mass,x,y,z);
	vec3.destroy();
	
	Mesh mesh=THREE.Mesh(THREE.CylinderGeometry(radius, radius, height, 10),material);
	mesh.setPosition(x, y, z);
	BoxBodyAndMesh box= new BoxBodyAndMesh(THREE.Vector3(radius, height, 0),body, mesh,TYPE_CYLINDER);
	
	
	return box;
}

public static BoxBodyAndMesh createCone(double radius,double halfHeight,double mass,Vector3 position,Material material){
	return createCone(radius,halfHeight, mass, position.getX(), position.getY(), position.getZ(), material);
}


public static BoxBodyAndMesh createCone(double radius,double height,double mass,double x,double y,double z,Material material){
	
	btRigidBody body=makeConeBody(radius,height,mass,x,y,z);
	
	
	Mesh mesh=THREE.Mesh(THREE.CylinderGeometry(radius/10, radius, height, 10),material);
	mesh.setPosition(x, y, z);
	BoxBodyAndMesh box= new BoxBodyAndMesh(THREE.Vector3(radius, height, 0),body, mesh,TYPE_CONE);
	
	
	return box;
}

public static BoxBodyAndMesh createCapsule(double radius,double halfHeight,double mass,Vector3 position,Material material){
	return createCapsule(radius,halfHeight, mass, position.getX(), position.getY(), position.getZ(), material);
}


public static BoxBodyAndMesh createCapsule(double radius,double height,double mass,double x,double y,double z,Material material){
	
	btRigidBody body=makeCapsuleBody(radius,height,mass,x,y,z);

	double move=height/2-radius;
	Geometry cylinder=THREE.CylinderGeometry(radius, radius, height-radius*2, 10);
	Geometry sphere=THREE.SphereGeometry(radius, 6,6);
	Matrix4 moveUpMatrix=THREE.Matrix4().makeTranslation(0, -move, 0);
	sphere.applyMatrix(moveUpMatrix);
	cylinder.merge(sphere);
	
	Geometry sphere2=THREE.SphereGeometry(radius, 6,6);
	Matrix4 downUpMatrix=THREE.Matrix4().makeTranslation(0, move, 0);
	sphere2.applyMatrix(downUpMatrix);
	cylinder.merge(sphere2);
	
	
	Mesh mesh=THREE.Mesh(cylinder,material);
	mesh.setPosition(x, y, z);
	BoxBodyAndMesh box= new BoxBodyAndMesh(THREE.Vector3(radius, height, 0),body, mesh,TYPE_CAPSULE);
	
	
	return box;
}

public SphereBodyAndMesh castToSphere(){
	checkArgument(shapeType==TYPE_SPHERE,"this shape type is "+shapeType+".this is not sphere-shape.you invalidly to cast.it would make Uncaught java.lang.ClassCastException");
	
	return (SphereBodyAndMesh)this;
}
public BoxBodyAndMesh castToBox(){
	checkArgument(shapeType==TYPE_BOX,"this shape type is "+shapeType+".this is not sphere-shape.you invalidly to cast.it would make Uncaught java.lang.ClassCastException");
	
	return (BoxBodyAndMesh)this;
}

//some sphere no need
private boolean rotationSync;
public boolean isRotationSync() {
	return rotationSync;
}
public void setRotationSync(boolean rotationSync) {
	this.rotationSync = rotationSync;
}

private double ammoMultipleScalar=1;
public double getAmmoMultipleScalar() {
	return ammoMultipleScalar;
}
/*
 * this must be conflict localScale
 */
public void setAmmoMultipleScalar(double ammoMultipleScalar) {
	this.ammoMultipleScalar = ammoMultipleScalar;
	getMesh().getScale().setScalar(1.0/ammoMultipleScalar);
}
public void syncTransform(){
	if(body==null || mesh==null){
		return;
	}
	
	body.getMotionState().getWorldTransform(transform);
	mesh.getPosition().set(transform.getOrigin().x()/ammoMultipleScalar, transform.getOrigin().y()/ammoMultipleScalar, transform.getOrigin().z()/ammoMultipleScalar);
	if(rotationSync){
		Quaternion q=THREE.Quaternion();
		transform.getRotation().copyTo(q);
	mesh.getQuaternion().set(transform.getRotation().x(), transform.getRotation().y(), transform.getRotation().z(), transform.getRotation().w());
	}
}

public static final   btRigidBody makeSphereBody(double radius,double mass,Vector3 pos){
	return makeSphereBody(radius, mass, pos.getX(),pos.getY(),pos.getZ());
}

public static final   btRigidBody makeBoxBody(btVector3 halfSize,double mass,Vector3 pos){
	return makeBoxBody(halfSize, mass, pos.getX(),pos.getY(),pos.getZ());
}
public static final   btRigidBody makeCylinderBody(btVector3 halfSize,double mass,Vector3 pos){
	return makeCylinderBody(halfSize, mass, pos.getX(),pos.getY(),pos.getZ());
}
public static final   btRigidBody makeCapsuleBody(double radius,double height,double mass,Vector3 pos){
	return makeCapsuleBody(radius,height, mass, pos.getX(),pos.getY(),pos.getZ());
}

public static final   btRigidBody makeConeBody(double radius,double height,double mass,Vector3 pos){
	return makeConeBody(radius,height, mass, pos.getX(),pos.getY(),pos.getZ());
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

public void updateMass(double mass){
	btVector3 localInertia=Ammo.btVector3(0, 0, 0);
	getBody().getCollisionShape().calculateLocalInertia(mass, localInertia);
	getBody().setMassProps(mass, localInertia);
	getBody().setMass(mass);
}

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

public static final  native btRigidBody makeCylinderBody(btVector3 halfSize,double mass,double x,double y,double z)/*-{
var pos=new $wnd.Ammo.btVector3(x, y, z);
var form = new $wnd.Ammo.btTransform();
  form.setIdentity();
  form.setOrigin(pos);
 
  var box = new $wnd.Ammo.btCylinderShape(halfSize);
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

/**
 * 
 * @param radius
 * @param height contain both circle
 * @param mass
 * @param x
 * @param y
 * @param z
 * @return
 */
public static final  native btRigidBody makeCapsuleBody(double radius,double height,double mass,double x,double y,double z)/*-{
var pos=new $wnd.Ammo.btVector3(x, y, z);
var form = new $wnd.Ammo.btTransform();
  form.setIdentity();
  form.setOrigin(pos);
 
  var box = new $wnd.Ammo.btCapsuleShape(radius,height-(radius*2));
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

public static final  native btRigidBody makeConeBody(double radius,double height,double mass,double x,double y,double z)/*-{
var pos=new $wnd.Ammo.btVector3(x, y, z);
var form = new $wnd.Ammo.btTransform();
  form.setIdentity();
  form.setOrigin(pos);
 
  var box = new $wnd.Ammo.btConeShape(radius,height);
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
