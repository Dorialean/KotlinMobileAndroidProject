package ru.mirea.lukutin.mireaproject.ui.calculator

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import ru.mirea.lukutin.mireaproject.R

class Calculator : Fragment(), View.OnClickListener {

    lateinit var zeroBtn: Button
    lateinit var oneBtn: Button
    lateinit var twoBtn: Button
    lateinit var threeBtn: Button
    lateinit var fourBtn: Button
    lateinit var fiveBtn: Button
    lateinit var sixBtn: Button
    lateinit var sevenBtn: Button
    lateinit var eightBtn: Button
    lateinit var nineBtn: Button
    lateinit var plusBtn: Button
    lateinit var minusBtn: Button
    lateinit var divideBtn: Button
    lateinit var multiplyBtn: Button
    lateinit var equalsBtn: Button
    lateinit var result: TextView
    lateinit var operationsTextView: TextView
    lateinit var clearBtn : Button
    var firstOperand : Double = 0.0
    var secondOperand : Double = 0.0
    var operation = ""
    var isAddingFirstOperand = true

    companion object {
        fun newInstance() = Calculator()
    }

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        zeroBtn = view.findViewById(R.id.zeroButton)
        zeroBtn.setOnClickListener(this)
        oneBtn = view.findViewById(R.id.oneButton)
        oneBtn.setOnClickListener(this)
        twoBtn = view.findViewById(R.id.twoButton)
        twoBtn.setOnClickListener(this)
        threeBtn = view.findViewById(R.id.threeButton)
        threeBtn.setOnClickListener(this)
        fourBtn = view.findViewById(R.id.fourButton)
        fourBtn.setOnClickListener(this)
        fiveBtn = view.findViewById(R.id.fiveButton)
        fiveBtn.setOnClickListener(this)
        sixBtn = view.findViewById(R.id.sixButton)
        sixBtn.setOnClickListener(this)
        sevenBtn = view.findViewById(R.id.sevenButton)
        sevenBtn.setOnClickListener(this)
        eightBtn = view.findViewById(R.id.eightButton)
        eightBtn.setOnClickListener(this)
        nineBtn = view.findViewById(R.id.nineButton)
        nineBtn.setOnClickListener(this)
        plusBtn = view.findViewById(R.id.plusButton)
        plusBtn.setOnClickListener(this)
        minusBtn = view.findViewById(R.id.minusButton)
        minusBtn.setOnClickListener(this)
        divideBtn = view.findViewById(R.id.divideButton)
        divideBtn.setOnClickListener(this)
        multiplyBtn = view.findViewById(R.id.multiplyButton)
        multiplyBtn.setOnClickListener(this)
        equalsBtn = view.findViewById(R.id.equalsButton)
        equalsBtn.setOnClickListener(this)
        clearBtn = view.findViewById(R.id.clearButton)
        clearBtn.setOnClickListener(this)
        result = view.findViewById(R.id.resultTextView)
        operationsTextView = view.findViewById(R.id.operationsTextView)

        return view
    }

    fun addFirstOperands(view:View) {
        operationsTextView.text = when (view.id) {
            R.id.zeroButton -> operationsTextView.text.toString() + 0
            R.id.oneButton -> operationsTextView.text.toString() + 1
            R.id.twoButton -> operationsTextView.text.toString() + 2
            R.id.threeButton -> operationsTextView.text.toString() + 3
            R.id.fourButton -> operationsTextView.text.toString() + 4
            R.id.fiveButton -> operationsTextView.text.toString() + 5
            R.id.sixButton -> operationsTextView.text.toString() + 6
            R.id.sevenButton -> operationsTextView.text.toString() + 7
            R.id.eightButton -> operationsTextView.text.toString() + 8
            R.id.nineButton -> operationsTextView.text.toString() + 9
            else -> {
                isAddingFirstOperand = false
                ""
            }
        }
    }

    fun addOperation(view:View){
            if(operationsTextView.text.last().isDigit())
                operationsTextView.text = when (view.id) {
                    R.id.plusButton -> operationsTextView.text.toString() + "+"
                    R.id.minusButton -> operationsTextView.text.toString() + "-"
                    R.id.multiplyButton -> operationsTextView.text.toString() + "*"
                    R.id.divideButton -> operationsTextView.text.toString() + "%"
                    else -> {
                        ""
                    }
                }
            else{
                isAddingFirstOperand = false
                operationsTextView.text = when (view.id) {
                    R.id.plusButton -> operationsTextView.text.subSequence(0,operationsTextView.text.length - 1).toString() + "+"
                    R.id.minusButton -> operationsTextView.text.subSequence(0,operationsTextView.text.length - 1).toString() + "-"
                    R.id.multiplyButton -> operationsTextView.text.subSequence(0,operationsTextView.text.length - 1).toString() + "*"
                    R.id.divideButton -> operationsTextView.text.subSequence(0,operationsTextView.text.length - 1).toString() + "%"
                    else -> {
                        ""
                    }
            }
        }
    }
    fun equalsBtnClick(view:View){
        val firstOp: Double = operationsTextView.text.subSequence(0,operationsTextView.text.indexOfFirst { x -> x == '+'
                || x == '-' || x == '%' || x == '*' }).toString().toDouble()
        val secondOp: Double = operationsTextView.text.subSequence(operationsTextView.text.indexOfFirst { x -> x == '+'
                || x == '-' || x == '%' || x == '*' } + 1,operationsTextView.text.length).toString().toDouble()
        result.text = countResult(firstOp,secondOp).toString()
    }

    private fun countResult(firstOp:Double, secondOp:Double) : Double{
        return when(operationsTextView.text.find{x -> x == '+' || x == '-' || x == '%' || x == '*' }){
            '+' -> firstOp + secondOp
            '-' -> firstOp - secondOp
            '*' -> firstOp * secondOp
            '%' -> firstOp / secondOp
            else -> {0.0}
        }
    }

    fun addSecondOperand(view:View){
        operationsTextView.text = when (view.id) {
            R.id.zeroButton -> operationsTextView.text.toString() + 0
            R.id.oneButton -> operationsTextView.text.toString() + 1
            R.id.twoButton -> operationsTextView.text.toString() + 2
            R.id.threeButton -> operationsTextView.text.toString() + 3
            R.id.fourButton -> operationsTextView.text.toString() + 4
            R.id.fiveButton -> operationsTextView.text.toString() + 5
            R.id.sixButton -> operationsTextView.text.toString() + 6
            R.id.sevenButton -> operationsTextView.text.toString() + 7
            R.id.eightButton -> operationsTextView.text.toString() + 8
            R.id.nineButton -> operationsTextView.text.toString() + 9
            else -> {
                ""
            }
        }
    }

    fun clearAll(view:View){
        firstOperand = 0.0
        secondOperand = 0.0
        isAddingFirstOperand = true
        operationsTextView.text = ""
        result.text = ""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)
    }

    override fun onClick(p0: View?) {
       when(p0!!.id){
           R.id.zeroButton -> if(isAddingFirstOperand) addFirstOperands(p0)
               else addSecondOperand(p0)
           R.id.oneButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.twoButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.threeButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.fourButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.fiveButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.sixButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.sevenButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.eightButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.nineButton -> if(isAddingFirstOperand) addFirstOperands(p0)
           else addSecondOperand(p0)
           R.id.plusButton -> addOperation(p0)
           R.id.minusButton -> addOperation(p0)
           R.id.multiplyButton -> addOperation(p0)
           R.id.divideButton -> addOperation(p0)
           R.id.equalsButton -> equalsBtnClick(p0)
           R.id.clearButton -> clearAll(p0)
       }
    }

}