/*窗口高630，宽630，由背景图片填充，海平面高250 */
/*爆炸效果大小32*32
炸弹大小10*15
潜艇大小30*10
军舰大小50*20
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    //定义常量表示游戏状态，开始界面：0，运行中：1，暂停：2
    public static final int BEGIN = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;

    //定义游戏背景对象
    public BackgroundObject background;
    //定义军舰对象
    public ShipObj ship;
    //定义炸弹对象列表
    public ArrayList<BombObj> bombList = new ArrayList<>();
    //定义潜艇对象列表
    public ArrayList<SubmarineObj> submarineList = new ArrayList<>();

    //定义游戏分数变量
    public int score = 0;

    //时间戳，发射炸弹和出现潜艇的时间控制
    long currentTime = 0;          //当前时间
    long lastBombTime = 0;         //上次发射炸弹时间
    long lastSubmarineTime = 0;    //上次出现潜艇时间
    long bombInterval = 800;       //发射炸弹间隔
    long submarineInterval = 400;  //出现潜艇间隔

    //定义游戏当前状态变量，初始为开始界面状态
    public static int state = BEGIN;

    //定义离屏图片变量
    private Image offScreenImage = null; 

    //构造方法，初始化窗口属性
    public MainWindow() {
        this.setVisible(true);//调整窗口为可见
        this.setSize(630, 630);
        this.setResizable(false); //禁止改变窗口大小
        this.setLocationRelativeTo(null);//设置窗口居中
        this.setTitle("潜艇猎手");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击叉号后结束游戏程序
        //创建背景对象
        background = new BackgroundObject(GameImage.backgroundImage, this);
        //创建军舰对象
        ship = new ShipObj(GameImage.shipLeftImage, GameImage.shipRightImage, this.getWidth() / 2, this);
    }

    //发射炸弹的方法
    public void launchBomb() {
        if (currentTime - lastBombTime >= bombInterval) {
            lastBombTime = currentTime;
            ship.addBomb();
        }
    }

    //更新炸弹列表的方法
    public void updateBombsList(Graphics g) {
        for (int i = 0; i < bombList.size(); i++) {
            BombObj bomb = bombList.get(i);
            bomb.paintSelf(g);
            if (!bomb.live) {
                if (currentTime - bomb.destroyedTime >= bomb.destroyedDuration) {
                    bombList.remove(i);
                    i--;//更新索引以避免跳过下一个炸弹
                }
            }
        }
    }

    //创造潜艇的方法
    public void createSubmarine() {
        if (currentTime - lastSubmarineTime >= submarineInterval) {
            lastSubmarineTime = currentTime;
            SubmarineObj submarine = new SubmarineObj(this, ship);
            //确保新潜艇与现有潜艇在Y轴上不重叠太近
            boolean isSafe = true;
            //检查新潜艇是否与现有潜艇在 Y 轴上重叠太近
            for (SubmarineObj existingSub : submarineList) {
                // 如果两艘潜艇 Y 坐标差距小于20，则认为不安全
                if (Math.abs(submarine.y - existingSub.y) < 20) {
                    isSafe = false;
                    break;
                }
            }
            //只有位置安全时才添加，否则这次就不生成了
            if (isSafe) {
                submarineList.add(submarine);
            }
        }
    }

    //更新潜艇列表的方法
    public void updateSubmarineList(Graphics g) {
        for (int i = 0; i < submarineList.size(); i++) {
            SubmarineObj submarine = submarineList.get(i);
            submarine.paintSelf(g);
            if (!submarine.live) {
                //检查被摧毁时间是否超过显示时间，超过则移除潜艇对象
                if (currentTime - submarine.destroyedTime >= submarine.destroyedDuration) {
                    submarineList.remove(i);
                    i--;
                }
            }
        }
    }


    //重写paint方法，使用双缓存技术实现GUI的绘制，先将各元素绘制在离屏图片上，再一次性绘制到屏幕上
    //根据游戏状态绘制不同界面
    @Override
    public void paint(Graphics g) {
        // 创建离屏图片
        if (offScreenImage == null) {
            offScreenImage = this.createImage(this.getWidth(), this.getHeight());
        }

        // 获取离屏图片的画笔
        Graphics gImage = offScreenImage.getGraphics();

        // 在离屏图片上进行绘制
        //游戏开始界面
        if (state == BEGIN) {
            //绘制背景图片,使其宽和高等于窗口的宽和高，使用参数this来指定图片的观察者
            gImage.drawImage(GameImage.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            //绘制向左的军舰
            gImage.drawImage(GameImage.shipLeftImage, this.getWidth() / 2 - 150, 250, this);
            //绘制炸弹
            gImage.drawImage(GameImage.bombImage, this.getWidth() / 2 - 125, 400, this);
            //绘制向右的潜艇
            gImage.drawImage(GameImage.submarineRightImage, this.getWidth() / 2 + 100, 500, this);
            //绘制爆炸效果图片
            gImage.drawImage(GameImage.explosion1Image, this.getWidth() / 2 - 100, 350, this);
            //绘制向右被击毁潜艇
            gImage.drawImage(GameImage.destroyedSubmarineRightImage, this.getWidth() / 2 - 95, 345, this);
            //开始界面文字
            gImage.setColor(Color.blue);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 40));
            gImage.drawString("潜艇猎手", this.getWidth() / 2 - 80, 150);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 20));
            gImage.drawString("鼠标左键点击此处开始游戏", this.getWidth() / 2 - 120, 220);
        }

        //游戏运行界面
        else if (state == RUNNING) {
            currentTime = System.currentTimeMillis();
            background.paintSelf(gImage);
            ship.paintSelf(gImage);
            //实时显示分数
            gImage.setColor(Color.BLUE);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 20));
            gImage.drawString("得分：" + score, 20, 60);

            //显示剩余炸弹数
            gImage.setColor(Color.BLUE);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 20));
            gImage.drawString("剩余炸弹数：" + ship.bombCount, 20, 90);

            //发射炸弹
            launchBomb();
            updateBombsList(gImage);
            //创造潜艇
            createSubmarine();
            updateSubmarineList(gImage);

            //检测游戏结束条件，若剩余炸弹数为0且屏幕上没有炸弹，则游戏结束
            if (ship.bombCount <= 0 && bombList.size() == 0) {
                state = GAMEOVER;
            }
        }

        //游戏暂停界面
        else if (state == PAUSE) {
            gImage.setColor(Color.black);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 30));
            gImage.drawString("游戏已暂停", this.getWidth() / 2 - 70, 120);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 20));
            gImage.drawString("当前分数：" + score, this.getWidth() / 2 - 50, 170);
            gImage.drawString("按空格键继续游戏", this.getWidth() / 2 - 75, 220);

        }

        //游戏结束界面
        else if (state == GAMEOVER) {
            gImage.setColor(Color.red);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 30));
            gImage.drawString("游戏结束", this.getWidth() / 2 - 70, 120);
            gImage.setFont(new Font("微软雅黑", Font.BOLD, 20));
            gImage.drawString("最终得分：" + score, this.getWidth() / 2 - 70, 170);
            gImage.drawString("开始新游戏", this.getWidth() / 2 - 65, 220);
        }

        //将画好的离屏图片一次性画到屏幕上
        g.drawImage(offScreenImage, 0, 0, null);
    }
    
    //启动游戏的方法
    public void launch() {
        //添加鼠标监听器，监听鼠标左键点击事件,用于开始界面点击开始游戏
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1 && state == BEGIN) {
                    int x = e.getX();
                    int y = e.getY();
                    // 判断鼠标点击位置是否在 "鼠标左键点击此处开始游戏" 文字范围内
                    if (x >= 200 && x <= 440 && y >= 200 && y <= 230) {
                        state = RUNNING;
                    }
                }
                else if(e.getButton() == 1 && state == GAMEOVER) {
                    int x = e.getX();
                    int y = e.getY();
                    // 判断鼠标点击位置是否在 "开始新游戏" 文字范围内
                    if (x >= 250 && x <= 365 && y >= 200 && y <= 230) {
                        //重置游戏状态和变量
                        state = RUNNING;
                        score = 0;
                        ship.bombCount = 20;
                        bombList.clear();
                        submarineList.clear();
                        lastBombTime = 0;
                        lastSubmarineTime = 0;
                    }
                }
            }
        });

        //添加键盘监听器，监听空格键按下事件，用于暂停和继续游戏
        this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    //按空格键，暂停或继续游戏
                    if (key == KeyEvent.VK_SPACE) {
                        if (state == RUNNING) {
                            state = PAUSE;
                        } 
                        else if (state == PAUSE) {
                            state = RUNNING;
                        }
                    }
                }
            });

        //游戏主循环，持续重绘窗口
        while (true) {
            repaint();
            try {
                Thread.sleep(16);//每秒60帧
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        MainWindow game = new MainWindow();
        game.launch();
    }
}