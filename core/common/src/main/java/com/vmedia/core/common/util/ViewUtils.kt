package com.vmedia.core.common.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable

var TextView.diffedValue: String
    get() = text.toString()
    set(value) {
        if (text.toString() == value) return
        setText(value)
    }

var ProgressBar.diffedValue: Int
    get() = progress
    set(value) {
        if (progress == value) return
        progress = value
    }

var CheckBox.diffedValue: Boolean
    get() = isChecked
    set(value) {
        if (isChecked == value) return
        isChecked = value
    }

var SwitchCompat.diffedValue: Boolean
    get() = isChecked
    set(value) {
        if (isChecked == value) return
        isChecked = value
    }

var TextInputLayout.diffedError: String
    get() = error.toString()
    set(value) {
        if (error.toString() == value) return
        error = value
    }

var RecyclerView.diffedAdapter: RecyclerView.Adapter<*>?
    set(value) {
        if (adapter === value) return
        adapter = value
    }
    get() = adapter

/** Returns a [Sequence] over the child views in this view group. */
val View.children: Sequence<View>
    get() = object : Sequence<View> {
        override fun iterator() = (this@children as ViewGroup).iterator()
    }

fun Fragment.inflate(
    @LayoutRes resource: Int,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
): View {
    return context!!.inflate(resource, root, attachToRoot)
}

fun Context.inflate(
    @LayoutRes resource: Int,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
): View {
    val inflater = LayoutInflater.from(this)
    return inflater.inflate(resource, root, attachToRoot)
}

fun View.inflate(
    @LayoutRes resource: Int,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
): View {
    return context.inflate(resource, root, attachToRoot)
}

fun EditText.addTextChangedListener(listener: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = listener.invoke(p0.toString())
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
    })
}

fun EditText.setOnEditorActionListener(actionId: Int, action: () -> Unit) {
    setOnEditorActionListener { _, currentActionId, _ ->
        if (currentActionId == actionId) {
            action.invoke()
            return@setOnEditorActionListener true
        }

        return@setOnEditorActionListener false
    }
}

fun View.addOnFocusedListener(listener: () -> Unit) {
    setOnFocusChangeListener { editText, isFocused ->
        if (isFocused) listener.invoke()
    }
}

fun CheckBox.setOnCheckedChangeListener(listener: (Boolean) -> Unit) {
    setOnCheckedChangeListener { _, isChecked -> listener.invoke(isChecked) }
}

fun SwitchCompat.setOnCheckedChangeListener(listener: (Boolean) -> Unit) {
    setOnCheckedChangeListener { _, isChecked -> listener.invoke(isChecked) }
}

fun View.setOnClickListener(listener: () -> Unit) {
    setOnClickListener { listener.invoke() }
}

fun View.setBackgroundTint(@ColorRes colorRes: Int) {
    backgroundTintList = resources.getColorStateList(colorRes)
}

fun ImageView.setTint(@ColorRes colorRes: Int) {
    imageTintList = resources.getColorStateList(colorRes)
}

fun TextView.setTextColorCompat(@ColorRes color: Int) {
    setTextColor(context.getColorCompat(color))
}

fun Button.setBackgroundTint(@ColorRes color: Int) {
    backgroundTintList = ContextCompat.getColorStateList(context, color)
}

fun RecyclerView.init(
    adapter: RecyclerView.Adapter<*>,
    itemTouchHelper: ItemTouchHelper.Callback? = null,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
) {
    this.layoutManager = layoutManager
    this.adapter = adapter

    itemTouchHelper?.let(::addItemTouchHelper)
}

fun RecyclerView.addItemTouchHelper(callback: ItemTouchHelper.Callback) {
    ItemTouchHelper(callback).attachToRecyclerView(this)
}

fun <T> Observable<T>.distinctByValue(valueReceiver: () -> T): Observable<T> {
    return filter { it != valueReceiver.invoke() }
}