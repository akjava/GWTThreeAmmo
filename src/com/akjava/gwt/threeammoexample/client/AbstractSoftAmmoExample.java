package com.akjava.gwt.threeammoexample.client;

import com.akjava.gwt.lib.client.LogUtils;
import com.akjava.gwt.stats.client.Stats;
import com.akjava.gwt.three.client.examples.js.THREEExp;
import com.akjava.gwt.three.client.examples.js.controls.TrackballControls;
import com.akjava.gwt.three.client.gwt.GWTParamUtils;
import com.akjava.gwt.three.client.java.ui.example.AbstractExample;
import com.akjava.gwt.three.client.java.utils.GWTThreeUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.cameras.PerspectiveCamera;
import com.akjava.gwt.three.client.js.lights.Light;
import com.akjava.gwt.three.client.js.renderers.WebGLRenderer;
import com.akjava.gwt.three.client.js.scenes.Scene;
import com.akjava.gwt.threeammo.client.AmmoControler;
import com.akjava.gwt.threeammo.client.core.Ammo;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class AbstractSoftAmmoExample extends AbstractAmmoExample{

	
	@Override
	public void init() {
		windowHalfX = getWindowInnerWidth() / 2;//var windowHalfX = window.innerWidth / 2;
		windowHalfY = getWindowInnerHeight() / 2;//var windowHalfY = window.innerHeight / 2;
		WIDTH = getWindowInnerWidth();
		HEIGHT =getWindowInnerHeight();		
		
		setUpThreeScene();
		setUpThreeRenderer();
		setUpTCamera();
		setUpLight();
		setUpStats();
		
		trackballControls = THREEExp.TrackballControls(camera);
		ammoControler=new AmmoControler(scene, Ammo.initSoftBodyWorld(0,-10,0));
		
		LogUtils.log(ammoControler.getWorld());
		
		controlerRootPanel=addResizeHandlerAndCreateGUIPanel();
		
		//make scene
		makeAmmoObjects();
		
		
	}



}
