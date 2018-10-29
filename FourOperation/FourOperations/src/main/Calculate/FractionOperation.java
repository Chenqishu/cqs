package main.Calculate;



import java.util.*;


/*分数运算*/
public class FractionOperation {

    public List<main.Calculate.Fraction> correctAnswerList = new ArrayList<>();
    public List<String> operatorExpressionList = new ArrayList<>();
    private int operationNum = 2;   //生成运算数的数量
    private int operatorNum = operationNum - 1;   // 生成运算符的数量

    public void engine() {
        SimpleFourOperation fourOperation = new SimpleFourOperation();
        fourOperation.init();

        List<main.Calculate.Fraction> newFractions;
        List<String> newOperators;
        main.Calculate.Fraction newAnswer;
        do {
            List<String> operators = fourOperation.storeOpInList(operatorNum);
            newOperators = fourOperation.deepCopy(operators);
            List<main.Calculate.Fraction> fractionsList = generateFractionList();
            newFractions = fourOperation.deepCopy(fractionsList);
            newAnswer = generateCorrectAnswer(operators, fractionsList);
        } while (isLessThanZero(newAnswer));
        printFractionExpression(newFractions, newOperators);
        correctAnswerList.add(newAnswer);

    }

    //判断分数是否为0
    private boolean isLessThanZero(main.Calculate.Fraction fraction) {
        int numerator = fraction.getNumerator();
        int denominator = fraction.getDenominator();
        if (numerator < 0 && denominator > 0)
            return true;
        else if (numerator > 0 && denominator < 0)
            return true;
        else
            return false;
    }

    //生成一组运算分数
    private List<main.Calculate.Fraction> generateFractionList() {
        List<main.Calculate.Fraction> fractionsList = new ArrayList<>();
        for (int i = 0; i < operationNum; i++)
            fractionsList.add(generateFraction());
        return fractionsList;
    }

    //打印分数运算表达式
    private void printFractionExpression(List<main.Calculate.Fraction> fractionsList, List<String> operators) {
        StringBuilder stringBuilder = new StringBuilder();
        main.Calculate.Fraction fraction;
        for (int i = 0; i < operationNum - 1; i++) {
            fraction = fractionsList.get(i);
            stringBuilder.append(pringFraction(fraction));
            stringBuilder.append(" " + operators.get(i) + " ");
        }
        fraction = fractionsList.get(operationNum - 1);
        stringBuilder.append(pringFraction(fraction) + " = ");

        operatorExpressionList.add(stringBuilder.toString());
    }

    // 计算运算式的正确结果
    public main.Calculate.Fraction generateCorrectAnswer(List<String> operators, List<main.Calculate.Fraction> operations) {
        //遍历运算符容器，完成乘除运算
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            if (operator.equals("*") || operator.equals("÷")) {
                operators.remove(i);                    //乘除符号将其从集合中移除
                main.Calculate.Fraction fractionLeft = operations.remove(i);      //拿运算符左侧的数字
                main.Calculate.Fraction fractionRight = operations.remove(i);     //拿运算符右侧的数字
                if (operator.equals("*"))
                    operations.add(fractionMultiple(fractionLeft, fractionRight));
                else
                    operations.add(fractionDivide(fractionLeft, fractionRight));
                i--;  //运算符容器的指针回到原来的位置,防止跳过下一个运算符
            }
        }

        //遍历运算符容器，完成加减运算，当运算符容器为空时，运算结束
        while (!operators.isEmpty()) {
            String operator = operators.remove(0);
            main.Calculate.Fraction fractionLeft = operations.remove(0);
            main.Calculate.Fraction fractionRight = operations.remove(0);
            if (operator.equals("+"))
                fractionLeft = fractionAdd(fractionLeft, fractionRight);
            else
                fractionLeft = fractionSubtract(fractionLeft, fractionRight);
            operations.add(0, fractionLeft);
        }

        //返回计算结果
        return operations.get(0);
    }

    // 真分数相乘运算
    public main.Calculate.Fraction fractionMultiple(main.Calculate.Fraction fractionLeft, main.Calculate.Fraction fractionRight) {
        int numerator = fractionLeft.getNumerator() * fractionRight.getNumerator();
        int denominator = fractionLeft.getDenominator() * fractionRight.getDenominator();

        main.Calculate.Fraction fraction = getGcdAndsimplificationFraction(numerator, denominator);
        return fraction;
    }

    // 真分数相除运算
    public main.Calculate.Fraction fractionDivide(main.Calculate.Fraction fractionLeft, main.Calculate.Fraction fractionRight) {
        int numerator = fractionLeft.getNumerator() * fractionRight.getDenominator();
        int denominator = fractionLeft.getDenominator() * fractionRight.getNumerator();

        main.Calculate.Fraction fraction = getGcdAndsimplificationFraction(numerator, denominator);
        return fraction;
    }

    // 真分数相加运算
    public main.Calculate.Fraction fractionAdd(main.Calculate.Fraction fractionLeft, main.Calculate.Fraction fractionRight) {
        int denominator;
        int numerator;
        //如果分母相同时直接分子相加
        if (fractionLeft.getDenominator() == fractionRight.getDenominator()) {
            denominator = fractionLeft.getDenominator();
            numerator = fractionLeft.getNumerator() + fractionRight.getNumerator();
        } else {
            denominator = fractionLeft.getDenominator() * fractionRight.getDenominator();
            numerator = fractionLeft.getNumerator() * fractionRight.getDenominator()
                    + fractionRight.getNumerator() * fractionLeft.getDenominator();
        }
        main.Calculate.Fraction fraction = getGcdAndsimplificationFraction(numerator, denominator);
        return fraction;
    }

    // 真分数相减运算
    public main.Calculate.Fraction fractionSubtract(main.Calculate.Fraction fractionLeft, main.Calculate.Fraction fractionRight) {
        int denominator;
        int numerator;
        //如果分母相同时直接分子相加
        if (fractionLeft.getDenominator() == fractionRight.getDenominator()) {
            denominator = fractionLeft.getDenominator();
            numerator = fractionLeft.getNumerator() - fractionRight.getNumerator();
        } else {
            denominator = fractionLeft.getDenominator() * fractionRight.getDenominator();
            numerator = fractionLeft.getNumerator() * fractionRight.getDenominator()
                    - fractionRight.getNumerator() * fractionLeft.getDenominator();
        }
        main.Calculate.Fraction fraction = getGcdAndsimplificationFraction(numerator, denominator);
        return fraction;
    }

    //获得最大公约数并且将真分数化简
    public main.Calculate.Fraction getGcdAndsimplificationFraction(int numerator, int denominator) {
        int greatestCommonDivisor = greatestCommonDivisor(numerator, denominator);
        numerator /= greatestCommonDivisor;
        denominator /= greatestCommonDivisor;
        return new main.Calculate.Fraction(numerator, denominator);
    }


    public String pringFraction(main.Calculate.Fraction fraction) {
        String fractionString = String.valueOf(fraction.getNumerator()) + "/"
                + String.valueOf(fraction.getDenominator());
        return fractionString;
    }

    //生成一个真分数
    private main.Calculate.Fraction generateFraction() {
        int numerator = generateNum();
        int denominator = regenerate(numerator, generateNum());
        int greatestCommonDivisor = greatestCommonDivisor(numerator, denominator);
        if (greatestCommonDivisor > 1) {
            numerator /= greatestCommonDivisor;
            denominator /= greatestCommonDivisor;
        }
        main.Calculate.Fraction fraction = new Fraction(numerator, denominator);
        return fraction;
    }

    //递归生成与分子不相等的分母
    private int regenerate(int numerator, int denominator) {
        if (numerator == denominator) {
            denominator = generateNum();
            return regenerate(numerator, denominator);
        } else {
            return denominator;
        }
    }

    //生成1-20随机数
    private int generateNum() {
        int num = (int) (Math.random() * 20) + 1;
        return num;
    }

    // 求a和b的最大公约数
    private int greatestCommonDivisor(int a, int b) {
        if (a < b) {
            int c = a;
            a = b;
            b = c;
        }
        int r = a % b;
        while (r != 0) {
            a = b;
            b = r;
            r = a % b;
        }
        return b;
    }

}
