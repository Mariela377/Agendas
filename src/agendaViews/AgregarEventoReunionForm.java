package agendaViews;

import agenda.Agenda;
import agenda.EventoReunion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AgregarEventoReunionForm extends JFrame {
    private JPanel panel1;
    private JTextField txtDescripcion;
    private JTextField txtFecha;
    private JSpinner spnPersonas;
    private JButton btnCancelar;
    private JButton btnGuardar;

    private Agenda laAgenda;

    public AgregarEventoReunionForm(Agenda laAgenda) {
        this.laAgenda = laAgenda;
        this.setContentPane(panel1);
        this.setTitle("Agregar Evento Reunión");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Personas: empieza en 2, mínimo 1, máximo 500
        spnPersonas.setModel(new SpinnerNumberModel(2, 1, 500, 1));

        this.pack();
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void guardar() {
        String descripcion = txtDescripcion.getText().trim();
        LocalDate fecha = Validaciones.convertirFecha(txtFecha.getText());
        int personas = (Integer) spnPersonas.getValue();

        if (Validaciones.estaVacio(descripcion)) {
            mostrarError("La descripción es obligatoria.");
            txtDescripcion.requestFocus();
            return;
        }
        if (fecha == null) {
            mostrarError("La fecha no es válida. Use el formato dd/mm/aaaa, ej: 25/10/2026");
            txtFecha.requestFocus();
            return;
        }
        if (fecha.isBefore(LocalDate.now())) {
            mostrarError("La fecha de la reunión no puede ser en el pasado.");
            txtFecha.requestFocus();
            return;
        }

        laAgenda.agregarEvento(new EventoReunion(personas, fecha, descripcion));

        JOptionPane.showMessageDialog(this, "Reunión agregada correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato inválido",
                JOptionPane.ERROR_MESSAGE);
    }
}