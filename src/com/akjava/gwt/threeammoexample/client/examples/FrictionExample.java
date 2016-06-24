package com.akjava.gwt.threeammoexample.client.examples;

import com.akjava.gwt.lib.client.LogUtils;
import com.akjava.gwt.three.client.gwt.GWTParamUtils;
import com.akjava.gwt.three.client.gwt.ui.LabeledInputRangeWidget2;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.akjava.gwt.threeammo.client.BoxBodyAndMesh;
import com.akjava.gwt.threeammo.client.core.Ammo;
import com.akjava.gwt.threeammo.client.core.btVector3;
import com.akjava.gwt.threeammoexample.client.AmmoExample;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class FrictionExample extends AmmoExample{

	@Override
	public String getName() {
		return "Friction";
	}

	@Override
	public String getTokenKey() {
		return "friction";
	}
	
	private double groundRestitution=0;
	private double sphereRestitution=0;
	
	private double groundFriction=0.1;
	private double sphereFriction=0.1;
	private BodyAndMesh ball;
	
	
/**
 * 
 * force is not clear
 * 
 */
	@Override
	protected void makeAmmoObjects() {
		
		camera.getPosition().setY(5);
		
		double groundMass=0.0;
		
		final BoxBodyAndMesh ground=ammoControler.createBox(THREE.Vector3(80, 2, 160), groundMass, 0, 0, 0, 
				THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x888888))
				);
		ground.getBody().setRestitution(groundRestitution);
		
		ball = createBox();
		
		
		
		//sphere.getBody().setDamping(.1, .1);
		
		controlerRootPanel.add(new HTML("<h4>Ball Shape</h4>"));
		controlerRootPanel.add(new Label("sphere naver stop"));
		boxCheck = new CheckBox("Make box instead of shpere");
		controlerRootPanel.add(boxCheck);
		boxCheck.setValue(true);
		boxCheck.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				newBall();
			}
			
		});
		
		
		controlerRootPanel.add(new Label("bigfriction make jump.over 56 ,most of them can't stop"));
		LabeledInputRangeWidget2 forceEditor=new LabeledInputRangeWidget2("Force", 1, 100, 1);
		forceEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				zForce=event.getValue().doubleValue();
				newBall();
				
			}
		});
		forceEditor.setValue(zForce);
		controlerRootPanel.add(forceEditor);
		
		
		controlerRootPanel.add(new HTML("<h4>Restitution</h4>"));
		controlerRootPanel.add(new Label("either 0 means no bumping"));
		LabeledInputRangeWidget2 sphereRestitutionEditor=new LabeledInputRangeWidget2("Sphere", 0, 2, 0.1);
		sphereRestitutionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				
				ball.getBody().setRestitution(event.getValue().doubleValue());
				newBall();
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
				newBall();
			}
		});
		controlerRootPanel.add(groundRestitutionEditor);
		groundRestitutionEditor.setValue(groundRestitution);
		
		//no effect on restitution
		controlerRootPanel.add(new HTML("<h4>Friction</h4>"));
		controlerRootPanel.add(new Label("if either is 0 ,never stop"));
		LabeledInputRangeWidget2 sphereFrictionEditor=new LabeledInputRangeWidget2("Sphere", 0, 10, 0.1);
		sphereFrictionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				ball.getBody().setFriction(event.getValue().doubleValue());
				newBall();
			}
		});
		sphereFrictionEditor.setValue(sphereFriction);
		controlerRootPanel.add(sphereFrictionEditor);
		
		
		LabeledInputRangeWidget2 groundFrictionEditor=new LabeledInputRangeWidget2("ground", 0, 10, 0.1);
		groundFrictionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				ground.getBody().setFriction(event.getValue().doubleValue());
				newBall();
				
			}
		});
		groundFrictionEditor.setValue(groundFriction);
		controlerRootPanel.add(groundFrictionEditor);
		
		
		resetPosition();
	}
	
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
		resetPosition();
}

	double zForce=10,xForce;
	private CheckBox boxCheck;
	public void resetPosition(){
		/*setAngularVelocity setLinearVelocity
		 * setLinearFactor setAngularFactor zero and update
		 * can't clear force complete only  ;
		 * 
		 * btVector3 factor=Ammo.btVector3(0, 0, 0);
		ball.getBody().setLinearFactor(factor);
		ball.getBody().setAngularFactor(factor);
		
		
		factor.set(1, 1, 1);
		ball.getBody().setLinearFactor(factor);
		ball.getBody().setAngularFactor(factor);
		
		 */
		//ball.getBody().setAngularVelocity(THREE.Vector3(0, 0, 0));
		//ball.getBody().setLinearVelocity(THREE.Vector3(0, 0, 0));
		
		
		
		
		ball.getBody().setPosition(0, 1, -79);
		
		
		ball.getBody().setLinearVelocity(THREE.Vector3(xForce, 0, zForce));
	}

	public BodyAndMesh createSphere(){
		 BodyAndMesh bm=ammoControler.createSphere(1, 1, 0, 0, 0, 
					THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x880000))
							);
		 
		 bm.getBody().setActivationState(Ammo.DISABLE_DEACTIVATION);
			btVector3 all=Ammo.btVector3(1, 1, 1);
			bm.getBody().setLinearFactor(all);
			bm.getBody().setAngularFactor(all);
			all.destroy();
			
			bm.getBody().setRestitution(sphereRestitution);
		return bm;
	}
	public BodyAndMesh createBox(){
		 BodyAndMesh bm=ammoControler.createBox(THREE.Vector3(2, 2, 2), 1, 0, 0, 0, 
					THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x880000))
							);
		 
		 LogUtils.log(bm.getBody());
		 
		 bm.getBody().setActivationState(Ammo.DISABLE_DEACTIVATION);
			btVector3 all=Ammo.btVector3(1, 1, 1);
			bm.getBody().setLinearFactor(all);
			bm.getBody().setAngularFactor(all);
			all.destroy();
			
			bm.getBody().setRestitution(sphereRestitution);
		return bm;
	}
}
