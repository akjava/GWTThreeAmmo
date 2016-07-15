package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.core.btRigidBody;

public class BoxBodyAndMesh extends BodyAndMesh{
	public BoxBodyAndMesh(Vector3 boxSize,btRigidBody body, Mesh mesh) {
		this(boxSize,body,mesh,TYPE_BOX);
	}
	public BoxBodyAndMesh(Vector3 boxSize,btRigidBody body, Mesh mesh,int type) {
		super(body, mesh);
		this.boxSize=boxSize;
		setShapeType(type);
		setRotationSync(true);
	}

/*
 * warrning
 * three.js width,height,depth
 * ammo.js halfWidth,halfWidht,halfDepth
 * 
 * this is three.js size
 */
private Vector3 boxSize;

public Vector3 getBoxSize() {
	return boxSize;
}


}
