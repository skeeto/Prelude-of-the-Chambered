package com.mojang.escape.entities;

import com.mojang.escape.Art;

public class EyeEntity extends EnemyEntity {
	public EyeEntity(double x, double z) {
		super(x, z, 4 * 8+4, Art.getCol(0x84ECFF));
		this.x = x;
		this.z = z;
		health = 4;
		r = 0.3;
		runSpeed = 2;
		spinSpeed *= 1.5;
		
		flying = true;
	}
}
