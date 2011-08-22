package com.mojang.escape.entities;

import com.mojang.escape.*;

public class BatBossEntity extends EnemyEntity {
	public BatBossEntity(int x, int z) {
		super(x, z, 4 * 8, Art.getCol(0xffff00));
		this.x = x;
		this.z = z;
		health = 5;
		r = 0.3;

		flying = true;
	}

	protected void die() {
		Sound.bosskill.play();
		level.addEntity(new KeyEntity(x, z));
	}

	public void tick() {
		super.tick();
		if (random.nextInt(20) == 0) {
			double xx = x + (random.nextDouble() - 0.5) * 2;
			double zz = z + (random.nextDouble() - 0.5) * 2;
			BatEntity batEntity = new BatEntity(xx, zz);
			batEntity.level = level;

			batEntity.x = -999;
			batEntity.z = -999;
			
			if (batEntity.isFree(xx, zz)) {
				level.addEntity(batEntity);
			}
		}
	}
}
