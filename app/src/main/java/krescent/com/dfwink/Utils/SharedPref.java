package krescent.com.dfwink.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sys-roid on 24/11/17.
 */

public class SharedPref {

    private static final String DfwPref = "DfwPref";
    private static final String CLICK_OPTION = "CLICK_OPTION";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPref(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences(DfwPref, Context.MODE_PRIVATE);
    }

    public void setClickOption(int option) {
        editor = sharedPreferences.edit();
        editor.putInt(CLICK_OPTION, option);
        editor.apply();
    }

    public int getClickOption() {
        return sharedPreferences.getInt(CLICK_OPTION, -1);
    }
}
