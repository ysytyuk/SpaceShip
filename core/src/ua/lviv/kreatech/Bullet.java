package ua.lviv.kreatech;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private float speed = 3.0f;
    private float speedcorrect;
    private Texture texture;
    private int width = Gdx.graphics.getWidth();
    private float deltatime = speed * Gdx.graphics.getDeltaTime();
    private boolean active;


    public Bullet(){

        this.position = new Vector2(0.0f, 0.0f);
        this.speedcorrect = speed + deltatime;
        this.active = false;
        if (this.texture == null) {
            this.texture = new Texture("bullet20.tga");
        }


    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y, 20, 10);
    }

    public void destroy(){
        active = false;
    }

    public void setup (float x, float y){
        position.x = x;
        position.y = y;
        active = true;
    }

    public void update(){
        position.x += speedcorrect;
        if(position.x > width){
            position.x = 0.0f;
            destroy();
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setSpeed(float speed) {
        this.speed += speed;
    }

    public void dispose(){
        texture.dispose();
    }
}
