package com.khushanshu.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null

    //flags to check what is last input
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false


    //private var btnOne:Button?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tvInput=findViewById<TextView>(R.id.tvInput)  here <TextView> not needed it is explicit type declaration
        tvInput = findViewById(R.id.tvInput)

        //in convention onClickListener is used but we use onClick to avoid writing same thing for all 13 buttons one as shown below
        /*btnOne=findViewById(R.id.btnOne)
        btnOne?.setOnClickListener {
            tvInput?.append("1")
        }
        now create for btn2 3 and all buttons */
    }


    fun onDigit(view: View) {
        //view of View type is the ui element on which we applied onDigit
        //here button

        //tvInput?.append("1")
        //accessing text of view cause that whats we need (as Button as view here is button)
        tvInput?.append((view as Button).text)
        lastNumeric = true
        //lastDot=false //allows to have 0.0.002. kind of number i.e if lastDot=false written here use ur back of copy approach to write dot/decimal function if removed so 1.264*5555 kind of number i.e no decimal in second operand
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimal(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {

        //if text of textview is not null and if last entered is numeric and no operator is added then append the operator of button pressed
        //it returns the not null content of tvInput.text in charSequence
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                lastNumeric = false;
                lastDot = false;
            }
        }
    }


    //below function performs calculation when equal button is pressed
    //it splits the string in textview around the operator used then performs specified operation on the operands
    fun onEqual(view: View) {

        //checking if last value is numeric or not if yes then store it as string and  perform calculations
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            //as calculation part can throw error so using try block
            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)                                            //if tvValue=-99 it converts them into to 99
                }


                when {
                    tvValue.contains("-") -> {

                        val splitValues = tvValue.split("-")                                         //splits the tvValue and store operands in splitValues list<String>

                        var one = splitValues[0]                                                             //first operand
                        var two = splitValues[1]                                                            //second Operand

                        if (prefix.isNotEmpty()) {
                            one = prefix + one                                                             // concadinating the "-" prefix
                        }

                        tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())        //parsing output to string and showing then removing .0
                    }

                    tvValue.contains("+") -> {

                        val splitValues = tvValue.split("+")                                         //splits the tvValue and store operands in splitValues list<String>

                        var one = splitValues[0]                                                             //first operand
                        var two = splitValues[1]                                                            //second Operand

                        if (prefix.isNotEmpty()) {
                            one = prefix + one                                                             // concadinating the "-" prefix
                        }

                        tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())        //parsing output to string and showing then removing .0
                    }

                    tvValue.contains("*") -> {

                        val splitValues = tvValue.split("*")                                         //splits the tvValue and store operands in splitValues list<String>

                        var one = splitValues[0]                                                             //first operand
                        var two = splitValues[1]                                                            //second Operand

                        if (prefix.isNotEmpty()) {
                            one = prefix + one                                                             // concadinating the "-" prefix
                        }

                        tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())        //parsing output to string and showing then removing .0
                    }

                    tvValue.contains("/") -> {

                        val splitValues = tvValue.split("/")                                         //splits the tvValue and store operands in splitValues list<String>

                        var one = splitValues[0]                                                             //first operand
                        var two = splitValues[1]                                                            //second Operand

                        if (prefix.isNotEmpty()) {
                            one = prefix + one                                                             // concadinating the "-" prefix
                        }

                        tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())        //parsing output to string and showing then removing .0
                    }
                }

                //we can also use if else if ladder see video lecture


            } catch (e: ArithmeticException) {
                e.printStackTrace()                                                                 //prints something in logcat
            }


        }
    }


    //below function checks whether an operator is added to string or not and it ignores first minus sign so that we can work with negative integers
    //return true if operator is added and false in case of first minus and other cases so that only one operator can be added in one string
    //DRAWBACK 2 :WE CANNOT MAKE SECOND OPERAND(NUMBER) NEGATIVE ONLY FIRST CAN SO CANNOT OPERATE ON FIRST POSITIVE AND SECOND NEGATIVES
    fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            //startsWith function description hover mouse
            return false
        } else {
            (value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/"))
        }
    }


    //below function removes ed zero from double result i.e 99.0=>99
    fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value;
    }
}

