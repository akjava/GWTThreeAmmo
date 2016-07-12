package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.math.Quaternion;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.threeammo.client.core.btQuaternion;
import com.akjava.gwt.threeammo.client.core.btRigidBody;
import com.akjava.gwt.threeammo.client.core.btVector3;

public class AmmoUtils {
	//TODO move js utils
	
	public static final native boolean isContainNaN(btVector3 object)/*-{
	return $wnd.isNaN(object.x()) || $wnd.isNaN(object.y())|| $wnd.isNaN(object.z());
	}-*/;
	
	public static final native boolean isContainNaN(btQuaternion object)/*-{
	return $wnd.isNaN(object.x()) || $wnd.isNaN(object.y())|| $wnd.isNaN(object.z()) || $wnd.isNaN(object.w());
	}-*/;
	
	
	public static final native boolean isContainNaN(Vector3 object)/*-{
	return $wnd.isNaN(object.x) || $wnd.isNaN(object.y)|| $wnd.isNaN(object.z);
	}-*/;
	
	public static final native boolean isContainNaN(Quaternion object)/*-{
	return $wnd.isNaN(object.x) || $wnd.isNaN(object.y)|| $wnd.isNaN(object.z) || $wnd.isNaN(object.w);
	}-*/;

	public static void updateBodyProperties(btRigidBody body, AmmoBodyPropertyData spherehProperties) {
		body.setFriction(spherehProperties.getFriction());
		body.setRestitution(spherehProperties.getRestitution());
		body.setDamping(spherehProperties.getDamping().getX(), spherehProperties.getDamping().getY());
	}
}
