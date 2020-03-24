package com.npkompleet.app4mc.viz.utils;

import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.zest.fx.ZestProperties;

public class DirectedEdge extends Edge {

	public DirectedEdge(Node source, Node target) {
		super(source, target);
		super.getAttributes().put(ZestProperties.TARGET_DECORATION__E,
				new javafx.scene.shape.Polygon(0, 0, 10, 3, 10, -3));
		super.getAttributes().put(ZestProperties.TARGET_DECORATION_CSS_STYLE__E, "-fx-fill: white;");
	}

}
