package com.akjava.gwt.threeammo.client.functions;

import com.akjava.gwt.three.client.js.math.Quaternion;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Mesh;
import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.akjava.gwt.threeammo.client.core.btRigidBody;
import com.google.common.base.Function;

public class BodyAndMeshFunctions {
	private BodyAndMeshFunctions(){}
	public static GetMesh getMesh(){
		return GetMesh.INSTANCE;
	}
	public enum  GetMesh implements Function<BodyAndMesh,Mesh >{
		INSTANCE;
		@Override
		public Mesh apply(BodyAndMesh value) {
			return value.getMesh();
		}
	}public static GetBody getBody(){
		return GetBody.INSTANCE;
	}
	public enum  GetBody implements Function<BodyAndMesh,btRigidBody>{
		INSTANCE;
		@Override
		public btRigidBody apply(BodyAndMesh value) {
			return value.getBody();
		}
	}
	
	public static GetName getName(){
		return GetName.INSTANCE;
	}
	public enum  GetName implements Function<BodyAndMesh,String >{
		INSTANCE;
		@Override
		public String apply(BodyAndMesh value) {
			return value.getName();
		}
	}
	
	
	public static GetMeshPosition getMeshPosition(){
		return GetMeshPosition.INSTANCE;
	}
	public enum  GetMeshPosition implements Function<BodyAndMesh,Vector3 >{
		INSTANCE;
		@Override
		public Vector3 apply(BodyAndMesh value) {
			return value.getMesh().getPosition();
		}
	}
	
	/* i'm not sure this contain position matrix?"*/
	public static GetRotation getRotation(){
		return GetRotation.INSTANCE;
	}
	public enum  GetRotation implements Function<Mesh,Quaternion >{
		INSTANCE;
		@Override
		public Quaternion apply(Mesh value) {
			return value.getQuaternion();
		}
	}
	
	public static  class CloneMultiply implements Function<Vector3,Vector3>{
		private double multipl;
		public CloneMultiply(double multiple){
			this.multipl=multiple;
		}
		@Override
		public Vector3 apply(Vector3 input) {
			// TODO Auto-generated method stub
			return input.clone().multiplyScalar(multipl);
		}
	}
	public static class CloneDivided implements Function<Vector3,Vector3>{
		private double multipl;
		public CloneDivided(double multiple){
			this.multipl=multiple;
		}
		@Override
		public Vector3 apply(Vector3 input) {
			// TODO Auto-generated method stub
			return input.clone().divideScalar(multipl);
		}
	}
}
