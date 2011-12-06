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
	static Shape torsoS, armRS, armLS, legRS, legLS, armR2S, armL2S, legR2S, legL2S, headS;
	static Node torsoTG, torsoN, armTG, legTG, leftArmGroup, rightArmGroup, leftLegGroup, headTG,
	rightLegGroup, rightArmN, leftArmN, rightLegN, leftLegN, rightArm2N, leftArm2N,
	rightLeg2N, leftLeg2N, headN, sun;
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
		Camera.setCenterOfProjection(new Vector3f(5, 5, 15));
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
			diffuse.load("../jrtr/shaders/diffuse.vert", "../jrtr/shaders/diffuse.frag");
		} catch (Exception e) {
			System.out.println("Problem loading shader");
		}

		float pi = (float) Math.PI;

		Matrix4f id = new Matrix4f();
		id.setIdentity();
		Matrix4f rot45 = new Matrix4f();
		Matrix4f rot_45 = new Matrix4f();
		Matrix4f rot20 = new Matrix4f();
		Matrix4f rot_20 = new Matrix4f();
		rot45.rotZ(pi / 4);
		rot_45.rotZ(pi / -4);
		rot20.rotZ(pi / 9);
		rot_20.rotZ(pi / -9);
		Matrix4f bodyT = new Matrix4f(2, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f rightArmT = new Matrix4f(1, 0, 0, 2.5f, 0, 1, 0, 2, 0, 0, 1, 0, 0, 0, 0, 1);
		rightArmT.mul(rot45);
		Matrix4f rightArm2T = new Matrix4f(1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f leftArmT = new Matrix4f(1, 0, 0, -2.5f, 0, 1, 0, 2, 0, 0, 1, 0, 0, 0, 0, 1);
		leftArmT.mul(rot_45);
		Matrix4f leftArm2T = new Matrix4f(1, 0, 0, -3, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f rightLegT = new Matrix4f(1, 0, 0, 2, 0, 1, 0, -3.5f, 0, 0, 1, 0, 0, 0, 0, 1);
		rightLegT.mul(rot20);
		Matrix4f rightLeg2T = new Matrix4f(1, 0, 0, 2.5f, 0, 1, 0, -5.5f, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f leftLegT = new Matrix4f(1, 0, 0, -2, 0, 1, 0, -3.5f, 0, 0, 1, 0, 0, 0, 0, 1);
		leftLegT.mul(rot_20);
		Matrix4f leftLeg2T = new Matrix4f(1, 0, 0, -2.5f, 0, 1, 0, -5.5f, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f headT = new Matrix4f(2, 0, 0, 0, 0, 2, 0, 4, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f a = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0.01f, 0, 0, 0, 1);

		torsoS = mesh.makeCube();
		armRS = mesh.makeZylinder(20);
		armR2S = mesh.makeZylinder(20);
		armLS = mesh.makeZylinder(20);
		armL2S = mesh.makeZylinder(20);
		legRS = mesh.makeZylinder(20);
		legR2S = mesh.makeZylinder(20);
		legLS = mesh.makeZylinder(20);
		legL2S = mesh.makeZylinder(20);
		headS = mesh.makeBall(99);

		torsoTG = new TransformGroup();
		torsoTG.setTransformationMat(id);
		armTG = new TransformGroup();
		armTG.setTransformationMat(id);
		legTG = new TransformGroup();
		legTG.setTransformationMat(id);
		headTG = new TransformGroup();
		headTG.setTransformationMat(a);

		Material mat = new Material();
		mat.setMatColor(new Vector3f(0.8f, 0.8f, 0.8f));
		mat.setShader(diffuse);

		torsoN = new ShapeNode();
		rightArmN = new ShapeNode();
		rightArm2N = new ShapeNode();
		leftArmN = new ShapeNode();
		leftArm2N = new ShapeNode();
		rightLegN = new ShapeNode();
		rightLeg2N = new ShapeNode();
		leftLegN = new ShapeNode();
		leftLeg2N = new ShapeNode();
		headN = new ShapeNode();

		torsoS.setMaterial(mat);
		armRS.setMaterial(mat);
		armR2S.setMaterial(mat);
		armLS.setMaterial(mat);
		armL2S.setMaterial(mat);
		legRS.setMaterial(mat);
		legR2S.setMaterial(mat);
		legLS.setMaterial(mat);
		legL2S.setMaterial(mat);
		headS.setMaterial(mat);

		torsoN.setShape(torsoS);
		rightArmN.setShape(armRS);
		rightArm2N.setShape(armR2S);
		leftArmN.setShape(armLS);
		leftArm2N.setShape(armL2S);
		rightLegN.setShape(legRS);
		rightLeg2N.setShape(legR2S);
		leftLegN.setShape(legLS);
		leftLeg2N.setShape(legL2S);
		headN.setShape(headS);

		torsoN.setTransformationMat(bodyT);
		rightArmN.setTransformationMat(rightArmT);
		rightArm2N.setTransformationMat(rightArm2T);
		leftArmN.setTransformationMat(leftArmT);
		leftArm2N.setTransformationMat(leftArm2T);
		rightLegN.setTransformationMat(rightLegT);
		rightLeg2N.setTransformationMat(rightLeg2T);
		leftLegN.setTransformationMat(leftLegT);
		leftLeg2N.setTransformationMat(leftLeg2T);
		headN.setTransformationMat(headT);

		torsoTG.addChild(torsoN);
		torsoTG.addChild(headTG);
		torsoTG.addChild(armTG);
		torsoTG.addChild(legTG);
		headTG.addChild(headN);
		armTG.addChild(rightArmN);
		armTG.addChild(rightArm2N);
		armTG.addChild(leftArmN);
		armTG.addChild(leftArm2N);
		legTG.addChild(rightLegN);
		legTG.addChild(rightLeg2N);
		legTG.addChild(leftLegN);
		legTG.addChild(leftLeg2N);

		Light l = new Light();
		l.setPosition(new Vector3f(0, 0, 10));
		l.setRadiance(new Vector3f(1, 1, 1));
		sun = new LightNode();
		sun.setLight(l);
		sun.setTransformationMat(id);
		torsoTG.addChild(sun);

		sceneManager.setRoot(torsoTG);
	}
}
