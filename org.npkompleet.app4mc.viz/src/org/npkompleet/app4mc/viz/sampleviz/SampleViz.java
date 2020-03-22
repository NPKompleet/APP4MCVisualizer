package org.npkompleet.app4mc.viz.sampleviz;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.app4mc.amalthea.model.SWModel;
import org.eclipse.app4mc.visualization.ui.registry.Visualization;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.annotations.Component;

import javafx.embed.swt.FXCanvas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

@Component(
		property = {
				"id=SampleViz",
				"name=SWModel Visualization",
				"description=Some other SWModel visualization" })
public class SampleViz implements Visualization {

	@PostConstruct
	public void createVisualization(SWModel model, Composite parent) throws IOException {
		parent.setLayout(new GridLayout());

		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);

		// Load the view fxml file and set the title of the visualization
		Parent layout = FXMLLoader.load(getClass().getResource("SampleVizView.fxml"));
		Label label = (Label) layout.getChildrenUnmodifiable().get(1);
		label.setText("A Sample Visualization");

		// create a Scene instance
		// set the layout container as root
		// set the background fill to the background color of the shell
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("Scene.css");

		// set the Scene to the FXCanvas
		canvas.setScene(scene);

	}

	@PreDestroy
	public void dispose() {
		System.out.println("Destroy resources");
	}
}
