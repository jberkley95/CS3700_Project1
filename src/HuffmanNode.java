/**
 * @author John Berkley
 * CPP Class: CS3700
 * Date Created: Oct 20, 2018
 */
class HuffmanNode extends HuffmanTree {
    final HuffmanTree left, right;

    HuffmanNode(HuffmanTree left, HuffmanTree right) {
        super(left.frequency + right.frequency);
        this.left = left;
        this.right = right;
    }
}