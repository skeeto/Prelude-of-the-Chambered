package com.mojang.escape.gui;

public class Sprite {
	public double x, y, z;
	public int tex;
	public int col = 0x202020;
	public boolean removed = false;

	public Sprite(double x, double y, double z, int tex, int color) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tex = tex;
		this.col = color;
	}

	public void tick() {
	}
}
