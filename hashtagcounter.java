import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class hashtagcounter {
    private Hashtable<String, Node> hashtagTable = new Hashtable<>();
    private MaxFibonacciHeap maxFibonacciHeap = new MaxFibonacciHeap();

    public static List<String> readHashtagsAndQueries(String fileName) {

        List<String> hashtagsAndQueriesList = Collections.emptyList();
        try {
            hashtagsAndQueriesList = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashtagsAndQueriesList;
    }


    public static void printResults(List<String> resultList) {
        for (String output : resultList) {
            System.out.println(output);
        }
    }


    public void outQueries(List<String> input, PrintWriter output) {
        int queriesNumber = 0;
        for (String line : input) {
            if (line.equalsIgnoreCase("stop"))
                break;

            if (line.charAt(0) == '#') {
                int index = 0;
                for (int i = 1; i < line.length(); i++) {
                    if (Character.isWhitespace(line.charAt(i))) {
                        index = i;
                        break;
                    }
                }

                String hashtag = line.substring(1, index);
                int freq = Integer.valueOf(line.substring(index + 1));
                if (hashtagTable.get(hashtag) != null)
                    maxFibonacciHeap.increaseKey(hashtagTable.get(hashtag), freq);
                else {
                    Node newNode = new Node(0, null, null, null, null, freq, hashtag, false);
                    newNode = maxFibonacciHeap.insert(newNode);
                    hashtagTable.put(hashtag, newNode);
                }
            } else {  //queries case
                queriesNumber++;
                int topNumber = Integer.valueOf(line);
                List<Node> removedMaxList = new ArrayList<>();

                for (int i = 0; i < topNumber; i++) {
                    removedMaxList.add(maxFibonacciHeap.removeMax(queriesNumber));
                }
                for (int i = 0; i < topNumber-1; i++) {
                    output.print(removedMaxList.get(i).getHashtag() + ",");
                }
                output.println(removedMaxList.get(topNumber - 1).getHashtag());

                for (Node node : removedMaxList) {
                    maxFibonacciHeap.insert(node);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<String> input = hashtagcounter.readHashtagsAndQueries(args[0]);
        hashtagcounter hashtagFrequencyCounter = new hashtagcounter();
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter(args[1], false), true);
            hashtagFrequencyCounter.outQueries(input, output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            output.close();
        }
    }
}




