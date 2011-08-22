package com.mojang.escape;

import java.applet.Applet;
import java.awt.BorderLayout;

public class EscapeApplet extends Applet {
	private static final long serialVersionUID = 1L;

	private EscapeComponent escapeComponent = new EscapeComponent();

	public void init() {
		setLayout(new BorderLayout());
		add(escapeComponent, BorderLayout.CENTER);
	}

	public void start() {
		escapeComponent.start();
	}

	public void stop() {
		escapeComponent.stop();
	}

}