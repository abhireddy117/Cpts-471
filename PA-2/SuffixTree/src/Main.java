import java.util.List;

public class Main {
    public static void main(String[] args){
        //To Read file locations from command line arguments
        String fastaFileName = "s1.fasta";
        String alphabetFileName = "English_alphabet.txt";

        // To read fasta file
        ReadFastaFile readFastaFile = new ReadFastaFile();
        List<Input> inputStrings =  readFastaFile.getInput(fastaFileName);
        String s = null;

        for (Input x: inputStrings) {
            s = x.getContent();
        }

        // To read alphabet
        ReadAlphabet readAlphabet = new ReadAlphabet();
        List<Character> alphabets = readAlphabet.readAlphabet(alphabetFileName);


        SuffixTree tree = new SuffixTree();

        tree.constructSuffixTree(s,alphabets);


        System.out.println("");
        System.out.println("---------------- Suffix Tree Stats. -----------------");
        System.out.println("Number of internal Node = " + tree.internalNodes);
        System.out.println("Number of leaves = " + tree.suffixId);
        System.out.println("Total number of node is = " + tree.number_of_nodes);
        System.out.println("Average String depth = " + tree.averageInternalStringDepth());
        System.out.println("String for the deepest internal node = " + tree.printStringForNodeWithMaxDepth());

        System.out.println("");
        System.out.println("---------------- BWT -----------------");
        // print BWT
        tree.depthFirstSearch(tree.root);

        System.out.println("");
        System.out.println("---------------- String depth of each node.-----------------");
        tree.dfsEnumerate();

        System.out.println("");
        System.out.println("---------------- Longest exactMatch.-----------------");
        tree.findLongestMatchSubstring(s);

        System.out.println("");
        System.out.println("<<<<<<<<<<<<<<<<<< Test of Suffix_Link tree. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        McCreightsSuffixTree linkTree = new McCreightsSuffixTree();

        long nanoTimeStart2= System.nanoTime();
        linkTree.constructMcCreightsSuffixTree(s,alphabets);
        long nanoTimeEnd2= System.nanoTime();
        System.out.println("---------------- Tree construction time. -----------------");
        System.out.println("TIME: "+(nanoTimeEnd2 - nanoTimeStart2) + " Nanoseconds.");

        System.out.println("");
        System.out.println("---------------- Suffix_Link Tree Stats. -----------------");
        System.out.println("Number of internal Node = " + linkTree.internalNodesList.size());
        System.out.println("Number of leaves = " + linkTree.suffixId);
        System.out.println("Total number of node is = " + linkTree.number_of_nodes);
        System.out.println("Average String depth = " + linkTree.averageInternalStringDepth());
        System.out.println("String for the deepest internal node = " + linkTree.getNodeSequenceWithLongestStringDepth());

        System.out.println("");
        System.out.println("---------------- BWT -----------------");
        // print BWT
        linkTree.depthFirstSearch(tree.root);

        System.out.println("");
        System.out.println("---------------- String depth of each node.-----------------");
        linkTree.dfsEnumerate();

        System.out.println("");
        System.out.println("---------------- Longest exactMatch.-----------------");
        linkTree.findLongestMatchSubstring(s);
    }
}
