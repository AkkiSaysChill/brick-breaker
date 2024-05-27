package brickbreaker;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gameplay gameplay = new Gameplay();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Billo Bage Billeya da ki kerre gi");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        obj.add(gameplay);
    }
}
/*
* sound only works when the programme is compiled to jar file and the sound folder and the jar file are in same folder
* idk how to fix it lol hehe
* you can fix it if you know
* Thanks !
*/
