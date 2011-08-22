package com.mojang.escape.level;

import com.mojang.escape.entities.Item;

public class TempleLevel extends Level {
	private int triggerMask = 0;

	public TempleLevel() {
		floorCol = 0x8A6496;
		ceilCol = 0x8A6496;
		wallCol = 0xCFADDB;
		name = "The Temple";
	}

	public void switchLevel(int id) {
		if (id == 1) game.switchLevel("overworld", 3);
	}

	public void getLoot(int id) {
		super.getLoot(id);
		if (id == 1) game.getLoot(Item.skates);
	}

	public void trigger(int id, boolean pressed) {
		triggerMask |= 1 << id;
		if (!pressed) triggerMask ^= 1 << id;

		if (triggerMask == 14) {
			super.trigger(1, true);
		} else {
			super.trigger(1, false);
		}
	}

}