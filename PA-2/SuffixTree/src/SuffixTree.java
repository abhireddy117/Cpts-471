import java.util.*;

public class SuffixTree {
    public int internalNodes;

    // same as leaves
    public int suffixId;

    public SuffixTreeNode root;

    public int number_of_nodes;

    public String stringSequence;

    public SuffixTreeNode exactMatchingSubstring;

    public List<SuffixTreeNode> internalNodesList;

    public SuffixTree() {
        root = new SuffixTreeNode();
        root.setNodeId(++number_of_nodes);
        root.setStart(0);
        root.setEnd(-1);
        root.setSuffixLink(root);
        root.setStringDepth(0);
        internalNodesList = new ArrayList<>();
    }


    public void constructSuffixTree(String s, List<Character> alphabets) {
        this.stringSequence = s + "$";
        for (int i = 0; i < this.stringSequence.length(); i++) {
            if (alphabets.contains(this.stringSequence.charAt(i))) {
                if (root != null) {
                    findPath(root, i);
                }
            } else {
                break;
            }
        }
    }

    private void findPath(SuffixTreeNode root, int i) {
        if (root.getChildren() != null && root.getChildren().containsKey(stringSequence.charAt(i))) {
            int j = i;
            //i is string pointer
            SuffixTreeNode current = root.getChildren().get(stringSequence.charAt(i));
            int start = current.getStart();
            int end = current.getEnd();

            while (start != end + 1) {
                if (stringSequence.charAt(start) == stringSequence.charAt(i)) {
                    // match
                    start++;
                    i++;
                } else {

                    SuffixTreeNode internalNode = new SuffixTreeNode();
                    internalNode.setStart(current.getStart());
                    internalNode.setEnd(start - 1);
                    internalNode.setNodeId(++number_of_nodes);
                    internalNode.setParent(root);
                    internalNode.setStringDepth(root.getStringDepth() + (internalNode.getEnd() - internalNode.getStart() + 1));
                    internalNodes++;


                    internalNodesList.add(internalNode);

                    SuffixTreeNode leaf = new SuffixTreeNode();
                    leaf.setStart(i);
                    leaf.setEnd(this.stringSequence.length() - 1);
                    leaf.setNodeId(++number_of_nodes);
                    leaf.setParent(internalNode);
                    leaf.setSuffix_id(suffixId++);
                    leaf.setStringDepth(root.getStringDepth() + (leaf.getEnd() - leaf.getStart() + 1));

                    current.setStart(start);
                    current.setParent(internalNode);

                    root.getChildren().put(stringSequence.charAt(j), internalNode);

                    Map<Character, SuffixTreeNode> children = null;
                    if (internalNode.getChildren() != null) {
                        internalNode.getChildren().put(this.stringSequence.charAt(i), leaf);
                        internalNode.getChildren().put(stringSequence.charAt(start), current);
                    } else {
                        children = new TreeMap<Character, SuffixTreeNode>();
                        children.put(this.stringSequence.charAt(i), leaf);
                        children.put(this.stringSequence.charAt(start), current);
                        internalNode.setChildrens(children);
                    }
                    break;
                }
            }

            if (start > end) {
                findPath(current, i + (end - start + 1));
            }

        } else {

            SuffixTreeNode leaf = new SuffixTreeNode();
            leaf.setStart(i);
            leaf.setEnd(this.stringSequence.length() - 1);
            leaf.setNodeId(++number_of_nodes);
            leaf.setParent(root);
            leaf.setSuffix_id(suffixId++);
            leaf.setStringDepth(root.getStringDepth() + (leaf.getEnd() - leaf.getStart() + 1));

            Map<Character, SuffixTreeNode> children = null;
            if (root.getChildren() != null) {
                root.getChildren().put(this.stringSequence.charAt(i), leaf);
            } else {
                children = new TreeMap<Character, SuffixTreeNode>();
                children.put(this.stringSequence.charAt(i), leaf);
                root.setChildrens(children);
            }
        }
    }

    public void dfsEnumerate() {
        dfsEnumerateHelper(root, 0);
    }

    private void dfsEnumerateHelper(SuffixTreeNode node, int depth) {

        System.out.println("Node " + (node.getNodeId() - 1) + " at depth " + depth + " with string depth " + node.getStringDepth());

        if (node.getChildren() != null) {
            for (SuffixTreeNode child : node.getChildren().values()) {
                dfsEnumerateHelper(child, depth + 1);
            }
        }
    }

    public void depthFirstSearch(SuffixTreeNode root) {
        if (root.getChildren() != null) {
            for (Map.Entry<Character, SuffixTreeNode> entry : root.getChildren().entrySet()) {
                depthFirstSearch(entry.getValue());
            }
        } else {
            if (root.getSuffix_id() != 0)
                System.out.println(stringSequence.charAt(root.getSuffix_id() - 1));
            else
                System.out.println(stringSequence.charAt(stringSequence.length() - 1));
        }
    }

    public void printAllPaths() {
        printAllPathsHelper(root, "");
    }

    private void printAllPathsHelper(SuffixTreeNode node, String path) {
        path += stringSequence.substring(node.getStart(), node.getEnd() + 1);

        if (node.getChildren() == null) {
            System.out.println(node.getSuffix_id() + path);
            return;
        }

        for (SuffixTreeNode child : node.getChildren().values()) {
            printAllPathsHelper(child, path);
        }
    }

    public double averageInternalStringDepth() {
        double totalInternalNodeStringDepth = 0;

        for (SuffixTreeNode node : internalNodesList) {
            totalInternalNodeStringDepth += node.getStringDepth();
            //System.out.println("Node " + node.getNodeId() + " String Depth: " + node.getStringDepth());
        }
        //System.out.println(totalInternalNodeStringDepth + " " + internalNodes);
        double aveStrDep = totalInternalNodeStringDepth / internalNodesList.size();
        return aveStrDep;
    }

    public String printStringForNodeWithMaxDepth() {
        SuffixTreeNode maxNode = null;
        int maxDepth = -1;

        for (SuffixTreeNode node : internalNodesList) {
            if (node.getStringDepth() > maxDepth) {
                maxNode = node;
                maxDepth = node.getStringDepth();
            }
        }

        StringBuilder sb = new StringBuilder();
        SuffixTreeNode currNode = maxNode;

        while (currNode != null) {
            sb.insert(0, stringSequence.substring(currNode.getStart(), currNode.getEnd() + 1));
            currNode = currNode.getParent();
        }

        return sb.toString();
    }


    public static String findLongestMatchSubstring(String input) {
        HashMap<String, Integer> map = new HashMap<>();
        int maxLength = 0;
        String longestMatchSubstring = "";
        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j <= input.length(); j++) {
                String substring = input.substring(i, j);
                if (map.containsKey(substring)) {
                    int length = j - i;
                    if (length > maxLength) {
                        maxLength = length;
                        longestMatchSubstring = substring;
                    }
                    map.put(substring, map.get(substring) + 1);
                } else {
                    map.put(substring, 1);
                }
            }
        }
        System.out.println("Longest exact match = " + longestMatchSubstring);
        System.out.println("Occurrences:");
        for (String substring : map.keySet()) {
            if (map.get(substring) > 1 && substring.length() == maxLength) {
                int index = input.indexOf(substring);
                while (index >= 0) {
                    System.out.println("From index " + (index+1) + " to index " + (index + maxLength));
                    index = input.indexOf(substring, index + 1);
                }
            }
        }
        return longestMatchSubstring;
    }
}
