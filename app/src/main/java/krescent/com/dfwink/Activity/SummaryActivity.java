package krescent.com.dfwink.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import krescent.com.dfwink.OrderEvent.OrderStatus;
import krescent.com.dfwink.Pojo.GetOrderCustomStatus;
import krescent.com.dfwink.R;
import krescent.com.dfwink.Utils.SharedPref;
import krescent.com.dfwink.WebService.ApiWebService;

/**
 * Created by sys-roid on 17/11/17.
 */

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener, ApiWebService.StatusListener {

    //private RelativeLayout layoutProcessing, layoutProduction, layoutShipped, layoutCheckProof, layoutPickUp, layoutThankYou;

    //private RelativeLayout layoutAwaitingProcessing, layoutProcessing, layoutShipped, layoutDelivered, layoutWillNotDeliver, layoutReturned, layoutReadyForPickup;

    //New
    private RelativeLayout layoutProcessing, layoutCheckProof, layoutReadyForProduction, layoutReadyForPickup, layoutShipped, layoutThankYou;

    private static String VENDOR_ORDER_NUMBER;

    private static final int PROCESSING_ID = 1, CHECK_PROOF_ID = 2, READY_FOR_PRODUCTION_ID = 3, READY_FOR_PICKUP_ID = 4, SHIPPED_ID = 5, THANK_YOU_ID = 6;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        /*Bundle bundle=getIntent().getExtras();
        status=bundle.getString("STATUS");*/
        initViews();

        //enableDisableOption(getIntent().getExtras().getString("STATUS"));

        VENDOR_ORDER_NUMBER = getIntent().getExtras().getString("VENDOR_ORDER_NUMBER");

        EventBus.getDefault().register(this);

        ApiWebService service = new ApiWebService(this);
        service.getOrderCustomStatus(this, VENDOR_ORDER_NUMBER);
    }

    private void enableDisableOption(String status) {

        /*OrderStatus=
                'AWAITING_PROCESSING',
                'PROCESSING',
                'SHIPPED',
                'DELIVERED',
                'WILL_NOT_DELIVER',
                'RETURNED',
                'READY_FOR_PICKUP'


        switch (status) {

            //layoutAwaitingProcessing,layoutProcessing,layoutShipped,layoutDelivered,layoutWillNotDeliver,layoutReturned,layoutReadyForPickup;

            case "AWAITING_PROCESSING":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //button clicked
                    //layoutAwaitingProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));

                } else {
                    //button clicked
                    layoutAwaitingProcessing.setBackgroundResource(R.drawable.rounded_button_red);
                }
                break;
            case "PROCESSING":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //Highlight recent button click
                    layoutProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));

                    //Disable previous option
                    //layoutAwaitingProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));

                    //Disable button click
                    //layoutAwaitingProcessing.setClickable(false);
                } else {
                    //Highlight recent button click
                    layoutProcessing.setBackgroundResource(R.drawable.rounded_button_red);

                    //Disable Previous option
                    //layoutAwaitingProcessing.setBackgroundResource(R.drawable.rounded_button);
                }

                //Disable button click
                //layoutAwaitingProcessing.setClickable(false);
                break;
            case "SHIPPED":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //Highlight recent button click
                    layoutShipped.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));

                    //Disable previous option
                    layoutAwaitingProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));

                } else {
                    //Highlight recent button click
                    layoutShipped.setBackgroundResource(R.drawable.rounded_button_red);

                    //Disable Previous option
                    layoutAwaitingProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutProcessing.setBackgroundResource(R.drawable.rounded_button);
                }
                //Disable button click
                layoutAwaitingProcessing.setClickable(false);
                layoutProcessing.setClickable(false);
                break;
            case "DELIVERED":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //Highlight recent button click
                    layoutDelivered.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));

                    //Disable previous option
                    layoutAwaitingProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutShipped.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));

                } else {
                    //Highlight recent button click
                    layoutDelivered.setBackgroundResource(R.drawable.rounded_button_red);

                    //Disable Previous option
                    layoutAwaitingProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutShipped.setBackgroundResource(R.drawable.rounded_button);
                }
                //Disable button click
                layoutAwaitingProcessing.setClickable(false);
                layoutProcessing.setClickable(false);
                layoutShipped.setClickable(false);

                break;
            case "WILL_NOT_DELIVER":

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //Highlight recent button click
                    layoutWillNotDeliver.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));

                    //Disable previous option
                    layoutAwaitingProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutShipped.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutDelivered.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));

                } else {
                    //Highlight recent button click
                    layoutWillNotDeliver.setBackgroundResource(R.drawable.rounded_button_red);

                    //Disable Previous option
                    layoutAwaitingProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutShipped.setBackgroundResource(R.drawable.rounded_button);
                    layoutDelivered.setBackgroundResource(R.drawable.rounded_button);
                }
                //Disable button click
                layoutAwaitingProcessing.setClickable(false);
                layoutProcessing.setClickable(false);
                layoutShipped.setClickable(false);
                layoutDelivered.setClickable(false);
                break;
            case "RETURNED":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //Highlight recent button click
                    layoutReturned.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));

                    //Disable previous option
                    layoutAwaitingProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutShipped.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutDelivered.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutWillNotDeliver.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));

                } else {
                    //Highlight recent button click
                    layoutReturned.setBackgroundResource(R.drawable.rounded_button_red);

                    //Disable Previous option
                    layoutAwaitingProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutShipped.setBackgroundResource(R.drawable.rounded_button);
                    layoutDelivered.setBackgroundResource(R.drawable.rounded_button);
                    layoutWillNotDeliver.setBackgroundResource(R.drawable.rounded_button);
                }
                //Disable button click
                layoutAwaitingProcessing.setClickable(false);
                layoutProcessing.setClickable(false);
                layoutShipped.setClickable(false);
                layoutDelivered.setClickable(false);
                layoutWillNotDeliver.setClickable(false);
                break;
            case "READY_FOR_PICKUP":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    //Highlight recent button click
                    layoutReadyForPickup.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));

                    //Disable previous option
                    layoutAwaitingProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutProcessing.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutShipped.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutDelivered.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutWillNotDeliver.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
                    layoutReturned.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));

                } else {
                    //Highlight recent button click
                    layoutReadyForPickup.setBackgroundResource(R.drawable.rounded_button_red);

                    //Disable Previous option
                    layoutAwaitingProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutProcessing.setBackgroundResource(R.drawable.rounded_button);
                    layoutShipped.setBackgroundResource(R.drawable.rounded_button);
                    layoutDelivered.setBackgroundResource(R.drawable.rounded_button);
                    layoutWillNotDeliver.setBackgroundResource(R.drawable.rounded_button);
                    layoutReturned.setBackgroundResource(R.drawable.rounded_button);
                }
                //Disable button click
                layoutAwaitingProcessing.setClickable(false);
                layoutProcessing.setClickable(false);
                layoutShipped.setClickable(false);
                layoutDelivered.setClickable(false);
                layoutWillNotDeliver.setClickable(false);
                layoutReturned.setClickable(false);

                break;
        }*/

    }

    private void initViews() {
        //layoutAwaitingProcessing,layoutProcessing,layoutShipped,layoutDelivered,layoutWillNotDeliver,layoutReturned,layoutReadyForPickup;

/*        layoutDelivered = (RelativeLayout) findViewById(R.id.layoutDelivered);
        layoutWillNotDeliver = (RelativeLayout) findViewById(R.id.layoutWillNotDeliver);
        layoutReturned = (RelativeLayout) findViewById(R.id.layoutReturned);
        layoutAwaitingProcessing = (RelativeLayout) findViewById(R.id.layoutAwaitingProcessing);*/

        layoutProcessing = (RelativeLayout) findViewById(R.id.layoutProcessing);
        layoutCheckProof = (RelativeLayout) findViewById(R.id.layoutCheckProof);
        layoutReadyForProduction = (RelativeLayout) findViewById(R.id.layoutReadyForProduction);
        layoutReadyForPickup = (RelativeLayout) findViewById(R.id.layoutReadyForPickup);
        layoutShipped = (RelativeLayout) findViewById(R.id.layoutShipped);
        layoutThankYou = (RelativeLayout) findViewById(R.id.layoutThankYou);

        layoutProcessing.setOnClickListener(this);
        layoutCheckProof.setOnClickListener(this);
        layoutReadyForProduction.setOnClickListener(this);
        layoutReadyForPickup.setOnClickListener(this);
        layoutShipped.setOnClickListener(this);
        layoutThankYou.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layoutProcessing:
                storeClickedOption(PROCESSING_ID);
                updateOrderStatus(getResources().getString(R.string.web_processing));

                break;
            case R.id.layoutCheckProof:
                storeClickedOption(CHECK_PROOF_ID);
                updateOrderStatus(getResources().getString(R.string.web_check_proof));

                break;
            case R.id.layoutReadyForProduction:
                storeClickedOption(READY_FOR_PRODUCTION_ID);
                updateOrderStatus(getResources().getString(R.string.web_ready_for_production));

                break;
            case R.id.layoutReadyForPickup:
                storeClickedOption(READY_FOR_PICKUP_ID);
                updateOrderStatus(getResources().getString(R.string.web_ready_for_pickup));

                break;
            case R.id.layoutShipped:
                storeClickedOption(SHIPPED_ID);
                updateOrderStatus(getResources().getString(R.string.web_shipped));

                break;
            case R.id.layoutThankYou:
                storeClickedOption(THANK_YOU_ID);
                updateOrderStatus(getResources().getString(R.string.web_thank_you));

                break;
            default:
        }
    }

    /**
     * Method to store recently clicked option into the shared preferences
     *
     * @param optionValue
     */
    private void storeClickedOption(int optionValue) {
        SharedPref pref = new SharedPref(SummaryActivity.this);
        pref.setClickOption(optionValue);
    }


    @Subscribe
    public void onEvent(OrderStatus orderStatus) {
        //enableDisableOption(orderStatus.getOrderStatus());
        Log.d("Test=", "onEvent called ");
        highLightClickedOption(new SharedPref(SummaryActivity.this).getClickOption());
    }

    private void highLightClickedOption(int clickOption) {


        switch (clickOption) {
            case PROCESSING_ID:
                disableOption(layoutProcessing);
                break;
            case CHECK_PROOF_ID:
                disableOption(layoutCheckProof);
                break;
            case READY_FOR_PRODUCTION_ID:
                disableOption(layoutReadyForProduction);
                break;
            case READY_FOR_PICKUP_ID:
                disableOption(layoutReadyForPickup);
                break;
            case SHIPPED_ID:
                disableOption(layoutShipped);
                break;
            case THANK_YOU_ID:
                disableOption(layoutThankYou);
                break;
            default:
                Toast.makeText(this, getResources().getString(R.string.dfw_net_errror), Toast.LENGTH_SHORT).show();
        }
    }

    private void hightLight(RelativeLayout HighLightLayout) {

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //button clicked
            HighLightLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));
        } else {
            //button clicked
            HighLightLayout.setBackgroundResource(R.drawable.rounded_button_red);
        }


        for (RelativeLayout layout : layouts) {

            Log.d("Layout=", String.valueOf(layouts));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
            } else {
                layout.setBackgroundResource(R.drawable.rounded_button);
            }
        }
*/
    }


    /**
     * Method to call web service when user clicked on option
     *
     * @param status contain the clicked option
     */
    private void updateOrderStatus(String status) {
        Log.d("ORDER_NUMBER=", VENDOR_ORDER_NUMBER);
        ApiWebService apiWebService = new ApiWebService(this);
        if (VENDOR_ORDER_NUMBER != null) {
            apiWebService.updateOrderCustomStatus(this, VENDOR_ORDER_NUMBER, status.trim());
        }
    }

    private void getOrderCustomStatus(String vendorOrderNumber) {
        ApiWebService apiWebService = new ApiWebService(this);
        if (vendorOrderNumber != null) {
            apiWebService.getOrderCustomStatus(this, VENDOR_ORDER_NUMBER);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SummaryActivity.this, MainActivity.class));
    }


    @Override
    public void customStatus(GetOrderCustomStatus status) {
        Log.d("Summary=", String.valueOf(status.getUsedStatus()));

        List<String> dCustomStatus = status.getUsedStatus();
        //Remove duplicates elements from the array list
        LinkedHashSet<String> customStatus = new LinkedHashSet<String>();
        customStatus.addAll(dCustomStatus);
        dCustomStatus.clear();
        dCustomStatus.addAll(customStatus);
        Log.d("after=", String.valueOf(dCustomStatus));

        enableDisableOption(dCustomStatus);

    }


    private void enableDisableOption(List<String> dCustomStatus) {

        for (String status : dCustomStatus) {
            switch (status) {
                case "PROCESSING":
                    disableOption(layoutProcessing);
                    break;
                case "CHECK_PROOF_SMS_ONLY":
                    disableOption(layoutCheckProof);
                    break;
                case "READY_FOR_PRODUCTION":
                    disableOption(layoutReadyForProduction);
                    break;
                case "READY_FOR_PICKUP":
                    disableOption(layoutReadyForPickup);
                    break;
                case "SHIPPED":
                    disableOption(layoutShipped);
                    break;
                case "THANKYOU":
                    disableOption(layoutThankYou);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method use to disable the option
     *
     * @param disableLayout contain the layout to disable
     */
    private void disableOption(RelativeLayout disableLayout) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            disableLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button_red));
            disableLayout.setEnabled(false);
        } else {
            disableLayout.setBackgroundResource(R.drawable.rounded_button_red);
            disableLayout.setEnabled(false);
        }

    }

}
