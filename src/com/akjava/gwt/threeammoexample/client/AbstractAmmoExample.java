package com.akjava.gwt.threeammoexample.client;

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

public abstract class AbstractAmmoExample extends AbstractExample{



	@Override
	public void animate(double timestamp) {
		trackballControls.update();
		ammoControler.update();
		renderer.render( scene, camera );
		
		stats.update();
	}
	protected double WIDTH;
	protected double HEIGHT;
	protected  WebGLRenderer renderer;
	protected Scene scene;
	protected  PerspectiveCamera camera;
	protected  Stats stats;

	protected AmmoControler ammoControler;
	protected FocusPanel rendererContainer;
	protected TrackballControls trackballControls;

	protected  double windowHalfX,windowHalfY;
	protected VerticalPanel controlerRootPanel;
	
	protected  void setUpThreeScene(){
		scene = THREE.Scene();
	}
	
	
	
	protected  void setUpThreeRenderer(){
		rendererContainer = createContainerPanel();
		 
		renderer = THREE.WebGLRenderer( GWTParamUtils.WebGLRenderer().antialias(true) );//renderer = new THREE.WebGLRenderer( { antialias: true } );
		renderer.setClearColor( 0);
		renderer.setPixelRatio( GWTThreeUtils.getWindowDevicePixelRatio() );//renderer.setPixelRatio( window.devicePixelRatio );
		renderer.setSize( WIDTH, HEIGHT );
		rendererContainer.getElement().appendChild( renderer.getDomElement() );
		
		
	}
	
	protected  void setUpTCamera(){
		camera = THREE.PerspectiveCamera(55, getWindowInnerWidth()/getWindowInnerHeight(), 1, 10000 );
		camera.getPosition().set(0, 0, 15);
	}
	
	protected  void setUpLight(){
		// make part
		Light light = THREE.DirectionalLight(0xcccccc);
		light.getPosition().set(0, 1, 1);
		scene.add(light);

		scene.add(THREE.AmbientLight(0x666666));
	}
	
	protected  void setUpStats(){
		stats=Stats.create();
		stats.setPosition(0, 0);
		rendererContainer.getElement().appendChild(stats.domElement());
	}
	
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
		ammoControler=new AmmoControler(scene, Ammo.initWorld());
		
		controlerRootPanel=addResizeHandlerAndCreateGUIPanel();
		
		//make scene
		makeAmmoObjects();
		
		
	}


	protected abstract void makeAmmoObjects();
	

	@Override
	public void stop() {
		super.stop();
		stats.domElement().removeFromParent();
		ammoControler.deleteGarbages();
		ammoControler.destroyWorld();
	}

	
	@Override
	public void onWindowResize() {
		windowHalfX = getWindowInnerWidth() / 2;
		windowHalfY = getWindowInnerHeight() / 2;
		
		camera.setAspect(getWindowInnerWidth() / getWindowInnerHeight());
		camera.updateProjectionMatrix();

		renderer.setSize( (int)getWindowInnerWidth() , (int)getWindowInnerHeight() );
	}

}
