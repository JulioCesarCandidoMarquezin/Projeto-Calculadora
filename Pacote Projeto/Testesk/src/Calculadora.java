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

     Color objetos = new Color(198, 235, 255,255);
     JPanel painel = new JPanel();
     ImageIcon imagem = new ImageIcon("ImagemSeduc.png");
     JLabel seduc = new JLabel(imagem);
     JFormattedTextField DataInicial, DataFinal;
     JTextField NomeIndividuo = new JTextField("");
     JButton Calcular = new JButton("Calcular");
     JButton Limpar = new JButton("Limpar");
     JButton Novo = new JButton("Novo");
     JTextArea Area = new JTextArea("");
     JTextArea AreaCalculos = new JTextArea("");
     JButton CalcularAreaCalculos = new JButton("Calcular");
     JButton LimparAreaCalculos = new JButton("Limpar");
     JScrollPane scroll = new JScrollPane(AreaCalculos);
     JScrollPane scroll1 = new JScrollPane(Area);
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

        boolean datasSaoInvalidas = false;
        String dataInicial = DataInicial.getText();
        String dataFinal = DataFinal.getText();
        int diaDataInicial = Integer.parseInt(dataInicial.substring(0,2));
        int mesDataInicial = Integer.parseInt(dataInicial.substring(3,5));
        int anoDataInicial = Integer.parseInt(dataInicial.substring(6,10));
        int diaDataFinal = Integer.parseInt(dataFinal.substring(0,2));
        int mesDataFinal = Integer.parseInt(dataFinal.substring(3,5));
        int anoDataFinal = Integer.parseInt(dataFinal.substring(6,10));

        if(0 >= diaDataInicial || (diaDataInicial > 31 && (mesDataInicial == 1 ||  mesDataInicial == 3 ||  mesDataInicial == 5 ||  mesDataInicial == 7 ||  mesDataInicial == 8 ||  mesDataInicial == 10 ||  mesDataInicial == 12)) || (diaDataInicial > 30 && (mesDataInicial == 2 ||  mesDataInicial == 4 ||  mesDataInicial == 6 ||  mesDataInicial == 9 ||  mesDataInicial == 11)) || (diaDataInicial > 28 && mesDataInicial == 2 && anoDataInicial%4 != 0) || (diaDataInicial > 29 && mesDataInicial == 2 && anoDataInicial%4 == 0)){
            AreaCalculos.append("A data inicial é inválida! \n\n");
            datasSaoInvalidas = true;
        }

        if(0 >= diaDataFinal || (diaDataFinal > 31 && (mesDataFinal == 1 ||  mesDataFinal == 3 ||  mesDataFinal == 5 ||  mesDataFinal == 7 ||  mesDataFinal == 8 ||  mesDataFinal == 10 ||  mesDataFinal == 12)) || (diaDataFinal > 30 && (mesDataFinal == 2 ||  mesDataFinal == 4 ||  mesDataFinal == 6 ||  mesDataFinal == 9 ||  mesDataFinal == 11)) || (diaDataFinal > 28 && mesDataFinal == 2 && anoDataFinal%4 != 0) || (diaDataFinal > 29 && mesDataFinal == 2 && anoDataFinal%4 == 0)){
            AreaCalculos.append("A data final é inválida! \n\n");
            datasSaoInvalidas = true;
        }

        if(e.getSource() == Calcular && !datasSaoInvalidas){
            CalculaTempo( AreaCalculos, DataInicial, DataFinal);
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
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex1) {
            java.util.logging.Logger.getLogger(Calculadora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex1);
        }

        new Calculadora();
    }
}