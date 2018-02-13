package sk.actplus.platformjelly;
 import com.badlogic.gdx.ApplicationListener;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.graphics.GL20;
 import com.badlogic.gdx.graphics.Mesh;
 import com.badlogic.gdx.graphics.VertexAttribute;
 import com.badlogic.gdx.graphics.VertexAttributes.Usage;

/**
 * Created by Timotej on 13.2.2018.
 */



    public class MeshTriangle implements ApplicationListener {
        private Mesh mesh;

        @Override
        public void create() {
            if (mesh == null) {
                mesh = new Mesh(true, 3, 3, new VertexAttribute(Usage.Position,3, "a_position"));

                mesh.setVertices(new float[] { -0.5f, -0.5f, 0,
                        0.5f, -0.5f, 0,
                        0, 0.5f, 0 });

                mesh.setIndices(new short[] { 0, 1, 2 });
            }
        }

        @Override
        public void dispose() { }

        @Override
        public void pause() { }

        @Override
        public void render() {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            mesh.render(shader,GL20.GL_TRIANGLES, 0, 3,0);
        }

        @Override
        public void resize(int width, int height) { }

        @Override
        public void resume() { }
    }
}
