package SequenceAlignment;

import java.io.*;
import java.util.*;

// The following class will read the fasta and config file
// Obtains the two sequences we are alignment form the fasta file
// and the penalties and scores from configure file
public class InputReader {
    // Array list containing sequences.
    List<Character> sequence1 = new ArrayList<Character>();
    List<Character> sequence2 = new ArrayList<Character>();
    // Detail of the DNA Sequence
    String sequence1Info;
    String sequence2Info;

    // Scan through file and obtain both sequences.
    public void getGetSequence(File fileName) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(fileName);
            this.sequence1Info = sc.nextLine();
            int num = 1;
            while(sc.hasNext()) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if(line.startsWith(">")) {
                        num = 2;
                        this.sequence2Info = line;
                    } else {
                        for (int i = 0; i < line.length(); i++) {
                            if (num == 1) {
                                this.sequence1.add(line.charAt(i));
                            } else {
                                this.sequence2.add(line.charAt(i));
                            }
                        }
                    }

                }
            }


        } catch (FileNotFoundException ex){
            throw new FileNotFoundException("File not found" + ex);
        }
    }

    public void getScoreParameters() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);

            Score.setMatchScore(Integer.parseInt(prop.getProperty("match")));
            Score.setMismatchPenalty(Integer.parseInt(prop.getProperty("mismatch")));
            Score.setOpeningGapPenalty(Integer.parseInt(prop.getProperty("h")));
            Score.setGapExtensionPenalty(Integer.parseInt(prop.getProperty("g")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
