package mytest;

/**
 * Created by Administrator on 2017/8/12.
 */
public class Car {
    private String color;
    private int hight;
    private  int width;

    public void drive(){
        System.out.println("我的颜色是:"+color);
        System.out.println("我的高度是:"+hight);
        System.out.println("我的宽度是:"+width);
        System.out.println("开车了...");
    }

    public Car(){}

    public Car(String color, int hight, int width) {
        this.color = color;
        this.hight = hight;
        this.width = width;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
