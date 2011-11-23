/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 5th Exercise
 */

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.GLRenderPanel;
import jrtr.GraphSceneManager;
import jrtr.Light;
import jrtr.LightNode;
import jrtr.Material;
import jrtr.Node;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shader;
import jrtr.Shape;
import jrtr.ShapeNode;
import jrtr.TransformGroup;

public class simple51 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static GraphSceneManager sceneManager;
	static float angle;
	static Shape torsoCube, armR, armL, legR, legL;
	static Node torsoGroup, torso, armGroup, legGroup, leftArmGroup, rightArmGroup, leftLegGroup,
	rightLegGroup, rightArm, leftArm, rightLeg, leftLeg, sun;
	static int task = 1;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			scene();

			Timer timer = new Timer();
			angle = 0.01f;
			timer.scheduleAtFixedRate(new AnimationTask(), 0, 10);
		}

	}

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			Matrix4f rotX = new Matrix4f();
			Matrix4f rotY = new Matrix4f();

			rotX.rotX(angle);
			rotY.rotY(1.5f * angle);

			renderPanel.getCanvas().repaint();
		}
	}

	public static void main(String[] args)
	{


		Camera.setCenterOfProjection(new Vector3f(5, 5, 10));
		sceneManager = new GraphSceneManager();

		renderPanel = new SimpleRenderPanel();
		JFrame jframe = new JFrame("simple");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

	public static void scene() {
		Shader diffuse = renderContext.makeShader();
		try {
			diffuse.load("../jrtr/shaders/default.vert", "../jrtr/shaders/default.frag");
		} catch (Exception e) {
			System.out.println("Problem loading shader");
		}

		Matrix4f id = new Matrix4f();
		id.setIdentity();
		Matrix4f bodyT = new Matrix4f(1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f rightArmT = new Matrix4f(1, 0, 0, 1, 0, 1, 0, 2, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f leftArmT = new Matrix4f(1, 0, 0, -1, 0, 1, 0, 2, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f rightlegT = new Matrix4f(1, 0, 0, 1, 0, 1, 0, -2, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f leftLegT = new Matrix4f(1, 0, 0, -1, 0, 1, 0, -2, 0, 0, 1, 0, 0, 0, 0, 1);

		torsoCube = simple21.makeCube();
		armR = simple21.makeZylinder(20);
		armL = simple21.makeZylinder(20);
		legR = simple21.makeZylinder(20);
		legL = simple21.makeZylinder(20);

		torsoGroup = new TransformGroup();
		torsoGroup.setTransformationMat(id);
		armGroup = new TransformGroup();
		armGroup.setTransformationMat(id);
		legGroup = new TransformGroup();
		legGroup.setTransformationMat(id);

		Material mat = new Material();
		mat.setShader(diffuse);

		torso = new ShapeNode();
		rightArm = new ShapeNode();
		leftArm = new ShapeNode();
		rightLeg = new ShapeNode();
		leftLeg = new ShapeNode();

		torsoCube.setMaterial(mat);
		armR.setMaterial(mat);
		armL.setMaterial(mat);
		legR.setMaterial(mat);
		legL.setMaterial(mat);

		torso.setShape(torsoCube);
		rightArm.setShape(armR);
		leftArm.setShape(armL);
		rightLeg.setShape(legR);
		leftLeg.setShape(legL);

		torso.setTransformationMat(bodyT);
		rightArm.setTransformationMat(rightArmT);
		leftArm.setTransformationMat(leftArmT);
		rightLeg.setTransformationMat(rightlegT);
		leftLeg.setTransformationMat(leftLegT);

		torsoGroup.addChild(torso);
		torsoGroup.addChild(armGroup);
		armGroup.addChild(rightArm);
		armGroup.addChild(leftArm);
		torsoGroup.addChild(legGroup);
		legGroup.addChild(rightLeg);
		legGroup.addChild(leftLeg);


		Light l = new Light();
		l.setPosition(new Vector3f(0, 0, 10));
		sun = new LightNode();
		sun.setLight(l);
		sun.setTransformationMat(id);
		torsoGroup.addChild(sun);

		sceneManager.setRoot(torsoGroup);
	}
}
