package SequenceAlignment;

// Class for setter and getter
public class Score {
    public static int openingGapPenalty;
    public static int gapExtensionPenalty;
    public static int matchScore;
    public static int mismatchPenalty;
    public static void setMatchScore(int matchScore) {
        Score.matchScore = matchScore;
    }
    public static void setMismatchPenalty(int mismatchPenalty) {
        Score.mismatchPenalty = mismatchPenalty;
    }
    public static void setOpeningGapPenalty(int openingGapPenalty) {
        Score.openingGapPenalty = openingGapPenalty;
    }
    public static void setGapExtensionPenalty(int gapExtensionPenalty) {
        Score.gapExtensionPenalty = gapExtensionPenalty;
    }
}