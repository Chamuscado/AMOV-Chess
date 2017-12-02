package com.isec.alex_joao.amov_tp.Chess;

public class Coord {
    private Character letter;
    private Integer numb;

    public Coord() {
        letter = null;
        numb = null;
    }

    public Coord(Character letter, Integer numb) {
        Character c = Character.toUpperCase(letter);
        if (c < 'A' || c > 'H')
            throw new IllegalArgumentException("<Chess.Coord> Letra invalida: " + letter);
        else
            this.letter = c;
        if (numb < 1 || numb > 8)
            throw new IllegalArgumentException("<Chess.Coord> Numero invalido: " + numb);
        this.numb = numb;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        Character c = Character.toUpperCase(letter);
        if (c < 'A' || c > 'H')
            throw new IllegalArgumentException("<Chess.Coord> Letra invalida: " + letter);
        else
            this.letter = c;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        if (numb < 1 || numb > 8)
            throw new IllegalArgumentException("<Chess.Coord> Numero invalido: " + numb);
        this.numb = numb;
    }

    public int getNumbAsInd() {
        return numb - 1;
    }

    public int getLetAsInd() {
        return letter - 'A';
    }


}
