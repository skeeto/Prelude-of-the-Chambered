package com.mojang.escape;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.mojang.escape.gui.Bitmap;

public class Art {
	public static Bitmap walls = loadBitmap("/tex/walls.png");
	public static Bitmap floors = loadBitmap("/tex/floors.png");
	public static Bitmap sprites = loadBitmap("/tex/sprites.png");
	public static Bitmap font = loadBitmap("/tex/font.png");
	public static Bitmap panel = loadBitmap("/tex/gamepanel.png");
	public static Bitmap items = loadBitmap("/tex/items.png");
	public static Bitmap sky = loadBitmap("/tex/sky.png");

	public static Bitmap logo = loadBitmap("/gui/logo.png");

	public static Bitmap loadBitmap(String fileName) {
		try {
			BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

			int w = img.getWidth();
			int h = img.getHeight();

			Bitmap result = new Bitmap(w, h);
			img.getRGB(0, 0, w, h, result.pixels, 0, w);
			for (int i = 0; i < result.pixels.length; i++) {
				int in = result.pixels[i];
				int col = (in & 0xf) >> 2;
				if (in == 0xffff00ff) col = -1;
				result.pixels[i] = col;
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int getCol(int c) {
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = (c) & 0xff;

		r = r * 0x55 / 0xff;
		g = g * 0x55 / 0xff;
		b = b * 0x55 / 0xff;

		return r << 16 | g << 8 | b;
	}
}