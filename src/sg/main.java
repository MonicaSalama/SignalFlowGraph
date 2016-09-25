package sg;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g = new Graph();
		DrawingBoard.st.add("y1");
		DrawingBoard.st.add("y2");
		DrawingBoard.st.add("y3");
		DrawingBoard.st.add("y4");
		DrawingBoard.st.add("y5");
		DrawingBoard.st.add("y6");
		DrawingBoard.st.add("y7");
		DrawingBoard.st.add("y8");
		g.addEdge(0, 1, 1);
		g.addEdge(1, 2, 1);
		g.addEdge(2, 3, 2);
		g.addEdge(3, 4, 3);
		g.addEdge(4, 5, 4);
		g.addEdge(5, 6, 5);
		g.addEdge(6, 7, 6);
		g.addEdge(7, 1, -3);
		g.addEdge(7, 5, -1);
		g.addEdge(6, 2, -2);
		g.addEdge(5, 4, -4);
		g.addEdge(3, 6, 7);
		g.addEdge(5, 7, 8);
		System.out.println("Transfer Function  :"+g.solve(0, 7, 8));
		System.out.println("----------------");
		for (int i = 0; i < g.loops.size(); i++) {
			System.out.println( "loop : "+g.loops.get(i).s+" gain: "+g.loops.get(i).gain);
			
		}
		System.out.println("-----------------");
		for (int i = 0; i < g.path.size(); i++) {
			System.out.println("Path : "+g.path.get(i).path +"gain : "+g.path.get(i).M +" delta : "+g.path.get(i).delta);
			System.out.println(g.path.get(i).non_touch);
			System.out.println();
		}
		System.out.println("-----------------");
		String temp = g.n_delta;
		System.out.println(g.n_delta);
		String[]z = temp.split("\\*");
		for (int i = 0; i < z.length; i++) {
			String[]y = z[i].split("\\+");
			System.out.println((i)+"   :");
			System.out.println("---------");
			for (int j = 0; j < y.length; j++) {
				if (!y[j].equals("")) {
					System.out.println(y[j]);
				}
			}
		}


	}

}
