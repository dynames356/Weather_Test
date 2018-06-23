package com.appletesting.hp.appletesting;

public class Forecast {
    private String forecastDate,forecastTemp, forecastText;

    public Forecast(){

    }

    public Forecast(String forecastDate, String forecastTemp, String  forecastText) {
        this.forecastDate = forecastDate;
        this.forecastTemp = forecastTemp;
        this.forecastText = forecastText;
    }

    public String getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(String forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getForecastTemp() {
        return forecastTemp;
    }

    public void setForecastTemp(String forecastTemp) {
        this.forecastTemp = forecastTemp;
    }

    public String getForecastText() {
        return forecastText;
    }

    public void setForecastText(String forecastText) {
        this.forecastText = forecastText;
    }
}
