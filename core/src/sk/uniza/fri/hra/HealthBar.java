package sk.uniza.fri.hra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class HealthBar {
    private float bar_width;
    private static final float BAR_HEIGHT = 7;
    private int maxHealth;
    private int currentHealth;

    public HealthBar(int maxHealth, float barWidth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.bar_width = barWidth;
    }

    public void setHealth(int health) {
        this.currentHealth = health;
    }

    public void draw(ShapeRenderer shapeRenderer, float x, float y) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x, y + 7, this.bar_width, BAR_HEIGHT);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y + 7, (this.bar_width * currentHealth) / maxHealth, BAR_HEIGHT);
        shapeRenderer.end();
    }
}
