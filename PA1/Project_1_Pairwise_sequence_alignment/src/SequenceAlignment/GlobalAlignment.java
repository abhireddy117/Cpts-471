package SequenceAlignment;

import java.util.List;

public class GlobalAlignment  {
    public static final int NEGATIVE_INFINITY = -999999999;
    public static final int ZERO = 0;
    public DP_Cell[][] table;
    public int optimalScore = 0;
    List<Character> sequence1;
    List<Character> sequence2;
    int countOpeningGaps = 0;
    int countGaps = 0;
    int countMatches = 0;
    int countMismatches = 0;
    public String alignedSequence1;

    public String alignedSequence2;

    public GlobalAlignment(List<Character> sequence1,  List<Character> sequence2) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
    }

    // Create the score matrix
    public void generateScoreTable() {
        //Size = (m + 1) * (n + 1) table using cell
        table = new DP_Cell[sequence1.size() + 1][sequence2.size() + 1];

        // initiate the left top conner negative zero because we have no other cell before it.
        table[0][0] = new DP_Cell();
        table[0][0].setSscore(0);
        table[0][0].setDscore(NEGATIVE_INFINITY);
        table[0][0].setIscore(NEGATIVE_INFINITY);


        // initiate first column and row negative infinity and zero.
        for (int i = 1; i < table.length; i++) {
            table[i][0] = new DP_Cell();
            table[i][0].setSscore(NEGATIVE_INFINITY);
            table[i][0].setDscore(Score.openingGapPenalty + i * Score.gapExtensionPenalty);
            table[i][0].setIscore(NEGATIVE_INFINITY);
        }

        for (int i = 1; i < table[0].length; i++) {
            table[0][i] = new DP_Cell();
            table[0][i].setSscore(NEGATIVE_INFINITY);
            table[0][i].setDscore(NEGATIVE_INFINITY);
            table[0][i].setIscore(Score.openingGapPenalty+ i * Score.gapExtensionPenalty);
        }

        // Filling out the inner cells of the score metrics
        for (int i = 1; i < table.length; i++) {
            for (int j = 1; j < table[0].length; j++) {
                table[i][j] = new DP_Cell();
                // Here the much score parameter should have been i and j according my in class's note.
                // but doing so result in out of bound exception maybe we should base the matrix length on
                // the sequence
                table[i][j].setSscore(getMax(table[i - 1][j - 1].getSscore(), table[i - 1][j - 1].getIscore(),
                        table[i - 1][j - 1].getDscore()) + matchScore(sequence1.get(i - 1), sequence2.get(j - 1)));

                table[i][j].setIscore(getMax(
                        table[i][j - 1].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        table[i][j - 1].getDscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        table[i][j - 1].getIscore() + Score.gapExtensionPenalty));

                table[i][j].setDscore(getMax(
                        table[i - 1][j].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i - 1][j].getDscore() + Score.gapExtensionPenalty));
            }
        }
        optimalScore = getMax(table[sequence1.size()][sequence2.size()].getSscore(),
                table[sequence1.size()][sequence2.size()].getDscore(), table[sequence1.size()][sequence2.size()].getIscore());
        traceOptimalPath();
    }

    public void traceOptimalPath(){
        int i = table.length - 1;
        int j = table[0].length - 1;
        int max = 0;
        String alignedStringS1 = "";
        String alignedStringS2 = "";

        while (i > 0 || j > 0) {
            max = getMax(table[i][j].getSscore(), table[i][j].getDscore(), table[i][j].getIscore());
            if (max == getMax(table[i][j - 1].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                    table[i][j - 1].getDscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                    table[i][j - 1].getIscore() + Score.gapExtensionPenalty
                    )) {

                if (getMax(table[i][j - 1].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        table[i][j - 1].getDscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i][j - 1].getIscore() + Score.gapExtensionPenalty) == table[i][j - 1].getDscore()
                        + Score.gapExtensionPenalty + Score.openingGapPenalty
                        || getMax(table[i][j - 1].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        table[i][j - 1].getDscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i][j - 1].getIscore() + Score.gapExtensionPenalty) == table[i][j - 1].getSscore()
                        + Score.openingGapPenalty + Score.gapExtensionPenalty) {
                    countOpeningGaps++;
                }

                alignedStringS1 = alignedStringS1 + "-";
                alignedStringS2 = alignedStringS2 + sequence2.get(j - 1);
                j--;
                countGaps++;
            } else if (max == getMax(
                    table[i - 1][j].getSscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                    table[i - 1][j].getDscore() + Score.gapExtensionPenalty,
                    table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty)) {
                if (getMax(table[i - 1][j].getSscore() + Score.gapExtensionPenalty
                                + Score.openingGapPenalty,
                        table[i - 1][j].getDscore() + Score.gapExtensionPenalty,
                        table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty
                         ) == table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty
                        || getMax(
                        table[i - 1][j].getSscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i - 1][j].getDscore() + Score.gapExtensionPenalty,
                        table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty
                        ) == table[i - 1][j].getSscore() + Score.gapExtensionPenalty + Score.openingGapPenalty) {
                    countOpeningGaps++;
                }
                alignedStringS1 = alignedStringS1 + sequence1.get(i - 1);
                alignedStringS2 = alignedStringS2 + "-";
                i--;
                countGaps++;
            } else if (max == (getMax(table[i - 1][j - 1].getSscore(),table[i - 1][j - 1].getDscore(),
                    table[i - 1][j - 1].getIscore()) + matchScore(sequence1.get(i - 1), sequence2.get(j - 1)))) {
                alignedStringS1 = alignedStringS1 + sequence1.get(i - 1);
                alignedStringS2 = alignedStringS2 + sequence2.get(j - 1);
                if (sequence1.get(i - 1) == sequence2.get(j - 1)) {
                    countMatches++;
                } else
                    countMismatches++;
                i--;
                j--;
            }
        }
        alignedSequence1 = new StringBuilder(alignedStringS1).reverse().toString();
        alignedSequence2 = new StringBuilder(alignedStringS2).reverse().toString();
    }

    public int getMax(int x, int y, int z){
        int a = Math.max(x, y);
        if (z > a){
            return z;
        } else {
            return a;
        }
    }

    public int matchScore(Character x, Character y){
        if (x == y) {
            return Score.matchScore;
        } else {
            return Score.mismatchPenalty;
        }
    }
}
