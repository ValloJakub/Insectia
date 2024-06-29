package sk.uniza.fri.hra.Resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import sk.uniza.fri.hra.Insect.InsectUnit;

import java.util.Random;

public class Resources {
    private final Texture texture;

    private Rectangle rHitBox;
    private int x;
    private int y;

    private int resourceValue;

    public Resources(int resourceValue) {
        this.texture = new Texture("Resources/flower.png");
        this.resourceValue = resourceValue;
        this.rHitBox = new Rectangle(this.x, this.y, this.texture.getWidth(), this.texture.getHeight());

        Random random = new Random();

        this.x = random.nextInt(480) + 400;
        this.y = random.nextInt(680) + 20;
    }

    public boolean wasPickedUp(InsectUnit unit) {
        return this.rHitBox.overlaps(unit.getUnitHitbox());
    }

    public int getWidth() {
        return this.texture.getWidth();
    }

    public int getHeight() {
        return this.texture.getHeight();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Batch batch) {
        batch.draw(this.texture, this.x, this.y, this.texture.getWidth(), this.texture.getHeight());
        this.rHitBox.setPosition(this.x, this.y);
    }

    public int getResourceValue() {
        return this.resourceValue;
    }

    public void dispose() {
        this.texture.dispose();
    }
}
