package org.npkompleet.app4mc.viz.sampleviz;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SampleVizController {
	@FXML
	private Label titleLabel;

	public void setTitleLabel(String value) {
		titleLabel.setText(value);
	}

	public void buttonClicked(Event e) {
		setTitleLabel("Button Clicked");
	}

}
