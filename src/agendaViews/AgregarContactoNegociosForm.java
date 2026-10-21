package agendaViews;

import agenda.Agenda;
import agenda.ContactoNegocios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarContactoNegociosForm extends JFrame {
    private JPanel panel1;
    private JTextField txtNombre;
    private JSpinner spnEdad;
    private JComboBox<String> cmbGenero;
    private JTextField txtTelefono;
    private JTextField txtCedula;
    private JTextField txtCorreo;
    private JButton btnCancelar;
    private JButton btnGuardar;

    private Agenda laAgenda;

    public AgregarContactoNegociosForm(Agenda laAgenda) {
        this.laAgenda = laAgenda;
        this.setContentPane(panel1);
        this.setTitle("Agregar Contacto de Negocios");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        spnEdad.setModel(new SpinnerNumberModel(18, 0, 120, 1));
        cmbGenero.setModel(new DefaultComboBoxModel<>(
                new String[]{"-- Seleccione --", "Masculino", "Femenino", "Otro"}));

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
        String nombre = txtNombre.getText().trim();
        int edad = (Integer) spnEdad.getValue();
        int genero = cmbGenero.getSelectedIndex();
        String telefono = txtTelefono.getText().trim();
        String textoCedula = txtCedula.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (!Validaciones.soloLetras(nombre)) {
            mostrarError("El nombre es obligatorio y solo puede tener letras.");
            txtNombre.requestFocus();
            return;
        }
        if (genero == 0) {
            mostrarError("Debe seleccionar un género.");
            cmbGenero.requestFocus();
            return;
        }
        if (!Validaciones.esTelefonoValido(telefono)) {
            mostrarError("El teléfono debe tener el formato 8888-8888.");
            txtTelefono.requestFocus();
            return;
        }
        if (!Validaciones.esCedulaValida(textoCedula)) {
            mostrarError("La cédula debe tener exactamente 9 dígitos (sin guiones).");
            txtCedula.requestFocus();
            return;
        }
        int cedula = Integer.parseInt(textoCedula);
        if (laAgenda.buscarContacto(cedula) != null) {
            mostrarError("Ya existe un contacto con la cédula " + cedula + ".");
            txtCedula.requestFocus();
            return;
        }
        if (!Validaciones.esCorreoValido(correo)) {
            mostrarError("El correo no es válido. Ejemplo: nombre@empresa.com");
            txtCorreo.requestFocus();
            return;
        }

        laAgenda.agregarContacto(new ContactoNegocios(
                nombre, edad, genero, telefono, cedula, correo));

        JOptionPane.showMessageDialog(this, "Contacto agregado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato inválido",
                JOptionPane.ERROR_MESSAGE);
    }
}