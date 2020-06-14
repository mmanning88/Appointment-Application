/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Report;

import Model.Appointment;
import Model.AppointmentList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Manning
 */
public class CityAppointments extends Report implements Reportable {
    

    public CityAppointments() {
        this.reportTitle = "Number of Appointments per City, report generated " + LocalDate.now();
    }
    
    private class City {
        private final String name;
        private int count;

        public City(String name) {
            this.name = name;
            this.count = 1;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
        

    }


    
    public ArrayList setContent() {
        ArrayList<City> cityList = new ArrayList<>();
        
        for (Appointment appointment : AppointmentList.monthlyAppointments) {
            int indexToCheck = checkCity(cityList, appointment.getCustomer().getAddress().getCity());
            if (indexToCheck < 0) {
                City newCity = new City(appointment.getCustomer().getAddress().getCity());
                cityList.add(newCity);
            } else {
                incrementCityInList(cityList, indexToCheck);
            }

        }
        return cityList;
    }
    
    private int checkCity(ArrayList<City> cityList, String name) {
        for (City city : cityList) {
            if (city.getName().equals(name)) {
                return cityList.indexOf(city);
            }
        }
        return -1;
    }
    
    private void incrementCityInList(ArrayList<City> cityList, int index) {
        cityList.get(index).setCount(cityList.get(index).getCount() + 1);
    }
    

    
    @Override
    public String collectAndDisplay() {
        ArrayList<City> data = setContent();
        StringBuilder builder = new StringBuilder();
        String titleString = this.reportTitle;
        String breakLine = "\n";
        
        builder.append(titleString).append(breakLine).append(breakLine);
        for (City city : data) {
            builder.append(city.getName()).append(" has ").append(city.getCount()).append(" associated customer appointments\n");
        }
        this.content = builder.toString();
        return this.content;
    }
    
    
}
