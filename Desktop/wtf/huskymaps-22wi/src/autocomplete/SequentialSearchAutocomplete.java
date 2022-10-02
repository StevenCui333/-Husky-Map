package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
    }


    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<>();
        int prefixLength = prefix.length();

        for (CharSequence term : this.terms) {
            boolean isMatch = true;
            if (term.length() < prefixLength) {
                isMatch = false;
            } else {
                for (int i = 0; i < prefixLength; i++) {
                    if (term.charAt(i) != prefix.charAt(i)) {
                        isMatch = false;
                    }
                }
            }

            if (isMatch) {
                matches.add(term);
            }
        }
        return matches;
    }
}