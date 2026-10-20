package agendaViews;

import agenda.Agenda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{
    private JPanel panel1;
    private JButton BTAgregar;
    private JButton BTMostrar;

    public MainForm(Agenda laAgenda) throws HeadlessException {
        this.setContentPane(panel1);
        this.setTitle("Mi super mega agenda");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        BTMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new MostrarAgendaForm(laAgenda)).setVisible(true);
            }
        });
    }
}
