package termFrequency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class TermFrequencyService {
    ArrayList<String> lines;
    HashMap<String, Integer> wordCount;

    // Remove any punctuations from the txt file:
    public static String removePunctuation(String line) {
        return line.replaceAll("[.,/:!-{}()\"\']", "");
    }

    //Read all lines from txt file and add to ArrayList<String>:
    public static ArrayList<String> readLinesFromFile(String fileName) throws IOException {

        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> lines = new ArrayList<String>();

        String currLine;
        while ((currLine = br.readLine()) != null) {
            // Trim the front and back white spaces
            String lineWithNoPunctuations = TermFrequencyService.removePunctuation(currLine);
            lines.add(lineWithNoPunctuations);
        }
        return lines;
    }

    // Count all the words in the txt file:
    public static HashMap<String, Integer> getWordCount(ArrayList<String> lines) {

        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
        for (String line : lines) {

            //Split by all spaces in between
            String[] words = line.split("\\s+");

            for (String word: words) {
                //Change all words to lower case
                String lowerCaseWord = word.toLowerCase();

                if (wordCounts.containsKey(lowerCaseWord)) {
                    wordCounts.put(lowerCaseWord, wordCounts.get(lowerCaseWord) + 1);
                } else {
                    wordCounts.put(lowerCaseWord, 1);
                }
            }
        }
        return wordCounts;
    }

    public static Double termFrequency(Integer wordCount, Integer allWordCount) {
        return (wordCount / (double) allWordCount);
    }

    public static Integer getAllWordCount(HashMap<String, Integer> wordCounts) {
        Integer allWords = 0;
        for (Integer count: wordCounts.values()) {
            allWords += count;
        }
        return allWords;
    }

    public ArrayList<String> topTenTermFrequency() {

        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Double> termFrequencies = new ArrayList<Double>();
        Integer allWordCount = TermFrequencyService.getAllWordCount(this.wordCount);

        for (String word: this.wordCount.keySet()) {
            String lowerWord = word.toLowerCase();
            Integer lowerWordCount = this.wordCount.get(lowerWord);
            Double lowerWordTermFrequency = TermFrequencyService.termFrequency(lowerWordCount, allWordCount);
            words.add(lowerWord);
            termFrequencies.add(lowerWordTermFrequency);
        }
        // Array list of index 0 to size of words, these are indexes, and we will be sorting them such that
        // words with the highest term frequencies are in front
        Integer[] wordsIndexes = IntStream.range(0, words.size()).boxed().toArray(Integer[]::new);

        // sort the words in descending order, by term frequencies
        Arrays.sort(wordsIndexes, new Comparator<Integer>(){
            @Override
            public int compare(Integer wordIndexOne, Integer wordIndexTwo) {
                // Sort words by term frequency
                // -1 for left > right
                // 0 for left == right
                // 1 for left < right
                double wordOneTermFrequency = termFrequencies.get(wordIndexOne);
                double wordTwoTermFrequency = termFrequencies.get(wordIndexTwo);

                return Double.compare(wordTwoTermFrequency, wordOneTermFrequency);
            }
        });

        ArrayList<String> topTenWordsWithHighestFrequencies = new ArrayList<String>();

        for (Integer wordIndex: wordsIndexes) {
            topTenWordsWithHighestFrequencies.add(words.get(wordIndex));
        }
        return topTenWordsWithHighestFrequencies;
    }

    public TermFrequencyService(String fileName) throws IOException {
        this.lines = TermFrequencyService.readLinesFromFile(fileName);
        this.wordCount = TermFrequencyService.getWordCount(this.lines);
    }

    public static void main(String[] args) {

    }
}
