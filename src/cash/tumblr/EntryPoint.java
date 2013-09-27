package cash.tumblr;

import java.awt.EventQueue;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cash.tumblr.controller.ScrapeController;
import cash.tumblr.view.ScraperView;

public class EntryPoint {
	public static void main(String[] args) {
		ScrapeController.INSTANCE.run();
	}

	}

