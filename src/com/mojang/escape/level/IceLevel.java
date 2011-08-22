package com.mojang.escape.level;

import com.mojang.escape.entities.Item;

public class IceLevel extends Level {
	public IceLevel() {
		floorCol = 0xB8DBE0;
		ceilCol = 0xB8DBE0;
		wallCol = 0x6BE8FF;
		name = "The Frost Cave";
	}

	public void switchLevel(int id) {
		if (id == 1) game.switchLevel("overworld", 5);
	}

	public void getLoot(int id) {
		super.getLoot(id);
		if (id == 1) game.getLoot(Item.skates);
	}
}