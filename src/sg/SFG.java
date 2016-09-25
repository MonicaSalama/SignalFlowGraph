package sg;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Button;
import java.awt.Font;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;

import javax.swing.JPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JTextPane;
import javax.swing.JTextField;

import java.awt.TextField;

import javax.swing.UIManager;

import java.awt.Panel;

import javax.swing.DropMode;

import java.awt.SystemColor;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JList;

import java.awt.Window.Type;
import java.awt.ScrollPane;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollBar;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComboBox;

public class SFG {
	static Graph g = new Graph();
	static int action_Performed = 0;
	static String node = "";
	static String from_node = "";
	static String to_node = "";
	static double weight = 0;
	static String source_node = "";
	static String sink_node = "";
	private JFrame frmSignalFlowGraph;
	private final JPanel required = new JPanel();
	private final JTextField node_name = new JTextField();
	private final JTextField from = new JTextField();
	private final JTextField to = new JTextField();
	private final JButton edge_line = new JButton("  Edge Line  ");
	private final JButton edge_arc = new JButton("Edge Arc ");
	private final JButton clear = new JButton("   Clear   ");
	private final JButton solve = new JButton("    Solve    ");
	private final JTextField gain = new JTextField();
	private final JTextField source = new JTextField();
	private final JTextField sink = new JTextField();
	private final JButton new_node = new JButton("New Node");
	private final DrawingBoard draw = new DrawingBoard();
	private final JPanel Loops_Paths = new JPanel();
	private final JPanel t_f = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	static final JTextArea transfer_f = new JTextArea();
	private final JLabel lblNewLabel = new JLabel(" Node Name    :");
	private final JLabel lblNewLabel_1 = new JLabel("  From - To  - Gain :");
	private final JLabel lblSourceSink = new JLabel("  Source - Sink   :");
	private final JScrollPane scrollPane_1 = new JScrollPane();
	static final JTree tree = new JTree();

	// private final JScrollPane scroll = new JScrollPane(textArea_1);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SFG window = new SFG();
					window.frmSignalFlowGraph.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SFG() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSignalFlowGraph = new JFrame();
		frmSignalFlowGraph.setFont(new Font("DialogInput", Font.BOLD, 16));
		frmSignalFlowGraph.setTitle("Signal Flow Graph");
		frmSignalFlowGraph.setResizable(false);
		frmSignalFlowGraph.getContentPane().setBackground(new Color(0, 0, 0));
		frmSignalFlowGraph.setAlwaysOnTop(true);
		frmSignalFlowGraph.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
		frmSignalFlowGraph.getContentPane().setForeground(new Color(0, 0, 0));
		frmSignalFlowGraph.setForeground(new Color(0, 0, 0));
		frmSignalFlowGraph.setBackground(new Color(0, 0, 0));
		frmSignalFlowGraph.setBounds(100, 100, 450, 300);
		frmSignalFlowGraph.setSize(1276, 964);
		frmSignalFlowGraph.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSignalFlowGraph.getContentPane().setLayout(null);
		required.setLayout(null);
		required.setBorder(null);
		required.setBackground(new Color(173, 216, 230));
		required.setBounds(0, 0, 972, 170);

		frmSignalFlowGraph.getContentPane().add(required);
		node_name.setToolTipText("Node Name");
		node_name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				node_name.setText(null);
				node_name.setEditable(true);

			}

		});
		node_name.setDropMode(DropMode.INSERT);
		node_name.setColumns(1);
		node_name.setBackground(Color.WHITE);
		node_name.setBounds(257, 15, 185, 35);

		required.add(node_name);
		from.setToolTipText("from");
		from.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				from.setText("");
				from.setEditable(true);
			}
		});
		from.setColumns(1);
		from.setBackground(Color.WHITE);
		from.setBounds(256, 61, 50, 35);

		required.add(from);
		to.setToolTipText("to");
		to.setBackground(new Color(255, 255, 255));
		to.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				to.setText("");
				to.setEditable(true);
			}
		});
		to.setColumns(1);
		to.setBounds(329, 61, 50, 35);

		required.add(to);
		edge_line.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				from_node = from.getText();
				to_node = to.getText();
				try {
					weight = Double.parseDouble(gain.getText());
					if (from_node != null && from_node != null
							&& to_node != null
							&& draw.hash.containsKey(from_node)
							&& draw.hash.containsKey(to_node)&& !g.check(draw.hash.get(from_node), draw.hash.get(to_node))) {
						action_Performed = 3;

					} else {
						JOptionPane.showMessageDialog(frmSignalFlowGraph,
								"All cells are required .\nNode must exist.\nNo edge can be added twice.",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frmSignalFlowGraph,
							"Gain must be numeric", "Error",
							JOptionPane.INFORMATION_MESSAGE);

				}

			}
		});
		edge_line.setFont(new Font("Tahoma", Font.BOLD, 16));
		edge_line.setBackground(SystemColor.controlHighlight);
		edge_line.setBounds(481, 61, 137, 39);

		required.add(edge_line);
		edge_arc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				from_node = from.getText();
				to_node = to.getText();
				try {
					weight = Double.parseDouble(gain.getText());
					if (from_node != null && from_node != null
							&& to_node != null
							&& draw.hash.containsKey(from_node)
							&& draw.hash.containsKey(to_node)&& !g.check(draw.hash.get(from_node), draw.hash.get(to_node))) {
						action_Performed = 2;
					} else {
						JOptionPane.showMessageDialog(frmSignalFlowGraph,
								"All cells are required .\nNode must exist.\nNo edge can be added twice.",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frmSignalFlowGraph,
							"Gain must be numeric", "Error",
							JOptionPane.INFORMATION_MESSAGE);

				}
			}
		});
		edge_arc.setFont(new Font("Tahoma", Font.BOLD, 16));
		edge_arc.setBackground(SystemColor.controlHighlight);
		edge_arc.setBounds(650, 61, 137, 39);

		required.add(edge_arc);
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clear();
				g.clear();
				draw.clear();
			}
		});
		clear.setFont(new Font("Tahoma", Font.BOLD, 16));
		clear.setBackground(SystemColor.controlHighlight);
		clear.setBounds(835, 0, 137, 37);

		required.add(clear);
		solve.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				source_node = source.getText();
				sink_node = sink.getText();
				if(source_node.equals(sink_node)){
					JOptionPane.showMessageDialog(frmSignalFlowGraph,
							"Source and sink node can't be the same", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else if (draw.hash.containsKey(source_node)
						&& draw.hash.containsKey(sink_node)) {
					transfer_f.setText("Transfer Function :");
					transfer_f.append(""
							+ g.solve(draw.hash.get(source_node),
									draw.hash.get(sink_node), draw.size));
				}else{
					JOptionPane.showMessageDialog(frmSignalFlowGraph,
							"All cells are required.\nNodes must exsist in the graph", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		solve.setFont(new Font("Tahoma", Font.BOLD, 16));
		solve.setBackground(SystemColor.controlHighlight);
		solve.setBounds(481, 111, 137, 37);

		required.add(solve);
		gain.setToolTipText("gain");
		gain.setBackground(new Color(255, 255, 255));
		gain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				gain.setText("");
				gain.setEditable(true);
			}
		});
		gain.setBounds(392, 61, 50, 35);

		required.add(gain);
		source.setToolTipText("source node");

		source.setBackground(new Color(255, 255, 255));
		source.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				source.setText("");
				source.setEditable(true);
			}
		});
		source.setBounds(257, 114, 80, 35);

		required.add(source);
		sink.setToolTipText("sink node");
		sink.setBackground(new Color(255, 255, 255));
		sink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sink.setText("");
				sink.setEditable(true);
			}
		});
		sink.setBounds(353, 114, 89, 35);

		required.add(sink);
		new_node.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				node = node_name.getText();
				if (!node.equals("") && node != null
						&& !(draw.hash.containsKey(node))) {
					action_Performed = 1;
				} else {
					JOptionPane.showMessageDialog(frmSignalFlowGraph,
							"Already Exists , Invalid Name", "Add Node",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		new_node.setFont(new Font("Tahoma", Font.BOLD, 16));
		new_node.setBackground(SystemColor.controlHighlight);
		new_node.setBounds(481, 11, 137, 39);
		required.add(new_node);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(107, 15, 137, 35);

		required.add(lblNewLabel);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(70, 61, 176, 35);

		required.add(lblNewLabel_1);
		lblSourceSink.setForeground(new Color(0, 0, 0));
		lblSourceSink.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSourceSink.setBounds(92, 111, 155, 35);

		required.add(lblSourceSink);
		draw.setBounds(0, 171, 972, 604);

		frmSignalFlowGraph.getContentPane().add(draw);
		Loops_Paths.setBackground(new Color(173, 216, 230));
		Loops_Paths.setBounds(971, -1, 297, 775);

		frmSignalFlowGraph.getContentPane().add(Loops_Paths);
		Loops_Paths.setLayout(null);
		scrollPane_1.setEnabled(false);
		scrollPane_1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(0, 0, 297, 775);

		Loops_Paths.add(scrollPane_1);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode(
				"Solution details :") {
			{
			}
		}));
		tree.setVisibleRowCount(100);
		tree.setShowsRootHandles(true);
		tree.setSelectionRows(new int[] { 20 });
		tree.setSelectionRow(20);
		tree.setFont(new Font("Tahoma", Font.BOLD, 15));
		tree.setEditable(true);
		tree.setBackground(new Color(173, 216, 230));

		scrollPane_1.setViewportView(tree);
		t_f.setBackground(new Color(173, 216, 230));
		t_f.setBounds(0, 775, 1268, 141);

		frmSignalFlowGraph.getContentPane().add(t_f);
		t_f.setLayout(null);
		scrollPane.setEnabled(false);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 1268, 141);

		t_f.add(scrollPane);
		transfer_f.setText("Transfer Function :");
		transfer_f.setEditable(false);
		transfer_f.setBackground(new Color(173, 216, 230));
		transfer_f.setFont(new Font("Monospaced", Font.BOLD, 20));
		transfer_f.setWrapStyleWord(true);
		transfer_f.setLineWrap(true);

		scrollPane.setViewportView(transfer_f);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public void clear() {
		transfer_f.setText("Transfer Function :");
		node_name.setText("");
		from.setText("");
		to.setText("");
		gain.setText("");
		source.setText("");
		sink.setText("");
		action_Performed = 0;
		fun();
	}

	static public void fun() {
		node = "";
		from_node = "";
		to_node = "";
		source_node = "";
		sink_node = "";
	}
}
