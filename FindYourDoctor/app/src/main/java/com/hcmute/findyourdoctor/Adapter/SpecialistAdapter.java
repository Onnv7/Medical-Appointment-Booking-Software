//package com.hcmute.findyourdoctor.Adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//public class SpecialistAdapter extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
//
//    ArrayList<CategoryDomain> categoryDomains;
//
//    public CategoryAdaptor(ArrayList<CategoryDomain> categoryDomains) {
//        this.categoryDomains = categoryDomains;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
//        return new ViewHolder(inflate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.categoryName.setText((categoryDomains.get(position).getTitle()));
//        String picUrl = "";
//
//        switch (position) {
//            case 0: {
//                picUrl = "cat_1";
//                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.cat_background1));
//                break;
//            }
//        }
//        int drawableResourseId = holder.itemView.getContext().getResources().getIdentifier(categoryDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
//        Glide.with(holder.itemView.getContext())
//                .load(drawableResourseId)
//                .into(holder.categoryPic);
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryDomains.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder{
//        TextView categoryName;
//        ImageView categoryPic;
//        ConstraintLayout mainLayout;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            categoryName = itemView.findViewById(R.id.categoryName);
//            categoryPic = itemView.findViewById(R.id.categoryPic);
//            mainLayout = itemView.findViewById(R.id.mainLayout);
//        }
//    }
//}
