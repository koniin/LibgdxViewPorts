package com.bloodcake.viewporttests;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Created by henke on 3/5/2016.
 */
public class TwoCamTest implements Screen {
    final float VIRTUAL_HEIGHT = 10f; // +++ The virtual height is 10 meters

    ResolutionFileResolver fileResolver;
    OrthographicCamera cam;
    SpriteBatch batch;
    Texture texture;
    Stage uiStage;
    ScreenViewport uiViewport;
    float y;
    float gravity = -9.81f; // earth gravity is +/- 9.81 m/s^2 downwards
    float velocity;
    float jumpHeight = 1f; // jump 1 meter every time
    private Skin skin;
    private TextButton button;


    public TwoCamTest() {
        create();
    }

    public void create () {
        fileResolver = new ResolutionFileResolver(new InternalFileHandleResolver(),
                new ResolutionFileResolver.Resolution(480, 800, "480"), // portraitWidth goes first
                new ResolutionFileResolver.Resolution(720, 1280, "720"), // portraitWidth goes first
                new ResolutionFileResolver.Resolution(1000, 1820, "1080") // portraitWidth goes first
                );

        batch = new SpriteBatch();
        texture = new Texture(fileResolver.resolve("sprites/text.png"));
        cam = new OrthographicCamera();
        uiViewport = new ScreenViewport();
        uiStage = new Stage(uiViewport, batch);

        skin = new Skin(fileResolver.resolve("data/uiskin.json"));

        button = new TextButton("Fire", skin, "default");
        button.setWidth(50f);
        button.setHeight(30f);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        table.right().bottom();
        uiStage.addActor(table);

        table.padRight(10).padBottom(10);

        table.add(button);

        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) y += jumpHeight;

        velocity += gravity * delta;
        y += velocity * delta;
        if (y <= 0) y = velocity = 0;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(texture, 0, y, 1.8f, 1.8f);
        batch.draw(texture, cam.viewportWidth / 2, cam.viewportHeight / 2, 1.8f, 1.8f);
        batch.end();

        uiStage.getViewport().apply(true);
        uiStage.draw();
    }

    public void resize (int width, int height) {
        cam.setToOrtho(false, VIRTUAL_HEIGHT * width / (float)height, VIRTUAL_HEIGHT);
        batch.setProjectionMatrix(cam.combined);
        uiViewport.update(width, height);
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
    public void dispose () {
        texture.dispose();
        batch.dispose();
    }
}
