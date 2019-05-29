package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFounds;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.LostFoundActivity;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LostFoundViewHolder;

public class LostFoundAdapter extends RecyclerView.Adapter<LostFoundViewHolder> {
    List<LostFound> lostFounds;
    Context context;

    public LostFoundAdapter(Context context, List<LostFound> lostFounds) {
        this.context = context;
        this.lostFounds = lostFounds;
    }

    @NonNull
    @Override
    public LostFoundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LostFoundViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lostfound_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LostFoundViewHolder holder, int position) {
        Log.d("LogPosition", "position = " + position);
        Log.d("LogLostFound", "lostfound = "+lostFounds.get(position));

        LostFound lostFound = lostFounds.get(position);

        holder.binding.lostfoundTitle.setText(lostFound.getTitle());
        holder.binding.lostfoundName.setText(lostFound.getMemberId());
        holder.binding.lostfoundUploadTime.setText(lostFound.getUpload_time());


//        Drawable drawable = LoadImageFromWebOperations(lostFound.getPicture().get(position).getUrl());
//
//        Log.d("url", lostFound.getPicture().get(position).getUrl());
//
//        holder.binding.lostfoundImageview.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {
        return lostFounds.size();
    }

    private Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (Exception e) {
            Log.d("error", e.toString());
            return null;
        }
    }
}
