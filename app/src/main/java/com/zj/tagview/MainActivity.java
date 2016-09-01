package com.zj.tagview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;

/**
 * CreateTime:16/9/1  10:09
 * @author 郑炯
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity {
    private SeekBar seekbarTagWidth;
    private SeekBar seekbarCornerDistance;
    private SeekBar seekbarTextSize;

    private AppCompatSpinner spinnerPosition;

    private TagView tagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagView = (TagView) findViewById(R.id.tagview);

        seekbarTagWidth = (SeekBar) findViewById(R.id.seekbar_tag_width);
        seekbarCornerDistance = (SeekBar) findViewById(R.id.seekbar_corner_distance);
        seekbarTextSize = (SeekBar) findViewById(R.id.seekbar_textsize);
        spinnerPosition = (AppCompatSpinner) findViewById(R.id.spinner_position);

        seekbarTagWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tagView.setTagWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarCornerDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tagView.setCornerDistance(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbarTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tagView.setTagTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        String[] positionItems = new String[]{"left_top", "right_top", "right_bottom", "left_bottom"};
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, positionItems);

        spinnerPosition.setAdapter(spinnerAdapter);

        spinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagView.setTagPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
