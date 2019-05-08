package UI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import Controllers.GraphController;
import SearchAlgorithm.EscapeByTypeAndWeight;
import SearchAlgorithm.GreedyByLength;
import SearchAlgorithm.IntermediateNodesSearch;
import SearchAlgorithm.ShortestWaySearch;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GUIFrame extends JFrame {
	private GraphController controller = GraphController.getInstance();
//	private GraphController controller1 = GraphController.getInstance();
	private JPanel contentPane;
	private JTextField textFieldFrom;
	private JTextField textFieldTo;
	private JTextField textFieldNode1;
	private JTextField textFieldNode2;
	private JTextField textFieldType;
	private JTextField textFieldWeight;
	private JTextArea ContentTextArea;

	public GUIFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
	

		JPanel controllPanel = new JPanel();
		contentPane.add(controllPanel);
		controllPanel.setLayout(null);

		JLabel lblFrom = new JLabel("From :");
		lblFrom.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblFrom.setBounds(7, 7, 46, 20);
		controllPanel.add(lblFrom);

		textFieldFrom = new JTextField();
		textFieldFrom.setBounds(57, 7, 86, 20);
		controllPanel.add(textFieldFrom);
		textFieldFrom.setColumns(10);

		JLabel labelTo = new JLabel("To :");
		labelTo.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTo.setBounds(153, 7, 46, 20);
		controllPanel.add(labelTo);

		textFieldTo = new JTextField();
		textFieldTo.setBounds(194, 7, 86, 20);
		textFieldTo.setColumns(10);
		controllPanel.add(textFieldTo);

		JLabel lblNode = new JLabel("Node 1 :");
		lblNode.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblNode.setBounds(7, 58, 46, 14);
		controllPanel.add(lblNode);

		textFieldNode1 = new JTextField();
		textFieldNode1.setBounds(57, 55, 86, 20);
		controllPanel.add(textFieldNode1);
		textFieldNode1.setColumns(10);

		textFieldNode2 = new JTextField();
		textFieldNode2.setColumns(10);
		textFieldNode2.setBounds(194, 55, 86, 20);
		controllPanel.add(textFieldNode2);

		JLabel label = new JLabel("Node 2 :");
		label.setFont(new Font("Tahoma", Font.BOLD, 11));
		label.setBounds(146, 58, 46, 14);
		controllPanel.add(label);

		JButton btnIntermediateSearch = new JButton("Intermediate Search");
		btnIntermediateSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isPath = controller.search(textFieldFrom.getText(), textFieldTo.getText(),
						new IntermediateNodesSearch(controller.getGraph(), textFieldNode1.getText(),
								textFieldNode2.getText()));
				showPath(isPath);

			}
		});
		btnIntermediateSearch.setBounds(7, 83, 156, 23);
		controllPanel.add(btnIntermediateSearch);

		JLabel label_1 = new JLabel("Type :");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_1.setBounds(7, 117, 64, 21);
		controllPanel.add(label_1);

		JLabel label_2 = new JLabel("Weight :");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(7, 147, 80, 21);
		controllPanel.add(label_2);

		textFieldType = new JTextField();
		textFieldType.setColumns(10);
		textFieldType.setBounds(90, 117, 155, 20);
		controllPanel.add(textFieldType);

		textFieldWeight = new JTextField();
		textFieldWeight.setColumns(10);
		textFieldWeight.setBounds(90, 151, 155, 20);
		controllPanel.add(textFieldWeight);

		JButton btnEscapeSearch = new JButton("Escape Search");
		btnEscapeSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean isPath = controller.search(textFieldFrom.getText(), textFieldTo.getText(),
							new EscapeByTypeAndWeight(controller.getGraph(), textFieldType.getText(),
									Double.parseDouble(textFieldWeight.getText())));
					showPath(isPath);
				} catch (NumberFormatException ex) {
					ContentTextArea.setText("Incorrect input data !!!");
				}
			}
		});
		btnEscapeSearch.setBounds(7, 179, 156, 23);
		controllPanel.add(btnEscapeSearch);

		JButton btnShortPathSearch = new JButton("Short Path Search");
		btnShortPathSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isPath = controller.search(textFieldFrom.getText(), textFieldTo.getText(),
						new ShortestWaySearch(controller.getGraph()));
				showPath(isPath);
			}
		});
		btnShortPathSearch.setBounds(7, 213, 156, 23);
		controllPanel.add(btnShortPathSearch);

		JButton btnGreedySearch = new JButton("Greedy Search");
		btnGreedySearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isPath = controller.search(textFieldFrom.getText(), textFieldTo.getText(),
						new GreedyByLength(controller.getGraph()));
				showPath(isPath);
			}
		});
		btnGreedySearch.setBounds(7, 247, 156, 23);
		controllPanel.add(btnGreedySearch);

		JButton btnLoadGraph = new JButton("Load Graph");
		btnLoadGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(GUIFrame.this) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					controller.readingFile(file);
					controller.getGraph().resetGraph();

				}
			}
		});
		btnLoadGraph.setBounds(7, 299, 114, 23);
		controllPanel.add(btnLoadGraph);

		JButton btnSaveGraph = new JButton("Save Graph");
		btnSaveGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				if (fileChooser.showSaveDialog(GUIFrame.this) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					controller.saveInFile(file);
				}
			}
		});
		btnSaveGraph.setBounds(153, 299, 114, 23);
		controllPanel.add(btnSaveGraph);

		ContentTextArea = new JTextArea();
		JScrollPane sp = new JScrollPane(ContentTextArea);
		contentPane.add(sp);
	}

	private void showPath(boolean isPath) {
		if (isPath) {
			
			ContentTextArea.setText("");
			ContentTextArea.append("Have a path : ");
			ContentTextArea.append(controller.getGraph().getPathInforamtion() + "\n");
			ContentTextArea.append("Path is : ");
			for (int i = controller.getPathNodes().size() - 1; i >= 0; i--) {
				ContentTextArea.append(controller.getPathNodes().get(i).getName() + ",");
			}
			ContentTextArea.append("\n");
			ContentTextArea.append(controller.getGraph().getFullInformation());
			ContentTextArea.append("Expense is : "+controller.getGraph().getMap().get(textFieldTo.getText()).getExpense());
		   // ContentTextArea.append("\n");
		  //  ContentTextArea.append(controller1.getGraph().getFullInformation());
		   // ContentTextArea.append(""+controller1.getGraph().getMap().get(textFieldTo.getText()).getExpense());


		} else {
			ContentTextArea.setText("Don't have a path !!! ");

		}
	}
}
