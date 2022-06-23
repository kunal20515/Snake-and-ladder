package sample;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.Timer;


interface snakeInter{
    public int getP1Row();
    public void checkSnk(int[][] boardData, int blockSize, ImageView player, ArrayList<File> sounds);
}
interface LadderInter{
    public int getP1Clmn();
    public void checkLdr(int[][] boardData, int blockSize, ImageView player, ArrayList<File> sounds);
}
interface diceInter{
    public int getDiceNum();
}
interface tempoInter{
    public boolean OneinProximity(int p1Row,int p1Clmn);
    public boolean TwoinProximity(int p2Row,int p2Clmn);
}
class Snake extends tempoParent implements snakeInter{

    public Snake(Info info,int p1Row,int p1Clmn,char direction,double xPos,double yPos  ){
        super(info,p1Row,p1Clmn, direction, xPos,yPos );
    }

}

class Ladder implements LadderInter{
    private TranslateTransition translate;
    private Info info;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int p1Row;
    private int p1Clmn;
    private char direction;
    private double xPos;
    private double yPos;
    public Ladder(Info info,int p1Row,int p1Clmn,char direction,double xPos,double yPos  ){
        this.info = info;
        this.direction = direction;
        this.xPos = xPos;
        this.yPos = yPos;
        this.p1Clmn = p1Clmn;
        this.p1Row = p1Row;
    }

    public int getP1Row() {
        return p1Row;
    }

    public int getP1Clmn() {
        return p1Clmn;
    }

    public char getDirection() {
        return direction;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void checkLdr(int[][] boardData, int blockSize, ImageView player, ArrayList<File> sounds){
        if(boardData[p1Row][p1Clmn] % 12 == 0 ) {
            //NOW USE PATH TRANSITION
            int preRow = p1Row;
            int preClmn = p1Clmn;
            String s = Integer.toString(p1Row) + " " + Integer.toString(p1Clmn);
            String toBe = info.getVal(s);
            String[] arr = toBe.split(" ");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            p1Row = a;
            p1Clmn = b;
            if (p1Row % 2 == 0) {
                direction = 'L';
            } else direction = 'R';
            if (boardData[p1Row][p1Clmn] % 21 == 0) {
                if (p1Row % 2 != 0 && direction == 'R') {
                    direction = 'L';
                } else if (p1Row % 2 == 0 && direction == 'L') {
                    direction = 'R';
                }
            }
            int dist = p1Clmn - preClmn;
            if (dist > 0) {
                translate = new TranslateTransition();
                translate.setDuration(Duration.millis(500));
                translate.setCycleCount(1);
                translate.setNode(player);
                translate.setToX(xPos + dist * blockSize);
                translate.play();
                xPos = xPos + dist * blockSize;

            } else if (dist < 0) {
                dist = -dist;
                translate = new TranslateTransition();
                translate.setDuration(Duration.millis(500));
                translate.setCycleCount(1);
                translate.setNode(player);

                translate.setToX(xPos - (dist * blockSize));
                translate.play();
                xPos = xPos - dist * blockSize;
            }
            translate = new TranslateTransition();
            translate.setDuration(Duration.millis(500));
            translate.setCycleCount(1);
            translate.setNode(player);
            translate.setToY(yPos - (preRow - p1Row) * 50);
            translate.play();
            yPos = yPos - (preRow - p1Row) * 50;
            if(boardData[preRow][preClmn] % 12 == 0 ){
                media  = new Media(sounds.get(0).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
        }
    }
}
class rollDice implements  diceInter{
    private Random rand;
    private int diceNum;

    public int getDiceNum(){
        rand =new Random();
        diceNum = (int) rand.nextInt()%6;
        if(diceNum<0) diceNum = -diceNum;
        diceNum++;
        return this.diceNum;
    }
}
class tempoParent implements tempoInter{
    private TranslateTransition translate;
    private Info info;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int p1Row;
    private int p1Clmn;
    private char direction;
    private double xPos;
    private double yPos;

    public boolean OneinProximity(int p1Row,int p1Clmn){
        if(p1Row == 0){
            if(p1Clmn >=1 && p1Clmn <= 6)return true;
        }
        return false;
    }
    public boolean TwoinProximity(int p2Row,int p2Clmn){
        if(p2Row == 0){
            if(p2Clmn >=1 && p2Clmn <= 6)return true;
        }
        return false;
    }

    public tempoParent(){

    }
    public tempoParent(Info info,int p1Row,int p1Clmn,char direction,double xPos,double yPos ){
        this.info = info;
        this.direction = direction;
        this.xPos = xPos;
        this.yPos = yPos;
        this.p1Clmn = p1Clmn;
        this.p1Row = p1Row;
    }

    public int getP1Row() {
        return p1Row;
    }

    public int getP1Clmn() {
        return p1Clmn;
    }

    public char getDirection() {
        return direction;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void checkSnk(int[][] boardData, int blockSize, ImageView player , ArrayList<File> sounds){
        if(boardData[p1Row][p1Clmn] % 19 == 0 ) {
            //NOW USE PATH TRANSITION
            int preRow = p1Row;
            int preClmn = p1Clmn;
            String s = Integer.toString(p1Row) + " " + Integer.toString(p1Clmn);
            String toBe = info.getVal(s);
            String[] arr = toBe.split(" ");
            int a = Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            p1Row = a;
            p1Clmn = b;
            if (p1Row % 2 == 0) {
                direction = 'L';
            } else direction = 'R';
            if (boardData[p1Row][p1Clmn] % 21 == 0) {
                if (p1Row % 2 != 0 && direction == 'R') {
                    direction = 'L';
                } else if (p1Row % 2 == 0 && direction == 'L') {
                    direction = 'R';
                }
            }
            int dist = p1Clmn - preClmn;
            if (dist > 0) {
                translate = new TranslateTransition();
                translate.setDuration(Duration.millis(500));
                translate.setCycleCount(1);
                translate.setNode(player);
                translate.setToX(xPos + dist * blockSize);
                translate.play();
                xPos = xPos + dist * blockSize;

            } else if (dist < 0) {
                dist = -dist;
                translate = new TranslateTransition();
                translate.setDuration(Duration.millis(500));
                translate.setCycleCount(1);
                translate.setNode(player);

                translate.setToX(xPos - (dist * blockSize));
                translate.play();
                xPos = xPos - dist * blockSize;
            }
            translate = new TranslateTransition();
            translate.setDuration(Duration.millis(500));
            translate.setCycleCount(1);
            translate.setNode(player);
            translate.setToY(yPos - (preRow - p1Row) * 50);
            translate.play();
            yPos = yPos - (preRow - p1Row) * 50;
            if(boardData[preRow][preClmn] % 19 == 0 ){
                media  = new Media(sounds.get(1).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
        }
    }

}
class Info{
    private HashMap<String,String> hm;
    public Info(HashMap<String,String> hm){
        this.hm = hm;
    }
    public String getVal(String key){
        String toBe = hm.get(key);
        return toBe;
    }
}
public class tempo extends tempoParent implements Initializable {
    @FXML
    private ImageView dice;
    @FXML
    private ImageView player;
    @FXML
    private ImageView player2;
    @FXML
    private ImageView highlight;
    @FXML
    private ImageView arrowFrame;
    @FXML
    private ImageView mainBoard;
    @FXML
    private ImageView winP;
    @FXML
    private ImageView diwaliFrame;
    @FXML
    private ImageView diwaliFrame2;
    private int diceNum;
    private double xPos;
    private double yPos;
    private double xPos2;
    private double yPos2;
    private rollDice DICE;
    private Image dice1;
    private Image GIF;
    private Image dice2;
    private Image diwali;
    private Image dice3;
    private Image dice4;
    private Image dice5;
    private Image dice6;
    private Image highlight1;
    private Image highlight2;
    private Image empty;
    private Image arrow;
    private Image winP1;
    private Image winP2;
    private int  p1Row ;
    private int  p1Clmn ;
    private boolean isMoving;
    private char direction ;
    private char direction2 ;
    private float unit;
    private int stepCount;
    private Info info;
    private Media media;
    private MediaPlayer mediaPlayer;
    private ArrayList<File> sounds;
    private File directory;
    private File[] files;
    private boolean running1;
    private int turn;
    private int p2Row ;
    private boolean running2 ;
    private boolean gameOverCalled;
    private  int p2Clmn ;
    private TranslateTransition translate ;
    private int [][] boardData={{1,19,1,19,1,19,1,1,1,19},
            {1,1,1,1,1,1,1,1,1,21},
            {21,1,1,1,1,1,1,1,1,1},
            {1,1,1,12,1,12,1,12,1,21},
            {21,19,1,19,1,19,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,21},
            {12*21,1,1,12,1,12,1,12,1,1},
            {1,1,1,19,1,19,1,19,1,21},
            {21,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,12,1,12,1,12,21},
    };
    private HashMap<String,String> hm  = new HashMap<>();
    private int blockSize;
    public tempo(){
        running1 = false;
        isMoving = false;
        direction2 ='R';
        hm.put("0 1","2 2");
        hm.put("0 3","2 4");
        hm.put("0 5","2 6");
        hm.put("0 9","5 9");
        hm.put("4 1","6 2");
        hm.put("4 3","6 4");
        hm.put("4 5","6 6");
        hm.put("7 3","8 2");
        hm.put("7 5","8 4");
        hm.put("7 7","8 6");
        //ladder
        hm.put("9 4","8 3");
        hm.put("9 6","8 5");
        hm.put("9 8","8 7");
        hm.put("6 0","1 0");
        hm.put("6 3","5 2");
        hm.put("6 5","5 4");
        hm.put("6 7","5 6");
        hm.put("3 3","1 2");
        hm.put("3 5","1 4");
        hm.put("3 7","1 6");
        info = new Info(hm);
        direction ='R';
        p1Row = 9;
        turn = 1;
        p2Row = 9;
        p2Clmn = 0;
        p1Clmn = 0;
        blockSize = 60;
        unit = 50/20f;
        running2 = false;
        xPos = 0;
        yPos = -40;
        xPos2 = -18;
        DICE = new rollDice();
        yPos2 = -40;
        diwali = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\02-PM-unscreen.gif");
        arrow = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\pointer.gif");
        empty = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\actual_fill.png");
        highlight1 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\player1.png");
        highlight2 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\player2.png");
        dice1 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\1.PNG");
        dice2 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\2.PNG");
        dice3 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\3.PNG");
        dice4 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\4.PNG");
        dice5 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\5.PNG");
        dice6 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\6.PNG");
        GIF = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\final_roller.gif");
        winP1 = new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\winP1.jpeg");
        winP2= new Image("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\winP2.jpeg");
        stepCount = 0;
        gameOverCalled = false;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void setP1Row(int p1Row) {
        this.p1Row = p1Row;
    }

    public void setP1Clmn(int p1Clmn) {
        this.p1Clmn = p1Clmn;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public void gifFun(){
        if(isMoving)return;
        diceNum = DICE.getDiceNum();
//        System.out.println(diceNum);
        dice.setImage(GIF);
        media  = new Media(sounds.get(3).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public void setImg(){
        if(isMoving)return;
        if(diceNum == 1)dice.setImage(dice1);
        if(diceNum == 2)dice.setImage(dice2);
        if(diceNum == 3)dice.setImage(dice3);
        if(diceNum == 4)dice.setImage(dice4);
        if(diceNum == 5)dice.setImage(dice5);
        if(diceNum == 6)dice.setImage(dice6);
    }
    public void mainMove(){
        if(isMoving)return;
        if(turn == 1){
            if(!running1 && diceNum == 1){
                isMoving = true;
                arrowFrame.setImage(empty);
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player);
                translate.setToY(-40);
                translate.play();
                media  = new Media(sounds.get(2).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                running1 = true;
                turn =2;
                isMoving = false;
                arrowFrame.setImage(arrow);
                highlight.setImage(highlight2);
                return;
            }}
        if(turn == 2){
            if(!running2 && diceNum == 1){
                isMoving = true;
                arrowFrame.setImage(empty);
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player2);
                translate.setToY(-40);
                translate.play();
                media  = new Media(sounds.get(2).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                running2 = true;
                turn =1;
                isMoving = false;
                arrowFrame.setImage(arrow);
                highlight.setImage(highlight2);
                return;
            }}
        if(turn ==2)if(!running2){
            turn =1;
            highlight.setImage(highlight1);
            return;
        }
        if(turn == 1)if(!running1){
            turn = 2;
            highlight.setImage(highlight2);
            return;
        }
        if(turn == 1){
            if(OneinProximity(p1Row,p1Clmn)){
                if(diceNum>p1Clmn){
                    turn = 2;
                    highlight.setImage(highlight2);
                    return;
                }}
        }
        if(turn == 2){
            if(TwoinProximity(p2Row,p2Clmn)){
                if(diceNum>p2Clmn){
                    if(turn == 2){
                        turn = 1;
                        highlight.setImage(highlight1);
                        return;
                    }
                }}
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int num = 10*diceNum;
            @Override
            public void run() {
                if(num >0){
                    isMoving =true;
                    arrowFrame.setImage(empty);
                    if(turn == 1)movePlayer();
                    else{
                        movePlayer2();
                    }
                    num--;
                }else {
                    if(turn == 1){
                        tempoParent snk = new Snake(info,p1Row,p1Clmn,direction,xPos,yPos);
                        snk.checkSnk(boardData,blockSize,player,sounds);
                        setP1Row(snk.getP1Row());
                        setP1Clmn(snk.getP1Clmn());
                        setDirection(snk.getDirection());
                        setxPos(snk.getxPos());
                        setyPos(snk.getyPos());
                        Ladder ldr = new Ladder(info,p1Row,p1Clmn,direction,xPos,yPos);
                        ldr.checkLdr(boardData,blockSize,player,sounds);
                        setP1Row(ldr.getP1Row());
                        setP1Clmn(ldr.getP1Clmn());
                        setDirection(ldr.getDirection());
                        setxPos(ldr.getxPos());
                        setyPos(ldr.getyPos());
                    }
                    else checkLadderSnk2();
                    if(isFinished()){
                        running1 = false;
                        gameOver1();
                    }if(isFinished2()){
                        running2 = false;
                        gameOver2();
                    }
                    if(turn == 1){
                        turn = 2;
                        highlight.setImage(highlight2);
                    }
                    else {
                        turn = 1;
                        highlight.setImage(highlight1);
                    }
                    timer.cancel();
                    if(!gameOverCalled)isMoving =false;
                    arrowFrame.setImage(arrow);
                }
            }
        };
        timer.scheduleAtFixedRate(task,0,37);
    }
    public void checkLadderSnk2(){
        if(boardData[p2Row][p2Clmn] % 19 == 0 || boardData[p2Row][p2Clmn] % 12 == 0){
            //NOW USE PATH TRANSITION
            int preRow = p2Row;
            int preClmn = p2Clmn;
            String s= Integer.toString(p2Row)+" "+Integer.toString(p2Clmn);
            String toBe  = hm.get(s);
            String[] arr = toBe.split(" ");
            int a= Integer.parseInt(arr[0]);
            int b = Integer.parseInt(arr[1]);
            p2Row = a;
            p2Clmn = b;
            if(p2Row %2 == 0){
                direction2 = 'L';
            }else direction2 ='R';
            if(boardData[p2Row][p2Clmn] % 21 == 0){
                if(p2Row %2 != 0 && direction2 == 'R'){
                    direction2 = 'L';
                }else if(p2Row %2 == 0 && direction2 == 'L'){
                    direction2 = 'R';
                }
            }
            int dist = p2Clmn-preClmn;
            if(dist>0){
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(500));
                translate.setCycleCount(1);
                translate.setNode(player2);
                translate.setToX(xPos2+ dist*blockSize);
                translate.play();

                xPos2 = xPos2+dist*blockSize;

            }else if (dist <0){
                dist = -dist;
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(500));
                translate.setCycleCount(1);
                translate.setNode(player2);
                translate.setToX(xPos2- (dist*blockSize));
                translate.play();
                xPos2 = xPos2 -dist*blockSize;
            }
            translate =  new TranslateTransition();
            translate.setDuration(Duration.millis(500));
            translate.setCycleCount(1);
            translate.setNode(player2);
            translate.setToY(yPos2- (preRow-p2Row )*50);
            translate.play();
            yPos2 = yPos2 - (preRow-p2Row )*50;
            if(boardData[preRow][preClmn] % 19 == 0){
                media  = new Media(sounds.get(1).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
            if(boardData[preRow][preClmn] % 12 == 0){
                media  = new Media(sounds.get(0).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            }
        }
    }


    public void movePlayer2(){
        int i = 0;
        if(boardData[p2Row][p2Clmn] % 21 == 0){
            if(p2Row %2 == 0 && direction2 == 'R'){
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player2);
                //translate.setByY(-blockSize);
//                    translate.setFromY(yPos);
                translate.setToY(yPos2-50/10f);
                yPos2 = yPos2-50/10f;
                stepCount++;
                translate.play();
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    stepCount = 0;
                    diceNum--;
                    p2Row--;
                }
            }else if(p2Row %2 != 0 && direction2 == 'L'){
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player2);
//                    translate.setByY(-blockSize);
                translate.setFromY(yPos2);
                translate.setToY(yPos2-50/10f);
                yPos2 = yPos2-50/10f;
                stepCount++;
                translate.play();
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    stepCount = 0;
                    diceNum--;
                    p2Row--;
                }
            }
        }
        if(i<diceNum &&boardData[p2Row][p2Clmn] % 21 != 0){
            if(p2Row %2 != 0){
                float checkyPos = -((9-p2Row)*50+12.5f);
                checkyPos += -40;
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player2);
                //translate.setByX(blockSize);
                translate.setToX(xPos2+blockSize/10f);
                translate.setToY(yPos2-unit);
                xPos2 =xPos2+blockSize/10f;
                yPos2 = yPos2-unit;
                if(yPos2 <= checkyPos){
                    unit = -unit;
                }if(yPos2>= ((-(9-p2Row)*50)-40)){
                    unit = -unit;
                }
                translate.play();
                stepCount++;
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    stepCount = 0;
                    p2Clmn++;
                    diceNum--;
                }
            }else {
                float checkyPos = -((9-p2Row)*50+12.5f);
                checkyPos += -40;
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player2);
//                    translate.setByX(-blockSize);
                translate.setToX(xPos2-blockSize/10f);
                translate.setToY(yPos2-unit);
                xPos2 =xPos2-blockSize/10f;
                yPos2 = yPos2-unit;
                if(yPos2 <= checkyPos){
                    unit = -unit;
                }if(yPos2>= ((-(9-p2Row)*50)-40) ){
                    unit = -unit;
                }
                translate.play();
                stepCount++;
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    p2Clmn--;
                    diceNum--;
                    stepCount = 0;
                }
            }
        }
        if(boardData[p2Row][p2Clmn] % 21 == 0){
            if(p2Row %2 != 0 && direction2 == 'R'){
                direction2 = 'L';
            }else if(p2Row %2 == 0 && direction2 == 'L'){
                direction2 = 'R';
            }
        }

    }
    public void movePlayer(){
        int i = 0;
        if(boardData[p1Row][p1Clmn] % 21 == 0){
            if(p1Row %2 == 0 && direction == 'R'){
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player);
                //translate.setByY(-blockSize);
//                    translate.setFromY(yPos);
                translate.setToY(yPos-50/10f);
                yPos = yPos-50/10f;
                stepCount++;
                translate.play();
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    stepCount = 0;
                    diceNum--;
                    p1Row--;
                }
            }else if(p1Row %2 != 0 && direction == 'L'){
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player);
//                    translate.setByY(-blockSize);
                translate.setFromY(yPos);
                translate.setToY(yPos-50/10f);
                yPos = yPos-50/10f;
                stepCount++;
                translate.play();
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    stepCount = 0;
                    diceNum--;
                    p1Row--;
                }
            }
        }
        if(i<diceNum &&boardData[p1Row][p1Clmn] % 21 != 0){
            if(p1Row %2 != 0){
                float checkyPos = -((9-p1Row)*50+12.5f);
                checkyPos  += -40;
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player);
                //translate.setByX(blockSize);
                translate.setToX(xPos+blockSize/10f);
                translate.setToY(yPos-unit);
                xPos =xPos+blockSize/10f;
                yPos = yPos-unit;
                if(yPos <= checkyPos){
                    unit = -unit;
                }if(yPos>= ((-(9-p1Row)*50)-40)){
                    unit = -unit;
                }
                translate.play();
                stepCount++;
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    stepCount = 0;
                    p1Clmn++;
                    diceNum--;
                }
            }else {
                float checkyPos = -((9-p1Row)*50+12.5f);
                checkyPos  += -40;
                translate =  new TranslateTransition();
                translate.setDuration(Duration.millis(50));
                translate.setCycleCount(1);
                translate.setNode(player);
//                    translate.setByX(-blockSize);
                translate.setToX(xPos-blockSize/10f);
                translate.setToY(yPos-unit);
                xPos =xPos-blockSize/10f;
                yPos = yPos-unit;
                if(yPos <= checkyPos){
                    unit = -unit;
                }if(yPos>= ((-(9-p1Row)*50)-40)){
                    unit = -unit;
                }
                translate.play();
                stepCount++;
                if(stepCount == 10){
                    media  = new Media(sounds.get(2).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    p1Clmn--;
                    diceNum--;
                    stepCount = 0;
                }
            }
        }
        if(boardData[p1Row][p1Clmn] % 21 == 0){
            if(p1Row %2 != 0 && direction == 'R'){
                direction = 'L';
            }else if(p1Row %2 == 0 && direction == 'L'){
                direction = 'R';
            }
        }

    }
    public void gameOver1(){
        gameOverCalled = true;
        isMoving = true;
        winP.setImage(winP1);
        diwaliFrame.setImage(diwali);
        diwaliFrame2.setImage(diwali);
        DropShadow ds = new DropShadow(45,Color.BLACK);
        BoxBlur bb = new BoxBlur();
        bb.setHeight(5);
        bb.setWidth(5);
        mainBoard.setEffect(bb);
        player.setEffect(bb);
        player2.setEffect(bb);
        arrowFrame.setEffect(bb);
        dice.setEffect(bb);
        highlight.setEffect(bb);
        winP.setEffect(ds);
    }
    public void gameOver2(){
        gameOverCalled =true;
        isMoving = true;
        winP.setImage(winP2);
        diwaliFrame.setImage(diwali);
        diwaliFrame2.setImage(diwali);
        DropShadow ds = new DropShadow(45, Color.BLACK);
        BoxBlur bb = new BoxBlur();
        bb.setHeight(5);
        bb.setWidth(5);
        mainBoard.setEffect(bb);
        player.setEffect(bb);
        player2.setEffect(bb);
        arrowFrame.setEffect(bb);
        dice.setEffect(bb);
        highlight.setEffect(bb);
        winP.setEffect(ds);
    }
    public boolean isFinished(){
        if(p1Row == 0 && p1Clmn == 0){
            return true;
        }
        return false;
    }
    public boolean isFinished2(){
        if(p2Row == 0 && p2Clmn == 0){
            return true;
        }
        return false;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sounds = new ArrayList<File>();
        directory = new File("C:\\Users\\Kshitij\\IdeaProjects\\HelloFX\\src\\sample\\music");
        files =directory.listFiles();
        if(files != null){
            for(File file :files){
                sounds.add(file);
            }
        }
    }
}