package com.muhammadfurqan.bangkit_e_class.sqlite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel
import com.muhammadfurqan.bangkit_e_class.sqlite.adapter.viewholder.BookViewHolder

class BookAdapter(
        private val dataList: ArrayList<BookModel>,
        private val listener: Listener
) : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
            BookViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(BookViewHolder.LAYOUT, parent, false),
                    listener
            )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun addData(newBook: ArrayList<BookModel>) {
        val firstPositionOfNewData = dataList.size
        dataList.addAll(newBook)
        notifyItemRangeInserted(firstPositionOfNewData, newBook.size)
    }

    interface Listener {
        fun onRemove(element: BookModel)
        fun onClick(element: BookModel)
    }

}