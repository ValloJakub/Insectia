package sk.uniza.fri.hra.Hives;

import com.badlogic.gdx.graphics.Texture;
import sk.uniza.fri.hra.Insect.Collector;
import sk.uniza.fri.hra.Insect.InsectUnit;

import java.util.Random;

public class WaspHive extends Hive {
    public WaspHive(int x, int y) {
        super(x, y);
        this.setTexture("Hives/WaspHive.png");
    }

    @Override
    public void recruitCollector() {
        Random random = new Random();
        int randomY = random.nextInt((420 - 280) + 1) + 280;
        if (this.getSide().equals("Left")) {
            this.listOfUnits.add(new Collector("Collectors/Wasp_R.png",180, randomY, 20, 20,1.4f, 0, 10));
        }
        if (this.getSide().equals("Right")) {
            this.listOfUnits.add(new Collector("Collectors/Wasp_L.png",1100, randomY, 20, 20, 1.4f, 0, 10));
        }
    }

    @Override
    public void recruitFighter() {

    }

    @Override
    public void recruitStriker() {

    }
}
