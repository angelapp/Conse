package conse.nrc.org.co.consejo.Utils;

import android.os.Environment;

/**
 * Created by apple on 6/28/18.
 */

public class CheckForSDCard{


        //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(

                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;

    }
}
