import bagel.DrawOptions;
import bagel.Image;

import java.util.ArrayList;

public class PlasticPipe extends Pipe {

    private final Image pipe1 = new Image("res/level/plasticPipe.png");
    private final Image pipe2 = new Image("res/level/plasticPipe.png");
    private int count = 100;

    /**
     * this is the constructor of plastic pipe
     */
    public PlasticPipe() {
        super();
    }

    /**
     * update the position and draw pipe
     */
    public void update() {
        xPipe -= this.PIPE_V ;   //pipe move from right to left at constant speed.
        pipe2.draw(xPipe, yPipe2, this.Rotations.setRotation(Math.PI));
        pipe1.draw(xPipe, yPipe1);
    }

    /**
     * check if the plastic pipe can be destroyed by weapon
     * since it is plastic pipe, any weapon can destroy it
     * @param w this is the weapon that use to destroy pipe
     */
    public void destroyCheck(Weapon w) {
            //check if the weapon hit on the pipe, since it is plastic pipe, any weapon can destroy it
            if ((w.weaponX >= getxPipe() && w.weaponX <= getxPipe() + PIPEWIDTH / 2) &&
                    (w.weaponY <= getyPipe1() + getPIPELENGTH() / 2 || w.weaponY >= getyPipe2() - getPIPELENGTH() / 2)) {
                destroied = true;
                w.outOfRange = true;
            }
    }
}
