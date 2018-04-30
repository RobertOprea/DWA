package com.dwa.rybridge.ryebridgedwa.ui.adapters;

import com.dwa.rybridge.ryebridgedwa.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends BaseExpandableListAdapter {

    private LayoutInflater layoutInflater;
    private List<String> groupData;
    private Map<String, List<String>> childrenList;

    public CategoriesAdapter(Context context, Map<String, List<String>> categoriesMap) {
        layoutInflater = LayoutInflater.from(context);
        groupData = new ArrayList<>(categoriesMap.keySet());
        childrenList = categoriesMap;
    }

    @Override
    public int getGroupCount() {
        return groupData != null ? groupData.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childrenList != null ? childrenList.get(groupData.get(groupPosition)).size() : 0;
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupData != null ? groupData.get(groupPosition) : "";
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return childrenList != null ? childrenList.get(groupData.get(groupPosition)).get(childPosition) : "";
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CategoryViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_category_header, parent, false);
            holder = new CategoryViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryViewHolder) convertView.getTag();
        }

        holder.categoryItem.setText(getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CategoryViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_category_header, parent, false);
            holder = new CategoryViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CategoryViewHolder) convertView.getTag();
        }

        holder.categoryItem.setText(getChild(groupPosition, childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class CategoryViewHolder {
        @BindView(R.id.category_item) TextView categoryItem;

        public CategoryViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
