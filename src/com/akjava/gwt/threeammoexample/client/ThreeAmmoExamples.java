package com.akjava.gwt.threeammoexample.client;

import java.util.List;
import java.util.Map;

import com.akjava.gwt.lib.client.URLUtils;
import com.akjava.gwt.three.client.java.ui.example.Example;
import com.akjava.gwt.three.client.java.ui.example.ExampleOwner;
import com.akjava.gwt.threeammoexample.client.examples.DampingExample;
import com.akjava.gwt.threeammoexample.client.examples.FrictionExample;
import com.akjava.gwt.threeammoexample.client.examples.HelloExample;
import com.akjava.gwt.threeammoexample.client.examples.MassExample;
import com.akjava.gwt.threeammoexample.client.examples.RestitutionExample;
import com.akjava.gwt.threeammoexample.client.examples.SoftDampingExample;
import com.akjava.gwt.threeammoexample.client.examples.constraints.Point2PointAndGenericExample;
import com.akjava.gwt.threeammoexample.client.examples.constraints.Point2PointAndGenericExample2;
import com.akjava.gwt.threeammoexample.client.examples.constraints.Point2PointExample;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ThreeAmmoExamples implements EntryPoint,ExampleOwner {
	private DockLayoutPanel center;

	/**
	 * 
	 * 
	 *  known warning
	 * 'webkitRequestAnimationFrame' is vendor-specific. Please use the standard 'requestAnimationFrame' instead.
	 * 'webkitCancelRequestAnimationFrame' is vendor-specific. Please use the standard 'cancelAnimationFrame' instead.
	 */
	@Override
	public void onModuleLoad() {

		DockLayoutPanel root=new DockLayoutPanel(Unit.PX);
		
		RootLayoutPanel.get().add(root);
		
		center = new DockLayoutPanel(Unit.PX);
		
		
		
		
		//left-side
		VerticalPanel side=new VerticalPanel();
		side.setWidth("100%");
		side.setStylePrimaryName("side");
		side.setSpacing(4);
		
		Label title=new Label("GWTThreeAmmo.js Examples");
		title.setStylePrimaryName("header");
		
		side.add(title);
		
		HTML webgl=new HTML("<h4>Basic Example</h4>");
		webgl.setWidth("100%");
		side.add(webgl);
		root.addWest(side, 300);
		root.add(center);
		
		
		List<AbstractAmmoExample> examples=Lists.newArrayList();
		
		examples.add(new SoftDampingExample());
		
		examples.add(new HelloExample());
		examples.add(new RestitutionExample());
		examples.add(new FrictionExample());
		examples.add(new DampingExample());
		examples.add(new MassExample());
		//Collections.sort(examples);
		examples.add(new Point2PointExample());
		examples.add(new Point2PointAndGenericExample());
		examples.add(new Point2PointAndGenericExample2());
		
		for(Example exp:examples){
		side.add(new DemoAnchor(this,exp));
		}
		
		side.add(new HTML("<h4>Links</h4>"));
		side.add(new Anchor("Three.js(github)", "https://github.com/mrdoob/three.js/"));
		side.add(new Anchor("Ammo.js(github)", "https://github.com/kripken/ammo.js/"));
		side.add(new Anchor("GWT-Three.js(github)", "https://github.com/akjava/gwt-three.js-test"));
		side.add(new Anchor("GWT", "http://www.gwtproject.org/"));
		
		Map<String,List<String>> tokens=URLUtils.parseToken(History.getToken(), false);
		
		for(String key:tokens.keySet()){
			for(final Example exp: examples){
				if(key.equals(exp.getTokenKey())){
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						
						@Override
						public void execute() {
							selectNewExample(exp);//wait complete initialize
						}
					});
					
					break;
				}
			}
		}
		
	}

	public class DemoAnchor extends Anchor{
		public DemoAnchor(final ExampleOwner owner,final Example demo){
			super(demo.getName());
			this.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					owner.selectNewExample(demo);
				}
			});
		}
	}

	@Override
	public Panel getPanel() {
		return center;
	}

	private Example lastDemo;
	@Override
	public void selectNewExample(Example demo) {
		if(lastDemo!=null){
			lastDemo.stop();
		}
		demo.start(getPanel());
		lastDemo=demo;
		History.newItem(demo.getTokenKey());
	}
}
