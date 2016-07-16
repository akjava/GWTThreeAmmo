package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.core.btRigidBody;

public class CapsuleBodyAndMesh extends BodyAndMesh{
	public CapsuleBodyAndMesh(double radius,double height,btRigidBody body, Mesh mesh) {
		this(radius,height,body,mesh,TYPE_CAPSULE);
	}
	public CapsuleBodyAndMesh(double radius,double height,btRigidBody body, Mesh mesh,int type) {
		super(body, mesh);
		this.radius=radius;
		this.height=height;
		setShapeType(type);
		setRotationSync(true);
	}

	private double radius;

	public double getRadius() {
		return radius;
	}
	private double height;

	public double getHeight() {
		return height;
	}


}
