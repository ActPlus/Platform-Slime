package sk.actplus.platformjelly;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;

import sun.java2d.ScreenUpdateManager;

import static sk.actplus.platformjelly.Game.CLIENT_HEIGHT;
import static sk.actplus.platformjelly.Game.CLIENT_WIDTH;
import static sk.actplus.platformjelly.Game.PPM;

/**
 * Created by Ja on 7.2.2018.
 */

public class SimulationScreen implements Screen, InputProcessor
{
    SpriteBatch batch;
    Texture img = new Texture("badlogic.jpg");
    Sprite sprite = new Sprite(img);
    Game game;
    String vertexShader;
    String fragmentShader;
    ShaderProgram shaderProgram;

    public static final String VERT_SHADER =
            "attribute vec4 a_position;\n" +
                    "attribute vec4 a_color;\n" +
                    "attribute vec2 a_texCoord0;\n" +
                    "uniform mat4 u_projTrans;\n" +
                    "varying vec4 vColor;\n" +
                    "varying vec2 v_texCoords;\n"+
                    "void main() {\n" +
                    "	vColor = a_color;\n" +
                    "	 v_texCoords = a_texCoord0;\n" +
                    "    gl_Position = u_projTrans * a_position;\n" +
                    "}";

    public static final String FRAG_SHADER =
            "#ifdef GL_ES\n" +
                    "    precision mediump float;\n" +
                    "#endif\n" +
                    "\n" +
                    "varying vec4 v_color;\n" +
                    "varying vec2 v_texCoords;\n" +
                    "uniform sampler2D u_texture;\n" +
                    "uniform mat4 u_projTrans;\n" +
                    "\n" +
                    "void main() {\n" +
                    "        vec3 color = texture2D(u_texture, v_texCoords).rgb;\n" +
                    "        float gray = (color.r + color.g + color.b) / 3.0;\n" +
                    "        vec3 grayscale = vec3(gray);\n" +
                    "\n" +
                    "        gl_FragColor = vec4(grayscale, 1.0);\n" +
                    "}";


    public SimulationScreen(SpriteBatch batch) {
        this.batch = batch;
        Gdx.input.setInputProcessor(this);

        vertexShader = VERT_SHADER;
        fragmentShader = FRAG_SHADER;
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        if (!shaderProgram.isCompiled()) throw new GdxRuntimeException("Couldn't compile shader: " + shaderProgram.getLog());

        game = new Game();
    }

    @Override
    public void show() {

    }
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    @Override
    public void render(float delta) {

        batch.begin();
        batch.draw(img, 0, 0);
        batch.setShader(shaderProgram);
        batch.draw(sprite,sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        batch.end();
        game.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println(character);
        if(character == 'r') {
            game.camera.goTo(0,0);
        }
        game.camera.update();

        return false;
    }

    Vector2 centerPoint = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

    public static class Button{
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int SCROLL_WHEEL = 2;

}
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Button.LEFT){
            game.camera.checkTouchDown(new Vector2((screenX - (CLIENT_WIDTH / 2))/20,((CLIENT_HEIGHT/2) - screenY)/20));
            System.out.println("Screen Position: x: " + screenX + " , y: " + screenY);
            System.out.println("World Position: x: " + screenX + " , y: " + screenY);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button == Button.LEFT) {
            game.camera.checkTouchUp();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        game.camera.checkMouseMoved(new Vector2(screenX,screenY));

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        game.camera.checkScrolled(amount);
        System.out.println("scrolling");
        return false;
    }
}
