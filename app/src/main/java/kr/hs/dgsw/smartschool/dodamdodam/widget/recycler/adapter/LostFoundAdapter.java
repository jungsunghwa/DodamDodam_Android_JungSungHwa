package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.lostfound.LostFound;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.activity.LostFoundWritingActivity;
import kr.hs.dgsw.smartschool.dodamdodam.network.request.LostFoundRequest;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LostFound.ItemViewHolder;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.LostFound.LoadingViewHolder;

public class LostFoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<LostFound> lostFounds;
    private EditText search;
    Context context;

    public LostFoundAdapter(Context context, List<LostFound> lostFounds, EditText search) {
        this.context = context;
        this.lostFounds = lostFounds;
        this.search = search;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lostfound_item, parent, false));
        } else {
            return new LoadingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lostfound_loading, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d("LogPosition", "position = " + position);
        Log.d("LogLostFound", "lostfound = " + lostFounds.get(position));

        LostFound lostFound = lostFounds.get(position);


        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position, lostFound);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return lostFounds.size();
    }

    @Override
    public int getItemViewType(int position) {
        return lostFounds.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position, LostFound lostFound) {
        viewHolder.binding.lostfoundTitle.setText(lostFound.getTitle());
        viewHolder.binding.lostfoundName.setText(lostFound.getMemberId());
        viewHolder.binding.lostfoundUploadTime.setText(lostFound.getUpload_time());
        if (lostFound.getPicture() == null) {
            Glide.with(context).load(R.drawable.ic_lost_found).into(viewHolder.binding.lostfoundImageview);
        } else {
            Log.d("TAG", lostFound.getPicture().get(0).getUrl());
            Glide.with(context).load(lostFound.getPicture().get(0).getUrl()).into(viewHolder.binding.lostfoundImageview);
        }

        viewHolder.binding.lostfoundCardView.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
            Intent intent = new Intent(context, LostFoundWritingActivity.class);
            intent.putExtra("viewType", 2); //SHOW == 2
            intent.putExtra("lostFound", new LostFoundRequest(lostFound));
            context.startActivity(intent);
            ((Activity)context).finish();
        });
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {

    }
}
