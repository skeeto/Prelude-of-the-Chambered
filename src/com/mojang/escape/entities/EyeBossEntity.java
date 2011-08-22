package com.mojang.escape.entities;

import com.mojang.escape.*;

public class EyeBossEntity extends EnemyEntity {
	public EyeBossEntity(double x, double z) {
		super(x, z, 4 * 8 + 4, Art.getCol(0xffff00));
		this.x = x;
		this.z = z;
		health = 10;
		r = 0.3;
		runSpeed = 4;
		spinSpeed *= 1.5;

		flying = true;
	}

	protected void die() {
		Sound.bosskill.play();
		level.addEntity(new KeyEntity(x, z));
	}
}
