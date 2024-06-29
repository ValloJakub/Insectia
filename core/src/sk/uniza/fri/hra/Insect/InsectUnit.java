package sk.uniza.fri.hra.Insect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.Batch;
import sk.uniza.fri.hra.HealthBar;

public abstract class InsectUnit {
    private Texture texture;
    private Rectangle unitHitbox;

    protected HealthBar healthBar;
    private int x;
    private int y;
    private int healthPoints;

    private int resourceCost;

    private int range;
    private float speed;
    private int damage;

    public InsectUnit(Texture texture, int x, int y, int healthPoints, int resourceCost, float speed, int damage, int range) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.healthPoints = healthPoints;
        this.resourceCost = resourceCost;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.unitHitbox = new Rectangle(this.x, this.y, this.texture.getWidth(), this.texture.getHeight());
        this.healthBar = new HealthBar(this.healthPoints, this.unitHitbox.getWidth());
    }

    public void draw(Batch batch) {
        batch.draw(this.texture, this.x, this.y);
    }

    public void dispose() {
        this.texture.dispose();
    }

    public Rectangle getUnitHitbox() {
        return this.unitHitbox;
    }

    public void move(float xValue, float yValue) {
        this.x += xValue;
        this.y += yValue;
        this.unitHitbox.setPosition(this.x, this.y);
    }

    public void strike(InsectUnit unit) {
        unit.takeDamage(this.getDamage());
    }

    public void takeDamage(int value) {
        this.healthPoints -= value;
        if (this.healthPoints < 0) this.healthPoints = 0;
        this.healthBar.setHealth(this.healthPoints);
    }

    public void setTexture(Texture newTexture) {
        this.texture = newTexture;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.texture.getWidth();
    }

    public int getHeight() {
        return this.texture.getHeight();
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    public int getResourceCost() {
        return resourceCost;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getAttackRange() {
        return this.range;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }
}
