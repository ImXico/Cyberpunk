package example.background;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.Set;

public class Background {

    private static final float TREE_GAP = 125f;
    private static final float TREE_Y = 75f;

    private Set<Tree> trees;

    public Background(int numTrees) {
        trees = new HashSet<Tree>();
        final String atlasKey = "pack";
        String regionName;
        /*
        On a real project, you would just recycle the textures as the faded out of camera sight.
        Here we make alot of trees to showcase the camera styles.
         */
        for (int i = 0; i < numTrees; i++) {
            regionName = (i % 2 == 0) ? "tree1" : "tree2";
            trees.add(new Tree(new Vector2(TREE_GAP * i, TREE_Y), regionName, atlasKey));
        }
    }

    public void render(Batch batch) {
        for (Tree tree : trees) {
            tree.render(batch);
        }
    }
}
