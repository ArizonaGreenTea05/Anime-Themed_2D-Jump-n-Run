package controller;

public interface Controller {
    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingRight();
    boolean isRequestingSprint();
    boolean isRequestingHit();
    boolean isNotRequestingHit();
    boolean isRequestingESC();
}
