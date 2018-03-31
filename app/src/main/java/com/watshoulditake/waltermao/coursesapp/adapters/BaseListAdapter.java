package com.watshoulditake.waltermao.coursesapp.adapters;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class BaseListAdapter<D extends Parcelable> extends RecyclerView.Adapter<BaseListAdapter.BaseViewHolder> {

    private List<D> mData;

    public BaseListAdapter(List<D> data) {
        mData = data;
    }

    public void setData(List<D> data) {
        mData = data;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListAdapter.BaseViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(D item);
    }

}
