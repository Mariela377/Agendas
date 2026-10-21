package agendaViews;

import agenda.Agenda;
import agenda.ContactoFamiliar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgregarContactoFamiliarForm extends JFrame {
    private JPanel panel1;
    private JTextField txtNombre;
    private JSpinner spnEdad;
    private JComboBox<String> cmbGenero;
    private JTextField txtTelefono;
    private JTextField txtCedula;
    private JTextField txtParentesco;
    private JButton btnCancelar;
    private JButton btnGuardar;

    private Agenda laAgenda;

    public AgregarContactoFamiliarForm(Agenda laAgenda) {
        this.laAgenda = laAgenda;
        this.setContentPane(panel1);
        this.setTitle("Agregar Contacto Familiar");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Edad: empieza en 18, mínimo 0, máximo 120, de 1 en 1
        spnEdad.setModel(new SpinnerNumberModel(18, 0, 120, 1));

        // Opciones del género (la posición 0 es "sin elegir")
        cmbGenero.setModel(new DefaultComboBoxModel<>(
                new String[]{"-- Seleccione --", "Masculino", "Femenino", "Otro"}));

        // Si querés colores por código, van aquí (antes del pack)

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
                dispose(); // cierra solo esta ventana
            }
        });
    }

    private void guardar() {
        // 1. Leer lo que escribió el usuario
        String nombre = txtNombre.getText().trim();
        int edad = (Integer) spnEdad.getValue();
        int genero = cmbGenero.getSelectedIndex(); // 1=Masc, 2=Fem, 3=Otro
        String telefono = txtTelefono.getText().trim();
        String textoCedula = txtCedula.getText().trim();
        String parentesco = txtParentesco.getText().trim();

        // 2. Validar uno por uno. Si algo falla: mensaje y salir
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
        if (laAgenda.buscarContactoPorTelefono(telefono) != null) {
            mostrarError("El teléfono " + telefono + " ya pertenece a "
                    + laAgenda.buscarContactoPorTelefono(telefono).getLaPersona().getNombre() + ".");
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
        if (!Validaciones.soloLetras(parentesco)) {
            mostrarError("El parentesco es obligatorio y solo puede tener letras.");
            txtParentesco.requestFocus();
            return;
        }

        // 3. Si todo está bien, crear y guardar
        laAgenda.agregarContacto(new ContactoFamiliar(
                nombre, edad, genero, telefono, cedula, parentesco));

        JOptionPane.showMessageDialog(this, "Contacto agregado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Dato inválido",
                JOptionPane.ERROR_MESSAGE);
    }
}