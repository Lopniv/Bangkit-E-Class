package com.muhammadfurqan.bangkit_e_class.sqlite

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammadfurqan.bangkit_e_class.R
import com.muhammadfurqan.bangkit_e_class.list.data.DummyData
import com.muhammadfurqan.bangkit_e_class.list.presentation.adapter.NationalHeroAdapter
import com.muhammadfurqan.bangkit_e_class.sqlite.adapter.BookAdapter
import com.muhammadfurqan.bangkit_e_class.sqlite.db.MyBookDatabase
import kotlinx.coroutines.launch

class SQLiteActivity : AppCompatActivity(), BookAdapter.Listener {

    // implement recyclerview to show the book list (only name)
    // the recyclerview data must be updated every time new book added
    // item must have edit function to change the book name
    // item must have delete function to delete book

    private var id: Int? = null

    private lateinit var etBookName: AppCompatEditText
    private lateinit var btnAdd: AppCompatButton
    private lateinit var btnRead: AppCompatButton
    private lateinit var btnUpdate: AppCompatButton
    private lateinit var btnCancel: AppCompatImageView
    private lateinit var tvNotFound: AppCompatTextView
    private lateinit var rvBookList: RecyclerView
    private lateinit var adapter: BookAdapter

    private val bookDatabase: MyBookDatabase by lazy {
        MyBookDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        rvBookList = findViewById(R.id.rv_books)
        etBookName = findViewById(R.id.et_book_name)
        tvNotFound = findViewById(R.id.tv_not_found)

        btnUpdate = findViewById(R.id.btn_update)
        btnUpdate.setOnClickListener {
            onUpdate()
        }

        btnCancel = findViewById(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            onCancel()
        }

        btnAdd = findViewById(R.id.btn_add)
        btnAdd.setOnClickListener {
            onAdd()
        }

        btnRead = findViewById(R.id.btn_read)
        btnRead.setOnClickListener {
            onRead()
        }
        onRead()
    }

    private fun onCancel() {
        etBookName.text?.clear()
        btnAdd.visibility = VISIBLE
        btnUpdate.visibility = INVISIBLE
        btnCancel.visibility = INVISIBLE
    }

    private fun onUpdate() {
        val bookName = etBookName.text.toString()
        etBookName.text?.clear()

        if (bookName.isNotEmpty()) {
            Toast.makeText(this, "Success updated the book", Toast.LENGTH_LONG).show()
            btnAdd.visibility = VISIBLE
            btnUpdate.visibility = INVISIBLE
            btnCancel.visibility = INVISIBLE
            lifecycleScope.launch {
                bookDatabase.updateBook(id.toString(), bookName)
                onRead()
            }
        } else {
            Toast.makeText(this, "Please input the book name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onAdd() {
        val bookName = etBookName.text.toString()
        etBookName.text?.clear()

        if (bookName.isNotEmpty()) {
            Toast.makeText(this, "Success added the book", Toast.LENGTH_LONG).show()
            lifecycleScope.launch {
                bookDatabase.addBook(bookName)
                onRead()
            }
        } else {
            Toast.makeText(this, "Please input the book name", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onRead() {
        val bookList = bookDatabase.getAllBooks()

        if (bookList.isEmpty()) {
            rvBookList.visibility = INVISIBLE
            tvNotFound.visibility = VISIBLE
        } else {
            rvBookList.visibility = VISIBLE
            tvNotFound.visibility = INVISIBLE
            showBookList(bookList)
        }
    }

    private fun showBookList(bookList: ArrayList<BookModel>) {
        adapter = BookAdapter(bookList, this)
        rvBookList.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvBookList.adapter = adapter
    }

    override fun onRemove(element: BookModel) {
        bookDatabase.deleteBook(element.id.toString())
        Toast.makeText(this, "Success deleted the book", Toast.LENGTH_LONG).show()
        onCancel()
        onRead()
    }

    override fun onClick(element: BookModel) {
        etBookName.setText(element.name)
        id = element.id
        btnAdd.visibility = INVISIBLE
        btnUpdate.visibility = VISIBLE
        btnCancel.visibility = VISIBLE
    }
}