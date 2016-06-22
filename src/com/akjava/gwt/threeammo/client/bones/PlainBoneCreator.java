package com.akjava.gwt.threeammo.client.bones;

import java.util.List;

import com.akjava.gwt.three.client.gwt.boneanimation.AnimationBone;
import com.akjava.gwt.three.client.java.utils.AnimationUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.akjava.gwt.threeammo.client.functions.BodyAndMeshFunctions;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class PlainBoneCreator {

	/**
	 * 
	 * use BodyAndMeshFunctions.getMeshPosition()) to transfrom BodyAndMesh to Vector3
	 * 
	 * @param positions list must created loop for(y){for(x){}}
	 * @param slices same as face number ,actual vertex number  is +1
	 * @return
	 */
	public JsArray<AnimationBone> createBone(List<Vector3> positions,int slices){
		JsArray<AnimationBone> bones=JavaScriptObject.createArray().cast();
		AnimationBone bone=AnimationUtils.createAnimationBone();
		bone.setParent(-1);
		bone.setName("root");
		bone.setPos(THREE.Vector3());
		bones.push(bone);
		
		int horizontalVertex=slices+1;
		int verticalVertex=positions.size()/horizontalVertex;
		
		int boneIndex=1;//index 0 added above
		for(int i=0;i<horizontalVertex;i++){
			int parent=0;
			Vector3 parentPos=THREE.Vector3();
			
			for(int j=0;j<verticalVertex;j++){
				
				int ind=(horizontalVertex)*j+i;
				
				Vector3 position=positions.get(ind);
				
				AnimationBone childBone=AnimationUtils.createAnimationBone();
				childBone.setParent(parent);
				childBone.setName(i+","+j);
				
				childBone.setPos(position.clone().sub(parentPos));
				bones.push(childBone);
				
				parent=boneIndex;
				boneIndex++;
				parentPos=position;
				
			}
			
		}
		
		
		return bones;
	}
}
