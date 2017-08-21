package CapaPresentacionSAC.Mapa.GUI;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import CapaPresentacionSAC.Mapa.Maps.Elevation;
import CapaPresentacionSAC.Mapa.Maps.Geocoding;
import CapaPresentacionSAC.Mapa.Maps.MapsJava;
import CapaPresentacionSAC.Mapa.Maps.Places;
import CapaPresentacionSAC.Mapa.Maps.Route;
import CapaPresentacionSAC.Mapa.Maps.StaticMaps;
import CapaPresentacionSAC.Mapa.Maps.StreetView;
import CapaPresentacionSAC.Mapa.Maps.ShowMaps;
import static capapresentacionsac.interfacesprincipales.Predios.JLabel_ME_ImaPredio;
import org.jsoup.Jsoup;

/**
 *
 * @author Luis Marcos
 */
public class MainFrame extends javax.swing.JFrame {

    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(this);
        setIconImage(new ImageIcon(getClass().getResource("avaluo.png")).getImage());
        capturarEventos();
    }

    private EventsStatusBar ObjStatusBar;
  
    
    private Geocoding ObjGeocoding=new Geocoding();
    private Elevation ObjElevation=new Elevation();
    private ShowMaps ObjShowMaps=new ShowMaps();
    private Route ObjRoute=new Route();
    private StreetView ObjStreetView=new StreetView();
    private StaticMaps ObjStaticMaps=new StaticMaps();
    private Places ObjPlaces=new Places();
    
    private void capturarEventos(){
        ObjStatusBar=new EventsStatusBar(this.jPanel5);
        recorrerComponentes(jTabbedPane1.getComponents());
        recorrerComponentes(jPanel7.getComponents());
        recorrerComponentes(jPanel8.getComponents());
    }
    double redondeoDosDecimales(double d) {
        return Math.rint(d*1000)/1000;
    }
    private void recorrerComponentes(Component[] componentes){
        for(int i=0; i<componentes.length;i++){ 
            componentes[i].addMouseListener(ObjStatusBar);
        }
    }

    private void comprobarStatus(JLabel label){
         label.setText(MapsJava.getLastRequestStatus());
    }
    private void cargarJList(ArrayList<String> arrayList, JList jlist){
        DefaultListModel listModel = new DefaultListModel();
        for(int i=0; i<arrayList.size(); i++) {
            listModel.add(i, arrayList.get(i));
        }
        jlist.setModel(listModel);
    }
    
    private void mostrarMapa(String direccion) throws IOException, URISyntaxException{
        String direccionMapa=ObjShowMaps.getURLMap(direccion);
        Desktop.getDesktop().browse(new URI(direccionMapa));
    }
    private void mostrarMapa(Double latitud, Double longitud) throws URISyntaxException, IOException{
        String direccionMapa=ObjShowMaps.getURLMap(latitud,longitud);
        Desktop.getDesktop().browse(new URI(direccionMapa));
    }

    private StaticMaps.Format seleccionarFormato(){
        StaticMaps.Format formato= StaticMaps.Format.png;
        switch(JCombo_ME_Formato.getSelectedItem().toString()){
            case "png":
                formato= StaticMaps.Format.png;
                break;
            case "png32":
                formato= StaticMaps.Format.png32;
                break;
            case "gif":
                formato= StaticMaps.Format.gif;
                break;
            case "jpg":
                formato= StaticMaps.Format.jpg;
                break;
            case "jpg_baseline":
                formato= StaticMaps.Format.jpg_baseline;
                break;
        }
        return formato;
    }
    
    private StaticMaps.Maptype seleccionarTipoMapa(){
        StaticMaps.Maptype tipoMapa= StaticMaps.Maptype.roadmap;
        switch(JCombo_ME_TipoMapa.getSelectedItem().toString()){
            case "roadmap":
                tipoMapa= StaticMaps.Maptype.roadmap;
                break;
            case "satellite":
                tipoMapa= StaticMaps.Maptype.satellite;
                break;
            case "hybrid":
                tipoMapa= StaticMaps.Maptype.hybrid;
                break;
            case "terrain":
                tipoMapa= StaticMaps.Maptype.terrain;
                break;
        }
        return tipoMapa;
    }

     private void cargarStreetView() throws MalformedURLException, UnsupportedEncodingException{
        if(!JText_SV_Direccion.getText().isEmpty()){
            JLabel_SV_Imagen.setText("");
            capapresentacionsac.interfacesprincipales.Predios.JLabel_ME_ImaPredio.setText("");
            Image imagenStreet=ObjStreetView.getStreetView(JText_SV_Direccion.getText(), new Dimension(500,500), 
                    Double.valueOf(JText_SV_horizontal.getText()), Double.valueOf(JText_SV_zoom.getText()),
                    -100);
            if(imagenStreet!=null){
                ImageIcon imgIcon=new ImageIcon(imagenStreet);
                Icon iconImage=(Icon)imgIcon;
                JLabel_SV_Imagen.setIcon(iconImage);
                JLabel_ME_ImaPredio.setIcon(iconImage);
            }
        }
    }
     
     private void crearMapa() throws MalformedURLException, UnsupportedEncodingException{
         if(!JText_ME_Direccion.getText().isEmpty()){
             this.JLabel_ME_Imagen.setText("");
             capapresentacionsac.interfacesprincipales.Predios.JLabel_ME_ImaPredio.setText("");
             Image imagenMapa=ObjStaticMaps.getStaticMap(JText_ME_Direccion.getText(),
                     Integer.valueOf(JText_ME_Zoom.getText()),new Dimension(500,500),
                     Integer.valueOf(JText_ME_Escala.getText()),this.seleccionarFormato(),
                     this.seleccionarTipoMapa());
            if(imagenMapa!=null){
                ImageIcon imgIcon=new ImageIcon(imagenMapa);
                Icon iconImage=(Icon)imgIcon;
                JLabel_ME_Imagen.setIcon(iconImage);
                JLabel_ME_ImaPredio.setIcon(iconImage);
            }
         }
         
         //JLabel_ME_ImaPredio
     }
     private class MyTableModel extends DefaultTableModel {

         public MyTableModel(Object[][] data, Object[] columnNames) {
             super(data, columnNames);
         }

      @Override
      public Class<?> getColumnClass(int columnIndex) {
                    Class<?> clazz = Object.class;
      Object aux = getValueAt(0, columnIndex);
       if (aux != null) {
        clazz = aux.getClass();
       }

       return clazz;
      }

     }
    private void rellenarPlaces(String[][] resultadoPlaces) throws MalformedURLException, IOException{
        //this.JLabel_Pl_Status.setText(MapsJava.getLastRequestStatus());
        if(resultadoPlaces.length>0){
            String[] columnas=new String[6];
            columnas[0]="Place";columnas[1]="Dirección";columnas[2]="Latitud";columnas[3]="Longitud";columnas[4]="Tipo";columnas[5]="Referencia";
            Object[][] obj=new Object[resultadoPlaces.length][resultadoPlaces[0].length];
            for(int i=0; i<obj.length;i++){
                obj[i][0]=resultadoPlaces[i][0].toString();
                obj[i][1]=resultadoPlaces[i][1].toString();
                obj[i][2]=resultadoPlaces[i][2].toString();
                obj[i][3]=resultadoPlaces[i][3].toString();
                Image imageCargada;
                imageCargada=ImageIO.read(new URL(resultadoPlaces[i][4]));
                imageCargada=imageCargada.getScaledInstance(20,20,Image.SCALE_FAST);
                obj[i][4]=new ImageIcon(imageCargada);
                obj[i][5]=resultadoPlaces[i][5].toString();
            }

        }
    }
    private void borrarTable(JTable jtable){
        jtable.setModel(new DefaultTableModel());
    }
    
    private void abrirFramePlaces(String referenciaPlace) throws UnsupportedEncodingException{
        if(!referenciaPlace.isEmpty()){
            for(UIManager.LookAndFeelInfo laf:UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(laf.getName()))
                    try {
                    UIManager.setLookAndFeel(laf.getClassName());
                } catch (Exception ex) {
                }
            }
            PlacesFrame mainF=new PlacesFrame(referenciaPlace);
            mainF.setVisible(true);
      
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        JText_CD_Buscar1 = new javax.swing.JButton();
        JText_SV_Direccion = new javax.swing.JTextField();
        jSlider1 = new javax.swing.JSlider();
        JText_SV_horizontal = new javax.swing.JTextField();
        jSlider2 = new javax.swing.JSlider();
        JText_SV_zoom = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        JLabel_SV_Imagen = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        JButton_ME_Buscar = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        JText_ME_Direccion = new javax.swing.JTextField();
        JSlider_ME_Escala = new javax.swing.JSlider();
        jLabel29 = new javax.swing.JLabel();
        JText_ME_Escala = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        JCombo_ME_Formato = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        JCombo_ME_TipoMapa = new javax.swing.JComboBox();
        JSlider_ME_Zoom = new javax.swing.JSlider();
        jLabel32 = new javax.swing.JLabel();
        JText_ME_Zoom = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        JLabel_ME_Imagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MAPAS");
        setAlwaysOnTop(true);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(146, 178, 206));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(173, 173, 173), new java.awt.Color(224, 224, 224), null, null));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(353, 401));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jLabel23.setText("Dirección");

        JText_CD_Buscar1.setBackground(new java.awt.Color(153, 153, 255));
        JText_CD_Buscar1.setText("Buscar");
        JText_CD_Buscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JText_CD_Buscar1ActionPerformed(evt);
            }
        });

        jSlider1.setMaximum(360);
        jSlider1.setValue(180);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        JText_SV_horizontal.setText("180");

        jSlider2.setMaximum(120);
        jSlider2.setMinimum(10);
        jSlider2.setValue(90);
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        JText_SV_zoom.setText("90");
        JText_SV_zoom.setToolTipText("");

        jLabel24.setText("Horizontal");

        jLabel27.setText("Zoom");

        JLabel_SV_Imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabel_SV_Imagen.setText("Imagen StreetView");
        jScrollPane4.setViewportView(JLabel_SV_Imagen);
        JLabel_SV_Imagen.getAccessibleContext().setAccessibleName("imagenSV");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(JText_SV_Direccion)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JText_SV_horizontal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JText_SV_zoom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(JText_CD_Buscar1, javax.swing.GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JText_SV_Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JText_SV_horizontal)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(JText_SV_zoom)
                        .addComponent(jSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JText_CD_Buscar1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel23.getAccessibleContext().setAccessibleName("direccionSV");
        JText_CD_Buscar1.getAccessibleContext().setAccessibleName("direccionSV");
        JText_SV_Direccion.getAccessibleContext().setAccessibleName("direccionSV");
        jSlider1.getAccessibleContext().setAccessibleName("horizontalSV");
        JText_SV_horizontal.getAccessibleContext().setAccessibleName("horizontalSV");
        jSlider2.getAccessibleContext().setAccessibleName("zoomSV");
        JText_SV_zoom.getAccessibleContext().setAccessibleName("zoomSV");
        jLabel24.getAccessibleContext().setAccessibleName("horizontalSV");
        jLabel27.getAccessibleContext().setAccessibleName("zoomSV");
        jScrollPane4.getAccessibleContext().setAccessibleName("imagenSV");

        jTabbedPane1.addTab("StreetView", jPanel7);
        jPanel7.getAccessibleContext().setAccessibleName("Principal");

        JButton_ME_Buscar.setBackground(new java.awt.Color(153, 153, 255));
        JButton_ME_Buscar.setText("Crear mapa");
        JButton_ME_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_ME_BuscarActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setText("Ingresa dirección o coordenadas:");

        JSlider_ME_Escala.setMaximum(2);
        JSlider_ME_Escala.setMinimum(1);
        JSlider_ME_Escala.setValue(1);
        JSlider_ME_Escala.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JSlider_ME_EscalaStateChanged(evt);
            }
        });

        jLabel29.setText("Escala");

        JText_ME_Escala.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JText_ME_Escala.setText("1");

        jLabel30.setText("Formato");

        JCombo_ME_Formato.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "png", "png32", "gif", "jpg", "jpg_baseline" }));

        jLabel31.setText("Tipo de mapa");

        JCombo_ME_TipoMapa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "roadmap", "satellite", "hybrid", "terrain" }));

        JSlider_ME_Zoom.setMaximum(20);
        JSlider_ME_Zoom.setMinimum(1);
        JSlider_ME_Zoom.setValue(14);
        JSlider_ME_Zoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JSlider_ME_ZoomStateChanged(evt);
            }
        });

        jLabel32.setText("Zoom");

        JText_ME_Zoom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JText_ME_Zoom.setText("14");

        JLabel_ME_Imagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        JLabel_ME_Imagen.setText("Mapa estático");
        jScrollPane5.setViewportView(JLabel_ME_Imagen);
        JLabel_ME_Imagen.getAccessibleContext().setAccessibleName("mapaEstaticoME");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JCombo_ME_Formato, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JCombo_ME_TipoMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(JSlider_ME_Escala, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JText_ME_Escala, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel29))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addComponent(JSlider_ME_Zoom, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(JText_ME_Zoom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel32))))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addComponent(JText_ME_Direccion)
                    .addComponent(jScrollPane5)
                    .addComponent(JButton_ME_Buscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JText_ME_Direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JText_ME_Escala)
                            .addComponent(JSlider_ME_Escala, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(JSlider_ME_Zoom, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(JText_ME_Zoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JCombo_ME_TipoMapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JCombo_ME_Formato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JButton_ME_Buscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        JButton_ME_Buscar.getAccessibleContext().setAccessibleName("crearMapaME");
        jLabel28.getAccessibleContext().setAccessibleName("direccionME");
        JText_ME_Direccion.getAccessibleContext().setAccessibleName("direccionME");
        JSlider_ME_Escala.getAccessibleContext().setAccessibleName("escalaME");
        jLabel29.getAccessibleContext().setAccessibleName("escalaME");
        JText_ME_Escala.getAccessibleContext().setAccessibleName("escalaME");
        jLabel30.getAccessibleContext().setAccessibleName("formatoME");
        JCombo_ME_Formato.getAccessibleContext().setAccessibleName("formatoME");
        jLabel31.getAccessibleContext().setAccessibleName("tipoMapaME");
        JCombo_ME_TipoMapa.getAccessibleContext().setAccessibleName("tipoMapaME");
        JSlider_ME_Zoom.getAccessibleContext().setAccessibleName("zoomME");
        jLabel32.getAccessibleContext().setAccessibleName("zoomME");
        JText_ME_Zoom.getAccessibleContext().setAccessibleName("zoomME");
        jScrollPane5.getAccessibleContext().setAccessibleName("mapaEstaticoME");

        jTabbedPane1.addTab("Mapa estático", jPanel8);
        jPanel8.getAccessibleContext().setAccessibleName("Principal");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Principal");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void JSlider_ME_ZoomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JSlider_ME_ZoomStateChanged
        this.JText_ME_Zoom.setText(String.valueOf(JSlider_ME_Zoom.getValue()));
    }//GEN-LAST:event_JSlider_ME_ZoomStateChanged

    private void JSlider_ME_EscalaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JSlider_ME_EscalaStateChanged
        this.JText_ME_Escala.setText(String.valueOf(JSlider_ME_Escala.getValue()));
    }//GEN-LAST:event_JSlider_ME_EscalaStateChanged

    private void JButton_ME_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_ME_BuscarActionPerformed
        try {
            this.crearMapa();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_JButton_ME_BuscarActionPerformed

    private void jSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        this.JText_SV_zoom.setText(String.valueOf(jSlider2.getValue()));
    }//GEN-LAST:event_jSlider2StateChanged

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        this.JText_SV_horizontal.setText(String.valueOf(jSlider1.getValue()));
    }//GEN-LAST:event_jSlider1StateChanged

    private void JText_CD_Buscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JText_CD_Buscar1ActionPerformed
        try {
            cargarStreetView();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_JText_CD_Buscar1ActionPerformed
       
 
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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_ME_Buscar;
    private javax.swing.JComboBox JCombo_ME_Formato;
    private javax.swing.JComboBox JCombo_ME_TipoMapa;
    private javax.swing.JLabel JLabel_ME_Imagen;
    private javax.swing.JLabel JLabel_SV_Imagen;
    private javax.swing.JSlider JSlider_ME_Escala;
    private javax.swing.JSlider JSlider_ME_Zoom;
    private javax.swing.JButton JText_CD_Buscar1;
    private javax.swing.JTextField JText_ME_Direccion;
    private javax.swing.JTextField JText_ME_Escala;
    private javax.swing.JTextField JText_ME_Zoom;
    private javax.swing.JTextField JText_SV_Direccion;
    private javax.swing.JTextField JText_SV_horizontal;
    private javax.swing.JTextField JText_SV_zoom;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JSlider jSlider2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
