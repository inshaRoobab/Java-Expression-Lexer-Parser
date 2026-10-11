
import java.util.ArrayList;
import java.util.Scanner;

public class ExpressionLexer {

    static class Token {
        String type;
        String value;

        public Token(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    static class LexerResult {
        ArrayList<Token> tokens;
        ArrayList<String> errors;

        LexerResult(ArrayList<Token> tokens, ArrayList<String> errors) {
            this.tokens = tokens;
            this.errors = errors;
        }
    }

    static LexerResult lexer(String inp) {
        int i = 0;
        ArrayList<Token> tokens = new ArrayList<>();
        ArrayList<String> errors = new ArrayList<>();

        while (i < inp.length()) {
            char ch = inp.charAt(i);

            if (Character.isDigit(ch)) {
                StringBuilder number = new StringBuilder();

                while (i < inp.length()
                        && Character.isDigit(inp.charAt(i))) {
                    number.append(inp.charAt(i));
                    i++;
                }

                tokens.add(new Token("NUMBER", number.toString()));

            } else if (ch == '+') {
                tokens.add(new Token("PLUS", "+"));
                i++;

            } else if (ch == '-') {
                tokens.add(new Token("MINUS", "-"));
                i++;

            } else if (ch == '*') {
                tokens.add(new Token("MULTIPLY", "*"));
                i++;

            } else if (ch == '/') {
                tokens.add(new Token("DIVIDE", "/"));
                i++;

            } else if (ch == '(') {
                tokens.add(new Token("LEFT_PAREN", "("));
                i++;

            } else if (ch == ')') {
                tokens.add(new Token("RIGHT_PAREN", ")"));
                i++;

            } else if (Character.isWhitespace(ch)) {
                i++;

            } else {
                errors.add("Unknown character: " + ch);
                i++;
            }
        }

        return new LexerResult(tokens, errors);
    }

    static class Parser {
        ArrayList<Token> tokens;
        int position = 0;

        Parser(ArrayList<Token> tokens) {
            this.tokens = tokens;
        }

        int factor() {
            if (position >= tokens.size()) {
                throw new IllegalArgumentException(
                    "Expected a number or '(' but input ended."
                );
            }

            Token current = tokens.get(position);

            if (current.type.equals("MINUS")) {
                position++;
                return -factor();
            }

            if (current.type.equals("NUMBER")) {
                position++;
                return Integer.parseInt(current.value);
            }

            if (current.type.equals("LEFT_PAREN")) {
                position++;
                int result = expression();

                if (position >= tokens.size()
                        || !tokens.get(position).type.equals("RIGHT_PAREN")) {
                    throw new IllegalArgumentException("Expected ')'.");
                }

                position++;
                return result;
            }

            throw new IllegalArgumentException(
                "Expected a number or '(', but found: " + current.value
            );
        }

        int term() {
            int result = factor();

            while (position < tokens.size()
                    && (tokens.get(position).type.equals("MULTIPLY")
                    || tokens.get(position).type.equals("DIVIDE"))) {

                Token operator = tokens.get(position);
                position++;

                int nextValue = factor();

                if (operator.type.equals("MULTIPLY")) {
                    result = result * nextValue;
                } else {
                    if (nextValue == 0) {
                        throw new ArithmeticException(
                            "Cannot divide by zero."
                        );
                    }

                    result = result / nextValue;
                }
            }

            return result;
        }

        int expression() {
            int result = term();

            while (position < tokens.size()
                    && (tokens.get(position).type.equals("PLUS")
                    || tokens.get(position).type.equals("MINUS"))) {

                Token operator = tokens.get(position);
                position++;

                int nextValue = term();

                if (operator.type.equals("PLUS")) {
                    result = result + nextValue;
                } else {
                    result = result - nextValue;
                }
            }

            return result;
        }
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter an expression: ");
            String inp = sc.nextLine();

            LexerResult result = lexer(inp);

            System.out.println("\nTokens:");

            for (Token t : result.tokens) {
                System.out.println(t.type + ": " + t.value);
            }

            if (!result.errors.isEmpty()) {
                System.out.println("\nLexer errors:");

                for (String error : result.errors) {
                    System.out.println(error);
                }

            } else {
                System.out.println("\nNo lexer errors found.");

                Parser parser = new Parser(result.tokens);

                try {
                    int answer = parser.expression();

                    if (parser.position != result.tokens.size()) {
                        throw new IllegalArgumentException(
                            "Unexpected token: "
                            + result.tokens.get(parser.position).value
                        );
                    }

                    System.out.println("Expression result: " + answer);
                    System.out.println("Final position: " + parser.position);

                } catch (ArithmeticException | IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}
