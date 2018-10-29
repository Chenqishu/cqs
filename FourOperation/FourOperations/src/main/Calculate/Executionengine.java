package main.Calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Executionengine {
    public  List<String> FinalExpressionoperatorsList;//最终输出的题目
    public   List<String> FinalcorrectanswerList;//最终题目相对于正确答案

    public Executionengine(){
        FinalExpressionoperatorsList = new ArrayList<>();
        FinalcorrectanswerList =new ArrayList<>();
    }

  //获取总的题目和正确答案
    public void generateFinalOpExp(int titleNum){
        SimpleFourOperation simpleFourOperation = new SimpleFourOperation();
        FractionOperation fractionOperation = new FractionOperation();

           Random random = new Random();
          int simpleFourOperationNum = random.nextInt(titleNum-1);//产生简单四则运算题数
          int FractionNum =  titleNum -1- simpleFourOperationNum;//产生简单分数运算题数
          int FactorialNum=1;//阶乘运算固定为1

        //获取简单四则运算的题目和正确答案
          for (int i = 0; i < simpleFourOperationNum; i++) {
              simpleFourOperation.generateFormula();
            //System.out.println("整数题目："+fourCalculations.operatorExpressionList.get(i));
            FinalExpressionoperatorsList.add(simpleFourOperation.operatorExpressionList.get(i));
           // System.out.println("整数答案："+fourCalculations.correctAnswerList.get(i));
            FinalcorrectanswerList.add(simpleFourOperation.correctAnswerList.get(i));
        }

        //获取阶乘运算的题目和正确答案
        for (int i = 0; i < FractionNum; i++) {
            fractionOperation.engine();
           //System.out.println("分数题目："+fractionOperation.operatorExpressionList.get(i) );
            FinalExpressionoperatorsList.add(fractionOperation.operatorExpressionList.get(i));
           // System.out.println("分数答案："+fractionOperation.pringFraction(fractionOperation.correctAnswerList.get(i)));
            FinalcorrectanswerList.add(fractionOperation.pringFraction(fractionOperation.correctAnswerList.get(i)));
        }

        //获取简单分数运算的题目和正确答案
        for (int i = 0; i < FactorialNum; i++) {
            FactorialOperation factorialOperation=new FactorialOperation();
         //  System.out.println("阶乘题目："+factorialOperation.num+"!");
            FinalExpressionoperatorsList.add(String.valueOf(factorialOperation.num )+"!");
           // System.out.println("阶乘答案："+factorialOperation.result);
            FinalcorrectanswerList.add(String.valueOf(factorialOperation.result));
        }

    }

}
