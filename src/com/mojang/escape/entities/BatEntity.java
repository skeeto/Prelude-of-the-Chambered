package com.mojang.escape.entities;

import com.mojang.escape.*;

public class BatEntity extends EnemyEntity {
	public BatEntity(double x, double z) {
		super(x, z, 4 * 8, Art.getCol(0x82666E));
		this.x = x;
		this.z = z;
		health = 2;
		r = 0.3;

		flying = true;
	}
}
