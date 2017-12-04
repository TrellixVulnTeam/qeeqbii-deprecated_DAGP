package ch.epfl.sweng.qeeqbii;

/*
 * Created by davidcleres on 31.10.17.
 * RecyclerViewAdapter overriding.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.qeeqbii.open_food.ClusterType;
import ch.epfl.sweng.qeeqbii.shopping_cart.ClusterProductList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    // opacity for products used in the activity
    private static float OPACITY_NORMAL = 1.0f;
    private static float OPACITY_CHECKED = 0.5f;

    private List<Float> m_opacities;
    private ClusterProductList m_cluster_product_list;
    private final View.OnClickListener mOnClickListener;
    private LayoutInflater inflater;
    private int mLayout;

    public RecyclerViewAdapter(LayoutInflater inflater, ClusterProductList cluster_product_list,
                               int layout, View.OnClickListener oncliklistener) {
        mLayout = layout;
        this.inflater = inflater;
        m_cluster_product_list = cluster_product_list;
        m_opacities = new ArrayList<>();
        mOnClickListener = oncliklistener;
        for (int i = 0; i < m_cluster_product_list.getItems().size(); ++i)
        {
            m_opacities.add(OPACITY_NORMAL);
        }
    }

    public RecyclerViewAdapter(LayoutInflater inflater, ClusterProductList cluster_product_list, int layout)
    {
        mLayout = layout;
        this.inflater = inflater;
        m_cluster_product_list = cluster_product_list;
        m_opacities = new ArrayList<>();
        mOnClickListener = null;
        for (int i = 0; i < m_cluster_product_list.getItems().size(); ++i)
        {
            m_opacities.add(OPACITY_NORMAL);
        }
    }

    public void addItem(ClusterType cluster)
    {
        m_opacities.add(1f);
        m_cluster_product_list.addItemToList(cluster);
        notifyDataSetChanged();
    }

    public void setOpacityChecked(int position)
    {
        m_opacities.set(position, OPACITY_CHECKED);
    }

    public void changeOpacity(int position)
    {
        if(m_opacities.get(position) == OPACITY_NORMAL)
        {
            m_opacities.set(position, OPACITY_CHECKED);
        } else {
            m_opacities.set(position, OPACITY_NORMAL);
        }
    }


    public void deleteSingleItem(ClusterType cluster)
    {
        m_opacities.remove(m_cluster_product_list.getItems().indexOf(cluster));
        m_cluster_product_list.deleteSingleItem(cluster);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(mLayout, parent, false);
        if (mOnClickListener != null)
            view.setOnClickListener(mOnClickListener);

        return new ViewHolder(view);}

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        final int position = viewHolder.getAdapterPosition();
        viewHolder.textView.setText(m_cluster_product_list.getItems().get(position).toString());
        viewHolder.imageView.setImageResource(m_cluster_product_list.getItems().get(position).getImageId());
        //viewHolder.isChecked.setChecked(products.get(position).getChecked());

        final ClusterType objIncome = m_cluster_product_list.getSpecificItemInList(position);
        //in some cases, it will prevent unwanted situations
        viewHolder.isChecked.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        viewHolder.isChecked.setChecked(m_cluster_product_list.isCheckedItem(objIncome));
        viewHolder.isChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status

                m_cluster_product_list.isCheckedItem(objIncome);
                if(isChecked) {
                    m_opacities.set(position,OPACITY_CHECKED);
                }
                else {
                    m_opacities.set(position,OPACITY_NORMAL);
                }
            }
        });

        viewHolder.isChecked.setAlpha(m_opacities.get(position));
        viewHolder.textView.setAlpha(m_opacities.get(position));
        viewHolder.imageView.setAlpha(m_opacities.get(position));
    }

    @Override
    public int getItemCount() {
        return m_cluster_product_list.getItems().size();
    }

    public LayoutInflater getLayoutInflater() { return inflater; }

    public List<ClusterType> getClusters() { return m_cluster_product_list.getItems(); }

    public void clear()
    {
        m_cluster_product_list.clear();
        m_opacities.clear();

    }


    //NESTED CLASS
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private CheckBox isChecked;

        private ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            imageView = (ImageView) view.findViewById(R.id.shoppingListImage);
            isChecked = (CheckBox) view.findViewById(R.id.shoppingCheckbox);

            if (isChecked.isClickable()) {

                isChecked.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (((CheckBox) v).isChecked()) {
                            v.setAlpha(0.5f);              //CHANGES THE OPACITY OF THE VIEW
                            imageView.setAlpha(0.50f);
                            textView.setAlpha(0.50f);
                        } else {
                            v.setAlpha(1f);              //CHANGES THE OPACITY OF THE VIEW
                            imageView.setAlpha(1f);
                            textView.setAlpha(1f);
                        }
                    }
                });
            }
        }
    }
}