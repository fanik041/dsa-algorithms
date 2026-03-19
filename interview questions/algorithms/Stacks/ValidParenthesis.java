package Stacks;

import java.util.Stack;

    public class ValidParenthesis {
        public static boolean isValidParentheses(String s) {
            int minOpen = 0;
            int maxOpen = 0;

            for (char bracket : s.toCharArray()) {
                if (bracket == '(') {
                    minOpen++;
                    maxOpen++;
                } else if (bracket == ')') {
                    minOpen--;
                    maxOpen--;
                } else {
                    minOpen--;
                    maxOpen++;
                }

                if (maxOpen < 0) {
                    return false;
                }

                if (minOpen < 0) {
                    minOpen = 0;
                }
            }

            return minOpen == 0;
        }

    public static void main(String[] args) {
        System.out.println(isValidParentheses("()"));    // true
        System.out.println(isValidParentheses("(())"));  // true
        System.out.println(isValidParentheses("(()"));   // false
        System.out.println(isValidParentheses("())"));   // false
        System.out.println(isValidParentheses(")("));    // false
        System.out.println(isValidParentheses(")*("));
        System.out.println(isValidParentheses("(*)"));  
    }
}
