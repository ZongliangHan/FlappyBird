import bagel.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Random;

import bagel.util.Point;
import org.lwjgl.system.CallbackI;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Zongliang Han
 */
public class ShadowFlap extends AbstractGame {
    //
    private final Bird bird = new Bird();
    private ArrayList<Pipe> pipes = new ArrayList<Pipe>();
    private Pipe currentPipe = new Pipe();
    private final Font font = new Font("res/font/slkscr.ttf", 48);
    private final Image background_0 = new Image("res/level-0/background.png");
    private final Image background_1 = new Image("res/level-1/background.png");
    private boolean start = false;
    private int score = 0;
    private int INT = 10;
    static int level = 0;
    private int count = 0;
    private int levelUpFrame = 0;
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    private int weaponSpawn = 0;
    private Random rand = new Random();
    private int life0 = 3;
    private int life1 = 6;
    private Life[] hearts;
    private  int PipeRender=100;
    private int timeControlCount = 1;

    //define the useful data
    public final int PIPELENGTH = 768;
    public final int PIPEWIDTH = 65;
    private final double FREQUENCY = 10;
    private final int UPPERBOUND = 0;
    private final int LOWERBOUND = 768;
    private final int SCORE_POSITION = 100;
    private final int LEVEL0_LIFE_TOTAL = 3;
    private final int LEVEL1_LIFE_TOTAL = 6;


    public ShadowFlap() {
        //create a window first
        super(1024, 768, "FlappyBird");
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */

    @Override
    public void update(Input input) {
        if (level == 0) {
            //draw the background first
            background_0.draw(Window.getWidth() / 2, Window.getHeight() / 2);
            if (pipes.size()==0) {
                pipes.add(new Pipe());
            }
            //check if Esc was pressed, if pressed then end the game.
            if (input.wasPressed(Keys.ESCAPE)) {
                Window.close();

     /* check if the game has started yet. (space key was pressed at first time)
        if not start, show the instruction message.
        if space was pressed at the first time, start the game. */
            } else if (!start) {
                font.drawString("PRESS SPACE TO PLAY",
                        (Window.getWidth() - font.getWidth("PRESS SPACE TO PLAY")) / 2, Window.getHeight() / 2);
                if (input.wasPressed(Keys.SPACE)) {
                    start = true;
                }

            //if life is 0, end the game and print the message
            } else if (life0 == 0) {
                gameOverPrint();

                /* check if score is 10, if it is then show level up massage */
            } else if (score >= 10) {
                font.drawString("Level-Up",
                        (Window.getWidth() - font.getWidth("Level-Up")) / 2, background_0.getHeight() / 2);
                levelUpFrame++;
                //reset the score, life, pipes for next level (stay for 50 frame which works on my computer)
                if (levelUpFrame == 50) {
                    level = 1;
                    start = false;
                    score = 0;
                    bird.setBirdY(200);
                    pipes.clear();
                }

                /* game has start and nothing happens yet, bird is in the air. */
            } else {
                //if bird hit the pipe or out of bound, move the bird to its initial position and remove the pipe.
                //and lost a life
                if (collision()==1) {
                    life0--;
                    pipes.remove(currentPipe);
                } else if (bird.getBirdY() < UPPERBOUND || bird.getBirdY() > LOWERBOUND) {

                    bird.setBirdY(350);
                    life0--;
                }

                gameRun(input);
            lifeUpdate(LEVEL0_LIFE_TOTAL, life0);
                addScore();
            }

        } else if (level == 1) {
            if (pipes.size()==0) {
                pipes.add(new Pipe());
            }
            //draw the background first
            background_1.draw(Window.getWidth() / 2, Window.getHeight() / 2);
            //check if Esc was pressed, if pressed then end the game.
            if (input.wasPressed(Keys.ESCAPE)) {
                Window.close();

     /* check if the game has started yet. (space key was pressed at first time)
        if not start, show the instruction message.
        if space was pressed at the first time, start the game. */
            } else if (!start) {
                font.drawString("PRESS SPACE TO PLAY",
                        (Window.getWidth() - font.getWidth("PRESS SPACE TO PLAY")) / 2, Window.getHeight() / 2);
                font.drawString("PRESS 'S' TO SHOOT",
                        (Window.getWidth() - font.getWidth("PRESS 'S' TO SHOOT")) / 2, Window.getHeight() / 2 + 75);
                if (input.wasPressed(Keys.SPACE)) {
                    start = true;
                }

            //if run out of life then end the game, and show the message
            } else if (life1==0) {
                gameOverPrint();
            //if score exceed bound, then win the game and show the message
            } else if (score >= 30) {
                font.drawString("CONGRATULATIONS!",
                        (Window.getWidth() - font.getWidth("CONGRATULATIONS!")) / 2, background_1.getHeight() / 2);
            } else {
                //if bird hit the pipe or out of bound, move the bird to its initial position and remove the pipe.
                //and lost a life
                if (collision()==1) {
                    life1--;
                    pipes.remove(currentPipe);
                } else if (bird.getBirdY() < UPPERBOUND || bird.getBirdY() > LOWERBOUND) {

                    bird.setBirdY(350);
                    life1--;
                }

                gameRun(input);
                lifeUpdate(LEVEL1_LIFE_TOTAL, life1);
                addScore();
            }
        }
    }

    //check if bird is collide with pipe
    private int collision() {
        for (Pipe p : pipes) {
            //check the pipe
            if ((bird.getBirdX()+27 >= p.getxPipe()-32 && bird.getBirdX() <= p.getxPipe() + p.PIPEWIDTH / 2) &&
                    (bird.getBirdY()+19 <= p.getyPipe1() + p.getPIPELENGTH() / 2 ||
                            bird.getBirdY()-19 >= p.getyPipe2() - p.getPIPELENGTH() / 2)) {
                return 1;
            }
            //check the flame
            if (level==1 && p.getFlame() == 1) {
                if ((bird.getBirdX()+27 >= p.getxPipe()-32 && bird.getBirdX() <= p.getxPipe() + p.PIPEWIDTH / 2) &&
                        (bird.getBirdY()-19 <= p.getyPipe1() + p.getPIPELENGTH() / 2 + 39||
                                bird.getBirdY()+19 >= p.getyPipe2() - p.getPIPELENGTH() / 2 - 39)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    //get the next pipe which bird is facing
    private Pipe getCurrentPipe() {
        for (Pipe p: pipes) {
            //check if the pipe is in the range of between bird and next pipe
            if (p.getxPipe()>=300-27-32 && p.getxPipe()<300-27-32+ p.PIPE_V*PipeRender) {
                return p; //return the current pipe
            }
        }
        //return the first pipe in the list, this is for the every start of the game
        return pipes.get(0);
    }

    //update all the pipes, weapons and bird
    private void gameRun(Input input) {
        timeControl(input);
        //get the pipe list, only add pipe into the list every "PipeRender" frame
        //using the count to keep track of frame
        if (count == PipeRender) {
            //at level 0, only add plastic pipe into the list
            if (level == 0) {
                //add all plastic pipe
                timeControl(input);
                pipes.add(new PlasticPipe());

            //at level 1, randomly choose different type of pipe into list
            } else if (level == 1) {

                //randomly add plastic pipe or steel pipe
                if (rand.nextBoolean()) {
                    pipes.add(new PlasticPipe());
                } else {
                    pipes.add(new SteelPipe());
                }
            }
            //after add a pipe into the list, make sure reset the count to 0, for adding the next pipe
            count = 0;
        }
        count++;

        //pipeRender is easily affected by timeControl
        //this is for adding the pipe after changing the time rate.
        if (count > PipeRender) {
            count=0;
        }

        //remove the pipe that outside of window from list
        for (Pipe p: pipes) {

            if (p.getxPipe() < -PIPEWIDTH/2) {
                pipes.remove(p);
                break;
            }
        }


        //move the pipe at constant speed.
        for (Pipe p: pipes) {
            p.update();
        }


        //get weapon

        if (level == 1) {
            //add a weapon into the list every "PipeRender+70" frame
            //weaponSpawn is for keeping track of frames.
            if (weaponSpawn == PipeRender+70) {
                //make sure weapons will not overlap with pipes
                if (count >= 15 && count <= PipeRender-15) {
                    weapons.add(getRandomWeapon());
                }

                weaponSpawn = 0;  //reset the weaponSpawn in order to get next weapon.
            }
            weaponSpawn++;
        }

        //get rid of the effect of timeControl
        if (weaponSpawn > PipeRender+70) {
            weaponSpawn = 0;
        }

        //remove weapon from list
        for (Weapon w: weapons) {
            //check if the weapon is out of range
            w.rangeCheck();

            //check if the weapon destroy a pipe, if it does, remove this weapon from the list and add a score
            if (w.shoot) {
                for (Pipe p : pipes) {
                    p.destroyCheck(w); //check if the pipe is destroyed
                    if (p.destroied) {
                        pipes.remove(p); //if a pipe is destroyed, then remove it from the list and add a score
                        score++;
                        break;
                    }
                }
            }

            //remove the weapon if it is outside of window or out of range
            if (w.getWeaponX() < -PIPEWIDTH/2 || w.outOfRange) {
                weapons.remove(w);
                break;
            }


        }

        //move the weapon
        for (Weapon w: weapons) {
            //check if the bird hit a weapon
            bird.hitWeapon(w);
            //if the weapon is picked up by the bird and is shot, then move forward respect with bird
            if (input.wasPressed(Keys.S) && w.hasBird) {
                w.shootWeapon(bird);
            //if bird hit weapon and bird does not have a weapon at this moment, then this weapon will move with bird
            } else if (w.isHitBird()) {
                w.attachToBird(bird);
            //if weapon is not picked up by bitd, then move the weapon from right to left
            } else {
                w.update();
            }
        }



        // if space key was pressed, bird would fly up.
        if (input.wasPressed(Keys.SPACE)) {
            //bird up draw every 10 frames, otherwise draw bird down.
            if (level == 0) {
                birdSpawn(bird.getBirdU(), bird.getBirdD(), true);
            } else if (level == 1) {
                birdSpawn(bird.getBirdU_level1(), bird.getBirdD_level1(), true);
            }
            // if space key was not pressed, bird would fall.
        } else {
            //bird up draw every 10 frames, otherwise draw bird down.
            if (level == 0) {
                birdSpawn(bird.getBirdU(), bird.getBirdD(), false);
            } else if (level == 1) {
                birdSpawn(bird.getBirdU_level1(), bird.getBirdD_level1(), false);
            }
        }
    }

    //add a score after the bird pass a pipe
    private int addScore() {
        //show the score at up left conner.
        font.drawString("Score: " + score, SCORE_POSITION, SCORE_POSITION);

        //if bird pass the pipe, score +1;
        currentPipe = getCurrentPipe();

        if (bird.getBirdX() < currentPipe.getxPipe() + PIPEWIDTH / 2 &&
                bird.getBirdX() >= currentPipe.getxPipe() + PIPEWIDTH / 2 - currentPipe.getPIPE_V()) {
            score++;
        }
        return score;
    }

    //print out the message when run out of life.
    private void gameOverPrint() {

            /* check if bird hit the pipe, if hit the pipe, then end the game and show the massage. */

            font.drawString("GAME OVER",
                    (Window.getWidth() - font.getWidth("GAME OVER")) / 2, Window.getHeight() / 2);
            font.drawString("SCORE: " + score,
                    (Window.getWidth() - font.getWidth("SCORE: " + score)) / 2, Window.getHeight() / 2 + 75);

    }

    //randomly return bomb or rock
    private Weapon getRandomWeapon() {
        if (rand.nextBoolean()) {
            return (new Bomb());
        } else {
            return (new Rock());
        }

    }

    //draw the heart on the screen based on the life remain
    //draw the right number of full heart and empty heart, heart num is the total life, life num is the current life
    private void lifeUpdate(int heartNum, int lifeNum) {
        hearts = new Life[heartNum];
        int i, j;
        for (i=0; i<heartNum; i++) {
            hearts[i] = new Life();
        }
        //draw the full heart with number of current life
        for (i=0; i<lifeNum; i++) {
            hearts[i].getFullHeart().draw(100+i*50, 15);
        }
        //draw the rest of heart as empty heart
        for (j=0; j<heartNum-lifeNum; j++) {
            hearts[j].getEmptyHeart().draw(100+(j+i)*50, 15);
        }
    }

    //change the pipe v and render frame of pipe
    private void timeControl(Input input) {
        //if k was pressed, pipe render rate should decrease 50%, and it should not lower than scale 1
        if (input.wasPressed(Keys.K) && timeControlCount>1) {
            Pipe.PIPE_V = Pipe.PIPE_V/1.5;
            PipeRender = (int)Math.round(300/Pipe.PIPE_V);
            timeControlCount--;
        //if l was pressed, pipe render rate should increase 50%, and it should not larger than scale 5
        } else if (input.wasPressed(Keys.L) && timeControlCount<5) {
            Pipe.PIPE_V = Pipe.PIPE_V*1.5;
            PipeRender = (int)Math.round(300/Pipe.PIPE_V);
            timeControlCount++;
        }
    }

    //spawn the bird based on the level, and spawn up image every 10 frame, if the space was pressed, bird fly up, else
    //bird fall down
    private void birdSpawn(Image bu, Image bd, boolean fly) {
        //spawn down image
        if (INT != FREQUENCY) {
            //check if the space key was pressed.
            if (fly) {
                bird.fly();
            } else {
                bird.fall();
            }
            bd.draw(bird.getBirdX(), bird.getBirdY());
            INT++;
        //spawn up image
        } else if (INT == FREQUENCY) {
            //check if the space key was pressed
            if (fly) {
                bird.fly();
            } else {
                bird.fall();
            }
            bu.draw(bird.getBirdX(), bird.getBirdY());
            INT = 0;
        }
    }
}