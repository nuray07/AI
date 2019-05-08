package SearchAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;

import uni.Graph;
import uni.Link;
import uni.Node;

public class GreedyByLength extends ASearch {

	public GreedyByLength(Graph graph) {
		super(graph);

	}

	@Override
	public boolean search(String from, String to) {
		myGraph.resetGraph();
		if (!myMap.containsKey(from) || !myMap.containsKey(to)) {
			return false;
		}

		Node fromNode = myMap.get(from);
		fromNode.setDepth(0);
		calculateDirectLine(myMap.get(to));
		ArrayList<Node> queue = new ArrayList<Node>();
		queue.add(fromNode);

		while (!queue.isEmpty()) {
			Node temp = queue.get(0);
			queue.remove(0);
			setDepth(temp);
			myGraph.setPathInforamtion(myGraph.getPathInforamtion() + temp.getName() + ",");
			myGraph.setFullInformation(myGraph.getFullInformation() + temp.getName() + " : " + "| Parent : "
					+ (temp.getParent() != null ? temp.getParent().getName() : "Null") + " | Expense : "
					+ temp.getExpense() + " | Depth : " + temp.getDepth() + " | \n");
			if (temp.getName().equals(to)) {
				myGraph.setPath(printPath(temp));
				return true;
			}
			temp.setTested(true);

			ArrayList<Link> links = (ArrayList<Link>) temp.getLinks().clone();

			Comparator<Link> comperator = (l2,l1) -> {
				int difference = Double.compare(l1.getLength(), l2.getLength());
				if (difference == 0) {

					difference = Double.compare(l1.getToNode().getDistanceToEnd(), l2.getToNode().getDistanceToEnd());
				}

				return difference;
			};
			links.sort(comperator);
			for (Link l : links) {
				if (!l.getToNode().isTested()) {
					queue.remove(l.getToNode());
					queue.add(0,l.getToNode());

				}
			}

			temp.setExpanded(true);
		}

		return false;
	}

	private void calculateDirectLine(Node end) {
		for (Node node : myMap.values()) {
			double directLine = Math
					.sqrt(Math.pow(end.getX() - node.getX(), 2) + Math.pow(end.getY() - node.getY(), 2));
			node.setDistanceToEnd(directLine);
		}
	}

}
