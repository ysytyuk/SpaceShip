package ua.lviv.kreatech;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Vector2 position;
    private float speed;
    private Texture texture;
    private int width = Gdx.graphics.getWidth();
    private int height = Gdx.graphics.getHeight();
    private float deltatime = 5.0f * Gdx.graphics.getDeltaTime();
    private int firetimer;
    private final int FIRE_RATE = 4;
    private int life;
    private Bullet[] bullet;
    private Sound blaster;


    public Hero(Bullet[] bullet){
        this.bullet = bullet;
        this.position = new Vector2(10.0f, (height / 2) - 64);
        this.speed = 5.0f + deltatime;
        this.firetimer = 0;
        this.life = 10;
        this.blaster = Gdx.audio.newSound(Gdx.files.internal("sounds/blaster.mp3"));
        if(this.texture == null) {
            this.texture = new Texture("ship.tga");
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
    }

    public void getDamage(int x){
        this.life -= x;
    }


    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += speed;
            if (position.y > (height - (texture.getHeight() / 2))) {
                position.y = height - (texture.getHeight() / 2);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= speed;
            if (position.y < -(texture.getHeight() / 2)) {
                position.y = -(texture.getHeight() / 2);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= speed;
            if (position.x < 10) {
                position.x = 10;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += speed;
            if (position.x > (width - 138)) {
                position.x = (width - 138);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched(1)){
            firetimer++;
            if(firetimer > FIRE_RATE){
                firetimer = 0;
                for (int i = 0; i < bullet.length; i++){
                    if (life > 0) {
                        if (!bullet[i].isActive()) {
                            bullet[i].setup(position.x + 128, position.y + 60);
                            blaster.play(1.0f);
                            break;
                        }
                    }
                }
            }
        }

        if(Gdx.input.isTouched(0)){
            position.x = (Gdx.input.getX(0) - (texture.getWidth() / 2));
            position.y = ((Gdx.graphics.getHeight() - Gdx.input.getY(0)) - (texture.getHeight() / 2));
            if (position.x > width - texture.getWidth()){
                position.x = width - texture.getWidth();
            }else if (position.x < 10){
                position.x = 10;
            }else if (position.y > height){
                position.y = height;
            }else if (position.y < -(texture.getHeight() / 2)){
                position.y = -(texture.getHeight() / 2);
            }
        }


    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Rectangle getRect(){
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public Texture getTexture() {
        return texture;
    }


    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose(){
        texture.dispose();
        blaster.dispose();
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }
}
