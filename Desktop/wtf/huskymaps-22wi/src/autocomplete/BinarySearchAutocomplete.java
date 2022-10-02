package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<>();
        int index = Collections.binarySearch(this.terms, prefix, CharSequence::compare);
        boolean isMatch = true;
        if (index < 0) {
            isMatch = false;
            index = index + 1 * -1;
        }
        int prefixLength = prefix.length();
        while (isMatch) {
            if (this.terms.get(index).length() < prefix.length()) {
                isMatch = false;
            } else {
                for (int i = 0; i < prefixLength; i++) {
                    if (this.terms.get(index).charAt(i) != prefix.charAt(i)) {
                        isMatch = false;
                    }
                }
                if (isMatch) {
                    matches.add(this.terms.get(index));
                }
                index++;
            }
        }
        return matches;
    }
}