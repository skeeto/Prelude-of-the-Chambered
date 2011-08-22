package com.mojang.escape.entities;

import com.mojang.escape.Art;

public class OgreEntity extends EnemyEntity {
	private int shootDelay;

	public OgreEntity(double x, double z) {
		super(x, z, 4 * 8 + 2, Art.getCol(0x82A821));
		this.x = x;
		this.z = z;
		health = 6;
		r = 0.4;
		spinSpeed = 0.05;
	}

	@Override
	protected void hurt(double xd, double zd) {
		super.hurt(xd, zd);
		shootDelay = 50;
	}

	public void tick() {
		super.tick();
		if (shootDelay > 0) shootDelay--;
		else if (random.nextInt(40) == 0) {
			shootDelay = 40;
			level.addEntity(new Bullet(this, x, z, Math.atan2(level.player.x - x, level.player.z - z), 0.3, 1, defaultColor));
		}
	}
}
