package com.mns.financialcalculator.model

import android.os.Parcel
import android.os.Parcelable

data class SIPPlans(val plan_name: String,val inv:String,val tenure:String,val rate:String,
                    val init_inv:String,val yr_rate:String,val start_date:String,val total_inv:String,
                    val mat_val:String)

data class LoanPlans(val plan_name: String,val loan_type:String,val start_date:String,val loan_amt:String,
                     val duration:String,val int_rate:String,val tot_paid:String,val total_int:String,
                     val emi:String)

data class FdPlans(val plan_name: String,val pri_amt:String,val tenure:String,val int_rate:String,
                   val compounding:String,val start_date:String,val total_mat:String,val total_ins:String)

 data class InsPlans(val poli_name: String,val pay_method:String,
                     val prem_date: String,val prem_amt: String,val poli_no:String)


data class ViewSIPPlanitems(var month:Long, var interest: Double,
                            var balance: Double, var year: Long, var investment: Double)


data class SWPView(val month:Long,val interest:Double,
                   val balance: Double, val year:Long, val swpamt: Double)

data class STPView(var year:Long,var month: Long,var bal_begin: Double,
                    var STPAmt:Double,var interest: Double,  var bal:Double)


data class LoanView(var month: String?, var emi:Double, var interest: Double,
                                  var principal:Double, var balance: String?, var year: Int):
    Parcelable
{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(month)
        parcel.writeDouble(emi)
        parcel.writeDouble(interest)
        parcel.writeDouble(principal)
        parcel.writeString(balance)
        parcel.writeInt(year)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoanView> {
        override fun createFromParcel(parcel: Parcel): LoanView {
            return LoanView(parcel)
        }

        override fun newArray(size: Int): Array<LoanView?> {
            return arrayOfNulls(size)
        }
    }
}

data class FdView(var month: Int,var year: Int, var interest: Double,var balance: Double):Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(month)
        parcel.writeInt(year)
        parcel.writeDouble(interest)
        parcel.writeDouble(balance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FdView> {
        override fun createFromParcel(parcel: Parcel): FdView {
            return FdView(parcel)
        }

        override fun newArray(size: Int): Array<FdView?> {
            return arrayOfNulls(size)
        }
    }
}
