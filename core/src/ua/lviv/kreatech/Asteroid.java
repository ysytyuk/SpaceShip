package ua.lviv.kreatech;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {
    private Vector2 position;
    private float speed = 1.0f;
    private float correctspeed;
    private Texture texture;
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();
    private float deltatime = speed * Gdx.graphics.getDeltaTime();
    private float angle;
    private int minustexture = 50;


    public Asteroid(){

        this.position = new Vector2(width + (float)(Math.random() * 1000), (float)(Math.random() * (height - minustexture)));
        this.correctspeed = (speed + (float)(Math.random()* 10.0f)) + deltatime;
        this.angle = (float)(Math.random() * 360);
        if (this.texture == null) {
            this.texture = new Texture("asteroid.tga");
        }

    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y, 33, 30, 66, 60, 1, 1, angle, 0, 0, 66, 60, false, false);
    }

    public void update(){
        angle += 5.0f - correctspeed;
        position.x -= correctspeed;
        if(position.x < -80){
            position.x = width + (float)(Math.random() * 1000);
            position.y = (float)(Math.random() * (height - minustexture));
        }

    }

    public void recreate(){
        this.position = new Vector2(width + (float)(Math.random() * 1000), (float)(Math.random() * (height - minustexture)));
        this.correctspeed = (speed + (float)(Math.random()* 10.0f)) + deltatime;
    }

    public void setSpeed(float speed) {
        this.speed += speed;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRect(){
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void dispose(){
        texture.dispose();
    }


}
