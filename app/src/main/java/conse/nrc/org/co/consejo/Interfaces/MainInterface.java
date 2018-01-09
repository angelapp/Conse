package conse.nrc.org.co.consejo.Interfaces;

/**
 * Created by apple on 11/27/17.
 */

public interface MainInterface {

    void startVbgCourse();
    void startLeadersCourse();

    void setPreviousFragment();

    void saveActivityCompleted(int activity_code);

    void hideToolBar(boolean hide);

    void setCourseSelectionFragment();

    void openDoc(String doc);

    void shareDoc(String doc);

    boolean validateGpsStatus(boolean show_alert);

    boolean validateNetworkStatus(boolean show_alert);
}
