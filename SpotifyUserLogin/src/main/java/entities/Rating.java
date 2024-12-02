package entities;

import java.io.Serializable;

/**
 * Represents a rating given by a user, including a score and an optional comment.
 */
public class Rating implements Serializable {
    private final String userId; // User giving the rating
    private int score; // Rating out of 5
    private String comment; // Optional comment

    /**
     * Constructs a new {@code Rating} instance.
     *
     * @param userId the ID of the user giving the rating
     * @param score the rating score, must be between 1 and 5
     * @param comment an optional comment (can be empty or null)
     */
    public Rating(String userId, int score, String comment) {
        this.userId = userId;
        this.score = score;
        this.comment = comment;
    }

    /**
     * Returns the user ID of the user giving the rating.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the score of the rating.
     *
     * @return the score, a value between 1 and 5
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the comment associated with the rating.
     *
     * @return the comment, or {@code null} if no comment was provided
     */
    public String getComment() {
        return comment;
    }

    /**
     * Updates the score for the rating.
     *
     * @param newScore the new score, must be between 1 and 5
     * @throws IllegalArgumentException if the score is out of range
     */
    public void setScore(int newScore) {
        this.score = newScore;
    }

    /**
     * Updates the comment for the rating.
     *
     * @param newComment the new comment, can be empty or null
     */
    public void setComment(String newComment) {
        this.comment = newComment;
    }
}
