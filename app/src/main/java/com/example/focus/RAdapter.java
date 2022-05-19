package com.example.focus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RAdapter extends RecyclerView.Adapter<RAdapter.ViewHolder>{
    private List<appInfo> appsList;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView img;

        public ViewHolder(View itemView){
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.a_row_text);
            img = (ImageView) itemView.findViewById(R.id.a_row_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            Context context = view.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appsList.get(pos).packageName.toString());
            context.startActivity(launchIntent);
            Toast.makeText(view.getContext(), appsList.get(pos).label.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public RAdapter(Context c){
        PackageManager pm = c.getPackageManager();
        appsList = new ArrayList<appInfo>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pm.queryIntentActivities(i,0);
        for(ResolveInfo ri:allApps){
            appInfo app = new appInfo();
            app.label = ri.loadLabel(pm);
            app.packageName = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(pm);
            appsList.add(app);
        }
    }



    @Override
    public void onBindViewHolder(RAdapter.ViewHolder viewHolder, int i) {
        String appLabel = appsList.get(i).label.toString();
        String appPackage = appsList.get(i).packageName.toString();
        Drawable appIcon = appsList.get(i).icon;

        TextView textView = viewHolder.textView;
        textView.setText(appLabel);
        ImageView imageView = viewHolder.img;
        imageView.setImageDrawable(appIcon);
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }

    @Override
    public RAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
}
