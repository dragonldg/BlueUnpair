package dc.com.dcblueunpair;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

import dc.com.dcblueunpair.databinding.ListItemBinding;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<BluetoothDevice> datas;
    private Context mContext;

    ListViewAdapter(Context context, ArrayList<BluetoothDevice> datas) {
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_listview_item, parent, false);
            myHolder = new ViewHolder(convertView);
            myHolder.bind(datas.get(position));
            convertView.setTag(myHolder);
            return convertView;
        } else {
            myHolder = (ViewHolder) convertView.getTag();
            myHolder.bind(datas.get(position));
            return convertView;
        }
    }

    private static class ViewHolder {
        ListItemBinding itemBinding;

        ViewHolder(View view) {
            itemBinding = DataBindingUtil.bind(view);
        }

        void bind(BluetoothDevice name) {
            itemBinding.setDevice(name);
        }
    }
}
