package workshop.akbolatss.xchangesrates.screens.snapshots;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import workshop.akbolatss.xchangesrates.R;
import workshop.akbolatss.xchangesrates.model.dao.ChartData;
import workshop.akbolatss.xchangesrates.model.dao.ChartDataCharts;
import workshop.akbolatss.xchangesrates.model.dao.ChartDataInfo;

/**
 * Author: Akbolat Sadvakassov
 * Date: 18.12.2017
 */

public class SnapshotsAdapter extends RecyclerView.Adapter<SnapshotsAdapter.SnapshotsVH> {

    private List<ChartData> mSnapshotModels;
    private onSnapshotClickListener mListener;

    public SnapshotsAdapter(onSnapshotClickListener mListener) {
        this.mSnapshotModels = new ArrayList<>();
        this.mListener = mListener;
    }

    @Override
    public SnapshotsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rv_snapshot, parent, false);
        return new SnapshotsVH(view);
    }

    @Override
    public void onBindViewHolder(SnapshotsVH holder, int position) {
        holder.bind(mSnapshotModels.get(position), mListener);
    }

    public void onAddItems(List<ChartData> modelList) {
        if (modelList != null) {
            mSnapshotModels.clear();
            mSnapshotModels.addAll(modelList);
            notifyDataSetChanged();
        }
    }

    public List<ChartData> getSnapshotModels() {
        return mSnapshotModels;
    }

    public void onUpdateSnapshot(ChartData data, int pos) {
        mSnapshotModels.set(pos, data);
        notifyItemChanged(pos);
    }

    public void onUpdateInfo(ChartDataInfo dataInfo, int pos) {
        mSnapshotModels.get(pos).setInfo(dataInfo);
        notifyItemChanged(pos);
    }

    @Override
    public int getItemCount() {
        if (mSnapshotModels == null) {
            return 0;
        }
        return mSnapshotModels.size();
    }

    public interface onSnapshotClickListener {

        public void onSnapshotClick(ChartData model, int pos);

        public void onGetChartsInfo(long key, int pos);
    }

    public class SnapshotsVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tvSnapName)
        TextView name;
        @BindView(R.id.tvCurrRate)
        TextView currRate;
        @BindView(R.id.tvHighRate)
        TextView highRate;
        @BindView(R.id.tvLowRate)
        TextView lowRate;
        @BindView(R.id.tvDate)
        TextView date;
        @BindView(R.id.tvTime)
        TextView time;
        @BindView(R.id.tvExchanger)
        TextView exchanger;
        @BindView(R.id.lineChart)
        LineChart lineChart;
        @BindView(R.id.frameLayout)
        FrameLayout frameLayout;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.imgError)
        ImageView imgError;

        public SnapshotsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            onInitChart();
        }

        public void bind(final ChartData model, final onSnapshotClickListener listener) {
            String s = model.getCoin() + "/" + model.getCurrency();
            name.setText(s);
            exchanger.setText(model.getExchange());

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frameLayout.setClickable(false);
                    progressBar.setVisibility(View.VISIBLE);
                    date.setVisibility(View.INVISIBLE);
                    time.setVisibility(View.INVISIBLE);

                    listener.onSnapshotClick(model, getAdapterPosition());
                }
            });

            if (!model.isInfoNull()) {
                bindInfo(model.getInfo());
            } else {
                listener.onGetChartsInfo(model.getId(), getAdapterPosition());
            }

            bindChart(model.getCharts());
        }

        public void bindInfo(ChartDataInfo dataInfo) {
            currRate.setText(dataInfo.getLast());
            highRate.setText(dataInfo.getHigh());
            lowRate.setText(dataInfo.getLow());
            String[] timestamp = dataInfo.getUpdated().split(" ");
            date.setText(timestamp[0]);
            time.setText(timestamp[1]);
        }

        private void onInitChart() {
            lineChart.getDescription().setEnabled(false);
            lineChart.getLegend().setEnabled(false);

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setEnabled(false);

            YAxis yAxis = lineChart.getAxisLeft();
            yAxis.setEnabled(false);

            YAxis yAxis1 = lineChart.getAxisRight();
            yAxis1.setEnabled(false);
        }

        private void bindChart(List<ChartDataCharts> chartsList) {
            rxCalculate(chartsList)
                    .delay(2000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(new DisposableSingleObserver<LineData>() {
                        @Override
                        public void onSuccess(LineData lineData) {
                            lineChart.setData(lineData);
                            lineChart.invalidate();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }

        private Single<LineData> rxCalculate(final List<ChartDataCharts> chartsList) {
            return Single.fromCallable(new Callable<LineData>() {
                @Override
                public LineData call() throws Exception {
                    List<Entry> entries = new ArrayList<>();
                    for (int i = 0; i < chartsList.size(); i++) {
                        entries.add(new BarEntry(i,
                                Float.parseFloat(chartsList.get(i).getPrice())));
                    }

                    LineDataSet set = new LineDataSet(entries, "");
                    set.setDrawCircles(false);
                    set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    set.setLineWidth(1f);
                    LineData lineData = new LineData(set);
                    lineData.setDrawValues(false);
                    lineData.setHighlightEnabled(false);
                    return lineData;
                }
            });
        }
    }
}
