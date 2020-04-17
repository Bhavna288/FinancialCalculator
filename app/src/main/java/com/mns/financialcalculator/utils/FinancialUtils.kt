package com.mns.financialcalculator.utils

import android.util.Log


class FinancialUtils {

    companion object{



    fun CalculateInsuranceNeeded(
        paramInt1: Int, paramInt2: Int,
        paramFloat1: Float, paramFloat2: Float, paramFloat3: Float,
        paramFloat4: Float, paramFloat5: Float, paramFloat6: Float,
        paramFloat7: Float
    ): Double {
        var paramFloat2 = paramFloat2
        if (paramFloat3 == paramFloat2) paramFloat2 += 0.001f
        val f1 = (1.0f + paramFloat3) / (1.0f + paramFloat2) - 1.0f
        Log.i("Insurance", "Rate: $f1")
        val i = paramInt2 - paramInt1
        Log.i("Insurance", "Years To Cover: $i")
        val f2 = paramFloat1 * 12.0f
        Log.i("Insurance", "Yearly Expenses: $f2")
        val d1 = PV(f1, i, -f2, 0.0, 0)
        Log.i("Insurance", "Insurance Need: $d1")
        val d2 = d1 + paramFloat4 + paramFloat5 - paramFloat6 - paramFloat7
        Log.i("Insurance", "Insurance Needed: $d2")
        return d2
    }

    fun FDMaturityValue(
        mPrincipalAmount: Double, mTenure: Int,
        mRate: Float, mCompounding: Int
    ): Double {
        var d1 = mPrincipalAmount
        val f = mRate / 100.0f / 12.0f
        var i = 0
        var d2 = 0.0
        for (j in 1..mTenure) {
            val d3 = d1 * f
            d2 += d3
            Log.i(
                "Maturity Value",
                "$j,$mPrincipalAmount,$d3,$d1"
            )
            i++
            if (i == mCompounding) {
                i = 0
                d1 += d2
                d2 = 0.0
            }
        }
        return d1 + d2
    }

    fun FV(paramFloat1: Float, paramInt: Int, paramFloat2: Float): Float {
        var f = 0.0f
        for (i in 0 until paramInt) f += paramFloat2 + f * paramFloat1
        return f
    }

    fun FutualValue(
        paramFloat1: Float, paramFloat2: Float,
        paramFloat3: Float, paramInt: Int, paramFloat4: Float
    ): Float {
        var f1 = paramFloat1
        val f2 = paramFloat4 / 100.0f
        val f3 = paramFloat3 / 100.0f
        var i = 0
        var f4 = paramFloat2
        for (j in 1..paramInt) {
            val f5 = f2 * (f1 + f4)
            f1 = f5 + (f1 + f4)
            Log.i("SIP Calculator", "$j,$f4,$f5,$f1")
            i++
            if (i == 12) {
                i = 0
                f4 += paramFloat2 * f3
            }
        }
        return f1
    }

    fun Loan_InterestRate(
        paramDouble1: Double, paramLong: Long,
        paramDouble2: Double
    ): Double {
        Log.i(
            "Loan_InterestRate", "\nLoan Amount: " + paramDouble1
                    + "\nNo Of Payments: " + paramLong + "\nMonthly Payment: "
                    + paramDouble2
        )
        val d1 =
            Math.log10(1.0 + 1.0 / paramLong) / Math.log10(2.0)
        Log.i("Loan_InterestRate", "q= $d1")
        val d2 = Math.pow(
            Math.pow(1.0 + paramDouble2 / paramDouble1, 1.0 / d1) - 1.0,
            d1
        ) - 1.0
        Log.i("Loan_InterestRate", "rate#1= $d2")
        val d3 = (d2
                - (paramDouble2 - (paramDouble2
                * Math.pow(1.0 + d2, -paramLong.toDouble())) - d2 * paramDouble1)
                / ((paramDouble2 * paramLong
                * Math.pow(1.0 + d2, -paramLong - 1L.toDouble())) - paramDouble1))
        Log.i("Loan_InterestRate", "rate#2= $d3")
        val d4 = d3 * 1200.0
        Log.i("Loan_InterestRate", "Interest Rate: $d4")
        return d4
    }

    fun Loan_LoanAmount(
        paramLong: Long, paramDouble1: Double,
        paramDouble2: Double
    ): Long {
        Log.i(
            "Loan_NoOfPayments", "No Of Payments: " + paramLong
                    + "\nInterest Rate: " + paramDouble1 + "\nMonthly Payment: "
                    + paramDouble2
        )
        val d1 = paramDouble1 / 1200.0
        val d2 = (paramDouble2 / d1
                * (1.0 - Math.pow(1.0 + d1, -paramLong.toDouble())))
        Log.i("Loan_NoOfPayments", "Loan Amount: $d2")
        return Math.round(d2)
    }

    fun Loan_NoOfPayments(
        paramDouble1: Double,
        paramDouble2: Double, paramDouble3: Double
    ): Long {
        Log.i(
            "Loan_NoOfPayments", "Loan Amount: " + paramDouble1
                    + "\nInterest Rate: " + paramDouble2 + "\nMonthly Payment: "
                    + paramDouble3
        )
        val d1 = paramDouble2 / 1200.0
        val d2 = -Math.log10(1.0 - d1 * paramDouble1 / paramDouble3)
        val d3 = Math.log10(1.0 + d1)
        val d4 = d2 / d3
        Log.i(
            "Loan_NoOfPayments", "N1: " + d2 + "\nN2: " + d3
                    + "\nNo Of Payments: " + d4
        )
        return Math.round(d4)
    }

    fun PV(
        paramFloat1: Float, paramInt1: Int,
        paramFloat2: Float, paramDouble: Double, paramInt2: Int
    ): Double {
        return (((-paramFloat2
                * (1.0f + paramFloat1 * paramInt2)
                * ((Math.pow(
            1.0f + paramFloat1.toDouble(),
            paramInt1.toDouble()
        ) - 1.0) / paramFloat1)) - paramDouble)
                / Math.pow(1.0f + paramFloat1.toDouble(), paramInt1.toDouble()))
    }

    fun PresentValue(
        paramDouble: Double, paramFloat: Float,
        paramInt: Int
    ): Double {
        return paramDouble / Math.pow(
            1.0f + paramFloat / 12.0f.toDouble(),
            paramInt * 12.toDouble()
        )
    }

    fun InvestmentValue(
        paramFloat1: Float,
        paramDouble: Double,
        paramFloat2: Float,
        paramInt: Int,
        paramFloat3: Float
    ): Float {
        var f1 = paramFloat1
        var f2 = paramFloat1
        val f3 = paramFloat3 / 100.0f
        val f4 = paramFloat2 / 100.0f
        var i = 0
        var d = paramDouble
        for (j in 1..paramInt) {
            f2 = (f2 + (d + f2 * f3)).toFloat()
            f1 = (d + f1).toFloat()
            i++
            if (i == 12) {
                i = 0
                d += paramDouble * f4
            }
        }
        // (f2 + f2 * f3);
        return f1
    }

    fun RDMaturityValue(
        paramFloat1: Float, paramInt1: Int,
        paramFloat2: Float, paramInt2: Int
    ): Double {
        var d1 = 0.0
        val f = paramFloat2 / 100.0f / 12.0f
        var i = 0
        var d2 = 0.0
        for (j in 1..paramInt1) {
            d1 += paramFloat1.toDouble()
            val d3 = d1 * f
            d2 += d3
            Log.i(
                "Maturity Value",
                "$j,$paramFloat1,$d3,$d1"
            )
            i++
            if (i == paramInt2) {
                i = 0
                d1 += d2
                d2 = 0.0
            }
        }
        return d1 + d2
    }

    fun RDMaturityValueV1(
        paramFloat1: Float, paramInt1: Int,
        paramFloat2: Float, paramInt2: Int
    ): Double {
        val f = paramFloat2 / 100.0f
        val i = paramInt1 / 3
        Log.i(
            "Maturity Value", "Monthly Investment:" + paramFloat1
                    + ",Tenure Months:" + paramInt1 + ",Interest Rate:" + f
        )
        val d1 =
            paramFloat1 * (Math.pow(1.0f + f / 4.0f.toDouble(), i.toDouble()) - 1.0)
        val d2 =
            1.0 - Math.pow(1.0f + f / 4.0f.toDouble(), -0.3333333333333333)
        Log.i("Maturity Value", "V1:$d1,V2:$d2")
        return d1 / d2
    }

    fun calcFV(
        paramFloat1: Float, paramInt: Int,
        paramFloat2: Float
    ): Float {
        var f = paramFloat1
        for (i in 0 until paramInt) f += f * paramFloat2
        return f
    }

    fun pmt(
        paramDouble1: Double, paramLong: Long,
        paramDouble2: Double, paramDouble3: Double, paramInt: Int
    ): Double {
        var d =
            paramDouble1 / (Math.pow(1.0 + paramDouble1, paramLong.toDouble()) - 1.0) *
                    -(paramDouble3 + paramDouble2 * Math.pow(
                        1.0 + paramDouble1,
                        paramLong.toDouble()
                    ))
        if (paramInt == 1) d /= 1.0 + paramDouble1
        return d
    }

    }

}
