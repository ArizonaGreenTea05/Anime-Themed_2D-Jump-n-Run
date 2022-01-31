package controller;

public interface Controller {
    void setRequestingUp(boolean requestingUp);

    void setRequestingDown(boolean requestingDown);

    void setRequestingLeft(boolean requestingLeft);

    void setRequestingRight(boolean requestingRight);

    void setRequestingSprint(boolean requestingSprint);

    void setRequestingHit(boolean requestingHit);

    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingRight();
    boolean isRequestingSprint();
    boolean isRequestingHit();
    boolean isRequestingESC();
}
