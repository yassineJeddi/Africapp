/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ma.bservices.tgcc.mb.mensuel.bean;

import java.io.Serializable;

/**
 *
 * @author zakaria.dem C'est l'objet qu'on va utilisé dans l'interface de
 * pointage
 */
public class PointageFront implements Serializable {

    private String value;

    private String subValue = "";

    private String subValueAb = "";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSubValue() {
        return subValue;
    }

    public void setSubValue(String subValue) {
        this.subValue = subValue;

        switch (subValue) {
            case "Client":
                this.subValueAb = "C";
                break;

            case "Fournisseur":
                this.subValueAb = "F";
                break;

            case "Prestataire":
                this.subValueAb = "P";
                break;

            case "Repas professionel":
                this.subValueAb = "RP";
                break;

            case "Autre":
                this.subValueAb = "A";
                break;

            case "Maladie":
                this.subValueAb = "M";
                break;

            case "Engagement familial":
                this.subValueAb = "EF";
                break;

            case "Problème technique":
                this.subValueAb = "PT";
                break;

        }

    }

    public PointageFront() {

    }

    public PointageFront(String value) {
        this.value = value;
    }

    public PointageFront(String value, String subValue) {

        this.value = value;
        this.subValue = subValue;
        if (subValue.compareTo("") != 0) {

            switch (subValue) {

                case "Client":
                    this.subValueAb = "C";
                    break;

                case "Fournisseur":
                    this.subValueAb = "F";
                    break;

                case "Prestataire":
                    this.subValueAb = "P";
                    break;

                case "Repas professionel":
                    this.subValueAb = "RP";
                    break;

                case "Autre":
                    this.subValueAb = "A";
                    break;

                case "Maladie":
                    this.subValueAb = "M";
                    break;

                case "Engagement familial":
                    this.subValueAb = "EF";
                    break;

                case "Problème technique":
                    this.subValueAb = "PT";
                    break;

            }

        }

    }

    public String getSubValueAb() {
        return subValueAb;
    }

    public void setSubValueAb(String subValueAb) {
        this.subValueAb = subValueAb;
    }

}
