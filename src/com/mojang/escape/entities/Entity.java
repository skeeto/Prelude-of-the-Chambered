package com.mojang.escape.entities;

import java.util.*;

import com.mojang.escape.gui.Sprite;
import com.mojang.escape.level.Level;

public class Entity {
	protected static final Random random = new Random();
	public List<Sprite> sprites = new ArrayList<Sprite>();

	public double x, z, rot;
	public double xa, za, rota;
	public double r = 0.4;

	public Level level;
	public int xTileO = -1;
	public int zTileO = -1;
	public boolean flying = false;

	private boolean removed = false;

	public final void updatePos() {
		int xTile = (int) (x + 0.5);
		int zTile = (int) (z + 0.5);
		if (xTile != xTileO || zTile != zTileO) {
			level.getBlock(xTileO, zTileO).removeEntity(this);

			xTileO = xTile;
			zTileO = zTile;

			if (!removed) level.getBlock(xTileO, zTileO).addEntity(this);
		}
	}

	public boolean isRemoved() {
		return removed;
	}

	public void remove() {
		level.getBlock(xTileO, zTileO).removeEntity(this);
		removed = true;
	}

	protected void move() {
		int xSteps = (int) (Math.abs(xa * 100) + 1);
		for (int i = xSteps; i > 0; i--) {
			double xxa = xa;
			if (isFree(x + xxa * i / xSteps, z)) {
				x += xxa * i / xSteps;
				break;
			} else {
				xa = 0;
			}
		}

		int zSteps = (int) (Math.abs(za * 100) + 1);
		for (int i = zSteps; i > 0; i--) {
			double zza = za;
			if (isFree(x, z + zza * i / zSteps)) {
				z += zza * i / zSteps;
				break;
			} else {
				za = 0;
			}
		}
	}

	protected boolean isFree(double xx, double yy) {
		int x0 = (int) (Math.floor(xx + 0.5 - r));
		int x1 = (int) (Math.floor(xx + 0.5 + r));
		int y0 = (int) (Math.floor(yy + 0.5 - r));
		int y1 = (int) (Math.floor(yy + 0.5 + r));

		if (level.getBlock(x0, y0).blocks(this)) return false;
		if (level.getBlock(x1, y0).blocks(this)) return false;
		if (level.getBlock(x0, y1).blocks(this)) return false;
		if (level.getBlock(x1, y1).blocks(this)) return false;

		int xc = (int) (Math.floor(xx + 0.5));
		int zc = (int) (Math.floor(yy + 0.5));
		int rr = 2;
		for (int z = zc - rr; z <= zc + rr; z++) {
			for (int x = xc - rr; x <= xc + rr; x++) {
				List<Entity> es = level.getBlock(x, z).entities;
                for (Entity e : es) {
                    if (e == this) continue;

                    if (!e.blocks(this, this.x, this.z, r) && e.blocks(this, xx, yy, r)) {
                        e.collide(this);
                        this.collide(e);
                        return false;
                    }
                }
			}
		}
		return true;
	}

	protected void collide(Entity entity) {
	}

	public boolean blocks(Entity entity, double x2, double z2, double r2) {
		if (entity instanceof Bullet) {
			if (((Bullet) entity).owner == this) return false;
		}
		if (x + r <= x2 - r2) return false;
		if (x - r >= x2 + r2) return false;

		if (z + r <= z2 - r2) return false;
		if (z - r >= z2 + r2) return false;

		return true;
	}

	public boolean contains(double x2, double z2) {
		if (x + r <= x2) return false;
		if (x - r >= x2) return false;

		if (z + r <= z2) return false;
		if (z - r >= z2) return false;

		return true;
	}

	public boolean isInside(double x0, double z0, double x1, double z1) {
		if (x + r <= x0) return false;
		if (x - r >= x1) return false;

		if (z + r <= z0) return false;
		if (z - r >= z1) return false;

		return true;
	}

	public boolean use(Entity source, Item item) {
		return false;
	}

	public void tick() {
	}
}
