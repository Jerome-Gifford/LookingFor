package lf.lookingfor;

public class Rating {

    private String userIdLeavingRating;
    private float rating;

    public Rating(String userIdLeavingRating, float rating) {
        this.userIdLeavingRating = userIdLeavingRating;
        this.rating = rating;
    }

    public Rating(){}

    public String getUserIdLeavingRating() {
        return userIdLeavingRating;
    }

    public void setUserIdLeavingRating(String userIdLeavingRating) {
        this.userIdLeavingRating = userIdLeavingRating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
