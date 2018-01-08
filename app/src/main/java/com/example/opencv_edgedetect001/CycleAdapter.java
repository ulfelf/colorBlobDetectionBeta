package com.example.opencv_edgedetect001;

/**
 * Created by blandfars on 2018-01-08.
 */

        import android.content.Context;
        import android.os.Parcelable;
        import android.support.v4.view.PagerAdapter;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ImageView;

        import java.util.List;

/**
 * Created by Fredrik on 12/28/2017.
 */

public class CycleAdapter extends PagerAdapter {

    List<Integer> listImages;
    Context context;
    LayoutInflater layoutInflater;

    public CycleAdapter(List<Integer> listImages, Context context)
    {
        this.listImages = listImages;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.card_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.genericImage);
        imageView.setImageResource(listImages.get(position));
        container.addView(view);
        return view;
    }
}
