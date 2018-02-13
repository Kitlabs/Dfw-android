package krescent.com.dfwink.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sys-roid on 17/11/17.
 */

public class Apiclient {

    //http://112.196.92.142/barcode/dfwink/api.php?action=GetOrderDetails&OrderNumber=1679

    private static final String BASE_URL = "http://112.196.92.142";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
