package com.mojang.escape.entities;

import com.mojang.escape.*;

public class BossOgre extends EnemyEntity {
	private int shootDelay;
	private int shootPhase;

	public BossOgre(double x, double z) {
		super(x, z, 4 * 8 + 2, Art.getCol(0xffff00));
		this.x = x;
		this.z = z;
		health = 10;
		r = 0.4;
		spinSpeed = 0.05;
	}

	protected void die() {
		Sound.bosskill.play();
		level.addEntity(new KeyEntity(x, z));
	}

	public void tick() {
		super.tick();
		if (shootDelay > 0) shootDelay--;
		else {
			shootDelay = 5;
			int salva = 10;

			for (int i = 0; i < 4; i++) {
				double rot = Math.PI / 2 * (i + shootPhase / salva % 2 * 0.5);
				level.addEntity(new Bullet(this, x, z, rot, 0.4, 1, defaultColor));
			}

			shootPhase++;
			if (shootPhase % salva == 0) shootDelay = 40;
		}

	}
}
