package com.akjava.gwt.threeammo.client.core;

import com.akjava.gwt.threeammo.client.core.constraints.btGeneric6DofSpringConstraint;


public class btTypedConstraint extends AmmoObject{
protected btTypedConstraint(){}

public final  native btGeneric6DofSpringConstraint castToGeneric6DofSpringConstraint()/*-{
return this;
}-*/;
}