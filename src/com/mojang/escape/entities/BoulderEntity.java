package com.mojang.escape.entities;

import com.mojang.escape.*;
import com.mojang.escape.gui.Sprite;

public class BoulderEntity extends Entity {
	public static final int COLOR = Art.getCol(0xAFA293);
	private Sprite sprite;
	private double rollDist = 0;

	public BoulderEntity(int x, int z) {
		this.x = x;
		this.z = z;
		sprite = new Sprite(0, 0, 0, 16, COLOR);
		sprites.add(sprite);
	}

	public void tick() {
		rollDist += Math.sqrt(xa * xa + za * za);
		sprite.tex = 8 + ((int) (rollDist * 4) & 1);
		double xao = xa;
		double zao = za;
		move();
		if (xa == 0 && xao != 0) xa = -xao * 0.3;
		if (za == 0 && zao != 0) za = -zao * 0.3;
		xa *= 0.98;
		za *= 0.98;
		if (xa * xa + za * za < 0.0001) {
			xa = za = 0;
		}
	}

	public boolean use(Entity source, Item item) {
		if (item != Item.powerGlove) return false;
		Sound.roll.play();

		xa += Math.sin(source.rot) * 0.1;
		za += Math.cos(source.rot) * 0.1;
		return true;
	}
}
