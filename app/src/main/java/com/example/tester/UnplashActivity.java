package com.example.tester;

import android.app.WallpaperManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UnplashActivity extends AppCompatActivity {

    private Handler mainHandlerUnplash = new Handler();

    RelativeLayout mainView;

    Button btnSetWallpaperUnplash;
    Button btnDownloadWallpaperUnplash;
    Spinner timeSetSpinner;
    EditText setupInputKey;
    Spinner setupInputOrientation;
    Spinner setupInputColor;
    Spinner setupInputSort;
    Button btnSaveInput;


    ArrayAdapter<String> inputOrientation;
    ArrayAdapter<String> inputColor;
    ArrayAdapter<String> inputSort;

    String orientation;
    String color;
    String sort;
    String inputKey;

    String orientationData;
    String colorData;
    String sortData;
    String inputKeyData;
    int numberData = 0;

    ImageView imageView;
    TextView unplashImageURL;
    Button btnNext;
    Button btnPrevious;

    RelativeLayout expandableCard2;
    Button btnDownCard2;
    RelativeLayout expandableCard3;
    Button btnDownCard3;
    RelativeLayout expandableCard4;
    Button btnDownCard4;
    Button btnLoadWallpaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unplash);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        timeSetSpinner = findViewById(R.id.timeSetSpinner);
        ArrayAdapter<String> timeSet = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.timeSetArray));
        timeSet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSetSpinner.setAdapter(timeSet);
        timeSetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedTime = adapterView.getSelectedItem().toString();
                Toast.makeText(adapterView.getContext(), selectedTime, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setupInputKey = findViewById(R.id.setupInputKey);
        setupInputOrientation = findViewById(R.id.setupInputOrientation);
        setupInputColor = findViewById(R.id.setupInputColor);
        setupInputSort = findViewById(R.id.setupInputSort);

        inputOrientation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setupInputOrientation));
        timeSet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setupInputOrientation.setAdapter(inputOrientation);
        setupInputOrientation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOrientation = adapterView.getSelectedItem().toString();
                orientation = selectedOrientation;
                Toast.makeText(adapterView.getContext(), selectedOrientation, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputColor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setupInputColor));
        timeSet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setupInputColor.setAdapter(inputColor);
        setupInputColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedColor = adapterView.getSelectedItem().toString();
                color = selectedColor;
                Toast.makeText(adapterView.getContext(), selectedColor, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inputSort = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setupInputSort));
        timeSet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setupInputSort.setAdapter(inputSort);
        setupInputSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = adapterView.getSelectedItem().toString();
                sort = selectedSort;
                Toast.makeText(adapterView.getContext(), selectedSort, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSaveInput = findViewById(R.id.btnSaveInput);
        btnSaveInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputKey = setupInputKey.getText().toString();
                saveDataUnplash();
                loadDataUnplash();
                updateViewUnplash();
                Toast.makeText(UnplashActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
            }
        });


        mainView = findViewById(R.id.mainView);

        expandableCard2 = findViewById(R.id.expandableCard2);
        btnDownCard2 = findViewById(R.id.btnDownCard2);
        expandableCard3 = findViewById(R.id.expandableCard3);
        btnDownCard3 = findViewById(R.id.btnDownCard3);
        expandableCard4 = findViewById(R.id.expandableCard4);
        btnDownCard4 = findViewById(R.id.btnDownCard4);

        btnDownCard2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard2.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard2.setVisibility(View.VISIBLE);
                    btnDownCard2.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                    //btnDownCard2.setAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_down_to_up));
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard2.setVisibility(View.GONE);
                    btnDownCard2.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                    //btnDownCard2.setAnimation(AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_up_to_down));
                }
            }
        });

        btnDownCard3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard3.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard3.setVisibility(View.VISIBLE);
                    btnDownCard3.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);

                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard3.setVisibility(View.GONE);
                    btnDownCard3.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        btnDownCard4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard4.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard4.setVisibility(View.VISIBLE);
                    btnDownCard4.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);

                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard4.setVisibility(View.GONE);
                    btnDownCard4.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });


        imageView = findViewById(R.id.imagePreview);
        unplashImageURL = findViewById(R.id.unplashImagelURL);

        btnDownloadWallpaperUnplash = findViewById(R.id.btnDownloadWallpaperUnplash);
        btnDownloadWallpaperUnplash.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                downloadWallpaperUnplash();
            }
        });

        btnLoadWallpaper = findViewById(R.id.btnLoadWallpaper);
        btnLoadWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LoadImageThread loadImage = new LoadImageThread();
                    new Thread(loadImage).start();
                    saveDataUnplash();
                    loadDataUnplash();
                    updateViewUnplash();
                    Toast.makeText(UnplashActivity.this, "Load successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btnPrevious = findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberData<=0) {
                    Toast.makeText(UnplashActivity.this, "No previous wallpaper", Toast.LENGTH_SHORT).show();
                } else {
                    numberData--;
                    try {
                        LoadImageThread loadImage = new LoadImageThread();
                        new Thread(loadImage).start();
                        saveDataUnplash();
                        loadDataUnplash();
                        updateViewUnplash();
                        Toast.makeText(UnplashActivity.this, "Moved back", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberData++;
                try {
                    LoadImageThread loadImage = new LoadImageThread();
                    new Thread(loadImage).start();
                    saveDataUnplash();
                    loadDataUnplash();
                    updateViewUnplash();
                    Toast.makeText(UnplashActivity.this, "Skipped", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ((Button)findViewById(R.id.btnReset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UnplashActivity.this, "Data reseted", Toast.LENGTH_SHORT).show();
                deleteDataUnplash();
                loadDataUnplash();
                updateViewUnplash();
            }
        });


        btnSetWallpaperUnplash = findViewById(R.id.btnSetWallpaperUnplash);
        //final Locale vietnam = new Locale("vi","VN");

        btnSetWallpaperUnplash.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                setWallpaperUnplash();
            }
        });


        loadDataUnplash();
        updateViewUnplash();

    }




    class LoadImageThread extends Thread {
        List<String> dataList;
        LoadImageThread() throws IOException {
            this.dataList = unplashWallpaper(inputKeyData, orientationData, colorData, sortData);
        }
        @Override
        public void run() {
            mainHandlerUnplash.post(new Runnable() {
                @Override
                public void run() {
                    unplashImageURL.setText(dataList.get(numberData));
                    Glide.with(UnplashActivity.this).load(dataList.get(numberData)).into(imageView);
                }
            });
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void downloadWallpaperUnplash() {

        File directory = new File(Environment.getExternalStorageDirectory(), "/DCIM/wallpaperChanger");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] dir1 = directory.listFiles();
        assert dir1 != null;
        Arrays.sort(dir1, Comparator.comparingLong(File::lastModified).reversed());


        if (dir1.length >= 10) {
            File deleteFile = new File(String.valueOf(dir1[dir1.length - 1]));
            deleteFile.delete();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss", new Locale("vi", "VN"));
        String date = simpleDateFormat.format(new Date());

        try {
            List<String> dataList = unplashWallpaper(inputKeyData, orientationData, colorData, sortData);

            File file = new File(Environment.getExternalStorageDirectory(), String.format("/DCIM/wallpaperChanger/IMG%s.jpg", date));
            try (InputStream in = new URL(dataList.get(numberData)).openStream()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Files.copy(in, Paths.get(String.valueOf(file)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(UnplashActivity.this, String.valueOf(file), Toast.LENGTH_LONG).show();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setWallpaperUnplash() {

        File directory = new File(Environment.getExternalStorageDirectory(), "/DCIM/wallpaperChanger");
        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] dir1 = directory.listFiles();
        assert dir1 != null;
        Arrays.sort(dir1, Comparator.comparingLong(File::lastModified).reversed());
//        System.out.println(Arrays.toString(dir1));
//        System.out.println(dir1[dir1.length-1]);

        if (dir1.length >= 10) {
            File deleteFile = new File(String.valueOf(dir1[dir1.length - 1]));
            deleteFile.delete();
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-HH-mm-ss", new Locale("vi", "VN"));
        String date = simpleDateFormat.format(new Date());

        try {
            List<String> dataList = unplashWallpaper(inputKeyData, orientationData, colorData, sortData);

            File file = new File(Environment.getExternalStorageDirectory(), String.format("/DCIM/wallpaperChanger/IMG%s.jpg", date));
            try (InputStream in = new URL(dataList.get(numberData)).openStream()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Files.copy(in, Paths.get(String.valueOf(file)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getBaseContext());

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

            wallpaperManager.setBitmap(myBitmap);

            Toast.makeText(UnplashActivity.this, String.valueOf(file), Toast.LENGTH_LONG).show();

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }



    public void saveDataUnplash() {
        SharedPreferences sharedPreferencesUnplash = getSharedPreferences("UnplashData", MODE_PRIVATE);
        SharedPreferences.Editor editorUnplash = sharedPreferencesUnplash.edit();


        editorUnplash.putInt("numberData", numberData);
        editorUnplash.putString("inputKeyDefault", inputKey);
        editorUnplash.putString("orientationDefault", orientation);
        editorUnplash.putString("colorDefault", color);
        editorUnplash.putString("sortDefault", sort);

        editorUnplash.apply();
    }

    public void loadDataUnplash() {
        SharedPreferences sharedPreferencesUnplash = getSharedPreferences("UnplashData", MODE_PRIVATE);

        numberData = sharedPreferencesUnplash.getInt("numberData", 0);
        inputKeyData = sharedPreferencesUnplash.getString("inputKeyDefault", "");
        orientationData = sharedPreferencesUnplash.getString("orientationDefault", "portrait");
        colorData = sharedPreferencesUnplash.getString("colorDefault", "none");
        sortData = sharedPreferencesUnplash.getString("sortDefault", "relevance");
    }


    public void updateViewUnplash() {
        setupInputKey.setText(inputKeyData);
        setupInputOrientation.setSelection(inputOrientation.getPosition(orientationData));
        setupInputColor.setSelection(inputColor.getPosition(colorData));
        setupInputSort.setSelection(inputSort.getPosition(sortData));

        ((TextView)findViewById(R.id.data1)).setText(String.valueOf("Postion  :  " + numberData));
        ((TextView)findViewById(R.id.data2)).setText(String.valueOf("Input key  :  " + inputKeyData));
        ((TextView)findViewById(R.id.data3)).setText(String.valueOf("Orientation  :  " + orientationData));
        ((TextView)findViewById(R.id.data4)).setText(String.valueOf("Color  :  " + colorData));
        ((TextView)findViewById(R.id.data5)).setText(String.valueOf("Sort  :  " + sortData));

    }

    public void deleteDataUnplash() {
        SharedPreferences sharedPreferencesUnplash = getSharedPreferences("UnplashData", MODE_PRIVATE);
        sharedPreferencesUnplash.edit().remove("numberData").apply();
        sharedPreferencesUnplash.edit().remove("inputKeyDefault").apply();
        sharedPreferencesUnplash.edit().remove("orientationDefault").apply();
        sharedPreferencesUnplash.edit().remove("colorDefault").apply();
        sharedPreferencesUnplash.edit().remove("sortDefault").apply();
    }

    public List<String> unplashWallpaper(String inputKeyData, String orientationData, String colorData, String sortData) throws IOException {

        List<String> linkData = new ArrayList<>();

        if (sortData.equals("relevance")) {
            if (colorData.equals("none")) {
                String unplashURL = String.format("https://unsplash.com/s/photos/%s?orientation=%s", inputKeyData, orientationData);
                Document doc = Jsoup.connect(unplashURL).userAgent("Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/W.X.Y.Z Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").get();
                Elements data1 = doc.select("a._2Mc8_");

                for (Element value: data1) {
                    String link = value.attr("href");
                    linkData.add("https://unsplash.com"+ link + "/download?force=true");
                }

            } else {
                String unplashURL = String.format("https://unsplash.com/s/photos/%s?orientation=%s&color=%s", inputKeyData, orientationData, colorData);
                Document doc = Jsoup.connect(unplashURL).userAgent("Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/W.X.Y.Z Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").get();
                Elements data1 = doc.select("a._2Mc8_");

                for (Element value: data1) {
                    String link = value.attr("href");
                    linkData.add("https://unsplash.com"+ link + "/download?force=true");
                }
            }

        } else {
            if (colorData.equals("none")) {
                String unplashURL = String.format("https://unsplash.com/s/photos/%s?%sorder_by=&orientation=%s", inputKeyData, sortData, orientationData);
                Document doc = Jsoup.connect(unplashURL).userAgent("Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/W.X.Y.Z Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").get();
                Elements data1 = doc.select("a._2Mc8_");

                for (Element value: data1) {
                    String link = value.attr("href");
                    linkData.add("https://unsplash.com"+ link + "/download?force=true");
                }
            } else {
                String unplashURL = String.format("https://unsplash.com/s/photos/%s?%sorder_by=&orientation=%s&color=%s", inputKeyData, sortData, orientationData, colorData);
                Document doc = Jsoup.connect(unplashURL).userAgent("Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5X Build/MMB29P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/W.X.Y.Z Mobile Safari/537.36 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").get();
                Elements data1 = doc.select("a._2Mc8_");

                for (Element value: data1) {
                    String link = value.attr("href");
                    linkData.add("https://unsplash.com"+ link + "/download?force=true");
                }
            }

        }

        /*
        Document doc2 = Jsoup.connect(linkData.get(numberData)).get();

        Elements thumbnail = doc2.select("div.c_6Je").select("img._2UpQX");
        Elements fullImage = doc2.select("a[href]");
        for (Element detailedValue: thumbnail) {
            thumbnailData = (((detailedValue.toString()).substring(detailedValue.toString().indexOf("srcset"), detailedValue.toString().indexOf("alt"))).replace("srcset=\"","")).split(",");
            break;
        }
        for (Element detailedValue: fullImage) {
            if (detailedValue.attr("href").contains("force=true")) {
                imgData = detailedValue.attr("href");
            }
        }
         */

        //System.out.println(linkData.get(numberData));
        //System.out.println(thumbnailData.length);
        //System.out.println(Arrays.toString(thumbnailData));
        //System.out.println(thumbnailData[0].substring(0,thumbnailData[0].indexOf(" ")));
        //returnData.add(thumbnailData[numberData].substring(0,thumbnailData[numberData].indexOf(" ")));

        return linkData;
    }


    @Override
    public void finish() {
        super.finish();
        inputKey = setupInputKey.getText().toString();
        saveDataUnplash();
    }
}