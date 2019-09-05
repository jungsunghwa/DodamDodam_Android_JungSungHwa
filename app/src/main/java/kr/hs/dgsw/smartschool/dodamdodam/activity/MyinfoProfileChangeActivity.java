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
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.StudentViewModel;

public class MyinfoProfileChangeActivity extends BaseActivity<MyinfoProfileChangeActivityBinding> {

    private final int PICK_FROM_ALBUM = 1;
    private final int REQUEST_IMAGE_CROP = 2;

    private MemberViewModel memberViewModel;
    private StudentViewModel studentViewModel;
    private Uri photoURI;

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
        observeStudentViewModel();

        clickEvent();

        tedPermission();
    }

    private void observeMemberViewModel() {
        memberViewModel.getSuccessMessage().observe(this, message -> {
            studentViewModel.getClasses();
            studentViewModel.getStudent();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
        memberViewModel.getErrorMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    private void observeStudentViewModel() {
        studentViewModel.getIsSuccess().observe(this, sucess -> {
            if (sucess) {
                finish();
            }
        });
    }

    private void clickEvent() {
        binding.profileImage.setOnClickListener(v -> {
            if (isPermission)
                goToAlbum();
            else
                Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
        });

        binding.changeBtn.setOnClickListener(v -> {
            setRequest();
            memberViewModel.change();
        });
    }

    private void setRequest() {
        if (!binding.inputEmail.getText().toString().isEmpty()) {
            memberViewModel.request.setEmail(binding.inputEmail.getText().toString());
        }
        if (!binding.inputPhone.getText().toString().isEmpty()) {
            memberViewModel.request.setMobile(binding.inputPhone.getText().toString());
        }
        if (!binding.inputStatusMessage.getText().toString().isEmpty()) {
            memberViewModel.request.setStatus_message(binding.inputStatusMessage.getText().toString());
        }
    }

    private void goToAlbum() {

        tedPermission();

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(memberViewModel.file.getValue() != null) {
                if (memberViewModel.file.getValue().exists()) {
                    if (memberViewModel.file.getValue().delete()) {
                        Log.e("TAG", memberViewModel.file.getValue().getAbsolutePath() + " 삭제 성공");
                        memberViewModel.file.setValue(null);
                    }
                }
            }

            return;
        }

        switch (requestCode) {
            case PICK_FROM_ALBUM:
                if (data == null) {
                    return;
                }

                photoURI = data.getData();

                cropImage();

                break;


            case REQUEST_IMAGE_CROP:

                if (resultCode == Activity.RESULT_OK) {

                    memberViewModel.request.setProfile_image(new ImgUpload(this).ImgUpload(changeToBytes(), memberViewModel.file.getValue().getName()).get(0).getUpload_name());
                    setImage();
                }

                break;
        }
    }

    private void cropImage() {

        File file = new File(Environment.getExternalStorageDirectory().toString() + "/DodamDodam");

        if (!file.exists())

            file.mkdirs();

        memberViewModel.file.setValue(new File(Environment.getExternalStorageDirectory().toString() + "/DodamDodam/profile" + new Random().nextInt(100000000) + ".jpg"));

        try {
            memberViewModel.file.getValue().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        memberViewModel.uri.setValue(Uri.fromFile(memberViewModel.file.getValue()));

        Intent cropIntent = new Intent("com.android.camera.action.CROP").addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("output",memberViewModel.uri.getValue());

        startActivityForResult(cropIntent,REQUEST_IMAGE_CROP);

    }

    private byte[] changeToBytes() {
        int size = (int) memberViewModel.file.getValue().length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(memberViewModel.file.getValue()));
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

    private void setImage() {
        Glide.with(this).load(memberViewModel.uri.getValue()).into(binding.profileImage);
        memberViewModel.file.setValue(null);
    }

    private void initView() {
        DatabaseHelper helper = DatabaseHelper.getInstance(this);
        Member member = helper.getMyInfo();

        if (member.getProfileImage() != null) {
            Glide.with(this).load(member.getProfileImage()).into(binding.profileImage);
        }
        binding.inputStatusMessage.setText(member.getStatusMessage());
        binding.inputEmail.setText(member.getEmail());
        if (member.getMobile().substring(0, 0).equals("0")) {
            binding.inputPhone.setText(member.getMobile());
        }
        else {
            binding.inputPhone.setText(0 + member.getMobile());
        }
    }

    private void initViewModel() {
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
    }

    @Override
    protected int layoutId() {
        return R.layout.myinfo_profile_change_activity;
    }
}
