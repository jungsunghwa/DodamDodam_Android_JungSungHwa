package kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.List;

import kr.hs.dgsw.smartschool.dodamdodam.Model.rule.Rule;
import kr.hs.dgsw.smartschool.dodamdodam.R;
import kr.hs.dgsw.smartschool.dodamdodam.widget.recycler.holder.RuleViewHolder;

public class RuleAdapter extends RecyclerView.Adapter<RuleViewHolder> {

    private RequestManager glide;
    List<Rule> ruleList;
    Rule rule;

    public RuleAdapter(Context context, List<Rule> ruleList) {
        glide = Glide.with(context.getApplicationContext());
        this.ruleList = ruleList;
    }

    @NonNull
    @Override
    public RuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RuleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rule_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RuleViewHolder holder, int position) {
        Rule rules = ruleList.get(position);

        Log.d("TAG", "onBindViewHolder: " + rules.getContent());

        holder.binding.textIndex.setText(rule.getIdx());
        holder.binding.textContent.setText(rule.getContent());
        holder.binding.textNote.setText(rule.getRemarks());
    }

    @Override
    public int getItemCount() {
        return ruleList.size();
    }
}
