package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;

import kr.hs.dgsw.smartschool.dodamdodam.Model.Token;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.LostfoundWritingActivityBinding;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;
import kr.hs.dgsw.smartschool.dodamdodam.viewmodel.LostFoundViewModel;

public class LostFoundWritingActivity extends BaseActivity<LostfoundWritingActivityBinding> {

    LostFoundViewModel lostFoundViewModel;
    private Boolean isPermission = true;
    int type = 1;
    private File tempFile;
    private String currentFileName;


    @Override
    protected int layoutId() {
        return R.layout.lostfound_writing_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.lostfoundCardImageView.setImageResource(R.drawable.add_picture);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initViewModel();

        binding.lostfoundCardImageView.setOnClickListener(v -> {
            if (isPermission)
                gotoAlbum();
            else
                Toast.makeText(v.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
        });

        // 분실/습득물 체크시 변환
        binding.kindofCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                binding.kindofCheckbox.setText("분실물");
                type = 1;
            } else {
                binding.kindofCheckbox.setText("습득물");
                type = 2;
            }
        });

        binding.postWritingLostfound.setOnClickListener(v -> {
            LostFoundRequest request = new LostFoundRequest();

            editTextEmptyCheck();
            setLostFoundRequestData(request);

            lostFoundViewModel.postCreateLostFound(request);
            Toast.makeText(this, "신청 성공!", Toast.LENGTH_SHORT).show();
        });

        tedPermission();

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

        if (requestCode == 1) {

            Uri photoUri = data.getData();
            Log.d("TAG", "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
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

            setImage();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    private void initViewModel() {
        lostFoundViewModel = new LostFoundViewModel(this);
    }

    private void editTextEmptyCheck() {
        if (binding.writingTitleEdittext.length() == 0) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingTitleEdittext.requestFocus();
        }
        else if (binding.writingPlaceEdittext.length() == 0) {
            Toast.makeText(this, "장소를 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingPlaceEdittext.requestFocus();
        }
        else if (binding.writingContentEdittext.length() == 0) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingContentEdittext.requestFocus();
        }
        else if (binding.writingContactEdittext.length() == 0) {
            Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            binding.writingContactEdittext.requestFocus();
        }
    }

    private LostFoundRequest setLostFoundRequestData(LostFoundRequest request) {

        request.setPlace(binding.writingPlaceEdittext.getText().toString());
        request.setTitle(binding.writingTitleEdittext.getText().toString());
        request.setContent(binding.writingContentEdittext.getText().toString());
        request.setContact(binding.writingContactEdittext.getText().toString());
        if (type > 0) {
            request.setType(type);
        } else {
            Toast.makeText(this, "타입 오류", Toast.LENGTH_SHORT).show();
            return null;
        }


        return request;
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
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
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


        binding.lostfoundCardImageView.setImageBitmap(originalBm);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }
}
