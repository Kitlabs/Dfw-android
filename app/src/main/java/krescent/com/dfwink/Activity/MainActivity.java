package krescent.com.dfwink.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import krescent.com.dfwink.OrderEvent.OrderDetail;
import krescent.com.dfwink.Pojo.GetOrderCustomStatus;
import krescent.com.dfwink.R;
import krescent.com.dfwink.WebService.ApiWebService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ApiWebService.StatusListener {

    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);
        //register EventBus(this activity is a subscriber)
        //EventBus.getDefault().register(this);
        openCamera();
    }


    @Override
    public void onClick(View v) {


/*
        ApiWebService response = new ApiWebService();
        response.getOrderDetails(this, "GetOrderDetails", "1679");
*/

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setPrompt("Scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();

    }

    private void openCamera() {

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                finish();
                //Log.d("Tag=", "null");
                //startActivity(new Intent(MainActivity.this, MainActivity.class));
            } else {
                //take data from here to hit the webservice
                /*tvScanFormat.setText("Format=" + result.getFormatName());
                tvScanContent.setText("Content=" + result.getContents());*/
                String scanContent = result.getContents();
                String orderNumber = scanContent.substring(scanContent.lastIndexOf("-") + 1);

/*                Log.d("OrderNumber=", orderNumber);
                Log.d("Contents=", result.getContents());
                Log.d("format_name=", result.getFormatName());*/

                ApiWebService response = new ApiWebService(this);
                response.getOrderDetails(this, orderNumber.trim());
            }
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(MainActivity.this, MainActivity.class));
    }


    @Override
    public void customStatus(GetOrderCustomStatus status) {

    }
}
