package lf.lookingfor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private ArrayList<UserGroup> groups;

    GroupAdapter(ArrayList<UserGroup> groups){
        this.groups = groups;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView itemCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);
            itemCategory =
                    (TextView)itemView.findViewById(R.id.item_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("group", groups.get(position));

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    ViewGroupsFragment myFragment = new ViewGroupsFragment();
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, myFragment).addToBackStack(null).commit();

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(groups.get(i).getGroupName());
        viewHolder.itemDetail.setText(groups.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public void updateArrayList(ArrayList<UserGroup> newList){
        groups = new ArrayList<UserGroup>();
        groups.addAll(newList);
        notifyDataSetChanged();
    }
}
