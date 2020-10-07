package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

@Suppress("SpellCheckingInspection")
class MainActivity : AppCompatActivity() {

    var lastNumeric : Boolean = false //if last entered thing is numeric
    var lastDot : Boolean = false // if last entered thing is a decimal point
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) { //func when a button is clicked
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){ // func when clear button is pressed
        tvInput.text = "" //re-setting text view
        lastNumeric = false //setting last numeric & decimal as false
        lastDot = false
    }

    fun onDecimalPoint(view: View){ // function when "." button is pressed
        if (lastNumeric && !lastDot){ //if last entered thing is a number and not a decimal point
            tvInput.append(".")
            lastNumeric= false //setting it as false as now the last character is a "."
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue=tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-") // splits the values at "-"
                    var one = splitValue[0] //split value at position zero
                    var two = splitValue[1] // split value at position one
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                }else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+") // splits the values at "+"
                    var one = splitValue[0] //split value at position zero
                    var two = splitValue[1] // split value at position one
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                }else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*") // splits the values at "*"
                    var one = splitValue[0] //split value at position zero
                    var two = splitValue[1] // split value at position one
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                }else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/") // splits the values at "/"
                    var one = splitValue[0] //split value at position zero
                    var two = splitValue[1] // split value at position one
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                }

            }catch (e: ArithmeticException){
                e.printStackTrace() //Prints this throwable and its backtrace to the specified print stream
            }
        }
    }

    private fun removeZeroAfterDot(result:String): String{ // function to remove zero sfter "."
        var value = result
        if(result.contains("0"))
            value = result.substring(0,result.length - 2)
        return value
    }

    fun onOperator(view: View){
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString()) ){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value:String) : Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")|| value.contains("*")
                    ||value.contains("+")||value.contains("-")
        }
    }
}