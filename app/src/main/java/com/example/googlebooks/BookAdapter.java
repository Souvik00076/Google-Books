package com.example.googlebooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
//Adapter class for every book
public class BookAdapter extends ArrayAdapter<Book> {
    private List<Book> list;
    private ImageView coverImage;
    private TextView titleId;
    private TextView authorId;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
        initViews(convertView);
        Bitmap bitmap=list.get(position).getBitmapID();
        if(bitmap!=null)
        coverImage.setImageBitmap(list.get(position).getBitmapID());
        else
            coverImage.setVisibility(View.INVISIBLE);
        titleId.setText(list.get(position).getTitle());
        authorId.setText(list.get(position).getAuthors().get(0));
        return convertView;
    }

    private void initViews(View convertView) {
        coverImage = convertView.findViewById(R.id.small_image_id);
        titleId =  convertView.findViewById(R.id.title_id);
        authorId =  convertView.findViewById(R.id.author_id);
    }


}
