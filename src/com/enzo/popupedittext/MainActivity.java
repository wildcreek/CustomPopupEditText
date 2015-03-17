package com.enzo.popupedittext;

import java.util.ArrayList;
import java.util.List;

 

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {
	private List<String> data;
	private EditText et_number;
	private ImageButton ib_arrow;
	private MyAdapter adapter;
	private PopupWindow pw;
	static class ViewHolder{
		TextView tv_number;
		ImageView iv_delete;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 
		et_number = (EditText) findViewById(R.id.et_number);
		ib_arrow = (ImageButton) findViewById(R.id.ib_arrow);
		ib_arrow.setOnClickListener(this);
		data = new ArrayList<String>();
		for(int i = 0;i<100;i++){
			data.add("1378700"+i);
		}
	}
	@Override
	public void onClick(View v) {//设置PopupWindow
		ListView lv = new ListView(this);
		lv.setOnItemClickListener(this);
		lv.setDividerHeight(0);
		lv.setAdapter(adapter);
		adapter = new MyAdapter();
		pw = new PopupWindow(lv, et_number.getWidth(), 200);
		pw.setFocusable(true);
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.showAsDropDown(et_number);
	}

	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if(convertView != null){
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}else{
				view = View.inflate(getApplicationContext(), R.layout.item, null);
				holder = new ViewHolder();
				holder.tv_number = (TextView) view.findViewById(R.id.tv_number);
				holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
				view.setTag(holder);
			}
			String number = data.get(position);
			holder.tv_number.setText(number);
			holder.iv_delete.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//删除该条目（其实就是把数据删除）
					data.remove(position);//扩展：把数据库  文件  清除    
					adapter.notifyDataSetChanged();
				}
			});
			
			return view;
		}
		
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String number = (String) adapter.getItem(position);
		et_number.setText(number);
		et_number.setSelection(number.length());
		pw.dismiss();
	}

}
