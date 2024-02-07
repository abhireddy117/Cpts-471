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

    private List<Integer> separatorPositions = new ArrayList<>();

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

    public void constructCombinedSuffixTree(List<String> dnaSequences, List<Character> alphabets) {
        // Initialize the combined string
        StringBuilder combinedStringBuilder = new StringBuilder();

        // Combine all the input strings and store separator positions
        int position = 0;
        for (int i = 0; i < dnaSequences.size(); i++) {
            combinedStringBuilder.append(dnaSequences.get(i));
            position += dnaSequences.get(i).length();
            separatorPositions.add(position);
            if (i < dnaSequences.size() - 1) {
                combinedStringBuilder.append('#');
                position++;
            }
        }

        // Set the combined string
        this.stringSequence = combinedStringBuilder.toString() + "$";

        // Construct the combined suffix tree
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

    private void findPath(SuffixTreeNode root, int i, String stringWithEndChar) {
        if (root.getChildren() != null && root.getChildren().containsKey(stringWithEndChar.charAt(i))) {
            int j = i;
            //i is string pointer
            SuffixTreeNode current = root.getChildren().get(stringWithEndChar.charAt(i));
            int start = current.getStart();
            int end = current.getEnd();

            while (start != end + 1) {
                if (stringWithEndChar.charAt(start) == stringWithEndChar.charAt(i)) {
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
                    int inputStringIndex = 0;
                    for (int separatorPosition : separatorPositions) {
                        if (i < separatorPosition) {
                            break;
                        }
                        inputStringIndex++;
                    }
                    leaf.setInputStringIndex(inputStringIndex);
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
                findPath(current, i + (end - start + 1), stringWithEndChar);
            }

        } else {

            SuffixTreeNode leaf = new SuffixTreeNode();
            int inputStringIndex = 0;
            for (int separatorPosition : separatorPositions) {
                if (i < separatorPosition) {
                    break;
                }
                inputStringIndex++;
            }
            leaf.setInputStringIndex(inputStringIndex);
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
        System.out.println("String for the deepest internal node = "  + sb.toString());
        System.out.println("String for the deepest internal node length = "  + sb.toString().length());

        return sb.toString();
    }


    public void colorNodes(SuffixTreeNode node, int numFiles) {
        if (node.isLeaf()) {
            // Set the color of the leaf node to its inputStringIndex + 1
            node.setColor(node.getInputStringIndex() + 1);
            return;
        }

        // Visit each child and color them
        for (SuffixTreeNode child : node.getChildren().values()) {
            colorNodes(child, numFiles);
        }

        // Check if all children have the same color
        Set<Integer> uniqueChildColors = new HashSet<>();
        for (SuffixTreeNode child : node.getChildren().values()) {
            uniqueChildColors.add(child.getColor());
        }

        if (uniqueChildColors.size() == 1) {
            // If all children have the same color, set the current node's color to that color
            node.setColor(uniqueChildColors.iterator().next());
        } else {
            // If children have different colors, set the current node's color to K+1
            node.setColor(numFiles + 1);
        }
    }
}