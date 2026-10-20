package agendaViews;

import agenda.Agenda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MostrarAgendaForm extends JFrame {

    private JPanel panel1;
    private JTextArea textArea1;
    private JButton mostrarButton;

    private Agenda laAgenda;

    public MostrarAgendaForm(Agenda laAgenda) throws HeadlessException {
        this.laAgenda = laAgenda;
        this.setContentPane(panel1);
        this.setTitle("Mostrar Agenda");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Configuración del área de texto
        textArea1.setEditable(false);      // el usuario no puede escribir encima
        textArea1.setLineWrap(true);       // corta las líneas largas
        textArea1.setWrapStyleWord(true);  // corta por palabras completas

        // Si pusiste colores por código, van aquí (antes del pack)

        this.pack();
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);  // centra la ventana

        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTexto();
            }
        });
    }

    private void actualizarTexto() {
        textArea1.setText(laAgenda.toString());
        textArea1.setCaretPosition(0);     // el scroll vuelve arriba
    }
}