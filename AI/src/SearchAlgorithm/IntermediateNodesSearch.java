package SearchAlgorithm;

import java.util.ArrayList;

import uni.Graph;
import uni.Node;

public class IntermediateNodesSearch extends ASearch {
	private ArrayList<String> intermediateNodes;
	private ArrayList<Node> paths = new ArrayList<Node>();
	private String pathInformation = "";
	private String fullInfo = "";

	public IntermediateNodesSearch(Graph graph, String nodeName1,String nodeName2) {
		super(graph);
		intermediateNodes = new ArrayList<String>();
		intermediateNodes.add(nodeName1);
	//	intermediateNodes.add(nodeName2);
		
	}

	@Override
	public boolean search(String from, String to) {
		myGraph.resetGraph();
		
		if (!intermediateNodes.isEmpty()) {
			intermediateNodes.add(0,from);
			intermediateNodes.add(to);
			
			
			for (int i = 0; i < intermediateNodes.size() - 1; i++) {

				if (!myGraph.searchPath(intermediateNodes.get(i), intermediateNodes.get(i + 1),
						new ShortestWaySearch(myGraph))) {
					intermediateNodes.remove(0);
					intermediateNodes.remove(intermediateNodes.size()-1);
					return false;
				}//premahvash 1viq i posledniq vutre v cikala 
				paths.addAll(0,myGraph.getPath());
				pathInformation += myGraph.getPathInforamtion();
				fullInfo += myGraph.getFullInformation()+"\n";

			}
			
			myGraph.setPathInforamtion(pathInformation);
			myGraph.setFullInformation(fullInfo);
			myGraph.getPath().clear();
			myGraph.getPath().addAll(paths);

			intermediateNodes.remove(0);
			intermediateNodes.remove(intermediateNodes.size()-1);
			return true;
		} else {
			return myGraph.searchPath(from, to, new ShortestWaySearch(myGraph));
		}
	}

}
