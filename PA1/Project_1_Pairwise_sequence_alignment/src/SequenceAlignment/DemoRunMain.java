package SequenceAlignment;
/* Note issues remain to be resolved. 1. Resolve compiling errors.
(4 errors during compile) (Could out of scope variable)   */
/* This class will implement main function for terminal input*/
// Also look at this page on to run from terminal for a packaged file.
// https://rollbar.com/blog/how-to-resolve-the-cannot-find-symbol-error-in-java/
import java.io.File;
import java.io.FileNotFoundException;

public class DemoRunMain {
    public static void main(String[] args) throws FileNotFoundException {
            String filePath = args[0];
            String alignmentType = args[1];
            System.out.println(filePath);
            System.out.println(alignmentType);
            printGlobalAlignment(filePath);
    }

    public static void printGlobalAlignment(String filePath) throws FileNotFoundException {
        InputReader read = new InputReader();
        read.getGetSequence(new File(filePath));
        read.getScoreParameters();
        GlobalAlignment global = new GlobalAlignment(read.sequence1, read.sequence2);
        global.generateScoreTable();

        System.out.println("INPUT: \n\n******");
        System.out.println(read.sequence1Info);
        for(int i = 0; i < read.sequence1.size(); i++){
            System.out.print(read.sequence1.get(i));
        }
        System.out.println("\n");
        System.out.println(read.sequence2Info);
        for(int i = 0; i < read.sequence2.size(); i++){
            System.out.print(read.sequence2.get(i));
        }
        System.out.println("\n");
        System.out.println("OUTPUT: \n******");
        System.out.println("");
        // System.out.println("Scores: match = "+ Score.matchScore +", mismatch = "+ Score.mismatchPenalty +", h = "
        //        + Score.openingGapPenalty+", g = "+ Score.gapExtensionPenalty);
        System.out.println();
        System.out.println("Sequence 1 =\""+read.sequence1Info+", length = "+read.sequence1.size()+" Characters");
        System.out.println("Sequence 2 =\""+read.sequence2Info+", length = "+read.sequence2.size()+" Characters");

        int x=0;
        int y=0;
        int count = 0;
        int count2 = 0;
    }
}

