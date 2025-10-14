package coconuts;

import javafx.scene.image.Image;

// Represents the falling object that can kill crabs. If hit by a laser, the coconut disappears
// This is a domain class; other than Image, do not introduce JavaFX or other GUI components here
public class Coconut extends HittableIslandObject {
    private static final int WIDTH = 50;
    private static final Image coconutImage = new Image("file:images/coco-1.png");

    public Coconut(OhCoconutsGameManager game, int x) {
        super(game, x, 0, WIDTH, coconutImage);
    }

    @Override
    public void step() {
        y += 5;
    }

    // TODO
    /**
     * Create code to find when a coconut hits the ground and make it disappear.
     * Create code to find when a coconut is hit by a laser.
     * Have it update its observers when a coconut hits the ground or is hit by a laser.
     * Use HitEvent?
     */
}
