package com.syk.first;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class game2048 extends JFrame implements KeyListener, ActionListener {

    int [][]datas = new int[4][4];
    //失败标记，控制失败图片加载时机
    int loseFlag=1;
    //得分功能
    int score=0;

    //图片资源路径
    String path = "A";

    //创建菜单分支
    JMenuItem item1 = new JMenuItem("经典");
    JMenuItem item2 = new JMenuItem("霓虹");
    JMenuItem item3 = new JMenuItem("糖果");

    public game2048(){
        initFrame();
        paintFrame();
        //为窗体添加键盘监听事件
        this.addKeyListener(this);
        //设置窗体可见
        setVisible(true);
    }

    public void initData(){
        newData();
        newData();
    }
    //菜单
    public void initMenu(){
        //创建menu
        JMenuBar mb = new JMenuBar();
        //创建栏目对象
        JMenu menu1 = new JMenu("换肤");
        JMenu menu2 = new JMenu("关于游戏");

        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);

        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        mb.add(menu1);
        mb.add(menu2);
        //给窗体设置菜单
        setJMenuBar(mb);
    }

    //初始化窗体
    public void initFrame(){
        //初始化界面数据
        initData();
        initMenu();
        setTitle("2048game");
        setSize(514,538);
        //设置居中
        setLocationRelativeTo(null);
        //设置窗体置顶
        setAlwaysOnTop(true);
        //设置关闭模式
        setDefaultCloseOperation(3);
        //清除窗体默认布局
        setLayout(null);

    }

    public void paintFrame(){
        //保证重新绘制时，能够正常显示
        getContentPane().removeAll();
        JLabel scoreLabel = new JLabel("得分："+score);
        scoreLabel.setBounds(50,20,100,20);
        getContentPane().add(scoreLabel);
        if (loseFlag==2){
            //先加载失败图片，保证失败图片能优先方块加载
            JLabel label = new JLabel(new ImageIcon("src\\image\\"+path+"-lose.png"));
            //摆放位置
            label.setBounds(90,100,334,228);
            getContentPane().add(label);
        }
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                JLabel image = new JLabel(new ImageIcon("src\\image\\"+path+"-"+datas[i][j]+".png"));
                image.setBounds(50+100*j,50+100*i,100,100);
                getContentPane().add(image);
            }
        }
            JLabel background = new JLabel(new ImageIcon("src\\image\\"+path+"-Background.jpg"));
            background.setBounds(40,40,420,420);
            getContentPane().add(background);
            //刷新界面方法
            getContentPane().repaint();
    }

    //判断是否可以左移动
    public boolean checkLeft(){
        int dest[][]=new int[4][4];
        //备份数组
        copyArray(datas,dest);
        boolean flag = false;
        movetoleft();
        //判断是否相等确定是否可移动
        flag = isFlag(dest, flag);
        copyArray(dest,datas);
        return flag;
    }

    public void copyArray(int[][] datas, int[][] dest) {
        for (int i=0;i<datas.length;i++){
            for (int j=0;j<datas[i].length;j++){
                dest[i][j]=datas[i][j];
            }
        }
    }

    //判断是否可以右移动
    public boolean checkRight(){
        int dest[][]=new int[4][4];
        //备份数组
        copyArray(datas,dest);
        boolean flag = false;
        movetoRight();
        //判断是否相等确定是否可移动
        flag = isFlag(dest, flag);
        copyArray(dest,datas);
        return flag;
    }

    private boolean isFlag(int[][] dest, boolean flag) {
        lo:
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j] != dest[i][j]) {
                    flag = true;
                    break lo;
                }
            }
        }
        //备份数据的还原
        copyArray(dest, datas);
        return flag;
    }

    //判断是否可以上移动
    public boolean checkTop(){
        int dest[][]=new int[4][4];
        //备份数组
        copyArray(datas,dest);
        boolean flag = false;
        //模拟移动
        movetoTop();
        //判断是否相等确定是否可移动
        flag = isFlag(dest, flag);
        copyArray(dest,datas);
        return flag;
    }
    //判断是否可以下移动
    public boolean checkBottom(){
        int dest[][]=new int[4][4];
        //备份数组
        copyArray(datas,dest);
        boolean flag = false;
        //模拟移动
        movetoBottom();
        //判断是否相等确定是否可移动
        flag = isFlag(dest, flag);
        copyArray(dest,datas);
        return flag;
    }

    //检查是否可以移动
    public void check(){
        if (checkLeft()==false&&checkRight()==false&&
                checkBottom()==false&&checkTop()==false){
            loseFlag=2;
        }
    }

    public static void main(String[] args) {
        new game2048();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == 37) {
            movetoleft();
            newData();
        }
        if (keyCode == 38) {
            movetoTop();
            newData();
        }
        if (keyCode == 39) {
            movetoRight();
            newData();
        }
        if (keyCode == 40) {
            movetoBottom();
            newData();
        }
        //每次移动完，检查是否游戏失败
        check();

        paintFrame();
    }

    public void newData(){
        int row[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int col[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int count=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (datas[i][j]==0){
                    row[count]=i;
                    col[count]=j;
                    count++;
                }
            }
        }
        if (count!=0){
            Random random = new Random();
            int i = random.nextInt(count);
            datas[row[i]][col[i]] = 2;
        }
    }

    public void movetoTop() {
        changeRC();
        movetoleft();
        changeRC();
    }

    public void movetoBottom() {
        reverseTT();
        changeRC();
        movetoleft();
        changeRC();
        reverseTT();
    }

    //datas上下反转
    public void reverseTT() {
        int temp;
        for (int i=0;i<4;i++){
            for (int start=0,end=datas[i].length-1;start<end;start++,end--){
                temp=datas[start][i];
                datas[start][i]=datas[end][i];
                datas[end][i]=temp;
            }
        }
    }

    public void changeRC() {
        int temp[][] = new int[4][4];
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                temp[j][i]=datas[i][j];
            }
        }
        datas=temp;
    }

    public void movetoRight() {
        reverseDatas();
        movetoleft();
        reverseDatas();
    }

    public void reverseDatas() {
        int temp;
        for (int i=0;i<4;i++){
            for (int start=0,end=datas[i].length-1;start<end;start++,end--){
                temp=datas[i][start];
                datas[i][start]=datas[i][end];
                datas[i][end]=temp;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void movetoleft(){
        for (int q=0;q<4;q++){
            int temp[] = new int[4];
            int index = 0;
            for (int i=0;i<4;i++){
                if (datas[q][i]!=0){
                    temp[index]=datas[q][i];
                    index++;
                }
            }
            datas[q]=temp;

            for (int i=0;i<3;i++){
                if (datas[q][i]==datas[q][i+1]){
                    score+=datas[q][i];
                    datas[q][i]*=2;
                    for (int j=i+1;j<3;j++){
                        datas[q][j]=datas[q][j+1];
                    }
                    datas[q][3]=0;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //获取哪一个对象被点击了
        Object source = e.getSource();
        if (source==item1){
            path="A";
        }
        if (source==item2){
            path="B";
        }
        if (source==item3){
            path="C";
        }
        paintFrame();
    }
}
