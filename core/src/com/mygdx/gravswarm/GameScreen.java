package com.mygdx.gravswarm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class GameScreen extends ScreenAdapter {
	enum ClipPlane{NEAR, FAR, LEFT, RIGHT, TOP, BOTTOM};
	enum ScreenMode{GRAVITY,PLANECHANGE,SPAWN};
	enum EdgeMode{NONE, WARP, REFLECT,DESPAWN,STOP};
	float LIGHT_INTENSITIY;
	float GRAVITY_PLANE_DISTANCE;
	int MOONS_TO_SPAWN;
	int THREAD_COUNT;
	boolean speedCheck;
	boolean blackScreen;
	EdgeMode edgeMode;
	ScreenMode screenMode;
	GravSwarm game;

	Vector<Moon> moons;
	Pool<Gravity> freeGravities;
	Pool<Moon> freeMoons;
	Vector<Gravity>gravities;
	Vector<GravityHandler>gravityHandlers;
	Vector<Gravity>gravitiesToBeCulled;
	Vector<Moon> moonsToReposition;
	Vector<Moon> moonsToDespawn;
	Material moonTexture;
	Ray warpRay;
	Vector3 workerVec, workerVec2;
	//Stage ui;

	PerspectiveCamera cam;
	Model modelTemplate;
	ModelBatch modelBatch;
	ModelInstance visualTouchPlane;
	Environment environment;
	//CameraInputController camController;
	CoreInputProcessor coreInput;
	CyclicBarrier barrier;


	public GameScreen(GravSwarm currentGame)
	{
		game=currentGame;
		speedCheck=false;
		blackScreen=true;
		setScreenColor(blackScreen);
		Random rnd=new Random();
		warpRay=new Ray();
		workerVec=new Vector3();
		workerVec2=new Vector3();
		moons=new Vector<Moon>();
		gravities=new Vector<Gravity>();
		gravitiesToBeCulled=new Vector<Gravity>();
		moonsToReposition=new Vector<Moon>();
		gravityHandlers=new Vector<GravityHandler>();
		moonsToDespawn=new Vector<Moon>();
		moonTexture=new Material(ColorAttribute.createDiffuse(1,1,1,1));
		freeGravities=new Pool<Gravity>() {
			@Override
			protected Gravity newObject() {
				return new Gravity(1f);
			}
		};

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		LIGHT_INTENSITIY=game.settings.getLIGHT_INTENSITY();
		GRAVITY_PLANE_DISTANCE=game.settings.getTOUCH_PLANE_DEPTH();
		MOONS_TO_SPAWN=game.settings.getINITIAL_MOONS_TO_SPAWN();
		THREAD_COUNT=game.settings.getWORKER_THREADS();
		edgeMode=edgeMode.values()[game.settings.getBOUNDARY_MODE().ordinal()];
		screenMode=ScreenMode.GRAVITY;

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));

		coreInput=new CoreInputProcessor();
		Gdx.input.setInputProcessor(coreInput);

		modelBatch = new ModelBatch();
		barrier= new CyclicBarrier(THREAD_COUNT+1);

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(500f, 500f, 500f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 3000f;
		cam.update();


		ModelBuilder modelBuilder = new ModelBuilder();
//		modelTemplate=modelBuilder.createLineGrid(30,30,100,100,moonTexture,VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//		visualTouchPlane=new ModelInstance(modelTemplate);

		//workerVec.scl(-1000f);
		//visualTouchPlane.transform.setToLookAt(workerVec,cam.direction);
		//visualTouchPlane.transform.setToRotation(cam.position, 0);
		//visualTouchPlane.transform.setToRotation(workerVec, 0);
		//visualTouchPlane.transform.rotate(cam.up,0);
		//visualTouchPlane.transform.setFromEulerAngles()

		modelTemplate=modelBuilder.createSphere(5f, 5f, 5f, 8, 5, moonTexture, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		freeMoons=new Pool<Moon>()
		{
			protected Moon newObject()
			{
				return new Moon(modelTemplate);
			}
		};
		for(int x=0;x<MOONS_TO_SPAWN;++x)
		{
			moons.add(new Moon(modelTemplate));
			moons.elementAt(x).transform.translate((rnd.nextFloat() * 200) - 100, (rnd.nextFloat() * 200) - 100, (rnd.nextFloat() * 200) - 100);
		}
		assignMoons(true);
		for(int x=0;x<THREAD_COUNT;++x)
		{
			gravityHandlers.elementAt(x).start();
		}


	}


	@Override
	public void render(float delta) {

		if(speedCheck)
		{
			for(int i=0;i<moons.size();++i)
				moons.elementAt(i).scaleVelocity(.7f);
		}
		try {
			barrier.await();//sync with the gravity threads before rendering
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		//singleCoreGravity();

		for(int moonNumber=0;moonNumber<moons.size();++moonNumber)
		{
			if(edgeMode!=EdgeMode.NONE)
			{
				if(moons.elementAt(moonNumber).willBeOffscreen())
				{
					switch (edgeMode)
					{
						case WARP:
							moons.elementAt(moonNumber).warp();
							break;
						case REFLECT:
							moons.elementAt(moonNumber).reflect();
							break;
						case DESPAWN:
							moonsToDespawn.add(moons.elementAt(moonNumber));
							break;
						case STOP:
							moons.elementAt(moonNumber).scaleVelocity(0f);
							break;
					}
				}
			}
			moons.elementAt(moonNumber).move();
		}


		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(cam);
		if(moons.size()>0)
			modelBatch.render(moons, environment);
		modelBatch.end();

		while(gravitiesToBeCulled.size()>0)
		{
			freeGravities.free(gravitiesToBeCulled.elementAt(0));
			gravities.remove(gravitiesToBeCulled.elementAt(0));
			gravitiesToBeCulled.remove(0);
		}
		while(moonsToReposition.size()>0)
		{
			moonsToReposition.elementAt(0).transform.setToTranslation(moonsToReposition.elementAt(0).getNextPosition());
			moonsToReposition.remove(0);
		}
		while(moonsToDespawn.size()>0)
		{
			moonsToDespawn.elementAt(0).reset();
			moons.remove(moonsToDespawn.elementAt(0));
			moonsToDespawn.remove(0);
			assignMoons(false);
		}
		try {
			barrier.await();//restart gravity calculations
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	void setScreenColor(boolean black)
	{
		blackScreen=black;
		int c=blackScreen?0:1;
		Gdx.gl.glClearColor(c,c,c,1);
	}

	void assignMoons(boolean firstrun)
	{
		int curr=0;
		int step=(firstrun?MOONS_TO_SPAWN/THREAD_COUNT:moons.size()/THREAD_COUNT);
		int next=step;
		for(int x=0;x<THREAD_COUNT;++x)
		{
			if(x<THREAD_COUNT-1)
			{
				if(firstrun)
					gravityHandlers.add(new GravityHandler(curr, next));
				else
					gravityHandlers.elementAt(x).setBounds(curr, next);
				curr=next;
				next+=step+1;
			}
			else
			{
				if(firstrun)
					gravityHandlers.add(new GravityHandler(curr,-1));
				else
					gravityHandlers.elementAt(x).setBounds(curr,-1);
			}
		}
	}

	void singleCoreGravity()
	{
		Vector3 position=new Vector3();
		int x=0;
		int y;
		while(x<moons.size())
		{
			for(y=0;y<gravities.size();++y)
			{
				moons.elementAt(x).transform.getTranslation(position);
				moons.elementAt(x).updateVelocity(gravities.elementAt(y).getAccel(position));
			}
			moons.elementAt(x).move();
			++x;
		}
	}

	@Override
	public void dispose () {
		//model.dispose();
	}

	class CoreInputProcessor extends InputAdapter
	{
		Gravity[] touchGravities;
		Vector3 worldPoint;
		int downX, downY;

		CoreInputProcessor()
		{
			touchGravities=new Gravity[10];
			worldPoint=new Vector3();
			downX=0;
			downY=0;
		}
		@Override public boolean touchDown(int x, int y, int pointer, int button)
		{
			if(button==Input.Buttons.LEFT&&screenMode==ScreenMode.GRAVITY)
			{
				worldPoint.set(cam.unproject(workerVec.set(x, y, 0f)));
				worldPoint.lerp(cam.unproject(workerVec.set(x, y, 1f)),GRAVITY_PLANE_DISTANCE);
				touchGravities[pointer] = freeGravities.obtain();
				touchGravities[pointer].spawn(worldPoint);
				gravities.add(touchGravities[pointer]);
				return true;
			}
			if(button==Input.Buttons.LEFT&&screenMode==ScreenMode.SPAWN)
			{
				downX=x;
				downY=y;
			}
			return false;
		}
		@Override public boolean touchDragged(int x,int y,int pointer)
		{
			if(touchGravities[pointer]!=null)
			{
				worldPoint.set(cam.unproject(workerVec.set(x, y, 0f)));
				worldPoint.lerp(cam.unproject(workerVec.set(x, y, 1f)),GRAVITY_PLANE_DISTANCE);
				touchGravities[pointer].setPosition(worldPoint);
				return true;
			}
			return false;
		}
		@Override public boolean touchUp(int x, int y, int pointer, int button) {
			if(button!= Input.Buttons.LEFT) return false;
			if (touchGravities[pointer] != null) {
				gravitiesToBeCulled.add(touchGravities[pointer]);
				touchGravities[pointer] = null;
				return true;
			}
			if(screenMode == ScreenMode.SPAWN)
			{
				for (int i = 0; i < 50; ++i) {
					worldPoint.set(cam.unproject(workerVec.set(downX, downY, 0f)));
					worldPoint.lerp(cam.unproject(workerVec.set(downX, downY, 1f)), GRAVITY_PLANE_DISTANCE);
					Moon tempMoon=freeMoons.obtain();
					tempMoon.transform.setToTranslation(worldPoint);
					workerVec2.set(worldPoint);
					worldPoint.set(cam.unproject(workerVec.set(x, y, 0f)));
					worldPoint.lerp(cam.unproject(workerVec.set(x, y, 1f)), GRAVITY_PLANE_DISTANCE);
					workerVec2.sub(worldPoint);
					workerVec.setToRandomDirection();
					workerVec.scl(5f);
					workerVec.add(workerVec2);
					workerVec.scl(-.2f);
					tempMoon.updateVelocity(workerVec);
					moons.add(tempMoon);
				}
				assignMoons(false);
			}
			return false;
		}
		@Override public boolean keyDown(int keycode)
		{
			switch (keycode)
			{
				case Input.Keys.SPACE:
					speedCheck=true;
					return true;
				case Input.Keys.E:
					int tmp=edgeMode.ordinal();
					++tmp;
					if(tmp>=edgeMode.values().length)
						tmp=0;
					edgeMode=edgeMode.values()[tmp];
					return true;
				case Input.Keys.S:
					screenMode=ScreenMode.SPAWN;
					return true;
				case Input.Keys.C:
					blackScreen=!blackScreen;
					setScreenColor(blackScreen);
					return true;
			}
			return false;
		}
		@Override public boolean keyUp(int keycode)
		{
			switch (keycode)
			{
				case Input.Keys.SPACE:
					speedCheck=false;
					return true;
				case Input.Keys.S:
					screenMode=ScreenMode.GRAVITY;
					return true;
			}
			return false;
		}
	}


	class Moon extends ModelInstance implements Pool.Poolable
	{
		Vector3 velocity;
		Vector3 nextPosition;
		ClipPlane clipPlane;
		Moon(Model template)
		{
			super(template);
			velocity=new Vector3();
			nextPosition=new Vector3();
		}
		void updateVelocity(Vector3 change)
		{
			velocity.add(change);
		}

		public Vector3 getNextPosition() {
			return nextPosition;
		}

		void move() {
			this.transform.translate(velocity);
		}
		void scaleVelocity(float scalar){velocity.scl(scalar);}
		void updateNextPosition()
		{
			this.transform.getTranslation(nextPosition);
			nextPosition.add(velocity);
		}
		public void reset()
		{
			velocity.set(0,0,0);
		}
		public boolean willBeOffscreen()
		{
			updateNextPosition();
			return !cam.frustum.pointInFrustum(nextPosition);
		}
		private void findClipPlane()
		{
			float distance=Float.MAX_VALUE;
			float testDistance;
			updateNextPosition();
			for(int x=0;x<6;++x)
			{
				testDistance=cam.frustum.planes[x].distance(nextPosition);
				if(testDistance<distance)
				{
					clipPlane=ClipPlane.values()[x];
					distance=testDistance;
				}
			}
		}
		public void warp()
		{
			findClipPlane();
			moonsToReposition.add(this);
			switch (clipPlane)
			{
				case NEAR:
					workerVec.set(cam.project(this.transform.getTranslation(workerVec2)));
					workerVec.set(nextPosition.x,nextPosition.y,1f);
					nextPosition.set(cam.unproject(workerVec));
					break;
				case FAR:
					nextPosition.set(cam.project(this.transform.getTranslation(nextPosition)));
					workerVec.set(nextPosition.x, nextPosition.y, 0f);
					workerVec.set(cam.unproject(workerVec));
					workerVec2.set(nextPosition.x,nextPosition.y,1f);
					workerVec2.set(cam.unproject(workerVec2));
					workerVec.lerp(workerVec2,.02f);
					nextPosition.set(workerVec);
					break;
				case LEFT:
					workerVec2.set(cam.direction);
					workerVec2.rotate(cam.up, -90);
					warpRay.set(this.transform.getTranslation(workerVec), workerVec2);
					Intersector.intersectRayPlane(warpRay,cam.frustum.planes[3],nextPosition);
					break;
				case RIGHT:
					workerVec2.set(cam.direction);
					workerVec2.rotate(cam.up, 90);
					warpRay.set(this.transform.getTranslation(workerVec), workerVec2);
					Intersector.intersectRayPlane(warpRay, cam.frustum.planes[2], nextPosition);
					break;
				case TOP:
					workerVec2.set(cam.up);
					workerVec2.rotate(cam.direction, 180);
					warpRay.set(this.transform.getTranslation(workerVec), workerVec2);
					Intersector.intersectRayPlane(warpRay, cam.frustum.planes[5], nextPosition);
					break;
				case BOTTOM:
					warpRay.set(this.transform.getTranslation(workerVec),cam.up);
					Intersector.intersectRayPlane(warpRay, cam.frustum.planes[4], nextPosition);
			}
		}
		public void reflect()
		{

			float length;
			//findClipPlane();
			updateNextPosition();
			float x=nextPosition.x;
			float y=nextPosition.y;
			float z=nextPosition.z;
			workerVec.set(x, 0, 0);
			if(!cam.frustum.pointInFrustum(workerVec))
				x=-1;
			else
				x=1;
			workerVec.set(0,y,0);
			if(!cam.frustum.pointInFrustum(workerVec))
				y=-1;
			else
				y=1;
			workerVec.set(0,0,z);
			if(!cam.frustum.pointInFrustum(workerVec))
				z=-1;
			else
				z=1;

			velocity.x*=x;
			velocity.y*=y;
			velocity.z*=z;



//			switch (clipPlane)
//			{
//				case NEAR:
//					workerVec.set(cam.direction);
//					break;
//				case FAR:
//					workerVec.set(cam.direction);
//					workerVec.scl(-1);
//					break;
//				case LEFT:
//					workerVec.set(cam.direction);
//					workerVec.rotate(cam.up, -90);
//					break;
//				case RIGHT:
//					workerVec.set(cam.direction);
//					workerVec.rotate(cam.up, 90);
//					break;
//				case TOP:
//					workerVec.set(cam.up);
//					workerVec.scl(-1);
//					break;
//				case BOTTOM:
//					workerVec.set(cam.up);
//					break;
//			}
//			length=velocity.len();
//			velocity.nor();
//			velocity.crs(workerVec);
//			velocity.setLength(length);


//			length=velocity.len();
//			workerVec.set(cam.frustum.planes[clipPlane.ordinal()].normal);
//			workerVec.scl(length);
//			velocity.crs(workerVec);
//			//velocity.setLength(length);
		}
	}

	class Gravity implements Pool.Poolable
	{
		Vector3 position;
		float magnitude;
		boolean quadratic;
		PointLight light;

		Gravity()
		{
			this(new Vector3(0f,0f,0f),1,false);
		}

		Gravity(Vector3 position)
		{
			this(position,1,false);
		}

		Gravity(Vector3 position,float magnitude)
		{
			this(position, magnitude,false);
		}
		Gravity(float magnitude)
		{
			this(new Vector3(0f, 0f, 0f), magnitude);
		}

		Gravity(Vector3 position,float magnitude, boolean quadratic)
		{
			this.position=new Vector3(position);
			this.magnitude=magnitude;
			this.quadratic=quadratic;
			light=new PointLight();
			light.set(0f, 0f, 1f, position, LIGHT_INTENSITIY);
		}

		public void setQuadratic(boolean mode)
		{
			quadratic=mode;
		}

		public void setMagnitude(float magnitude) {
			this.magnitude = magnitude;
		}

		public void setPosition(Vector3 position) {
			this.position = position;
			light.setPosition(position);
		}

		@Override public void reset()
		{
			environment.remove(light);
		}

		public void spawn(Vector3 position)
		{
			environment.add(light);
			this.setPosition(position);
		}

		public Vector3 getAccel(Vector3 satPos)
		{
			Vector3 output;
			output=satPos.sub(position);
			output.nor();
			if(quadratic)
			{
				output.scl(-magnitude*(1/position.dst2(satPos)));
			}
			else
			{
				output.scl(-magnitude);
			}
			return output;
		}


	}

	class GravityHandler extends Thread
	{
		int lowBound, highBound;

		GravityHandler()
		{
			this(-1,-1);
		}

		GravityHandler(int lowBound,int highBound)
		{
			this.lowBound=lowBound;
			this.highBound=highBound;
		}

		public void setBounds(int low, int high)
		{
			lowBound=low;
			highBound=high;
		}

		@Override public void run()
		{
			int gravityNumber,moonNumber;
			Vector3 position=new Vector3();
			while(!Thread.interrupted())
			{
				if(highBound==-1)
					highBound=moons.size();
				if(lowBound==-1)
					lowBound=0;
				moonNumber=lowBound;
				while(moonNumber<highBound&&moons.size()>0)
				{
					for(gravityNumber=0;gravityNumber<gravities.size();++gravityNumber)
					{
						moons.elementAt(moonNumber).transform.getTranslation(position);
						moons.elementAt(moonNumber).updateVelocity(gravities.elementAt(gravityNumber).getAccel(position));
					}
					++moonNumber;
				}
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
	}
}