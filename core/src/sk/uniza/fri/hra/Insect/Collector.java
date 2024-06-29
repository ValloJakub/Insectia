package sk.uniza.fri.hra.Insect;

import com.badlogic.gdx.graphics.Texture;

public class Collector extends InsectUnit {

    public Collector(String textureName, int x, int y, int healthPoints, int resourceCost, float speed, int damage, int range) {
        super(new Texture(textureName), x, y, healthPoints, resourceCost, speed, damage, range);
    }
}
