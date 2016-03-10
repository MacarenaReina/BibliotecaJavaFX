package dad.bibliotecafx.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import dad.bibliotecafx.modelo.Usuario;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class ReportsUtils {
	
	public void generarCarnet(Usuario usuario) throws IOException, JRException {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios.add(usuario);
		Image imagen = ImageIO.read(ReportsUtils.class.getResource("/dad/bibliotecafx/images/icono_libros.png"));
//		Image foto = ImageIO.read(ReportsUtils.class.getResource("/dad/bibliotecafx/images/NoPhoto.jpg"));
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("IMAGEN", imagen);
//		parametros.put("FOTO", foto);
		InputStream is = ReportsUtils.class.getResourceAsStream("/dad/bibliotecafx/reports/carnet_usuario.jasper");
		JRDataSource dataSource = new JRBeanCollectionDataSource(usuarios);
		JasperPrint jasperPrint = JasperFillManager.fillReport(is, parametros, dataSource);
		JasperViewer viewer = new JasperViewer(jasperPrint, false);
		viewer.setTitle("Carnet de Biblioteca");
		viewer.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(ReportsUtils.class.getResource("/dad/bibliotecafx/images/biblioteca.png")));
		viewer.setVisible(true);
	}
	
	
	
}
