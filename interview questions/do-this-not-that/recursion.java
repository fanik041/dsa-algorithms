package do-this-not-that;

public class recursion {

    //instead of this
    public static int factorial(int n) {
    int result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    //do this
    public static int factorialRecursive(int n) {
        if (n == 1) return 1;
        return n * factorialRecursive(n - 1); 
    }


}
