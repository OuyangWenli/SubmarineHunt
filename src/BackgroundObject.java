//背景类，实现背景的打印
import java.awt.*;

public class BackgroundObject extends GameObj {
    //构造方法，初始化背景对象的属性，位置固定在(0,0)，宽和高固定等于窗口的宽和高，速度固定为0
    public BackgroundObject(Image image, MainWindow frame) {
        super(image, 0, 0, frame.getWidth(), frame.getHeight(), 0, frame);
    }
    //重写绘制方法，使背景图片填充整个窗口
    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(image1, 0, 0, frame.getWidth(), frame.getHeight(), frame);
    }
}
