package org.npkompleet.app4mc.viz;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.app4mc.amalthea.model.SWModel;
import org.eclipse.app4mc.visualization.ui.registry.Visualization;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.annotations.Component;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

@Component(property= {
		"id=SampleViz",
		"name=SWModel Visualization",
		"description=Some other SWModel visualization"
})
public class SampleViz implements Visualization {

	@PostConstruct
	public void createVisualization(SWModel model, Composite parent) {
		parent.setLayout(new GridLayout());
		
		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);
		
		GridDataFactory
		    .fillDefaults()
		    .grab(true, true)
		    .applyTo(canvas);
	
		// create the root layout pane
		BorderPane layout = new BorderPane();
		layout.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	
		// create a Scene instance
		// set the layout container as root
		// set the background fill to the background color of the shell
		Scene scene = new Scene(layout);
	
		// set the Scene to the FXCanvas
		canvas.setScene(scene);
		
		Label output = new Label();
		layout.setCenter(output);
		
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), output);
		rotateTransition.setByAngle(360);

		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), output);
		scaleTransition.setFromX(1.0);
		scaleTransition.setFromY(1.0);
		scaleTransition.setToX(4.0);
		scaleTransition.setToY(4.0);

		ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, scaleTransition);

//		output.setText(model.getClass().getSimpleName());
		output.setText("Visualization");
		parallelTransition.play();
	}
	
	@PreDestroy
	public void dispose() {
		System.out.println("Destroy resources");
	}
}
