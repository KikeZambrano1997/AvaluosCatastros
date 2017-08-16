/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegociosSAC;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Pamela
 */
public class ValidacionesFormularios {
    
    public void numeros (JTextField a){
        a.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if (!Character.isDigit(c)){
                    //getToolkit().beep();
                    e.consume();
                }
            }
        });
    }
    
}
