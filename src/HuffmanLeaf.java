/**
 * @author John Berkley
 * CPP Class: CS3700
 * Date Created: Oct 20, 2018
 */
class HuffmanLeaf extends HuffmanTree {
    final char value;

    HuffmanLeaf(int freq, char value) {
        super(freq);
        this.value = value;
    }
}