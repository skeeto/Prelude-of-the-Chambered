package com.mojang.escape.level.block;

import com.mojang.escape.Art;
import com.mojang.escape.entities.*;

public class IceBlock extends Block {
	public IceBlock() {
		blocksMotion = false;
		floorTex = 16;
	}

	public void tick() {
		super.tick();
		floorCol = Art.getCol(0x8080ff);
	}

	public double getWalkSpeed(Player player) {
		if (player.getSelectedItem() == Item.skates) return 0.05;
		return 1.4;
	}

	public double getFriction(Player player) {
		if (player.getSelectedItem() == Item.skates) return 0.98;
		return 1;
	}

	public boolean blocks(Entity entity) {
		if (entity instanceof Player) return false;
		if (entity instanceof Bullet) return false;
		if (entity instanceof EyeBossEntity) return false;
		if (entity instanceof EyeEntity) return false;
		return true;
	}
}
