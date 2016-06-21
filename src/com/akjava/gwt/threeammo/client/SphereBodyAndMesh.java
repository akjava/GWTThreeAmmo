package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.core.btRigidBody;

public class SphereBodyAndMesh extends BodyAndMesh{
public SphereBodyAndMesh(double radius,btRigidBody body, Mesh mesh) {
		super(body, mesh);
		this.radius=radius;
	}

private double radius;

public double getRadius() {
	return radius;
}

public void setRadius(double radius) {
	this.radius = radius;
}
}
