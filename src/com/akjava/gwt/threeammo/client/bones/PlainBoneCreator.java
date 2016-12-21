package com.akjava.gwt.threeammo.client.bones;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import com.akjava.gwt.lib.client.LogUtils;
import com.akjava.gwt.three.client.gwt.boneanimation.AnimationBone;
import com.akjava.gwt.three.client.java.utils.AnimationUtils;
import com.akjava.gwt.three.client.js.THREE;
import com.akjava.gwt.three.client.js.math.Quaternion;
import com.akjava.gwt.three.client.js.math.Vector2;
import com.akjava.gwt.three.client.js.math.Vector3;
import com.akjava.gwt.three.client.js.objects.Bone;
import com.akjava.gwt.three.client.js.objects.Skeleton;
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
	public static int calcurateBoneCount(int positionsSize,int slices){
		int ret=1;
		int horizontalVertexCount=slices+1;
		int verticalVertexCount=positionsSize/horizontalVertexCount;
		return ret+horizontalVertexCount*verticalVertexCount;
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
	
		ammoControler.updateBone(bones.get(0), rootPos,THREE.Quaternion(),divided);
		for(int i=1;i<bones.length();i++){
			
			if(bones.get(i).getName().equals("root")){
				continue;
			}
			String[] name=bones.get(i).getName().split(",");
			int atY=Integer.parseInt(name[1]);
			
			
			if(atY==0){//already calcurated for make root(center) pos
				Vector3AndQuaternion vq=vqMap.get(bones.get(i).getName());
				ammoControler.updateBone(bones.get(i),vq,divided);
			}else{
				int atX=Integer.parseInt(name[0]);
				//should i make a body and bone?
				BodyAndMesh bm=getParticle(particles,w,atX, atY);
				//ammoControler.updateBone(bones.get(i), bm,null,divided,false);//i'm not sure disable position is good
				ammoControler.updateBone(bones.get(i), bm,null,divided);
			}	
		}
		
		//update root-pos
		//somehow faild TODO fix ,maybe update root first
		
	}


public static native final void pose(Skeleton skelton,int offset,int length)/*-{


	var bone;

	// recover the bind-time world matrices

	for ( var b = offset, bl = offset+length; b < bl; b ++ ) {

	   
		bone = skelton.bones[ b ];

		if ( bone ) {

			bone.matrixWorld.getInverse( skelton.boneInverses[ b ] );

		}

	}

	// compute the local matrices, positions, rotations and scales

	for ( var b = offset, bl = offset+length; b < bl; b ++ ) {

		bone = skelton.bones[ b ];
		
		if ( bone ) {

			if ( bone.parent ) {

				bone.matrix.getInverse( bone.parent.matrixWorld );
				bone.matrix.multiply( bone.matrixWorld );

			} else {

				bone.matrix.copy( bone.matrixWorld );

			}

			bone.matrix.decompose( bone.position, bone.quaternion, bone.scale );

		}

	}

}-*/;

//fixed bug #8976 https://github.com/mrdoob/three.js/pull8976/
public static native final void pose(Skeleton skelton)/*-{

	var bone;

	// recover the bind-time world matrices

	for ( var b = 0, bl = skelton.bones.length; b < bl; b ++ ) {

		bone = skelton.bones[ b ];

		if ( bone ) {

			bone.matrixWorld.getInverse( skelton.boneInverses[ b ] );

		}

	}

	// compute the local matrices, positions, rotations and scales

	for ( var b = 0, bl = skelton.bones.length; b < bl; b ++ ) {

		bone = skelton.bones[ b ];

		if ( bone ) {

			if ( bone.parent instanceof $wnd.THREE.Bone ) {

				bone.matrix.getInverse( bone.parent.matrixWorld );
				bone.matrix.multiply( bone.matrixWorld );

			} else {

				bone.matrix.copy( bone.matrixWorld );

			}

			bone.matrix.decompose( bone.position, bone.quaternion, bone.scale );

		}

	}

}-*/;

//test res q
public static void testsyncBones(Quaternion q,AmmoControler ammoControler,SkinnedMesh mesh,int w,List<BodyAndMesh> particles,double divided,String suffix,int offset,int boneLength){
	
	
	JsArray<Bone> bones=mesh.getSkeleton().getBones();
	
	if(offset+boneLength>bones.length()){
		LogUtils.log("invalid bone offset or length:offset="+offset+",boneLength="+boneLength+",bones="+bones.length());
		return;
	}
	
	//LogUtils.log("reset!");
	//mesh.getSkeleton().pose();//is this reset?,maybe yes
	
	//some how this change scale
	//pose(mesh.getSkeleton(),offset+1,boneLength-1);
	//pose(mesh.getSkeleton(),0,1);
	//pose(mesh.getSkeleton(),offset,boneLength);
	//pose(mesh.getSkeleton(),offset-1,boneLength+1);
	boolean enablePosition=false;//switch to mixer first<what mean?
	//pose(mesh.getSkeleton());
	
	
	Vector3 rootPos=THREE.Vector3();
	int rootPosCount=0;
	//no need [0] root
	
	//calcurate average to define root pos,and cache data
	Map<String,Vector3AndQuaternion> vqMap=Maps.newHashMap();
	for(int i=1+offset;i<offset+boneLength;i++){
		if(bones.get(i).getName().equals("root")){//guess never happen
			continue;
		}

		if(bones.get(i).getName().length()<suffix.length()){
			LogUtils.log("smaller than suffix,invalid name:"+bones.get(i).getName());
			continue;
		}
		
		String[] name=bones.get(i).getName().substring(suffix.length()).split(",");
		
		if(name.length==1){
			LogUtils.log("not cordinate pattern,invalid name:"+bones.get(i).getName());
			continue;
		}
		
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
	
//	rootPos.divideScalar(500);//character //temporary.somehow become small & strange position
	//no need root? can't trust this root value
	//maybe no need root?
	//if set broken
	//ammoControler.updateBone(bones.get(offset), rootPos,THREE.Quaternion(),divided);//TODO support quaternion
	
	for(int i=1+offset;i<offset+boneLength;i++){
		
		if(bones.get(i).getName().equals("root")){
			continue;
		}
		
		if(bones.get(i).getName().length()<suffix.length()){
			LogUtils.log("smaller than suffix,invalid name:"+bones.get(i).getName());
			continue;
		}
		
		String[] name=bones.get(i).getName().substring(suffix.length()).split(",");
		
		if(name.length==1){
			LogUtils.log("not cordinate pattern,invalid name:"+bones.get(i).getName());
			continue;
		}
		
		int atY=Integer.parseInt(name[1]);
		
		//LogUtils.log("update-bone:"+bones.get(i).getName());
		
		//bones.get(i).setScale(1, 1, 1);//test
		if(bones.get(i).getScale().getX()!=1){
		//bones.get(i).getPosition().setScalar(1000);
		}
		//bones.get(i).updateMatrixWorld(true);
		
		Quaternion inverse=q.clone().inverse();
		
		if(atY==0){//already calcurated for make root(center) pos
			Vector3AndQuaternion vq=vqMap.get(bones.get(i).getName());
			vq.getQuaternion().multiply(inverse);
			
			ammoControler.updateBone(bones.get(i),vq,divided,enablePosition);//pinned & no need
		}else{
			int atX=Integer.parseInt(name[0]);
			//should i make a body and bone?
			BodyAndMesh bm=getParticle(particles,w,atX, atY);
			Vector3AndQuaternion vq=ammoControler.getWorldTransform(bm,null);
			vq.getQuaternion().multiply(inverse);
			
			ammoControler.updateBone(bones.get(i),vq,divided,enablePosition);
			//ammoControler.updateBone(bones.get(i), bm,null,divided,enablePosition);
		}	
	}
	
	//update root-pos
	//somehow faild TODO fix ,maybe update root first
	
}
public static void syncBones(AmmoControler ammoControler,SkinnedMesh mesh,int w,List<BodyAndMesh> particles,double divided,String suffix,int offset,int boneLength){
	
	
	JsArray<Bone> bones=mesh.getSkeleton().getBones();
	
	if(offset+boneLength>bones.length()){
		LogUtils.log("invalid bone offset or length:offset="+offset+",boneLength="+boneLength+",bones="+bones.length());
		return;
	}
	
	//LogUtils.log("reset!");
	//mesh.getSkeleton().pose();//is this reset?,maybe yes
	
	//some how this change scale
	//pose(mesh.getSkeleton(),offset+1,boneLength-1);
	//pose(mesh.getSkeleton(),0,1);
	//pose(mesh.getSkeleton(),offset,boneLength);
	//pose(mesh.getSkeleton(),offset-1,boneLength+1);
	boolean enablePosition=false;//switch to mixer first<what mean?
	//pose(mesh.getSkeleton());
	
	
	Vector3 rootPos=THREE.Vector3();
	int rootPosCount=0;
	//no need [0] root
	
	//calcurate average to define root pos,and cache data
	Map<String,Vector3AndQuaternion> vqMap=Maps.newHashMap();
	for(int i=1+offset;i<offset+boneLength;i++){
		if(bones.get(i).getName().equals("root")){//guess never happen
			continue;
		}

		if(bones.get(i).getName().length()<suffix.length()){
			LogUtils.log("smaller than suffix,invalid name:"+bones.get(i).getName());
			continue;
		}
		
		String[] name=bones.get(i).getName().substring(suffix.length()).split(",");
		
		if(name.length==1){
			LogUtils.log("not cordinate pattern,invalid name:"+bones.get(i).getName());
			continue;
		}
		
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
	
//	rootPos.divideScalar(500);//character //temporary.somehow become small & strange position
	//no need root? can't trust this root value
	//maybe no need root?
	//if set broken
	//ammoControler.updateBone(bones.get(offset), rootPos,THREE.Quaternion(),divided);//TODO support quaternion
	
	for(int i=1+offset;i<offset+boneLength;i++){
		
		if(bones.get(i).getName().equals("root")){
			continue;
		}
		
		if(bones.get(i).getName().length()<suffix.length()){
			LogUtils.log("smaller than suffix,invalid name:"+bones.get(i).getName());
			continue;
		}
		
		String[] name=bones.get(i).getName().substring(suffix.length()).split(",");
		
		if(name.length==1){
			LogUtils.log("not cordinate pattern,invalid name:"+bones.get(i).getName());
			continue;
		}
		
		int atY=Integer.parseInt(name[1]);
		
		//LogUtils.log("update-bone:"+bones.get(i).getName());
		
		//bones.get(i).setScale(1, 1, 1);//test
		if(bones.get(i).getScale().getX()!=1){
		//bones.get(i).getPosition().setScalar(1000);
		}
		//bones.get(i).updateMatrixWorld(true);
		
		if(atY==0){//already calcurated for make root(center) pos
			Vector3AndQuaternion vq=vqMap.get(bones.get(i).getName());
			ammoControler.updateBone(bones.get(i),vq,divided,enablePosition);//pinned & no need
		}else{
			int atX=Integer.parseInt(name[0]);
			//should i make a body and bone?
			BodyAndMesh bm=getParticle(particles,w,atX, atY);
			ammoControler.updateBone(bones.get(i), bm,null,divided,enablePosition);
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
