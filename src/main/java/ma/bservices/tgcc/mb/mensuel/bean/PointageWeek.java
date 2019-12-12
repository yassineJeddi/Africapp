/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel.bean;

/**
 *
 * @author zakaria.dem
 */
public class PointageWeek {
    
    //attributes
    
    private int week_number;
    private int year;
    
    
    //constructors
    
    public PointageWeek(int week_number, int year) {
        this.week_number = week_number;
        this.year = year;
    }
    
    public PointageWeek() {}

    
    //getters and setters
    
    public int getWeek_number() {
        return week_number;
    }

    public void setWeek_number(int week_number) {
        this.week_number = week_number;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    
    
    
    
}
