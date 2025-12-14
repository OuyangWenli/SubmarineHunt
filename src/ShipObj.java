import java.awt.*;
import java.awt.event.*;

public class ShipObj extends GameObj {
    Image currentImage;
    //移动状态标记
    boolean movingLeft = false;
    boolean movingRight = false;
    //时间戳
    long lastLaunchTime = 0;
    long interval = 1300; // 发射间隔1300毫秒
    //剩余炸弹数
    int bombCount = 20;

    //构造方法，初始化军舰对象的属性，y坐标固定为250，宽度固定为原图宽度50，高度固定为原图宽度20
    public ShipObj(Image leftImage,Image rightImage, int x, MainWindow frame) {
        super(leftImage, rightImage, x, 250, 50, 20, 6, frame);
        currentImage = image1;
        //添加键盘事件
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                //按A键，军舰向左移动
                if (key == KeyEvent.VK_A) {
                    movingLeft = true;
                    currentImage = image1;
                }
                //按D键，军舰向右移动
                else if (key == KeyEvent.VK_D) {
                    movingRight = true;
                    currentImage = image2;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                // 3. 松开时取消状态
                if (key == KeyEvent.VK_A) {
                    movingLeft = false;
                }
                if (key == KeyEvent.VK_D) {
                    movingRight = false;
                }
            } 
        });

    }
    //重写绘制方法，实现军舰的绘制
    @Override
    public void paintSelf(Graphics g) {
        g.drawImage(currentImage, x, y, width, height, frame);
        if (movingLeft) {
            x -= speed;
        } 
        else if (movingRight) {
            x += speed;
        }

        //边界检测
        if (x < 0) {
            x = 0;
        } 
        else if (x > frame.getWidth() - width - 4) {
            x = frame.getWidth() - width - 4;
        }
    }
    //发射炸弹的方法，在列表中添加一个炸弹对象
    public void addBomb() {
        if( bombCount > 0 ) {
            BombObj bomb = new BombObj(x + width / 2 - 5, frame);
            frame.bombList.add(bomb);
            bombCount--;
        }
    }
}
