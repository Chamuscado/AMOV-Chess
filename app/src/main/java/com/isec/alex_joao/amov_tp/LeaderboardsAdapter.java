package com.isec.alex_joao.amov_tp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chamuscado on 05/01/2018.
 */

public class LeaderboardsAdapter extends BaseAdapter {

    private List<Perfil> list;
    private Context context;

    public LeaderboardsAdapter(Context c, List<Perfil> list) {
        context = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Perfil perfil = list.get(i);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_perfis, null);
        }
        TextView tvData = (TextView) view.findViewById(R.id.tvData);
        TextView tvName = (TextView) view.findViewById(R.id.tvNome);
        ImageView imageView = (ImageView) view.findViewById(R.id.imagePreview);

        tvName.setText(perfil.getStrNome());
        tvData.setText(context.getString(R.string.wins) + perfil.getWins()
                + context.getString(R.string.defeats) + perfil.getDefeats());
        utils.setPic(imageView, perfil.getImagemFundo());


        return view;
    }


}
