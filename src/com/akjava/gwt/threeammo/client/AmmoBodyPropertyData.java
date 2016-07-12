package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector2;

public class AmmoBodyPropertyData {
	private double  friction;
	private double  restitution;
	private Vector2 damping=THREE.Vector2();
	public double getFriction() {
		return friction;
	}
	public void setFriction(double friction) {
		this.friction = friction;
	}
	public double getRestitution() {
		return restitution;
	}
	public void setRestitution(double restitution) {
		this.restitution = restitution;
	}
	public Vector2 getDamping() {
		return damping;
	}
	public void setDamping(Vector2 damping) {
		this.damping = damping;
	}
	public void setDamping(double linear,double anguler) {
		setDamping(THREE.Vector2(linear, anguler));
	}
}
