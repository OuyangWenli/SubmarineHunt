import java.awt.*;

public class BombObj extends GameObj {
    boolean live = true;//存活标记

    long destroyedTime = 0;//被摧毁时间戳
    long destroyedDuration = 350;//被摧毁图片显示时间
    Image explosionImage;
    //构造方法，宽度固定为原图宽度10，高度固定为原图高度15，速度固定为1
    public BombObj(int x, MainWindow frame) {
        super(GameImage.bombImage, x, 270, 10, 15, 1, frame);
        //随机选择一种爆炸图片
        if (Math.random() < 0.3) {
            explosionImage = GameImage.explosion1Image;
        }
        else if (Math.random() < 0.6) {
            explosionImage = GameImage.explosion2Image;
        } 
        else {
            explosionImage = GameImage.explosion3Image;
        }
    }
    //绘制方法的重载，y坐标初始为270，x坐标等于参数x（绘制时传入军舰中点的x坐标）
    //碰撞检测在潜艇类SubmarineObj中实现
    public void paintSelf(Graphics g) {
        if(!live){
            image1 = explosionImage;
            if(destroyedTime == 0){
                destroyedTime = System.currentTimeMillis();
            }
        }
        g.drawImage(image1, this.x, this.y, frame);
        //如果炸弹已爆炸，则不再移动
        if(!live){
            return;
        }
        this.y += speed;
        //边界检测，若y坐标大于窗口高度，就将存活标记设为false
        if (this.y > frame.getHeight()) {
            this.live = false;
        }
    }
}
