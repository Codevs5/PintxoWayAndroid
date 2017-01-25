package com.example.codevs.pintxoway;

/**
 * Created by Borja on 25/01/2017.
 */

public class LocalListCard {
    private String mainData,data2,data3;

    public LocalListCard(String mainData, String data2, String data3) {
        this.mainData = mainData;
        this.data2 = data2;
        this.data3 = data3;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String dato3) {
        this.data3 = dato3;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String dato2) {
        this.data2 = dato2;
    }

    public String getMainData() {
        return mainData;
    }

    public void setMainData(String dato1) {
        this.mainData = dato1;
    }
}
