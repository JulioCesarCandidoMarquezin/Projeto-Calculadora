import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LIMPAR {
    public static void limpar(JTextField Nome_Individuo, JTextArea Area, JFormattedTextField Data_Inicial, JFormattedTextField Data_Final){

        Nome_Individuo.setText("");
        Data_Inicial.setText("");
        Data_Final.setText("");
        Area.setText("");
    }
    public static void limpar_area_calculos(JTextArea Area_Calculos){
        Area_Calculos.setText("");
    }
}
