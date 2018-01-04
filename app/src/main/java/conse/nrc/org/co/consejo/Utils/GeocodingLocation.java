package conse.nrc.org.co.consejo.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import conse.nrc.org.co.consejo.R;

/**
 * Created by apple on 1/26/17.
 */

public class GeocodingLocation {

    private static final String TAG = "GeocodingLocation";

    public static void getLocationFromAddress(final String locationAddress,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                Address address = null;
                try {
                    List<Address> addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if (addressList != null && addressList.size() > 0) {
                        address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getLatitude()).append("\n");
                        sb.append(address.getLongitude()).append("\n");
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putDouble(LocalConstants.LATITUDE, address.getLatitude());
                        bundle.putDouble(LocalConstants.LONGITUDE, address.getLongitude());
                        result = "Address: " + locationAddress +
                                "\n\nLatitude and Longitude :\n" + result;
                        //bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Address: " + locationAddress +
                                "\n Unable to get Latitude and Longitude for this address location.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String errorMessage = "";
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result;
                Address address;
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                }catch (IllegalArgumentException illegalArgumentException) {
                    // Catch invalid latitude or longitude values.
                    Log.e(TAG, errorMessage + ". " +
                            "Latitude = " + latitude +
                            ", Longitude = " +
                            longitude, illegalArgumentException);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (addressList == null || addressList.size()  == 0) {
                        if (errorMessage.isEmpty()) {
                            errorMessage = context.getString(R.string.no_address_found);
                            Log.e(TAG, errorMessage);
                        }
                        result = errorMessage;
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);

                    } else {
                        address = addressList.get(0);
                        result = address.getAddressLine(0);
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static void getCityFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                String errorMessage = "";
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result;
                Address address;
                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    Log.e(TAG, "Unable to connect to Geocoder", e);
                }catch (IllegalArgumentException illegalArgumentException) {
                    // Catch invalid latitude or longitude values.
                    Log.e(TAG, errorMessage + ". " +
                            "Latitude = " + latitude +
                            ", Longitude = " +
                            longitude, illegalArgumentException);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (addressList == null || addressList.size()  == 0) {
                        if (errorMessage.isEmpty()) {
                            errorMessage = context.getString(R.string.no_address_found);
                            Log.e(TAG, errorMessage);
                        }
                        result = errorMessage;
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        message.setData(bundle);

                    } else {
                        address = addressList.get(0);
                        result = address.getLocality() + " - " + address.getAdminArea();// address.getAddressLine(0);
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("city", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

}
