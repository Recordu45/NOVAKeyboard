package com.nova.keyboard;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NovaKeyboardService extends InputMethodService {

    private LinearLayout root;
    private LinearLayout keysContainer;

    private boolean shift = false;
    private boolean hindi = false;
    private boolean numbers = false;

    private final String[][] EN = {
            {"Q","W","E","R","T","Y","U","I","O","P"},
            {"A","S","D","F","G","H","J","K","L"},
            {"⇧","Z","X","C","V","B","N","M","⌫"},
            {"123","☺",",","SPACE",".","EN/HI","↵"}
    };

    private final String[][] HI = {
            {"क","ख","ग","घ","च","छ","ज","झ","ट","ठ"},
            {"ड","ढ","ण","त","थ","द","ध","न","प"},
            {"फ","ब","भ","म","य","र","ल","व","⌫"},
            {"123","☺","ा","SPACE","ि","EN/HI","↵"}
    };

    private final String[][] NUM = {
            {"1","2","3","4","5","6","7","8","9","0"},
            {"@","#","₹","&","*","-","+","(",")"},
            {"%","!","?","\"","'","/",";",":","⌫"},
            {"ABC","☺",",","SPACE",".","EN/HI","↵"}
    };

    @Override
    public View onCreateInputView() {
        buildKeyboard();
        return root;
    }

    private void buildKeyboard() {

        root = new LinearLayout(this);

        root.setOrientation(LinearLayout.VERTICAL);

        root.setPadding(
                dp(7),
                dp(7),
                dp(7),
                dp(7)
        );

        root.setBackgroundColor(Color.rgb(7, 7, 11));

        LinearLayout suggestions = new LinearLayout(this);

        suggestions.setGravity(Gravity.CENTER_VERTICAL);

        suggestions.setPadding(
                dp(8),
                0,
                dp(8),
                dp(5)
        );

        TextView brand = createText(
                "NOVA",
                15,
                0xFF22D3EE
        );

        suggestions.addView(
                brand,
                weight(1)
        );

        TextView s1 = createText(
                "Hello",
                14,
                0xFFEDEDF7
        );

        suggestions.addView(
                s1,
                weight(1)
        );

        TextView s2 = createText(
                "Hi",
                14,
                0xFFEDEDF7
        );

        suggestions.addView(
                s2,
                weight(1)
        );

        TextView s3 = createText(
                "How",
                14,
                0xFFEDEDF7
        );

        suggestions.addView(
                s3,
                weight(1)
        );

        root.addView(
                suggestions,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(38)
                )
        );

        keysContainer = new LinearLayout(this);

        keysContainer.setOrientation(
                LinearLayout.VERTICAL
        );

        root.addView(
                keysContainer,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        String[][] layout;

        if (numbers) {
            layout = NUM;
        } else if (hindi) {
            layout = HI;
        } else {
            layout = EN;
        }

        for (String[] row : layout) {

            LinearLayout rowView =
                    new LinearLayout(this);

            rowView.setGravity(
                    Gravity.CENTER
            );

            rowView.setPadding(
                    0,
                    dp(2),
                    0,
                    dp(2)
            );

            for (String key : row) {

                Button button =
                        createKey(key);

                rowView.addView(
                        button,
                        keyParams(key)
                );
            }

            keysContainer.addView(
                    rowView,
                    new LinearLayout.LayoutParams(
                            -1,
                            0,
                            1
                    )
            );
        }
    }

    private Button createKey(String key) {

        Button button = new Button(this);

        if (key.equals("SPACE")) {
            button.setText("NOVA");
        } else {
            button.setText(key);
        }

        if (key.equals("SPACE")) {
            button.setTextSize(12);
        } else {
            button.setTextSize(18);
        }

        button.setTextColor(Color.WHITE);

        button.setAllCaps(false);

        button.setPadding(
                0,
                0,
                0,
                0
        );

        button.setMinHeight(0);
        button.setMinWidth(0);

        int backgroundColor;

        if (key.equals("↵")) {

            backgroundColor = 0xFF4F46E5;

        } else if (
                key.equals("⌫") ||
                key.equals("⇧")
        ) {

            backgroundColor = 0xFF2B203A;

        } else if (key.equals("SPACE")) {

            backgroundColor = 0xFF191927;

        } else {

            backgroundColor = 0xFF15151F;
        }

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(
                backgroundColor
        );

        drawable.setCornerRadius(
                dp(10)
        );

        drawable.setStroke(
                dp(1),
                0xFF39396A
        );

        button.setBackground(
                drawable
        );

        button.setOnClickListener(
                view -> {

                    view.performHapticFeedback(
                            HapticFeedbackConstants.KEYBOARD_TAP
                    );

                    handleKey(key);
                }
        );

        return button;
    }

    private LinearLayout.LayoutParams keyParams(
            String key
    ) {

        float weight;

        if (key.equals("SPACE")) {

            weight = 3.5f;

        } else if (
                key.equals("⌫") ||
                key.equals("⇧") ||
                key.equals("123") ||
                key.equals("ABC") ||
                key.equals("↵")
        ) {

            weight = 1.35f;

        } else {

            weight = 1f;
        }

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        -1,
                        weight
                );

        params.setMargins(
                dp(2),
                dp(2),
                dp(2),
                dp(2)
        );

        return params;
    }

    private void handleKey(String key) {

        InputConnection inputConnection =
                getCurrentInputConnection();

        if (inputConnection == null) {
            return;
        }

        if (key.equals("⌫")) {

            inputConnection.deleteSurroundingText(
                    1,
                    0
            );

        } else if (key.equals("↵")) {

            EditorInfo info =
                    getCurrentInputEditorInfo();

            int action =
                    info != null
                            ? info.imeOptions &
                            EditorInfo.IME_MASK_ACTION
                            : EditorInfo.IME_ACTION_NONE;

            if (
                    action != EditorInfo.IME_ACTION_NONE &&
                    action != EditorInfo.IME_ACTION_UNSPECIFIED
            ) {

                inputConnection.performEditorAction(
                        action
                );

            } else {

                inputConnection.sendKeyEvent(
                        new KeyEvent(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_ENTER
                        )
                );

                inputConnection.sendKeyEvent(
                        new KeyEvent(
                                KeyEvent.ACTION_UP,
                                KeyEvent.KEYCODE_ENTER
                        )
                );
            }

        } else if (key.equals("SPACE")) {

            inputConnection.commitText(
                    " ",
                    1
            );

        } else if (key.equals("⇧")) {

            shift = !shift;

            buildKeyboard();

        } else if (key.equals("123")) {

            numbers = true;

            buildKeyboard();

        } else if (key.equals("ABC")) {

            numbers = false;

            buildKeyboard();

        } else if (key.equals("EN/HI")) {

            hindi = !hindi;

            numbers = false;

            buildKeyboard();

        } else if (key.equals("☺")) {

            inputConnection.commitText(
                    "😊",
                    1
            );

        } else {

            String output = key;

            if (!hindi && shift) {
                output = key.toUpperCase();
            }

            inputConnection.commitText(
                    output,
                    1
            );

            if (shift) {

                shift = false;

                buildKeyboard();
            }
        }
    }

    private TextView createText(
            String value,
            float size,
            int color
    ) {

        TextView textView =
                new TextView(this);

        textView.setText(value);

        textView.setTextSize(size);

        textView.setTextColor(color);

        textView.setGravity(
                Gravity.CENTER
        );

        return textView;
    }

    private LinearLayout.LayoutParams weight(
            float value
    ) {

        return new LinearLayout.LayoutParams(
                0,
                -1,
                value
        );
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
}
