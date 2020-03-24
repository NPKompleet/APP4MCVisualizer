package org.npkompleet.app4mc.viz.sampleviz;

import java.io.IOException;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.app4mc.amalthea.model.SWModel;
import org.eclipse.app4mc.visualization.ui.registry.Visualization;
import org.eclipse.gef.common.adapt.AdapterKey;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef.mvc.fx.domain.IDomain;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.zest.fx.ZestFxModule;
import org.eclipse.gef.zest.fx.ZestProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.component.annotations.Component;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.npkompleet.app4mc.viz.utils.DirectedEdge;

import javafx.application.Platform;
import javafx.embed.swt.FXCanvas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

@Component(
		property = {
				"id=SampleViz",
				"name=SWModel Visualization",
				"description=Some other SWModel visualization" })
public class SampleViz implements Visualization {

	private static int id = 0;
	protected static final String ID = ZestProperties.CSS_ID__NE;

	protected IDomain domain;
	protected IViewer viewer;

	@PostConstruct
	public void createVisualization(SWModel model, Composite parent) throws IOException {
		parent.setLayout(new GridLayout());

		FXCanvas canvas = new FXCanvas(parent, SWT.NONE);

		GridDataFactory.fillDefaults().grab(true, true).applyTo(canvas);

		// Load the view fxml file and set the title of the visualization
		ScrollPane layout = (ScrollPane) FXMLLoader.load(getClass().getResource("SampleVizView.fxml"));
		AnchorPane aPane = (AnchorPane) layout.getContent();
		Label label = (Label) aPane.getChildrenUnmodifiable().get(1);
		label.setText("A Sample Visualization");

		StackPane stckPane = (StackPane) aPane.getChildrenUnmodifiable().get(3);

		Graph graph = createGraph();
		Injector injector = Guice.createInjector(createModule());
		IDomain domain = injector.getInstance(IDomain.class);
		IViewer viewer = domain.getAdapter(AdapterKey.get(IViewer.class, IDomain.CONTENT_VIEWER_ROLE));

		stckPane.getChildren().add(viewer.getCanvas());

		// create a Scene instance
		// set the layout container as root
		// set the background fill to the background color of the shell
		Scene scene = new Scene(layout);
		scene.getStylesheets().add("Scene.css");

		// set the Scene to the FXCanvas
		canvas.setScene(scene);

		// Activate the domain
		domain.activate();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				viewer.getContents().setAll(Collections.singletonList(graph));
			}
		});

	}

	@PreDestroy
	public void dispose() {
		System.out.println("Destroy resources");
	}

	protected Graph createGraph() {

		Node n = n(ZestProperties.LABEL__NE, "Paper");
		Node n2 = n(ZestProperties.LABEL__NE, "Rock");
		Node n3 = n(ZestProperties.LABEL__NE, "Scissors");
		Node n4 = n(ZestProperties.LABEL__NE, "Lizard");
		Node n5 = n(ZestProperties.LABEL__NE, "Spock");
		Edge e12 = new DirectedEdge(n, n2);
		e12.getAttributes().put(ZestProperties.TARGET_DECORATION__E,
				new javafx.scene.shape.Polygon(0, 0, 10, 3, 10, -3));
		e12.getAttributes().put(ZestProperties.TARGET_DECORATION_CSS_STYLE__E, "-fx-fill: white;");
		Edge e23 = new DirectedEdge(n2, n3);
		Edge e31 = new DirectedEdge(n3, n);
		Edge e35 = new DirectedEdge(n3, n5);
		Edge e24 = new DirectedEdge(n2, n4);
		Edge e51 = new DirectedEdge(n5, n);
		return new Graph.Builder().nodes(n, n2, n3, n4, n5).edges(e12, e23, e31, e35, e24, e51)
				.attr(ZestProperties.LAYOUT_ALGORITHM__G,
						new SpringLayoutAlgorithm())
				.build();
	}

	protected static Node n(Object... attr) {
		org.eclipse.gef.graph.Node.Builder builder = new org.eclipse.gef.graph.Node.Builder();
		String id = genId();
		builder.attr(ID, id).attr(ZestProperties.LABEL__NE, id);
		for (int i = 0; i < attr.length; i += 2) {
			builder.attr(attr[i].toString(), attr[i + 1]);
		}
		return builder.buildNode();
	}

	protected static String genId() {
		return Integer.toString(id++);
	}

	protected Module createModule() {
		return new ZestFxModule();
	}

	protected Scene createScene(IViewer viewer) {
		return new Scene(viewer.getCanvas());
	}
}
