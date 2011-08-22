package com.mojang.escape.entities;

import com.mojang.escape.Art;

public class GhostEntity extends EnemyEntity {
	private double rotatePos = 0;

	public GhostEntity(double x, double z) {
		super(x, z, 4 * 8 + 6, Art.getCol(0xffffff));
		this.x = x;
		this.z = z;
		health = 4;
		r = 0.3;

		flying = true;
	}

	public void tick() {
		animTime++;
		sprite.tex = defaultTex + animTime / 10 % 2;

		double xd = (level.player.x + Math.sin(rotatePos)) - x;
		double zd = (level.player.z + Math.cos(rotatePos)) - z;
		double dd = xd * xd + zd * zd;

		if (dd < 4 * 4) {
			if (dd < 1) {
				rotatePos += 0.04;
			} else {
				rotatePos = level.player.rot;
				xa += (random.nextDouble() - 0.5) * 0.02;
				za += (random.nextDouble() - 0.5) * 0.02;
			}
			
			dd = Math.sqrt(dd);

			xd /= dd;
			zd /= dd;

			xa += xd * 0.004;
			za += zd * 0.004;
		}

		move();

		xa *= 0.9;
		za *= 0.9;
	}

	protected void hurt(double xd, double zd) {
	}

	protected void move() {
		x += xa;
		z += za;
	}
}