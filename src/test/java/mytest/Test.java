package mytest;

import java.io.IOException;
import java.util.Scanner;

//控制台简单五子棋
public class Test{

    //二维数组作为棋盘
    private static char[][] board=new char[16][16];
    //已下棋子数目
    private static int cnt;
    //分别代表玩家1,2的棋子
    private static char[] qizi={'#','@'};
    //八方向搜索检测
    private static int[] dx={-1,0,-1,-1,1,1,0,1};
    private static int[] dy={0,-1,-1,1,-1,1,1,0};

    //输入检测
    private static int CheckInput(int x,int y){

        if(board[x][y]!='+')
            return 0;
        if(x<1||x>15||y<1||y>15)
            return 0;
        return 1;
    }

    //边界检测
    private static int Check(int x,int y){
        if(x>15||x<1||y>15||y<1)
            return 0;
        return 1;
    }
    //检测玩家是否获胜,需要传入玩家编号，及当前落点坐标
    private static int CheckAns(int num,int x,int y){
        if(cnt==15*15)
            return 1;
        //横竖 左斜 右斜 四个方向判断是否有五个棋子连在一起
        for(int i=0;i<4;i++){
            int sum=1;
            int xx=x+dx[i];
            int yy=y+dy[i];
            while(board[xx][yy]==qizi[num]&&Check(xx,yy)==1){
                sum++;
                xx=xx+dx[i];
                yy=yy+dy[i];
            }
            xx=x+dx[7-i];
            yy=y+dy[7-i];
            while(board[xx][yy]==qizi[num]&&Check(xx,yy)==1){
                sum++;
                xx=xx+dx[7-i];
                yy=yy+dy[7-i];
            }
            if(sum>=5)
                return 1;
        }
        return 0;
    }

    //初始化
    private static void Init(){
        cnt=0;
        for(int i=1;i<=15;i++)
            for(int j=1;j<=15;j++)
                board[i][j]='+';
    }

    //输出棋盘
    private static void Print(){
        int i,j;
        for(i=1;i<=15;i++){
            for(j=1;j<=15;j++){

                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public static int run(){

        int x,y;
        Init();
        //当前棋手编号,默认0号先下
        int num=0;
        while(true){
            Print();
            System.out.print(num+1+"号选手请输入您下棋的坐标,应以x y形式: ");
            Scanner sc =new Scanner(System.in);
            while(true){
                x=sc.nextInt();
                y=sc.nextInt();
                if(CheckInput(x,y)==1)
                    break;
                System.out.print("输入坐标不合法，请重新输入： ");
            }
            board[x][y]=qizi[num];
            if(CheckAns(num,x,y)==1){
                return num+1;
            }
            num=1-num;
        }
    }

    public static void main(String[] args){

        System.out.println("欢迎使用lkl的五子棋!");
        int ans=run();
        System.out.println("恭喜"+ans+"号选手获得最终的胜利");
    }
}