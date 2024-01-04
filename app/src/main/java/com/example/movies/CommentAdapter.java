package com.example.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    public CommentAdapter() {
    }
    public  void setData (List<Comment> list){
        this.commentList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        if(comment == null){
            return ;
        }

        String nameUser = comment.getUser();
        String contentComment = comment.getContent();
        Date timeComment = comment.getTime();

        holder.nameUserCmt.setText(nameUser);
        holder.contentCmt.setText(contentComment);
        holder.timeCmt.setText(timeComment.toString());
    }

    @Override
    public int getItemCount() {
        if(commentList != null){
            return commentList.size();
        }
        return 0;
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView contentCmt;
        private TextView timeCmt;
        private TextView nameUserCmt;
        private  LinearLayout formComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameUserCmt = itemView.findViewById(R.id.nameUserComment);
            timeCmt = itemView.findViewById(R.id.timeComment);
            contentCmt = itemView.findViewById(R.id.contentComment);
            formComment = itemView.findViewById(R.id.formComment);
        }
    }

}
