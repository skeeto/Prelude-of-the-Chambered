package com.mojang.escape.level.block;

import java.util.Random;

import com.mojang.escape.Art;
import com.mojang.escape.gui.Sprite;
import com.mojang.escape.level.Level;

public class TorchBlock extends Block {
	private Sprite torchSprite;

	public TorchBlock() {
		torchSprite = new Sprite(0, 0, 0, 3, Art.getCol(0xffff00));
		sprites.add(torchSprite);
	}

	public void decorate(Level level, int x, int y) {
		Random random = new Random((x + y * 1000) * 341871231);
		double r = 0.4;
		for (int i = 0; i < 1000; i++) {
			int face = random.nextInt(4);
			if (face == 0 && level.getBlock(x - 1, y).solidRender) {
				torchSprite.x -= r;
				break;
			}
			if (face == 1 && level.getBlock(x, y - 1).solidRender) {
				torchSprite.z -= r;
				break;
			}
			if (face == 2 && level.getBlock(x + 1, y).solidRender) {
				torchSprite.x += r;
				break;
			}
			if (face == 3 && level.getBlock(x, y + 1).solidRender) {
				torchSprite.z += r;
				break;
			}
		}
	}

	public void tick() {
		super.tick();
		if (random.nextInt(4) == 0) torchSprite.tex = 3 + random.nextInt(2);
	}
}
