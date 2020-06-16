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
import java.util.Collection;
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
    
    // Inner class to create city objects for cityList
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


    @Override
    public ArrayList setContent() {
        ArrayList<City> cityList = new ArrayList<>();
        
        for (Appointment appointment : AppointmentList.allAppointments) {
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
    

    // Creates formatted string of report data
    @Override
    public String collectAndDisplay() {
        Collection<City> data = setContent();
        StringBuilder builder = new StringBuilder();
        String titleString = this.reportTitle;
        String breakLine = "\n";
        
        builder.append(titleString).append(breakLine).append(breakLine);
        // Lambda function allows more concise way of showing iteration over a collection
        data.forEach((city) -> {
            builder.append(city.getName()).append(" has ").append(city.getCount()).append(" associated customer appointments\n");
        });
        this.content = builder.toString();
        return this.content;
    }
    
    
}
