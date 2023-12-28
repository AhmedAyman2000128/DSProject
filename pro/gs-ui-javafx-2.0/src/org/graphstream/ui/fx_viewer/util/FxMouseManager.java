/*
 * This file is part of GraphStream <http://graphstream-project.org>.
 * 
 * GraphStream is a library whose purpose is to handle static or dynamic
 * graph, create them from scratch, file or any source and display them.
 * 
 * This program is free software distributed under the terms of two licenses, the
 * CeCILL-C license that fits European law, and the GNU Lesser General Public
 * License. You can  use, modify and/ or redistribute the software under the terms
 * of the CeCILL-C license as circulated by CEA, CNRS and INRIA at the following
 * URL <http://www.cecill.info> or under the terms of the GNU LGPL as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C and LGPL licenses and that you accept their terms.
 */

 /**
  * @author Antoine Dutot <antoine.dutot@graphstream-project.org>
  * @author Guilhelm Savin <guilhelm.savin@graphstream-project.org>
  * @author Hicham Brahimi <hicham.brahimi@graphstream-project.org>
  */
  
package org.graphstream.ui.fx_viewer.util;

import java.util.EnumSet;

import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.InteractiveElement;
import org.graphstream.ui.view.util.MouseManager;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class FxMouseManager implements MouseManager
{	
	/**
	 * The view this manager operates upon.
	 */
	protected View view;

	/**
	 * The graph to modify according to the view actions.
	 */
	protected GraphicGraph graph;

	final private EnumSet<InteractiveElement> types;

	// Construction
	
	public FxMouseManager() {
		this(EnumSet.of(InteractiveElement.NODE,InteractiveElement.SPRITE));
	}
	
	public FxMouseManager(EnumSet<InteractiveElement> types) {
		this.types = types;
	}

	public void init(GraphicGraph graph, View view) {
		this.view = view;
		this.graph = graph;
		view.addListener(MouseEvent.MOUSE_PRESSED, mousePressed);
		view.addListener(MouseEvent.MOUSE_DRAGGED, mouseDragged);
		view.addListener(MouseEvent.MOUSE_RELEASED, mouseRelease);
	}
	
	// Command

	protected void mouseButtonPress(MouseEvent event) {
		view.requireFocus();
	
		// Unselect all.
		if (!event.isShiftDown()) {
			graph.nodes().filter(n -> n.hasAttribute("ui.selected")).forEach(n -> n.removeAttribute("ui.selected"));
			graph.sprites().filter(s -> s.hasAttribute("ui.selected")).forEach(s -> s.removeAttribute("ui.selected"));
		    graph.edges().filter(e -> e.hasAttribute("ui.selected")).forEach(e -> e.removeAttribute("ui.selected"));
		}
	}
	
	protected void mouseButtonRelease(MouseEvent event,
									  Iterable<GraphicElement> elementsInArea) {
		for (GraphicElement element : elementsInArea) {
			if (!element.hasAttribute("ui.selected"))
			element.setAttribute("ui.selected");
		}
	}
	
	protected void mouseButtonPressOnElement(GraphicElement element,
											 MouseEvent event) {
		view.freezeElement(element, true);
		if (event.getButton() == MouseButton.SECONDARY) {
			element.setAttribute("ui.selected");
		} else {
			element.setAttribute("ui.clicked");
		}
	}
	
	protected void elementMoving(GraphicElement element, MouseEvent event) {
		view.moveElementAtPx(element, event.getX(), event.getY());
	}
	
	protected void mouseButtonReleaseOffElement(GraphicElement element,
												MouseEvent event) {
		view.freezeElement(element, false);
		if (event.getButton() != MouseButton.SECONDARY) {
			element.removeAttribute("ui.clicked");
		}
	}
	
	// Mouse Listener

	protected GraphicElement curElement;

	protected double x1, y1;
	
	EventHandler<MouseEvent> mousePressed = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {			
			curElement = view.findGraphicElementAt(types, e.getX(), e.getY());

			if (curElement != null) {
				mouseButtonPressOnElement(curElement, e);
			} else {
				x1 = e.getX();
				y1 = e.getY();
				mouseButtonPress(e);
				view.beginSelectionAt(x1, y1);
			}
		}
	};
	
	EventHandler<MouseEvent> mouseDragged = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (curElement != null) {
				elementMoving(curElement, event);
			} else {
				view.selectionGrowsAt(event.getX(), event.getY());
			}
		}
	};	
	
	EventHandler<MouseEvent> mouseRelease = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (curElement != null) {
				mouseButtonReleaseOffElement(curElement, event);
				curElement = null;
			} else {
				double x2 = event.getX();
				double y2 = event.getY();
				double t;

				if (x1 > x2) {
					t = x1;
					x1 = x2;
					x2 = t;
				}
				if (y1 > y2) {
					t = y1;
					y1 = y2;
					y2 = t;
				}

				mouseButtonRelease(event, view.allGraphicElementsIn(types,x1, y1, x2, y2));
				view.endSelectionAt(x2, y2);
			}
		}
	};
	
	@Override
	public void release() {
		view.removeListener(MouseEvent.MOUSE_PRESSED, mousePressed);
		view.removeListener(MouseEvent.MOUSE_DRAGGED, mouseDragged);
		view.removeListener(MouseEvent.MOUSE_RELEASED, mouseRelease);
	}

	@Override
	public EnumSet<InteractiveElement> getManagedTypes() {
		// TODO Auto-generated method stub
		return types;
	}
}
