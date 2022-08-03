package sorter.impl.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Expression {

    private final List<Term> terms;
    private final List<Character> operators;

    /**
     * Create new expression checker. <br/>
     * Throws InvalidExpressionException if expression is not in the correct format
     * @param expression expression in format: <br/>
     * expression   =   label | expr, op, expr | not, expr ; <br/>
     * not          =   "!" ; <br/>
     * op           =   space, ( "&" | "|" ), space ; <br/>
     * label        =   ? one or more alphanumerical characters ? ; <br/>
     * space        =   ? zero or more space characters ? ; <br/>
     */
    public Expression(String expression){
        terms = new ArrayList<>();
        operators = new ArrayList<>();

        parseExpression(expression);
    }

    private void parseExpression(String expression){
        for(String or : expression.split("\\|")){
            String[] ands = or.split("&");
            terms.add(new Term(ands[0].trim()));
            for (int i = 1; i < ands.length; i++) {
                terms.add(new Term(ands[i].trim()));
                operators.add('&');
            }
            operators.add('|');
        }
    }

    /**
     * Tests if the input labels matches the expression
     * @param labels labels to check
     * @return true if expression matching with label, otherwise false
     */
    public boolean checkMatch(Set<String> labels){
        Term term = terms.get(0);
        boolean result1 = labels.contains(term.getTerm()) == !term.isNeg();
        Character operator = operators.get(0);
        for(int i = 1; i < terms.size(); i++){
            term = terms.get(i);

            //if term matches with tested labels
            boolean result2 = labels.contains(term.getTerm()) == !term.isNeg();

            //logical evaluation of the "subexpression"
            result1 = (operator == '&') ? result1 && result2 : result1 || result2;
            operator = operators.get(i);

            //if left operand is true and operator is 'or', expression will be true.
            if(result1 && operator == '|'){
                return true;
            }
        }
        return result1;
    }
}
