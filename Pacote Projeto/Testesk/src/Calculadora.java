import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Calculadora extends JFrame implements ActionListener {

    protected Color objetos = new Color(79, 236, 255,255);
    protected JPanel painel = new JPanel();
    protected ImageIcon imagem = new ImageIcon("ImagemSeduc.png");
    protected JLabel seduc = new JLabel(imagem);
    protected JFormattedTextField DataInicial, DataFinal;
    protected JTextField NomeIndividuo = new JTextField("");
    protected JButton Calcular = new JButton("Calcular");
    protected JButton Limpar = new JButton("Limpar");
    protected JButton Novo = new JButton("Novo");
    protected JTextArea Area = new JTextArea("");
    protected JTextArea AreaCalculos = new JTextArea("");
    protected JButton CalcularAreaCalculos = new JButton("Calcular");
    protected JButton LimparAreaCalculos = new JButton("Limpar");
    protected JScrollPane scroll = new JScrollPane(AreaCalculos);
    protected JScrollPane scroll1 = new JScrollPane(Area);
    Period tempoDeTrabalho = Period.parse("P0Y0M0D");
    long somaDiasEntreDuasDatas = 0;
    int ordemDosPeriodos = 0;
    DateTimeFormatter formatoLocalDate;
    LocalDate localDataInicial, localDataFinal;
    Period periodoDeTrabalho;
    long diasEntreDuasDatas = 0;

    protected Calculadora (){

        try{

            setIconImage(Toolkit.getDefaultToolkit().getImage(("Brasão_de_Rondônia.svg.png")));
            painel.setSize(700, 550);
            painel.setLayout(null);
            painel.setBackground(new Color(19,114,218,255));
            add(painel);

            seduc.setBounds(0, 0, 700, 60);

            DataInicial = new JFormattedTextField(new MaskFormatter("##/##/####"));

            DataFinal = new JFormattedTextField(new MaskFormatter("##/##/####"));

            TitledBorder titulo_Nome_Individuo = BorderFactory.createTitledBorder( "Nome");
            titulo_Nome_Individuo.setTitleJustification(TitledBorder.CENTER);
            NomeIndividuo.setBorder(titulo_Nome_Individuo);
            NomeIndividuo.setBounds(10, 70, 325, 55);
            NomeIndividuo.setFont(new Font("Arial", Font.PLAIN, 12));
            NomeIndividuo.setBackground(objetos);
            NomeIndividuo.setToolTipText("Nome da pessoa ao qual está lidando, serve para se organizar caso esteja lidando com multiplas pessoas ao mesmo tempo.");

            TitledBorder titulo_Data_Inicial = BorderFactory.createTitledBorder("Data Inicial");
            titulo_Data_Inicial.setTitleJustification(TitledBorder.CENTER);
            DataInicial.setBorder(titulo_Data_Inicial);
            DataInicial.setBounds(10, 130, 160, 55);
            DataInicial.setBackground(objetos);
            DataInicial.setToolTipText("Data inicial de um periodo.");

            TitledBorder titulo_Data_Final = BorderFactory.createTitledBorder("Data Final");
            titulo_Data_Final.setTitleJustification(TitledBorder.CENTER);
            DataFinal.setBorder(titulo_Data_Final);
            DataFinal.setBounds(175, 130, 160, 55);
            DataFinal.setBackground(objetos);
            DataFinal.setToolTipText("Data final de um periodo.");

            Calcular.setBounds(10, 200, 105, 30);
            Calcular.addActionListener(this);

            Limpar.setBounds(120, 200, 105, 30);
            Limpar.addActionListener(this);
            Limpar.addMouseListener(getMouseEvent());
            Limpar.getCursor();
            Limpar.setToolTipText("Limpa as caixas de data, caixa de texto e area de anotações.");

            Novo.setBounds(230, 200, 105, 30);
            Novo.addActionListener(this);
            Novo.setToolTipText("Cria uma nova guia.");

            TitledBorder titulo_Area = BorderFactory.createTitledBorder("Anotações");
            titulo_Area.setTitleJustification(TitledBorder.CENTER);
            Area.setBorder(titulo_Area);
            Area.setLineWrap(true);
            Area.setWrapStyleWord(true);
            Area.setFont(new Font("Arial", Font.PLAIN, 12));
            Area.setBackground(objetos);
            scroll1.setBounds(10, 240, 325, 200);
            scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            TitledBorder titulo_Area_Calculos = BorderFactory.createTitledBorder("Calculos");
            titulo_Area_Calculos.setTitleJustification(TitledBorder.CENTER);
            AreaCalculos.setBorder(titulo_Area_Calculos);
            AreaCalculos.setLineWrap(true);
            AreaCalculos.setWrapStyleWord(true);
            AreaCalculos.setEditable(false);
            AreaCalculos.setFont(new Font("Arial", Font.PLAIN, 12));
            AreaCalculos.setText("");
            AreaCalculos.setBackground(objetos);
            scroll.setBounds(350, 70, 325, 330);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            CalcularAreaCalculos.setBounds(350, 410, 105, 30);
            CalcularAreaCalculos.addActionListener(this);
            LimparAreaCalculos.setBounds(570, 410, 105, 30);
            LimparAreaCalculos.addMouseListener(getMouseEvent());
            LimparAreaCalculos.addActionListener(this);

            painel.add(seduc);
            painel.add(NomeIndividuo);
            painel.add(DataInicial);
            painel.add(DataFinal);
            painel.add(Calcular);
            painel.add(Limpar);
            painel.add(scroll1);
            painel.add(Novo);
            painel.add(scroll);
            painel.add(CalcularAreaCalculos);
            painel.add(LimparAreaCalculos);

            setTitle("Tempo de Contribuição");
            setSize(700, 490);
            setLayout(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
        }
        catch(ParseException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == Calcular){
            CalculaTempo( AreaCalculos, DataInicial, DataFinal);
            Calcular.setEnabled(false);
        }
        if(e.getSource() == Limpar){
            LIMPAR.limpar(NomeIndividuo, Area, DataInicial, DataFinal);
        }
        if(e.getSource() == Novo){
            new Calculadora();
        }
        if(e.getSource() == CalcularAreaCalculos){
            MostraCalculoFinal(AreaCalculos);
        }
        if(e.getSource() == LimparAreaCalculos){
            LIMPAR.limpar_area_calculos(AreaCalculos);
        }
    }

    private MouseAdapter getMouseEvent() {

        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                if(e.getSource() == Limpar){
                    Limpar.setText("Certeza?");
                }

                if(e.getSource() == LimparAreaCalculos){
                    LimparAreaCalculos.setText("Certeza?");
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(e.getSource() == Limpar){
                    Limpar.setText("Limpar");
                }

                if(e.getSource() == LimparAreaCalculos){
                    LimparAreaCalculos.setText("Limpar");
                }

            }

        };
    }

    protected void CalculaTempo(JTextArea AreaCalculos, JTextField DataInicial, JTextField DataFinal){

        formatoLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        localDataInicial = LocalDate.parse(DataInicial.getText(), formatoLocalDate);
        localDataFinal = LocalDate.parse(DataFinal.getText(), formatoLocalDate);


        periodoDeTrabalho = Period.between(localDataInicial, localDataFinal);
        diasEntreDuasDatas = ChronoUnit.DAYS.between(localDataInicial, localDataFinal);

        // Problema: "tempoDeTrabalho" não passa o excedente de dias para meses //

        tempoDeTrabalho = tempoDeTrabalho.plus(periodoDeTrabalho);
        somaDiasEntreDuasDatas = somaDiasEntreDuasDatas + diasEntreDuasDatas;
        tempoDeTrabalho = tempoDeTrabalho.normalized();
        ++ordemDosPeriodos;

        AreaCalculos.append(ordemDosPeriodos + "° Periodo" +
                "\n" + "De " + localDataInicial.getDayOfMonth() + "/" + localDataInicial.getMonthValue()+ "/" + localDataInicial.getYear() + " até " +
                localDataFinal.getDayOfMonth() + "/" + localDataFinal.getMonthValue() + "/" + localDataFinal.getYear() +
                "\n" + "Duração: " +periodoDeTrabalho.getYears() + " Anos " +  periodoDeTrabalho.getMonths() + " Meses " + periodoDeTrabalho.getDays() + " e Dias" +
                "\n" + "Duração em Dias: " + diasEntreDuasDatas + "\n \n");
        DataInicial.setText("");
        DataFinal.setText("");
    }

    protected void MostraCalculoFinal(JTextArea AreaCalculos){
        AreaCalculos.append("Tempo total de trabalho: " + tempoDeTrabalho.getYears() + " Anos " + tempoDeTrabalho.getMonths() + " Meses e " + tempoDeTrabalho.getDays() + " Dias"
                + "\n" + "Tempo total em Dias: " + somaDiasEntreDuasDatas + "\n\n");
    }

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException ex1) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex1);
        }

        new Calculadora();
    }
}