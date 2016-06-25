package com.akjava.gwt.threeammo.client.core.constraints;

import com.akjava.gwt.threeammo.client.core.btTypedConstraint;
import com.akjava.gwt.threeammo.client.core.btVector3;

public class btGeneric6DofConstraint extends btTypedConstraint{
protected btGeneric6DofConstraint(){}




public final  native void setLinearLowerLimit(btVector3 limit)/*-{
this.setLinearLowerLimit(limit);
}-*/;

public final  native void setLinearUpperLimit(btVector3 limit)/*-{
this.setLinearUpperLimit(limit);
}-*/;

public final  native void setAngularLowerLimit(btVector3 limit)/*-{
this.setAngularLowerLimit(limit);
}-*/;

public final  native void setAngularUpperLimit(btVector3 limit)/*-{
this.setAngularUpperLimit(limit);
}-*/;





}