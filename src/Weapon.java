import bagel.Input;

import java.util.Random;

public class Weapon {
    protected double weaponX;
    protected double weaponY;
    //protected double weaponV = Pipe.PIPE_V;
    private Random rand = new Random();
    protected boolean hasBird = false;
    private boolean first = true;
    private boolean hitBird = false;
    protected boolean shoot = false;
    protected boolean outOfRange = false;
    protected int count = 0;

    /**
     * this is the constructor of weapon. it spawn at random position at right side of window
     */
    public Weapon() {
        weaponX = 1024;
        weaponY = 100 + rand.nextInt(400);
    }

    /**
     * this get the x coordinate of weapon
     * @return double this return the x coordinate of weapon
     */
    public double getWeaponX() {
        return weaponX;
    }

    /**
     * check if a weapon is hit with a bird
     * @return boolean return true if weapon hit with a bird, otherwise return false
     */
    public boolean isHitBird() {
        return hitBird;
    }

    /**
     * set the boolean value to hitBird
     * @param hitBird this the value that assigns to hitBird
     */
    public void setHitBird(boolean hitBird) {
        this.hitBird = hitBird;
    }

    /**
     * update the position and state (attached to the bird or not, shoot out or not) of the weapon
     */
    public void update(){
        //if the weapon is shot, it will move to right respect to bird
        if (shoot) {
            weaponX += 5;
            count++;
        //if a weapon is not attached to a bird, it wil move from right to left
        } else if (!hasBird) {
            weaponX -= Pipe.PIPE_V;
        }


    }

    /**
     * make a weapon move along with a bird
     * @param b this is the bird that weapon attached to
     */
    public void attachToBird (Bird b) {
        //weapon can only be picked up when bird does not have weapon at the moment, when a weapon is picked up by a
        //bird, it will move with bird
        if (!b.isHasWeapon() || hasBird) {
            //if weapon is not shot out
            if (!shoot) {
                //weapon is attached to a bird, and draw it around the beak of bird
                hasBird = true;
                weaponX = b.getBirdX() + 25;
                weaponY = b.getBirdY() + 20;
                b.setHasWeapon(true);
            }
        }
        update();
    }

    /**
     * if the weapon has been shot, make the state of weapon be shot and set there is no weapon attached to bird
     * @param b this the bird that shoot the weapon
     */
    public void shootWeapon(Bird b) {
        shoot = true;
        b.setHasWeapon(false);
    }

    /**
     * check if the weapon is out of range
     * @return boolean if weapon is out of range, set it to false and return it.
     */
    public boolean rangeCheck() {
        return outOfRange = false;
    }
}
