package com.example.creditmanagement.Data;

import android.provider.BaseColumns;

public class ContractClass {
    public class entry implements BaseColumns {
        public static final String userTableName="UserTable";
        public static final String userId="uid";
        public static  final String userName="name";
        public static  final String userEmail="email";
        public static  final String userPoints="points";

        public static  final String transactionTableName="TransactionTable";
        public static final String transId="tid";
        public static  final String transfrom ="Ptfrom";
        public static  final String transTo="PtTo";
        public static  final String transTime="time";
        public static final String transAmt="amount";
    }

}
