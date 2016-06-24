package com.akjava.gwt.threeammo.client.core;
public class btPoint2PointConstraint extends btTypedConstraint{
protected btPoint2PointConstraint(){}


public  final  native btVector3 getPivotInA()/*-{
return this.getPivotInA();
}-*/;

public  final  native btVector3 getPivotInB()/*-{
return this.getPivotInB();
}-*/;
}