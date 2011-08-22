package com.mojang.escape.menu;

import com.mojang.escape.*;
import com.mojang.escape.gui.Bitmap;

public class AboutMenu extends Menu {
	private int tickDelay = 30;

	public void render(Bitmap target) {
		target.fill(0, 0, 160, 120, 0);

		target.draw("About", 60, 8, Art.getCol(0xffffff));
		
		String[] lines = {
				"Prelude of the Chambered",
				"by Markus Persson.",
				"Made Aug 2011 for the",
				"21'st Ludum Dare compo.",
				"",
				"This game is freeware,",
				"and was made from scratch",
				"in just 48 hours.",
		};
		
		for (int i=0; i<lines.length; i++) {
			target.draw(lines[i], 4, 28+i*8, Art.getCol(0xa0a0a0));
		}

		if (tickDelay == 0) target.draw("-> Continue", 40, target.height - 16, Art.getCol(0xffff80));
	}

	public void tick(Game game, boolean up, boolean down, boolean left, boolean right, boolean use) {
		if (tickDelay > 0) tickDelay--;
		else if (use) {
			Sound.click1.play();
			game.setMenu(new TitleMenu());
		}
	}
}
