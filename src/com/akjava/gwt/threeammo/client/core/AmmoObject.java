package com.akjava.gwt.threeammo.client.core;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
public class AmmoObject extends JavaScriptObject{
protected AmmoObject(){}

public final  native void destroy()/*-{
$wnd.Ammo.destroy(this);
if(this._refs){
	for(var i=0;i<this._refs.length;i++){
		$wnd.Ammo.destroy(this._refs[i]);
	}
}

}-*/;


public final  native JsArray<AmmoObject> getDestroyWiths()/*-{
if(!this._refs){
	this._refs=[];
}
return this._refs;

}-*/;
}