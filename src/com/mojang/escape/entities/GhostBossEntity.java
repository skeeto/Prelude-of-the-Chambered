package com.mojang.escape.entities;

import com.mojang.escape.Art;

public class GhostBossEntity extends EnemyEntity {
	private double rotatePos = 0;
	private int shootDelay = 0;

	public GhostBossEntity(double x, double z) {
		super(x, z, 4 * 8 + 6, Art.getCol(0xffff00));
		this.x = x;
		this.z = z;
		health = 10;
		flying = true;
	}

	public void tick() {
		animTime++;
		sprite.tex = defaultTex + animTime / 10 % 2;

		double xd = (level.player.x + Math.sin(rotatePos) * 2) - x;
		double zd = (level.player.z + Math.cos(rotatePos) * 2) - z;
		double dd = xd * xd + zd * zd;

		if (dd < 1) {
			rotatePos += 0.04;
		} else {
			rotatePos = level.player.rot;
		}
		if (dd < 4 * 4) {
			dd = Math.sqrt(dd);

			xd /= dd;
			zd /= dd;

			xa += xd * 0.006;
			za += zd * 0.006;
			
			if (shootDelay > 0) shootDelay--;
			else if (random.nextInt(10) == 0) {
				shootDelay = 10;
				level.addEntity(new Bullet(this, x, z, Math.atan2(level.player.x - x, level.player.z - z), 0.20, 1, defaultColor));
			}

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
