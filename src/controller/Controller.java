package controller;

public interface Controller {
    void setRequestingUp(boolean requestingUp);

    void setRequestingDown(boolean requestingDown);

    void setRequestingLeft(boolean requestingLeft);

    void setRequestingRight(boolean requestingRight);

    void setRequestingSprint(boolean requestingSprint);

    void setRequestingHit(boolean requestingHit);

    void setNotRequestingHit(boolean notRequestingHit);

    boolean isRequestingUp();
    boolean isRequestingDown();
    boolean isRequestingLeft();
    boolean isRequestingRight();
    boolean isRequestingSprint();
    boolean isRequestingHit();
    boolean isNotRequestingHit();
    boolean isRequestingESC();
}
