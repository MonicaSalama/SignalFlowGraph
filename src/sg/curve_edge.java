package sg;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;

class curve_edge {
	Point startDrag =  new Point();
	Point endDrag = new Point();
	Double w;
	boolean self_Loop = false;
	public curve_edge(Point s , Point e , Double c) {
		startDrag = s;
		endDrag = e;
		w = c;
	}
	curve_edge() {}
	  public void paint(Graphics g) {	
	      Graphics2D g2 = (Graphics2D) g;
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      g2.setStroke(new BasicStroke(3));
	      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
	      if (startDrag != null && endDrag != null) {
	    	  double x7 = 0, y7 = 0, theta = 0;
	    	  GeneralPath p = new GeneralPath();
	  			p.moveTo(startDrag.x,startDrag.y);
	  			int z = (Math.abs(endDrag.x - startDrag.x))/2;
	  		// NORMAL
	  		if (!self_Loop) {
	  			theta = Math.atan2(endDrag.y - startDrag.y, endDrag.x - startDrag.x);
	  			double x3 = (startDrag.x + endDrag.x) / 2;
	  			double y3 = (startDrag.y + endDrag.y) / 2;
	  			double x4 = x3 + z * 1 * Math.sin(theta);
	  			double y4 = y3 - z * 1 * Math.cos(theta);
	  			double x5 = (x4 + startDrag.x) / 2;
	  			double y5 = (y4 + startDrag.y) / 2;
	  			double x6 = (x4 + endDrag.x) / 2;
	  			double y6 = (y4 + endDrag.y) / 2;
	  			x7 = (x5 + x6) / 2;
	  			y7 = (y5 + y6) / 2;		
	  			p.quadTo(x4, y4, endDrag.x,endDrag.y);
	  		}
	  		// SELF LOOP
	  		else {
	  			int y = 32;
	  			p.curveTo(startDrag.x - y, startDrag.y - y, startDrag.x + y, startDrag.y - y, startDrag.x, startDrag.y);
	  			x7 = startDrag.x + 4;
	  			y7 = startDrag.y - 24;
	  		}
	  		((Graphics2D) g).draw(p);
	  		if (!self_Loop) {
				triangle((Graphics2D) g, x7, y7, theta, 0.5, 20);
			}else{
				triangle((Graphics2D) g, x7, y7, theta, 0.5, 8);
			}
			g2.setPaint(Color.yellow);
	  		g2.drawString(w+"", (int)x7 , (int)y7 - 5);
	  		g2.setPaint(Color.MAGENTA);
	      }
	    }
	 public  void triangle(Graphics2D g, double x, double y, double theta, double epsilon, double r) {
		    g.setPaint(Color.red);
			GeneralPath p = new GeneralPath();
			p.moveTo(x, y);
			p.lineTo(x - r * Math.cos(theta + epsilon), y - r * Math.sin(theta + epsilon));
			p.lineTo(x - r * Math.cos(theta - epsilon), y - r * Math.sin(theta - epsilon));
			g.fill(p);
		}

}
