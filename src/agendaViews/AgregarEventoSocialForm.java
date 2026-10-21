package agendaViews;

import agenda.Agenda;
import agenda.EventoSocial;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AgregarEventoSocialForm extends JFrame {
    private JPanel panel1;
    private JTextField txtDescripcion;
    private JTextField txtFecha;
    private JTextField txtLugar;
    private JButton btnCancelar;
    private JButton btnGuardar;

    private Agenda laAgenda;

    public AgregarEventoSocialForm(Agenda laAgenda) {
        this.laAgenda = laAgenda;
        this.setContentPane(panel1);
        this.setTitle("Agregar Evento Social");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
        String lugar = txtLugar.getText().trim();
        LocalDate fecha = Validaciones.convertirFecha(txtFecha.getText());

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
            mostrarError("La fecha del evento no puede ser en el pasado.");
            txtFecha.requestFocus();
            return;
        }
        if (Validaciones.estaVacio(lugar)) {
            mostrarError("El lugar es obligatorio.");
            txtLugar.requestFocus();
            return;
        }

        laAgenda.agregarEvento(new EventoSocial(lugar, fecha, descripcion));

        JOptionPane.showMessageDialog(this, "Evento agregado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato inválido",
                JOptionPane.ERROR_MESSAGE);
    }
}