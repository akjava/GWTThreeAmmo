package com.akjava.gwt.threeammoexample.client.examples.constraints;


import com.akjava.gwt.lib.client.LogUtils;
import com.akjava.gwt.three.client.gwt.GWTParamUtils;
import com.akjava.gwt.three.client.gwt.ui.LabeledInputRangeWidget2;
import com.akjava.gwt.three.client.java.ui.experiments.SimpleVector3Editor;
import com.akjava.gwt.three.client.java.ui.experiments.SimpleVector3Editor.SimpleVector3EditorListener;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.akjava.gwt.threeammo.client.BoxBodyAndMesh;
import com.akjava.gwt.threeammo.client.ConstraintAndLine;
import com.akjava.gwt.threeammo.client.core.Ammo;
import com.akjava.gwt.threeammo.client.core.btTransform;
import com.akjava.gwt.threeammo.client.core.btVector3;
import com.akjava.gwt.threeammo.client.core.constraints.btGeneric6DofConstraint;
import com.akjava.gwt.threeammoexample.client.AbstractAmmoExample;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class Point2PointAndGenericExample extends AbstractAmmoExample{

	@Override
	public String getName() {
		return "Point2PointAndGenericConstraint";
	}

	@Override
	public String getTokenKey() {
		return "point2generic";
	}
	
	/**
	 * 
	 * DON'T SET PROPERY WITHOUT FINAL
	 * 
	 * INITIALIZE ON makeAmmoObjects();
	 * 
	 * because when example was selected again preset value used.
	 * 
	 * 
	 */
	private double groundRestitution;
	private double sphereRestitution;
	
	private double groundFriction;
	private double sphereFriction;
	private BodyAndMesh ball;
	private BodyAndMesh ball2;
	private double mass;
	

	private double linearDamping;
	private double angularDamping;
	
	private Vector3 ballPosition;
	private Vector3 ball2Position;
	private Vector3 pivot0Position;
	
	private boolean useFrameB;
/**
 * 
 * force is not clear
 * 
 */
	
	boolean ball1Fixed;
	@Override
	protected void makeAmmoObjects() {

		//init here;
		groundRestitution=0.5;
		sphereRestitution=0.5;
		
		groundFriction=0.1;
		sphereFriction=0.1;
		mass=1;
		

		linearDamping=0.1;
		angularDamping=0.1;
		
		ballPosition=THREE.Vector3(0, 10, 0);
		ball2Position=THREE.Vector3(0, 5, 0);
		pivot0Position=THREE.Vector3(0, 15, 0);
		
		
		camera.getPosition().set(0, 50, 50);
		
		double groundMass=0.0;
		
		useFrameB=true;
		
		
		final BoxBodyAndMesh ground=ammoControler.createBox(THREE.Vector3(20, 2, 20), groundMass, 0, 0, 0, 
				THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x888888))
				);
		ground.getBody().setRestitution(groundRestitution);
		
		
		ball = createBox();
		ball2 = createBox();
		
		controlerRootPanel.add(new HTML("<h4>Constraint</h4>"));
		controlerRootPanel.add(new Label("Pivot0"));
		SimpleVector3Editor pivot0PositionEditor=new SimpleVector3Editor(new SimpleVector3EditorListener() {
			@Override
			public void onValueChanged(Vector3 value) {
				pivot0Position.copy(value);
				resetProperties();
				
				initConstraint();
			}
		});
		controlerRootPanel.add(pivot0PositionEditor);
		pivot0PositionEditor.setValue(pivot0Position);
		
		CheckBox ball1FixedCheck=new CheckBox("ball is fixed(mass=0)");
		controlerRootPanel.add(ball1FixedCheck);
		ball1FixedCheck.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				ball1Fixed=event.getValue();
				resetProperties();
				
				initConstraint();
			}
		});
		
		controlerRootPanel.add(new Label("ball"));
		SimpleVector3Editor ballPositionEditor=new SimpleVector3Editor(new SimpleVector3EditorListener() {
			@Override
			public void onValueChanged(Vector3 value) {
				ballPosition.copy(value);
				resetProperties();
				
				initConstraint();
			}
		});
		controlerRootPanel.add(ballPositionEditor);
		ballPositionEditor.setValue(ballPosition);
		
		controlerRootPanel.add(new Label("ball2s"));
		SimpleVector3Editor ball2PositionEditor=new SimpleVector3Editor(new SimpleVector3EditorListener() {
			@Override
			public void onValueChanged(Vector3 value) {
				ball2Position.copy(value);
				resetProperties();
				
				initConstraint();
			}
		});
		controlerRootPanel.add(ball2PositionEditor);
		ball2PositionEditor.setValue(ball2Position);
		
		CheckBox frameConstraintCheck=new CheckBox("use FrameB");
		controlerRootPanel.add(frameConstraintCheck);
		frameConstraintCheck.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				useFrameB=event.getValue();
				resetProperties();
				
				initConstraint();
			}
		});
		frameConstraintCheck.setValue(useFrameB);
		
		
		controlerRootPanel.add(new HTML("<h4>Mass</h4>"));
		controlerRootPanel.add(new Label("no idea.how effect"));
		LabeledInputRangeWidget2 massEditor=new LabeledInputRangeWidget2("mass", .1, 100, .1);
		massEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				mass=event.getValue().doubleValue();
				resetProperties();
				
				
			}
		});
		massEditor.setValue(mass);
		controlerRootPanel.add(massEditor);
		
		

		controlerRootPanel.add(new HTML("<h4>Ball Damping</h4>"));
		
		LabeledInputRangeWidget2 linearDampingEditor=new LabeledInputRangeWidget2("Linear", 0, 1, .01);
		linearDampingEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				linearDamping=event.getValue().doubleValue();
				
				resetProperties();
				
				
			}
		});
		linearDampingEditor.setValue(linearDamping);
		controlerRootPanel.add(linearDampingEditor);
		
		LabeledInputRangeWidget2 angularDampingEditor=new LabeledInputRangeWidget2("Angular", 0, 1, .01);
		angularDampingEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				angularDamping=event.getValue().doubleValue();
				resetProperties();
				
				
			}
		});
		angularDampingEditor.setValue(angularDamping);
		controlerRootPanel.add(angularDampingEditor);
		
		//sphere.getBody().setDamping(.1, .1);
		
	//	controlerRootPanel.add(new HTML("<h4>Ball Shape</h4>"));
	//	controlerRootPanel.add(new Label("sphere naver stop"));
		boxCheck = new CheckBox("Make box instead of shpere");
		boxCheck.setValue(true);
		boxCheck.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				newBall();
				resetProperties();
			}
			
		});
		//controlerRootPanel.add(boxCheck);
		controlerRootPanel.add(new HTML("<h4>Initial Force(Only Force2)</h4>"));
		LabeledInputRangeWidget2 xForceEditor=new LabeledInputRangeWidget2("X-Force", 0, 100, 1);
		xForceEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				xForce=event.getValue().doubleValue();
				resetProperties();
				
			}
		});
		xForceEditor.setValue(xForce);
		controlerRootPanel.add(xForceEditor);
		LabeledInputRangeWidget2 yForceEditor=new LabeledInputRangeWidget2("Y-Force", 0, 100, 1);
		yForceEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				yForce=event.getValue().doubleValue();
				resetProperties();
				
			}
		});
		yForceEditor.setValue(yForce);
		controlerRootPanel.add(yForceEditor);
		LabeledInputRangeWidget2 zForceEditor=new LabeledInputRangeWidget2("Z-Force", 0, 100, 1);
		zForceEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				zForce=event.getValue().doubleValue();
				resetProperties();
				
			}
		});
		zForceEditor.setValue(zForce);
		controlerRootPanel.add(zForceEditor);
		
		
		controlerRootPanel.add(new HTML("<h4>Restitution</h4>"));
		controlerRootPanel.add(new Label("either 0 means no bumping"));
		LabeledInputRangeWidget2 sphereRestitutionEditor=new LabeledInputRangeWidget2("Ball", 0, 2, 0.1);
		sphereRestitutionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				sphereRestitution=event.getValue().doubleValue();
				resetProperties();
			}
		});
		sphereRestitutionEditor.setValue(sphereRestitution);
		controlerRootPanel.add(sphereRestitutionEditor);
		//sphereRestitutionEditor.setValue(sphereRestitution,true);//never fire because value is already 1
		
		LabeledInputRangeWidget2 groundRestitutionEditor=new LabeledInputRangeWidget2("ground", 0, 2, 0.1);
		groundRestitutionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				
				ground.getBody().setRestitution(event.getValue().doubleValue());
				resetProperties();
			}
		});
		controlerRootPanel.add(groundRestitutionEditor);
		groundRestitutionEditor.setValue(groundRestitution);
		
		//no effect on restitution
		controlerRootPanel.add(new HTML("<h4>Friction</h4>"));
		controlerRootPanel.add(new Label("if either is 0 ,never stop"));
		LabeledInputRangeWidget2 sphereFrictionEditor=new LabeledInputRangeWidget2("Ball", 0, 10, 0.1);
		sphereFrictionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				sphereFriction=event.getValue().doubleValue();
				resetProperties();
			}
		});
		sphereFrictionEditor.setValue(sphereFriction);
		controlerRootPanel.add(sphereFrictionEditor);
		
		
		LabeledInputRangeWidget2 groundFrictionEditor=new LabeledInputRangeWidget2("ground", 0, 10, 0.1);
		groundFrictionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				ground.getBody().setFriction(event.getValue().doubleValue());
				resetProperties();
				
			}
		});
		groundFrictionEditor.setValue(groundFriction);
		controlerRootPanel.add(groundFrictionEditor);
		
		
		resetProperties();
		initConstraint();
		
		
		
	}
	
	ConstraintAndLine constraint1;
	ConstraintAndLine constraint2;
	public void initConstraint(){
		if(constraint1!=null){
			ammoControler.destroyConstraintAndLine(constraint1);
		}
		constraint1=ammoControler.createPoint2PointConstraint(ball, THREE.Vector3().copy(pivot0Position));
		
		//constraint1.setEnableSync(false);
		
		if(constraint2!=null){
			ammoControler.destroyConstraintAndLine(constraint2);
		}
		btTransform frameInA=ammoControler.makeTransform();
		btTransform frameInB=ammoControler.makeTransform();
		//not good when A fixed
		if(useFrameB){
		frameInB.getOrigin().copy(ball2Position.clone().sub(ballPosition).multiplyScalar(-1));
		}
		constraint2=ammoControler.createGeneric6DofConstraint(ball, ball2, frameInA, frameInB, false);
		
		btGeneric6DofConstraint dof=constraint2.getConstraint().castToGeneric6DofConstraint();
	
		double MPI=Math.PI/2;//180
		dof.setAngularLowerLimit(ammoControler.makeVector3(THREE.Vector3(-MPI, -MPI, -MPI)));
		dof.setAngularUpperLimit(ammoControler.makeVector3(THREE.Vector3(MPI, MPI, MPI)));
		if(useFrameB){
		
		}else{
			//distance something special
			double distance=ball2Position.distanceTo(ballPosition);
			dof.setLinearLowerLimit(ammoControler.makeVector3(THREE.Vector3(-distance, -distance, -distance)));
			dof.setLinearUpperLimit(ammoControler.makeVector3(THREE.Vector3(distance, distance, distance)));
			
		}
		//LogUtils.log(constraint2.getConstraint());
		
	}
	
	/*
	 * i have no idea how to clear all force,so make each time.
	 */
	protected void newBall() {
		boolean isBox=boxCheck.getValue();
		if(ball!=null){
			ammoControler.destroyBodyAndMesh(ball);
		}
		if(isBox){
			ball=createBox();
		}else{
			ball=createSphere();
		}
}

	double zForce=10,xForce,yForce;
	private CheckBox boxCheck;
	public void resetProperties(){
		resetProperties(ball,ballPosition);
		resetProperties(ball2,ball2Position);
	}
	public void resetProperties(BodyAndMesh target,Vector3 ballPosition){
		target.getBody().setFriction(sphereFriction);
		target.getBody().setRestitution(sphereRestitution);
		
		if(ball1Fixed && target==ball){
		target.updateMass(0);	
		}else{
		target.updateMass(mass);
		}
		target.getBody().setPosition(ballPosition);
		target.getBody().setDamping(linearDamping, angularDamping);
		
		target.getBody().setAngularVelocity(THREE.Vector3());//this working
		if(target==ball2){
			target.getBody().setLinearVelocity(THREE.Vector3(xForce, yForce, zForce));
		}else{
			target.getBody().setLinearVelocity(THREE.Vector3());
		}
		target.getBody().updateInertiaTensor();//what?
		target.getBody().setActivationState(Ammo.DISABLE_DEACTIVATION);
	}

	public BodyAndMesh createSphere(){
		 BodyAndMesh bm=ammoControler.createSphere(1, mass, 0, 0, 0, 
					THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x880000))
							);
		 
		 bm.getBody().setActivationState(Ammo.DISABLE_DEACTIVATION);
			btVector3 all=Ammo.btVector3(1, 1, 1);
			bm.getBody().setLinearFactor(all);
			bm.getBody().setAngularFactor(all);
			all.destroy();
			
		return bm;
	}
	public BodyAndMesh createBox(){
		 BodyAndMesh bm=ammoControler.createBox(THREE.Vector3(2, 2, 2), mass, 0, 0, 0, 
					THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x880000))
							);
		 LogUtils.log(bm.getBody());
		 
		 bm.getBody().setActivationState(Ammo.DISABLE_DEACTIVATION);
			btVector3 all=Ammo.btVector3(1, 1, 1);
			bm.getBody().setLinearFactor(all);
			bm.getBody().setAngularFactor(all);
			all.destroy();
			
		return bm;
	}
}
