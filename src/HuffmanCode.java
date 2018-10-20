import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * @author John Berkley
 * CPP Class: CS3700
 * Date Created: Oct 20, 2018
 */

public class HuffmanCode {
    private static HuffmanTree buildTree(int[] charFreqs) {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();

        //Fill queue with single node trees
        for (int i = 0; i < charFreqs.length; i++)
            if (charFreqs[i] > 0)
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));

        //Loop until the final tree is constructed
        while (trees.size() > 1) {
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            //Re-insert combined tree
            assert b != null;
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }

    private static void generateHashMap(HuffmanTree tree, StringBuffer prefix, HashMap<Character, String> map) {
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf)tree;

            // print out character, frequency, and code for this leaf
            //System.out.printf("%5c \t %5d \t %15s\n", leaf.value, leaf.frequency, prefix);
            map.put(leaf.value, prefix.toString());

        } else if (tree instanceof HuffmanNode) {
            HuffmanNode node = (HuffmanNode)tree;

            // traverse left
            prefix.append('0');
            generateHashMap(node.left, prefix, map);
            prefix.deleteCharAt(prefix.length()-1);

            // traverse right
            prefix.append('1');
            generateHashMap(node.right, prefix, map);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    public static void main(String[] args) throws IOException {
        //Read in file
        String test = new String(Files.readAllBytes(Paths.get("/Users/johnberkley/IdeaProjects/CS3700_Project1/src/Constitution")));

        //Get frequencies
        int[] charFreqs = new int[256];
        for (char c : test.toCharArray())
            charFreqs[c]++;

        //Build Tree
        long startTreeBuilding = System.nanoTime();
        HuffmanTree tree = buildTree(charFreqs);
        long endTreeBuilding = System.nanoTime();
        long singleThreadTreeBuildTime = endTreeBuilding - startTreeBuilding;

        //Get conversion map
        HashMap<Character, String> encodingMap = new HashMap<>();
        generateHashMap(tree, new StringBuffer(), encodingMap);

        //Set up output file
        FileOutputStream outputStream = new FileOutputStream("/Users/johnberkley/IdeaProjects/CS3700_Project1/src/ConstitutionEncoded");
        StringBuffer encodedContent = new StringBuffer();

        //Encode file
        long startEncoding = System.nanoTime();
        for (int i = 0; i < test.length(); i++) {
            encodedContent.append(encodingMap.get(test.charAt(i)));
        }
        long endEncoding = System.nanoTime();
        long singleThreadEncodingTime = endEncoding - startEncoding;

        byte[] strToBytes = encodedContent.toString().getBytes();
        outputStream.write(strToBytes);
        outputStream.close();

        //Display compression rate
        System.out.println("Original Size: " + test.length() + " bytes");
        System.out.println("Compressed Size: " + encodedContent.toString().length() / 8 + " bytes");
        System.out.printf("Compression reduced size by %.2f%%.%n%n", (100 - ((encodedContent.toString().length()/8.0)/test.length() * 100)));

        //Display single threaded times
        System.out.println("Single Threaded Tree Building Time: " + singleThreadTreeBuildTime * 1e-6 + " ms.");
        System.out.println("Single Threaded Encoding Time: " + singleThreadEncodingTime * 1e-6 + " ms.");
    }
}