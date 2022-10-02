package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    public void addAll(Collection<? extends CharSequence> terms) {
        for (CharSequence term : terms) {
            if (this.overallRoot == null) {
                this.overallRoot = new Node(term.charAt(0));
            }

            addAll(term, 0, this.overallRoot);
        }
    }

    private void addAll(CharSequence term, int index, Node current) {

        if (term.charAt(index) < current.data) {
            if (current.left == null) {
                current.left = new Node(term.charAt(index));
            }
            addAll(term, index, current.left);
        } else if (term.charAt(index) > current.data) {
            if (current.right == null) {
                current.right = new Node(term.charAt(index));
            }
            addAll(term, index, current.right);
        } else {
            if (index + 1 < term.length()) {
                if (current.mid == null) {
                    current.mid = new Node(term.charAt(index + 1));
                }
                addAll(term, index + 1, current.mid);
            } else {
                current.isTerm = true;
            }
        }
    }

    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<>();
        //find the prefix starting node
        Node startNode = allMatches(prefix, 0, this.overallRoot);
        // iterate through to find all matches with prefix
        if (startNode.isTerm) {
            matches.add(prefix);
        }
        if (startNode.mid != null) {
            allMatches(startNode.mid, matches, "" + prefix);
        }
        return matches;
    }

    private Node allMatches(CharSequence prefix, int preIndex, Node current) {
        if (prefix.charAt(preIndex) < current.data) {
            if (current.left == null) {
                current.left = new Node(prefix.charAt(preIndex));
            }
            return allMatches(prefix, preIndex, current.left);
        } else if (prefix.charAt(preIndex) > current.data) {
            if (current.right == null) {
                current.right = new Node(prefix.charAt(preIndex));
            }
            return allMatches(prefix, preIndex, current.right);

        } else {
            //equal
            if (preIndex + 1 < prefix.length()) {
                if (current.mid == null) {
                    current.mid = new Node(prefix.charAt(preIndex + 1));
                }
                return allMatches(prefix, preIndex + 1, current.mid);
            } else {
                return current;
            }
        }
    }

    private void allMatches(Node current, List<CharSequence> matches, String word) {
        // is current a word?
        if (current.isTerm) {
            // add to matches
            matches.add(word + current.data);
        }

        // move left right or down
        if (current.left != null) {
            allMatches(current.left, matches, word);
        }
        if (current.right != null) {
            allMatches(current.right, matches, word);
        }
        if (current.mid != null) {
            allMatches(current.mid, matches, word + current.data);
        }
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}