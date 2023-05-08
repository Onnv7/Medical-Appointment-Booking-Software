package com.example.doctorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorapp.Model.Article;
import com.example.doctorapp.R;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<Article> articleList;
    private Context context;

    public ArticleAdapter(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_article, parent ,false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.tvTitle.setText(article.getTitle());
        Glide.with(holder.itemView)
                .load(article.getImage())
                .into(holder.ivImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (URLUtil.isValidUrl(article.getLink())) {
                    // Tạo intent để mở trình duyệt web
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "URL is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(articleList != null) {
            return articleList.size();
        }
        return 0;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvTitle;


        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image_article_card);
            tvTitle = itemView.findViewById(R.id.tv_title_article_card);
        }
    }
}
