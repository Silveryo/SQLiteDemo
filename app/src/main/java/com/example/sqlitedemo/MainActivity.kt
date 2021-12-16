package com.example.sqlitedemo

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase /* vrátí instanci SQLite databáze zatímco objekt databáze SQLite je vytvořený */
        var rs = db.rawQuery("SELECT * FROM USERS", null) /* rawQuery - Runs the provided SQL and returns a Cursor over the result set. */

        if(rs.moveToNext())
            Toast.makeText(applicationContext,rs.getString(1), Toast.LENGTH_LONG).show()

        btnVlozit.setOnClickListener {
            var cv = ContentValues()
            cv.put("UNAME",etName.text.toString())
            cv.put("PWD",etPass.text.toString())
            db.insert("USERS",null, cv)

            etName.setText("")                  /* blank - prázdný */
            etPass.setText("")

            etName.requestFocus()
        }

        btnRead.setOnClickListener {
            var content = ""
            if (rs.moveToFirst()) {
                do {
                    content = content + " " + rs.getString(1)
                    content = content + " " + rs.getString(2)
                } while (rs.moveToNext())
            }
            outputField.text = content
        }
    }
}
