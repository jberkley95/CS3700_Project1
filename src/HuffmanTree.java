/**
 * @author John Berkley
 * CPP Class: CS3700
 * Date Created: Oct 20, 2018
 */

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    final int frequency;

    HuffmanTree(int frequency) {
        this.frequency = frequency;
    }

    //CompareTo for priority queue
    @Override
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}