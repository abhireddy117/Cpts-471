package SequenceAlignment;

import java.io.*;
import java.text.DecimalFormat;


// Main used for TTD
public class TDDTestMain {
    public static void main(String[] args) throws FileNotFoundException {
        // Test file path and reader.
        // Global alignment test run.
        InputReader read = new InputReader();
        read.getGetSequence(new File("SampleTest.fasta"));
        read.getScoreParameters();
        GlobalAlignment global = new GlobalAlignment(read.sequence1, read.sequence2);
        global.generateScoreTable();
        printGlobalAlignment(read, global);

        /*
        //local alignment test
        InputReader read2 = new InputReader();
        read2.getGetSequence(new File("SampleTest.fasta"));
        read2.getScoreParameters();
        LocalAlignment local = new LocalAlignment(read.sequence1, read.sequence2);
        local.generateScoreTable();
        printLocalAlignment(read, local);

    /*
        // Test if the sequences characters match with the file.
        System.out.println(Arrays.deepToString(r.sequence1.toArray()));
        System.out.println(Arrays.deepToString(r.sequence2.toArray()));
        System.out.println();

        // Test if DNA info is read correct
        System.out.println(r.sequence1Info);
        System.out.println(r.sequence2Info);
        System.out.println();

        // Test if the character size much
        System.out.println(r.sequence1.size());
        System.out.println(r.sequence2.size());
        System.out.println();

        // test if config file is read properly.
        r.getScoreParameters();
        System.out.println(Score.matchScore);
        System.out.println(Score.mismatchPenalty);
        System.out.println(Score.openingGapPenalty);
        System.out.println(Score.gapExtensionPenalty);
        System.out.println();

     */

        /*******************************************Input reading completed.*********************************/

        /*
        // Test global alignment object.
        GlobalAlignment global = new GlobalAlignment(r.sequence1, r.sequence2);
        // test generating table.
        global.generateScoreTable();
        // test optimal score
        System.out.println((global.optimalScore));

        // test retrace;
        System.out.println((global.alignedSequence1));
        System.out.println((global.alignedSequence2));*/

        /****************************Global Alignment dynamic table and retrace completed *********8************/
    }

    public static void printGlobalAlignment(InputReader read, GlobalAlignment alignment){

        int x=0;
        int y=0;
        int count = 0;
        int count2 = 0;

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
        System.out.println("Scores: match = "+ Score.matchScore +", mismatch = "+ Score.mismatchPenalty +", h = "
                + Score.openingGapPenalty+", g = "+ Score.gapExtensionPenalty);
        System.out.println();
        System.out.println("Sequence 1 =\""+read.sequence1Info+", length = "+read.sequence1.size()+" Characters");
        System.out.println("Sequence 2 =\""+read.sequence2Info+", length = "+read.sequence2.size()+" Characters");
        System.out.println();


        for (int i=0; x<alignment.alignedSequence1.length();i++) {
            System.out.print("s1\t"+(x + 1)+"\t");
            for(int j=0;j<60 && x<alignment.alignedSequence1.length();j++) {
                System.out.print(alignment.alignedSequence1.charAt(x));
                if (alignment.alignedSequence1.charAt(x) == '-') {
                    count2++;
                }
                x++;
            }
            System.out.print("\t" + (x - count2));
            System.out.println();
            x=y;

            System.out.print("\t\t");

            for (int j = 0; j < 60 && x<alignment.alignedSequence1.length(); j++) {
                if(alignment.alignedSequence1.charAt(x) =='-' && alignment.alignedSequence2.charAt(x)=='-') {
                    System.out.print(" ");
                }
                else if(alignment.alignedSequence1.charAt(x) == alignment.alignedSequence2.charAt(x)) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                x++;
            }
            System.out.println();
            x=y;

            System.out.print("s2\t"+(x - count + 1)+"\t");
            for (int j = 0; j < 60 && x<alignment.alignedSequence1.length(); j++) {
                System.out.print(alignment.alignedSequence2.charAt(x));
                if(alignment.alignedSequence1.charAt(x) == '-'){
                    count++;
                }

                x++;
            }
            System.out.print("\t"+ (x - count));
            System.out.println();
            System.out.println();
            y=x;
        }
        System.out.println();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        System.out.println("Report:\n");
        System.out.println("Global Optimal Score = "+alignment.optimalScore+"\n");
        System.out.println("Number of: Matches = "+alignment.countMatches+" , Mismatches = "+alignment.countMismatches
                + ", Opening Gaps = "+alignment.countOpeningGaps + " , Gaps extensions = "+alignment.countGaps);
        System.out.println();
        System.out.println("Identities = "+alignment.countMatches+"/"+alignment.alignedSequence1.length()
                +" ("+(df.format((float)alignment.countMatches/(float)alignment.alignedSequence1.length()*100))+"%), Gaps = "
                +alignment.countGaps+"/"+alignment.alignedSequence1.length()
                +" ("+df.format((float)alignment.countGaps/(float)alignment.alignedSequence1.length()*100)+"%)");
    }

    public static void printLocalAlignment(InputReader read, LocalAlignment alignment){

        int x=0;
        int y=0;
        int count = 0;
        int count2 = 0;

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
        System.out.println("Scores: match = "+ Score.matchScore +", mismatch = "+ Score.mismatchPenalty +", h = "
                + Score.openingGapPenalty+", g = "+ Score.gapExtensionPenalty);
        System.out.println();
        System.out.println("Sequence 1 =\""+read.sequence1Info+", length = "+read.sequence1.size()+" Characters");
        System.out.println("Sequence 2 =\""+read.sequence2Info+", length = "+read.sequence2.size()+" Characters");


        for (int i=0; x<alignment.alignedSequence1.length();i++) {
            System.out.print("s1\t"+(x + 1)+"\t");
            for(int j=0;j<60 && x<alignment.alignedSequence1.length();j++) {
                System.out.print(alignment.alignedSequence1.charAt(x));
                if (alignment.alignedSequence1.charAt(x) == '-') {
                    count2++;
                }
                x++;
            }
            System.out.print("\t" + (x - count2));
            System.out.println();
            x=y;

            System.out.print("\t\t");

            for (int j = 0; j < 60 && x<alignment.alignedSequence1.length(); j++) {
                if(alignment.alignedSequence1.charAt(x) =='-' && alignment.alignedSequence2.charAt(x)=='-') {
                    System.out.print(" ");
                }
                else if(alignment.alignedSequence1.charAt(x) == alignment.alignedSequence2.charAt(x)) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                x++;
            }
            System.out.println();
            x=y;

            System.out.print("s2\t"+(x - count + 1)+"\t");
            for (int j = 0; j < 60 && x<alignment.alignedSequence1.length(); j++) {
                System.out.print(alignment.alignedSequence2.charAt(x));
                if(alignment.alignedSequence1.charAt(x) == '-'){
                    count++;
                }

                x++;
            }
            System.out.print("\t"+ (x - count));
            System.out.println();
            System.out.println();
            y=x;
        }
        System.out.println();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        System.out.println("Report:\n");
        System.out.println("Local Optimal Score = "+alignment.optimalScore+"\n");
        System.out.println("Number of: Matches = "+alignment.countMatches+" , Mismatches = "+alignment.countMismatches
                + ", Opening Gaps = "+alignment.countOpeningGaps + " , Gaps extensions = "+alignment.countGaps);
        System.out.println();
        System.out.println("Identities = "+alignment.countMatches+"/"+alignment.alignedSequence1.length()
                +" ("+(df.format((float)alignment.countMatches/(float)alignment.alignedSequence1.length()*100))+"%), Gaps = "
                +alignment.countGaps+"/"+alignment.alignedSequence1.length()
                +" ("+df.format((float)alignment.countGaps/(float)alignment.alignedSequence1.length()*100)+"%)");
    }
}
