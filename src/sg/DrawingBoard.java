package sg;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class DrawingBoard extends JComponent {
	static int r = 30;
	int size = 0;
	ArrayList<Ellipse2D.Float> shapes = new ArrayList<Ellipse2D.Float>();
	static ArrayList<String> st = new ArrayList<String>();
	ArrayList<curve_edge> curves = new ArrayList<curve_edge>();
	ArrayList<Line2D.Float> lines = new ArrayList<Line2D.Float>();
	ArrayList<Double> wt = new ArrayList<Double>();
    static Hashtable<String , Integer> hash = new Hashtable<String , Integer>();
	Ellipse2D.Float temp = new Ellipse2D.Float();
	Point curePoint;
	curve_edge d = new curve_edge();
	Line2D.Float line = new Line2D.Float();
	Point start_point;
	boolean flag = false;
	boolean flag2 = false;
	boolean line_flag = false;
	boolean line_flag_drag = false;
	double s;
	int hash_fromNode;
	public void clear(){
		size = 0;
		shapes.clear();
		st.clear();
		curves.clear();
		lines.clear();
		wt.clear();
	    hash.clear();
		flag = false;
	    flag2 = false;
		line_flag = false;
		line_flag_drag = false;
		repaint();
		
	}
	public DrawingBoard() {
		this.setBounds(0, 171, 972, 604);
		temp.height = r;
		temp.width = r;
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				curePoint = e.getPoint();
				if(SFG.action_Performed == 1 && curePoint.x<= 938 && curePoint.x >= 2 && curePoint.y <= 572 && curePoint.y >=3){
					temp.x = curePoint.x;
					temp.y = curePoint.y;
					repaint();
				}
			}
		});
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				flag = false;
				Point temp_c = new Point(e.getX(),e.getY());
				if (SFG.action_Performed == 1 && temp_c.x<= 938 && temp_c.x >= 2 && temp_c.y <= 572 && temp_c.y >=3) {
					Ellipse2D.Float t = new Ellipse2D.Float(temp_c.x, temp_c.y,
							r, r);
					hash.put(SFG.node, size);
					st.add(SFG.node);
					shapes.add(t);
					size++;
					SFG.action_Performed = 0;
				}
				repaint();
				
			}
		});
		this.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	        	if(SFG.action_Performed == 2){
	        		flag = false;
	        		flag2 = false;
					hash_fromNode = hash.get(SFG.from_node);
					curePoint = e.getPoint();
					if(shapes.get(hash_fromNode).contains(curePoint)){
						flag = true;
						d.startDrag = curePoint;
						d.endDrag = curePoint;
						d.w = SFG.weight;
						if(SFG.to_node.equals(SFG.from_node)){
							d.self_Loop = true;
						}
						else{
							d.self_Loop = false;
						}
					}
				}else if(SFG.action_Performed == 3){
					line_flag = false;
					line_flag_drag = false;
					int index = hash.get(SFG.from_node);
					hash_fromNode = hash.get(SFG.from_node);
					curePoint = e.getPoint();
					if(shapes.get(index).contains(curePoint)){
						line_flag = true;
						line.x1 = curePoint.x;
						line.x2 = line.x1;
						line.y1 = curePoint.y;
						line.y2 = line.y1;
						s = SFG.weight;
					}
				}
	          repaint();
	        }
	       });
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent arg0) {
				if(flag2 && flag && SFG.action_Performed == 2){
					SFG.action_Performed = 0;
					flag = false;
					flag2 = false;
				    d.endDrag = arg0.getPoint();
				    int hash_value = hash.get(SFG.to_node);
				    if(shapes.get(hash_value).contains(d.endDrag)){
				    	curve_edge e = new curve_edge(d.startDrag , d.endDrag , SFG.weight);
				    	if(SFG.to_node.equals(SFG.from_node)){
				    		e.self_Loop = true;
				    	}
				    	curves.add(e);
				    	SFG.g.addEdge(hash_fromNode, hash_value, SFG.weight);
				    }
				   
				}else if(line_flag && line_flag_drag && SFG.action_Performed == 3){
					SFG.action_Performed = 0;
					line_flag = false;
                    line_flag_drag= false;
				    curePoint= arg0.getPoint();
				    int hash_value = hash.get(SFG.to_node);
				    if(shapes.get(hash_value).contains(curePoint)){
				    	lines.add(new Line2D.Float(line.x1, line.y1, curePoint.x, curePoint.y));
				    	wt.add(SFG.weight);
				    	SFG.g.addEdge(hash_fromNode, hash_value, SFG.weight);
				    }
				   
				}
				 repaint();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if(flag && SFG.action_Performed == 2){
					d.endDrag = e.getPoint();
					flag2 = true;
				}else if(line_flag && SFG.action_Performed == 3){
					curePoint = e.getPoint();
					line_flag_drag = true;
					line.x2 = curePoint.x;
					line.y2 = curePoint.y;
				}
				repaint();
			}
		});
	}
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        g2.setPaint(Color.MAGENTA);
        for (int i = 0; i < shapes.size(); i++) {
        	g2.draw(shapes.get(i));
        	g2.fill(shapes.get(i));
        	g2.setPaint(Color.CYAN);
        	g2.drawString(st.get(i), (int)shapes.get(i).getCenterX()-3,(int) shapes.get(i).getCenterY()+5);
        	g2.setPaint(Color.MAGENTA);
		}
        for (int i = 0; i < curves.size(); i++) {
        	g2.setPaint(Color.blue);
			curves.get(i).paint(g2);
		}
        for (int i = 0; i < lines.size(); i++) {
          g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
          g2.setPaint(Color.blue);
          g2.draw(lines.get(i));
		  drawArrow((int)lines.get(i).x2, (int)lines.get(i).x1,(int)lines.get(i).y2, (int)lines.get(i).y1, g2 , i);
		}
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
        if (SFG.action_Performed == 1) {
			g2.setPaint(Color.red);
			g2.draw(temp);
			g2.setPaint(Color.red);
			g2.fill(temp);
		}else if(SFG.action_Performed == 2 && flag && flag2){
			g2.setPaint(Color.red);
			d.paint(g2);
		}else if(SFG.action_Performed == 3 && line_flag && line_flag_drag){
			g2.setPaint(Color.red);
			g2.draw(line);
		}
		
	}
	private  void drawArrow(int tipX, int tailX, int tipY, int tailY, Graphics2D g , int i)
	{
		int x1 = ((tipX - tailX)/2) + tailX ;
		int y1 = ((tipY - tailY)/2) + tailY;
		tipX = x1;
		tipY = y1;
	    int arrowLength = 20; //can be adjusted
	    int dx = tipX - tailX;
	    int dy = tipY - tailY;
	    g.setPaint(Color.yellow);
	    g.drawString(wt.get(i)+"", tipX-3,tipY+3);
	    g.setPaint(Color.red);
        
	    double theta = Math.atan2(dy, dx);

	    double rad = Math.toRadians(35); //35 angle, can be adjusted
	    double x = tipX - arrowLength * Math.cos(theta + rad);
	    double y = tipY - arrowLength * Math.sin(theta + rad);

	    double phi2 = Math.toRadians(-35);//-35 angle, can be adjusted
	    double x2 = tipX - arrowLength * Math.cos(theta + phi2);
	    double y2 = tipY - arrowLength * Math.sin(theta + phi2);

	    int[] arrowYs = new int[3];
	    arrowYs[0] = tipY;
	    arrowYs[1] = (int) y;
	    arrowYs[2] = (int) y2;

	    int[] arrowXs = new int[3];
	    arrowXs[0] = tipX;
	    arrowXs[1] = (int) x;
	    arrowXs[2] = (int) x2;

	    g.fillPolygon(arrowXs, arrowYs, 3);
	}
	

}
