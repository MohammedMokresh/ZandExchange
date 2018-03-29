package com.sarafizand.sarafizand.models;

/**
 * Created by Mohammed on 29/12/2017.
 */

public class Banks {
    String swiftCode;
    String bankName;

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    @Override
    public String toString() {
        return ("1:" + this.getBankName()+
                " 2: " + this.getSwiftCode()
        );

    }
}
