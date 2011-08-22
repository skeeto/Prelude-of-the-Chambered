package com.mojang.escape.gui;

public class RubbleSprite extends Sprite {
	private double xa, ya, za;

	public RubbleSprite() {
		super(Math.random() - 0.5, Math.random() * 0.8, Math.random() - 0.5, 2, 0x555555);
		xa = Math.random() - 0.5;
		ya = Math.random();
		za = Math.random() - 0.5;
	}

	public void tick() {
		x += xa * 0.03;
		y += ya * 0.03;
		z += za * 0.03;
		ya -= 0.1;
		if (y < 0) {
			y = 0;
			xa *= 0.8;
			za *= 0.8;
			if (Math.random() < 0.04)
				removed = true;
		}
	}
}
