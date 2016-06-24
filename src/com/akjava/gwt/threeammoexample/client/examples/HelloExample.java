package com.akjava.gwt.threeammoexample.client.examples;

import com.akjava.gwt.three.client.gwt.GWTParamUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.threeammo.client.BoxBodyAndMesh;
import com.akjava.gwt.threeammo.client.SphereBodyAndMesh;
import com.akjava.gwt.threeammoexample.client.AmmoExample;

public class HelloExample extends AmmoExample{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Hello World";
	}

	@Override
	public String getTokenKey() {
		// TODO Auto-generated method stub
		return "hello";
	}
	
	@Override
	protected void makeAmmoObjects() {
		
		double groundMass=0.0;
		
		BoxBodyAndMesh ground=ammoControler.createBox(THREE.Vector3(2, 2, 2), groundMass, 0, 0, 0, 
				THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x888888))
				);
		
		SphereBodyAndMesh sphere=ammoControler.createSphere(1, 1, 0, 10, 0, 
				THREE.MeshPhongMaterial(GWTParamUtils.MeshPhongMaterial().color(0x880000))
						);
	}
	
	

}
