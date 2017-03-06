package oktalkassignmentapp.sumit.com.oktalkassignmentapp.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import oktalkassignmentapp.sumit.com.oktalkassignmentapp.R;
import oktalkassignmentapp.sumit.com.oktalkassignmentapp.adapter.Assignment1Adapter;
import oktalkassignmentapp.sumit.com.oktalkassignmentapp.model.NameValuePairItem;

/**
 * Created by sumit on 3/4/17.
 */

public class Assignment1Activity extends AppCompatActivity {

    private static final String TAG = Assignment1Activity.class.getSimpleName();
    private static final int FILE_CHOOSER_REQUEST_CODE = 201;
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 36;

    private RecyclerView mListOfWordsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_1);

        Button mReselectAFileButton = (Button) findViewById(R.id.scan_new_file_btn);
        mReselectAFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkExternalStoragePermission();
            }
        });

        mListOfWordsRv = (RecyclerView) findViewById(R.id.word_with_occurrence_number_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);
        mListOfWordsRv.setLayoutManager(layoutManager);

        checkExternalStoragePermission();
    }

    private void startFilePickerActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, getString(R.string.select_file_to_upload)),
                    FILE_CHOOSER_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, R.string.install_file_manager,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            Log.d(TAG, "File Uri: " + uri.toString());
            // Get the path
            String path = null;
            try {
                path = getPath(this, uri);
                Log.d(TAG, "File Path: " + path);
                if (path != null) {
                    getWordsOccurrenceFromFile(path);
                } else {

                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


        }
    }

    private void getWordsOccurrenceFromFile(String filepath) {

        //HashMap<String, Integer> wordCountMap = new HashMap<>();
        ArrayList<NameValuePairItem> wordCountMap = new ArrayList<>();
        Assignment1Adapter assignment1Adapter = new Assignment1Adapter(Assignment1Activity.this, wordCountMap);
        mListOfWordsRv.setAdapter(assignment1Adapter);

        BufferedReader reader = null;

        File file = new File(filepath);
        if (file.exists()) {
            try {
                int currentIndex = 0;
                int previousIndex = 0;
                reader = new BufferedReader(new FileReader(file));
                String currentLine = reader.readLine();

                Map<String, Integer> tempWordCountMap = new HashMap<>();

                while (currentLine != null) {

                    if (currentIndex % 10 != 0) {
                        String[] words = currentLine.toLowerCase().split(" ");
                        for (String word : words) {
                            if (tempWordCountMap.containsKey(word)) {
                                tempWordCountMap.put(word.replaceAll("\\W", ""), tempWordCountMap.get(word) + 1);
                            } else {
                                tempWordCountMap.put(word, 1);
                            }
                        }
                        currentLine = reader.readLine();
                        currentIndex++;

                    } else {
                        if (currentIndex > 0) {
                            wordCountMap.add(new NameValuePairItem(String.format(Locale.ENGLISH, "%d - %d", previousIndex, currentIndex), -1));
                            wordCountMap.addAll(addAllFromHashMap(tempWordCountMap));
                            previousIndex = currentIndex;
                            assignment1Adapter.notifyDataSetChanged();
                            tempWordCountMap = new HashMap<>();
                        }

                        currentIndex++;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<NameValuePairItem> addAllFromHashMap(Map<String, Integer> mWordMap) {

        mWordMap = sortByValue(mWordMap);

        ArrayList<NameValuePairItem> tempList = new ArrayList<>();
        Set<String> keySet =  mWordMap.keySet();
        for (String key : keySet) {
            tempList.add(new NameValuePairItem(key, mWordMap.get(key)));
        }

        return tempList;
    }

    private void checkExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startFilePickerActivity();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Assignment1Activity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showRationaleReadStorageDialog();
            } else {
                ActivityCompat.requestPermissions(Assignment1Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }

        }
    }

    private void showRationaleReadStorageDialog() {
        Dialog dialog;
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(Assignment1Activity.this);
        builder.setTitle(R.string.read_external_storage_request_title);
        builder.setMessage(R.string.read_external_storage_request)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                        ActivityCompat.requestPermissions(Assignment1Activity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }


        return sortedMap;
    }
}
