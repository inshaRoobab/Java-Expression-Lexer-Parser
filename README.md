# Java Expression Lexer and Parser

A Java-based arithmetic expression evaluator that demonstrates lexical analysis and recursive-descent parsing.

## Features

- Tokenizes arithmetic expressions into numbers and operators.
- Supports multi-digit integers and unary minus.
- Supports addition, subtraction, multiplication, and division.
- Handles parentheses and operator precedence.
- Detects unknown characters and invalid syntax.
- Handles division-by-zero errors.
- Accepts expressions through console input.

## Technologies

- Java
- ArrayList and StringBuilder
- Lexical Analysis
- Recursive-Descent Parsing

## How to Run

Compile the source file:

```bash
javac ExpressionLexer.java
```

Run the program:

```bash
java ExpressionLexer
```

Enter an expression when prompted, for example:

```text
10 + 5 * 2
```

Expected result:

```text
Expression result: 20
```

## How It Works

- **Lexer:** Converts input characters into tokens.
- **factor():** Handles numbers, parentheses, and unary minus.
- **term():** Handles multiplication and division.
- **expression():** Handles addition and subtraction.

## Limitations

- Supports integer arithmetic only.
- Division uses integer division (`7 / 2` returns `3`).
- Exponentiation and variables are not supported.

## Learning Outcomes

Practiced tokenization, parsing, operator precedence, recursion, and exception handling in Java.
