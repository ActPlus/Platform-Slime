package sk.actplus.platformjelly;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import sun.java2d.ScreenUpdateManager;

import static sk.actplus.platformjelly.Game.PPM;

/**
 * Created by Ja on 7.2.2018.
 */

public class SimulationScreen implements Screen, InputProcessor
{
    SpriteBatch batch;
    Texture img = new Texture("badlogic.jpg");
    Game game;

    public SimulationScreen(SpriteBatch batch) {
        this.batch = batch;
        Gdx.input.setInputProcessor(this);

        game = new Game();
    }

    @Override
    public void show() {

    }
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    @Override
    public void render(float delta) {

        batch.begin();
        //batch.draw(img, 0, 0);
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
            System.out.println("Screen Position: x: " + screenX + " , y: " + screenY);
            System.out.println("World Position: x: " + screenX + " , y: " + screenY);
    }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(Gdx.input.isButtonPressed(Button.RIGHT)){
            game.camera.move((screenX-centerPoint.x)/20,(centerPoint.y-screenY)/20);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        PPM-=amount;
        game.camera.updatePPM();
        return false;
    }
}
