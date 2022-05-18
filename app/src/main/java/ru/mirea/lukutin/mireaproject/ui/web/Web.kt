package ru.mirea.lukutin.mireaproject.ui.web

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Browser
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import ru.mirea.lukutin.mireaproject.R

class Web : Fragment(), View.OnClickListener{

    lateinit var browser: WebView
    lateinit var qBtn : Button
    lateinit var queryEditText: EditText


    companion object {
        fun newInstance() = Web()
    }

    private lateinit var viewModel: WebViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_web, container, false)
        browser = view.findViewById(R.id.browserWebView)
        qBtn = view.findViewById(R.id.queryButton)
        qBtn.setOnClickListener(this)
        queryEditText = view.findViewById(R.id.queryPlainText)
        browser.webViewClient = MyWebViewClient()
        browser.loadUrl("http://mathprofi.ru/")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WebViewModel::class.java)
    }

    fun toValidating(s:String): String {
        var strRes:String = s
        if(queryEditText.text.isNotBlank()){
            if(!s.startsWith("https://www."))
                strRes = "https://www.$s"
            if(!(s.endsWith(".com") || s.endsWith(".ru")))
                strRes += ".com"
        }
        return strRes
    }

    override fun onClick(p0: View?) {
        browser.loadUrl(toValidating(queryEditText.text.toString()))
    }

}