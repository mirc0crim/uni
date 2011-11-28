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
	static Shape torsoS, armRS, armLS, legRS, legLS, headS;
	static Node torsoTG, torsoN, armTG, legTG, leftArmGroup, rightArmGroup, leftLegGroup,
	headTG, rightLegGroup, rightArmN, leftArmN, rightLegN, headN, leftLegN, sun;
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
		Matrix4f headT = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 3, 0, 0, 1, 0, 0, 0, 0, 1);
		Matrix4f right = new Matrix4f(1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);

		torsoS = mesh.makeCube();
		armRS = mesh.makeZylinder(20);
		armLS = mesh.makeZylinder(20);
		legRS = mesh.makeZylinder(20);
		legLS = mesh.makeZylinder(20);
		headS = mesh.makeBall(99);

		torsoTG = new TransformGroup();
		torsoTG.setTransformationMat(id);
		armTG = new TransformGroup();
		armTG.setTransformationMat(id);
		legTG = new TransformGroup();
		legTG.setTransformationMat(id);
		headTG = new TransformGroup();
		headTG.setTransformationMat(id);

		Material mat = new Material();
		mat.setShader(diffuse);

		torsoN = new ShapeNode();
		rightArmN = new ShapeNode();
		leftArmN = new ShapeNode();
		rightLegN = new ShapeNode();
		leftLegN = new ShapeNode();
		headN = new ShapeNode();

		torsoS.setMaterial(mat);
		armRS.setMaterial(mat);
		armLS.setMaterial(mat);
		legRS.setMaterial(mat);
		legLS.setMaterial(mat);
		headS.setMaterial(mat);

		torsoN.setShape(torsoS);
		rightArmN.setShape(armRS);
		leftArmN.setShape(armLS);
		rightLegN.setShape(legRS);
		leftLegN.setShape(legLS);
		headN.setShape(headS);

		torsoN.setTransformationMat(bodyT);
		rightArmN.setTransformationMat(rightArmT);
		leftArmN.setTransformationMat(leftArmT);
		rightLegN.setTransformationMat(rightlegT);
		leftLegN.setTransformationMat(leftLegT);
		headN.setTransformationMat(headT);

		torsoTG.addChild(torsoN);
		torsoTG.addChild(headTG);
		torsoTG.addChild(armTG);
		torsoTG.addChild(legTG);
		headTG.addChild(headN);
		armTG.addChild(rightArmN);
		armTG.addChild(leftArmN);
		legTG.addChild(rightLegN);
		legTG.addChild(leftLegN);


		Light l = new Light();
		l.setPosition(new Vector3f(0, 0, 10));
		sun = new LightNode();
		sun.setLight(l);
		sun.setTransformationMat(id);
		torsoTG.addChild(sun);

		sceneManager.setRoot(torsoTG);
	}
}
