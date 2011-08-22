package com.mojang.escape.menu;

import com.mojang.escape.*;
import com.mojang.escape.entities.Item;
import com.mojang.escape.gui.Bitmap;

public class GotLootMenu extends Menu {
	private int tickDelay = 30;
	private Item item;

	public GotLootMenu(Item item) {
		this.item = item;
	}

	public void render(Bitmap target) {
		String str = "You found the " + item.name + "!";
		target.scaleDraw(Art.items, 3, target.width / 2 - 8 * 3, 2, item.icon * 16, 0, 16, 16, Art.getCol(item.color));
		target.draw(str, (target.width - str.length() * 6) / 2 + 2, 60 - 10, Art.getCol(0xffff80));

		str = item.description;
		target.draw(str, (target.width - str.length() * 6) / 2 + 2, 60, Art.getCol(0xa0a0a0));

		if (tickDelay == 0) target.draw("-> Continue", 40, target.height - 40, Art.getCol(0xffff80));
	}

	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use) {
		if (tickDelay > 0) tickDelay--;
		else if (use) {
			game.setMenu(null);
		}
	}
}