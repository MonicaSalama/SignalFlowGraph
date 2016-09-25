package sg;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.omg.CORBA.ARG_IN;

public class Graph {
	HashSet<String>set = new HashSet<String>();
	ArrayList<Edge>[]edges;
	ArrayList<LOOP>loops;
	ArrayList<Path> path;
	HashSet<Integer>test ;
	int numNodes;
	boolean[]visited;
	int max = 100;
	boolean flag = false;
	int destination;
	double delta = 1;
	String non_t ="";
	String n_delta;
	Graph(){
		test = new HashSet<Integer>();
		loops = new ArrayList<LOOP>();
		path = new ArrayList<Path>();
		edges = new ArrayList[max];
		for (int i = 0; i < edges.length; i++) {
			edges[i] =  new ArrayList<Edge>();
		}
	}
	boolean check(int fromNode , int toNode){
		String s = fromNode+" "+toNode;
		return set.contains(s);
	}
	public void addEdge(int from , int to , double w){
		flag = false;
		Edge t = new Edge(to , w);
		edges[from].add(t);
		set.add(from+" "+to);
	}
		
	
	public double solve(int source , int sink , int size){
		path.clear();
		numNodes = size;
		visited = new boolean[numNodes];
		visited[source] = true;
		destination = sink;
		dfs(source, DrawingBoard.st.get(source), 1.0 ,(1<<source));
		System.out.println("Paths :");
		for (int i = 0; i < path.size(); i++) {
			System.out.println(path.get(i).path+"    "+path.get(i).M);
		}
		System.out.println();
		if(!flag){
			flag = true;
			test.clear();
			loops.clear();
			getLoops();
			delta = 1;
			boolean operation = false;
			ArrayList<LOOP> current = new ArrayList<LOOP>();
			double d = 1;
			int non = 1;
			non_t ="";
			while(d != 0){
				d = 0;
				d = non_touching_loops(non, 1, 0, current, loops , "");
				if(!operation){
					operation = true;
					delta -= d;
				}else{
					operation = false;
					delta += d;
				}
				non_t += "*";
				non++;
			}
			n_delta = non_t;
			non_t ="";
		}
		System.out.println("Loops :");
		for (int i = 0; i < loops.size(); i++) {
			System.out.println(loops.get(i).s+"    "+loops.get(i).gain);
			int temp = loops.get(i).loop;
		}
		System.out.println();	
		handle_path();
		jtree();
		return cal();
	}
	public double cal(){
		double value = 0;
		for (int i = 0; i < path.size(); i++) {
			value += path.get(i).M * path.get(i).delta;
		}
		value /= delta;
		System.out.println("delta : "+delta);
		return value;
	}
	public void dfs(int node , String p , double m , int path_n){
		if(node == destination){
			Path P = new Path(p , m , 0 ,path_n);
			path.add(P);
			return;
		}
		for (int i = 0; i < edges[node].size(); i++) {
			int temp = edges[node].get(i).toNode;
			if(!visited[temp]){
				visited[temp] = true;
				double t = edges[node].get(i).w;
				path_n = path_n|(1<<temp);
				dfs(temp,p+"-"+DrawingBoard.st.get(temp),m*t,path_n);
				path_n = path_n & (~(1<<temp));
				visited[temp] = false;
			}
		}
	}
	public double non_touching_loops(int n ,double m,int c ,ArrayList<LOOP>current , ArrayList<LOOP> loop , String str){
		double d = 0;
		if(current.size() == n){
			non_t = non_t+"+"+str;
			return m;
		}
		for (int i = c; i < loop.size(); i++){
			boolean flag = true;
			int temp = loop.get(i).loop;
			for (int j = 0; j < current.size(); j++) {
				int temp1 = current.get(j).loop;
				if((temp1 & temp) != 0){
					flag = false;
					break;
				}
			}
			if(flag){
				double t = loop.get(i).gain;
				int s = current.size();
				current.add(loop.get(i));
				d += non_touching_loops(n, m*t, i+1, current, loop , str+"("+loop.get(i).s+")" );
				current.remove(s);
				
			}
		}
		return d;
		
		
	}
	public void getLoops(){
		for (int i = 0; i < numNodes; i++) {
			visited = new boolean[numNodes];
			String s =""+DrawingBoard.st.get(i);
			loop(i ,i ,s , 0 , 1);
		}
		
	}
	public void loop(int source , int node ,String s ,int loop , double m){
		if(visited[source] &&  source == node ){
			if (!test.contains(loop)) {
				test.add(loop);
				LOOP l = new LOOP(s, loop, m);
				loops.add(l);
			}
			return;
		}
		for (int i = 0; i < edges[node].size(); i++) {
			int temp = edges[node].get(i).toNode;
			if(!visited[temp]){
				visited[temp] = true;
				double t = edges[node].get(i).w;
				m *= t;
				loop = loop|(1<<temp);
				loop(source,temp,(s+"-"+DrawingBoard.st.get(temp)), loop, m);
				visited[temp] = false;
				loop = loop & (~(1<<temp));
				m /=t;
			}
		}
	}
	public void handle_path(){
		ArrayList<LOOP> temp;
		ArrayList<LOOP> current = new ArrayList<LOOP>();
		for (int i = 0; i < path.size(); i++) {
			temp = path_loop(i);
			boolean operation = false;
			double d = 1;
			int non = 1;
			double temp_delta  = 1;
			non_t ="";
			while(d != 0){
				d = 0;
				d = non_touching_loops(non, 1, 0, current, temp,"");
				if(!operation){
					operation = true;
					temp_delta -= d;
				}else{
					operation = false;
					temp_delta += d;
				}
				non_t += "*";
				non++;
			}
			path.get(i).delta = temp_delta;	
			path.get(i).non_touch = non_t;
		}
	}
	
    public ArrayList<LOOP> path_loop(int path_index){
    	int temp = path.get(path_index).path_nodes;
    	ArrayList<LOOP>path_loop =  new ArrayList<LOOP>();
    	for (int i = 0; i < loops.size(); i++) {
			int t = loops.get(i).loop;
			if((temp & t) == 0){
				path_loop.add(loops.get(i));
			}
		}
    	return path_loop;
    }
    public void clear(){
    	set.clear();
    	path.clear();
    	loops.clear();
    	test.clear();
        flag = false;
    	delta = 1;
    	non_t ="";
    	String n_delta = "";
    	for (int i = 0; i < edges.length; i++) {
			edges[i].clear();
		}
    	SFG.tree.setModel(new DefaultTreeModel(
    			new DefaultMutableTreeNode("Solution details :") {
    				{
    				}
    			}
    	));
    	
    }
    public void jtree(){
    	SFG.tree.setModel(new DefaultTreeModel(
    			new DefaultMutableTreeNode("Solution details :") {
    				{
    				}
    			}
    		));
    	SFG.tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Solution details :") {
    				{
    					DefaultMutableTreeNode node_1;
    					DefaultMutableTreeNode node_2;
    					DefaultMutableTreeNode node_3;
    					DefaultMutableTreeNode node_4;
    					node_1 = new DefaultMutableTreeNode("Path:");
    					for (int i = 0; i <path.size(); i++) {
    						Path temp = path.get(i);
    						node_2 = new DefaultMutableTreeNode("Path "+(i+1)+":");
    						node_3 = new DefaultMutableTreeNode("Path:");
    							node_3.add(new DefaultMutableTreeNode(temp.path));
    						node_2.add(node_3);
    						node_3 = new DefaultMutableTreeNode("Gain:");
    							node_3.add(new DefaultMutableTreeNode(temp.M));
    						node_2.add(node_3);
    						node_3 = new DefaultMutableTreeNode("Individual loops");
    						boolean f = false;
    						String d = temp.non_touch;
    						String[]z = d.split("\\*");
    						for (int k = 0; k < z.length; k++) {
    							String[]y = z[k].split("\\+");
    							node_4 = new DefaultMutableTreeNode((k+1)+" individual loop");
    							for (int j = 0; j < y.length; j++) {
    								if (!y[j].equals("")) {
    									f = true;
										node_4.add(new DefaultMutableTreeNode(
												"	" + y[j]));
									}
    							}
    							node_3.add(node_4);
    						}
    						if(f){
    							node_2.add(node_3);
    						}
    						node_3 = new DefaultMutableTreeNode("Delta:");
    							node_3.add(new DefaultMutableTreeNode(temp.delta));
    						node_2.add(node_3);
    					node_1.add(node_2);
    					}
    					add(node_1);
    					node_1 = new DefaultMutableTreeNode("Loops:");
    					for (int i = 0; i < loops.size(); i++) {
    						LOOP temp =loops.get(i);
    						node_2 = new DefaultMutableTreeNode("Loop "+(i+1)+":");
    						node_3 = new DefaultMutableTreeNode("Loop:");
    							node_3.add(new DefaultMutableTreeNode(temp.s));
    						node_2.add(node_3);
    						node_3 = new DefaultMutableTreeNode("Gain:");
    							node_3.add(new DefaultMutableTreeNode(temp.gain));
    						node_2.add(node_3);
    					node_1.add(node_2);
    					}
    					node_1 = new DefaultMutableTreeNode("Individual loops");
    					node_2 = new DefaultMutableTreeNode("Delta");
    					node_2.add(new DefaultMutableTreeNode(delta+""));
    					node_1.add(node_2);
						String d = n_delta;
						String[]z = d.split("\\*");
						boolean f = false;
						for (int k = 0; k < z.length; k++) {
							String[]y = z[k].split("\\+");
							node_2 = new DefaultMutableTreeNode((k+1)+" Non touching");
							for (int j = 0; j < y.length; j++) {
								if (!y[j].equals("")) {
									f = true;
									node_2.add(new DefaultMutableTreeNode(
											"	" + y[j]));
								}
							}
							if (f) {
								node_1.add(node_2);
							}
						}
    					add(node_1);
    				}
    			}
    		));
    }
}

class LOOP{
	String s;
	int loop;
	double gain;
	LOOP(String s ,int loop , double gain){
		this.s = s;
		this.loop = loop;
		this.gain = gain;
	}
}
class Edge{
    int toNode;
    double w;
    Edge(int to , double w) {
		this.toNode = to;
		this.w = w;
	}
}
class Path{
	String non_touch;
	int path_nodes;
	String path;
	double M;
	double delta;
	Path(String p , double gain ,double d , int path_n) {
		this.path = p;
		this.M = gain;
		this.delta = d;
		this.path_nodes = path_n;
	}
	
}
