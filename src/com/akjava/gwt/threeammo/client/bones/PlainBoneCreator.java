package com.akjava.gwt.threeammo.client.bones;

import java.util.List;

import com.akjava.gwt.three.client.gwt.boneanimation.AnimationBone;
import com.akjava.gwt.three.client.java.utils.AnimationUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Bone;
import com.akjava.gwt.three.client.js.objects.SkinnedMesh;
import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.akjava.gwt.threeammo.client.ThreeAmmoControler;
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
	
	public static void syncBones(ThreeAmmoControler ammoControler,SkinnedMesh mesh,int w,List<BodyAndMesh> particles){
		
		
		JsArray<Bone> bones=mesh.getSkeleton().getBones();
		
		//LogUtils.log("reset!");
		mesh.getSkeleton().pose();//is this reset?,maybe yes
		
		//no need [0] root
		for(int i=1;i<bones.length();i++){
			
			if(bones.get(i).getName().equals("root")){
				continue;
			}
			
			String[] name=bones.get(i).getName().split(",");
			int atX=Integer.parseInt(name[0]);
			int atY=Integer.parseInt(name[1]);
			
			if(atY==0){ //has ?
				continue;//locked;
			}else{
				//should i make a body and bone?
				BodyAndMesh bm=getParticle(particles,w,atX, atY);
				ammoControler.updateBone(bones.get(i), bm,null);
			}	
		}
	}
	
	public static BodyAndMesh getParticle(List<BodyAndMesh> particles,int w,int x,int y){
		int index=(w+1)*y+x;
		//LogUtils.log(x+"x"+y+"="+index);
		return particles.get(index);
	}
}
