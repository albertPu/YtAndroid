package com.yt.net.entity

data class SMSMsgRequest(var payEndBankCode:String?,
                         var payMoney:String?,
                         var payTime:String?,
                         var payBankName:String?,
                         var bankRemainMoney:String?,
                         var msgContent:String?)