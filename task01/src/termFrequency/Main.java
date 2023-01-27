package termFrequency;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = args[0];

        TermFrequencyService tfs = new TermFrequencyService(fileName);

        System.out.println("The top 10 words with the highest term frequency are:" + "\n");
        tfs.printTopTenTermFrequency();
    }
}
