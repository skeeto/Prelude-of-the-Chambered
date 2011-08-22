package com.mojang.escape.level.block;

import com.mojang.escape.Sound;
import com.mojang.escape.entities.Entity;

public class PressurePlateBlock extends Block {
	public boolean pressed = false;

	public PressurePlateBlock() {
		floorTex = 2;
	}

	public void tick() {
		super.tick();
		double r = 0.2;
		boolean steppedOn = level.containsBlockingNonFlyingEntity(x - r, y - r, x + r, y + r);
		if (steppedOn != pressed) {
			pressed = steppedOn;
			if (pressed) floorTex = 3;
			else floorTex = 2;

			level.trigger(id, pressed);
			if (pressed)
				Sound.click1.play();
			else
				Sound.click2.play();
		}
	}

	public double getFloorHeight(Entity e) {
		if (pressed) return -0.02;
		else return 0.02;
	}
}
