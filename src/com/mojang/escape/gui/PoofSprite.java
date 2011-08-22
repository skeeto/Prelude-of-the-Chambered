package com.mojang.escape.gui;

public class PoofSprite extends Sprite {
	int life = 20;

	public PoofSprite(double x, double y, double z) {
		super(x, y, z, 5, 0x222222);
	}

	public void tick() {
		if (life-- <= 0) removed = true;

	}
}
