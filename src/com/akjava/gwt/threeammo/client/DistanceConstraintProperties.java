package com.akjava.gwt.threeammo.client;

import java.util.List;

import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.threeammo.client.core.btTransform;
import com.google.common.collect.Lists;


public class DistanceConstraintProperties {
	
	
	private double baseDistance;
	public DistanceConstraintProperties(double baseDistance) {
		super();
		this.baseDistance = baseDistance;
	}
	
	public void updateFrameInA(btTransform transform,Vector3 pos1,Vector3 pos2){
		transform.getOrigin().copy(pos2.clone().sub(pos1).multiplyScalar(frameInARelativePosRatio));
	}
	
	public void updateFrameInB(btTransform transform,Vector3 pos1,Vector3 pos2){
		transform.getOrigin().copy(pos2.clone().sub(pos1).multiplyScalar(frameInBRelativePosRatio));
	}
	
	
	public double getBaseDistance() {
		return baseDistance;
	}
	public void setBaseDistance(double baseDistance) {
		this.baseDistance = baseDistance;
	}
	private boolean useLinearReferenceFrameA=true;
	public boolean isUseLinearReferenceFrameA() {
		return useLinearReferenceFrameA;
	}
	public void setUseLinearReferenceFrameA(boolean useLinearReferenceFrameA) {
		this.useLinearReferenceFrameA = useLinearReferenceFrameA;
	}
	public boolean isDisableCollisionsBetweenLinkedBodies() {
		return disableCollisionsBetweenLinkedBodies;
	}
	public void setDisableCollisionsBetweenLinkedBodies(boolean disableCollisionsBetweenLinkedBodies) {
		this.disableCollisionsBetweenLinkedBodies = disableCollisionsBetweenLinkedBodies;
	}
	public double getFrameInARelativePosRatio() {
		return frameInARelativePosRatio;
	}
	public void setFrameInARelativePosRatio(double frameInARelativePosRatio) {
		this.frameInARelativePosRatio = frameInARelativePosRatio;
	}
	public double getFrameInBRelativePosRatio() {
		return frameInBRelativePosRatio;
	}
	public void setFrameInBRelativePosRatio(double frameInBRelativePosRatio) {
		this.frameInBRelativePosRatio = frameInBRelativePosRatio;
	}
	public List<Boolean> getEnableSprings() {
		return enableSprings;
	}
	public void setEnableSprings(List<Boolean> enableSprings) {
		this.enableSprings = enableSprings;
	}
	public List<Double> getDampings() {
		return dampings;
	}
	public void setDampings(List<Double> dumpings) {
		this.dampings = dumpings;
	}
	public List<Double> getStiffnesses() {
		return stiffnesses;
	}
	public void setStiffnesses(List<Double> stiffnesses) {
		this.stiffnesses = stiffnesses;
	}
	public Vector3 getAngularLowerLimit() {
		return angularLowerLimit;
	}
	public void setAngularLowerLimit(Vector3 angularLowerLimit) {
		this.angularLowerLimit = angularLowerLimit;
	}
	public Vector3 getAngularUpperLimit() {
		return angularUpperLimit;
	}
	public void setAngularUpperLimit(Vector3 angularUpperLimi) {
		this.angularUpperLimit = angularUpperLimi;
	}
	public Vector3 getLinearLowerLimit() {
		return linearLowerLimit;
	}
	public void setLinearLowerLimit(Vector3 linearLowerLimit) {
		this.linearLowerLimit = linearLowerLimit;
	}
	public Vector3 getLinearUpperLimit() {
		return linearUpperLimit;
	}
	public void setLinearUpperLimit(Vector3 linearUpperLimit) {
		this.linearUpperLimit = linearUpperLimit;
	}
	private boolean disableCollisionsBetweenLinkedBodies=true;
	//TODO make absolute pos
	private double frameInARelativePosRatio=0.5;
	private double frameInBRelativePosRatio=-0.5;
	
	private List<Boolean> enableSprings=Lists.newArrayList(false,false,false,false,false,false);
	private List<Double> dampings=Lists.newArrayList(0.0,0.0,0.0,0.0,0.0,0.0);
	private List<Double> stiffnesses=Lists.newArrayList(0.0,0.0,0.0,0.0,0.0,0.0);
	
	private Vector3 angularLowerLimit=THREE.Vector3().setScalar(-Math.PI/2);
	private Vector3 angularUpperLimit=THREE.Vector3().setScalar(Math.PI/2);
	private Vector3 linearLowerLimit=THREE.Vector3();
	private Vector3 linearUpperLimit=THREE.Vector3();
	public void setEnableSpringsAll(boolean value) {
		for(int i=0;i<6;i++){
			enableSprings.set(i, value);
		}
	}
	public void setStiffnessAll(double value) {
		for(int i=0;i<6;i++){
			stiffnesses.set(i, value);
		}
	}
	public void setDampingAll(double value) {
		for(int i=0;i<6;i++){
			dampings.set(i, value);
		}
	}
}
