package com.akjava.gwt.threeammo.client.core;

import com.akjava.gwt.threeammo.client.core.constraints.btGeneric6DofConstraint;
import com.akjava.gwt.threeammo.client.core.constraints.btGeneric6DofSpringConstraint;
import com.akjava.gwt.threeammo.client.core.constraints.btPoint2PointConstraint;


public class btTypedConstraint extends AmmoObject{
protected btTypedConstraint(){}

public final  native btGeneric6DofSpringConstraint castToGeneric6DofSpringConstraint()/*-{
return this;
}-*/;
public final  native btGeneric6DofConstraint castToGeneric6DofConstraint()/*-{
return this;
}-*/;
public final  native btPoint2PointConstraint castToPoint2PointConstraint()/*-{
return this;
}-*/;
}