package br.com.esparza.exslotmachine;

import java.io.Serializable;

/**
 * Created by Esparza on 19/05/2017.
 */
@SuppressWarnings("serial")
public class Player implements Serializable {

    public Player(String name, int numFicha, boolean sexoM){
        this.name = name;
        this.numFicha = numFicha;
        this.sexoM = sexoM;
    }

    private String name;
    private int numFicha;
    private boolean sexoM;

    public boolean isSexoM() {
        return sexoM;
    }

    public int getNumFicha() {
        return numFicha;
    }

    public void addFicha(int numFicha) {
        this.numFicha += numFicha;
    }
    public void subFicha() {
        this.numFicha--;
    }

    public String getName() {
        return name;
    }

}
