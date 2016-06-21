package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.core.btTypedConstraint;

public class ConstraintAndMesh extends AmmoAndThreeContainer{

	private btTypedConstraint constraint;
	public btTypedConstraint getConstraint() {
		return constraint;
	}
	public void setConstraint(btTypedConstraint constraint) {
		this.constraint = constraint;
	}
	public ConstraintAndMesh(btTypedConstraint constraint, Mesh mesh) {
		super();
		this.constraint = constraint;
		this.mesh = mesh;
	}
	private Mesh mesh;
	public Mesh getMesh() {
		throw new RuntimeException("not support yet");
		//return mesh;
	}
	public void setMesh(Mesh mesh) {
		throw new RuntimeException("not support yet");
		//this.mesh = mesh;
	}
}
