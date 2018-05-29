package androidpractice.demo.com.citrixtestproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton exploreFiles;
    String filePath, fileData;
    Uri fileUri;
    private static final int READ_REQUEST_CODE = 4;
    ArrayList<Contacts> contactsList = new ArrayList<>();

    ContactsRecyclerAdapter contactsRecyclerAdapter;
    RecyclerView contactsRecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exploreFiles = (FloatingActionButton) findViewById(R.id.explore_button);
        contactsRecyclerview = (RecyclerView) findViewById(R.id.contacts_recyclerview);

        contactsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        exploreFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Permissions.hasStorageReadWritePermissionGranted(MainActivity.this)){
                    openFileChooser();
                }


            }
        });
    }

    void openFileChooser(){

        Intent chooseFile = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("*/*");
//                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                fileUri = data.getData();
//                Log.e("SelectedFile", "Uri: " + fileUri.toString());

                filePath = fileUri.getPath();
//                Log.e("SelectedFile", "Url: " + filePath);

                try {
                    fileData = loadJSONFromFile(fileUri);
//                    Log.e("SelectedFile", "FileData: " + fileData);

                    JSONObject jsonObject = new JSONObject(fileData);
//                    Log.e("SelectedFile", "JSONData: " + jsonObject.toString());

                    JSONArray jsonArray = jsonObject.getJSONArray("contacts");
//                    Log.e("SelectedFile", "JSONArrayData: " + jsonArray);

//                    Log.e("SelectedFile", "JSONArrayDataLength: " + jsonArray.length());


                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject js = (JSONObject) jsonArray.get(i);

                        Contacts contacts = new Contacts();
                        LinkedHashMap<String,ArrayList<String>> contactDetails = new LinkedHashMap<>();

//                        Log.e("SelectedFile", "JSONObjectData: " + js.toString());

                        Iterator<String> iter = js.keys();
                        while (iter.hasNext()){
                            String key = iter.next();
                            if (key.contains("companyName") | key.contains("name")){

                                String name = (String) js.get(key);

//                                Log.e("SelectedFile", "JSONObjectDataEntry: " +key+" "+name);

                                contacts.setContactName(name);
                            }
                            else {
                                try{
                                    Object val  =js.get(key);

                                    if (val instanceof JSONObject){

                                        String jsonvalue = (String) js.get(key);

//                                        Log.e("SelectedFile", "JSONObjectDataEntry: " +key+" "+jsonvalue);

                                        ArrayList<String> value = new ArrayList<>();
                                        value.add(jsonvalue);

                                        contactDetails.put(key,value);


                                    }
                                    else if (val instanceof JSONArray){

                                        JSONArray jsonArray1 = js.getJSONArray(key);
                                        ArrayList<String> value = new ArrayList<>();

                                        for (int j=0;j<jsonArray1.length();j++){
                                            value.add((String) jsonArray1.get(j));
                                        }

                                        contactDetails.put(key,value);

                                    }
                                }
                                catch (JSONException e){
                                    //Something went Wrong....
                                }
                            }
                        }

//                        Log.e("SelectedFile", "ContactData: " + contacts.toString());

                        contacts.setContactDetails(contactDetails);
                        contactsList.add(contacts);

//                        Log.e("SelectedFile", "ContactKeys: " + contactsList.size());
                    }

//                    Log.e("SelectedFile", "ContactLength: " + contactsList.size());

                    contactsRecyclerAdapter = new ContactsRecyclerAdapter(MainActivity.this,contactsList);
                    contactsRecyclerview.setAdapter(contactsRecyclerAdapter);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                openFileChooser();
            } else {
                // permission denied
            }

        }
    }

    public String loadJSONFromFile(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
