package sk.uniza.fri.hra.Hives;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Batch;
import sk.uniza.fri.hra.HealthBar;
import sk.uniza.fri.hra.Insect.InsectUnit;

import java.util.ArrayList;
import java.util.Random;

public abstract class Hive {
    private Texture texture;
    private Rectangle hiveHitbox;
    private HealthBar healthBar;

    private int resourceCount;
    private int healthPoints;
    private int x;
    private int y;

    private String side;

    protected ArrayList<InsectUnit> listOfUnits;
    public Hive(int x, int y) {
        this.listOfUnits = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.resourceCount = 100;
        this.healthPoints = 500;
        this.hiveHitbox = new Rectangle(this.x, this.y, 160, 160);
        this.healthBar = new HealthBar(this.healthPoints, this.hiveHitbox.getWidth());
    }

    public void draw(Batch batch, ShapeRenderer shapeRenderer) {
        batch.draw(this.texture, this.x, this.y);
    }

    public void repairHive(int value) {
        this.healthPoints += value;
    }

    public void damageHive(int value) {
        this.healthPoints -= value;
    }

    public void addResources(int value) {
        this.resourceCount += value;
    }

    public void reduceResources(int value) {
        this.resourceCount -= value;
    }

    public Rectangle getHiveHitbox() {
        return this.hiveHitbox;
    }

    public int getResourceCount() {
        return this.resourceCount;
    }
    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    protected void setTexture(String texture) {
        this.texture = new Texture(texture);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSide() {
        return this.side;
    }

    public abstract void recruitCollector();

    public abstract void recruitFighter();

    public abstract void recruitStriker();

    public ArrayList<InsectUnit> getListOfUnits() {
        return this.listOfUnits;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        this.texture.dispose();
    }
}
