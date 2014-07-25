package com.mojang.escape.gui;

import com.mojang.escape.*;
import com.mojang.escape.entities.Entity;
import com.mojang.escape.level.Level;
import com.mojang.escape.level.block.*;

public class Bitmap3D extends Bitmap {
	private double[] zBuffer;
	private double[] zBufferWall;
	private double xCam, yCam, zCam, rCos, rSin, fov, xCenter, yCenter, rot;

	public Bitmap3D(int width, int height) {
		super(width, height);

		zBuffer = new double[width * height];
		zBufferWall = new double[width];
	}

	public void render(Game game) {
		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0;
		}
		for (int i = 0; i < width * height; i++) {
			zBuffer[i] = 10000;
		}
		rot = game.player.rot;
		xCam = game.player.x - Math.sin(rot) * 0.3;
		yCam = game.player.z - Math.cos(rot) * 0.3;
		zCam = -0.2 + Math.sin(game.player.bobPhase * 0.4) * 0.01 * game.player.bob - game.player.y;

		xCenter = width / 2.0;
		yCenter = height / 3.0;

		rCos = Math.cos(rot);
		rSin = Math.sin(rot);

		fov = height;

		Level level = game.level;
		int r = 6;

		int xCenter = (int) (Math.floor(xCam));
		int zCenter = (int) (Math.floor(yCam));
		for (int zb = zCenter - r; zb <= zCenter + r; zb++) {
			for (int xb = xCenter - r; xb <= xCenter + r; xb++) {
				Block c = level.getBlock(xb, zb);
				Block e = level.getBlock(xb + 1, zb);
				Block s = level.getBlock(xb, zb + 1);

				if (c instanceof DoorBlock) {
					double rr = 1 / 8.0;
					double openness = 1 - ((DoorBlock) c).openness * 7 / 8;
					if (e.solidRender) {
						renderWall(xb + openness, zb + 0.5 - rr, xb, zb + 0.5 - rr, c.tex, (c.col & 0xfefefe) >> 1, 0, openness);
						renderWall(xb, zb + 0.5 + rr, xb + openness, zb + 0.5 + rr, c.tex, (c.col & 0xfefefe) >> 1, openness, 0);
						renderWall(xb + openness, zb + 0.5 + rr, xb + openness, zb + 0.5 - rr, c.tex, c.col, 0.5 - rr, 0.5 + rr);
					} else {
						renderWall(xb + 0.5 - rr, zb, xb + 0.5 - rr, zb + openness, c.tex, c.col, openness, 0);
						renderWall(xb + 0.5 + rr, zb + openness, xb + 0.5 + rr, zb, c.tex, c.col, 0, openness);
						renderWall(xb + 0.5 - rr, zb + openness, xb + 0.5 + rr, zb + openness, c.tex, (c.col & 0xfefefe) >> 1, 0.5 - rr, 0.5 + rr);
					}

				}

				if (c.solidRender) {
					if (!e.solidRender) {
						renderWall(xb + 1, zb + 1, xb + 1, zb, c.tex, c.col);
					}
					if (!s.solidRender) {
						renderWall(xb, zb + 1, xb + 1, zb + 1, c.tex, (c.col & 0xfefefe) >> 1);
					}
				} else {
					if (e.solidRender) {
						renderWall(xb + 1, zb, xb + 1, zb + 1, e.tex, e.col);
					}
					if (s.solidRender) {
						renderWall(xb + 1, zb + 1, xb, zb + 1, s.tex, (s.col & 0xfefefe) >> 1);
					}
				}
			}
		}
		for (int zb = zCenter - r; zb <= zCenter + r; zb++) {
			for (int xb = xCenter - r; xb <= xCenter + r; xb++) {
				Block c = level.getBlock(xb, zb);

				for (int j = 0; j < c.entities.size(); j++) {
					Entity e = c.entities.get(j);
					for (int i = 0; i < e.sprites.size(); i++) {
						Sprite sprite = e.sprites.get(i);
						renderSprite(e.x + sprite.x, 0 - sprite.y, e.z + sprite.z, sprite.tex, sprite.col);
					}
				}

				for (int i = 0; i < c.sprites.size(); i++) {
					Sprite sprite = c.sprites.get(i);
					renderSprite(xb + sprite.x, 0 - sprite.y, zb + sprite.z, sprite.tex, sprite.col);
				}
			}
		}

		renderFloor(level);
	}

	private void renderFloor(Level level) {
		for (int y = 0; y < height; y++) {
			double yd = ((y + 0.5) - yCenter) / fov;

			boolean floor = true;
			double zd = (4 - zCam * 8) / yd;
			if (yd < 0) {
				floor = false;
				zd = (4 + zCam * 8) / -yd;
			}

			for (int x = 0; x < width; x++) {
				if (zBuffer[x + y * width] <= zd) continue;

				double xd = (xCenter - x) / fov;
				xd *= zd;

				double xx = xd * rCos + zd * rSin + (xCam + 0.5) * 8;
				double yy = zd * rCos - xd * rSin + (yCam + 0.5) * 8;

				int xPix = (int) (xx * 2);
				int yPix = (int) (yy * 2);
				int xTile = xPix >> 4;
				int yTile = yPix >> 4;

				Block block = level.getBlock(xTile, yTile);
				int col = block.floorCol;
				int tex = block.floorTex;
				if (!floor) {
					col = block.ceilCol;
					tex = block.ceilTex;
				}

				if (tex < 0) {
					zBuffer[x + y * width] = -1;
				} else {
					zBuffer[x + y * width] = zd;
					pixels[x + y * width] = Art.floors.pixels[((xPix & 15) + (tex % 8) * 16) + ((yPix & 15) + (tex / 8) * 16) * 128] * col;
				}
			}
		}

	}

	private void renderSprite(double x, double y, double z, int tex, int color) {
		double xc = (x - xCam) * 2 - rSin * 0.2;
		double yc = (y - zCam) * 2;
		double zc = (z - yCam) * 2 - rCos * 0.2;

		double xx = xc * rCos - zc * rSin;
		double yy = yc;
		double zz = zc * rCos + xc * rSin;

		if (zz < 0.1) return;

		double xPixel = xCenter - (xx / zz * fov);
		double yPixel = (yy / zz * fov + yCenter);

		double xPixel0 = xPixel - height / zz;
		double xPixel1 = xPixel + height / zz;

		double yPixel0 = yPixel - height / zz;
		double yPixel1 = yPixel + height / zz;

		int xp0 = (int) Math.ceil(xPixel0);
		int xp1 = (int) Math.ceil(xPixel1);
		int yp0 = (int) Math.ceil(yPixel0);
		int yp1 = (int) Math.ceil(yPixel1);

		if (xp0 < 0) xp0 = 0;
		if (xp1 > width) xp1 = width;
		if (yp0 < 0) yp0 = 0;
		if (yp1 > height) yp1 = height;
		zz *= 4;
		for (int yp = yp0; yp < yp1; yp++) {
			double ypr = (yp - yPixel0) / (yPixel1 - yPixel0);
			int yt = (int) (ypr * 16);
			for (int xp = xp0; xp < xp1; xp++) {
				double xpr = (xp - xPixel0) / (xPixel1 - xPixel0);
				int xt = (int) (xpr * 16);
				if (zBuffer[xp + yp * width] > zz) {
					int col = Art.sprites.pixels[(xt + tex % 8 * 16) + (yt + (tex / 8) * 16) * 128];
					if (col >= 0) {
						pixels[xp + yp * width] = col * color;
						zBuffer[xp + yp * width] = zz;
					}
				}
			}
		}

	}

	private void renderWall(double x0, double y0, double x1, double y1, int tex, int color) {
		renderWall(x0, y0, x1, y1, tex, color, 0, 1);
	}

	private void renderWall(double x0, double y0, double x1, double y1, int tex, int color, double xt0, double xt1) {
		double xc0 = ((x0 - 0.5) - xCam) * 2;
		double yc0 = ((y0 - 0.5) - yCam) * 2;

		double xx0 = xc0 * rCos - yc0 * rSin;
		double u0 = ((-0.5) - zCam) * 2;
		double l0 = ((+0.5) - zCam) * 2;
		double zz0 = yc0 * rCos + xc0 * rSin;

		double xc1 = ((x1 - 0.5) - xCam) * 2;
		double yc1 = ((y1 - 0.5) - yCam) * 2;

		double xx1 = xc1 * rCos - yc1 * rSin;
		double u1 = ((-0.5) - zCam) * 2;
		double l1 = ((+0.5) - zCam) * 2;
		double zz1 = yc1 * rCos + xc1 * rSin;

		xt0 *= 16;
		xt1 *= 16;

		double zClip = 0.2;

		if (zz0 < zClip && zz1 < zClip) return;

		if (zz0 < zClip) {
			double p = (zClip - zz0) / (zz1 - zz0);
			zz0 = zz0 + (zz1 - zz0) * p;
			xx0 = xx0 + (xx1 - xx0) * p;
			xt0 = xt0 + (xt1 - xt0) * p;
		}

		if (zz1 < zClip) {
			double p = (zClip - zz0) / (zz1 - zz0);
			zz1 = zz0 + (zz1 - zz0) * p;
			xx1 = xx0 + (xx1 - xx0) * p;
			xt1 = xt0 + (xt1 - xt0) * p;
		}

		double xPixel0 = xCenter - (xx0 / zz0 * fov);
		double xPixel1 = xCenter - (xx1 / zz1 * fov);

		if (xPixel0 >= xPixel1) return;
		int xp0 = (int) Math.ceil(xPixel0);
		int xp1 = (int) Math.ceil(xPixel1);
		if (xp0 < 0) xp0 = 0;
		if (xp1 > width) xp1 = width;

		double yPixel00 = (u0 / zz0 * fov + yCenter);
		double yPixel01 = (l0 / zz0 * fov + yCenter);
		double yPixel10 = (u1 / zz1 * fov + yCenter);
		double yPixel11 = (l1 / zz1 * fov + yCenter);

		double iz0 = 1 / zz0;
		double iz1 = 1 / zz1;

		double iza = iz1 - iz0;

		double ixt0 = xt0 * iz0;
		double ixta = xt1 * iz1 - ixt0;
		double iw = 1 / (xPixel1 - xPixel0);

		for (int x = xp0; x < xp1; x++) {
			double pr = (x - xPixel0) * iw;
			double iz = iz0 + iza * pr;

			if (zBufferWall[x] > iz) continue;
			zBufferWall[x] = iz;
			int xTex = (int) ((ixt0 + ixta * pr) / iz);

			double yPixel0 = yPixel00 + (yPixel10 - yPixel00) * pr - 0.5;
			double yPixel1 = yPixel01 + (yPixel11 - yPixel01) * pr;

			int yp0 = (int) Math.ceil(yPixel0);
			int yp1 = (int) Math.ceil(yPixel1);
			if (yp0 < 0) yp0 = 0;
			if (yp1 > height) yp1 = height;

			double ih = 1 / (yPixel1 - yPixel0);
			for (int y = yp0; y < yp1; y++) {
				double pry = (y - yPixel0) * ih;
				int yTex = (int) (16 * pry);
				pixels[x + y * width] = Art.walls.pixels[((xTex) + (tex % 8) * 16) + (yTex + tex / 8 * 16) * 128] * color;
				zBuffer[x + y * width] = 1 / iz * 4;
			}
		}
	}

	public void postProcess() {
		for (int i = 0; i < width * height; i++) {
			double zl = zBuffer[i];
			if (zl < 0) {
				int xx = ((int) Math.floor((i % width) - rot * 512 / (Math.PI * 2))) & 511;
				int yy = i / width;
				pixels[i] = Art.sky.pixels[xx + yy * 512] * 0x444455;
			} else {
				int xp = (i % width);
				int yp = (i / width) * 14;

				double xx = ((i % width - width / 2.0) / width);
				int col = pixels[i];
				int brightness = (int) (300 - zl * 6 * (xx * xx * 2 + 1));
				brightness = (brightness + ((xp + yp) & 3) * 4) >> 4 << 4;
				if (brightness < 0) brightness = 0;
				if (brightness > 255) brightness = 255;

				int r = (col >> 16) & 0xff;
				int g = (col >> 8) & 0xff;
				int b = (col) & 0xff;

				r = r * brightness / 255;
				g = g * brightness / 255;
				b = b * brightness / 255;

				pixels[i] = r << 16 | g << 8 | b;
			}
		}
	}
}
