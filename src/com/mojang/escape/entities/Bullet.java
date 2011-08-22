package com.mojang.escape.entities;

import com.mojang.escape.Art;
import com.mojang.escape.gui.Sprite;

public class Bullet extends Entity {
	Entity owner;

	public Bullet(Entity owner, double x, double z, double rot, double pow, int sprite, int col) {
		this.r = 0.01;
		this.owner = owner;

		xa = Math.sin(rot) * 0.2 * pow;
		za = Math.cos(rot) * 0.2 * pow;
		this.x = x - za / 2;
		this.z = z + xa / 2;

		sprites.add(new Sprite(0, 0, 0, 8 * 3 + sprite, Art.getCol(col)));

		flying = true;
	}

	public void tick() {
		double xao = xa;
		double zao = za;
		move();

		if ((xa == 0 && za == 0) || xa != xao || za != zao) {
			remove();
		}
	}

	public boolean blocks(Entity entity, double x2, double z2, double r2) {
		if (entity instanceof Bullet) {
			return false;
		}
		if (entity == owner) return false;
		
		return super.blocks(entity, x2, z2, r2);
	}

	protected void collide(Entity entity) {
	}
}
