package com.mojang.escape.level;

import com.mojang.escape.entities.Item;

public class CryptLevel extends Level {
	public CryptLevel() {
		floorCol = 0x404040;
		ceilCol = 0x404040;
		wallCol = 0x404040;
		name = "The Crypt";
	}

	public void switchLevel(int id) {
		if (id == 1) game.switchLevel("overworld", 2);
	}

	public void getLoot(int id) {
		super.getLoot(id);
		if (id == 1) game.getLoot(Item.flippers);
	}
}