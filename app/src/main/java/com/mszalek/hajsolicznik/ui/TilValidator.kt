package com.mszalek.hajsolicznik.ui

import android.support.design.widget.TextInputLayout
import android.widget.EditText

/**
 * Created by marcinus on 26.12.16.
 */
class TilValidator(val til: TextInputLayout, val editText: EditText) {

    fun validate(): Boolean {
        var value: Int? = null
        try {
            value = editText.text.toString().toInt()
        } catch (e: Exception) {

        }
        if (value == null) {
            til.error = "Podałeś złą wartość"
        } else {
            til.error = null
        }
        return value != null
    }
}