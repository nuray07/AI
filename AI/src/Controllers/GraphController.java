package Controllers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import SearchAlgorithm.EscapeByTypeAndWeight;
import SearchAlgorithm.ISearch;
import SearchAlgorithm.IntermediateNodesSearch;
import uni.Graph;
import uni.Link;
import uni.Node;

public class GraphController {
	Graph graph;

	private static GraphController graphController = null;
	

	private GraphController() {
		graph = new Graph();

	}

	public static GraphController getInstance() {
		if (graphController == null) {
			graphController = new GraphController();
		}

		return graphController;
	}

	public boolean search(String from, String to, ISearch algorithm) {
	

		return graph.searchPath(from, to, algorithm);
	}

	

	public void clearGraph() {
		graph.getMap().clear();
	}

	public void addNodeInGraph(String name, int x, int y, double weight) {
		if (graph.getMap().containsKey(name)) {
			Node node = graph.getMap().get(name);
			node.setX(x);
			node.setY(y);
			node.setWeight(weight);
		} else {
			Node node = new Node(name, weight, x, y);
			graph.addNode(node);
		}

	}


	public boolean addRouteInGraph(String from, String to, double lenght, String type, boolean isTwoWay) {
		if (!graph.getMap().containsKey(from) || !graph.getMap().containsKey(to)) {
			return false;
		}
		if (isTwoWay) {
			graph.addTwoWayRoute(graph.getMap().get(from), graph.getMap().get(to), lenght, type);
		} else {
			graph.addRoute(graph.getMap().get(from), graph.getMap().get(to), lenght, type);
		}

		return true;
	}

	public ArrayList<String> getNodeNames() {
		ArrayList<String> names = new ArrayList<>();
		for (Node n : graph.getMap().values()) {
			names.add(n.getName());
		}
		return names;
	}

	public Collection<Node> getNodes() {
		return graph.getMap().values();
	}

	public ArrayList<Node> getPathNodes() {
		return graph.getPath();
	}

	public void readingFile(File file) {
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();

			while (line != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
				line = bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stringBuilder.length() > 0) {
			try {
				makeGraphOfFileString(stringBuilder.toString());
			} catch (NumberFormatException e) {
				System.out.println("makeGraphOfFileString-GPD ");
			}

		}

	}

	private void makeGraphOfFileString(String fileString) {

		ArrayList<String[]> nodeOrLink = new ArrayList<String[]>();
		String[] lines = fileString.split(";");
		for (int i = 0; i < lines.length; i++) {
			nodeOrLink.add(lines[i].split(","));
		}

		Comparator<String[]> comperator = (e1, e2) -> {
			int difference = Integer.compare(e1.length, e2.length);
			return difference;
		};
		
		nodeOrLink.sort(comperator);
		if (!nodeOrLink.isEmpty()) {
			clearGraph();
			for (String[] strArr : nodeOrLink) {
				for (int i = 0; i < strArr.length; i++) {
					strArr[i] = strArr[i].replaceAll("^\\s+|\\s+$", "");

				}
				if (strArr.length == 4) {
					addNodeInGraph(strArr[0].toString(), (int) Double.parseDouble(strArr[1]),
							(int) Double.parseDouble(strArr[2]), Double.parseDouble(strArr[3]));
				} else if (strArr.length == 5) {
					if (strArr[4].toLowerCase().equals("t")) {
						addRouteInGraph(strArr[0], strArr[1], Double.parseDouble(strArr[2]), strArr[3], true);
					} else if (strArr[4].toLowerCase().equals("f")) {
						addRouteInGraph(strArr[0], strArr[1], Double.parseDouble(strArr[2]), strArr[3], false);
					}
				}
			}
		}
	}

	public void saveInFile(File file) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Node n :graph.getMap().values()) {
			writer.println(n.getName() + "," + n.getX() + "," + n.getY() + "," + n.getWeight() + ";");
		}
		HashMap<String, Node> copyMap = (HashMap<String, Node>) graph.getMap().clone();
		boolean flag = false;
		for (Node n : copyMap.values()) {
			for (Link l : n.getLinks()) {
				flag = false;
				Node temp = l.getToNode();
				for (Link link : temp.getLinks()) {
					if (link.getToNode().equals(n)) {
						writer.println(n.getName() + "," + l.getToNode().getName() + "," + l.getLength() + ","
								+ l.getType() + "," + "T;");
						temp.getLinks().remove(link);
						flag = true;
						break;
					}
				}
				if (!flag) {
					writer.println(n.getName() + "," + l.getToNode().getName() + "," + l.getLength() + "," + l.getType()
							+ "," + "F;");
				}
			}
		}
		writer.close();
		
	}

	public Graph getGraph() {
		return graph;
	}

}
