package com.rayamajs.cwapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
public class ImageText extends AppCompatActivity {
    //declare objects
    ImageView mPreviewIv;
    EditText mResultEt;
    //declare variables
    private static final int IMAGE_PICK_GALLERY_CODE = 1000;
    private static final int IMAGE_PICK_CAMERA_CODE = 1001;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 400;

    String storagePermission[];
    String cameraPermission [];

    Uri image_uri; //declaring uniform resource identifier

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_text);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Pick Image");
        mResultEt = findViewById(R.id.resultET);
        mPreviewIv = findViewById(R.id.imageIV);
        cameraPermission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    }
    //menu for action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //handle item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addImage){
            showImageImportDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showImageImportDialog() {
        //items to display
        String [] items = {" New", " Existing"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //title set
        dialog.setTitle("Choose Image");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //click camera
                    if (!checkCameraPermission()){
                        //blocked camera so ask for permission
                        requestCameraPermission();
                    }
                    else {
                        //allowed permission so capture photo
                        pickCamera();
                    }
                }
                if (which == 1){
                    //gallery option clicked
                    if(!checkStoragePermission()){
                        //storage blocked request it
                        requestStoragePermission();
                    }
                    else{
                        //pick picture from gallery
                        pickGallery();
                    }
                }
            }
        });
        dialog.create().show();
        //display dialog
    }

    private void pickCamera() {
        //captured image stored into phone storage for better quality
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPicture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }
    private void pickGallery() {
        //select picture from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);

    }
    private boolean checkCameraPermission() {
        //check camera permission and return the result
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED); // save to external storage so there can be high quality image
        return result & result1;
    }

    private boolean checkStoragePermission() {  //storage permission is required to insert to image view.*/
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);

    }
    //handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if (grantResults.length >0){
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults [0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    }
                    else {
                        Toast.makeText(this, "Denied Permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_REQUEST_CODE:
                if (grantResults.length >0){
                    boolean writeStorageAccepted = grantResults [0] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (writeStorageAccepted){
                        pickGallery();
                    }

                    else {
                        Toast.makeText(this, "Denied permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
    //handle image result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //crop image from the gallery
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON) //guideline for image
                        .start(this);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //crop image from the capture
                CropImage.activity(image_uri)
                        .setGuidelines(CropImageView.Guidelines.ON) //guideline for image
                        .start(this);
            }
        }
        //get cropped images
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri(); //get uri of image
                //set image view with image
                mPreviewIv.setImageURI(resultUri);
                //get drawable bitmap for text recognition
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mPreviewIv.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    StringBuilder sb = new StringBuilder();
                    //if there is not text
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock myItem = items.valueAt(i);
                        sb.append(myItem.getValue());
                        sb.append("\n");
                    }
                    mResultEt.setText(sb.toString());
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //display any errors
                Exception error = result.getError();
                Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }
    }
}

