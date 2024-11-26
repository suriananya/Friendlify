package entities;

import java.io.Serializable;

public class Rating implements Serializable {
    private final String userId; // User giving the rating
    private final int score; // Rating out of 5
    private final String comment; // Optional comment

    public Rating(String userId, int score, String comment) {
        this.userId = userId;
        this.score = score;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }
}


