//游戏相关的游戏元素父类，包含游戏元素的公共属性和方法
import java.awt.*;

public class GameObj {
    //游戏元素的图片,有些游戏元素有向左、向右两张图片，若没有两张就用image1表示
    Image image1;
    Image image2 = null;
    //游戏元素的x坐标
    int x;
    //游戏元素的y坐标
    int y;
    //游戏元素的宽度
    int width;
    //游戏元素的高度
    int height;
    //游戏元素的速度
    double speed;
    //游戏元素的窗口
    MainWindow frame;

    //单图片构造方法
    public GameObj(Image image, int x, int y, int width, int height, double speed, MainWindow frame) {
        this.image1 = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }
    //双图片构造方法
    public GameObj(Image image1, Image image2,int x, int y, int width, int height, double speed, MainWindow frame) {
        this.image1 = image1;
        this.image2 = image2;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.frame = frame;
    }
    // 绘制游戏元素的方法
    public void paintSelf(Graphics g) {
        g.drawImage(image1, x, y, frame);
    }

    //获取游戏元素的矩形区域，用于后续碰撞检测
    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}
