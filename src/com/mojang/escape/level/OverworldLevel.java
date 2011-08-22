package com.mojang.escape.level;

import com.mojang.escape.entities.Item;

public class OverworldLevel extends Level {
	public OverworldLevel() {
		ceilTex = -1;
		floorCol = 0x508253;
		floorTex = 8 * 3;
		wallCol = 0xa0a0a0;
		name = "The Island";
	}

	public void switchLevel(int id) {
		if (id == 1) game.switchLevel("start", 1);
		if (id == 2) game.switchLevel("crypt", 1);
		if (id == 3) game.switchLevel("temple", 1);
		if (id == 5) game.switchLevel("ice", 1);
	}

	public void getLoot(int id) {
		super.getLoot(id);
		if (id == 1) game.getLoot(Item.cutters);
	}
}