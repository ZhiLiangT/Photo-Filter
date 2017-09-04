package com.tianzl.photofilter.dialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianzl.photofilter.R;
import com.tianzl.photofilter.adapter.DialogFolderAdapter;
import com.tianzl.photofilter.been.FolderChooserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 文件夹选择dialog
 * Created by tianzl on 2017/8/31.
 */

public class FoldersDialogFragment extends DialogFragment {

    private TextView tvFilePath;
    private RecyclerView rvContent;
    private ImageButton ibBack,ibEnsure;
    private TextView tvTitle;
    private LinearLayout loading_view;
    private DialogFolderAdapter adapter;
    private ExecutorService singleThreadExecutor;
    private String mInitialPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private File parentFolder;;
    private List<FolderChooserInfo> parentContents;
    private List<FolderChooserInfo> mData;
    private boolean canGoUp = true;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    tvFilePath.setText(parentFolder.getAbsolutePath());
                    mData.clear();
                    mData.addAll(getContentsArray());
                    adapter.notifyDataSetChanged();
                    loading_view.setVisibility(View.GONE);
                    rvContent.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view=inflater.inflate(R.layout.dialog_folders,container,false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    /**
     * 事件监听
     */
    private void initEvent() {
        //确定事件
        ibEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path=parentFolder.getAbsolutePath();
                callBack.callBack(path);
               dismiss();
            }
        });
        //返回事件
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //item点击事件
        adapter.setOnFolderItemClickListener(new DialogFolderAdapter.OnFolderItemClickListener() {

            @Override
            public void onFolderItemClick(View view, int position, FolderChooserInfo info) {
                onSelection(view, position, info);
            }
        });
    }
    public interface PathCallBack{
        void callBack(String path);
    }
    private PathCallBack callBack;
    public void setCallBack(PathCallBack callBack){
        this.callBack=callBack;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mData=new ArrayList<>();
        //创建线程池
        singleThreadExecutor= Executors.newSingleThreadExecutor();
        parentFolder = new File(mInitialPath);
        tvTitle.setText("请选择文件夹");
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        rvContent.setLayoutManager(manager);
        adapter=new DialogFolderAdapter(getActivity(),mData);
        rvContent.setAdapter(adapter);
        tvFilePath.setText(parentFolder.getAbsolutePath());
        setData();
    }
    /**
     * 获取数据
     */
    private void setData() {
        singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                parentContents = listFiles();
                handler.sendEmptyMessage(0);
            }
        });
    }
    /**
     * 初始化View
     * @param view
     */
    private void initView(View view) {
        tvFilePath=view.findViewById(R.id.dialog_folder_current_path);
        rvContent=view.findViewById(R.id.dialog_folder_rv);
        tvTitle=view.findViewById(R.id.bar_title_title);
        ibBack=view.findViewById(R.id.bar_title_left);
        ibEnsure=view.findViewById(R.id.bar_title_right);
        loading_view=view.findViewById(R.id.loading_view);
    }

    private List<FolderChooserInfo> listFiles() {
        File[] contents = parentFolder.listFiles();
        List<FolderChooserInfo> results = new ArrayList<>();
        if (contents != null) {
            for (File fi : contents) {
                if (fi.isDirectory()){
                    FolderChooserInfo info = new FolderChooserInfo();
                    info.setName(fi.getName());
                    info.setFile(fi);
                    info.setImage(fileType(fi));
                    results.add(info);
                }
            }
            Collections.sort(results, new FolderSorter());
            return results;
        }
        return null;
    }
    public void onSelection( View view, int position, FolderChooserInfo info) {
        if (canGoUp && position == 0) {
            if (parentFolder.isFile()) {
                parentFolder = parentFolder.getParentFile();
            }
            parentFolder = parentFolder.getParentFile();
            if (parentFolder.getAbsolutePath().equals("/storage/emulated"))
                parentFolder = parentFolder.getParentFile();
            canGoUp = parentFolder.getParent() != null;
        } else {
            parentFolder = info.getFile();
            canGoUp = true;
            if (parentFolder.getAbsolutePath().equals("/storage/emulated"))
                parentFolder = Environment.getExternalStorageDirectory();
        }
        if (parentFolder.isFile()) {

        }else{
            loading_view.setVisibility(View.VISIBLE);
            rvContent.setVisibility(View.GONE);
            setData();
        }
    }


    private static class FolderSorter implements Comparator<FolderChooserInfo> {
        @Override
        public int compare(FolderChooserInfo lhs, FolderChooserInfo rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }

    private List<FolderChooserInfo> getContentsArray() {
        List<FolderChooserInfo> results = new ArrayList<>();
        if (parentContents == null) {
            if (canGoUp){
                FolderChooserInfo info = new FolderChooserInfo();
                info.setName("...");
                info.setFile(null);
                info.setImage(R.mipmap.folders);
                results.add(info);
            }
            return results;
        }
        if (canGoUp){
            FolderChooserInfo info = new FolderChooserInfo();
            info.setName("...");
            info.setFile(null);
            info.setImage(R.mipmap.folders);
            results.add(info);
        }
        results.addAll(parentContents);
        return results;
    }

    private int fileType(File file){
        int image = R.mipmap.ic_launcher;
        if(file.isDirectory()){
            image = R.mipmap.ic_launcher;
        }else{
            try {
//            指定文件类型的图标
                String[] token = file.getName().split("\\.");
                String suffix = token[token.length - 1];
                if (suffix.equalsIgnoreCase("txt")) {
                    image = R.mipmap.ic_launcher;
                } else if (suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("gif")) {
                    image = R.mipmap.ic_launcher;
                } else if (suffix.equalsIgnoreCase("mp3")) {
                    image = R.mipmap.ic_launcher;
                } else if (suffix.equalsIgnoreCase("mp4") || suffix.equalsIgnoreCase("rmvb") || suffix.equalsIgnoreCase("avi")) {
                    image = R.mipmap.ic_launcher;
                } else if (suffix.equalsIgnoreCase("apk")) {
                    image = R.mipmap.ic_launcher;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return image;
    }

}
