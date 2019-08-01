package su.bnet.utils.viewlisteners;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.widget.EditText;

/**
 * Created by Timur Khakimov on 15.04.2019
 */
public class PhonePatternWatcher extends PhoneNumberFormattingTextWatcher {

    public static final int MAX_LENGTH = 11;

    private EditText editText;
    private boolean backspacingFlag = false;
    private boolean editedFlag = false;
    private int cursorComplement;
    private boolean firstInput = true;

    public PhonePatternWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        cursorComplement = s.length() - editText.getSelectionStart();
        if (count > after) {
            backspacingFlag = true;
        } else {
            backspacingFlag = false;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //
    }

    @Override
    public void afterTextChanged(Editable s) {
        String string = s.toString();
        if (string.length() >= 19) {
            string = string.substring(0, 18);
        }
        String phone = string.replaceAll("[^\\d]", "");
        if (!editedFlag && !backspacingFlag && (phone.length() > 0)) {
            editedFlag = true;
            String ans = "";
            if (phone.length() >= 9) {
                ans = "+" + phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + "-" + phone.substring(7, 9) + "-" + phone.substring(9);
            } else if (phone.length() >= 7) {
                ans = "+" + phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4, 7) + "-" + phone.substring(7);
            } else if (phone.length() >= 4) {
                ans = "+" + phone.substring(0, 1) + " (" + phone.substring(1, 4) + ") " + phone.substring(4);
            } else if (phone.length() >= 1) {
                if (phone.substring(0, 1).equals("7")) {
                    ans = "+" + phone;
                } else {
                    ans = "+7" + phone;
                }
            }
            editText.setText(ans);
            editText.setSelection(editText.getText().length() - cursorComplement);
        } else {
            editedFlag = false;
        }
    }
}
