package com.akjava.gwt.threeammo.client;

import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Line;
import com.akjava.gwt.threeammo.client.core.btTypedConstraint;

public class ConstraintAndLine extends AmmoAndThreeContainer{
	private BodyAndMesh body1,body2;
	private btTypedConstraint constraint;
	public btTypedConstraint getConstraint() {
		return constraint;
	}
	public void setConstraint(btTypedConstraint constraint) {
		this.constraint = constraint;
	}
	public ConstraintAndLine(btTypedConstraint constraint, Line line,BodyAndMesh body1,BodyAndMesh body2) {
		super();
		this.constraint = constraint;
		this.line = line;
		this.body1=body1;
		this.body2=body2;
	}
	
	
	/*
	public String getType(){
		if(constraint instanceof btPoint2PointConstraint){
			return "point2point";
		}
		
		if(constraint instanceof btGeneric6DofConstraint){
			return "generic";
		}
		
		if(constraint instanceof btGeneric6DofSpringConstraint){
			return "spring";
		}
		
		return null;
	}
	*/
	
	private Line line;
	
	//TODO make interface hasPosition
	private Vector3 pivot0;//point2point
	
	public Vector3 getPivot0() {
		return pivot0;
	}
	public void setPivot0(Vector3 pivot0) {
		this.pivot0 = pivot0;
	}
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	private boolean enableSync=true;
	public boolean isEnableSync() {
		return enableSync;
	}
	public void setEnableSync(boolean enableSync) {
		this.enableSync = enableSync;
	}
	//must call after body1 position setted
	public void sync() {
		if(!enableSync){
			return;
		}
		
		line.getGeometry().getVertices().get(0).copy(body1.getMesh().getPosition());
		if(body2!=null){
		line.getGeometry().getVertices().get(1).copy(body2.getMesh().getPosition());
		}else{
			
			//Point2PointConstraint
			if(pivot0!=null){
				line.getGeometry().getVertices().get(1).copy(pivot0);
			}
		}
		//LogUtils.log(getType());
		//ThreeLog.log("pt0", line.getGeometry().getVertices().get(0));
		//ThreeLog.log("pt1", line.getGeometry().getVertices().get(1));
		
		line.getGeometry().setVerticesNeedUpdate(true);
	}
	
	public void setVisibleLine(boolean value){
		line.setVisible(value);
	}
}
