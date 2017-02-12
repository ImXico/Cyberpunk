package example.source.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import source.ImageManager.ImageManager;

public class Tree {

    private static final float TREE_WIDTH = 32f;
    private static final float TREE_HEIGHT = 128f;

    private final Vector2 position;
    private final TextureRegion treeRegion;

    public Tree(Vector2 position, String regionName, String atlasKey) {
        this.position = position;
        treeRegion = ImageManager.take(regionName, atlasKey);
    }

    public void render(Batch batch) {
        batch.setColor(Color.WHITE);
        batch.draw(treeRegion, position.x, position.y, TREE_WIDTH, TREE_HEIGHT);
    }
}
