package ua.lviv.kreatech;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class SpaceShip extends ApplicationAdapter {
	private final int ASTEROID_COUNT = 30;
	private final int BULLET_COUNT = 21;
	private SpriteBatch batch;
	private BitmapFont fontlife;
	private BitmapFont fontscore;
	private Background bg;
	private Hero hero;
	private Asteroid[] asteroids;
	private Bullet[] bullet;
	private Texture gameover;
	private int score = 0;
	private ParticleEffect explosion;
	private Music music;
	private Sound explosionship;
	private Texture soundturn;
	//private Sound shotasteroid;





	@Override
	public void create () {
		batch = new SpriteBatch();
		bg = new Background();
		music = Gdx.audio.newMusic(Gdx.files.internal("music/spacetrip.mp3"));
		music.setVolume(0.3f);
		music.setLooping(true);
		asteroids = new Asteroid[ASTEROID_COUNT];
		for (int i = 0; i < ASTEROID_COUNT; i++){
			asteroids[i] = new Asteroid();
		}
		bullet = new Bullet[BULLET_COUNT];
		hero = new Hero(bullet);
		for (int i = 0; i < bullet.length; i++){
			bullet[i] = new Bullet();
		}
		fontlife = new BitmapFont(Gdx.files.internal("comicsans.fnt"));
		fontlife.setColor(Color.FIREBRICK);
		gameover = new Texture("gameover.png");
		fontscore = new BitmapFont(Gdx.files.internal("comicsans.fnt"));
		explosion = new ParticleEffect();
		explosionship = Gdx.audio.newSound(Gdx.files.internal("sounds/explosionship.mp3"));
		soundturn = new Texture("img/sound.png");
		//shotasteroid = Gdx.audio.newSound(Gdx.files.internal("sounds/shotasteroid.mp3"));
	}

	@Override
	public void render () {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		bg.render(batch);
		if (hero.getLife() > 0){
			hero.render(batch);
			for (int i = 0; i < ASTEROID_COUNT; i++) {
				asteroids[i].render(batch);
			}
			for (int i = 0; i < bullet.length; i++) {
				if (bullet[i].isActive()) {
					bullet[i].render(batch);
				}
			}
			fontlife.draw(batch, "LIFE: " + String.valueOf(hero.getLife()), 100, (Gdx.graphics.getHeight() - 20));
			fontscore.draw(batch, "SCORE: " + String.valueOf(score), (Gdx.graphics.getWidth() - 300), (Gdx.graphics.getHeight() - 20));
		}else if (hero.getLife() == 0){
			batch.draw(gameover, ((Gdx.graphics.getWidth() / 2) - (gameover.getWidth() / 2)), ((Gdx.graphics.getHeight()) / 2) - (gameover.getHeight()/ 2 ));
			fontscore.draw(batch, "YOUR'S SCORE: " + String.valueOf(score), ((Gdx.graphics.getWidth() / 2) - (fontscore.getSpaceWidth() / 2 + 120)), (Gdx.graphics.getHeight()/ 2) - (gameover.getHeight()));
			if (Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ENTER)){
				if(getRectGame().contains(getVector().x,getVector().y) || Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
					hero.setLife(10);
					score = 0;
					//hero.setPosition(new Vector2(10.0f, (Gdx.graphics.getHeight() / 2) - hero.getTexture().getHeight()));
				}
			}
		}
		explosion.update(Gdx.graphics.getDeltaTime());
		explosion.draw(batch);
		batch.draw(soundturn, (soundturn.getWidth() / 2), Gdx.graphics.getHeight() - soundturn.getHeight() - 10);
		if (Gdx.input.justTouched()){
			if(getSoundTurn().contains(getVector().x,getVector().y)) {
				if(!music.isPlaying()) {
					music.play();
				}
				else {
					music.pause();
				}
			}
		}
		batch.end();
	}



	public void update(){
		bg.update();
		hero.update();
		for (int i = 0; i < ASTEROID_COUNT; i++){
			asteroids[i].update();
		}
		for (int i = 0; i < bullet.length; i++){
			if (bullet[i].isActive()){
				bullet[i].update();
			}
		}
		for(int i = 0; i < ASTEROID_COUNT; i++){
			for (int j = 0; j < bullet.length; j++){
				if(asteroids[i].getRect().contains(bullet[j].getPosition())){
					if(bullet[j].isActive()) {
						if(hero.getLife() > 0) {
							asteroids[i].recreate();
							bullet[j].destroy();
							//shotasteroid.play(1.0f);
							score++;
						}
					}
				}
			}
		}
		for (int i = 0; i < ASTEROID_COUNT; i++){
			if (hero.getRect().contains(asteroids[i].getRect()))
			{
				if (hero.getLife() > 0) {
					hero.getDamage(1);
					asteroids[i].recreate();
					explosion.load(Gdx.files.internal("effects/explosion.p"), Gdx.files.internal("img"));
					explosion.setPosition(hero.getPosition().x + (hero.getTexture().getWidth() / 2), hero.getPosition().y + (hero.getTexture().getHeight() / 2));
					explosion.start();
					explosionship.play(1.0f);
					explosion.setDuration(500);
					if (explosion.isComplete()){
						explosion.dispose();
						explosionship.dispose();
					}
				}
			}
		}

		increaseScore(score);

	}

	public void pause(){
		this.score = score;
		hero.setLife(hero.getLife());
		hero.setPosition(hero.getPosition().x, hero.getPosition().y);
	}


	@Override
	public void dispose () {
		batch.dispose();
		fontscore.dispose();
		fontlife.dispose();
		gameover.dispose();
		hero.dispose();
		for (int i = 0; i < ASTEROID_COUNT; i++){
			asteroids[i].dispose();
		}
		for (int i = 0; i < BULLET_COUNT; i++){
			bullet[i].dispose();
		}
		bg.dispose();
		explosion.dispose();
		explosionship.dispose();
		soundturn.dispose();
		music.dispose();
		//shotasteroid.dispose();
	}

	public Rectangle getRectGame(){
		return new Rectangle(((Gdx.graphics.getWidth() / 2) - (gameover.getWidth() / 2)),((Gdx.graphics.getHeight()) / 2) - (gameover.getHeight()/ 2 ), gameover.getWidth(),gameover.getHeight());
	}

	public Rectangle getSoundTurn(){
		return new Rectangle((soundturn.getWidth() / 2), (Gdx.graphics.getHeight() - soundturn.getHeight() - 10), soundturn.getWidth(), soundturn.getHeight());
	}

	public Vector2 getVector(){
		return new Vector2(Gdx.input.getX(0), Gdx.graphics.getHeight() - Gdx.input.getY(0));
	}

	public void increaseScore(int score) {
		if ( score % 100 == 0 && score != 0) {
			bullet[0].setSpeed(0.2f);
			asteroids[0].setSpeed(0.1f);
		}
	}
}

