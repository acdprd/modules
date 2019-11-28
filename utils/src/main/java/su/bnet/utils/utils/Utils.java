package su.bnet.utils.utils;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.FontRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import su.bnet.utils.helper.CustomTypefaceSpan;

/**
 * Created by Timur Khakimov on 15.04.2019
 * Статические вспомогательные методы-утилиты
 */
public class Utils {

    public static String onlyDigits(String s) {
        return s.replaceAll("[^\\d]", "");
    }

    public static boolean isEmailValid(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isPhoneValid(CharSequence inputText) {
        if (inputText.length() == 0) {
            return false;
        }
        String phone = inputText.toString().replaceAll("[^\\d]", "");
        if (phone.length() == 11) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param tv          - tv
     * @param start       - start pos
     * @param end         - end pos
     * @param color       - colorRes
     * @param toUpperCase - toUpperCase
     */
    public static TextView recolor(TextView tv, int start, int end, @ColorRes int color, boolean toUpperCase) {
        SpannableStringBuilder ssb;
        if (toUpperCase) ssb = new SpannableStringBuilder(tv.getText().toString().toUpperCase());
        else ssb = new SpannableStringBuilder(tv.getText());
        ssb.setSpan(new ForegroundColorSpan(tv.getResources().getColor(color)), start, end, 0);
        tv.setText(ssb, TextView.BufferType.SPANNABLE);
        return tv;
    }

    public static TextView resize(TextView tv, int start, int end, @DimenRes int size) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(tv.getText());
        ssb.setSpan(new AbsoluteSizeSpan(tv.getContext().getResources().getDimensionPixelSize(size)), start, end, 0);
        tv.setText(ssb, TextView.BufferType.SPANNABLE);
        return tv;
    }

    public static TextView refont(TextView tv, int start, int end, String fontFamily) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(tv.getText());
        ssb.setSpan(new TypefaceSpan(fontFamily), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return tv;
    }

    public static TextView refontCustom(TextView tv, int start, int end, @FontRes int fontRes) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(tv.getText());
        Typeface typeface = ResourcesCompat.getFont(tv.getContext(), fontRes);
        if (typeface != null) {
            ssb.setSpan(new CustomTypefaceSpan(typeface), start, end, 0);
            tv.setText(ssb, TextView.BufferType.SPANNABLE);
        } else {
            Log.w("TypeFaceSpan", "typeface==null");
        }

        return tv;
    }

    public static TextView setUnderLineWithListener(TextView tv, int start, int end, @NonNull final Function0<Unit> moveListener) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(tv.getText());
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                moveListener.invoke();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, start, end, 0);
        tv.setMovementMethod(new LinkMovementMethod());
        tv.setText(ssb, TextView.BufferType.SPANNABLE);
        return tv;
    }

    /**
     * @param s - "январь 2018"
     * @return "Январь 2018"
     */
    public static String firstCharToUpper(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("null or empty string");
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(s.charAt(0)).toUpperCase(new Locale("ru"))).append(s.substring(1, s.length()));
        return sb.toString();
    }

    /**
     * @param email - email
     * @return - intent for send email
     */
    public static Intent getIntentForEmail(String email) {
        String uri = "mailto:";
        return new Intent(Intent.ACTION_SENDTO, Uri.parse(uri + email));
    }

    /**
     * @param phoneNumber 79991112233 или +79991112233
     * @return intent for dial
     */
    public static Intent getIntentForPhoneDial(String phoneNumber) {
        String plus = "+";
        String uri = "tel:";
        String formatted;
        if (!phoneNumber.startsWith(plus)) {
            formatted = uri + (plus + phoneNumber);
        } else {
            formatted = uri + phoneNumber;
        }
        return new Intent(Intent.ACTION_DIAL, Uri.parse(formatted));
    }

    public static Intent getIntentForRoute(double x, double y) {
        return new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + x + "," + y));
    }

    public static Intent getIntentForExternalLink(@NonNull String link) {
        return Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(link)), null);
    }

    public static Intent getIntentToTurnGpsOn() {
        return new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    }

    /**
     * @param phoneNumber "79991112233"
     * @return "+7 999 111 22 33"
     */
    public static String formatPhoneNumber(String phoneNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("+").append(phoneNumber.substring(0, 1)).append(" ")
                .append(phoneNumber.substring(1, 4)).append(" ")
                .append(phoneNumber.substring(4, 7))
                .append(" ").append(phoneNumber.substring(7, 9))
                .append(" ").append(phoneNumber.substring(9));
        return sb.toString();
    }

    /**
     * @param phoneNumber "79991112233"
     * @return "+7 (999) 111-22-33"
     */
    public static String formatPhoneNumberWithRoundBrackets(String phoneNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("+").append(phoneNumber.substring(0, 1))
                .append(" (").append(phoneNumber.substring(1, 4)).append(") ")
                .append(phoneNumber.substring(4, 7))
                .append("-").append(phoneNumber.substring(7, 9))
                .append("-").append(phoneNumber.substring(9));
        return sb.toString();
    }

    /**
     * делает из ет мультилайн с кнопкой готово
     * в хмл установить inputType="textMultiLine"
     *
     * @param et
     */
    public static void fixMultilineActionDone(EditText et) {
        et.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    public static void findPhraseIndexesInPhrase(String what, String where, Function1<Pair<Integer, Integer>, Unit> res) {
        int start = -1;
        int end = -1;
        start = where.indexOf(what);
        if (start != -1) {
            end = start + what.length();
            res.invoke(new Pair<>(start, end));
        } else res.invoke(null);
    }
}
