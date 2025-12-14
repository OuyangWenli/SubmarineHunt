import java.awt.*;

public class SubmarineObj extends GameObj {
    Image currentImage;
    //存活标记
    boolean live = true;
    //移动状态标记
    boolean movingLeft = false;
    boolean movingRight = false;
    //关联的军舰对象，用于增加分数
    ShipObj ship;
    //被摧毁时间戳
    long destroyedTime = 0;
    //被摧毁图片显示时间
    long destroyedDuration = 700;
    //被摧毁图片
    Image destroyedLeftImage = GameImage.destroyedSubmarineLeftImage;
    Image destroyedRightImage = GameImage.destroyedSubmarineRightImage;

    //构造方法，初始化潜艇对象的属性，宽度固定为原图宽度30，高度固定为原图宽度10,速度固定为4
    //潜艇的方向随机，y坐标初始为280-615之间的随机值
    public SubmarineObj(MainWindow frame, ShipObj ship) {
        super(GameImage.submarineLeftImage, GameImage.submarineRightImage, 0, 0, 30, 10, 0, frame);
        this.ship = ship;
        if (Math.random() < 0.25) {
            speed = 1;
        } else if (Math.random() < 0.75) {
            speed = 2;
        } else {
            speed = 3;
        }
        if (Math.random() < 0.5) {
            movingLeft = true;
            currentImage = image1;
            x = frame.getWidth();
        } else {
            movingRight = true;
            currentImage = image2;
            x = -width;
        }
        this.y = (int) (Math.random() * 335) + 280;
    }
    
    //碰撞检测，若潜艇与炸弹碰撞，则将存活标记设为false,并更换为被摧毁图片
    public void checkCollision() {
        for (int i = 0; i < frame.bombList.size(); i++) {
            BombObj bomb = frame.bombList.get(i);
            if (this.getRect().intersects(bomb.getRect())) {
                //增加分数
                ship.bombCount++;
                frame.score++;
                live = false;
                bomb.live = false;
                if (movingLeft) {
                    currentImage = destroyedLeftImage;
                } else if (movingRight) {
                    currentImage = destroyedRightImage;
                }
                break;
            }
        }
    }

    //重写绘制方法，实现潜艇的绘制
   @Override
    public void paintSelf(Graphics g) {
        g.drawImage(currentImage, x, y, width, height, frame);
        if(!live){
            return;
        }
        if (movingLeft) {
            x -= speed;
        } 
        else if (movingRight) {
            x += speed;
        }
        //边界检测，若潜艇完全移出窗口，则将存活标记设为false
        if (movingLeft && x + width < 0) {
            live = false;
        } 
        else if (movingRight && x > frame.getWidth()) {
            live = false;
        }

        //检查与炸弹的碰撞
        checkCollision();

        //如果潜艇已被摧毁，则不再移动
        //在此处记录被摧毁时间，绘制时检查被摧毁时间是否超过显示时间，超过则不再绘制
        if (!live) {
            //记录被摧毁时间
            if (destroyedTime == 0) {
                destroyedTime = System.currentTimeMillis();
            }
        }
    }
    
}
