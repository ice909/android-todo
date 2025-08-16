package io.github.ice909.todo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.github.ice909.todo.R
import io.github.ice909.todo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            showBottomSheetDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("InflateParams", "ServiceCast")
    private fun showBottomSheetDialog() {
        // 创建BottomSheetDialog
        val bottomSheetDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)

        // 加载布局
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_add_task, null)
        bottomSheetDialog.setContentView(view)
        println("edgeToEdge: "+bottomSheetDialog.edgeToEdgeEnabled)

        // 获取视图组件
        val etTaskTitle = view.findViewById<TextInputEditText>(R.id.et_task_title)
        val btnAdd = view.findViewById<MaterialButton>(R.id.btn_add)
        val btnCancel = view.findViewById<MaterialButton>(R.id.btn_cancel)

        bottomSheetDialog.setOnShowListener {
            etTaskTitle.requestFocus()

            // 显示软键盘
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etTaskTitle, InputMethodManager.SHOW_IMPLICIT)
        }

        // 设置按钮点击事件
        btnAdd.setOnClickListener {
            val title = etTaskTitle.text.toString().trim()

            if (title.isEmpty()) {
                etTaskTitle.error = "请输入任务标题"
                return@setOnClickListener
            }

            // 处理添加任务逻辑
//            handleAddTask(title, description)
            bottomSheetDialog.dismiss()
        }

        btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        // 显示BottomSheetDialog
        bottomSheetDialog.show()
    }
}