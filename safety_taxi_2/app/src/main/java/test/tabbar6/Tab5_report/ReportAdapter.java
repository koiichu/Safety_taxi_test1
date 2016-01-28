package test.tabbar6.Tab5_report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import test.tabbar6.R;

/**
 * Created by KMITL on 18/01/2016.
 */
public class ReportAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context; //รับ Context จาก Autocomplete
    private ArrayList<ReportInfo> listData = new ArrayList<ReportInfo>();

    public ReportAdapter(Context context, ArrayList<ReportInfo> listData){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HolderListReport holderListReport;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.report_listview, null);//ใช้ Layout ของ List เราเราสร้างขึ้นเอง
            holderListReport = new HolderListReport();//สร้างตัวเก็บส่วนประกอบของ List แต่ละอัน
            holderListReport.license_holder = (TextView) convertView.findViewById(R.id.list_license);
            holderListReport.checkbox_holder = (TextView) convertView.findViewById(R.id.list_checkbox);
            holderListReport.comment_holder = (TextView) convertView.findViewById(R.id.list_comment);
            holderListReport.rating_holder = (RatingBar) convertView.findViewById(R.id.list_ratingBar);
            holderListReport.duration_holder = (TextView) convertView.findViewById(R.id.duration);
            holderListReport.usernameReport_holder = (TextView) convertView.findViewById(R.id.username_report);

            convertView.setTag(holderListReport);
        }
        else{
            holderListReport = (HolderListReport) convertView.getTag();
        }

        //ดึงข้อมูลจาก listData มาแสดงทีละ position
        holderListReport.license_holder.setText(listData.get(position).getLicense_info());
        holderListReport.checkbox_holder.setText(listData.get(position).getCheckbox_info());
        holderListReport.comment_holder.setText(listData.get(position).getComment_info());
        holderListReport.rating_holder.setRating(listData.get(position).getRating_info());
        holderListReport.duration_holder.setText(listData.get(position).getDateTime_info());
        holderListReport.usernameReport_holder.setText("Report by : " +listData.get(position).getUsernameReport_info());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "List " + position + " click!!", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
