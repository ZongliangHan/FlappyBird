import bagel.Image;

public class Life {

    private final Image fullHeart = new Image("res/level/fullLife.png");
    private final Image emptyHeart = new Image("res/level/noLife.png");

    /**
     * this is the constructor of life
     */
    public Life() {
    }

    /**
     * get the image of full heart
     * @return Image return the image of full heart
     */
    public Image getFullHeart() {
        return fullHeart;
    }

    /**
     * get the image of empty heart
     * @return Image return the image of empty heart
     */
    public Image getEmptyHeart() {
        return emptyHeart;
    }

}
