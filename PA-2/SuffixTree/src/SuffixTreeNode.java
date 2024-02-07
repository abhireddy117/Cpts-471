import java.util.Map;

public class SuffixTreeNode {
    // members for Node class
    // { node ID, suffix-link pointer, parent pointer, parent edge label (i.e., label of the incoming edge from the parent), children pointers, string-depth}.

    //node ID,
    private int nodeId;

    //suffix-link pointer,
    private SuffixTreeNode suffix_link;

    //parent pointer
    private SuffixTreeNode parent;

    // edge labels
    private int start;
    private int end;

    // children pointers
    private Map<Character, SuffixTreeNode> children;

    //  string-depth
    private int StringDepth;

    //  string-depth
    private int suffix_id;

    // Getter and setter for NodeID
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }


    // Getter and setter for suffix_link
    public void setSuffixLink(SuffixTreeNode suffixLink) {
        this.suffix_link = suffixLink;
    }

    public SuffixTreeNode getSuffixLink() {
        return suffix_link;
    }

    // Getter and setter for Parent
    public void setParent(SuffixTreeNode parent) {
        this.parent = parent;
    }

    public SuffixTreeNode getParent() {
        return parent;
    }

    // Getter and setter edge labels.
    public void setStart(int edgeStart) {
        this.start = edgeStart;
    }

    public int getStart() {
        return start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getEnd() {
        return end;
    }

    // Getter and Setter for children pointers
    public Map<Character, SuffixTreeNode> getChildren() {
        return children;
    }
    public void setChildrens(Map<Character, SuffixTreeNode> children) {
        this.children = children;
    }

    // Getter and Setter for string-depth
    public void setStringDepth(int stringDepth) {
        StringDepth = stringDepth;
    }

    public int getStringDepth() {
        return StringDepth;
    }

    // Getter and Setter for suffix_id
    public void setSuffix_id(int suffix_id) {
        this.suffix_id = suffix_id;
    }

    public int getSuffix_id() {
        return suffix_id;
    }
}