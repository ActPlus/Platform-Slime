package sk.actplus.platformjelly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;

import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by Ja on 7.2.2018.
 */


public class Game {
    public static final Vector2 GRAVITY = new Vector2(0,-10);
    public static int PPM = 64;
    public static final int CLIENT_WIDTH = Gdx.graphics.getWidth();
    public static final int CLIENT_HEIGHT = Gdx.graphics.getHeight();

    //Position attribute - (x, y)
    public static final int POSITION_COMPONENTS = 2;
    //Color attribute - (r, g, b, a)
    public static final int COLOR_COMPONENTS = 4;
    //Total number of components for all attributes
    public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;
    //The maximum number of triangles our mesh will hold
    public static final int MAX_TRIS = 1;
    //The maximum number of vertices our mesh will hold
    public static final int MAX_VERTS = MAX_TRIS * 3;
    private float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];

    //The index position
    private int idx = 0;
    /**
     * shader
     */
    public static final String VERT_SHADER =
            "attribute vec2 a_position;\n" +
                    "attribute vec4 a_color;\n" +
                    "uniform mat4 u_projTrans;\n" +
                    "varying vec4 vColor;\n" +
                    "void main() {\n" +
                    "	vColor = a_color;\n" +
                    "	gl_Position =  u_projTrans * vec4(a_position.xy, 0.0, 1.0);\n" +
                    "}";

    public static final String FRAG_SHADER =
            "#ifdef GL_ES\n" +
                    "precision mediump float;\n" +
                    "#endif\n" +
                    "varying vec4 vColor;\n" +
                    "void main() {\n" +
                    "	gl_FragColor = vColor;\n" +
                    "}";

    protected static ShaderProgram createMeshShader() {
        ShaderProgram.pedantic = false;
        ShaderProgram shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
        String log = shader.getLog();
        if (!shader.isCompiled())
            throw new GdxRuntimeException(log);
        if (log!=null && log.length()!=0)
            System.out.println("Shader Log: "+log);
        return shader;
    }

    Mesh mesh;
    World world;
    Box2DDebugRenderer bddr;
    public MovableCamera camera;
    RayHandler rayHandler;

    public Game() {
        world = new World(GRAVITY,false);
        bddr = new Box2DDebugRenderer();
        camera = new MovableCamera(0,0,CLIENT_WIDTH,CLIENT_HEIGHT);


        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0,0);
        vertices[1] = new Vector2(2,3);
        vertices[2] = new Vector2(4,1);
        shape.set(vertices);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        rayHandler = new RayHandler(world);
        rayHandler.setShadows(false);
        new PointLight(rayHandler, 5, new Color(0.1f,1,1,0.8f), 100, 0, 0);
    }

    public void render(float delta) {
        update(delta);
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();
        bddr.render(world,camera.combined);
    }


    public void update(float delta){
        camera.update();
    }
}
