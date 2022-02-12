import bagel.*;

import java.util.ArrayList;
import java.util.Random;


public class Pipe  {

    protected double xPipe;
    protected double yPipe1;
    protected double yPipe2;
    protected DrawOptions Rotations = new DrawOptions();
    protected int flame=0;
    protected boolean destroied = false;


    //useful data
    private final int GAP = 168;
    protected final int PIPELENGTH = 768;
    protected final int PIPEWIDTH = 65;
    static double PIPE_V = 3     ;

    /**
     * constructor of pipe, give them initial position.
     */
    public Pipe() {
        randomPipePosition(ShadowFlap.level);
    }

    /**
     * get random pipe position, for level 0, get random low middle high gap
     * for level 1, get random position between 100 to 500
     * @param level this is the level of the game
     */
    private void randomPipePosition(int level) {
        Random rand = new Random();
        if (level == 0) {
            int[] intArray = {100, 300, 500};   //get the position of gaps (low middle high)
            int index = rand.nextInt(3);    //randomly choose one of gap
            this.yPipe1 = intArray[index] - PIPELENGTH / 2;     //set the pipe position
        } else if (level == 1) {
            this.yPipe1 = (100 + rand.nextInt(400)) - PIPELENGTH/2;     //get random position between 100 and 500
        }
        this.xPipe = Window.getWidth();     //pipe come out from right side of window
        this.yPipe2 = yPipe1 + PIPELENGTH + GAP;
    }

    /**
     * update the position and state of pipes
     */
    public void update() {};

    /**
     * get the x coordinate of a pipe
     * @return double return the x coordinate of a pipe
     */
    public double getxPipe() {
        return xPipe;
    }

    /**
     * get the y coordinate of a pipe (top)
     * @return double return the y coordinate of a pipe
     */
    public double getyPipe1() {
        return yPipe1;
    }

    /**
     * get the y coordinate of a pipe (bottom)
     * @return double return the y coordinate of a pipe
     */
    public double getyPipe2() {
        return yPipe2;
    }

    /**
     * get the velocity of pipe
     * @return double this return the velocity of pipe
     */
    public double getPIPE_V() {
        return PIPE_V;
    }

    /**
     * check if the flame is shot out, if shot out flame is 1, otherwise is 0
     * @return int this return the state of flame
     */
    public int getFlame() {
        return flame;
    }

    /**
     * get the length of pipe
     * @return int this return the length of pipe
     */
    public int getPIPELENGTH() {
        return PIPELENGTH;
    }

    /**
     * check if the pipe was destroyed by a weapon
     * @param w this is the weapon that use to destroy pipe
     */
    public void destroyCheck(Weapon w) {}


}
