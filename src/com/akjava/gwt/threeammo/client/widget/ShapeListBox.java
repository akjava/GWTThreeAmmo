package com.akjava.gwt.threeammo.client.widget;

import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.google.gwt.user.client.ui.ListBox;

public class ShapeListBox extends ListBox{

	public ShapeListBox(){
		super();
		//order must be same as BodyAndMesh value
		addItem("Sphere");
		addItem("Box");
		addItem("Cylinder");
		addItem("Capsule");
		addItem("Cone");
		setSelectedIndex(0);
	}
	
	
}
