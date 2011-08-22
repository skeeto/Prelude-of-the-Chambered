package com.mojang.escape.entities;

import com.mojang.escape.*;
import com.mojang.escape.gui.*;

public class EnemyEntity extends Entity {
	protected Sprite sprite;
	protected double rot;
	protected double rota;
	protected int defaultTex;
	protected int defaultColor;
	protected int hurtTime = 0;
	protected int animTime = 0;
	protected int health = 3;
	protected double spinSpeed = 0.1;
	protected double runSpeed = 1;

	public EnemyEntity(double x, double z, int defaultTex, int defaultColor) {
		this.x = x;
		this.z = z;
		this.defaultColor = defaultColor;
		this.defaultTex = defaultTex;
		sprite = new Sprite(0, 0, 0, 4 * 8, defaultColor);
		sprites.add(sprite);
		r = 0.3;
	}

	public void tick() {
		if (hurtTime > 0) {
			hurtTime--;
			if (hurtTime == 0) {
				sprite.col = defaultColor;
			}
		}
		animTime++;
		sprite.tex = defaultTex + animTime / 10 % 2;
		move();
		if (xa == 0 || za == 0) {
			rota += (random.nextGaussian() * random.nextDouble()) * 0.3;
		}

		rota += (random.nextGaussian() * random.nextDouble()) * spinSpeed;
		rot += rota;
		rota *= 0.8;
		xa *= 0.8;
		za *= 0.8;
		xa += Math.sin(rot) * 0.004 * runSpeed;
		za += Math.cos(rot) * 0.004 * runSpeed;
	}

	public boolean use(Entity source, Item item) {
		if (hurtTime > 0) return false;
		if (item != Item.powerGlove) return false;

		hurt(Math.sin(source.rot), Math.cos(source.rot));

		return true;
	}

	protected void hurt(double xd, double zd) {
		sprite.col = Art.getCol(0xff0000);
		hurtTime = 15;

		double dd = Math.sqrt(xd * xd + zd * zd);
		xa += xd / dd * 0.2;
		za += zd / dd * 0.2;
		Sound.hurt2.play();
		health--;
		if (health <= 0) {
			int xt = (int) (x + 0.5);
			int zt = (int) (z + 0.5);
			level.getBlock(xt, zt).addSprite(new PoofSprite(x - xt, 0, z - zt));
			die();
			remove();
			Sound.kill.play();
		}
	}

	protected void die() {

	}

	protected void collide(Entity entity) {
		if (entity instanceof Bullet) {
			Bullet bullet = (Bullet) entity;
			if (bullet.owner.getClass() == this.getClass()) {
				return;
			}
			if (hurtTime > 0) return;
			entity.remove();
			hurt(entity.xa, entity.za);
		}
		if (entity instanceof Player) {
			((Player) entity).hurt(this, 1);
		}
	}
}
