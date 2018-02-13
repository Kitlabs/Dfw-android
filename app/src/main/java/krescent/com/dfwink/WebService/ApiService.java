package krescent.com.dfwink.WebService;

import krescent.com.dfwink.Pojo.GetOrderCustomStatus;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by sys-roid on 17/11/17.
 */

public interface ApiService {

    ////http://112.196.92.142/barcode/dfwink/api.php?action=GetOrderDetails&OrderNumber=1679

    @GET("/barcode/dfwink/api.php")
    Call<ResponseBody> getOrderDetails(@Query("action") String action, @Query("OrderNumber") String orderNumber);


    //http://112.196.92.142/barcode/dfwink/api.php?action=UpdateOrderStatus&OrderNumber=1679&OrderStatus=AWAITING_PROCESSING
    @FormUrlEncoded
    @POST("/barcode/dfwink/api.php")
    Call<ResponseBody> updateOrderStatus(@Field("action") String action, @Field("OrderNumber") String orderNumber, @Field("OrderStatus") String orderStatus);



    //http://112.196.92.142/barcode/dfwink/api.php?action=GetOrderCustomStatus&OrderNumber=1679
    @GET("/barcode/dfwink/api.php")
    Call<GetOrderCustomStatus> getOrderCustomStatus(@Query("action") String action, @Query("OrderNumber") String orderNumber);

    //http://112.196.92.142/barcode/dfwink/api.php?action=UpdateOrderStatusCustom&OrderNumber=1679&OrderStatus=PROCESSING
    @FormUrlEncoded
    @POST("/barcode/dfwink/api.php")
    Call<ResponseBody> updateOrderStatusCustom(@Field("action") String action, @Field("OrderNumber") String orderNumber, @Field("OrderStatus") String orderStatus);

}
