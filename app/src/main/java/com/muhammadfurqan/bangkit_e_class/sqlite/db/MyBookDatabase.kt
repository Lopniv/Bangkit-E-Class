package com.muhammadfurqan.bangkit_e_class.sqlite.db

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.muhammadfurqan.bangkit_e_class.sqlite.BookModel
import com.muhammadfurqan.bangkit_e_class.sqlite.db.MyBookOpenHelper.Companion.FIELD_ID

/**
 * @author by furqan on 08/04/2021
 */
class MyBookDatabase(context: Context) {

    private val openHelper: MyBookOpenHelper by lazy {
        MyBookOpenHelper(context)
    }

    private val readableDb by lazy {
        openHelper.readableDatabase
    }

    fun addBook(name: String) {
        val writeableDb = openHelper.writableDatabase

        val values = ContentValues()
        values.put(MyBookOpenHelper.FIELD_NAME, name)

        writeableDb.insert(MyBookOpenHelper.TABLE_BOOK, null, values)
        //writeableDb.close()
    }

    fun updateBook(id: String, name: String) {
        val writeableDb = openHelper.writableDatabase

        val values = ContentValues()
        values.put(MyBookOpenHelper.FIELD_NAME, name)

        writeableDb.update(MyBookOpenHelper.TABLE_BOOK, values, "$FIELD_ID = ?", arrayOf(id))
    }

    fun deleteBook(id: String) {
        val writeableDb = openHelper.writableDatabase

        writeableDb.delete(MyBookOpenHelper.TABLE_BOOK, "$FIELD_ID = '$id'", null)
    }

    fun getAllBooks(): ArrayList<BookModel> {
        val bookList: ArrayList<BookModel> = arrayListOf()

        val cursor = readableDb.rawQuery(
            "SELECT * FROM ${MyBookOpenHelper.TABLE_BOOK}",
            null
        )

        cursor?.apply {
            while (moveToNext()) {
                val book = BookModel(
                    getInt(getColumnIndexOrThrow(MyBookOpenHelper.FIELD_ID)),
                    getString(getColumnIndexOrThrow(MyBookOpenHelper.FIELD_NAME))
                )
                bookList.add(book)
            }
        }

        return bookList
    }

}