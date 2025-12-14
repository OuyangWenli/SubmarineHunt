//游戏相关的图片类，用于获取图片资源
import java.awt.*;

public class GameImage {
    //获取背景图片
    public static Image backgroundImage = Toolkit.getDefaultToolkit().getImage("images/background.png");
    //获取舰首向左军舰图片
    public static Image shipLeftImage = Toolkit.getDefaultToolkit().getImage("images/ship0.png");
    //获取舰首向右军舰图片
    public static Image shipRightImage = Toolkit.getDefaultToolkit().getImage("images/ship1.png");
    //获取舰首向左潜艇图片
    public static Image submarineLeftImage = Toolkit.getDefaultToolkit().getImage("images/h2.png");
    //获取舰首向右潜艇图片
    public static Image submarineRightImage = Toolkit.getDefaultToolkit().getImage("images/q1.png");
    //获取舰首向左被击毁潜艇图片
    public static Image destroyedSubmarineLeftImage = Toolkit.getDefaultToolkit().getImage("images/q2.png");
    //获取舰首向右被击毁潜艇图片
    public static Image destroyedSubmarineRightImage = Toolkit.getDefaultToolkit().getImage("images/r1.png");
    //获取深水炸弹图片
    public static Image bombImage = Toolkit.getDefaultToolkit().getImage("images/boom.png");
    //获取爆炸图片
    public static Image explosion1Image = Toolkit.getDefaultToolkit().getImage("images/b.png");
    public static Image explosion2Image = Toolkit.getDefaultToolkit().getImage("images/b1.png");
    public static Image explosion3Image = Toolkit.getDefaultToolkit().getImage("images/b2.png");
}
