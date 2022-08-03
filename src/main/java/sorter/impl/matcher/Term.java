package sorter.impl.matcher;

public class Term {

    private final String term;
    private final boolean isNeg;

    /**
     * Create new term
     * @param term valid term which contains only one or more alphanumerical characters
     */
    public Term(String term){
        int i = 0;
        while(term.charAt(i) == '!'){
            i += 1;
        }
        this.isNeg = !(i % 2 == 0);
        this.term = term.substring(i);
    }


    public String getTerm() {
        return term;
    }

    /**
     * @return true if term is negated otherwise false
     */
    public boolean isNeg(){
        return isNeg;
    }
}
