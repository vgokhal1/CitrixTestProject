package androidpractice.demo.com.citrixtestproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class Permissions {

    public static boolean hasStorageReadWritePermissionGranted(Context context)
    {
        boolean hasPermission = false;

        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)) {
                Log.e("Permission","Permission is granted");
                hasPermission = true;
            } else {

                Log.e("Permission","Permission is revoked");
                ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                hasPermission = false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.e("Permission","Permission is granted");
            hasPermission = true;
        }

        return hasPermission;
    }
}
