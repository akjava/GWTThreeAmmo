package com.akjava.gwt.threeammo.client.bones;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import com.akjava.gwt.three.client.gwt.boneanimation.AnimationBone;
import com.akjava.gwt.three.client.java.utils.AnimationUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Vector2;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Bone;
import com.akjava.gwt.three.client.js.objects.SkinnedMesh;
import com.akjava.gwt.threeammo.client.AmmoControler;
import com.akjava.gwt.threeammo.client.BodyAndMesh;
import com.akjava.gwt.threeammo.client.Vector3AndQuaternion;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class PlainBoneCreator {

	/**
	 * 
	 * use BodyAndMeshFunctions.getMeshPosition()) to transfrom BodyAndMesh to Vector3
	 * 
	 * @param positions list must created loop for(y){for(x){}} , not contain root position
	 * @param slices same as face number ,actual vertex number  is +1
	 * 
	 * not support horizontal connection.this means make a hoke
	 * @return
	 */
	
	
	/**
	 * maybe this is useless
	 */
	private int startVerticalIndex;
	public PlainBoneCreator startVerticalIndex(int index){
		startVerticalIndex=index;
		return this;
	}
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
				childBone.setName(i+","+(j+startVerticalIndex));
				
				childBone.setPos(position.clone().sub(parentPos));
				bones.push(childBone);
				
				parent=boneIndex;
				boneIndex++;
				parentPos=position;
				
			}
			
		}
		
		
		return bones;
	}
	
	public static List<List<Integer>> splitBySlices(JsArray<AnimationBone> bones,int slices){
		List<List<Integer>> list=Lists.newArrayList();
		int horizontalVertexCount=slices+1;
		int verticalVertexCount=(bones.length()-1)/horizontalVertexCount;
		
		for(int i=0;i<horizontalVertexCount;i++){
			List<Integer> array=Lists.newArrayList();
			for(int j=0;j<verticalVertexCount;j++){
				int index=verticalVertexCount*i+j+1;//0 is root
				array.add(index);
			}
			list.add(array);
		}
		return list;
	}
	

	public static Vector2 parseBoneName(String boneName){
		checkNotNull(boneName,"boneName is  null");
		checkArgument(boneName.indexOf(",")!=-1,"not plain-bone name ");
		String[] name=boneName.split(",");
		int atX=Integer.parseInt(name[0]);
		int atY=Integer.parseInt(name[1]);
		return THREE.Vector2(atX, atY);
	}

public static void syncBones(AmmoControler ammoControler,SkinnedMesh mesh,int w,List<BodyAndMesh> particles,double divided){
		
		
		JsArray<Bone> bones=mesh.getSkeleton().getBones();
		
		//LogUtils.log("reset!");
		mesh.getSkeleton().pose();//is this reset?,maybe yes
		
		Vector3 rootPos=THREE.Vector3();
		int rootPosCount=0;
		//no need [0] root
		Map<String,Vector3AndQuaternion> vqMap=Maps.newHashMap();
		for(int i=1;i<bones.length();i++){
			if(bones.get(i).getName().equals("root")){//guess never happen
				continue;
			}
			
			String[] name=bones.get(i).getName().split(",");
			int atX=Integer.parseInt(name[0]);
			int atY=Integer.parseInt(name[1]);
			
			if(atY==0){
				BodyAndMesh bm=getParticle(particles,w,atX, atY);
				Vector3AndQuaternion vq=ammoControler.getWorldTransform(bm,null);
				rootPos.add(vq.getVector3());
				rootPosCount++;
				vqMap.put(bones.get(i).getName(), vq);
			}else{
				//break;
			}
		}
		rootPos.divideScalar(rootPosCount);
		ammoControler.updateBone(bones.get(0), rootPos,THREE.Quaternion(),divided);//TODO support quaternion
		
		for(int i=1;i<bones.length();i++){
			
			if(bones.get(i).getName().equals("root")){
				continue;
			}
			String[] name=bones.get(i).getName().split(",");
			int atY=Integer.parseInt(name[1]);
			
			
			if(atY==0){
				Vector3AndQuaternion vq=vqMap.get(bones.get(i).getName());
				ammoControler.updateBone(bones.get(i),vq,divided);
			}else{
				int atX=Integer.parseInt(name[0]);
				//should i make a body and bone?
				BodyAndMesh bm=getParticle(particles,w,atX, atY);
				ammoControler.updateBone(bones.get(i), bm,null,divided);
			}	
		}
		
		//update root-pos
		//somehow faild TODO fix ,maybe update root first
		
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
