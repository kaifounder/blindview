package com.example.zkw.blindview;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class PaintFragment extends Fragment {
    private SeekBar seekBar;
    private Button textView;
    private Button paint;
    private Button eraser;
    private Button choose_color;
    private Button takePhoto;
    private Button chooseFromAlbum;
    private DrawView myView;
    private int penSize;

    private static final int TAKE_PHOTO = 1;
    private static final int camerquest = 1;
    private static final int writequest = 2;
    private static final int CHOOSE_PHOTO = 2;
    private Uri imageUri;
    private File outputImage;
    private String imageName;
    public static Bitmap bitmap;

    private View view;
    private RelativeLayout penSize_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.layout_paint,container,false);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        penSize_layout = (RelativeLayout)getActivity().findViewById(R.id.penSize);
        paint = (Button) getActivity().findViewById(R.id.draw);
        eraser = (Button) getActivity().findViewById(R.id.clear);
        choose_color = (Button) getActivity().findViewById(R.id.choose_color);
        myView = (DrawView) getActivity().findViewById(R.id.main_draw);

        seekBar = (SeekBar) getActivity().findViewById(R.id.progress);
        textView = (Button) getActivity().findViewById(R.id.text1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                penSize = progress;
                textView.setText( Integer.toString(progress) + ":确定");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "停止滑动！");
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penSize_layout.setVisibility(View.GONE);
                myView.pen_size(penSize+5);
                myView.eraser_size(penSize+20);
            }
        });

        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penSize_layout.setVisibility(View.VISIBLE);
                myView.hua();
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penSize_layout.setVisibility(View.VISIBLE);
                myView.clear();
            }
        });

        choose_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ColorChooseActivity.class);
                startActivity(intent);
            }
        });

        takePhoto = (Button)getActivity().findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取相机权限
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},camerquest);
                }else {
                    openCamera();
                }

            }
        });

        chooseFromAlbum = (Button)getActivity().findViewById(R.id.choose_from_album);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取读取权限
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},writequest);
                }else {
                    openAlbum();
                }
            }
        });
    }
    private void openAlbum() {
        //打开相册
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");//选择图片
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void openCamera() {
        //启动相机程序，并将相机照的图片返回过来
        try{
            createImageFile();
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void createImageFile(){
        //创建文件
        imageName = "output_image.jpg";
        outputImage = new File(getContext().getExternalCacheDir(),imageName);
        try {
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(getContext(),
                    "com.example.blindview.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case camerquest:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }else {
                    Toast.makeText(getContext(),
                            "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case writequest:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(getContext(),
                            "You denied the permission", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        //将拍摄的照片显示出来
                        bitmap = BitmapFactory.decodeStream
                                (getContext().getContentResolver().openInputStream(imageUri));
                        myView.setbackground(bitmap);
                        //saveBitmap(bitmap);
                        //updateSystemGallery();
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else {
                        //4.4及以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(),uri)){
            //如果是document类型的Uri。则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.document".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if ("com.android.providers.downloads.document".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse
                        ("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContext().getContentResolver().query(uri,null,selection,
                null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void displayImage(String imagePath) {
        if (imagePath != null){
            bitmap = BitmapFactory.decodeFile(imagePath);
            myView.setbackground(bitmap);
        }else {
            Toast.makeText(getContext(),"failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

}
