package ua.lviv.kreatech;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Background {

    class Stars{

        private Vector2 position;
        private float speed;
        private int size;
        private int width = Gdx.graphics.getWidth();
        private int height = Gdx.graphics.getHeight();
        private float deltatime = Gdx.graphics.getDeltaTime();


        public Stars(){
            this.position = new Vector2((float)(Math.random()* width), (float) (Math.random() * height));
            this.speed = (0.5f + (float)(Math.random() * 5.0f));
            this.size = (int)(3 * this.speed);
        }

        public void update(){
            position.x -= speed;
            if(position.x < -50)
                position.x = width + 70;
        }

    }

    private Texture texture;
    private Texture texstars;
    private Stars[] stars;
    private final int STARSCOUNT = 200;

    public Background(){
        texture = new Texture("bg.png");
        texstars = new Texture("star12.tga");
        stars = new Stars[STARSCOUNT];
        for (int i = 0; i < STARSCOUNT; i++){
            stars[i] = new Stars();
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, 0, 0);
        for (int i = 0; i < STARSCOUNT; i++){
            batch.draw(texstars, stars[i].position.x, stars[i].position.y, stars[i].size, stars[i].size);
        }
    }

    public void update(){
        for(int i = 0; i < STARSCOUNT; i++){
            stars[i].update();
        }
    }

    public void dispose(){
        texture.dispose();
        texstars.dispose();
    }
}
