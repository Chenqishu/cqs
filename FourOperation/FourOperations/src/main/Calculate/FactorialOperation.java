package main.Calculate;

import java.util.Random;

public class FactorialOperation {
    public int num;
    public int result;

    public FactorialOperation() {
        int i;
        result = 1;
        Random random = new Random();
        num = random.nextInt(10) + 1;
        for (i = 1; i <= num; i++) {
            result *= i;
        }

    }
}


