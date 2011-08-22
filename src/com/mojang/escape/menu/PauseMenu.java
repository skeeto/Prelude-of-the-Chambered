package com.mojang.escape.menu;

import com.mojang.escape.*;
import com.mojang.escape.gui.Bitmap;

public class PauseMenu extends Menu {
	private String[] options = { "Abort game", "Continue" };
	private int selected = 1;

	public void render(Bitmap target) {
		target.draw(Art.logo, 0, 8, 0, 0, 160, 36, Art.getCol(0xffffff));

		for (int i = 0; i < options.length; i++) {
			String msg = options[i];
			int col = 0x909090;
			if (selected == i) {
				msg = "-> " + msg;
				col = 0xffff80;
			}
			target.draw(msg, 40, 60 + i * 10, Art.getCol(col));
		}
	}

	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use) {
		if (up || down) Sound.click2.play();
		if (up) selected--;
		if (down) selected++;
		if (selected < 0) selected = 0;
		if (selected >= options.length) selected = options.length - 1;
		if (use) {
			Sound.click1.play();
			if (selected == 0) {
				game.setMenu(new TitleMenu());
			}
			if (selected == 1) {
				game.setMenu(null);
			}
		}
	}
}
