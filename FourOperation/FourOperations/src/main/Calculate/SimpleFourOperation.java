package main.Calculate;

import java.io.*;
import java.util.*;

/*简单四则运算*/
public class SimpleFourOperation {

    public List<String> operatorsList;
    public List<String> operatorExpressionList;
    public List<String> correctAnswerList;

    public SimpleFourOperation() {
        init();
    }
    // 初始化；
    public void init() {
        String[] operators = {"+", "-", "*", "÷"};
        operatorsList = Arrays.asList(operators);
        operatorExpressionList = new ArrayList<>();
        correctAnswerList = new ArrayList<>();
    }

    // 生成运算公式并计算正确结果，将最终的存入List中
    public void generateFormula() {
        int operationNum = 3 ;   //运算数的个数
        int operatorNum = 2;  //运算符的个数

        List<String> newOperators;
        List<Integer> newOperations;
        int newAnswer;
        //计算运算式答案
        do {
            List<String> operators = storeOpInList(operatorNum);
            List<Integer> operations = operations(operationNum, operators);
            newOperators = deepCopy(operators);
            newOperations = deepCopy(operations);
            newAnswer = generateCorrectAnswer(operators, operations);
        } while (newAnswer < 0);

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < operationNum - 1; i++)
        stringBuilder.append(newOperations.get(i) + " " + newOperators.get(i) + " ");
        stringBuilder.append(newOperations.get(operationNum - 1) + " = ");
        operatorExpressionList.add(stringBuilder.toString());
        correctAnswerList.add(String.valueOf(newAnswer));


    }

    // 计算运算式的正确结果
    public int generateCorrectAnswer(List<String> operators, List<Integer> operations) {
        //遍历运算符容器，完成乘除运算
        for (int i = 0; i < operators.size(); i++) {
            String operator = operators.get(i);
            if (operator.equals("*") || operator.equals("÷")) {
                operators.remove(i);                    //乘除符号将其从集合中移除
                int operateLeft = operations.remove(i);      //拿运算符左侧的数字
                int operateRight = operations.remove(i);     //拿运算符右侧的数字
                if (operator.equals("*"))
                    operations.add(i, operateLeft * operateRight);
                else
                    operations.add(i, operateLeft / operateRight);
                i--;  //运算符容器的指针回到原来的位置,防止跳过下一个运算符
            }
        }

        //遍历运算符容器，完成加减运算，当运算符容器为空时，运算结束
        while (!operators.isEmpty()) {
            String operator = operators.remove(0);
            int operateLeft = operations.remove(0);
            int operateRight = operations.remove(0);
            if (operator.equals("+"))
                operateLeft = operateLeft + operateRight;
            else
                operateLeft = operateLeft - operateRight;
            operations.add(0, operateLeft);
        }

//        System.out.println(operations.get(0));
        return operations.get(0);
    }

    //生成operationNum个100以内的随机数并存入List集合中
    public List<Integer> operations(int operationNum, List<String> operators) {
        List<Integer> operations = new ArrayList<>();
        for (int i = 0; i < operationNum; i++) {
            int num = generateNum();
            operations.add(num);
        }
        //当被除数不能整除除数时，随机生成能够整除的除数
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).equals("÷")) {
                int x = decide(operations.get(i), operations.get(i + 1));
                operations.set(i + 1, x);
            }
        }
        return operations;
    }

    /**
     * 随即取x,y为1-100之间，x可以整除y的y值
     * 通过递归实现整除
     */
    private static int decide(int x, int y) {
        Random random = new Random();
        if (x % y != 0) {
            y = random.nextInt(100) + 1;
            return decide(x, y);
        } else
            return y;
    }

    //生成运算数
    private int generateNum() {
        int num = (int) (Math.random() * 99) + 1;
        return num;
    }

    //深拷贝List
    public static <T> List<T> deepCopy(List<T> src) {
        @SuppressWarnings("unchecked")
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dest;
    }

   //生成operatorsNum个运算符并存入List集合中
    public List<String> storeOpInList(int operatorNum) {
        List<String> operatorsList = new ArrayList<>();
        for (int i = 0; i < operatorNum; i++) {
            String operator = generateOperator();
            operatorsList.add(operator);
        }
        return operatorsList;
    }

    //生成运算符
    private String generateOperator() {
        int x = (int) (Math.random() * 4);
        String operator = operatorsList.get(x);
        return operator;
    }

}
