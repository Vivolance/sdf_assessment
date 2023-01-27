package termFrequency;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TermFrequencyService {
    ArrayList<String> lines;
    HashMap<String, Integer> wordCount;
    HashMap<String, Double> wordTermFrequencies;

    //Creating a constructor
    public TermFrequencyService(String fileName) throws IOException {
        this.lines = TermFrequencyService.readLinesFromFile(fileName);
        this.wordCount = TermFrequencyService.getWordCount(this.lines);
        this.wordTermFrequencies = TermFrequencyService.getTermFrequencies(this.wordCount);
    }

    //Method to remove punctuations from a text file
    public static String removePunctuation(String line) {
        List<Character> characters = Arrays.asList('.', ',', ':', '!', '-', '(', ')', '{', '}', '`', '\'', '\"', '?');
        HashSet<Character> punctuations = new HashSet<>(characters);

        //Build  a new string to store characters
        StringBuilder sb = new StringBuilder();
        //Iterate through each char to remove all punctuations within the line
        for (int i = 0; i < line.length(); i++) {
            char currChar = line.charAt(i);
            boolean containsPunctuation = punctuations.contains(currChar);
            if (!containsPunctuation) {
                sb.append(currChar);;
            }
        }
        return sb.toString();
    }

    //Read lines from txt file
    public static ArrayList<String> readLinesFromFile(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> lines = new ArrayList<String>();

        String currLine;
        while ((currLine = br.readLine()) != null) {
            //Trim the front and back white spaces
            String lineWithNoPunctuations = TermFrequencyService.removePunctuation(currLine).trim();
            if (lineWithNoPunctuations.length() > 0) {
                lines.add(lineWithNoPunctuations);
            }
        }
        return lines;
    }

    //Get total word count in text file
    public static HashMap<String, Integer> getWordCount(ArrayList<String> lines) {
        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word: words) {
                String lowerCaseWord = TermFrequencyService.removePunctuation(word.toLowerCase());
                if (wordCounts.containsKey(lowerCaseWord)) {
                    wordCounts.put(lowerCaseWord, wordCounts.get(lowerCaseWord) + 1);
                } else {
                    wordCounts.put(lowerCaseWord, 1);
                }
            }
        }
        return wordCounts;
    }

    //Method to get term frequencies
    public static HashMap<String, Double> getTermFrequencies(HashMap<String, Integer> wordCounts) {
        HashMap<String, Double> termFrequencies = new HashMap<String, Double>();
        Integer allWordCount = TermFrequencyService.getAllWordCount(wordCounts);

        for (String word: wordCounts.keySet()) {
            //Set all possibilities eg. All,all,ALL to lower case.
            String lowerWord = word.toLowerCase();
            Integer lowerWordCount = wordCounts.get(lowerWord);
            Double lowerWordTermFrequency = TermFrequencyService.termFrequency(lowerWordCount, allWordCount);
            termFrequencies.put(lowerWord, lowerWordTermFrequency);
        }
        return termFrequencies;
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

    //Method to find words with top ten term frequencies
    public ArrayList<String> topTenTermFrequency() {
        ArrayList<String> words = new ArrayList<String>();
        for (String word: this.wordCount.keySet()) {
            String lowerWord = word.toLowerCase();
            words.add(lowerWord);
        }
        //Array list of index 0 to size of words, these are indexes, sorting in descending order

        String[] wordsArray = words.toArray(String[]::new);

        HashMap<String, Double> wordTermFrequencies = this.wordTermFrequencies;

        //Sort the words in descending order, by term frequencies
        Arrays.sort(wordsArray, new Comparator<String>(){
            @Override
            public int compare(String wordOne, String wordTwo) {
                String lowerWordOne = wordOne.toLowerCase();
                String lowerWordTwo = wordTwo.toLowerCase();
                double wordOneTermFrequency = wordTermFrequencies.get(lowerWordOne);
                double wordTwoTermFrequency = wordTermFrequencies.get(lowerWordTwo);
                return Double.compare(wordTwoTermFrequency, wordOneTermFrequency);
            }
        });

        String[] firstTenWords = Arrays.stream(wordsArray).limit(10).toArray(String[]::new);

        return new ArrayList<String>(Arrays.asList(firstTenWords));
    }

    //Print method for word with the top 10 term frequencies.
    public void printTopTenTermFrequency() {
        ArrayList<String> topTenTermFrequencies = this.topTenTermFrequency();
        for (String word: topTenTermFrequencies) {
            System.out.println("Word: " + word + " | " + "Term Frequency = " + this.wordTermFrequencies.get(word));
        }
    }

}