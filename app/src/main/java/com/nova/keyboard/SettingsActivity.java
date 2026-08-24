package com.nova.keyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setPadding(
                dp(24),
                dp(30),
                dp(24),
                dp(24)
        );

        root.setGravity(
                Gravity.CENTER_HORIZONTAL
        );

        root.setBackgroundColor(
                0xFF07070B
        );

        TextView title = new TextView(this);

        title.setText(
                "NOVA Keyboard"
        );

        title.setTextColor(
                0xFFFFFFFF
        );

        title.setTextSize(30);

        title.setGravity(
                Gravity.CENTER
        );

        root.addView(
                title,
                layoutParams(
                        -1,
                        -2
                )
        );

        TextView subtitle = new TextView(this);

        subtitle.setText(
                "Original futuristic keyboard • v1.0"
        );

        subtitle.setTextColor(
                0xFFB8B8C8
        );

        subtitle.setTextSize(15);

        subtitle.setGravity(
                Gravity.CENTER
        );

        LinearLayout.LayoutParams subtitleParams =
                layoutParams(-1, -2);

        subtitleParams.topMargin =
                dp(8);

        root.addView(
                subtitle,
                subtitleParams
        );

        Button enableButton =
                new Button(this);

        enableButton.setText(
                "Enable NOVA Keyboard"
        );

        enableButton.setOnClickListener(
                view -> {

                    Intent intent =
                            new Intent(
                                    Settings.ACTION_INPUT_METHOD_SETTINGS
                            );

                    startActivity(intent);
                }
        );

        LinearLayout.LayoutParams enableParams =
                layoutParams(
                        -1,
                        dp(54)
                );

        enableParams.topMargin =
                dp(36);

        root.addView(
                enableButton,
                enableParams
        );

        Button chooseButton =
                new Button(this);

        chooseButton.setText(
                "Choose Default Keyboard"
        );

        chooseButton.setOnClickListener(
                view -> {

                    android.view.inputmethod.InputMethodManager imm =
                            (android.view.inputmethod.InputMethodManager)
                                    getSystemService(
                                            INPUT_METHOD_SERVICE
                                    );

                    if (imm != null) {
                        imm.showInputMethodPicker();
                    }
                }
        );

        LinearLayout.LayoutParams chooseParams =
                layoutParams(
                        -1,
                        dp(54)
                );

        chooseParams.topMargin =
                dp(14);

        root.addView(
                chooseButton,
                chooseParams
        );

        TextView info =
                new TextView(this);

        info.setText(
                "Enable NOVA Keyboard in Android Settings, " +
                "then open any text box and select NOVA " +
                "from the keyboard switcher."
        );

        info.setTextColor(
                0xFF8F8FA1
        );

        info.setTextSize(14);

        info.setGravity(
                Gravity.CENTER
        );

        LinearLayout.LayoutParams infoParams =
                layoutParams(
                        -1,
                        -2
                );

        infoParams.topMargin =
                dp(28);

        root.addView(
                info,
                infoParams
        );

        setContentView(root);
    }

    private int dp(int value) {

        return (int) (
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
                + 0.5f
        );
    }

    private LinearLayout.LayoutParams layoutParams(
            int width,
            int height
    ) {

        return new LinearLayout.LayoutParams(
                width,
                height
        );
    }
}
