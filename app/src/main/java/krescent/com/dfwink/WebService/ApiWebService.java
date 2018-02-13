package krescent.com.dfwink.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import krescent.com.dfwink.Activity.MainActivity;
import krescent.com.dfwink.OrderEvent.OrderStatus;
import krescent.com.dfwink.Pojo.GetOrderCustomStatus;
import krescent.com.dfwink.R;
import krescent.com.dfwink.Activity.SummaryActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sys-roid on 17/11/17.
 */

public class ApiWebService {


    private ProgressDialog progressDialog;
    private final StatusListener statusListener;

    public ApiWebService(StatusListener listener) {
        statusListener = listener;
    }

    public interface StatusListener {
        void customStatus(GetOrderCustomStatus status);
    }


    public void getOrderDetails(final Context ctx, String orderNumber) {

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ApiService getResponse = Apiclient.getClient().create(ApiService.class);
        Call<ResponseBody> call = getResponse.getOrderDetails("GetOrderDetails", orderNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TAG", "Inside onResponse");
                progressDialog.dismiss();

                //On wrong product id
                //{"msg":"error","msg_details":"Not getting order details","msg_resp_details":{"errorMessage":"Order not found, number = 1"}}

                //on correct product id

                try {
                    String responseData = new String(response.body().bytes());
                    Log.d("RESPONSE=", responseData);
                    JSONObject jsonObject = new JSONObject(responseData);

                    if (jsonObject.getString("msg").equals("success")) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("msg_resp_details");
                        String status = jsonObject1.getString("fulfillmentStatus");
                        String vendorOrderNumber = jsonObject1.getString("vendorOrderNumber");
                        Bundle bundle = new Bundle();
                        //bundle.putString("STATUS", status);
                        bundle.putString("VENDOR_ORDER_NUMBER", vendorOrderNumber);
                        Intent intent = new Intent(ctx, SummaryActivity.class);
                        intent.putExtras(bundle);

                        //EventBus.getDefault().post(new OrderDetail(status));
                        ctx.startActivity(intent);


                    } else if (jsonObject.getString("msg").equals("error")) {
                        showDialog(ctx, jsonObject.getString("msg_details"), true);
                    }


                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                showDialog(ctx, ctx.getString(R.string.dfw_net_errror), true);
            }
        });
    }


    public void updateOrderStatus(final Context ctx, String orderNumber, String orderStatus) {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ApiService getResponse = Apiclient.getClient().create(ApiService.class);
        Call<ResponseBody> call = getResponse.updateOrderStatus("UpdateOrderStatus", orderNumber, orderStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    String res = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.getString("msg").equals("success")) {

                        JSONObject orderDetails = jsonObject.getJSONObject("order_details");
                        JSONObject msgDetails = orderDetails.getJSONObject("msg_resp_details");
                        String status = msgDetails.getString("fulfillmentStatus");
                        showDialog(ctx, jsonObject.getString("msg_details"), false);

                        EventBus.getDefault().post(new OrderStatus(jsonObject.getString("msg")));
                    }

                    if (jsonObject.getString("msg").equals("error")) {
                        showDialog(ctx, jsonObject.getString("msg_details"), false);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                showDialog(ctx, ctx.getString(R.string.dfw_net_errror), false);
                Log.d("TAG", "inside onFailure()");
            }
        });
    }

    public void updateOrderCustomStatus(final Context ctx, String orderNumber, String orderStatus) {
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ApiService apiService = Apiclient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.updateOrderStatusCustom("UpdateOrderStatusCustom", orderNumber, orderStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                try {

                    String res = new String(response.body().bytes());
                    JSONObject jsonObject = new JSONObject(res);

                    if (jsonObject.getString("msg").equals("success")) {


                        EventBus.getDefault().post(new OrderStatus(jsonObject.getString("msg")));
                    }

                    if (jsonObject.getString("msg").equals("error")) {
                        showDialog(ctx, jsonObject.getString("msg_details"), false);
                    }

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void getOrderCustomStatus(final Context ctx, final String orderNumber) {

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        ApiService getResponse = Apiclient.getClient().create(ApiService.class);
        Call<GetOrderCustomStatus> call = getResponse.getOrderCustomStatus("GetOrderCustomStatus", orderNumber);
        call.enqueue(new Callback<GetOrderCustomStatus>() {
            @Override
            public void onResponse(Call<GetOrderCustomStatus> call, Response<GetOrderCustomStatus> response) {
                Log.d("TAG", "Inside onResponse");
                progressDialog.dismiss();
                try {

                    GetOrderCustomStatus customStatus = response.body();
                    if (customStatus.getMsg().equals("success")) {
                        if (!customStatus.getUsedStatus().isEmpty()) {
                            //EventBus.getDefault().post(new OrderStatus().setCustomOrder(customStatus.getUsedStatus()));
                            statusListener.customStatus(response.body());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<GetOrderCustomStatus> call, Throwable t) {
                progressDialog.dismiss();
                showDialog(ctx, ctx.getString(R.string.dfw_net_errror), true);
            }
        });
    }


    private void showDialog(final Context context, String msg, final boolean status) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        if (status) {
                            //Take back user to scanning activity
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);

                        }
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


}
