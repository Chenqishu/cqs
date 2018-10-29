package main.Interface;

import main.Calculate.Executionengine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyWindow extends JFrame implements Runnable {

    private JPanel outPanel; //  输出面板
    private JLabel timeLabel;//  时间显示
    private JLabel scoreLabel;//  得分标签
    JLabel textInput; //  题目输入框提示
    JTextField input;//  题目输入框
    private JLabel[] titleLabel;//  题目标签
    private JTextField[] answerArea;
    Executionengine executionEengine;
    int TNum = 0;//答对的题目

    public MyWindow() {
        this.init();
    }

    //  初始化
    private void init() {
        this.setTitle("自动生成简单计算题");
        this.setBounds(0, 0, 800, 600);
        this.initCommand(this);                                                                                 //  初始化控制界面
        this.initOutPanel();
        this.setLayout(null);
        this.setVisible(true);                                                                                          //  显示
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);                                                                  //  关闭窗口时结束
    }

    //  初始化输出面板
    private void initOutPanel() {
        outPanel = new JPanel(null);
        outPanel.setBounds(50, 30, 500, 500);
        outPanel.setBorder(BorderFactory.createLineBorder(Color.red));
        this.add(outPanel);
    }

    //  初始化控制台
    private void initCommand(MyWindow window) {
        int startX = 600, startY = 50;
        int width = 150, height = 50;
        int step = 80;

        //  登陆按钮
     /*   JButton loginButton = new JButton("登陆");
        loginButton.setBounds(0, 0, step, 20);
        loginButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.add(loginButton);*/

        //  背景色按钮
        JButton changeButton = new JButton("背景");
        changeButton.setBounds(20, 5, step, 20);
        changeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color color = JColorChooser.showDialog(outPanel, "背景色", outPanel.getBackground());
                if (color != null) {
                    outPanel.setBackground(color);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.add(changeButton);

        //  生成成绩单按钮
      /*  JButton outButton = new JButton("成绩单");
        outButton.setBounds(step * 2, 0, step, 20);
        outButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.add(outButton);*/

        //  帮助按钮
       /* JButton helpButton = new JButton("帮助");
        helpButton.setBounds(step * 3, 0, step, 20);
        helpButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.add(helpButton);*/

        //  时间显示
        timeLabel = new JLabel("用时 00:00", JLabel.CENTER);
        timeLabel.setBorder(BorderFactory.createLineBorder(Color.black));                                               //  边框和边框颜色
        timeLabel.setFont(new Font("宋体", Font.BOLD, 10));                                                 //  字体
        timeLabel.setBounds(400, 5, width, 20);                                                             //  区域大小
        this.add(timeLabel);

        //  用户输入提示框
        textInput = new JLabel("题目个数", JLabel.CENTER);
        textInput.setBorder(BorderFactory.createLineBorder(Color.black));                                               //  边框和边框颜色
        textInput.setFont(new Font("宋体", Font.BOLD, 10));                                                 //  字体
        textInput.setBounds(startX, startY, width, 20);                                                     //  区域大小
        this.add(textInput);

        //  用户输入框
        input = new JTextField();
        input.setHorizontalAlignment(JTextField.CENTER);                                                                //  水平居中
        input.setBounds(startX, startY + 20, width, 40);
        input.setVisible(true);
        this.add(input);

        //  总计得分
        scoreLabel = new JLabel("得分:",JLabel.CENTER);
        scoreLabel.setFont(new Font("宋体", Font.BOLD, 20));                                                //  字体
        scoreLabel.setForeground(Color.red);                                                                            //  颜色
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.red));
        scoreLabel.setBounds(startX, startY + 100, width, 20);
        scoreLabel.setVisible(false);
        this.add(scoreLabel);


        //  开始按钮
        JButton startButton = new JButton("开始");
        startButton.setBounds(startX, startY + step * 4, width, height);
        startButton.addMouseListener(new MouseListener() {
            Thread time;

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {                                                                   //  如果鼠标释放
                if (startButton.getText() == "开始") {                                                                  //  如果是开始做题
                    if (!checkTitleNum()) {                                                                             //  如果输入不符合要求
//                        input.setText("题目数应该在 0 -- 5 之内");
                        textInput.setText("题目数应该在 1 -- 5 之内");
                        input = null;
                    }
                    startButton.setText("交卷");
                    scoreLabel.setVisible(false);

                    time = new Thread(window);
                    initTitle();                                                                                        //  初始化题库
                    time.start();
                } else {
                    startButton.setText("开始");
                    scoreLabel.setVisible(true);

                    markingPapers();                                                                                    //  判卷
                    time.stop();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        this.add(startButton);
    }

    //  检查题数目是否合法
    private boolean checkTitleNum() {
        boolean ret = true;
        String str = input.getText();
        int num;
        for (int i = 0; i < str.length(); i++) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        num = Integer.parseInt(str);
        if (num < 0 || num > 5)
            ret = false;
        return ret;
    }

    //  初始化题库
    private void initTitle() {
        int startX = 20, startY = 15;
        int width = 400, height = 20;
        int stepX = 410, stepY = 50;
        outPanel.removeAll();
        int gnum = Integer.parseInt(input.getText());

        titleLabel = new JLabel[gnum];
        answerArea = new JTextField[gnum];

        executionEengine = new Executionengine();
        executionEengine.generateFinalOpExp(gnum);

        if (gnum > 0) {
            for (int i = 0; i < executionEengine.FinalExpressionoperatorsList.size(); i++) {
                answerArea[i] = new JTextField();
                answerArea[i].setBounds(startX + stepX, startY + stepY * i, 50, height);
                titleLabel[i] = new JLabel("第" + (i + 1) + "题: " + executionEengine.FinalExpressionoperatorsList.get(i));
                titleLabel[i].setFont(new Font("宋体", Font.BOLD, 15));
                titleLabel[i].setBorder(BorderFactory.createLineBorder(Color.gray));
                titleLabel[i].setBounds(startX, startY + stepY * i, width, height);
                outPanel.add(titleLabel[i]);
                outPanel.add(answerArea[i]);
            }
            outPanel.updateUI();
        }

    }

    //  答案判断
    private void markingPapers() {
        TNum=0;
        if (answerArea.length > 0) {
            for (int i = 0; i < answerArea.length; i++) {
                if (answerArea[i].getText().equals(executionEengine.FinalcorrectanswerList.get(i))) {
                    answerArea[i].setText("对");// 正确
                    TNum++;
                } else {
                    answerArea[i].setText("错" + executionEengine.FinalcorrectanswerList.get(i));  // 错误
                }
            }
        }
//        System.out.print("恭喜您答对了" + TNum);
        scoreLabel.setText("恭喜您答对了" + TNum+"题！答错了"+(answerArea.length -TNum)+"题");
        scoreLabel.setFont(new Font("宋体", Font.BOLD, 10));
    }

//    计时器
    public void run() {                                                                                                 //  完成时间计时
        int second = 0;
        int minute = 0;
        String time;
        timeLabel.setText("用时 00:00");                                                                               // 初始化用时
        while (true) {
            try {
                Thread.sleep(1000);                                                                               //  按时间设置
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            second++;
            if (second == 60) {
                second = 0;
                minute++;
            }
            if (minute < 10) {
                time = "用时 " + "0" + minute + ":";
            } else {
                time = "用时 " + minute + ":";
            }
            if (second < 10) {
                time = time + "0" + second;
            } else {
                time = time + second;
            }
            timeLabel.setText(time);
        }
    }

    public static void main(String[] args) {
        MyWindow window = new MyWindow();
    }
}