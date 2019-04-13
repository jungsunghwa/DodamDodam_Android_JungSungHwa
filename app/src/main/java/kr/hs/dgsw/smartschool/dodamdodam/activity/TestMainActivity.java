package kr.hs.dgsw.smartschool.dodamdodam.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;
import androidx.databinding.DataBindingUtil;
import androidx.palette.graphics.Palette;

import java.util.Random;

import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.databinding.TestMainActivityBinding;

public class TestMainActivity extends BaseActivity<TestMainActivityBinding> {

    private static final String TAG = "TestMainActivity";

    @Override
    protected int layoutId() {
        return R.layout.test_main_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding.background.setFactory(() -> {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT));
            return imageView;
        });
        binding.background.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        binding.background.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));


        binding.button.setOnClickListener(v -> nextImage());
    }

    private void nextImage() {
        Drawable drawable = getRandomBackgroundDrawable();
        Palette palette = Palette.from(((BitmapDrawable) drawable).getBitmap()).generate();
        Palette.Swatch swatch;
        binding.background.setImageDrawable(drawable);

        if ((swatch = palette.getDominantSwatch()) != null) {
            Log.d(TAG, "DominantSwatch_Title: " + Integer.toHexString(swatch.getTitleTextColor()));
            Log.d(TAG, "DominantSwatch_Body: " + Integer.toHexString(swatch.getBodyTextColor()));
            Log.d(TAG, "DominantSwatch_Rgb: " + Integer.toHexString(swatch.getRgb()));
        }
        if ((swatch = palette.getMutedSwatch()) != null) {
            Log.d(TAG, "MutedSwatch_Title: " + Integer.toHexString(swatch.getTitleTextColor()));
            Log.d(TAG, "MutedSwatch_Body: " + Integer.toHexString(swatch.getBodyTextColor()));
            Log.d(TAG, "MutedSwatch_Rgb: " + Integer.toHexString(swatch.getRgb()));
        }
        if ((swatch = palette.getVibrantSwatch()) != null) {
            Log.d(TAG, "VibrantSwatch_Title: " + Integer.toHexString(swatch.getTitleTextColor()));
            Log.d(TAG, "VibrantSwatch_Body: " + Integer.toHexString(swatch.getBodyTextColor()));
            Log.d(TAG, "VibrantSwatch_Rgb: " + Integer.toHexString(swatch.getRgb()));
        }
        if ((swatch = palette.getDarkMutedSwatch()) != null) {
            Log.d(TAG, "DarkMutedSwatch_Title: " + Integer.toHexString(swatch.getTitleTextColor()));
            Log.d(TAG, "DarkMutedSwatch_Body: " + Integer.toHexString(swatch.getBodyTextColor()));
            Log.d(TAG, "DarkMutedSwatch_Rgb: " + Integer.toHexString(swatch.getRgb()));
        }
        if ((swatch = palette.getDarkVibrantSwatch()) != null) {
            Log.d(TAG, "DarkVibrantSwatch_Title: " + Integer.toHexString(swatch.getTitleTextColor()));
            Log.d(TAG, "DarkVibrantSwatch_Body: " + Integer.toHexString(swatch.getBodyTextColor()));
            Log.d(TAG, "DarkVibrantSwatch_Rgb: " + Integer.toHexString(swatch.getRgb()));
        }
        if ((swatch = palette.getLightMutedSwatch()) != null) {
            Log.d(TAG, "LightMutedSwatch_Title: " + Integer.toHexString(swatch.getTitleTextColor()));
            Log.d(TAG, "LightMutedSwatch_Body: " + Integer.toHexString(swatch.getBodyTextColor()));
            Log.d(TAG, "LightMutedSwatch_Rgb: " + Integer.toHexString(swatch.getRgb()));
        }
        if ((swatch = palette.getLightVibrantSwatch()) != null) {
            Log.d(TAG, "LightVibrantSwatch_Title: " + Integer.toHexString(swatch.getTitleTextColor()));
            Log.d(TAG, "LightVibrantSwatch_Body: " + Integer.toHexString(swatch.getBodyTextColor()));
            Log.d(TAG, "LightVibrantSwatch_Rgb: " + Integer.toHexString(swatch.getRgb()));
        }

        binding.button.setTextColor(palette.getDominantSwatch().getTitleTextColor());
        binding.button.setBackgroundColor(palette.getDominantSwatch().getRgb());
        binding.textView.setTextColor(palette.getDominantSwatch().getTitleTextColor());
        binding.cardBody.setCardBackgroundColor(ColorUtils.setAlphaComponent(palette.getDominantSwatch().getRgb(), 192));
        binding.textView2.setTextColor(palette.getDominantSwatch().getBodyTextColor());

        int color = palette.getDominantSwatch().getTitleTextColor() | 0xFF000000;
        int flags = getWindow().getDecorView().getSystemUiVisibility();

        if (color == 0xFFFFFFFF) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags ^= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    flags ^= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
        } else if (color == 0xFF000000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
        }

        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    private Drawable getRandomBackgroundDrawable() {
        @DrawableRes int drawableId = R.drawable.back_1;
        switch (new Random().nextInt(17) + 1) {
            case 1:
                drawableId = R.drawable.back_1;
                break;
            case 2:
                drawableId = R.drawable.back_2;
                break;
            case 3:
                drawableId = R.drawable.back_3;
                break;
            case 4:
                drawableId = R.drawable.back_4;
                break;
            case 5:
                drawableId = R.drawable.back_5;
                break;
            case 6:
                drawableId = R.drawable.back_6;
                break;
            case 7:
                drawableId = R.drawable.back_7;
                break;
            case 8:
                drawableId = R.drawable.back_8;
                break;
            case 9:
                drawableId = R.drawable.back_9;
                break;
            case 10:
                drawableId = R.drawable.back_10;
                break;
            case 11:
                drawableId = R.drawable.back_11;
                break;
            case 12:
                drawableId = R.drawable.back_12;
                break;
            case 13:
                drawableId = R.drawable.back_13;
                break;
            case 14:
                drawableId = R.drawable.back_14;
                break;
            case 15:
                drawableId = R.drawable.back_15;
                break;
            case 16:
                drawableId = R.drawable.back_16;
                break;
            case 17:
                drawableId = R.drawable.back_17;
        }

        return blurry(drawableId);
    }

    private Drawable blurry(@DrawableRes int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        RenderScript rs = RenderScript.create(this);
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(15f);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);

        return new BitmapDrawable(getResources(), bitmap);
    }
}
