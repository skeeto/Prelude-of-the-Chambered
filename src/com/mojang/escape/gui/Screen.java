package com.mojang.escape.gui;

import java.util.Random;

import com.mojang.escape.*;
import com.mojang.escape.entities.Item;
import com.mojang.escape.level.block.Block;

public class Screen extends Bitmap {
	private static final int PANEL_HEIGHT = 29;

	private Bitmap testBitmap;
	private Bitmap3D viewport;

	public Screen(int width, int height) {
		super(width, height);

		viewport = new Bitmap3D(width, height - PANEL_HEIGHT);

		Random random = new Random();
		testBitmap = new Bitmap(64, 64);
		for (int i = 0; i < 64 * 64; i++) {
			testBitmap.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}
	}

	int time = 0;

	public void render(Game game, boolean hasFocus) {
		if (game.level == null) {
			fill(0, 0, width, height, 0);
		} else {
			boolean itemUsed = game.player.itemUseTime > 0;
			Item item = game.player.items[game.player.selectedSlot];

			if (game.pauseTime > 0) {
				fill(0, 0, width, height, 0);
				String[] messages = { "Entering " + game.level.name, };
				for (int y = 0; y < messages.length; y++) {
					draw(messages[y], (width - messages[y].length() * 6) / 2, (viewport.height - messages.length * 8) / 2 + y * 8 + 1, 0x111111);
					draw(messages[y], (width - messages[y].length() * 6) / 2, (viewport.height - messages.length * 8) / 2 + y * 8, 0x555544);
				}
			} else {
				viewport.render(game);
				viewport.postProcess(game.level);

				Block block = game.level.getBlock((int) (game.player.x + 0.5), (int) (game.player.z + 0.5));
				if (block.messages != null && hasFocus) {
					for (int y = 0; y < block.messages.length; y++) {
						viewport.draw(block.messages[y], (width - block.messages[y].length() * 6) / 2, (viewport.height - block.messages.length * 8) / 2 + y * 8 + 1, 0x111111);
						viewport.draw(block.messages[y], (width - block.messages[y].length() * 6) / 2, (viewport.height - block.messages.length * 8) / 2 + y * 8, 0x555544);
					}
				}

				draw(viewport, 0, 0);
				int xx = (int) (game.player.turnBob * 32);
				int yy = (int) (Math.sin(game.player.bobPhase * 0.4) * 1 * game.player.bob + game.player.bob * 2);

				if (itemUsed) xx = yy = 0;
				xx += width / 2;
				yy += height - PANEL_HEIGHT - 15 * 3;
				if (item != Item.none) {
					scaleDraw(Art.items, 3, xx, yy, 16 * item.icon + 1, 16 + 1 + (itemUsed ? 16 : 0), 15, 15, Art.getCol(item.color));
				}

				if (game.player.hurtTime > 0 || game.player.dead) {
					double offs = 1.5 - game.player.hurtTime / 30.0;
					Random random = new Random(111);
					if (game.player.dead) offs = 0.5;
					for (int i = 0; i < pixels.length; i++) {
						double xp = ((i % width) - viewport.width / 2.0) / width * 2;
						double yp = ((i / width) - viewport.height / 2.0) / viewport.height * 2;

						if (random.nextDouble() + offs < Math.sqrt(xp * xp + yp * yp)) pixels[i] = (random.nextInt(5) / 4) * 0x550000;
					}
				}
			}

			draw(Art.panel, 0, height - PANEL_HEIGHT, 0, 0, width, PANEL_HEIGHT, Art.getCol(0x707070));

			draw("å", 3, height - 26 + 0, 0x00ffff);
			draw("" + game.player.keys + "/4", 10, height - 26 + 0, 0xffffff);
			draw("Ä", 3, height - 26 + 8, 0xffff00);
			draw("" + game.player.loot, 10, height - 26 + 8, 0xffffff);
			draw("Å", 3, height - 26 + 16, 0xff0000);
			draw("" + game.player.health, 10, height - 26 + 16, 0xffffff);

			for (int i = 0; i < 8; i++) {
				Item slotItem = game.player.items[i];
				if (slotItem != Item.none) {
					draw(Art.items, 30 + i * 16, height - PANEL_HEIGHT + 2, slotItem.icon * 16, 0, 16, 16, Art.getCol(slotItem.color));
					if (slotItem == Item.pistol) {
						String str = "" + game.player.ammo;
						draw(str, 30 + i * 16 + 17 - str.length() * 6, height - PANEL_HEIGHT + 1 + 10, 0x555555);
					}
					if (slotItem == Item.potion) {
						String str = "" + game.player.potions;
						draw(str, 30 + i * 16 + 17 - str.length() * 6, height - PANEL_HEIGHT + 1 + 10, 0x555555);
					}
				}
			}

			draw(Art.items, 30 + game.player.selectedSlot * 16, height - PANEL_HEIGHT + 2, 0, 48, 17, 17, Art.getCol(0xffffff));

			draw(item.name, 26 + (8 * 16 - item.name.length() * 4) / 2, height - 9, 0xffffff);
		}

		if (game.menu != null) {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = (pixels[i] & 0xfcfcfc) >> 2;
			}			
			game.menu.render(this);
		}

		if (!hasFocus) {
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = (pixels[i] & 0xfcfcfc) >> 2;
			}
			if (System.currentTimeMillis() / 450 % 2 != 0) {
				String msg = "Click to focus!";
				draw(msg, (width - msg.length() * 6) / 2, height / 3 + 4, 0xffffff);
			}
		}
	}
}