package com.bloodcake.viewporttests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by henke on 3/5/2016.
 */
public class ViewPortTestScreen  implements Screen {
    public Stage stage;
    private Skin skin;
    private Viewport screenViewport;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera gameCamera;
    private FitViewport gameViewPort;
    private ExtendViewport extendViewPort;
    private Texture image;

    TextButton button;

    final static int VIRTUAL_WIDTH = 800;
    final static int VIRTUAL_HEIGHT = 480;

    public ViewPortTestScreen() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        screenViewport = new ScreenViewport();
        extendViewPort = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        stage = new Stage(extendViewPort, spriteBatch);


        // Fit viewport
        gameCamera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        gameViewPort = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, gameCamera);
        //gameViewPort = new FitViewport(StarDestroyer.VIRTUAL_WIDTH, StarDestroyer.VIRTUAL_HEIGHT);

        image = new Texture("badlogic.jpg");

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        button = new TextButton("Fire", skin, "default");
        button.setWidth(50f);
        button.setHeight(30f);
        //button.setPosition(100, 100, Align.bottomRight);

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
        stage.addActor(table);

        table.padRight(10).padBottom(10);

        table.add(button);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getViewport().apply(true);
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(120 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);
        shapeRenderer.rect(0, 0, stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        shapeRenderer.end();

        gameViewPort.apply(true);
        spriteBatch.setProjectionMatrix(gameViewPort.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(image, 100, 100);
        spriteBatch.end();

        shapeRenderer.setProjectionMatrix(gameViewPort.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(250 / 255.0f, 250 / 255.0f, 120 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 100, 100);
        shapeRenderer.end();

        stage.getViewport().apply(true);
        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height, false);
        stage.getViewport().update(width, height, true);

        System.out.println("widthXheight: " + width + "x" + height);
        System.out.println("gameViewportWorld: " + gameViewPort.getWorldWidth() + "x" + gameViewPort.getWorldHeight());
        System.out.println("gameViewportScreen: " + gameViewPort.getScreenWidth() + "x" + gameViewPort.getScreenHeight());
        System.out.println("stageViewportWorld: " + stage.getViewport().getWorldWidth() + "x" + stage.getViewport().getWorldHeight());
        System.out.println("stageViewportScreen: " + stage.getViewport().getScreenWidth() + "x" + stage.getViewport().getScreenHeight());

        // Updating UI position
        button.setPosition(stage.getViewport().getWorldWidth()  - 105, 10);
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
        stage.dispose();
    }
}
