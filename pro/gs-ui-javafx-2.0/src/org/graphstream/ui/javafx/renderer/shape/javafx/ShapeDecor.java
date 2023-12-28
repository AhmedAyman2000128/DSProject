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
  
package org.graphstream.ui.javafx.renderer.shape.javafx;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.geom.Vector2;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.stylesheet.Style;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.TextMode;
import org.graphstream.ui.graphicGraph.stylesheet.StyleConstants.Units;
import org.graphstream.ui.javafx.Backend;
import org.graphstream.ui.view.camera.DefaultCamera2D;
import org.graphstream.ui.javafx.renderer.ConnectorSkeleton;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

public abstract class ShapeDecor {
	
	/** Generate a new icon and text specific to the given `element`, according to the given
	 *  `style` and `camera`. */
	public IconAndText iconAndText(Style style, DefaultCamera2D camera, GraphicElement element ) {
		return IconAndText.apply( style, camera, element);
	}
	
	public static ShapeDecor apply(Style style) {
		if( style.getTextMode() == TextMode.HIDDEN ) {
			return new EmptyShapeDecor();
		}
		else {
			switch (style.getTextAlignment()) {
				case CENTER:	return new CenteredShapeDecor();
				case LEFT:		return new LeftShapeDecor();
				case RIGHT:		return new RightShapeDecor();
				case AT_LEFT:	return new AtLeftShapeDecor();
				case AT_RIGHT:	return new AtRightShapeDecor();
				case UNDER:		return new UnderShapeDecor();
				case ABOVE:		return new AboveShapeDecor();
				case JUSTIFY:	return new CenteredShapeDecor();
				case ALONG:		return new AlongShapeDecor();
				default:		return null ;
			}
		}
	}
	
	/** Render the decoration inside the given box coordinates. The shape decoration contains all the metrics
	  * to render the `iconAndText` icon and text. The coordinates (`x0`, `y0`) and (`x1`, `y1`)
	  * indicates the lower-left and upper-right coordinates of the area where the decoration should be
	  * drawn. */
	public abstract void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0, double x1, double y1 );

	/** Render along the given line coordinates. The shape decoration contains all the metrics
	  * to render the `iconAndText` icon and text. The coordinates (`x0`, `y0`) and (`x1`, `y1`)
	  * indicates the start and end points of the line to draw the text on. */
	public abstract void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0, double x1, double y1 );
	public abstract void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, ConnectorSkeleton skel);
	
	/** Overall size (getWidth() and getHeight()) of the decoration, taking into account the `iconAndText` as
	 *  well as the various metrics specified by the style. */
	public abstract Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText);
}

/** A decor that does nothing. */
class EmptyShapeDecor extends ShapeDecor {
	public void renderInside(Backend b, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0, double x1, double y1) {}
	public void renderAlong(Backend b, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0, double x1, double y1) {}
	public void renderAlong(Backend b, DefaultCamera2D camera, IconAndText iconAndText, ConnectorSkeleton skel) {}
	public Tuple<Double, Double> size(Backend b, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

/** Base for shape decors that work in pixels units, not GU. */
abstract class PxShapeDecor extends ShapeDecor {
	/* We choose here to replace the transform (GU->PX) into a the identity to draw
	 * The text and icon. Is this the best way ? Maybe should we merely scale the 
	 * font size to render the text at the correct size ? How to handle the icon in
	 * this case ? 
	 */
	protected void renderGu2Px(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x, double y, double angle,
			FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionPx ) {
		GraphicsContext g  = backend.graphics2D();
		Point3 p  = camera.transformGuToPx( x, y, 0 );
		Affine Tx = g.getTransform();

		g.setTransform( new Affine() );

		p = positionPx.apply(backend, p, iconAndText, angle);
		iconAndText.render(backend, camera, p.x, p.y );
		g.setTransform( Tx );
	}
}

class CenteredShapeDecor extends PxShapeDecor {
	
	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x - ( iconAndText.getWidth() / 2 + 1 ) + iconAndText.padx;
		p.y = p.y + ( iconAndText.getHeight() / 2 ) - iconAndText.pady*2;
		return p;
	};
	
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x0 + (x1 - x0) / 2 ;
		double cy = y0 + (y1 - y0) / 2 ;

		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconPx) ;
	}
	
	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		Vector2 dir = new Vector2( x1-x0, y1-y0 );
		dir.scalarMult( 0.5f );
				
		renderGu2Px(backend, camera, iconAndText, x0 + dir.x(), y0 + dir.y(), 0, positionTextAndIconPx);	
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, ConnectorSkeleton skel) {
		Point3 p = skel.pointOnShape(0.5);
		renderGu2Px(backend, camera, iconAndText, p.x, p.y, 0, positionTextAndIconPx);
	}

	@Override
	public Tuple<Double, Double> size(Backend b, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>( 
				camera.getMetrics().lengthToGu( iconAndText.getWidth(), Units.PX ),
				camera.getMetrics().lengthToGu( iconAndText.getHeight(), Units.PX ) );
	}
	
}

class AtLeftShapeDecor extends PxShapeDecor {
	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x - ( iconAndText.getWidth() + 2 ) + iconAndText.padx;
		p.y = p.y + ( iconAndText.getHeight() / 2 ) - iconAndText.pady;
		return p;
	};
	
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x0 ;
		double cy = y0 + (y1 -y0) / 2;
		
		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x0;
		double cy = y0;
				
		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconPx );	
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText,
			ConnectorSkeleton skel) {
		renderAlong(backend, camera, iconAndText, skel.from().x, skel.from().y, skel.to().x, skel.to().y);		
	}

	@Override
	public Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

class AtRightShapeDecor extends PxShapeDecor {

	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x + iconAndText.padx;
		p.y = p.y + ( iconAndText.getHeight() / 2 ) - iconAndText.pady;
		return p;
	};
	
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x1;
		double cy = y0 + ( y1 - y0 ) / 2;

		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x1;
		double cy = y1;
				
		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText,
			ConnectorSkeleton skel) {
		renderAlong(backend, camera, iconAndText, skel.from().x, skel.from().y, skel.to().x, skel.to().y);		
	}

	@Override
	public Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

class LeftShapeDecor extends PxShapeDecor {
	
	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconAreaPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x - ( iconAndText.getWidth() + 2 ) + iconAndText.padx;
		p.y = p.y + ( iconAndText.getHeight() / 2 ) - iconAndText.pady;
		return p;
	};
	
	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconAlongPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x + iconAndText.padx;
		p.y = p.y + ( iconAndText.getHeight() / 2 ) - iconAndText.pady;
		return p;
	};
	
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x0 + ( x1 - x0 ) / 2;
		double cy = y0 + ( y1 - y0 ) / 2;

		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconAreaPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		renderGu2Px(backend, camera, iconAndText, x0, y0,  0, positionTextAndIconAlongPx );		
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText,
			ConnectorSkeleton skel) {
		renderAlong(backend, camera, iconAndText, skel.from().x, skel.from().y, skel.to().x, skel.to().y);		
	}

	@Override
	public Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

class RightShapeDecor extends PxShapeDecor {
	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconAreaPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x + iconAndText.padx;
		p.y = p.y + ( iconAndText.getHeight() / 2 ) - iconAndText.pady;
		return p;
	};
	
	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconAlongPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x - ( iconAndText.getWidth() + 2 + iconAndText.padx );
		p.y = p.y + ( iconAndText.getHeight() / 2 ) - iconAndText.pady;
		return p;
	};
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x0 + ( x1 - x0 ) / 2;
		double cy = y0 + ( y1 - y0 ) / 2;

		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconAreaPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		renderGu2Px(backend, camera, iconAndText, x1, y1, 0, positionTextAndIconAlongPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText,
			ConnectorSkeleton skel) {
		renderAlong(backend, camera, iconAndText, skel.from().x, skel.from().y, skel.to().x, skel.to().y);
	}

	@Override
	public Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

class UnderShapeDecor extends PxShapeDecor {

	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x - ( iconAndText.getWidth() / 2 + 1 ) + iconAndText.padx;
		p.y = p.y + ( iconAndText.getHeight() ) - iconAndText.pady;
		return p;
	};
	
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x0 + ( x1 - x0 ) / 2;
		double cy = y0;

		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		Vector2 dir = new Vector2( x1-x0, y1-y0 );
		dir.scalarMult( 0.5f );

		renderGu2Px(backend, camera, iconAndText, x0+dir.x(), y0+dir.y(), 0, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText,
			ConnectorSkeleton skel) {
		renderAlong(backend, camera, iconAndText, skel.from().x, skel.from().y, skel.to().x, skel.to().y);
	}

	@Override
	public Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

class AboveShapeDecor extends PxShapeDecor {
	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconPx = (backend, p, iconAndText, angle) -> {
		p.x = p.x - ( iconAndText.getWidth() / 2 + 1 ) + iconAndText.padx;
		p.y = p.y - iconAndText.pady;
		return p;
	};
	
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		double cx = x0 + ( x1 - x0 ) / 2;
		double cy = y1;

		renderGu2Px(backend, camera, iconAndText, cx, cy, 0, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		Vector2 dir = new Vector2( x1-x0, y1-y0 );
		dir.scalarMult( 0.5f );

		renderGu2Px(backend, camera, iconAndText, x0+dir.x(), y0+dir.y(), 0, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText,
			ConnectorSkeleton skel) {
		renderAlong(backend, camera, iconAndText, skel.from().x, skel.from().y, skel.to().x, skel.to().y);
	}

	@Override
	public Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

class AlongShapeDecor extends PxShapeDecor {

	FunctionIn<Backend, Point3, IconAndText, Double, Point3> positionTextAndIconPx = (backend, p, iconAndText, angle) -> {
		 GraphicsContext g = backend.graphics2D();
		 g.translate( p.x, p.y );
		 g.rotate( angle );
		 g.translate( -iconAndText.getWidth()/2, +iconAndText.getHeight()/2 );
		 return new Point3( 0, 0, 0 );
	};
	
	@Override
	public void renderInside(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText, double x0, double y0,
			double x1, double y1) {
		Vector2 dir = new Vector2( x1-x0, y1-y0 );
		dir.scalarMult( 0.5f );
		double cx = x0 + dir.x();
		double cy = y0 + dir.y();
		dir.normalize();
		double angle = Math.acos( dir.dotProduct( 1, 0 ) );
		
		if( dir.y() > 0 )			// The angle is always computed for acute angles
			angle = ( Math.PI - angle );
		
		if( angle > Math.PI/2 ) 
			angle = ( Math.PI + angle );
		
		renderGu2Px(backend, camera, iconAndText, cx, cy, angle, positionTextAndIconPx );
	}

	@Override
	public void renderAlong(Backend backend, DefaultCamera2D camera, IconAndText iconAndText,
			ConnectorSkeleton skel) {
		renderAlong(backend, camera, iconAndText, skel.from().x, skel.from().y, skel.to().x, skel.to().y);		
	}

	@Override
	public Tuple<Double, Double> size(Backend backend, DefaultCamera2D camera, IconAndText iconAndText) {
		return new Tuple<Double, Double>(0.0, 0.0);
	}
	
}

/******************************* UTIL *******************************/

@FunctionalInterface
interface FunctionIn<A,B,C,D, Z> {
	
	/**
	 * Applies this function to the given arguments.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 * @param c the third function argument
	 * @param d the fourth function argument
	 * @return the function result
	 */
	Z apply(A a, B b, C c, D d);
}

class Tuple<X, Y> { 
	public final X val1; 
	public final Y val2; 
	
	public Tuple(X x, Y y) { 
		this.val1 = x; 
		this.val2 = y; 
	} 
	
	@Override
	public String toString() {
		return "["+val1+","+val2+"]";
	}
}
