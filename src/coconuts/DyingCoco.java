/**
 * Course: CSC1110
 * Fall 2024-2025
 * Assignment Name
 * Name: Jacob Rebartchek
 * Created: 10/16/2025
 */
package coconuts;

import javafx.scene.image.Image;

/**
 * enter description
 */
public class DyingCoco extends IslandObject {
    private static final Image cocofire = new Image("file:images/coco-fire.png");

    private int dyingCount;

    public DyingCoco(OhCoconutsGameManager game, int x, int y, int width) {
        super(game, x, y, width, cocofire);
        dyingCount = 20;
    }

    @Override
    public void step() {
        dyingCount--;
    }

    public int getDyingCount() {
        return dyingCount;
    }
}
