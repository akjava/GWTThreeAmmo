package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Quaternion;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.threeammo.client.core.btTransform;

public class Vector3AndQuaternion {
private Vector3 vector3;
private Quaternion quaternion;
public Vector3 getVector3() {
	return vector3;
}
public void setVector3(Vector3 vector3) {
	this.vector3 = vector3;
}
public Quaternion getQuaternion() {
	return quaternion;
}
public void setQuaternion(Quaternion quaternion) {
	this.quaternion = quaternion;
}
public Vector3AndQuaternion(){
	this(null);
}
public Vector3AndQuaternion(btTransform transform){
	vector3=THREE.Vector3();
	quaternion=THREE.Quaternion();
	set(transform);
}

public void set(btTransform transform){
	if(transform==null){
		return;
	}
	transform.getOrigin().copyTo(vector3);
	transform.getRotation().copyTo(quaternion);
}

}
