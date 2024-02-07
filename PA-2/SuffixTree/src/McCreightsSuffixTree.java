import java.util.*;

public class McCreightsSuffixTree {
    public SuffixTreeNode root;
    public int number_of_nodes;
    public String stringSequence;
    public int suffixId;
    public int longestDepth=0;
    public int subsStart;
    public int subsEnd;
    public SuffixTreeNode exactMatchingSubstring;
    public List<SuffixTreeNode> internalNodesList;

    public McCreightsSuffixTree() {
        root = new SuffixTreeNode();
        root.setNodeId(++number_of_nodes);
        root.setStart(-1);
        root.setEnd(-1);
        root.setSuffixLink(root);
        root.setStringDepth(0);
        internalNodesList = new ArrayList<>();
    }

    public SuffixTreeNode findPath(SuffixTreeNode u, int i) {
        SuffixTreeNode leaf = null;
        if(u.getChildren()!=null && u.getChildren().containsKey(stringSequence.charAt(i))) {
            int j=i;

            SuffixTreeNode current = u.getChildren().get(stringSequence.charAt(i));
            int start = current.getStart();
            int end = current.getEnd();

            while(start!=end+1) {
                if(stringSequence.charAt(start)== stringSequence.charAt(i)) {
                    start++;
                    i++;
                } else {

                    SuffixTreeNode internalNode = new SuffixTreeNode();
                    internalNode.setStart(current.getStart());
                    internalNode.setEnd(start-1);
                    internalNode.setNodeId(++number_of_nodes);
                    internalNode.setParent(u);
                    internalNodesList.add(internalNode);

                    leaf = new SuffixTreeNode();
                    leaf.setStart(i);
                    leaf.setEnd(this.stringSequence.length()-1);
                    leaf.setNodeId(++number_of_nodes);
                    leaf.setParent(internalNode);
                    leaf.setSuffix_id(suffixId++);


                    current.setEnd(start);
                    current.setParent(internalNode);

                    u.getChildren().put(stringSequence.charAt(j), internalNode);

                    Map<Character,SuffixTreeNode> children = null;
                    if(internalNode.getChildren()!=null) {
                        internalNode.getChildren().put(this.stringSequence.charAt(i), leaf);
                        internalNode.getChildren().put(stringSequence.charAt(start), current);
                    } else {
                        children = new TreeMap<Character,SuffixTreeNode>();
                        children.put(this.stringSequence.charAt(i), leaf);
                        children.put(this.stringSequence.charAt(start), current);
                        internalNode.setChildrens(children);
                    }

                    internalNode.setStringDepth(internalNode.getParent().getStringDepth()+(internalNode.getEnd() - internalNode.getStart() + 1));
                    leaf.setStringDepth(leaf.getParent().getStringDepth()+(leaf.getEnd() - leaf.getStart() + 1));
                    break;
                }
            }

            if(start>end) {
                findPath(current, i);
            }
        } else {

            leaf= new SuffixTreeNode();
            leaf.setStart(i);
            leaf.setEnd(this.stringSequence.length()-1);
            leaf.setNodeId(++number_of_nodes);
            leaf.setParent(u);
            leaf.setSuffix_id(suffixId++);

            Map<Character,SuffixTreeNode> children = null;
            if(u.getChildren()!=null) {
                u.getChildren().put(this.stringSequence.charAt(i), leaf);
            } else {
                children = new TreeMap<Character,SuffixTreeNode>();
                children.put(this.stringSequence.charAt(i), leaf);
                u.setChildrens(children);
            }
        }
        return leaf;
    }

    public void constructMcCreightsSuffixTree(String s, List<Character> alphabets) { //
        this.stringSequence = s+"$";
        for(int i = 0; i<this.stringSequence.length(); i++) {
            constructSuffixLinks(root, i);
        }
    }

    public void constructSuffixLinks(SuffixTreeNode leaf, int i) {

        if(i< stringSequence.length()) {

            // Case 1
            if(null==leaf.getParent()) {
                findPath(leaf, i);
            } else {

                SuffixTreeNode u = leaf.getParent();
                SuffixTreeNode v = null;
                int alpha = u.getStringDepth();

                // Case 1: SL(u) is known
                if(null!=u.getSuffixLink()) {

                    // Case 1A: u is root
                    if(u == root) {
                        v = root;
                        findPath(v, i);
                    }

                    // Case 1B: u is not root
                    else {
                        v = u.getSuffixLink();
                        findPath(v, i+alpha);
                    }
                }

                // Case 2: SL(u) is not known
                else {

                    SuffixTreeNode uPrime = u.getParent();
                    SuffixTreeNode vPrime;

                    // Case 2A: u' is the root
                    if(uPrime == root) {

                        if(u.getStart() == u.getEnd()) {
                            u.setSuffixLink(root);
                            v=root;
                        } else {
                            v = nodeHop(root,u.getStart()+1,u.getEnd(),i);
                            if(v!=null) {
                                u.setSuffixLink(v);
                            }
                        }
                        findPath(v, i+(u.getEnd()-u.getStart()));
                    } else {

                        // Case 2B: u' is not root
                        vPrime = uPrime.getSuffixLink();
                        if(vPrime!=null) {
                            v = nodeHop(vPrime, u.getStart(), u.getEnd(), i+vPrime.getStringDepth());
                        }

                        if(v!=null) {
                            u.setSuffixLink(v);
                            findPath(v, i+v.getStringDepth());
                        }
                    }
                }

            }
        }
    }

    private SuffixTreeNode nodeHop(SuffixTreeNode vPrime, int betaStart, int betaEnd, int i) {
        SuffixTreeNode v=null;

        int beta = betaEnd - betaStart + 1;
        int c;
        if(beta == 0) {
            v = vPrime;
        } else {

            SuffixTreeNode child = vPrime.getChildren().get(stringSequence.charAt(i));
            c = child.getEnd()-child.getStart()+1;

            if (beta == c) {
                v = child;
                return v;
            } else {
                if(beta < c) {
                    int n = 0; int j= child.getStart();

                    i=i+beta;
                    j=j+beta;
                    n=n+beta;


                    v = new SuffixTreeNode();
                    v.setNodeId(++number_of_nodes);
                    v.setStart(child.getStart());
                    v.setEnd(j-1);
                    internalNodesList.add(v);


                    SuffixTreeNode leaf = new SuffixTreeNode();
                    leaf.setNodeId(++number_of_nodes);
                    leaf.setStart(i);
                    leaf.setEnd(stringSequence.length()-1);
                    leaf.setSuffix_id(suffixId++);

                    Map<Character, SuffixTreeNode> children = null;

                    if(v.getChildren()!=null) {

                    } else {
                        children = new TreeMap<Character, SuffixTreeNode>();
                        children.put(stringSequence.charAt(n), leaf);
                        children.put(this.stringSequence.charAt(j), child);
                        v.setChildrens(children);
                    }

                    v.setParent(child.getParent());
                    leaf.setParent(v);
                    child.setParent(v);


                    vPrime.getChildren().put(stringSequence.charAt(0), v);

                    child.setStart(j);

                    v.setStringDepth(v.getParent().getStringDepth()+(v.getEnd() - v.getStart() + 1));

                } else {
                    v = nodeHop(child, betaStart + c, betaEnd, i);
                }
            }
        }

        return v;
    }

    // short by one string depth
    public double averageInternalStringDepth() {
        double totalInternalNodeStringDepth = 0;

        for (SuffixTreeNode node : internalNodesList) {
            totalInternalNodeStringDepth += node.getStringDepth();
            //System.out.println("Node " + node.getNodeId() + " String Depth: " + node.getStringDepth());
        }
        double aveStrDep = totalInternalNodeStringDepth / internalNodesList.size();
        return aveStrDep;
    }


    // Shows possible node order problem.
    public String getNodeSequenceWithLongestStringDepth() {
        SuffixTreeNode nodeWithLongestStringDepth = null;
        int maxStringDepth = Integer.MIN_VALUE;

        for (SuffixTreeNode node : internalNodesList) {
            if (node.getStringDepth() > maxStringDepth) {
                nodeWithLongestStringDepth = node;
                maxStringDepth = node.getStringDepth();
            }
        }

        if (nodeWithLongestStringDepth != null) {
            StringBuilder sequenceBuilder = new StringBuilder();
            SuffixTreeNode currentNode = nodeWithLongestStringDepth;
            while (currentNode.getParent() != null) {
                sequenceBuilder.insert(0, this.stringSequence.substring(currentNode.getStart(), currentNode.getEnd() + 1));
                currentNode = currentNode.getParent();
            }
            return sequenceBuilder.toString();
        } else {
            return "No internal nodes found.";
        }
    }

    public void depthFirstSearch(SuffixTreeNode root) {
        if(root.getChildren()!=null) {
            for(Map.Entry<Character,SuffixTreeNode> entry : root.getChildren().entrySet()) {
                depthFirstSearch(entry.getValue());
            }
        } else {
            if(root.getSuffix_id()!=0)
                System.out.println(stringSequence.charAt(root.getSuffix_id()-1));
            else
                System.out.println(stringSequence.charAt(stringSequence.length()-1));
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
