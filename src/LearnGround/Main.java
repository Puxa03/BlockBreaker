package LearnGround;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {
    static int BLOCK_ROWS=5;
    static int BLOCK_COLUMNS=16;

    @Override
    public void start(Stage primaryStage) throws Exception{


        ArrayList<ArrayList<Block>> blocks=new ArrayList<>();
        ArrayList<KeyCode> list=new ArrayList<>();

        Image greenBlock=new Image(("images/green1.png"));
        Image yellowBlock=new Image(("images/yellow1.png"));
        Image arrow=new Image("images/arrow1.png");
        Image press=new Image("images/press.png");
        Image UI=new Image("images/UI1.png");
        Image gameOver=new Image("images/gameover.png");
        Image bye=new Image("images/bye1.png");
        Ball ball=new Ball(new Image("images/ball3.png"));
        Image playerBlock=new Image("images/player2.png");
        Image background=new Image("images/galaxy50.jpg");
        Image gameWon=new Image("images/gameWon.png");

        Canvas canvas=new Canvas(1600,900);
        Sprite player=new Sprite(playerBlock);

        player.positionX=575;
        player.positionY=790;
        ball.positionX=724;
        ball.positionY=380;


        Group group=new Group();
        group.getChildren().addAll(canvas);
        Scene scene=new Scene(group,1530,850);
        primaryStage.setTitle("GameThingy");
        primaryStage.setScene(scene);


        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(background,0,0);


        MyAnimationTimer introTimer=new MyAnimationTimer() {

            final long timeOld=System.nanoTime();
            int x=650;
            boolean direction=false;
            @Override
            public void handle(long timeNow) {
                gc.drawImage(background,0,0);
                gc.drawImage(arrow, x, 750);
                gc.drawImage(press,75,50);
                gc.drawImage(UI,200,250);

                if(!direction){
                    x+=2;
                }
                else{
                    x-=2;
                }
                if(x>750){
                    direction=true;
                }
                else if(x<650){
                    direction=false;
                }


            }
        };


        MyAnimationTimer gameTimer=new MyAnimationTimer() {
            boolean lastHitPlayer=false;

            @Override
            public void handle(long now) {

                //controls
                if(list.contains(KeyCode.A) && player.positionX>=0){
                    if(list.contains(KeyCode.SHIFT)){
                        player.positionX-=10;

                    }
                    else {
                        player.positionX -= 5;
                    }

                }
                if(list.contains(KeyCode.D)&&player.positionX+337<=1530){
                    if(list.contains(KeyCode.SHIFT)){
                        player.positionX+=10;

                    }
                    else {
                        player.positionX += 5;
                    }
                }


                boolean bgameWon=true;

                //ball hitting block
                for(int i=0;i<BLOCK_ROWS;i++){
                    for(int j=0;j<BLOCK_COLUMNS;j++){
                        Block thisOne=blocks.get(i).get(j);
                        thisOne.render(gc);

                        if(!thisOne.hit && ball.intersects(thisOne)){
                            if(ball.positionX>thisOne.positionX+thisOne.width-5){   //right collision
                                System.out.println("first");
                                ball.angle=-180 - ball.angle;
                            }
                            else if(ball.positionX+ball.width-4<thisOne.positionX){ //left collision
                                ball.angle=180 - ball.angle;
                                System.out.println("second");
                            }
                            else{   //on surface
                                ball.angle = 360 - ball.angle;

                            }

                            thisOne.hit=true;
                            lastHitPlayer=false;
                            ball.speedMultiplier+=0.2;
                            System.out.println(now);
                            System.out.println("-----");
                            break;      //prevents multiple blocks from being destroyed at the same time
                        }
                        if(bgameWon && !thisOne.hit){
                            bgameWon=false;
                        }
                    }
                }


                //rendering stuff
                gc.drawImage(background,0,0);
                player.render(gc);
                ball.move();
                ball.render(gc);

                //this loop is for rendering only. couldnt merge in the upper one cuz the last ones wouldnt render in that case
                for(int i=0;i<BLOCK_ROWS;i++) {
                    for (int j = 0; j < BLOCK_COLUMNS; j++) {
                        Block thisOne = blocks.get(i).get(j);
                        thisOne.render(gc);
                    }
                }




                //ball hitting player
                if(ball.intersects(player)){
                    if(!lastHitPlayer) {

                        if (ball.positionX < player.positionX-15 ) {
                            if(list.contains(KeyCode.A)){
                                ball.angle = -180-new Random().nextInt(30);
                            }
                            else {
                                ball.angle = 180 - ball.angle;
                            }
                        }
                        else if (ball.positionX > player.positionX + 330) {
                            if(list.contains(KeyCode.D)){
                                ball.angle=new Random().nextInt(30);
                            }
                            else {
                                ball.angle = -180 - ball.angle;
                            }
                        }
                        else {
                            ball.angle = 360 - ball.angle;

                        }
                        lastHitPlayer = true;
                    }
                }


                //boll hitting border
                if(ball.positionX<=0){
                    ball.angle = -180 - ball.angle;
                    lastHitPlayer=false;
                }
                else if(ball.positionX+40>=1530){
                    ball.angle = 180- ball.angle;
                    lastHitPlayer=false;
                }
                if(ball.positionY<=0){
                    ball.angle=-360-ball.angle;
                    lastHitPlayer=false;
                }
                else if(ball.positionY+30>=850){

                    this.stop();
                    gc.clearRect(0,0,1530,850);
                    gc.drawImage(background,0,0);
                    gc.drawImage(gameOver,430,150);
                    gc.drawImage(bye,400,300);
                }
                if(bgameWon){

                    this.stop();
                    gc.clearRect(0,0,1530,850);
                    gc.drawImage(background,0,0);
                    gc.drawImage(gameWon,430,150);
                    gc.drawImage(bye,400,300);
                }


            }

        };


        scene.setOnKeyPressed(e->{
            if((e.getCode()==KeyCode.SHIFT||e.getCode()==KeyCode.A || e.getCode()==KeyCode.D)&&!list.contains(e.getCode())) {
                list.add(e.getCode());
            }
            else if(e.getCode()==KeyCode.SPACE && !gameTimer.isRunning()){
                introTimer.stop();

                //creaging level and setting canvas
                blocks.clear();
                int x;
                int y=10;
                for(int i=0;i<BLOCK_ROWS;i++){
                    blocks.add(new ArrayList<Block>());
                    x=10;
                    for(int j=0;j<BLOCK_COLUMNS;j++){
                        if(i<2){
                            blocks.get(i).add(new Block(greenBlock));
                        }
                        else{
                            blocks.get(i).add(new Block(yellowBlock));
                        }
                        blocks.get(i).get(j).positionX=x;
                        x+=blocks.get(i).get(j).width;
                        x+=10;
                        blocks.get(i).get(j).positionY=y;
                    }

                    y+=50;
                }

                ball.positionX=724;
                ball.positionY=380;
                ball.newAngle();
                ball.speedMultiplier=4;
                gameTimer.start();

            }
            else if(e.getCode()==KeyCode.Q && !gameTimer.isRunning() && !introTimer.isRunning()){
                primaryStage.close();
            }

        });
        scene.setOnKeyReleased(e->{
            list.remove(e.getCode());
        });


        introTimer.start();
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
