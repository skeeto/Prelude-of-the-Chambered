package com.mojang.escape.entities;

public enum Item {
	none(-1, 0xFFC363, "", ""), 
	powerGlove(0, 0xFFC363, "Power Glove", 		"Smaaaash!!"), 
	pistol(1, 0xEAEAEA, "Pistol",          		"Pew, pew, pew!"), 
	flippers(2, 0x7CBBFF, "Flippers", 			"Splish splash!"), 
	cutters(3, 0xCCCCCC, "Cutters", 			"Snip, snip!"), 
	skates(4, 0xAE70FF, "Skates", 				"Sharp!"),
	key(5, 0xFF4040, "Key", 					"How did you get this?"), 
	potion(6, 0x4AFF47, "Potion", 				"Healthy!"); 
	
	public final int icon;
	public final int color;
	public final String name;
	public final String description;
	
	private Item(int icon, int color, String name, String description) {
		this.icon = icon;
		this.color = color;
		this.name = name;
		this.description = description;
	}

}
