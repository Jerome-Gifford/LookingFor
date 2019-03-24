package lf.lookingfor;

import java.util.ArrayList;

public class UserRating {

    private String userId;
    ArrayList<Rating> ratings = new ArrayList<>();

    public UserRating(String userId) {
        this.userId = userId;
        //Start at 5 star rating
        ratings.add(new Rating(userId, 5));
    }

    public UserRating(){}

    public String getUserId() {
        return userId;
    }

    public void rate(Rating rate) {
        ratings.add(rate);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float averageRating(){
        float average = 0;
        for (Rating rating : ratings) {
            average += rating.getRating();
        }
        return average/ratings.size();
    }
}
