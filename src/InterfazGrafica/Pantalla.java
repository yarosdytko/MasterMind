/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfazGrafica;

import Excepciones.*;
import IODatos.DatosIn;
import IODatos.DatosOut;
import MasterMind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Yaros
 */
public class Pantalla extends javax.swing.JFrame {
    
    private Almacen_Login almacen = new Almacen_Login();
    private Clasificacion clasificacion = new Clasificacion();
    private Usuario ulog1 = null;
    private Usuario ulog2 = null;
    
    //constantes para modo de juego
    private final int ENTRENAMIENTO = 0;
    private final int PARTIDA = 1;
    private final int ESTADISTICA = 2; 
    private final int CLASIFICACION = 3;
    private final int GUARDAR = 4;
    private final int SALIR = 5;
    
    //otras variables, modo entrenamiento
    private int intentos;
    private boolean entrenamientoStop = false;
    private boolean intentosOK=false;
    private boolean comboOK=false;
    private Ronda rondaEntrenamiento = null;
    private Combinacion combinacionEntrenamiento = null;
    
    //variables para modo partida
    private Ronda rondaPartida = null;
    private Partida partida = null;
    private Combinacion combinacionSecreta = null;
    private Combinacion combinacionIntento = null;
    private Usuario u1Ronda=null;
    private Usuario u2Ronda=null;
    private int indiceRondaActual=0;
    
    private boolean partidaSelected=false;
    
    /**
     * Creates new form Pantalla
     */
    public Pantalla() {
        
        //añado un usuario para pruebas
        /*
        try {
            almacen.registrar(new Usuario("u1", "123",true));
            almacen.registrar(new Usuario("u2", "123",true));
        } catch (UsuarioYaExisteException ex) {
            Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        initComponents();
        
        Login.setVisible(true);
        NuevoUsuario.setVisible(false);
        Entrenamiento.setVisible(false);
        EstadisticaUsuario.setVisible(false);
        Partida.setVisible(false);
    }
    
    private void showMessage(String s){
        JOptionPane.showMessageDialog(rootPane, s);
    }
    
    //intercambia el turno entre usuarios loguedados
    private void cambioDeTurno(int indiceDeRonda){
        if(indiceDeRonda%2==0){ //rondas pares
            u1Ronda=ulog1;
            u2Ronda=ulog2;
            
        } else {  //rondas impares
            u1Ronda=ulog2;
            u2Ronda=ulog1;
        }
        jLabelPUsuario1.setText("");
        jLabelPUsuario2.setText("");
        jLabelPUsuario1.setText(u1Ronda.getNombre());
        jLabelPUsuario2.setText(u2Ronda.getNombre());
        //jTextAreaPartidaInfo.append(partida.getHistoricoIntentos(indiceDeRonda-i));
        showMessage("Cambio de turno "+u1Ronda.getNombre()+" VS "+u2Ronda.getNombre());
        
    }
    
    private void showMessageCombinacionNoValida(){
        showMessage("Combinacion no valida\nColores: B - blanco, "
                    + "N - negro, A - azul, R - rojo, V - verde, M - marron");
    }
    
    private int seleccionarModoDeJuego(){
        int i= -1;
        
        Object[] options={"Entrenamiento","Partida","Estadistica","Clasificaion","Guardar","Salir"};
        
        do{
            i = JOptionPane.showOptionDialog(this, "Selecciona modo de juego", 
                "Modo de juego", JOptionPane.YES_NO_OPTION, 
                JOptionPane.PLAIN_MESSAGE, null, options, options[5]);
            if(i==-1){
                showMessage("Por favor selecciona una de las opciones");
            }
        } while(i==-1);
        
        return i;
    }
    
    private void verEstadistica(Usuario usuario){
        jTextAreaEstUsuario.setText(""); // se limpia el area de texto
        jLabelESTPJugadas.setText(Integer.toString(usuario.getPartidas_jugadas()));
        jLabelESTPGanadas.setText(Integer.toString(usuario.getPartidas_ganadas()));
        jLabelESTPPerdidas.setText(Integer.toString(usuario.getPartidas_perdidas()));
        jLabelESTPVictorias.setText(Double.toString(usuario.getPorcentaje_victorias()));
        jLabelESTPAnotados.setText(Integer.toString(usuario.getPuntos_anotados()));
        jLabelESTPEncajados.setText(Integer.toString(usuario.getPuntos_encajados()));
        //
        StringBuilder sb = new StringBuilder();
        if(usuario.getPartidas_jugadas()>0){
            ArrayList<Partida> partidasUsuario = usuario.getPartidasJugadas();
            jComboBoxPartidas.setEnabled(true);
            for (int i = 0; i < partidasUsuario.size(); i++) {
                sb.append(i+1).append(" ").append(partidasUsuario.get(i).toString()).append("\n");
                jComboBoxPartidas.addItem("Partida "+(i+1));
            }
        } else {
            jComboBoxPartidas.setEnabled(false);
            sb.append("El usuario ").append(usuario.getNombre()).append(" no ha jugado partidas");
        }
        jTextAreaEstUsuario.setText(sb.toString());

    }
    
    private void guardarDatosEnDisco(){
        jFileChooser1.setDialogTitle("Guardar archivo");
        jFileChooser1.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.ser", "ser", "serial");
        jFileChooser1.setFileFilter(filter);
        int result = jFileChooser1.showSaveDialog(rootPane);
        if(result==JFileChooser.APPROVE_OPTION){
            //escribir datos
            String nombreArchivo = jFileChooser1.getSelectedFile().getName();
            DatosOut salidaDatos = new DatosOut();
            try {
                salidaDatos.open(nombreArchivo);
            } catch (IOException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                salidaDatos.write(almacen);
                salidaDatos.write(clasificacion);
            } catch (IOException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                salidaDatos.close();
            } catch (IOException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            showMessage("Datos guardados en archivo: "+nombreArchivo);
        }
        
        mostrarMenu();
    }
    
    private void leerDatosDeDisco(){
        jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser1.setDialogTitle("Selecciona archivo");
        jFileChooser1.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.ser", "ser", "serial");
        jFileChooser1.setFileFilter(filter);
        int result = jFileChooser1.showOpenDialog(rootPane);
        if(result==JFileChooser.APPROVE_OPTION){
            String nombreArchivo = jFileChooser1.getSelectedFile().getName();
            DatosIn entradaDatos = new DatosIn();
            
            try {
                entradaDatos.open(nombreArchivo);
            } catch (IOException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                almacen = (Almacen_Login)entradaDatos.read();
                clasificacion = (Clasificacion)entradaDatos.read();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                entradaDatos.close();
            } catch (IOException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(almacen!=null){
                showMessage("Datos cargados");
            } else {
                showMessage("No ha sido posible cargar datos");
            }
        }
    }
    
    private void mostrarClasificacion(){
        
        if(ulog1.getAdministrador()){
            jButtonGuardarClasificacion.setEnabled(true);
        } else {
            jButtonGuardarClasificacion.setEnabled(false);
        }
        jTextAreaClasificacion.setText("");
        jTextAreaClasificacion.setText(clasificacion.mostrarClasificacion());

        JOptionPane.showConfirmDialog(this, jPanelClasificacion, "Clasificacion",JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE);
        mostrarMenu();
    }
    
    private void mostrarMenu(){
        int opcion = seleccionarModoDeJuego();
        switch(opcion){
            case ENTRENAMIENTO:
                entrenamientoReset();
                Login.setVisible(false);
                NuevoUsuario.setVisible(false);
                EstadisticaUsuario.setVisible(false);
                Entrenamiento.setVisible(true);
                Partida.setVisible(false);
                break;
            case PARTIDA:
                partidaReset();
                Login.setVisible(false);
                NuevoUsuario.setVisible(false);
                Entrenamiento.setVisible(false);
                EstadisticaUsuario.setVisible(false);
                Partida.setVisible(true);
                showLoginDialog();
                break;
            case CLASIFICACION:
                mostrarClasificacion();
                break;
            case GUARDAR:
                guardarDatosEnDisco();
                break;
            case ESTADISTICA:
                verEstadistica(ulog1);
                Login.setVisible(false);
                NuevoUsuario.setVisible(false);
                Entrenamiento.setVisible(false);
                EstadisticaUsuario.setVisible(false);
                Partida.setVisible(false);
                EstadisticaUsuario.setVisible(true);
                break;
            case SALIR:
                System.exit(0);
                break;
        }
    }
    
    private void entrenamientoFinOpcion(){
        int i = -1;
        do{
            i=JOptionPane.showConfirmDialog(this, "Generar nueva clave para entrenamiento ?", "Mensaje", JOptionPane.YES_NO_OPTION);
        }while(i==-1);
        if(i==JOptionPane.YES_OPTION){
            entrenamientoReset();
        } else {
            mostrarMenu();
        }
    }
    
    private void entrenamientoReset(){
        rondaEntrenamiento=null;
        combinacionEntrenamiento=null;
        intentos=-1;
        intentosOK=false;
        comboOK=false;
        entrenamientoStop=false;
        jTextAreaEntrenamientoLog.setText("");
        jButtonEVerClaveSercreta.setEnabled(false);
        jButtonAddCombinacion.setEnabled(false);
        jButtonEJugar.setEnabled(false);
        jTextFieldENumIntentos.setText("");
        jLabelEIntRestantes.setText("-");
        jLabelEIntRealizados.setText("-");
        jTextFieldECombinacion.setText("");
        jButtonSetIntentos.setEnabled(true);
    }
    
    private void partidaReset(){
            rondaPartida = null;
            partida = null;
            combinacionSecreta = null;
            combinacionIntento = null;
            u1Ronda=ulog1;
            u2Ronda=null;
            indiceRondaActual=0;                   
            jLabelPTotalIntentos.setText("-");
            jLabelPIntGastados.setText("-");
            jLabelPNumeroRonda.setText("-");
            jLabelPTotRondas.setText("-");
            jButtonPJugar.setEnabled(false);
            jButtonPAddIntento.setEnabled(false);
            jButtonPAddClaveSecreta.setEnabled(true);
            
    }
    
    private Usuario identificaUsuario(Usuario usuario){
        
        Usuario u = null;
        
        try {
            u=almacen.identificar(usuario);
            //System.out.println(ulog1.toString());
        } catch (WrongPasswordException ex) {
            showMessage("Clave incorrecta");
        } catch (UsuarioNoExisteException ex) {
            showMessage("Usuario no existe");
        }
        return u;
    }
    
    private void showLoginDialog(){
        int i = -1;
        do{
            i = JOptionPane.showConfirmDialog(this, jPanelLoginDialog, "Identificar usuario 2",JOptionPane.OK_CANCEL_OPTION);
            
            if(i==JOptionPane.OK_OPTION){
                String nombre = jTextFieldLoginDialog.getText().trim();
                String clave = String.valueOf(jPasswordLoginDialog.getPassword());
                if(nombre.isEmpty() || clave.isEmpty() || "".equals(nombre) || "".equals(clave)){
                    showMessage("No se permiten campos vacios");
                    i=-1;
                } else {
                    Usuario usuario = new Usuario(nombre, clave);
                    ulog2=identificaUsuario(usuario);
                    nombre="";
                    clave="";
                    if(ulog2!=null){
                        u2Ronda=ulog2;
                        showMessage("Bienvenido "+u2Ronda.getNombre());    
                        jLabelPUsuario1.setText(u1Ronda.getNombre());
                        jLabelPUsuario2.setText(u2Ronda.getNombre());
                        Partida p = new Partida(u1Ronda, u2Ronda); //se crea nueva partida con dos usuarios
                        partida=p;
                        jLabelPTotRondas.setText(Integer.toString(partida.getNumero_de_rondas()));
                        jLabelPNumeroRonda.setText(Integer.toString(partida.getRondas_gastadas()));
                    } else {
                        jTextFieldLoginDialog.setText("");
                        jPasswordLoginDialog.setText("");
                        //setVentana(seleccionarModoDeJuego());
                        showLoginDialog();
                    }
                }
            } else {
                i=-2;
                mostrarMenu();
            }
            
        } while(i==-1);
        
        
    }
    
    private String addClaveSecreta(){
        int op = -1;
        String cl="";
        do{
            jPasswordPClaveSecreta.setText("");
            op = JOptionPane.showConfirmDialog(this, jPanelSetClaveSecreta, "Clave secreta", JOptionPane.OK_CANCEL_OPTION);
            if(op==JOptionPane.OK_OPTION){
                cl = String.valueOf(jPasswordPClaveSecreta.getPassword());
                System.out.println("Secreto: "+cl);
                if(cl.isEmpty() || "".equals(cl)){
                    showMessage("No se permiten campos vacios");
                    op=-1;
                }
            }
        } while (op!=JOptionPane.OK_OPTION);
        return cl;
    }
    
    private String addIntento(){
        int op = -1;
        String cl="";
        do{
            jTextFieldPIntento.setText("");
            op = JOptionPane.showConfirmDialog(this, jPanelSetIntento, "Clave intento", JOptionPane.OK_OPTION);
            if(op==JOptionPane.OK_OPTION){
                cl = jTextFieldPIntento.getText().trim();
                if(cl.isEmpty() || "".equals(cl)){
                    showMessage("No se permiten campos vacios");
                    op=-1;
                }
            }
        } while (op!=JOptionPane.OK_OPTION);
        return cl;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanelLoginDialog = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPasswordLoginDialog = new javax.swing.JPasswordField();
        jTextFieldLoginDialog = new javax.swing.JTextField();
        jPanelSetClaveSecreta = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPasswordPClaveSecreta = new javax.swing.JPasswordField();
        jPanelSetIntento = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldPIntento = new javax.swing.JTextField();
        jPanelClasificacion = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaClasificacion = new javax.swing.JTextArea();
        jButtonFporPVictorias = new javax.swing.JButton();
        jButtonFporPGanadas = new javax.swing.JButton();
        jButtonGuardarClasificacion = new javax.swing.JButton();
        Login = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldLoginUsuario = new javax.swing.JTextField();
        jButtonNuevoUsuario = new javax.swing.JButton();
        jButtonLogin = new javax.swing.JButton();
        jButtonCargarDatos = new javax.swing.JButton();
        jPasswordFieldPasswordUsuario = new javax.swing.JPasswordField();
        NuevoUsuario = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldNombreNUsuario = new javax.swing.JTextField();
        jTextFieldPasswordNUsuario = new javax.swing.JTextField();
        jButtonOKNUsuario = new javax.swing.JButton();
        jButtonVolverNUsuario = new javax.swing.JButton();
        jCheckBoxAdmin = new javax.swing.JCheckBox();
        Entrenamiento = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldENumIntentos = new javax.swing.JTextField();
        jButtonSetIntentos = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldECombinacion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabelEIntRestantes = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabelEIntRealizados = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaEntrenamientoLog = new javax.swing.JTextArea();
        jButtonEJugar = new javax.swing.JButton();
        jButtonAddCombinacion = new javax.swing.JButton();
        jButtonEVerClaveSercreta = new javax.swing.JButton();
        jButtonETerminar = new javax.swing.JButton();
        Partida = new javax.swing.JPanel();
        jButtonPAddClaveSecreta = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaPartidaInfo = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabelPTotalIntentos = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelPIntGastados = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabelPNumeroRonda = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabelPTotRondas = new javax.swing.JLabel();
        jButtonPAddIntento = new javax.swing.JButton();
        jButtonPJugar = new javax.swing.JButton();
        jButtonTerminarPartida = new javax.swing.JButton();
        jLabelPUsuario1 = new javax.swing.JLabel();
        jLabelPUsuario2 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        EstadisticaUsuario = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabelESTPVictorias = new javax.swing.JLabel();
        jLabelESTPAnotados = new javax.swing.JLabel();
        jLabelESTPEncajados = new javax.swing.JLabel();
        jLabelESTPJugadas = new javax.swing.JLabel();
        jLabelESTPGanadas = new javax.swing.JLabel();
        jLabelESTPPerdidas = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaEstUsuario = new javax.swing.JTextArea();
        jButtonEstVolver = new javax.swing.JButton();
        jComboBoxPartidas = new javax.swing.JComboBox<>();
        jButtonVerPartida = new javax.swing.JButton();

        jDialog1.setModal(true);

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jFileChooser1.setName(""); // NOI18N

        jLabel11.setText("Usuario");

        jLabel13.setText("Clave");

        javax.swing.GroupLayout jPanelLoginDialogLayout = new javax.swing.GroupLayout(jPanelLoginDialog);
        jPanelLoginDialog.setLayout(jPanelLoginDialogLayout);
        jPanelLoginDialogLayout.setHorizontalGroup(
            jPanelLoginDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginDialogLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanelLoginDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11))
                .addGap(42, 42, 42)
                .addGroup(jPanelLoginDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordLoginDialog, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(jTextFieldLoginDialog))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanelLoginDialogLayout.setVerticalGroup(
            jPanelLoginDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLoginDialogLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanelLoginDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldLoginDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLoginDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jPasswordLoginDialog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTextFieldLoginDialog.grabFocus();

        jLabel18.setText("Clave Secreta");

        javax.swing.GroupLayout jPanelSetClaveSecretaLayout = new javax.swing.GroupLayout(jPanelSetClaveSecreta);
        jPanelSetClaveSecreta.setLayout(jPanelSetClaveSecretaLayout);
        jPanelSetClaveSecretaLayout.setHorizontalGroup(
            jPanelSetClaveSecretaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSetClaveSecretaLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jPasswordPClaveSecreta, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanelSetClaveSecretaLayout.setVerticalGroup(
            jPanelSetClaveSecretaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSetClaveSecretaLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelSetClaveSecretaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jPasswordPClaveSecreta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jLabel22.setText("Clave Intento");

        javax.swing.GroupLayout jPanelSetIntentoLayout = new javax.swing.GroupLayout(jPanelSetIntento);
        jPanelSetIntento.setLayout(jPanelSetIntentoLayout);
        jPanelSetIntentoLayout.setHorizontalGroup(
            jPanelSetIntentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSetIntentoLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldPIntento, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanelSetIntentoLayout.setVerticalGroup(
            jPanelSetIntentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSetIntentoLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelSetIntentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextFieldPIntento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanelClasificacion.setName("Clasificacion"); // NOI18N
        jPanelClasificacion.setPreferredSize(new java.awt.Dimension(750, 500));

        jTextAreaClasificacion.setEditable(false);
        jTextAreaClasificacion.setColumns(20);
        jTextAreaClasificacion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextAreaClasificacion.setRows(5);
        jScrollPane3.setViewportView(jTextAreaClasificacion);

        jButtonFporPVictorias.setText("Filtrar por porcetaje de victorias");
        jButtonFporPVictorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFporPVictoriasActionPerformed(evt);
            }
        });

        jButtonFporPGanadas.setText("Filtrar por partidas ganadas");
        jButtonFporPGanadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFporPGanadasActionPerformed(evt);
            }
        });

        jButtonGuardarClasificacion.setText("Guardar en archivo");
        jButtonGuardarClasificacion.setEnabled(false);
        jButtonGuardarClasificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarClasificacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelClasificacionLayout = new javax.swing.GroupLayout(jPanelClasificacion);
        jPanelClasificacion.setLayout(jPanelClasificacionLayout);
        jPanelClasificacionLayout.setHorizontalGroup(
            jPanelClasificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClasificacionLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanelClasificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelClasificacionLayout.createSequentialGroup()
                        .addComponent(jButtonFporPVictorias)
                        .addGap(35, 35, 35)
                        .addComponent(jButtonFporPGanadas)
                        .addGap(36, 36, 36)
                        .addComponent(jButtonGuardarClasificacion)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanelClasificacionLayout.setVerticalGroup(
            jPanelClasificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClasificacionLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanelClasificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonFporPVictorias)
                    .addComponent(jButtonFporPGanadas)
                    .addComponent(jButtonGuardarClasificacion))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Master Mind");
        setResizable(false);
        getContentPane().setLayout(new java.awt.CardLayout());

        Login.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel1.setText("Usuario");

        jLabel2.setText("Clave");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Master Mind");

        jButtonNuevoUsuario.setText("Nuevo Usuario");
        jButtonNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoUsuarioActionPerformed(evt);
            }
        });

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonCargarDatos.setText("Cargar Datos");
        jButtonCargarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCargarDatosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LoginLayout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, LoginLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(LoginLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonCargarDatos))
                            .addGroup(LoginLayout.createSequentialGroup()
                                .addComponent(jButtonNuevoUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonLogin))
                            .addGroup(LoginLayout.createSequentialGroup()
                                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(87, 87, 87)
                                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldLoginUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                                    .addComponent(jPasswordFieldPasswordUsuario))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(103, 103, 103))
        );
        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel3)
                .addGap(62, 62, 62)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldLoginUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordFieldPasswordUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevoUsuario)
                    .addComponent(jButtonLogin))
                .addGap(76, 76, 76)
                .addComponent(jButtonCargarDatos)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        getContentPane().add(Login, "card2");

        NuevoUsuario.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel4.setText("Usuario");

        jLabel5.setText("Clave");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Nuevo Usuario");

        jButtonOKNUsuario.setText("Ok");
        jButtonOKNUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKNUsuarioActionPerformed(evt);
            }
        });

        jButtonVolverNUsuario.setText("Volver");
        jButtonVolverNUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverNUsuarioActionPerformed(evt);
            }
        });

        jCheckBoxAdmin.setText("Administrador");

        javax.swing.GroupLayout NuevoUsuarioLayout = new javax.swing.GroupLayout(NuevoUsuario);
        NuevoUsuario.setLayout(NuevoUsuarioLayout);
        NuevoUsuarioLayout.setHorizontalGroup(
            NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jLabel6))
                    .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                                .addComponent(jButtonOKNUsuario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonVolverNUsuario))
                            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(87, 87, 87)
                                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCheckBoxAdmin)
                                    .addComponent(jTextFieldNombreNUsuario)
                                    .addComponent(jTextFieldPasswordNUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))))))
                .addGap(103, 103, 103))
        );
        NuevoUsuarioLayout.setVerticalGroup(
            NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NuevoUsuarioLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(62, 62, 62)
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldNombreNUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldPasswordNUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxAdmin)
                .addGap(15, 15, 15)
                .addGroup(NuevoUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOKNUsuario)
                    .addComponent(jButtonVolverNUsuario))
                .addContainerGap(194, Short.MAX_VALUE))
        );

        getContentPane().add(NuevoUsuario, "card3");

        Entrenamiento.setToolTipText("");
        Entrenamiento.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel8.setText("Numero de intentos");

        jButtonSetIntentos.setText("OK");
        jButtonSetIntentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetIntentosActionPerformed(evt);
            }
        });

        jLabel9.setText("Combinacion");

        jLabel10.setText("Intentos definidos");

        jLabelEIntRestantes.setText("-");

        jLabel12.setText("Intentos realizados");

        jLabelEIntRealizados.setText("-");

        jTextAreaEntrenamientoLog.setEditable(false);
        jTextAreaEntrenamientoLog.setColumns(25);
        jTextAreaEntrenamientoLog.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jTextAreaEntrenamientoLog.setRows(5);
        jScrollPane1.setViewportView(jTextAreaEntrenamientoLog);

        jButtonEJugar.setText("Jugar");
        jButtonEJugar.setEnabled(false);
        jButtonEJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEJugarActionPerformed(evt);
            }
        });

        jButtonAddCombinacion.setText("Añadir");
        jButtonAddCombinacion.setEnabled(false);
        jButtonAddCombinacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddCombinacionActionPerformed(evt);
            }
        });

        jButtonEVerClaveSercreta.setText("Ver clave secreta");
        jButtonEVerClaveSercreta.setEnabled(false);
        jButtonEVerClaveSercreta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEVerClaveSercretaActionPerformed(evt);
            }
        });

        jButtonETerminar.setText("Terminar entrenamiento");
        jButtonETerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonETerminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EntrenamientoLayout = new javax.swing.GroupLayout(Entrenamiento);
        Entrenamiento.setLayout(EntrenamientoLayout);
        EntrenamientoLayout.setHorizontalGroup(
            EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntrenamientoLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(EntrenamientoLayout.createSequentialGroup()
                        .addComponent(jButtonETerminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonEJugar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, EntrenamientoLayout.createSequentialGroup()
                        .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel8))
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addGap(47, 47, 47)
                        .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EntrenamientoLayout.createSequentialGroup()
                                .addComponent(jLabelEIntRealizados)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(EntrenamientoLayout.createSequentialGroup()
                                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldENumIntentos)
                                    .addComponent(jTextFieldECombinacion, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonSetIntentos)
                                    .addComponent(jButtonAddCombinacion))
                                .addGap(63, 63, 63))
                            .addGroup(EntrenamientoLayout.createSequentialGroup()
                                .addComponent(jLabelEIntRestantes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonEVerClaveSercreta)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        EntrenamientoLayout.setVerticalGroup(
            EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntrenamientoLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldENumIntentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSetIntentos))
                .addGap(32, 32, 32)
                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldECombinacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAddCombinacion))
                .addGap(29, 29, 29)
                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabelEIntRealizados))
                .addGap(18, 18, 18)
                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabelEIntRestantes)
                    .addComponent(jButtonEVerClaveSercreta))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(EntrenamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEJugar)
                    .addComponent(jButtonETerminar))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getContentPane().add(Entrenamiento, "card4");

        Partida.setToolTipText("");
        Partida.setPreferredSize(new java.awt.Dimension(500, 500));

        jButtonPAddClaveSecreta.setText("Añadir Clave Secreta");
        jButtonPAddClaveSecreta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPAddClaveSecretaActionPerformed(evt);
            }
        });

        jTextAreaPartidaInfo.setEditable(false);
        jTextAreaPartidaInfo.setColumns(25);
        jTextAreaPartidaInfo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        jTextAreaPartidaInfo.setRows(10);
        jTextAreaPartidaInfo.setRequestFocusEnabled(false);
        jScrollPane2.setViewportView(jTextAreaPartidaInfo);

        jLabel7.setText("Total de intentos:");

        jLabelPTotalIntentos.setText("-");

        jLabel14.setText("Intentos gastados:");

        jLabelPIntGastados.setText("-");

        jLabel16.setText("Numero de ronda");

        jLabelPNumeroRonda.setText("-");

        jLabel15.setText("Total rondas");

        jLabelPTotRondas.setText("-");

        jButtonPAddIntento.setText("Añadir Intento");
        jButtonPAddIntento.setEnabled(false);
        jButtonPAddIntento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPAddIntentoActionPerformed(evt);
            }
        });

        jButtonPJugar.setText("Jugar");
        jButtonPJugar.setEnabled(false);
        jButtonPJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPJugarActionPerformed(evt);
            }
        });

        jButtonTerminarPartida.setText("Terminar partida");
        jButtonTerminarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTerminarPartidaActionPerformed(evt);
            }
        });

        jLabelPUsuario1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPUsuario1.setText(" ");
        jLabelPUsuario1.setToolTipText("");

        jLabelPUsuario2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelPUsuario2.setText(" ");

        jLabel17.setText("Colores: B - blanco, N - negro, A - azul, R - rojo, V - verde, M - marron");

        javax.swing.GroupLayout PartidaLayout = new javax.swing.GroupLayout(Partida);
        Partida.setLayout(PartidaLayout);
        PartidaLayout.setHorizontalGroup(
            PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PartidaLayout.createSequentialGroup()
                .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PartidaLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(PartidaLayout.createSequentialGroup()
                                .addComponent(jLabelPUsuario1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelPUsuario2))
                            .addComponent(jButtonTerminarPartida)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PartidaLayout.createSequentialGroup()
                                .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(PartidaLayout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelPTotRondas))
                                    .addGroup(PartidaLayout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelPNumeroRonda)))
                                .addGap(24, 24, 24)
                                .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(PartidaLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelPTotalIntentos))
                                    .addGroup(PartidaLayout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelPIntGastados)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonPJugar))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PartidaLayout.createSequentialGroup()
                                .addComponent(jButtonPAddClaveSecreta)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonPAddIntento))))
                    .addGroup(PartidaLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        PartidaLayout.setVerticalGroup(
            PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PartidaLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPUsuario1)
                    .addComponent(jLabelPUsuario2))
                .addGap(30, 30, 30)
                .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonPAddClaveSecreta)
                    .addComponent(jButtonPAddIntento))
                .addGap(39, 39, 39)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabelPIntGastados)
                    .addComponent(jLabel16)
                    .addComponent(jLabelPNumeroRonda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabelPTotRondas)
                    .addComponent(jLabel7)
                    .addComponent(jLabelPTotalIntentos)
                    .addComponent(jButtonPJugar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTerminarPartida)
                .addGap(10, 10, 10))
        );

        getContentPane().add(Partida, "card4");

        EstadisticaUsuario.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel19.setText("Partidas ganadas");

        jLabel20.setText("Partidas perdidas");

        jLabel21.setText("Partidas jugadas");

        jLabel25.setText("Porcentaje victorias");

        jLabel26.setText("Puntos anotados");

        jLabel27.setText("Puntos encajados");

        jLabelESTPVictorias.setText("-");

        jLabelESTPAnotados.setText("-");

        jLabelESTPEncajados.setText("-");

        jLabelESTPJugadas.setText("-");

        jLabelESTPGanadas.setText("-");

        jLabelESTPPerdidas.setText("-");

        jTextAreaEstUsuario.setEditable(false);
        jTextAreaEstUsuario.setColumns(20);
        jTextAreaEstUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextAreaEstUsuario.setRows(5);
        jTextAreaEstUsuario.setFocusable(false);
        jScrollPane4.setViewportView(jTextAreaEstUsuario);

        jButtonEstVolver.setText("Volver");
        jButtonEstVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEstVolverActionPerformed(evt);
            }
        });

        jComboBoxPartidas.setEnabled(false);
        jComboBoxPartidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPartidasActionPerformed(evt);
            }
        });

        jButtonVerPartida.setText("Ver partida");
        jButtonVerPartida.setEnabled(false);
        jButtonVerPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerPartidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EstadisticaUsuarioLayout = new javax.swing.GroupLayout(EstadisticaUsuario);
        EstadisticaUsuario.setLayout(EstadisticaUsuarioLayout);
        EstadisticaUsuarioLayout.setHorizontalGroup(
            EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EstadisticaUsuarioLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEstVolver)
                    .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(EstadisticaUsuarioLayout.createSequentialGroup()
                            .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(EstadisticaUsuarioLayout.createSequentialGroup()
                                    .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel21))
                                    .addGap(19, 19, 19)
                                    .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabelESTPGanadas, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelESTPJugadas)))
                                .addGroup(EstadisticaUsuarioLayout.createSequentialGroup()
                                    .addComponent(jLabel20)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabelESTPPerdidas)))
                            .addGap(45, 45, 45)
                            .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel27)
                                .addComponent(jLabel25)
                                .addComponent(jLabel26))
                            .addGap(18, 18, 18)
                            .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(EstadisticaUsuarioLayout.createSequentialGroup()
                                    .addComponent(jLabelESTPAnotados)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(EstadisticaUsuarioLayout.createSequentialGroup()
                                    .addComponent(jLabelESTPEncajados)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonVerPartida))
                                .addGroup(EstadisticaUsuarioLayout.createSequentialGroup()
                                    .addComponent(jLabelESTPVictorias)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBoxPartidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        EstadisticaUsuarioLayout.setVerticalGroup(
            EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EstadisticaUsuarioLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel25)
                    .addComponent(jLabelESTPVictorias)
                    .addComponent(jLabelESTPJugadas)
                    .addComponent(jComboBoxPartidas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabelESTPAnotados)
                    .addComponent(jLabel19)
                    .addComponent(jLabelESTPGanadas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(jLabelESTPPerdidas))
                    .addGroup(EstadisticaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27)
                        .addComponent(jLabelESTPEncajados)
                        .addComponent(jButtonVerPartida)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonEstVolver)
                .addGap(27, 27, 27))
        );

        getContentPane().add(EstadisticaUsuario, "card6");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoUsuarioActionPerformed
        // TODO add your handling code here:
        Login.setVisible(false);
        NuevoUsuario.setVisible(true);
    }//GEN-LAST:event_jButtonNuevoUsuarioActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        String nombre = jTextFieldLoginUsuario.getText().trim();
        String clave = String.valueOf(jPasswordFieldPasswordUsuario.getPassword());
        System.out.println(nombre+" "+clave);
        
        if(nombre.isEmpty() || clave.isEmpty() || "".equals(nombre) || "".equals(clave)){
            showMessage("No se permiten campos vacios");
        } else {
            Usuario usuario = new Usuario(nombre, clave);
            ulog1=identificaUsuario(usuario);
            nombre="";
            clave="";
            jTextFieldLoginUsuario.setText("");
            jPasswordFieldPasswordUsuario.setText("");
            //}
            if(ulog1!=null){
                showMessage("Bienvenido "+ulog1.getNombre());
                mostrarMenu();
            }
        }
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonVolverNUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverNUsuarioActionPerformed
        // TODO add your handling code here:
        NuevoUsuario.setVisible(false);
        Login.setVisible(true);
    }//GEN-LAST:event_jButtonVolverNUsuarioActionPerformed

    private void jButtonOKNUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKNUsuarioActionPerformed
        // TODO add your handling code here:
        String nombre = jTextFieldNombreNUsuario.getText().trim();
        String clave = jTextFieldPasswordNUsuario.getText().trim();
        if(nombre.isEmpty() || clave.isEmpty() || "".equals(nombre) || "".equals(clave)){
            showMessage("Debes rellenar todos los campos");
        } else {
                Usuario usuario = new Usuario(nombre, clave,jCheckBoxAdmin.isSelected());
            
            try {
                usuario=almacen.registrar(usuario);
                clasificacion.addUsuario(usuario);
            } catch (UsuarioYaExisteException ex) {
                usuario=null;
                showMessage("El usuario "+nombre+" ya existe");
            } finally {
                nombre="";
                clave="";
                jTextFieldNombreNUsuario.setText("");
                jTextFieldPasswordNUsuario.setText("");
            }
            
            if(usuario!=null){
                showMessage("Usuario "+usuario.getNombre()+" registrado");
            } else {
                showMessage("Algo ha ido mal, usuario no registrado");
                System.exit(0);
            }
        }
    }//GEN-LAST:event_jButtonOKNUsuarioActionPerformed

    private void jButtonCargarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCargarDatosActionPerformed
        // TODO add your handling code here:
        leerDatosDeDisco();
    }//GEN-LAST:event_jButtonCargarDatosActionPerformed

    private void jButtonSetIntentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetIntentosActionPerformed
        // TODO add your handling code here:
        
        String intString = "";
        
        intString = jTextFieldENumIntentos.getText();
        
        if(intString.isEmpty()){
            showMessage("No se permiten campos vacios");
        } else {
            try {
            intentos = Integer.parseInt(intString);
            } catch (NumberFormatException nfe) {
                showMessage("Formato incorrecto solo se admiten numeros");
                jTextFieldENumIntentos.setText("");
            } finally {
                if(intentos<0){
                    showMessage("Solo se admiten enteros positivos");
                } else {
                    if(intentos==0){
                        jLabelEIntRestantes.setText("infinito");
                    } else {
                        jLabelEIntRestantes.setText(Integer.toString(intentos));
                    }
                    intentosOK=true;
                    jButtonSetIntentos.setEnabled(false);
                    jButtonAddCombinacion.setEnabled(true);
                }
            }

            if(intentosOK && comboOK){
                jButtonEJugar.setEnabled(true);
            }
        }
        
    }//GEN-LAST:event_jButtonSetIntentosActionPerformed

    private void jButtonEJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEJugarActionPerformed
        // TODO add your handling code here:
        StringBuilder str = new StringBuilder();
        
        if(rondaEntrenamiento==null){
            Ronda ronda = new Ronda(intentos);
            rondaEntrenamiento=ronda;
            if(ulog1.getAdministrador()==true){
                jButtonEVerClaveSercreta.setEnabled(true);
            }
        }
        //Combinacion combinacion = new Combinacion(combinacionEntrenamiento);
        rondaEntrenamiento.jugar(combinacionEntrenamiento);
        str.append(rondaEntrenamiento.getIntentosGastados()).append(".-").append(rondaEntrenamiento.getIntento().toString());
        str.append(" ").append(rondaEntrenamiento.getAciertos()).append(" aciertos con ").append(rondaEntrenamiento.getColocados()).append(" colocados");
        if(rondaEntrenamiento.esGanadora()){
            str.append("\n---- !!! GANADOR !!! ----");
            entrenamientoStop=true;
        }
        if(rondaEntrenamiento.getIntentosGastados()==intentos && !entrenamientoStop){
            str.append("\nNo quedan mas intentos\nLa clave era: ").append(rondaEntrenamiento.getClave().toString());
            entrenamientoStop=true;
        }
        jTextAreaEntrenamientoLog.append(str.toString()+"\n");
        jLabelEIntRealizados.setText(Integer.toString(rondaEntrenamiento.getIntentosGastados()));
        jButtonEJugar.setEnabled(false);
        if(entrenamientoStop){
            entrenamientoFinOpcion();
        }
        jButtonAddCombinacion.setEnabled(true);
        jTextFieldECombinacion.grabFocus();
    }//GEN-LAST:event_jButtonEJugarActionPerformed

    private void jButtonAddCombinacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddCombinacionActionPerformed
        // TODO add your handling code here:
        String s = jTextFieldECombinacion.getText();
        if(s.isEmpty()){
            showMessage("El campo no puede estar vacio");
        } else {
            combinacionEntrenamiento = new Combinacion(s);
            if(combinacionEntrenamiento.checkCombinacion()){
                jTextAreaEntrenamientoLog.append("Has añadido la combinacion: "+combinacionEntrenamiento.toString()+"\n");
                comboOK=true;
                jButtonAddCombinacion.setEnabled(false);
            } else {
                showMessageCombinacionNoValida();
            }
            
            if(intentosOK && comboOK){
                jButtonEJugar.setEnabled(true);
            }
        }
        jTextFieldECombinacion.setText("");
        
    }//GEN-LAST:event_jButtonAddCombinacionActionPerformed

    private void jButtonEVerClaveSercretaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEVerClaveSercretaActionPerformed
        // TODO add your handling code here:
        showMessage("La clave secreta es: "+rondaEntrenamiento.getClave().toString());
    }//GEN-LAST:event_jButtonEVerClaveSercretaActionPerformed

    private void jButtonETerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonETerminarActionPerformed
        // TODO add your handling code here:
        mostrarMenu();
    }//GEN-LAST:event_jButtonETerminarActionPerformed

    private void jButtonPAddClaveSecretaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPAddClaveSecretaActionPerformed
        // TODO add your handling code here:
        
        String secString = addClaveSecreta();
        Combinacion combinacion = new Combinacion(secString);
        if(combinacion.checkCombinacion()){
            combinacionSecreta = combinacion;
            
            // se crea y se añade nueva ronda a la partida
            rondaPartida = new Ronda(combinacionSecreta); 
            partida.addRonda(rondaPartida); //se añade ronda a la partida
            jLabelPTotalIntentos.setText(Integer.toString(partida.getIntentos()));
            jLabelPIntGastados.setText(Integer.toString(partida.getIntentosGastados()));
            jLabelPNumeroRonda.setText(Integer.toHexString(partida.getRondas_gastadas()));
            jTextAreaPartidaInfo.setText("Combinacion secreta añadida, inicio de la ronda\n");
            jButtonPAddClaveSecreta.setEnabled(false);
            jButtonPAddIntento.setEnabled(true);
        } else {
            showMessageCombinacionNoValida();
        }
    }//GEN-LAST:event_jButtonPAddClaveSecretaActionPerformed

    private void jButtonPAddIntentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPAddIntentoActionPerformed
        // TODO add your handling code here:
        String intString = addIntento();
        Combinacion combinacion = new Combinacion(intString);
        if(combinacion.checkCombinacion()){
            combinacionIntento=combinacion;
            jButtonPAddIntento.setEnabled(false);
            jTextAreaPartidaInfo.append("Combinacion añadida: "+combinacionIntento.toString()+"\n");
            jButtonPJugar.setEnabled(true);
        } else {
            showMessageCombinacionNoValida();
        }
    }//GEN-LAST:event_jButtonPAddIntentoActionPerformed

    private void jButtonPJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPJugarActionPerformed
        // TODO add your handling code here:
        //jTextAreaPartidaInfo.setText("");
        boolean finRonda = false;
        
        partida.jugarRonda(indiceRondaActual, combinacionIntento);
        //obtengo ronda jugada y muestro los datos correspondientes
        Ronda rondaActual = partida.getRonda(indiceRondaActual); //se obtiene ronda correspondiente
        jLabelPIntGastados.setText(Integer.toString(rondaActual.getIntentosGastados()));
        
        //uso este metodo para sacar un historico de intentos anteriores
        jTextAreaPartidaInfo.append(partida.getPistas(indiceRondaActual));

        if(rondaActual.getIntentosRestantes()==0 || rondaActual.esGanadora()){
            if(rondaActual.esGanadora()){
                jTextAreaPartidaInfo.append("\n----!!!GANADOR!!!----\n");
            }
            jTextAreaPartidaInfo.append("Fin de ronda\n");
            finRonda=true;
            indiceRondaActual++;
            cambioDeTurno(indiceRondaActual);
        } 
        //jTextAreaPartidaInfo.append(jTextAreaPartidaInfo.toString());
        
        if(finRonda){
            jButtonPAddClaveSecreta.setEnabled(true);
            jButtonPAddIntento.setEnabled(false);
            if(partida.getRondasRestantes()==0){
                partida.asignarEstadísticas();
                clasificacion.actualizarUsuario(ulog1);
                clasificacion.actualizarUsuario(ulog2);
                showMessage("Fin de partida");
                mostrarMenu();
            }
        } else {
            jButtonPAddIntento.setEnabled(true);
        }
        jButtonPJugar.setEnabled(false);
    }//GEN-LAST:event_jButtonPJugarActionPerformed

    private void jButtonTerminarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTerminarPartidaActionPerformed
        // TODO add your handling code here:
        mostrarMenu();
    }//GEN-LAST:event_jButtonTerminarPartidaActionPerformed

    private void jButtonEstVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEstVolverActionPerformed
        // TODO add your handling code here:
        mostrarMenu();
    }//GEN-LAST:event_jButtonEstVolverActionPerformed

    private void jButtonFporPVictoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFporPVictoriasActionPerformed
        // TODO add your handling code here:
        jTextAreaClasificacion.setText("");
        jTextAreaClasificacion.setText(clasificacion.mostrarClasificacion());
    }//GEN-LAST:event_jButtonFporPVictoriasActionPerformed

    private void jButtonFporPGanadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFporPGanadasActionPerformed
        // TODO add your handling code here:
        jTextAreaClasificacion.setText("");
        jTextAreaClasificacion.setText(clasificacion.mostrarClasificacionPorPartidasGanadas());
    }//GEN-LAST:event_jButtonFporPGanadasActionPerformed

    private void jButtonGuardarClasificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarClasificacionActionPerformed
        // TODO add your handling code here:
        jFileChooser1.setDialogTitle("Guardar archivo");
        jFileChooser1.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jFileChooser1.setFileFilter(filter);
        int result = jFileChooser1.showSaveDialog(rootPane);
        if(result==JFileChooser.APPROVE_OPTION){
            //escribir datos
            File file = jFileChooser1.getSelectedFile();
            try {
                clasificacion.volcar_a_txt(file);
                showMessage("Datos guardados en archivo: "+file.getName());
            } catch (IOException ex) {
                Logger.getLogger(Pantalla.class.getName()).log(Level.SEVERE, null, ex);
                showMessage("No ha sido podible escribir datos en archivo: "+file.getName());
            }
        }
        
    }//GEN-LAST:event_jButtonGuardarClasificacionActionPerformed

    private void jButtonVerPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVerPartidaActionPerformed
        // TODO add your handling code here:
        if(partidaSelected){
            Partida p = ulog1.getPartidasJugadas().get(jComboBoxPartidas.getSelectedIndex());
            showMessage(p.toString());
        }
    }//GEN-LAST:event_jButtonVerPartidaActionPerformed

    private void jComboBoxPartidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPartidasActionPerformed
        // TODO add your handling code here
        partidaSelected=true;
        jButtonVerPartida.setEnabled(true);
    }//GEN-LAST:event_jComboBoxPartidasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pantalla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pantalla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Entrenamiento;
    private javax.swing.JPanel EstadisticaUsuario;
    private javax.swing.JPanel Login;
    private javax.swing.JPanel NuevoUsuario;
    private javax.swing.JPanel Partida;
    private javax.swing.JButton jButtonAddCombinacion;
    private javax.swing.JButton jButtonCargarDatos;
    private javax.swing.JButton jButtonEJugar;
    private javax.swing.JButton jButtonETerminar;
    private javax.swing.JButton jButtonEVerClaveSercreta;
    private javax.swing.JButton jButtonEstVolver;
    private javax.swing.JButton jButtonFporPGanadas;
    private javax.swing.JButton jButtonFporPVictorias;
    private javax.swing.JButton jButtonGuardarClasificacion;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonNuevoUsuario;
    private javax.swing.JButton jButtonOKNUsuario;
    private javax.swing.JButton jButtonPAddClaveSecreta;
    private javax.swing.JButton jButtonPAddIntento;
    private javax.swing.JButton jButtonPJugar;
    private javax.swing.JButton jButtonSetIntentos;
    private javax.swing.JButton jButtonTerminarPartida;
    private javax.swing.JButton jButtonVerPartida;
    private javax.swing.JButton jButtonVolverNUsuario;
    private javax.swing.JCheckBox jCheckBoxAdmin;
    private javax.swing.JComboBox<String> jComboBoxPartidas;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelEIntRealizados;
    private javax.swing.JLabel jLabelEIntRestantes;
    private javax.swing.JLabel jLabelESTPAnotados;
    private javax.swing.JLabel jLabelESTPEncajados;
    private javax.swing.JLabel jLabelESTPGanadas;
    private javax.swing.JLabel jLabelESTPJugadas;
    private javax.swing.JLabel jLabelESTPPerdidas;
    private javax.swing.JLabel jLabelESTPVictorias;
    private javax.swing.JLabel jLabelPIntGastados;
    private javax.swing.JLabel jLabelPNumeroRonda;
    private javax.swing.JLabel jLabelPTotRondas;
    private javax.swing.JLabel jLabelPTotalIntentos;
    private javax.swing.JLabel jLabelPUsuario1;
    private javax.swing.JLabel jLabelPUsuario2;
    private javax.swing.JPanel jPanelClasificacion;
    private javax.swing.JPanel jPanelLoginDialog;
    private javax.swing.JPanel jPanelSetClaveSecreta;
    private javax.swing.JPanel jPanelSetIntento;
    private javax.swing.JPasswordField jPasswordFieldPasswordUsuario;
    private javax.swing.JPasswordField jPasswordLoginDialog;
    private javax.swing.JPasswordField jPasswordPClaveSecreta;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextAreaClasificacion;
    private javax.swing.JTextArea jTextAreaEntrenamientoLog;
    private javax.swing.JTextArea jTextAreaEstUsuario;
    private javax.swing.JTextArea jTextAreaPartidaInfo;
    private javax.swing.JTextField jTextFieldECombinacion;
    private javax.swing.JTextField jTextFieldENumIntentos;
    private javax.swing.JTextField jTextFieldLoginDialog;
    private javax.swing.JTextField jTextFieldLoginUsuario;
    private javax.swing.JTextField jTextFieldNombreNUsuario;
    private javax.swing.JTextField jTextFieldPIntento;
    private javax.swing.JTextField jTextFieldPasswordNUsuario;
    // End of variables declaration//GEN-END:variables
}
