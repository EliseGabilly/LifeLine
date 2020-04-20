package util;

import java.util.Comparator;

import pkg.PathFinder;

public class NodeComparator implements Comparator<Integer> {

	PathFinder pFinder;
	
	public NodeComparator(PathFinder pFinder) {
		this.pFinder=pFinder;
	}
	
	@Override
	public int compare(Integer n1, Integer n2) {
		if (pFinder.getNodeHeurystic(n1) < pFinder.getNodeHeurystic(n2))
			return 1;
		else if (pFinder.getNodeHeurystic(n1) > pFinder.getNodeHeurystic(n2))
			return -1;
		return 0;
	}
}