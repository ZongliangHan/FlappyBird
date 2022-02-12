import bagel.Image;
import bagel.Input;

public class Bird  {
    private double birdX;
    private double birdY;
    private  boolean hasWeapon = false;

    private double birdVelocity = 0;
    private final double FLYING = 6;
    private final double MAX_FALLING = 10;
    private final double ACCELARATION = 0.4;

    private final Image birdD = new Image("res/level-0/birdWingUp.png");
    private final Image birdU = new Image("res/level-0/birdWingDown.png");
    private final Image birdD_level1 = new Image ("res/level-1/birdWingDown.png");
    private final Image birdU_level1 = new Image ("res/level-1/birdWingUp.png");

    /**
     *constructor of bird
     */
    public Bird() {
        this.birdY = 200;
        this.birdX = 300;
    }

    /**
     *get bird down image of level 0
     * @return Image this return image of bird down of level 0
     */
    public Image getBirdD() {
        return birdD;
    }

    /**
     *get bird up image of level 0
     * @return Image this return image of bird up of level 0
     */
    public Image getBirdU() {
        return birdU;
    }

    /**
     *get bird down image of level 1
     * @return Image this return image of bird down of level 1
     */
    public Image getBirdD_level1() {
        return birdD_level1;
    }

    /**
     *get bird up image of level 1
     * @return Image this return image of bird up of level 1
     */
    public Image getBirdU_level1() {
        return birdU_level1;
    }

    /**
     *get x coordinate of bird
     * @return double this return x coordinate of bird
     */
    public double getBirdX() {
        return birdX;
    }

    /**
     *get y coordinate of bird
     * @return double this return y coordinate of bird
     */
    public double getBirdY() {
        return birdY;
    }

    /**
     *check if bird has weapon at the moment
     * @return boolean this return true if bird has weapon, otherwise return false
     */
    public boolean isHasWeapon() {
        return hasWeapon;
    }

    /**
     *method of flying
     */
    public void fly() {
        this.birdY -= FLYING;         //bird fly up.
        this.birdVelocity = -FLYING;  //after flying, bird current v is point up.
    }

    /**
     *method of falling. bird fall down at constant acceleration rate. after reach the max speed,
     *fall at constant speed.
     */
    //
    public void fall () {
        /* check if bird is at max velocity
           if not, the speed of bird will increase. */
        if (birdVelocity < MAX_FALLING) {
            this.birdVelocity += ACCELARATION;   //accelerate per frame.
            this.birdY += birdVelocity;
            /* if at max velocity, fall at constant max velocity. */
        } else {
            this.birdY += MAX_FALLING;
        }
    }

    /**
     *check if the bird hit the weapon
     * @param w this is the weapon that the bird possibly hit
     */
    public void hitWeapon(Weapon w) {
        if ((birdY <= w.weaponY+20 && birdY >= w.weaponY-20) && (birdX <= w.weaponX+20 && birdX >= w.weaponX-20)) {
           w.setHitBird(true);
        }

    }

    /**
     *set the boolean value of hasWeapon
     * @param hasWeapon this is the value that need to be assigned
     */
    public void setHasWeapon(boolean hasWeapon) {
        this.hasWeapon = hasWeapon;
    }

    /**
     *set the y coordinate of bird
     * @param birdY this is the y coordinate of bird
     */
    public void setBirdY(double birdY) {
        this.birdY = birdY;
    }
}
