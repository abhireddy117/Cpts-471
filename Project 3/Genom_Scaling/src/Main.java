import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        //To Read file locations from command line arguments
        List<String> fastaFileNames = Arrays.asList("Covid_Australia.fasta", "Covid_Brazil.fasta", "Covid_India.fasta",
        "Covid_USA-CA4.fasta", "Covid_Wuhan.fasta", "MERS_2012_KF600620.fasta", "MERS_2014_KY581694.fasta",
        "MERS_2014_USA_KP223131.fasta", "SARS_2003_GU553363.fasta", "SARS_2017_MK062179.fasta");
        String alphabetFileName = "DNA_alphabet.txt";

        ReadFastaFile readFastaFile = new ReadFastaFile();
        List<String> dnaSequences = new ArrayList<>();

        for (String fastaFileName : fastaFileNames) {
            List<Input> inputStrings = readFastaFile.getInput(fastaFileName);
            String s = null;

            for (Input x : inputStrings) {
                s = x.getContent();
            }
            dnaSequences.add(s);
        }

        ReadAlphabet readAlphabet = new ReadAlphabet();
        List<Character> alphabets = readAlphabet.readAlphabet(alphabetFileName);

        System.out.println("<<<<<<<<<<<<<<<<<< Test of Suffix Tree with just findPath.. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        SuffixTree tree = new SuffixTree();

        long nanoTimeStart= System.nanoTime();
        tree.constructCombinedSuffixTree(dnaSequences,alphabets);
        long nanoTimeEnd= System.nanoTime();
        System.out.println("---------------- Tree construction time. -----------------");
        System.out.println("TIME: "+(nanoTimeEnd - nanoTimeStart) + " Nanoseconds.");


        System.out.println("");
        System.out.println("---------------- Suffix Tree Stats. -----------------");
        System.out.println("Number of internal Node = " + tree.internalNodes);
        System.out.println("Number of leaves = " + tree.suffixId);
        System.out.println("Total number of node is = " + tree.number_of_nodes);
        System.out.println("Average String depth = " + tree.averageInternalStringDepth());
        tree.printStringForNodeWithMaxDepth();

        System.out.println("");
        System.out.println("---------------- BWT -----------------");
        // print BWT
        //tree.depthFirstSearch(tree.root);

    }
}