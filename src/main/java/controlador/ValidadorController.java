/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JTextField;

/**
 *
 * @author Lenovo LOQ
 */
public class ValidadorController {

    public static void validarSoloNumeros(JTextField campo, int limite) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();

                if (!Character.isDigit(c) || campo.getText().length() >= limite) {
                    evt.consume();
                }
            }
        });
    }

    public static void validarSoloLetras(JTextField campo) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();

                if (!Character.isLetter(c) && c != ' ') {
                    evt.consume();
                }
            }
        });
    }
}
