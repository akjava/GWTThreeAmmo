package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.core.btRigidBody;

public class BoxBodyAndMesh extends BodyAndMesh{
public BoxBodyAndMesh(Vector3 halfBoxSize,btRigidBody body, Mesh mesh) {
		super(body, mesh);
		this.boxSize=halfBoxSize;
		setShapeType(TYPE_BOX);
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
