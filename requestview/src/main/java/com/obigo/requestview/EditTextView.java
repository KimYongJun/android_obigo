package com.obigo.requestview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Grega on 23/11/15.
 */
public class EditTextView extends RelativeLayout implements ETT_EditText.OnEditTextListener, View.OnFocusChangeListener{

    private RelativeLayout rlContainer;
    private ImageView ettImageView;
    private TextView ettTextView;
    private ETT_EditText ettEditText;

    private boolean isInEditMode = false;
    private String emptyText;
    private int emptyStyle = 0;
    private int emptyColor = Color.BLACK;
    private int textColor = Color.BLACK;
    private int icon = 0;
    private int iconEmpty = 0;
    private int iconInEditMode = 0;

    private boolean selectOnFocus;
    private boolean showHint;
    private boolean locked;

    private EditTextViewListener mListener;

    public interface EditTextViewListener{
        void onEditTextViewEditModeStart();
        void onEditTextViewEditModeFinish(String text);
    }

    public EditTextView(Context context) {
        super(context);
    }

    public EditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View container = inflate(getContext(), R.layout.layout_edittextview, this);

        setSaveEnabled(true);

        ettImageView = (ImageView)findViewById(R.id.ettImageView);
        ettTextView = (TextView)findViewById(R.id.ettTextView);
        ettEditText = (ETT_EditText)findViewById(R.id.ettEditText);
        ettEditText.setOnKeyboardDismissedListener(this);
        ettEditText.setOnFocusChangeListener(this);

        rlContainer = (RelativeLayout)findViewById(R.id.ettContainer);

        Resources res = getResources();

        textColor = res.getColor(R.color.default_text_color);
        emptyColor = res.getColor(R.color.default_empty_color);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EditTextView,
                0, 0);

        try {
            emptyStyle = a.getInt(R.styleable.EditTextView_ettEmptyTexStyle, 2);
            emptyColor = a.getColor(R.styleable.EditTextView_ettEmptyColor, emptyColor);
            textColor = a.getColor(R.styleable.EditTextView_ettTextColor, textColor);
            selectOnFocus = a.getBoolean(R.styleable.EditTextView_ettSelectAllOnEditMode, true);
            showHint = a.getBoolean(R.styleable.EditTextView_ettShowHint, true);
            icon = a.getResourceId(R.styleable.EditTextView_ettIcon, 0);
            iconEmpty = a.getResourceId(R.styleable.EditTextView_ettIconEmpty, icon);
            iconInEditMode = a.getResourceId(R.styleable.EditTextView_ettIconInEditMode, icon);
            locked = a.getBoolean(R.styleable.EditTextView_ettLocked, false);

            /* Text Size */
            int textSize = a.getDimensionPixelSize(R.styleable.EditTextView_ettTextSize, getScaledPixels(16));
            if(textSize < getScaledPixels(12))
                textSize = getScaledPixels(12);

            ettTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            ettEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

            /* Input Type */
            int inputType = a.getInt(R.styleable.EditTextView_android_inputType, EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES);
            ettEditText.setInputType(inputType);

            int maxLines = a.getInt(R.styleable.EditTextView_ettMaxLines, 1);
            ettEditText.setMaxLines(maxLines);

            setEmptyText(a.getString(R.styleable.EditTextView_ettEmptyText));
            setText(a.getString(R.styleable.EditTextView_ettText));

            /* Margins */
            int marginTopBottom = a.getDimensionPixelSize(R.styleable.EditTextView_ettMarginTopBottom, getPixels(8) + (textSize - getPixels(12)));

            if(marginTopBottom < 0)
                marginTopBottom = getPixels(8) + (textSize - getPixels(12));

            int iconMarginLeft = a.getDimensionPixelSize(R.styleable.EditTextView_ettIconMarginLeft, getPixels(16));
            int iconMarginRight = a.getDimensionPixelSize(R.styleable.EditTextView_ettIconMarginRight, getPixels(32));

            setMargins(ettImageView, marginTopBottom, marginTopBottom, iconMarginLeft, iconMarginRight);

            int textMarginTopBottom = marginTopBottom - (getPixels(8) + textSize - getPixels(12))  + (int) ((textSize - getPixels(12))*0.5f);
            int textMarginLeft = a.getDimensionPixelSize(R.styleable.EditTextView_ettTextMarginLeft, getPixels(0));
            int textMarginRight = a.getDimensionPixelSize(R.styleable.EditTextView_ettTextMarginLeft, getPixels(8));

            setMargins(findViewById(R.id.rlText), textMarginTopBottom, textMarginTopBottom, textMarginLeft, textMarginRight);

        } finally {
            a.recycle();
        }

        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);

        rlContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLayout(true);
            }
        });

        checkLocked();
    }

    /**
     * Set callback listener for EditTextView changes
     * @param listener EditTextViewListener
     */
    public void setEditTextViewListener(EditTextViewListener listener){
        this.mListener= listener;
    }


    private static void setMargins(final View view, final int marginTop, final int marginBottom, final int marginLeft, final int marginRight) {
        final RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)view.getLayoutParams();
        lp.setMargins(marginLeft, marginTop == -1 ? lp.topMargin : marginTop, marginRight, marginBottom == -1 ? lp.bottomMargin : marginBottom);
        view.setLayoutParams(lp);

        // Fix for older Android version (<4.3) because of a bug in RelativeLayout margins
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2)
            view.setPadding(0, 0, 0, marginBottom == -1 ? lp.bottomMargin : marginBottom);
    }

    private int getPixels(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int getScaledPixels(float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v instanceof EditText){
            if(isInEditMode && !hasFocus)
               toggleLayout(true);
        }
    }

    private void hideKeyboard(){
        if(ettEditText != null && !isInEditMode()) {
            InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ettEditText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    private void showKeyboard(){
        if(ettEditText != null && !isInEditMode()) {
            InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(ettEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onEditTextKeyboardDismissed() {
        toggleLayout(false);
    }

    @Override
    public void onEditTextKeyboardDone() {
        toggleLayout(false);
    }

    /** SAVED INSTANCE **/

    private static class SavedState extends BaseSavedState {
        String text;
        String empty;

        boolean editmode;
        boolean select;
        boolean hint;
        boolean locked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            text = in.readString();
            empty = in.readString();

            select = in.readInt() == 1;
            hint = in.readInt() == 1;
            editmode = in.readInt() == 1;
            locked = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(text);
            out.writeString(empty);

            out.writeInt(select ? 1 : 0);
            out.writeInt(hint ? 1 : 0);
            out.writeInt(editmode ? 1 : 0);
            out.writeInt(locked ? 1 : 0);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.text = ettEditText.getText().toString();
        ss.empty = this.emptyText;

        ss.select = selectOnFocus;
        ss.hint = showHint;

        ss.editmode = isInEditMode;
        ss.locked = locked;

        if(isInEditMode) {
            InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(ettEditText.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        return ss;
    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        setText(ss.text);
        setEmptyText(ss.empty);

        selectOnFocus = ss.select;
        showHint = ss.hint;
        isInEditMode = ss.editmode;
        locked = ss.locked;

        if(isInEditMode) {
            showEditText(false);    // false - prevent notifying callback listener because state restored
        }else
            showTextView(false);    // false - prevent notifying callback listener because state restored

        checkLocked();
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray container) {
        super.dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray container) {
        super.dispatchThawSelfOnly(container);
    }

    /****/

    private void toggleLayout(boolean notifyListener){
        if(isInEditMode) {
            showTextView(notifyListener);   // TextView will be visible
        }else{
            showEditText(notifyListener);   // EditText will be visible
        }
    }

    private void checkLocked() {
        rlContainer.setEnabled(!locked);
    }

    private void showTextView(){
        showTextView(true);
    }

    private void showTextView(boolean notifyListener){
        setTextViewText(ettEditText.getText().toString());
        ettTextView.setVisibility(View.VISIBLE);
        ettEditText.setVisibility(View.INVISIBLE);

        hideKeyboard();

        isInEditMode = false;

        if(mListener != null && notifyListener) {
            mListener.onEditTextViewEditModeFinish(ettEditText.getText().toString());
        }
    }

    private void showEditText(){
        showEditText(true);
    }

    private void showEditText(boolean notifyListener){
        ettTextView.setVisibility(View.INVISIBLE);
        ettEditText.setVisibility(View.VISIBLE);

        setIcon(iconInEditMode);

        ettEditText.requestFocus();

        if(selectOnFocus)
            ettEditText.selectAll();
        else
            ettEditText.setSelection(getText().length());

        showKeyboard();

        isInEditMode = true;

        if(mListener != null && notifyListener)
            mListener.onEditTextViewEditModeStart();
    }

    /**
     * Switch back to normal mode from edit mode.
     */
    public void leaveEditMode(){
        if(isInEditMode)
            showTextView();
    }

    /**
     * Set text
     * @param text String
     */
    public void setText(String text){
        if(text == null){
            text = "";
        }

        ettEditText.setText(text);
        setTextViewText(text);
    }

    private void setTextViewText(String text){
        if(text.isEmpty()){
            ettTextView.setText(emptyText);
            ettTextView.setTypeface(null, emptyStyle);
            ettTextView.setTextColor(emptyColor);

            setIcon(iconEmpty);
        }else{
            ettTextView.setText(text);
            ettTextView.setTypeface(null, 0);
            ettTextView.setTextColor(textColor);

            setIcon(icon);
        }
    }

    /**
     * Get text
     * @return String
     */
    public String getText(){
        return ettEditText.getText().toString();
    }

    /**
     * Change text when empty or for hint (if enabled).
     * @param emptyText String
     */
    public void setEmptyText(String emptyText){
        if(emptyText == null || emptyText.isEmpty())
            emptyText = getResources().getString(R.string.default_empty_text);

        this.emptyText = emptyText;

        if(showHint) {
            ettEditText.setHint(emptyText);
            ettEditText.setHintTextColor(emptyColor);
        }else
            ettEditText.setHint("");
    }

    private void setIcon(int res){
        if(res == 0 || icon == 0)
            ettImageView.setVisibility(View.GONE);
        else
            ettImageView.setVisibility(View.VISIBLE);

        ettImageView.setImageResource(res);
    }

    /**
     * Check if EditTextView is locked.
     * @return boolean
     */
    public boolean isLocked(){
        return locked;
    }

    /**
     * Lock or unlock EditTextView.
     * @param locked boolean
     */
    public void setLocked(boolean locked){
        this.locked = locked;

        leaveEditMode();

        checkLocked();
    }

    /**
     * Check if EditTextView is in EditMode
     * @return boolean
     */
    public boolean isInEditMode(){
        return isInEditMode;
    }

}
