package SequenceAlignment;

import java.util.*;

public class LocalAlignment {
    public static final int NEGATIVE_INFINITY = -999999999;
    public static final int ZERO = 0;
    public DP_Cell[][] table;
    List<Character> sequence1;
    List<Character> sequence2;
    public int optimalScore = 0;

    int countOpeningGaps = 0;
    int countGaps = 0;
    int countMatches = 0;
    int countMismatches = 0;
    String alignedSequence1 = "";
    String alignedSequence2 = "";


    public LocalAlignment(List<Character> sequence1, List<Character> sequence2) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
    }

    public void generateScoreTable() {
        int imax = 0, jmax = 0;

        table = new DP_Cell[sequence1.size() + 1][sequence2.size() + 1];

        table[0][0] = new DP_Cell();
        table[0][0].setSscore(ZERO);
        table[0][0].setDscore(ZERO);
        table[0][0].setIscore(ZERO);

        for (int i = 0; i < table.length; i++) {
            table[i][0] = new DP_Cell();
            table[i][0].setSscore(ZERO);
            table[i][0].setDscore(ZERO);
            table[i][0].setIscore(ZERO);
        }

        for (int i = 1; i < table[0].length; i++) {
            table[0][i] = new DP_Cell();
            table[0][i].setSscore(ZERO);
            table[0][i].setDscore(ZERO);
            table[0][i].setIscore(ZERO);
        }

        for (int i = 1; i < table.length; i++) {
            for (int j = 1; j < table[0].length; j++) {

                table[i][j] = new DP_Cell();

                table[i][j].setSscore(getMax(table[i - 1][j - 1].getSscore(),
                        table[i - 1][j - 1].getDscore(), table[i - 1][j - 1].getIscore())
                        + matchScore(sequence1.get(i - 1), sequence2.get(j - 1)));

                table[i][j].setDscore(getMax(
                        table[i - 1][j].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        table[i - 1][j].getDscore() + Score.gapExtensionPenalty,
                        table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        ZERO));

                table[i][j].setIscore(getMax(
                        table[i][j - 1].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        table[i][j - 1].getIscore() + Score.gapExtensionPenalty,
                        table[i][j - 1].getDscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                        ZERO));

                if (getMax(table[i][j].getSscore(), table[i][j].getDscore(), table[i][j].getIscore()) > optimalScore) {
                    imax = i;
                    jmax = j;
                    optimalScore = getMax(optimalScore, table[i][j].getSscore(), table[i][j].getDscore(), table[i][j].getIscore());
                }
            }
        }
        traceOptimalPath(imax, jmax);
    }

    public void traceOptimalPath(int imax, int jmax) {
        int i = imax;
        int j = jmax;
        int max = 0;

        while (getMax(table[i][j].getSscore(),table[i][j].getDscore(), table[i][j].getIscore()) > 0) {
            max = getMax(table[i][j].getSscore(), table[i][j].getIscore(), table[i][j].getDscore());
            if (max == getMax( table[i][j - 1].getSscore() + Score.openingGapPenalty + Score.gapExtensionPenalty,
                    table[i][j - 1].getDscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                    table[i][j - 1].getIscore() + Score.gapExtensionPenalty)) {
                if (getMax(table[i][j - 1].getSscore() + Score.openingGapPenalty
                                + Score.gapExtensionPenalty,
                        table[i][j - 1].getDscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i][j - 1].getIscore() + Score.gapExtensionPenalty, 0) == table[i][j - 1].getDscore()
                        + Score.gapExtensionPenalty+ Score.openingGapPenalty
                        || getMax(table[i][j - 1].getSscore() + Score.openingGapPenalty
                                + Score.gapExtensionPenalty,
                        table[i][j - 1].getDscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i][j - 1].getIscore() + Score.gapExtensionPenalty,
                         0) == table[i][j - 1].getSscore()
                        + Score.openingGapPenalty + Score.gapExtensionPenalty) {
                    countOpeningGaps++;
                }

                alignedSequence1 = alignedSequence1 + "-";
                alignedSequence1 = alignedSequence1 + sequence2.get(j - 1);
                j--;
                countGaps++;
            } else if (max == getMax(
                    table[i - 1][j].getSscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                    table[i - 1][j].getDscore() + Score.gapExtensionPenalty,
                    table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty
                    )) {
                if (getMax(table[i - 1][j].getSscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i - 1][j].getDscore() + Score.gapExtensionPenalty,
                        table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        0) == table[i - 1][j].getIscore() + Score.gapExtensionPenalty
                        + Score.openingGapPenalty
                        || getMax(
                        table[i - 1][j].getSscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        table[i - 1][j].getDscore() + Score.gapExtensionPenalty,
                        table[i - 1][j].getIscore() + Score.gapExtensionPenalty + Score.openingGapPenalty,
                        0) == table[i - 1][j].getSscore()
                        + Score.gapExtensionPenalty + Score.openingGapPenalty) {
                    countOpeningGaps++;
                }
                alignedSequence1 = alignedSequence1 + sequence1.get(i -1);
                alignedSequence1 = alignedSequence1 + "-";
                i--;
                countGaps++;
            } else if (max == (getMax(table[i - 1][j - 1].getIscore(), table[i - 1][j - 1].getDscore(),
                    table[i - 1][j - 1].getSscore()) + matchScore(sequence1.get(i - 1), sequence2.get(j - 1)))) {
                alignedSequence1 = alignedSequence1 + sequence1.get(i - 1);
                alignedSequence1 = alignedSequence1 + sequence2.get(j - 1);
                if (sequence1.get(i - 1) == sequence2.get(j - 1)) {
                    countMatches++;
                } else
                    countMismatches++;
                i--;
                j--;
            }
        }
    }

    public int matchScore(Character x, Character y) {
        if (x == y) {
            return Score.matchScore;
        } else {
            return Score.mismatchPenalty;
        }
    }

    public int getMax(int... a) {
        int max = NEGATIVE_INFINITY;
        for (int i = 0; i < a.length; i++) {
            if (max < a[i])
                max = a[i];
        }
        return max;
    }
}