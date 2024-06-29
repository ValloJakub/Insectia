package sk.uniza.fri.hra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import sk.uniza.fri.hra.Hives.*;
import sk.uniza.fri.hra.Insect.Collector;
import sk.uniza.fri.hra.Insect.InsectUnit;
import sk.uniza.fri.hra.Resources.Resources;

import java.util.ArrayList;
import java.util.Random;

public class Insectia extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture texture;
	private BitmapFont resourcesCount;
	private ShapeRenderer shapeRenderer;

	private final int playerX = 0;
	private final int enemyX = 1120;
	private final int y = 280;

	private Hive playerHive = null;
	private Hive enemyHive = null;

	private ArrayList<Resources> listOfResources;

	/* Voľba úľov:
	1 = AntHill;
	2 = BeeHive;
	3 = CobWebs;
	4 = HornetNest;
	5 = TermiteColony;
	6 = WaspHive;
	*/

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.texture = new Texture("Grassland.jpg");
		this.shapeRenderer = new ShapeRenderer();
		this.resourcesCount = new BitmapFont();

		this.playerHive = new BeeHive(playerX, y);
		this.playerHive.setSide("Left");

		this.enemyHive =  new BeeHive(enemyX, y);
		this.enemyHive.setSide("Right");

		this.listOfResources = new ArrayList<>();
		}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();

		ScreenUtils.clear(0, 0, 0, 1);
		this.batch.begin();

		// Vykreslenie pozadia
		this.batch.draw(this.texture, 0, 0);

		// Vykreslenie množstva surovín hráča
		this.resourcesCount.draw(batch, "Resources: " + this.playerHive.getResourceCount(), 30, 50);

		// Vykreslenie hniezd
		this.playerHive.draw(batch, this.shapeRenderer);
		this.enemyHive.draw(batch, this.shapeRenderer);

		// Generácia zdrojov surovín
		this.generateResources();

		// Vyzdvihnutie surovín
		this.collectResource();

		// Pohyb zberačov
		this.collectorMovement();


		// Vytváranie jednotiek
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			if (this.playerHive.getResourceCount() >= 20) {	// TODO: Prepisat konstantu na cenu collectora (Vytvorit napr getter)
				this.playerHive.recruitCollector();
				this.playerHive.reduceResources(20);
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			if (this.enemyHive.getResourceCount() >= 20) {	// TODO: Prepisat konstantu na cenu collectora (Vytvorit napr getter)
				this.enemyHive.recruitCollector();
				this.enemyHive.reduceResources(20);
			}
		}

		this.batch.end();

		this.drawHealthBars();

		/*
		int xTexture = Gdx.graphics.getWidth() / 3;
		int yTexture = Gdx.graphics.getHeight() / 4;
		int yMouse = Gdx.graphics.getHeight() - Gdx.input.getY();
		int xMouse = Gdx.input.getX();

		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			//if (xMouse >= xTexture && xMouse <= xTexture + this.textura.getWidth() &&			// ci som klikol do obrazka = kontrola
			//	yMouse >= yTexture && yMouse <= yTexture + this.textura.getHeight()) {
			this.surX = xMouse - (this.textura.getWidth() / 2);
			this.surY = yMouse - (this.textura.getHeight() / 2);
		}

		if (this.surX != -1 || this.surY != -1) {
			if (!(this.surX < 0 || this.surX + this.textura.getWidth() > 800 || this.surY < 0 || this.surY + this.textura.getHeight() > 480)) {
				this.batch.draw(this.textura, this.surX, this.surY);
			}
		}*/
		/*
		if (this.surY + this.textura.getHeight() < 480) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				this.surY += 4;
			}
		}
		if (this.surY > 0) {
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				this.surY -= 4;
			}
		}
		if (this.surX > 0) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				this.surX -= 4;
			}
		}
		if (this.surX + this.textura.getWidth() < 800) {
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				this.surX += 4;
			}
		}*/

	}

	private void drawHealthBars() {
		this.playerHive.getHealthBar().draw(this.shapeRenderer, this.playerHive.getX(), this.playerHive.getY() + this.playerHive.getTexture().getHeight());
		this.enemyHive.getHealthBar().draw(this.shapeRenderer, this.enemyHive.getX(), this.enemyHive.getY() + this.enemyHive.getTexture().getHeight());

		for (InsectUnit fUnit: this.playerHive.getListOfUnits()) {
			fUnit.getHealthBar().draw(this.shapeRenderer, fUnit.getX(), fUnit.getY() + fUnit.getTexture().getHeight());
		}

		for (InsectUnit eUnit: this.enemyHive.getListOfUnits()) {
			eUnit.getHealthBar().draw(this.shapeRenderer, eUnit.getX(), eUnit.getY() + eUnit.getTexture().getHeight());
		}
	}

	private void collectorMovement() {
		if (!this.playerHive.getListOfUnits().isEmpty()) {
			for (InsectUnit fUnit : this.playerHive.getListOfUnits()) {
				if (fUnit instanceof Collector) {
					// Včela sa pohybuje smerom k najbližšej surovine
					this.moveTowardsResource(this.listOfResources, fUnit);

					// Jednotka sa pohybuje smerom k úľu ak neexistujú suroviny na zber
					this.moveTowardsHive(this.playerHive, this.playerHive.getX(), this.playerHive.getY(), fUnit);
				}
				fUnit.draw(batch);
			}
		}

		if (!this.enemyHive.getListOfUnits().isEmpty()) {
			for (InsectUnit eUnit : this.enemyHive.getListOfUnits()) {
				if (eUnit instanceof Collector) {
					// Jednotka sa pohybuje smerom k najbližšej surovine
					this.moveTowardsResource(this.listOfResources, eUnit);

					// Jednotka sa pohybuje smerom k úľu ak neexistujú suroviny na zber
					this.moveTowardsHive(this.enemyHive, this.enemyHive.getX(), this.enemyHive.getY(), eUnit);
				}
				eUnit.draw(batch);
			}
		}
	}

	private void moveTowardsHive(Hive hive, int hiveX, int hiveY, InsectUnit unit) {
		// 160x160 rozmer úľa
		int xMargin;
		int yMargin = 80;
		if (hive.getSide().equals("Left")) {
			xMargin = 180;
		} else {
			xMargin = -40;
		}


		if (listOfResources.isEmpty()) {
			if (hiveY + yMargin < unit.getY()) {
				unit.move(0, -unit.getSpeed());
			}
			if (hiveY + yMargin > unit.getY()) {
				unit.move(0, unit.getSpeed());
			}
			if (hiveX + xMargin < unit.getX()) {
				unit.move(-unit.getSpeed(), 0);
			}
			if (hiveX + xMargin > unit.getX()) {
				unit.move(unit.getSpeed(), 0);
			}
		}
	}

	private void moveTowardsResource(ArrayList<Resources> listOfResources, InsectUnit unit) {
		if (!listOfResources.isEmpty()) {
			if (this.findNearestResource(listOfResources, unit).getY() < unit.getY()) {
				unit.move(0, -unit.getSpeed());
			}
			if (this.findNearestResource(listOfResources, unit).getY() > unit.getY()) {
				unit.move(0, unit.getSpeed());
			}
			if (this.findNearestResource(listOfResources, unit).getX() < unit.getX()) {
				unit.move(-unit.getSpeed(), 0);
			}
			if (this.findNearestResource(listOfResources, unit).getX() > unit.getX()) {
				unit.move(unit.getSpeed(), 0);
			}
		}
	}
	private double calculateDistance(InsectUnit u, Resources r) {
		double dx = u.getX() - r.getX();
		double dy = u.getY() - r.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	private boolean collectorTouchedTheHive(Hive hive, InsectUnit unit) {
		return unit.getUnitHitbox().overlaps(hive.getHiveHitbox());
	}

	private Resources findNearestResource(ArrayList<Resources> listOfResources, InsectUnit unit) {
		Resources nearest = null;

		double minDistance = Double.MAX_VALUE;

		for (Resources r : listOfResources) {
			double distance = calculateDistance(unit, r);
			if (distance < minDistance) {
				minDistance = distance;
				nearest = r;
			}
		}
		return nearest;
	}

	private void collectResource() {
		ArrayList<Resources> resourcesToRemove = new ArrayList<>();

		for (Resources resource : this.listOfResources) {
			resource.draw(batch);

			for (InsectUnit unit : this.playerHive.getListOfUnits()) {
				if (unit instanceof Collector && resource.wasPickedUp(unit)) {
					this.playerHive.addResources(resource.getResourceValue());
					resource.dispose();
					resourcesToRemove.add(resource);
				}
			}

			for (InsectUnit unit : this.enemyHive.getListOfUnits()) {
				if (unit instanceof Collector && resource.wasPickedUp(unit)) {
					this.enemyHive.addResources(resource.getResourceValue());
					resource.dispose();
					resourcesToRemove.add(resource);
				}
			}
		}
		this.listOfResources.removeAll(resourcesToRemove);
	}

	private void generateResources() {
		this.generateNectar();
		// another
		// another
		// another
	}

	private void generateNectar() {
		Random random = new Random();
		if (random.nextInt(500) == 1) {
			this.listOfResources.add(new Resources(10));
		}
	}
	
	@Override
	public void dispose () {
		this.batch.dispose();
		this.texture.dispose();
		this.playerHive.dispose();
		this.enemyHive.dispose();
		this.resourcesCount.dispose();

		for (Resources r : this.listOfResources) {
			r.dispose();
		}
	}
}
