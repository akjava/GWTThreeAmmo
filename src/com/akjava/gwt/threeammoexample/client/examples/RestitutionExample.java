package com.akjava.gwt.threeammoexample.client.examples;

import com.akjava.gwt.three.client.gwt.GWTParamUtils;
import com.akjava.gwt.three.client.gwt.ui.LabeledInputRangeWidget2;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.threeammo.client.BoxBodyAndMesh;
import com.akjava.gwt.threeammo.client.SphereBodyAndMesh;
import com.akjava.gwt.threeammo.client.core.Ammo;
import com.akjava.gwt.threeammoexample.client.AmmoExample;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

public class RestitutionExample extends AmmoExample{

	@Override
	public String getName() {
		return "Restitution";
	}

	@Override
	public String getTokenKey() {
		return "restitution";
	}
	
	private double groundRestitution=1;
	private double sphereRestitution=1;
	
	
/**
 * 
 * force is not clear
 * 
 */
	@Override
	protected void makeAmmoObjects() {
		
		double groundMass=0.0;
		
		final BoxBodyAndMesh ground=ammoControler.createBox(THREE.Vector3(8, 2, 8), groundMass, 0, 0, 0, 
				THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x888888))
				);
		ground.getBody().setRestitution(groundRestitution);
		
		final SphereBodyAndMesh sphere=ammoControler.createSphere(1, 1, 0, 10, 0, 
				THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x880000))
						);
		sphere.getBody().setActivationState(Ammo.DISABLE_DEACTIVATION);
		
		sphere.getBody().setRestitution(sphereRestitution);
		//sphere.getBody().setDamping(.1, .1);
		
		controlerRootPanel.add(new HTML("<h4>Restitution</h4>"));
		controlerRootPanel.add(new Label("if sphere * ground == 1,it would move back."));
		LabeledInputRangeWidget2 sphereRestitutionEditor=new LabeledInputRangeWidget2("Sphere", 0, 2, 0.1);
		sphereRestitutionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				
				sphere.getBody().setRestitution(event.getValue().doubleValue());
				sphere.getBody().setPosition(0, 10, 0);
			}
		});
		controlerRootPanel.add(sphereRestitutionEditor);
		//sphereRestitutionEditor.setValue(sphereRestitution,true);//never fire because value is already 1
		
		LabeledInputRangeWidget2 groundRestitutionEditor=new LabeledInputRangeWidget2("ground", 0, 2, 0.1);
		groundRestitutionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				
				ground.getBody().setRestitution(event.getValue().doubleValue());
				sphere.getBody().setPosition(0, 10, 0);
			}
		});
		controlerRootPanel.add(groundRestitutionEditor);
		
		
		//no effect on restitution
		controlerRootPanel.add(new HTML("<h4>Friction</h4>"));
		controlerRootPanel.add(new Label("no effect,on this case"));
		LabeledInputRangeWidget2 sphereFrictionEditor=new LabeledInputRangeWidget2("Sphere", 0, 10, 0.1);
		sphereFrictionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				sphere.getBody().setFriction(event.getValue().doubleValue());
				sphere.getBody().setPosition(0, 10, 0);
			}
		});
		controlerRootPanel.add(sphereFrictionEditor);
		
		LabeledInputRangeWidget2 groundFrictionEditor=new LabeledInputRangeWidget2("ground", 0, 10, 0.1);
		groundFrictionEditor.addtRangeListener(new ValueChangeHandler<Number>() {
			@Override
			public void onValueChange(ValueChangeEvent<Number> event) {
				
				ground.getBody().setFriction(event.getValue().doubleValue());
				sphere.getBody().setPosition(0, 10, 0);
			}
		});
		controlerRootPanel.add(groundFrictionEditor);
	}
	
	

}
