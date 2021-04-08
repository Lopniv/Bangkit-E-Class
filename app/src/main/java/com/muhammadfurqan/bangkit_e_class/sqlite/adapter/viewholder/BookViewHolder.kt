package com.muhammadfurqan.bangkit_e_class.sqlite.adapter.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkit_e_class.R
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel
import com.muhammadfurqan.bangkit_e_class.sqlite.adapter.BookAdapter

class BookViewHolder(view: View, val listener: BookAdapter.Listener) :
        RecyclerView.ViewHolder(view) {

    private var btnRemove: AppCompatImageView
    private var tvName: AppCompatTextView

    init {
        with(itemView) {
            btnRemove = findViewById(R.id.btn_remove)
            tvName = findViewById(R.id.tv_book)

        }
    }

    fun bind(element: BookModel) {
        tvName.text = element.name

        btnRemove.setOnClickListener {
            listener.onRemove(element)
        }

        itemView.setOnClickListener {
            listener.onClick(element)
        }
    }

    companion object {
        val LAYOUT = R.layout.item_book
    }
}