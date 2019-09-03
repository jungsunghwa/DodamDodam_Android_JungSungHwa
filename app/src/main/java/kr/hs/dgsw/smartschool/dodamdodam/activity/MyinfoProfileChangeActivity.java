package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import kr.hs.dgsw.b1nd.service.model.Member;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.database.DatabaseHelper;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.MyinfoProfileChangeActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.fileupload.ImgUpload;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.MyinfoChangeRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.MemberViewModel;

public class MyinfoProfileChangeActivity extends BaseActivity<MyinfoProfileChangeActivityBinding> {

    private final int PICK_FROM_ALBUM = 1;
    private final int REQUEST_IMAGE_CROP = 2;

    private MemberViewModel memberViewModel;
    private File tempFile;
    private Uri tempUri;
    private String currentFileName;

    private Boolean isPermission = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();
        initView();

        observeMemberViewModel();

        clickEvent();

        tedPermission();
    }

    private void observeMemberViewModel() {
        memberViewModel.getSuccessMessage().observe(this, message -> {
            tempFile = null;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void clickEvent() {
        binding.profileImage.setOnClickListener(v -> {
            if (isPermission)
                gotoAlbum();
            else
                Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
        });

        binding.changeBtn.setOnClickListener(v -> {
            setRequest();
            memberViewModel.change();
        });
    }

    private void setRequest() {
        memberViewModel.request.setEmail(binding.inputEmail.getText().toString());
        memberViewModel.request.setMobile(binding.inputPhone.getText().toString());
        memberViewModel.request.setStatus_message(binding.inputStatusMessage.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e("TAG", tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();
            Log.d("TAG", "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

                Log.d("TAG", "tempFile Uri : " + Uri.fromFile(tempFile));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            memberViewModel.request.setProfile_image(new ImgUpload(this).ImgUpload(changeToBytes(),tempFile.getName()).get(0));

            cropImage();

        }
        else if (requestCode == REQUEST_IMAGE_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                setImage();
            }
        }
    }

    private void cropImage() {

        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(tempUri, "image/*");
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("output",tempUri);

        startActivityForResult(cropIntent,REQUEST_IMAGE_CROP);

    }

    private byte[] changeToBytes() {
        int size = (int) tempFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(tempFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this,LostFoundActivity.class));
            finish();
            return true;
        }

        return false;
    }

    private void tedPermission() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 성공
                isPermission = true;
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 실패
                isPermission = false;
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void gotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    private void setImage() {

        BitmapFactory.Options options = new BitmapFactory.Options();
        this.currentFileName = tempFile.getAbsolutePath();
        Bitmap originalBm = BitmapFactory.decodeFile(this.currentFileName, options);
        Log.d("TAG", "setImage : " + this.currentFileName);


        binding.profileImage.setImageBitmap(originalBm);
        tempFile = null;
    }

    private void initView() {
        DatabaseHelper helper = DatabaseHelper.getInstance(this);
        Member member = helper.getMyInfo();

        if (member.getProfileImage() != null) {
            Glide.with(this).load(member.getProfileImage()).into(binding.profileImage);
        }
        binding.inputStatusMessage.setText(member.getStatusMessage());
        binding.inputEmail.setText(member.getEmail());
        binding.inputPhone.setText(member.getMobile());
    }

    private void initViewModel() {
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
    }

    @Override
    protected int layoutId() {
        return R.layout.myinfo_profile_change_activity;
    }
}
