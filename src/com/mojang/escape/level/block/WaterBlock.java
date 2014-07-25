package com.mojang.escape.level.block;

import com.mojang.escape.Art;
import com.mojang.escape.entities.*;

public class WaterBlock extends Block {
	int steps = 0;

	public WaterBlock() {
		blocksMotion = true;
	}

	public void tick() {
		super.tick();
		steps--;
		if (steps <= 0) {
			floorTex = 8 + random.nextInt(3);
			floorCol = Art.getCol(0x0000ff);
			steps = 16;
		}
	}

	public boolean blocks(Entity entity) {
		if (entity instanceof Player) {
			if (((Player) entity).getSelectedItem() == Item.flippers) return false;
		}
        return !(entity instanceof Bullet) && blocksMotion;
    }

	public double getFloorHeight(Entity e) {
		return -0.5;
	}

	public double getWalkSpeed(Player player) {
		return 0.4;
	}

}
