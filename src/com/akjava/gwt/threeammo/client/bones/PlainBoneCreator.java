package com.akjava.gwt.threeammo.client.bones;

import java.util.List;

import com.akjava.gwt.three.client.gwt.boneanimation.AnimationBone;
import com.akjava.gwt.three.client.java.utils.AnimationUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Bone;
import com.akjava.gwt.three.client.js.objects.SkinnedMesh;
import com.akjava.gwt.threeammo.client.AmmoControler;
import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class PlainBoneCreator {

	/**
	 * 
	 * use BodyAndMeshFunctions.getMeshPosition()) to transfrom BodyAndMesh to Vector3
	 * 
	 * @param positions list must created loop for(y){for(x){}}
	 * @param slices same as face number ,actual vertex number  is +1
	 * 
	 * not support horizontal connection.this means make a hoke
	 * @return
	 */
	public JsArray<AnimationBone> createBone(List<Vector3> positions,int slices){
		JsArray<AnimationBone> bones=JavaScriptObject.createArray().cast();
		AnimationBone bone=AnimationUtils.createAnimationBone();
		bone.setParent(-1);
		bone.setName("root");
		bone.setPos(THREE.Vector3());
		bones.push(bone);
		
		int horizontalVertexCount=slices+1;
		int verticalVertexCount=positions.size()/horizontalVertexCount;
		
		Vector3 rootPos=THREE.Vector3();
		
		for(int i=0;i<horizontalVertexCount;i++){
		
			Vector3 position=positions.get(i);
			rootPos.add(position);
		}
		rootPos.divideScalar(horizontalVertexCount);
		bone.setPos(rootPos);
		
		int boneIndex=1;//index 0 added above
		for(int i=0;i<horizontalVertexCount;i++){
			int parent=0;
			Vector3 parentPos=rootPos.clone();
			
			for(int j=0;j<verticalVertexCount;j++){
				
				int ind=(horizontalVertexCount)*j+i;
				
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
	
	
public static void syncBones(AmmoControler ammoControler,SkinnedMesh mesh,int w,List<BodyAndMesh> particles,double divided){
		
		
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
			
			if(atY==0){//is this work?
				BodyAndMesh bm=getParticle(particles,w,atX, atY);
				ammoControler.updateBone(bones.get(i), bm,null,divided);
			}else{
				//should i make a body and bone?
				BodyAndMesh bm=getParticle(particles,w,atX, atY);
				ammoControler.updateBone(bones.get(i), bm,null,divided);
			}	
		}
	}

	public static void syncBones(AmmoControler ammoControler,SkinnedMesh mesh,int w,List<BodyAndMesh> particles){
		syncBones(ammoControler, mesh, w, particles,1);
	}
	
	public static BodyAndMesh getParticle(List<BodyAndMesh> particles,int w,int x,int y){
		int index=(w+1)*y+x;
		//LogUtils.log(x+"x"+y+"="+index);
		return particles.get(index);
	}
}
