package antitheftproject.android.cmpe277.antitheftproject.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import antitheftproject.android.cmpe277.antitheftproject.api.NetworkDetectionAPI;
import antitheftproject.android.cmpe277.antitheftproject.api.UtilsAPI;
import antitheftproject.android.cmpe277.antitheftproject.constant.Constant;
import antitheftproject.android.cmpe277.antitheftproject.model.NetworkPojo;
import antitheftproject.android.cmpe277.antitheftproject.services.LocationService;


public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            Toast.makeText(context, "Network changes", Toast.LENGTH_LONG).show();
            boolean notifyUser =  NetworkDetectionAPI.isNetworkChanged(context);
            //if (notifyUser) {
                Intent locService = new Intent(context, LocationService.class);
                context.startService(locService);
            //}
        } else if (intent.getAction().equals(Constant.LOCATION_SERVICE)) {
            //double lon = intent.getExtras().getDouble("longitude");
            //double lat = intent.getExtras().getDouble("latitude");
            String locationAddr = intent.getExtras().getString("location");
            Toast.makeText(context, "Location is received\n" + locationAddr,  Toast.LENGTH_LONG).show();
        } else if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            Intent locService = new Intent(context, LocationService.class);
            context.startService(locService);
            Toast.makeText(context, "App is uninstalled", Toast.LENGTH_LONG).show();
        } /*else if (intent.getAction().equals("android.intent.action.QUERY_PACKAGE_RESTART")) {
            boolean isAppUnstalled = UtilsAPI.isAppUninstalled(intent);
            if (isAppUnstalled) {
                Log.i("Please enter passcode" , " value to uninstalled");
            }
        }*/
    }
}
