package SequenceAlignment;
// Cell for Dynamic Programming table
public class DP_Cell {
    private int Sscore;
    private int Dscore;
    private int Iscore;


    public DP_Cell() {
    }

    public DP_Cell(int substitution, int deletion, int insertion) {
        this.Sscore = substitution;
        this.Dscore = deletion;
        this.Iscore = insertion;
    }

    public int getSscore() {
        return Sscore;
    }

    public void setSscore(int substitution) {
        this.Sscore = substitution;
    }

    public int getDscore() {
        return Dscore;
    }

    public void setDscore(int dscore) {
        this.Dscore = dscore;
    }

    public int getIscore() {
        return Iscore;
    }

    public void setIscore(int insertion) {
        this.Iscore = insertion;
    }
}
